package edu.colorado.cavinsb.shoppinglist;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditItem extends Activity {

	private Integer itemId;
	private boolean saveData = true;
	private ShoppingListDB sldb = new ShoppingListDB(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		// Show the Up button in the action bar.
		setupActionBar();
		itemId = getIntent().getIntExtra("item_id", -1);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.menu_save:
			saveData = true;
			finish();
			return true;
		case R.id.menu_cancel:
			saveData = false;
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		// TODO onResume(): query db, set text of EditTexts
		saveData = true;
		
		if (itemId >= 0) {
			EditText name = (EditText) findViewById(R.id.sl_item_name);
			EditText quantity = (EditText) findViewById(R.id.sl_item_quantity);
			EditText price = (EditText) findViewById(R.id.sl_item_price);
			
			System.out.println("EditItem: Getting readable database");
			SQLiteDatabase db = sldb.getReadableDatabase();
			Cursor cursor = db.query("shopping_list_items", new String[] {"id","name","quantity","price"}, 
					"id = ?", new String[] {itemId.toString()}, null, null, null);
			
			while (cursor.moveToNext()) {
				System.out.println("EditItem: item id = "+cursor.getInt(0));
				System.out.println("EditItem: item name = "+cursor.getString(1));
				name.setText(cursor.getString(1));
				System.out.println("EditItem: item quantity = "+cursor.getInt(2));
				quantity.setText(cursor.getInt(2));
				System.out.println("EditItem: itemd price = "+cursor.getDouble(3));
				price.setText(((Double) cursor.getDouble(3)).toString()); // TODO clean up this line
			}
			cursor.close();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		// TODO onPause(): if data is to be saved, grab text from EditTexts and save to db
		if (saveData) {
			EditText name = (EditText) findViewById(R.id.sl_item_name);
			EditText quantity = (EditText) findViewById(R.id.sl_item_quantity);
			EditText price = (EditText) findViewById(R.id.sl_item_price);
			
			System.out.println("EditText: Fetching data for EditTexts");
			String nameStr = name.getText().toString();
			String quantityStr = quantity.getText().toString();
			String priceStr = price.getText().toString();
			
			Integer quantityInt = Integer.parseInt(quantityStr);
			Double priceDouble = Double.parseDouble(priceStr);
			
			// TODO data validation for entered fields
			
			System.out.println("Getting writeable database");
			SQLiteDatabase db = sldb.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("name", nameStr);
			values.put("quantity", quantityInt);
			values.put("price", priceDouble);
			// TODO iscrossed
			if (db.insertOrThrow("shopping_list_items", null, values) < 0) {
				System.out.println("EditItem: Error inserting item into database");
			}
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		sldb.close();
	}

}
