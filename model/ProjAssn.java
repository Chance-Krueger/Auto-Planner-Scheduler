package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class ProjAssn extends Event {

//	Project/Assignment(Priority, time it'll take, due date/time, etc), ... extends: Event

	private Priority priority;
	private Duration time;
	private LocalDateTime due;

	public ProjAssn(String title, Priority priority, Duration time, LocalDateTime due) {
		super(title);
		this.priority = priority;
		this.time = time;
		this.due = due;
	}

	public ProjAssn(ProjAssn pa) {
		super(pa.getTitle());
		super.setLocation(pa.getLocation());
		super.setRepeatDates(pa.getRepeatDates());
		super.setNotes(pa.getNotes());
		super.setUrl(pa.getUrl());

		this.priority = pa.priority;
		this.time = pa.time;
		this.due = pa.due;
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

	public Duration getTime() {
		return time;
	}

	// ESCAPING REFERENCE?
	public void setTime(Duration time) {
		this.time = time;
	}

	public LocalDateTime getDue() {
		return due;
	}

	// ESCAPING REFERENCE?
	public void setDue(LocalDateTime due) {
		this.due = due;
	}

	// IF time <= duration -> adjust and return true, else return false
	public boolean subtractTime(long time) {
		Duration d = Duration.ofHours(time);
		if (time <= getTime().toHours()) {
			this.time = this.time.minus(d);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return this.getTitle() + " is Due " + due.getMonth() + "/" + due.getDayOfMonth() + "/" + due.getYear() + " at: "
				+ due.getHour() + ":" + due.getMinute() + ". With an estimated amount of time of " + this.time.toHours()
				+ " hours and has a Pritority Level " + this.priority.toString().toLowerCase() + ".";
	}

}
