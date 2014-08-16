package com.github.hahnrobert.genericadapter.core;

import android.view.LayoutInflater;
import android.view.View;

import com.github.hahnrobert.genericadapter.IViewTypeCallback;

public class LayoutHelper {

	private final View convertView;
	private final ViewHolder viewHolder;
	private final IViewTypeCallback<Object> callback;
	private final String uid;
	private final int layoutResId;

	public LayoutHelper(Object item, CallbackManager callbackManager,
			View convertView, LayoutInflater layoutInflater) {
		uid = callbackManager.getUid(item);

		assertViewTypeRegistered(getUid(), item);
		
		layoutResId = callbackManager.getLayout(getUid());
		callback = callbackManager.getCallback(getUid());

		if (convertView == null) {
			convertView = layoutInflater.inflate(getLayoutResId(), null);
			ViewHolder viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		this.convertView = convertView;
		viewHolder = (ViewHolder) convertView.getTag();
	}

	public static void assertViewTypeRegistered(String uid, Object item) {
		if (uid == null) {
			throw new RuntimeException("No ViewType registered for Class "
					+ item.getClass().getName());
		}
	}

	public View getConvertView() {
		return convertView;
	}

	public ViewHolder getViewHolder() {
		return viewHolder;
	}

	public IViewTypeCallback<Object> getCallback() {
		return callback;
	}

	public String getUid() {
		return uid;
	}

	public int getLayoutResId() {
		return layoutResId;
	}
}
