package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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


public class ToDoActivity extends FragmentActivity {
	private final int REQUEST_CODE = 20;
	private ListView lvItems;
	private ToDoItemsDataSource datasource; 
	private List<ToDoItem> todoItems;
	private EditText etNewItem;
	private ArrayAdapter<ToDoItem> aTodoItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        datasource = new ToDoItemsDataSource(this);
        datasource.open();
        lvItems = (ListView) findViewById(R.id.lvItems);
      //  etNewItem =(EditText) findViewById(R.id.editItem);
       // populateArrayItems();
        //readItems();
        todoItems = datasource.getAllToDos();
        aTodoItems = new ArrayAdapter<ToDoItem>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(aTodoItems);
        setupListViewListener();
    }


    private void setupListViewListener() {
    	// removes the element on long click hold
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				todoItems.remove(position);
				aTodoItems.notifyDataSetChanged();
				writeItems();
				return true;
			}
			
		});
		
		// Edits the item
    	lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
//				i.putExtra("position", position);
//				i.putExtra("itemText", todoItems.get(position));
//				startActivityForResult(i, REQUEST_CODE);
				final ToDoItem todoItem = todoItems.get(position);
				//long id = todoItem.getId();
				String text = todoItem.getText();
				Date dueDate = todoItem.getDueDate();
				Integer priority = todoItem.getPriority();
		    	FragmentManager fm = getSupportFragmentManager();
		        @SuppressWarnings("deprecation")
				EditItemDialog editItemDialog = EditItemDialog.newInstance("Edit ToDo Item", text, dueDate.getYear(), dueDate.getMonth(), dueDate.getDay(), priority);
		        editItemDialog.show(fm, "fragment_edit_item");
		        editItemDialog.setDialogListener(new EditItemDialog.EditItemDialogListener() {
					
					@Override
					public void onDialogDone( String text, Integer year, Integer month,
							Integer day, Integer priority) {
						// TODO Auto-generated method stub
						Date date = new Date(year, month, day);
						datasource.updateToDoItem(todoItem.getId(), text, date, priority);
						todoItem.setText(text);
						todoItem.setDueDate(date);
						todoItem.setPriority(priority);
						aTodoItems.notifyDataSetChanged();
								//new ToDoItem(text,date, priority);
						//aTodoItems.add(todo.toString());
						//writeItems();
					}
				});				
			}
		});

		
	}


/*	private void populateArrayItems() {
		todoItems = new ArrayList<String>();
		todoItems.add("Item 1");
		todoItems.add("Item 2");
		todoItems.add("Item 3");
	}*/

//	private void readItems(){
//		File filesDir = getFilesDir();
//		File todoFile = new File(filesDir, "todo.txt");
//		try{
//			ArrayList<String> fileItems = new ArrayList<String>(FileUtils.readLines(todoFile));
//			
//		}catch(IOException e){
//			todoItems = new ArrayList<String>();
//		}
//	}

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
    	  
//    	String itemText = etNewItem.getText().toString();
//    	aTodoItems.add(itemText);
//    	etNewItem.setText("");
//    	writeItems();
    	
    	
    	FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editItemDialog = EditItemDialog.newInstance("Add New ToDo Item", null, null, null, null, null);
        editItemDialog.show(fm, "fragment_edit_item");
        editItemDialog.setDialogListener(new EditItemDialog.EditItemDialogListener() {
			


			@Override
			public void onDialogDone( String text, Integer year,
					Integer month, Integer day, Integer priority) {
				// TODO Auto-generated method stub
				ToDoItem todo = new ToDoItem();
				todo.setText(text);
				@SuppressWarnings("deprecation")
				Date date = new Date(year, month, day);
				todo.setDueDate(date);
				todo.setPriority(priority);
				aTodoItems.add(todo);
				aTodoItems.notifyDataSetChanged();
			}
		});
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//    	 // REQUEST_CODE is defined above
//    	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//    	     String text = data.getStringExtra("itemText");
//    	     Integer position = data.getIntExtra("position", 0);
//    	     todoItems.set(position, text);
//    	     aTodoItems.notifyDataSetChanged();
//    	     writeItems();
//    	  }
//    }
}
