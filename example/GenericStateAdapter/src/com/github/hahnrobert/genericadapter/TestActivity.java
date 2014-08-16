package com.github.hahnrobert.genericadapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.github.hahnrobert.genericadapter.core.ViewHolder;
import com.github.hahnrobert.genericstateadapter.R;

public class TestActivity extends Activity {

	public class GrandParent {
	}

	public class Parent extends GrandParent {
	}

	public class Child extends Parent {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ExpandableListView list = (ExpandableListView) findViewById(R.id.list);
		GenericExpandableListAdapter adapter = new GenericExpandableListAdapter(this);
		registerViewTypes(adapter);
		// Test basic stuff.
		adapter.getDataProvider().addGroupItem(new GrandParent());
		adapter.getDataProvider().addGroupItem(new Child());
		adapter.getDataProvider().addGroupItem(new Parent());

		// Test ViewType-Object-Inheritance:
		// only Child and GrandParent ViewTypes are registered for Children
		// thus Parent will be shown as a Grandparent.
		adapter.getDataProvider().addChildItem(1, new Child());
		adapter.getDataProvider().addChildItem(1, new Parent());
		adapter.getDataProvider().addChildItem(1, new GrandParent());
		list.setAdapter(adapter);
	}

	private void registerViewTypes(GenericExpandableListAdapter adapter) {
		adapter.registerGroupView(R.layout.row_child, GrandParent.class, new IViewTypeCallback<GrandParent>() {

			@Override
			public void fillView(GrandParent item, int position) {
				getTextView(R.id.textView1).setText("GrandParent");
			}

		});
		adapter.registerGroupView(R.layout.row_child, Parent.class, new IViewTypeCallback<Parent>() {

			@Override
			public void fillView(Parent item, int position) {
				getTextView(R.id.textView1).setText("Parent");
			}
		});
		adapter.registerGroupView(R.layout.row_child, Child.class, new IViewTypeCallback<Child>() {

			@Override
			public void fillView(Child item, int position) {
				getTextView(R.id.textView1).setText("Child");
			}
		});

		adapter.registerChildView(R.layout.row_child, Child.class, new IChildViewTypeCallback<Child>() {

			@Override
			public void fillChildView(Child item, int groupPosition, int childPosition) {
				getTextView(R.id.textView1).setText("Child A");
			}
		});
		adapter.registerChildView(R.layout.row_child, GrandParent.class, new IChildViewTypeCallback<GrandParent>() {

			@Override
			public void fillChildView(GrandParent item, int groupPosition, int childPosition) {
				getTextView(R.id.textView1).setText("GrandParent A");
			}
		});
	}

}
