package edu.colorado.cavinsb.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private ListView listItems;
	//private TextView totalCostTextView;
	private int selectedItem = -1;
	private ShoppingListDB sldb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		sldb = new ShoppingListDB(this);
		
		listItems = (ListView) findViewById(R.id.main_shopping_list);
		listItems.setAdapter(getListItems());
		listItems.setOnItemClickListener(new shoppingListOnItemClickListener());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		listItems.setAdapter(getListItems());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_new_item:
			Intent intent = new Intent(this, EditItem.class);
			startActivity(intent);
		}
		return true;
	}
	
	public ArrayAdapter<ShoppingListItem> getListItems() {
		ArrayAdapter<ShoppingListItem> adapter = new ArrayAdapter<ShoppingListItem>(this, android.R.layout.simple_list_item_single_choice);
		ShoppingListItem slItem;
		String name;
		Integer quantity;
		Double price;
		
		System.out.println("Fetching readable database");
		SQLiteDatabase db = sldb.getReadableDatabase();
		
		System.out.println("Querying database");
		Cursor cursor = db.query("shopping_list_items", 
				new String[] {"name","quantity","price"}, null, null, null, null, "name");
		while (cursor.moveToNext()) {
			name = cursor.getString(0);
			System.out.println("Retrieved item name: "+name);
			quantity = cursor.getInt(1);
			System.out.println("Retrieved item quantity: "+quantity);
			price = cursor.getDouble(2);
			System.out.println("Retrieved item price: "+price);
			
			System.out.println("Creating shopping list item with retrieved data");
			slItem = new ShoppingListItem(name, quantity, price);
			
			System.out.println("Adding item to adapter");
			adapter.add(slItem);
		}
		cursor.close();
		
		return adapter;
	}
	
	private class shoppingListOnItemClickListener implements OnItemClickListener {
		
		public void onItemClick(AdapterView<?> list, View list_item, int position, long id) {
			MainActivity.this.selectedItem = position;
			MainActivity.this.invalidateOptionsMenu();
		}
	}

}
