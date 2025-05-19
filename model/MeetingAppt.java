package model;

import java.sql.Time;

public class MeetingAppt extends Event {

//	Meeting/Appointment(When, start/end times), ..., extends: Event

	private Time date;
	private Time startTime;
	private Time endTime;

	public MeetingAppt(String title, Time date, Time startTime, Time endTime) {
		super(title);
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Time getDate() {
		return date;
	}

	public void setDate(Time date) {
		this.date = date;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {

		return this.getTitle() + " is on " + this.date.toString() + " and starts from " + this.startTime.toString()
				+ " and will wend at " + this.endTime.toString();
	}

}
