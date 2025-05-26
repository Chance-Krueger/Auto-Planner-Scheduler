package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class MeetingAppt extends Event {

//	Meeting/Appointment(When, start/end times), ..., extends: Event

	private LocalDateTime date;
	private LocalTime startTime;
	private LocalTime endTime;

	public MeetingAppt(String title, LocalDateTime date, LocalTime startTime, LocalTime endTime) {
		super(title);
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

//	private String location;
//	private HashMap<Repeat, ArrayList<Repeat>> repeatDates;
//	private String notes;
//	private String url;

	public MeetingAppt(MeetingAppt ma) {
		super(ma.getTitle());
		super.setLocation(ma.getLocation());
		super.setRepeatDates(ma.getRepeatDates());
		super.setNotes(ma.getNotes());
		super.setUrl(ma.getUrl());

		this.date = ma.date;
		this.startTime = ma.startTime;
		this.endTime = ma.endTime;
	}

	public LocalDateTime getDate() {
		return date;
	}

	// ESCAPING REFERENCE?
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	// ESCAPING REFERENCE?
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	// ESCAPING REFERENCE?
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return this.getTitle() + " is on " + this.date.getMonth() + "/" + this.date.getDayOfMonth() + "/"
				+ this.date.getYear() + " and starts from " + this.startTime.getHour() + ":"
				+ this.startTime.getMinute() + " and will wend at " + this.endTime.getHour() + ":"
				+ this.endTime.getMinute() + ".";
	}

}
