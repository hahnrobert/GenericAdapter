package com.github.hahnrobert.genericadapter.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.hahnrobert.genericadapter.core.IAdapterWrapper;

public class GroupDataProvider {

	private final DataProvider groups;
	private final List<DataProvider> children = new ArrayList<DataProvider>();
	private IAdapterWrapper adapterWrapper;

	public GroupDataProvider() {
		groups = new DataProvider();
	}

	public void setAdapterWrapper(IAdapterWrapper adapterWrapper) {
		this.adapterWrapper = adapterWrapper;
		groups.setAdapterWrapper(adapterWrapper);
	}

	public int getChildrenCount(int groupPosition) {
		return children.get(groupPosition).size();
	}

	@SuppressWarnings("unchecked")
	public <T> T getGroupItem(int groupPosition) {
		return (T) groups.getItem(groupPosition);
	}

	public int getGroupCount() {
		return groups.size();
	}

	public void clearGroup(int groupPosition) {
		clearGroup(groupPosition, true);
	}

	@SuppressWarnings("unchecked")
	public <T> T getChildItem(int groupPosition, int childPosition) {
		assertChildPosition(groupPosition, childPosition);
		return (T) children.get(groupPosition).getItem(childPosition);
	}

	public <T> void addGroupItems(T[] groupItems) {
		addGroupItems(groupItems, true);
	}

	public <T> void addGroupItems(Collection<T> groupItems) {
		addGroupItems(groupItems, true);
	}

	public <T> void addGroupItem(T groupItem) {
		addGroupItem(groupItem, true);
	}

	public <T> void addGroupItemAt(int index, T groupItem) {
		addGroupItemAt(index, groupItem, true);
	}

	public <T> void addChildItems(int groupPosition, T[] childItems) {
		addChildItems(groupPosition, childItems, true);
	}

	public <T> void addChildItem(int groupPosition, T childItem) {
		addChildItem(groupPosition, childItem, true);
	}

	public <T> void addChildItemAt(int index, int groupPosition, T childItem) {
		addChildItemAt(index, groupPosition, childItem, true);
	}

	public <T> void addChildItems(int groupPosition, Collection<T> childItems) {
		addChildItems(groupPosition, childItems, true);
	}

	public <T> T removeChildItem(int groupPosition, int childPosition) {
		return removeChildItem(groupPosition, childPosition, true);
	}

	public <T> T removeGroupItem(int groupPosition) {
		return removeGroupItem(groupPosition, true);
	}

	public void clear() {
		clear(true);
	}

	public void clear(boolean notifyDataSetChanged) {
		groups.clear();
		children.clear();
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
	}

	private void assertGroupPosition(int groupPosition) {
		groups.assertIndex(groupPosition);
	}

	private void assertChildPosition(int groupPosition, int childPosition) {
		assertGroupPosition(groupPosition);
		children.get(groupPosition).assertIndex(childPosition);
	}

	public void clearGroup(int groupPosition, boolean notifyDataSetChanged) {
		assertGroupPosition(groupPosition);
		children.get(groupPosition).clear();
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
	}

	public <T> void addGroupItems(T[] groupItems, boolean notifyDataSetChanged) {
		for (T groupItem : groupItems) {
			addGroupItem(groupItem);
		}
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
	}

	public <T> void addGroupItems(Collection<T> groupItems, boolean notifyDataSetChanged) {
		for (T groupItem : groupItems) {
			addGroupItem(groupItem);
		}
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
	}

	public <T> void addGroupItem(T groupItem, boolean notifyDataSetChanged) {
		groups.addItem(groupItem);
		DataProvider dataProvider = new DataProvider();
		dataProvider.setAdapterWrapper(adapterWrapper);
		children.add(dataProvider);
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
	}

	public <T> void addGroupItemAt(int index, T groupItem, boolean notifyDataSetChanged) {
		groups.addItemAt(index, groupItem);
		DataProvider dataProvider = new DataProvider();
		dataProvider.setAdapterWrapper(adapterWrapper);
		children.add(dataProvider);
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
	}

	public <T> void addChildItems(int groupPosition, T[] childItems, boolean notifyDataSetChanged) {
		for (T childItem : childItems) {
			addChildItem(groupPosition, childItem);
		}
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
	}

	public <T> void addChildItem(int groupPosition, T childItem, boolean notifyDataSetChanged) {
		assertGroupPosition(groupPosition);
		children.get(groupPosition).addItem(childItem);
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
	}

	public <T> void addChildItemAt(int index, int groupPosition, T childItem, boolean notifyDataSetChanged) {
		assertGroupPosition(groupPosition);
		children.get(groupPosition).addItemAt(index, childItem);
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
	}

	public <T> void addChildItems(int groupPosition, Collection<T> childItems, boolean notifyDataSetChanged) {
		for (T childItem : childItems) {
			addChildItem(groupPosition, childItem);
		}
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T removeChildItem(int groupPosition, int childPosition, boolean notifyDataSetChanged) {
		assertChildPosition(groupPosition, childPosition);
		Object removeItem = children.get(groupPosition).removeItem(childPosition);
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
		return (T) removeItem;
	}

	@SuppressWarnings("unchecked")
	public <T> T removeGroupItem(int groupPosition, boolean notifyDataSetChanged) {
		assertGroupPosition(groupPosition);
		Object removeItem = groups.removeItem(groupPosition);
		children.remove(groupPosition);
		if (notifyDataSetChanged) {
			adapterWrapper.notifyDataSetChanged();
		}
		return (T) removeItem;
	}

}
