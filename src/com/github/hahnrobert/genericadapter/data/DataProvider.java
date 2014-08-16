package com.github.hahnrobert.genericadapter.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.hahnrobert.genericadapter.core.IAdapterWrapper;

public class DataProvider {

	private final List<Object> mData = new ArrayList<Object>();
	private IAdapterWrapper adapter;

	public void setAdapterWrapper(IAdapterWrapper adapterWrapper) {
		this.adapter = adapterWrapper;
	}

	@SuppressWarnings("unchecked")
	public <T> T getItem(int position) {
		assertIndex(position);
		return (T) mData.get(position);
	}

	public <T> void addItems(T[] items) {
		addItems(items, true);
	}

	public <T> void addItem(T item) {
		addItem(item, true);
	}

	public <T> void addItemAt(int index, T item) {
		addItemAt(index, item, true);
	}

	public <T> void addItems(Collection<T> items) {
		addItems(items, true);
	}

	public <T> void addItems(T[] items, boolean notifyDataSetChanged) {
		for (Object item : items) {
			addItem(item, false);
		}
		if (notifyDataSetChanged) {
			adapter.notifyDataSetChanged();
		}
	}

	public <T> void addItem(T item, boolean notifyDataSetChanged) {
		mData.add(item);
		if (notifyDataSetChanged) {
			adapter.notifyDataSetChanged();
		}
	}

	public <T> void addItemAt(int index, T item, boolean notifyDataSetChanged) {
		mData.add(index, item);
		if (notifyDataSetChanged) {
			adapter.notifyDataSetChanged();
		}
	}

	public <T> void addItems(Collection<T> items, boolean notifyDataSetChanged) {
		mData.addAll(items);
		if (notifyDataSetChanged) {
			adapter.notifyDataSetChanged();
		}
	}

	public <T> T removeItem(int position) {
		return removeItem(position, true);
	}

	@SuppressWarnings("unchecked")
	public <T> T removeItem(int position, boolean notifyDataSetChanged) {
		assertIndex(position);
		Object remove = mData.remove(position);
		if (notifyDataSetChanged) {
			adapter.notifyDataSetChanged();
		}
		return (T) remove;
	}

	public void clear() {
		clear(true);
	}

	public void clear(boolean notifyDataSetChanged) {
		mData.clear();
		if (notifyDataSetChanged) {
			adapter.notifyDataSetChanged();
		}
	}

	public int getCount() {
		return mData.size();
	}

	public int size() {
		return mData.size();
	}

	protected void assertIndex(int index) {
		if (index >= mData.size()) {
			throw new RuntimeException("There is no item with such index.");
		}
	}
}
