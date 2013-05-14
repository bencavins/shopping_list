package edu.colorado.cavinsb.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private ListView listItems;
	//private TextView totalCostTextView;
	private int selectedItem = -1;
	//private ShoppingListDB db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		listItems = (ListView) findViewById(R.id.main_shopping_list);
		listItems.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> list, View list_item, int position, long id) {
				MainActivity.this.selectedItem = position;
				MainActivity.this.invalidateOptionsMenu();
			}
		});
		
		//db = new ShoppingListDB(this);
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

}
