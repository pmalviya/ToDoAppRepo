package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	 EditText editItem;
	 private final int REQUEST_CODE = 20;
	 Integer pos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		String itemText = getIntent().getStringExtra("itemText");
		pos = getIntent().getIntExtra("position", 0);
		editItem =(EditText) findViewById(R.id.updateItem);
		editItem.setText(itemText);
		editItem.setSelection(itemText.length());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//public void onSubmit(View v) {
		//  // closes the activity and returns to first screen
		  //this.finish(); 
		//}
	public void onSaveItem(View v){
		
		 Intent i = new Intent();
		  i.putExtra("position", pos); // pass arbitrary data to launched activity
		  i.putExtra("itemText", editItem.getText().toString());
		  setResult(RESULT_OK, i);
		  finish();
	}
}
