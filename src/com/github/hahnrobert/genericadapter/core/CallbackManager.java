package com.github.hahnrobert.genericadapter.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.SparseArray;

import com.github.hahnrobert.genericadapter.IViewTypeCallback;

public class CallbackManager {

	private SparseArray<List<String>> viewTypes = new SparseArray<List<String>>();
	private HashMap<String, IViewTypeCallback<?>> callbacks = new HashMap<String, IViewTypeCallback<?>>();
	private SparseArray<List<Class<?>>> classes = new SparseArray<List<Class<?>>>();
	private HashMap<Class<?>, String> uidCache = new HashMap<Class<?>, String>();

	public <T> void registerLayout(int layoutResId, Class<T> itemType,
			IViewTypeCallback<T> callback) {
		final String uid = getUid(layoutResId, itemType);
		assertSingleRegistration(uid);

		// add callback
		callbacks.put(uid, callback);

		// add layout-class combination
		List<Class<?>> classes = this.classes.get(layoutResId);
		if (classes == null) {
			classes = new ArrayList<Class<?>>();
			this.classes.put(layoutResId, classes);
		}
		classes.add(itemType);

		// add layout
		List<String> uids = viewTypes.get(layoutResId);
		if (uids == null) {
			uids = new ArrayList<String>();
			this.viewTypes.put(layoutResId, uids);
		}
		uids.add(uid);
	}
	
	public int getViewTypeId(int layoutId) {
		return viewTypes.indexOfKey(layoutId);
	}

	public int getViewTypeCount() {
		return viewTypes.size();
	}
	
	private void assertSingleRegistration(String uid) {
		if (callbacks.containsKey(uid)) {
			throw new RuntimeException(
					"The class-layout combination has already been registered.");
		}
	}

	@SuppressWarnings("unchecked")
	public <T> IViewTypeCallback<T> getCallback(String uid) {
		if (uid == null) {
			return null;
		}
		return (IViewTypeCallback<T>) callbacks.get(uid);
	}

	public <T> int getLayout(String uid) {
		if (uid != null) {
			for (int i = 0; i < viewTypes.size(); i++) {
				if (viewTypes.valueAt(i).contains(uid)) {
					return viewTypes.keyAt(i);
				}
			}
		}
		return -1;
	}

	public <T> String getUid(T item) {
		String uid = uidCache.get(item.getClass());
		if (uid != null) {
			return uid;
		}
		final List<Class<?>> classHierarchy = getClassHierarchy(item);
		int priority = Integer.MAX_VALUE;

		for (int i = 0; i < classes.size(); i++) {
			int layoutResId = classes.keyAt(i);
			List<Class<?>> layoutClasses = classes.valueAt(i);

			for(Class<?> aClass: layoutClasses) {
				int index = classHierarchy.indexOf(aClass);
				if (index > -1 && index < priority) {
					// better callback found
					uid = getUid(layoutResId, classHierarchy.get(index));
					priority = index;
				}
			}
		}
		if (uid != null) {
			uidCache.put(item.getClass(), uid);
		}
		return uid;
	}

	private List<Class<?>> getClassHierarchy(Object anObject) {
		List<Class<?>> classHierarchy = new ArrayList<Class<?>>();
		Class<?> superClass = anObject.getClass();
		while (superClass != null) {
			classHierarchy.add(superClass);
			superClass = superClass.getSuperclass();
		}
		return classHierarchy;
	}

	private <T> String getUid(int layoutResId, Class<T> itemClass) {
		return layoutResId + "-" + itemClass.getName();
	}

}
