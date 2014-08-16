package com.github.hahnrobert.genericadapter.core;

import java.util.ArrayList;
import java.util.List;

/**
 * This ViewHolder will manage for a unique Layout-Class combination the view
 * holding and will be used by other viewHolders used for this layout-class
 * combination. The idea behind this ViewHolder concept is to give them some
 * kind of intelligence to automatically fill themself.
 */
public class StaticViewHolder {

	private final List<Integer> views = new ArrayList<Integer>();

	protected void addResourceId(int resourceId) {
		if (!views.contains(Integer.valueOf(resourceId))) {
			views.add(Integer.valueOf(resourceId));
		}
	}

	protected List<Integer> getResourceIds() {
		return views;
	}

}
