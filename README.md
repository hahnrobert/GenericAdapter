android-genericadapter
======================

Adapters but simple. Save all the boilerplate code.

GenericAdapter provide a powerfull Adapter for ListViews and ExpandableListViews which will save you a lot of time writing common boilerplate code. 

#### 'Intelligent ViewHolder'
The intelligent ViewHolder is a construct which will remeber what Views are used on all your inflated layouts in a particular adapter. When inflating a new item this information will be taken and Views will get imported. As a result you don't have to fill a ViewHolder manually. 

#### Layout inheritance
Sometimes you have multiple Classes which inherite from each other. Let's think about a 'Parent' which inherits from a 'GrandParent' class. 'GenericAdapter' will automatically detect that Parent inherits from 'GrandParent' and, if there is no other callback defined for 'Parent' object, they will be rendered like 'GrandParents'. 

#### Usage Example
```java
	adapter = new GenericAdapter();
	// render GrandParents
	adapter.registerGroupView(R.layout.row_family_member, GrandParent.class,
		new IAdapterCallback<GrandParent>() {

			@Override
			public void fillView(GrandParent item, int position, ViewHolder viewHolder) {
				viewHolder.getTextView(R.id.tvRelation).setText("GrandParent");
				viewHolder.getTextView(R.id.tvName).setText(item.getName());
		  	}
	});
			
	// render Parents
	adapter.registerGroupView(R.layout.row_family_member, Parent.class,
		new IAdapterCallback<Parent>() {

			@Override
			public void fillView(Parent item, int position, ViewHolder viewHolder) {
				viewHolder.getTextView(R.id.tvRelation).setText("Parent");
				viewHolder.getTextView(R.id.tvName).setText(item.getName());
			}
	});
```
Note: Due to layout inheritance, objects from type 'Child' which will inherit from 'Parent' will be rendered as 'Parents' since it's the best approximation. Of course, you can specify a callback for 'Child' objects as well. 

#### Contribute
Feel free to work on GenericAdapter and submit Pull Requests.

### License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
