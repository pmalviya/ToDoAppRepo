package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class ToDoActivity extends Activity {
	private final int REQUEST_CODE = 20;
	private ListView lvItems;
	private ArrayList<String> todoItems;
	private EditText etNewItem;
	private ArrayAdapter<String> aTodoItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem =(EditText) findViewById(R.id.editItem);
       // populateArrayItems();
        readItems();
        aTodoItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(aTodoItems);
        setupListViewListener();
    }


    private void setupListViewListener() {
/*		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				todoItems.remove(position);
				aTodoItems.notifyDataSetChanged();
				writeItems();
				return true;
			}
			
		});*/
    	lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
				i.putExtra("position", position);
				i.putExtra("itemText", todoItems.get(position));
				startActivityForResult(i, REQUEST_CODE);
				
			}
		});

		
	}


	private void populateArrayItems() {
		todoItems = new ArrayList<String>();
		todoItems.add("Item 1");
		todoItems.add("Item 2");
		todoItems.add("Item 3");
	}

	private void readItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try{
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		}catch(IOException e){
			todoItems = new ArrayList<String>();
		}
	}

	private void writeItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try{
			FileUtils.writeLines(todoFile, todoItems);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
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
    
    public void onAddItem(View v){
    	  
    	String itemText = etNewItem.getText().toString();
    	aTodoItems.add(itemText);
    	etNewItem.setText("");
    	writeItems();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	 // REQUEST_CODE is defined above
    	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    	     String text = data.getStringExtra("itemText");
    	     Integer position = data.getIntExtra("position", 0);
    	     todoItems.set(position, text);
    	     aTodoItems.notifyDataSetChanged();
    	     writeItems();
    	  }
    }
}
