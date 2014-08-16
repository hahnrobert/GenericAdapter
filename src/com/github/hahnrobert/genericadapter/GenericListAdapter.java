package com.github.hahnrobert.genericadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.hahnrobert.genericadapter.core.CallbackManager;
import com.github.hahnrobert.genericadapter.core.GenericBaseAdapter;
import com.github.hahnrobert.genericadapter.core.IAdapterWrapper;
import com.github.hahnrobert.genericadapter.core.LayoutHelper;
import com.github.hahnrobert.genericadapter.data.DataProvider;

public class GenericListAdapter extends BaseAdapter {

	public interface IAdapterHandler {

		public long getItemId(int position);

	}

	private class AdapterWrapper implements IAdapterWrapper {

		private BaseAdapter adapter;

		public AdapterWrapper(BaseAdapter adapter) {
			this.adapter = adapter;
		}

		@Override
		public void notifyDataSetChanged() {
			adapter.notifyDataSetChanged();
		}

	}

	private final DataProvider mData;
	private final CallbackManager callbackManager = new CallbackManager();
	private final GenericBaseAdapter baseAdapter;
	private IAdapterHandler mHandler;

	public GenericListAdapter(Context context) {
		this(context, new DataProvider());
	}

	public GenericListAdapter(Context context, DataProvider dataProvider) {
		this(context, dataProvider, null);
	}

	public GenericListAdapter(Context context, IAdapterHandler handler) {
		this(context, new DataProvider(), handler);
	}

	public GenericListAdapter(Context context, DataProvider dataProvider, IAdapterHandler handler) {
		mHandler = handler;
		baseAdapter = new GenericBaseAdapter(context);
		mData = dataProvider;
		mData.setAdapterWrapper(new AdapterWrapper(this));
	}

	@SuppressWarnings("unchecked")
	public <T> void registerView(int layoutId, Class<T> itemType, IViewTypeCallback<T> callback) {
		if (callback == null) {
			callback = (IViewTypeCallback<T>) baseAdapter.getNullCallback();
		}
		callbackManager.registerLayout(layoutId, itemType, callback);
	}

	@Override
	public int getCount() {
		return getDataProvider().getCount();
	}

	@Override
	public Object getItem(int position) {
		return getDataProvider().getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Object item = getItem(position);
		LayoutHelper layoutHelper = baseAdapter.getView(item, convertView, callbackManager);
		layoutHelper.getCallback().setViewHolder(layoutHelper.getViewHolder());
		layoutHelper.getCallback().fillView(item, position);
		return layoutHelper.getConvertView();
	}

	@Override
	public int getViewTypeCount() {
		return callbackManager.getViewTypeCount();
	}

	@Override
	public int getItemViewType(int position) {
		Object item = getItem(position);
		String uid = callbackManager.getUid(item);

		LayoutHelper.assertViewTypeRegistered(uid, item);

		return callbackManager.getViewTypeId(callbackManager.getLayout(uid));
	}

	public void setHandler(IAdapterHandler handler) {
		mHandler = handler;
	}

	public CallbackManager getCallbackManager() {
		return callbackManager;
	}

	/**
	 * Make sure you are calling {@link #notifyDataSetChanged()} when you've
	 * added or removed data from the data provider.
	 * 
	 * @return
	 */
	public DataProvider getDataProvider() {
		return mData;
	}

	/**
	 * Default implementation will return the <code>position</code>. You cannot
	 * override this method. Use {@link IAdapterHandler} instead.
	 * 
	 * @see BaseAdapter#getItemId(int)
	 */
	@Override
	public final long getItemId(int position) {
		if (mHandler != null) {
			return mHandler.getItemId(position);
		}
		return position;
	}

}
