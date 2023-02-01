# SSRecyclerViewAdapter

You don't have to create an adapter for your recycler view anymore! Using the ```SSRecyclerViewAdapter``` you can now focus on creating beautiful lists without the hassle of implementing complex adapters. ```SSRecyclerViewAdapter``` uses data binding to connect your data model with the views. It is flexible enough to let you create rows with complex design and also supports click listeners. 

## Releases
- [v1.0.1](https://github.com/sathwik-shetty/SSRecyclerViewAdapter/tree/release/release_1.0.1)

## Installation
Add the following to the root build.gradle at the end of repositories. 

```
allprojects {
 repositories {
  ...
  maven { url 'https://jitpack.io' }
 }
}
```
If you are using ```dependencyResolutionManagement```, add the following to settings.gradle instead. 

```
dependencyResolutionManagement {
 ...
 repositories {
  ...
  maven { url 'https://jitpack.io' }
 }
}
```

Add the following dependency to the app level build.gradle

```
dependencies {
 ...
 implementation 'com.github.sathwik-shetty:SSRecyclerViewAdapter:1.0.1'
}
```

## Usage
Enable data binding for your app by adding the following to the app level build.gradle

```
android {
 ...
 dataBinding {
  enabled = true
  }
  ...
}
```

### RecyclerDataModel.java
Create the data model for the recycler view. Let us call it ```RecyclerDataModel```. 

```
public class RecyclerDataModel {
    private String val;
    
    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
```

### activity_main.xml
Create an activity, say MainActivity, and modify activity_main.xml to support data binding by using `layout` tag at the root level. 

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Define a data binding variable of type SSRecyclerViewAdapter.
         This variable will be set as the adapter of the recycler view. -->
    <data>
        <variable
            name="adapter"
            type="com.sats.ssrecyclerviewadapter.SSRecyclerViewAdapter" />
    </data>
    
    <!-- Add other views -->
    
    <!-- Add a recycler view and set the adapter to the data binding variable defined above. -->
    <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:adapter="@{adapter}" 
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />
            
</layout>
```

### recycler_item.xml
Create the row for the recycler view and convert it to a data binding layout. 

<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <!-- Define a data binding variable of type RecyclerDataModel created above. -->
        <variable
            name="model"
            type="<replace_with_app_package_name>.RecyclerDataModel" />

        <!-- Define a data binding variable of type OnRecyclerViewItemClickListener to receive callbacks when the item is clicked / long pressed. -->
        <variable
            name="onClickListener"
            type="com.sats.ssrecyclerviewadapter.OnRecyclerViewItemClickListener" />
    </data>

    <!-- Create the root view with any id (recyclerItem is used as id here). 
         Pass the recyclerItem as the first argument to onClick and onLongClick methods. -->
    <androidx.appcompat.widget.LinearLayoutCompat
        android:id="@+id/recyclerItem"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:onClick="@{() -> onClickListener.onRecyclerItemClick(recyclerItem, model)}"
        android:onLongClick="@{() -> onClickListener.onRecyclerItemLongClick(recyclerItem, model)}"
        android:orientation="vertical">

        <!-- Add child views.
             Set the data for the child views using data binding as shown below. 
             Example - We can set the text of the TextView using android:text="@{model.val}" syntax of data binding -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{model.val}"
            android:textSize="20dp" />
            
        <!-- Add more views -->

    </androidx.appcompat.widget.LinearLayoutCompat>

</layout>

### MainActivity.java
Conform ```MainActivity``` to ```OnRecyclerViewItemClickListener``` if you want to receive callbacks when a row is clicked or long pressed. 

```
public class MainActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener {
    // The array of items to be populated in the recycler view. 
    private ArrayList<RecyclerDataModel> list = new ArrayList<RecyclerDataModel>();
    // The adapter to be bound to the recycler view. 
    private SSRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Populate test data into the list. 
        setData();
        
        // Create the adapter. 
        adapter = new SSRecyclerViewAdapter<RecyclerItemBinding, RecyclerDataModel>()
                .setRecyclerDataList(list) // The array list of items. 
                .setRecyclerViewItemLayoutId(R.layout.recycler_item) // The layout id of the row. 
                .setRecyclerDataId(BR.model) // The binding id of the model varaible defined in recycler_item.xml
                .setRecyclerItemClickListenerId(BR.onClickListener)  // The binding id of the listener defined in recycler_item.xml
                .setOnRecyclerViewItemClickListener(this); // To receive callbacks when a row is clicked / long pressed. 

        binding.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // Create test data. 
    private void setData() {
        for (int i = 0; i < 50; i++) {
            RecyclerDataModel obj = new RecyclerDataModel();
            obj.setVal("item " + i);
            list.add(obj);
        }
    }

    // This method is called when an item is clicked. 
    // rootView is the view passed to onRecyclerItemClick in recycler_item.xml. 
    // In our case it will be the LinearLayoutCompat with id recyclerItem. 
    @Override
    public void onRecyclerItemClick(View rootView, Object model) {
        // Perform any operation
    }

    // This method is called when an item is long pressed. 
    // rootView is the view passed to onRecyclerItemLongClick in recycler_item.xml. 
    // In our case it will be the LinearLayoutCompat with id recyclerItem. 
    @Override
    public boolean onRecyclerItemLongClick(View rootView, Object model) {
        // Perform any operation
        return true;
    }
}
```

Additionally, if you want to receive callbacks from ```onCreateViewHolder``` and ```onBindViewHolder```, conform ```MainActivity``` to ```OnRecyclerViewAdapterSetup``` and implement the following methods. 

```
public class MainActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener, OnRecyclerViewAdapterSetup {
    ...
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        adapter = new SSRecyclerViewAdapter<RecyclerItemBinding, RecyclerDataModel>()
                ...
                .setOnRecyclerViewItemSetup(this);
    }
    
    @Override
    public void onCreateViewHolder(int i) {
        // Perform any operation
    }

    @Override
    public void onBindViewHolder(View rootView, Object model, int i) {
        // Perform any operation
    }
    
    ...
}
```
