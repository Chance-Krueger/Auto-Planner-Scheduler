package model;

import java.sql.Time;

public class ProjAssn extends Event {

//	Project/Assignment(Priority, time it'll take, due date/time, etc), ... extends: Event

	private Priority priority;
	private Time time;
	private Time due;

	public ProjAssn(String title, Priority priority, Time time, Time due) {
		super(title);
		this.priority = priority;
		this.time = time;
		this.due = due;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriorityFromString(String s) {
		Priority p = Priority.fromToString(s);
		if (p != null)
			this.priority = p;
	}

	public void setPriorityFromInteger(int i) {
		Priority p = Priority.fromInteger(i);
		if (p != null)
			this.priority = p;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Time getDue() {
		return due;
	}

	public void setDue(Time due) {
		this.due = due;
	}

	@Override
	public String toString() {
		return this.getTitle() + " is Due" + due.toString() + " With an estimated amount of time of "
				+ this.time.toString() + " and has a Pritority Level " + this.priority.toString().toLowerCase();
	}

}
