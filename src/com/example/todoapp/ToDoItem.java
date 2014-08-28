package com.example.todoapp;

import java.util.Date;

public class ToDoItem{

	private String text;
	private Date dueDate;
	private Integer priority;
	private long id;

	public long getId() {
		return id;
	}
	@Override
	public String toString(){
		return this.text +"\n"+ this.dueDate.toString() +"\t" + this.priority;
	}

	public void setId(long id) {
		this.id = id;
	}


//	@SuppressWarnings("deprecation")
//	public ToDoItem(String text, Integer year, Integer month, Integer day, Integer priority){
//		this.text = text;
//		this.dueDate = new Date(year, month, day);
//		this.priority = priority;
//	}
	public String getText(){
		return text;
	}
	public void setText(String text){
		this.text = text;
	}
	public Date getDueDate(){
		return dueDate;
	}
	public void setDueDate(Date date){
		this.dueDate = date;
	}
	public Integer getPriority(){
		return priority;
	}
	public void setPriority(Integer priority){
		this.priority = priority;
	}
}