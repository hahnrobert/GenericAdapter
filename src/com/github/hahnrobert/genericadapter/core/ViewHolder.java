package com.github.hahnrobert.genericadapter.core;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewHolder {

	private StaticViewHolder viewHolder;
	private final SparseArray<View> views = new SparseArray<View>();
	private View convertView;

	ViewHolder(View convertView) {
		this.convertView = convertView;
	}

	void setParentViewHolder(StaticViewHolder viewHolder) {
		this.viewHolder = viewHolder;

		// import views from StaticViewHolder
		List<Integer> resourceIds = this.viewHolder.getResourceIds();
		for (int resId : resourceIds) {
			views.put(resId, convertView.findViewById(resId));
		}
	}

	/**
	 * Set a {@link TouchDelegate} for a given {@link View}.
	 * 
	 * @param viewResId
	 *            View's resource id.
	 * @param sizeInDp
	 *            {@link TouchDelegate}'s width and height. Note that the
	 *            {@link TouchDelegate} will be a square.
	 */
	public void setTouchDelegate(int viewResId, final int sizeInDp) {
		View view = getView(viewResId);
		Context context = view.getContext();
		int size = (int) convertDpToPixel(sizeInDp, context);
		setTouchDelegate(viewResId, size, size, size, size);
	}

	/**
	 * <u>Note:</u> Unlike in {@link #setTouchDelegate(int, int)} you have to
	 * set the {@link TouchDelegate}'s size in <b><u>pixels</u></b> and NOT in
	 * <u><b>dps</b></u>.<br>
	 * You can use {@link #convertDpToPixel(float, Context)} to convert dp to
	 * pixel.
	 * 
	 * @param viewResId
	 *            View's resource id.
	 * @param topInPx
	 *            Margin top in pixels.
	 * @param rightInPx
	 *            Margin right in pixels.
	 * @param bottomInPx
	 *            Margin bottom in pixels.
	 * @param leftInPx
	 *            Margin left in pixels.
	 * @see #setTouchDelegate(int, int)
	 * @see #convertDpToPixel(float, Context)
	 */
	public void setTouchDelegate(final int viewResId, final int topInPx,
			final int rightInPx, final int bottomInPx, final int leftInPx) {
		final View view = getView(viewResId);
		final View parent = (View) view.getParent();
		parent.post(new Runnable() {
			@Override
			public void run() {
				final Rect r = new Rect();
				view.getHitRect(r);
				r.left -= leftInPx;
				r.right += rightInPx;
				r.top -= topInPx;
				r.bottom += bottomInPx;
				parent.setTouchDelegate(new TouchDelegate(r, view));
			}
		});
	}

	public float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	/**
	 * @see StaticViewHolder#getView(int)
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int resId) {
		View view = views.get(resId);
		if (view == null) {
			this.viewHolder.addResourceId(resId);
			view = convertView.findViewById(resId);
			views.put(resId, view);
		}
		return (T) view;
	}

	public TextView getTextView(int resId) {
		return (TextView) getView(resId);
	}

	public EditText getEditText(int resId) {
		return (EditText) getView(resId);
	}

	public Button getButton(int resId) {
		return (Button) getView(resId);
	}

	public Spinner getSpinner(int resId) {
		return (Spinner) getView(resId);
	}

	public ImageView getImageView(int resId) {
		return (ImageView) getView(resId);
	}

	public CheckBox getCheckBox(int resId) {
		return (CheckBox) getView(resId);
	}

	public RadioButton getRadioButton(int resId) {
		return (RadioButton) getView(resId);
	}
}
