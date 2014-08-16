package com.github.hahnrobert.genericadapter;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.hahnrobert.genericadapter.core.ViewHolder;

public abstract class IViewTypeCallback<T> {

	private ViewHolder viewHolder;

	/**
	 * @deprecated Use {@link #fillView(Object, int)} instead and retrieve Views
	 *             by calling {@link #getView(int)}.
	 */
	public void fillView(T item, int position, ViewHolder viewHolder) {
		// Nothing to do here.
	}

	public <E extends View> E getView(int resourceId) {
		return viewHolder.getView(resourceId);
	}
	
	void setViewHolder(ViewHolder viewHolder) {
		this.viewHolder = viewHolder;
	}

	public TextView getTextView(int resourceId) {
		return viewHolder.getView(resourceId);
	}

	public Button getButton(int resourceId) {
		return viewHolder.getView(resourceId);
	}

	public CheckBox getCheckBox(int resourceId) {
		return viewHolder.getView(resourceId);
	}

	public RadioButton getRadioButton(int resourceId) {
		return viewHolder.getView(resourceId);
	}

	public EditText getEditText(int resourceId) {
		return viewHolder.getView(resourceId);
	}

	public ImageView getImageView(int resourceId) {
		return viewHolder.getView(resourceId);
	}

	/**
	 * 
	 * Use this method to setup your item's view. This is basically just a
	 * callback from the
	 * {@link BaseAdapter#getView(int, android.view.View, android.view.ViewGroup)}
	 * method. Use the viewHolder to get access to TextViews etc.<br>
	 * Note: The ViewHolder will automatically get filled with views. You do not
	 * have to do this at any point.
	 * 
	 * @param item
	 *            The item which shall get rendered.
	 * @param position
	 *            The item's position.
	 * @see BaseAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public abstract void fillView(T item, int position);

}
