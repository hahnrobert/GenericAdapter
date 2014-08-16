package com.github.hahnrobert.genericadapter.core;

import com.github.hahnrobert.genericadapter.IChildViewTypeCallback;
import com.github.hahnrobert.genericadapter.IViewTypeCallback;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

public class GenericBaseAdapter {

	private final SparseArray<StaticViewHolder> viewHolders = new SparseArray<StaticViewHolder>();
	private final LayoutInflater layoutInflater;
	private LayoutHelper layoutHelper;
	private IChildViewTypeCallback<Object> devNull = new IChildViewTypeCallback<Object>() {

		@Override
		public void fillChildView(Object item, int groupPosition, int itemPosition) {
			// TODO Auto-generated method stub
			
		}

	};
	
	public IViewTypeCallback<Object> getNullCallback() {
		return devNull;
	}

	public GenericBaseAdapter(Context context) {
		layoutInflater = LayoutInflater.from(context);
	}

	public LayoutHelper getView(Object item, View convertView, CallbackManager callbackManager) {
		layoutHelper = new LayoutHelper(item, callbackManager, convertView, layoutInflater);

		StaticViewHolder parentViewHolder = viewHolders.get(layoutHelper.getLayoutResId());
		if (parentViewHolder == null) {
			parentViewHolder = new StaticViewHolder();
			viewHolders.put(layoutHelper.getLayoutResId(), parentViewHolder);
		}
		layoutHelper.getViewHolder().setParentViewHolder(parentViewHolder);

		return layoutHelper;
	}
}
