package com.github.hahnrobert.genericadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.github.hahnrobert.genericadapter.core.CallbackManager;
import com.github.hahnrobert.genericadapter.core.GenericBaseAdapter;
import com.github.hahnrobert.genericadapter.core.IAdapterWrapper;
import com.github.hahnrobert.genericadapter.core.LayoutHelper;
import com.github.hahnrobert.genericadapter.data.GroupDataProvider;

public class GenericExpandableListAdapter extends BaseExpandableListAdapter {

	public interface IExpandableAdapterHandler {

		public long getGroupId(int groupPosition);

		public long getChildId(int groupPosition, int childPosition);

	}

	private class AdapterWrapper implements IAdapterWrapper {

		private BaseExpandableListAdapter adapter;

		public AdapterWrapper(BaseExpandableListAdapter adapter) {
			this.adapter = adapter;
		}

		@Override
		public void notifyDataSetChanged() {
			adapter.notifyDataSetChanged();
		}

	}

	private final GroupDataProvider mData;
	private final CallbackManager groupCallbackManager = new CallbackManager();
	private final CallbackManager childCallbackManager = new CallbackManager();
	private final GenericBaseAdapter baseAdapter;
	private IExpandableAdapterHandler mHandler;

	public GenericExpandableListAdapter(Context context) {
		this(context, new GroupDataProvider());
	}

	public GenericExpandableListAdapter(Context context, GroupDataProvider dataProvider) {
		this(context, dataProvider, null);
	}

	public GenericExpandableListAdapter(Context context, IExpandableAdapterHandler handler) {
		this(context, new GroupDataProvider(), handler);
	}

	public GenericExpandableListAdapter(Context context, GroupDataProvider dataProvider, IExpandableAdapterHandler handler) {
		baseAdapter = new GenericBaseAdapter(context);
		mData = dataProvider;
		mHandler = handler;
		mData.setAdapterWrapper(new AdapterWrapper(this));
	}

	@SuppressWarnings("unchecked")
	public <T> void registerGroupView(int layoutId, Class<T> itemType, IViewTypeCallback<T> callback) {
		if (callback == null) {
			callback = (IViewTypeCallback<T>) baseAdapter.getNullCallback();
		}
		groupCallbackManager.registerLayout(layoutId, itemType, callback);
	}

	@SuppressWarnings("unchecked")
	public <T> void registerChildView(int layoutId, Class<T> itemType, IChildViewTypeCallback<T> callback) {
		if (callback == null) {
			callback = (IChildViewTypeCallback<T>) baseAdapter.getNullCallback();
		}
		childCallbackManager.registerLayout(layoutId, itemType, callback);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		Object item = getChild(groupPosition, childPosition);

		LayoutHelper layoutHelper = baseAdapter.getView(item, convertView, childCallbackManager);

		IChildViewTypeCallback<Object> callback = (IChildViewTypeCallback<Object>) layoutHelper.getCallback();
		layoutHelper.getCallback().setViewHolder(layoutHelper.getViewHolder());
		callback.fillChildView(item, groupPosition, childPosition);

		return layoutHelper.getConvertView();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		Object item = getGroup(groupPosition);

		LayoutHelper layoutHelper = baseAdapter.getView(item, convertView, groupCallbackManager);

		IViewTypeCallback<Object> callback = layoutHelper.getCallback();
		layoutHelper.getCallback().setViewHolder(layoutHelper.getViewHolder());
		callback.fillView(item, groupPosition);

		return layoutHelper.getConvertView();
	}

	@Override
	public int getChildType(int groupPosition, int childPosition) {
		Object item = getChild(groupPosition, childPosition);
		String uid = childCallbackManager.getUid(item);

		LayoutHelper.assertViewTypeRegistered(uid, item);

		return childCallbackManager.getViewTypeId(childCallbackManager.getLayout(uid));
	}

	@Override
	public int getChildTypeCount() {
		return childCallbackManager.getViewTypeCount();
	}

	@Override
	public int getGroupType(int groupPosition) {
		Object item = getGroup(groupPosition);
		String uid = groupCallbackManager.getUid(item);

		LayoutHelper.assertViewTypeRegistered(uid, item);

		return groupCallbackManager.getViewTypeId(groupCallbackManager.getLayout(uid));
	}

	public void setHandler(IExpandableAdapterHandler handler) {
		mHandler = handler;
	}

	@Override
	public int getGroupTypeCount() {
		return groupCallbackManager.getViewTypeCount();
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return getDataProvider().getChildrenCount(groupPosition);
	}

	@Override
	public Object getGroup(int groupPosition) {
		return getDataProvider().getGroupItem(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return getDataProvider().getGroupCount();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return getDataProvider().getChildItem(groupPosition, childPosition);
	}

	/**
	 * Make sure you are calling {@link #notifyDataSetChanged()} when you've
	 * added or removed data from the data provider.
	 * 
	 * @return
	 */
	public GroupDataProvider getDataProvider() {
		return mData;
	}

	/**
	 * Default implementation will return the <code>childPosition</code>. You
	 * cannot override this method. Use {@link IExpandableAdapterHandler}
	 * instead.
	 * 
	 * @see BaseExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public final long getChildId(int groupPosition, int childPosition) {
		if (mHandler != null) {
			return mHandler.getChildId(groupPosition, childPosition);
		}
		return childPosition;
	}

	/**
	 * Default implementation will return the <code>groupPosition</code>. You
	 * cannot override this method. Use {@link IExpandableAdapterHandler}
	 * instead.
	 * 
	 * @see BaseExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public final long getGroupId(int groupPosition) {
		if (mHandler != null) {
			return mHandler.getGroupId(groupPosition);
		}
		return groupPosition;
	}

	/**
	 * Default implementation will return <code>true</code>. You can override
	 * this method without any concerns.
	 * 
	 * @see BaseExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
