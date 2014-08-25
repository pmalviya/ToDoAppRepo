package com.example.todoapp;

import java.sql.Date;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
// ...
import android.widget.EditText;

public class EditItemDialog extends DialogFragment {

	private EditText mEditText;
	private DatePicker mDueDate;
	private EditText mPriority;
	private Button mSave;
	
	public EditItemDialog() {
		// Empty constructor required for DialogFragment
	}
	
	public interface EditItemDialogListener {
	        void onDialogDone(String text, Integer year, Integer month, Integer day, Integer priority);
	}
	
	public EditItemDialogListener dListener;
	
	public void setDialogListener(EditItemDialogListener listener) {
		dListener = listener;
	}
	
	public static EditItemDialog newInstance(String title, String text, Integer year, Integer month, Integer day, Integer priority) {
		EditItemDialog frag = new EditItemDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		// Set the item attributes

		// If it is a new item then set due date as today's date 
		if(text == null){
			text = "";
	        final Calendar c = Calendar.getInstance();
	        year = c.get(Calendar.YEAR);
	        month = c.get(Calendar.MONTH);
	        day = c.get(Calendar.DAY_OF_MONTH);
	        priority = new Integer(1);
		}
		args.putString("text", text);
		args.putInt("year", year.intValue());
		args.putInt("day", day.intValue());
		args.putString("priority", priority.toString());
		frag.setArguments(args);
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit_item, container);
		
		
		String title = getArguments().getString("title", "Enter Item");
		getDialog().setTitle(title);
		
		// Set ToDo Item Name
		mEditText = (EditText) view.findViewById(R.id.updateItem);
		mEditText.setText(getArguments().getString("text"));
		
		// Set Due Date
		mDueDate = (DatePicker) view.findViewById(R.id.datePicker1);
		mDueDate.updateDate(getArguments().getInt("year"), getArguments().getInt("month"), getArguments().getInt("day"));
		
		// Set Priority
		mPriority = (EditText) view.findViewById(R.id.priority);
		mPriority.setText(getArguments().getString("priority", "1"));
		
		//Set listener for save button
		mSave = (Button) view.findViewById(R.id.saveButton);
		mSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text = mEditText.getText().toString();
				Integer year = mDueDate.getYear();
				Integer month = mDueDate.getMonth();
				Integer day = mDueDate.getDayOfMonth();
				Integer priority = new Integer(mPriority.getText().toString());
				
				dListener.onDialogDone(text, year, month, day, priority);
				getDialog().dismiss();
			}
		});
		// Show soft keyboard automatically
		mEditText.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return view;
	}


}