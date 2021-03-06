package com.example.todoapp;

import java.util.Calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ToDoSQLLiteHelper extends SQLiteOpenHelper{
	
	public static final String TABLE_TODOITEMS = "todos";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TEXT = "text";
	public static final String COLUMN_DATE =  "dueDate";
	public static final String COLUMN_PRIORITY = "priority";
	
	private static final String DATABASE_NAME = "todos.db";
	private static final int DATABASE_VERSION = 1;
	 
	 // Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
	      + TABLE_TODOITEMS + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_TEXT
	      + " text, " + COLUMN_DATE 
	      + " text, " + COLUMN_PRIORITY
	      + " integer );";
	
	
	public ToDoSQLLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	    Log.w(ToDoSQLLiteHelper.class.getName(),
	            "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOITEMS);
	        onCreate(db);
	}
	
}