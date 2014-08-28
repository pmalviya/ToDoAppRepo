package com.example.todoapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ToDoItemsDataSource{
	private SQLiteDatabase database;
	private ToDoSQLLiteHelper dbHelper;
	private String[] allColumns = { ToDoSQLLiteHelper.COLUMN_ID,
			ToDoSQLLiteHelper.COLUMN_DATE,
			ToDoSQLLiteHelper.COLUMN_PRIORITY,
			ToDoSQLLiteHelper.COLUMN_TEXT};

	public ToDoItemsDataSource(Context context) {
		dbHelper = new ToDoSQLLiteHelper(context);
		
	}
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		
	}

	public void close() {
		dbHelper.close();
	}
	
	public ToDoItem createToDoItem(String text, Date dueDate, Integer priority){
		ContentValues values = new ContentValues();
		values.put(ToDoSQLLiteHelper.COLUMN_TEXT, text);
		values.put(ToDoSQLLiteHelper.COLUMN_DATE, dueDate.toString());
		values.put(ToDoSQLLiteHelper.COLUMN_PRIORITY, priority);
		long insertId = database.insert(ToDoSQLLiteHelper.TABLE_TODOITEMS, null,
		        values);
		    Cursor cursor = database.query(ToDoSQLLiteHelper.TABLE_TODOITEMS,
		        allColumns, ToDoSQLLiteHelper.COLUMN_ID + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    ToDoItem newToDoItem = cursorToToDoItem(cursor);
		    newToDoItem.setText(text);
		    newToDoItem.setDueDate(dueDate);
		    newToDoItem.setPriority(priority);
		    cursor.close();
		    return newToDoItem;
	}
	private ToDoItem cursorToToDoItem(Cursor cursor) {
		@SuppressWarnings("deprecation")
		ToDoItem todoItem = new ToDoItem();
		todoItem.setId(cursor.getLong(0));
		//todoItem.setText(cursor.getString(1));
	    return todoItem;
	  }
	
	  public void deleteToDoItem(ToDoItem todoItem) {
		    long id = todoItem.getId();
		    System.out.println("Comment deleted with id: " + id);
		    database.delete(ToDoSQLLiteHelper.TABLE_TODOITEMS, ToDoSQLLiteHelper.COLUMN_ID + " = " + id, null);
		  }
	  public void updateToDoItem(long id, String text, Date dueDate, Integer priority) {
		    ContentValues values = new ContentValues();
			values.put(ToDoSQLLiteHelper.COLUMN_TEXT, text);
			values.put(ToDoSQLLiteHelper.COLUMN_DATE, dueDate.toString());
			values.put(ToDoSQLLiteHelper.COLUMN_PRIORITY, priority);
		    database.update(ToDoSQLLiteHelper.TABLE_TODOITEMS, values, ToDoSQLLiteHelper.COLUMN_ID + " = " + id, null);
		  }
		  public List<ToDoItem> getAllToDos() {
		    List<ToDoItem> toDoItems = new ArrayList<ToDoItem>();
		    dbHelper.onUpgrade(database, 1, 2);

		    Cursor cursor = database.query(ToDoSQLLiteHelper.TABLE_TODOITEMS, allColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	ToDoItem toDoItem = cursorToToDoItem(cursor);
		    	toDoItems.add(toDoItem);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return toDoItems;
		  }
}