package com.github.hahnrobert.genericadapter;

import android.widget.BaseExpandableListAdapter;

import com.github.hahnrobert.genericadapter.core.ViewHolder;

public abstract class IChildViewTypeCallback<ItemType> extends
		IViewTypeCallback<ItemType> {

	/**
	 * You can ignore this method. It's an artifact from its parent class used
	 * to save code redundancy.
	 * @deprecated
	 */
	@Override
	public final void fillView(ItemType item, int position,
			ViewHolder viewHolder) {
		// nothing to do here - use the overloaded fillView(...)
	}

	/**
	 * You can ignore this method. It's an artifact from its parent class used
	 * to save code redundancy.
	 */
	@Override
	public final void fillView(ItemType item, int position) {
		// nothing to do here - use the overloaded fillView(...)
	}

	/**
	 * @deprecated Use {@link #fillChildView(Object, int)} instead.
	 */
	public void fillChildView(ItemType item, int groupPosition,
			int childPosition, ViewHolder viewHolder) {
		// nothing to do here
	}

	/**
	 * Note that childPosition will be <code>-1</code> if it is a group item.
	 * 
	 * @see IViewTypeCallback#fillView(Object, int)
	 * @see BaseExpandableListAdapter#getChildView(int, int, boolean,
	 *      android.view.View, android.view.ViewGroup)
	 * @see BaseExpandableListAdapter#getGroupView(int, boolean,
	 *      android.view.View, android.view.ViewGroup)
	 */
	public abstract void fillChildView(ItemType item, int groupPosition, int itemPosition);

}
