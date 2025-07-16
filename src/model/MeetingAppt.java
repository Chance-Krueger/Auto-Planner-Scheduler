package model;

import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The {@code MeetingAppt} class represents a scheduled meeting or appointment
 * in the calendar system. It extends the {@code Event} class and adds specific
 * fields for tracking the date and time of the meeting, including its start and
 * end times.
 *
 * <p>
 * Each {@code MeetingAppt} includes inherited properties such as title,
 * location, repeat settings, notes, and URL. The class supports cloning via a
 * copy constructor and provides getter and setter methods for each time-related
 * field.
 *
 * <p>
 * <b>Instance Variables:</b>
 * <ul>
 * <li>{@code date} — The {@code LocalDate} representing the day and general
 * time of the appointment</li>
 * <li>{@code startTime} — The {@code LocalTime} when the appointment
 * begins</li>
 * <li>{@code endTime} — The {@code LocalTime} when the appointment ends</li>
 * </ul>
 *
 * <p>
 * Note: This class assumes valid inputs and does not enforce ordering between
 * start and end times. Proper validation should be handled by the client code
 * or scheduling logic.
 *
 * @see Event
 * @see model.Calendar
 * @see ProjAssn
 * 
 * @author Chance Krueger
 */

public class MeetingAppt extends Event {

//	Meeting/Appointment(When, start/end times), ..., extends: Event

	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;

	/**
	 * Constructs a {@code MeetingAppt} with the specified title, date, start time,
	 * and end time.
	 *
	 * @param title     the title of the meeting
	 * @param date      the {@code LocalDate} representing the date of the meeting
	 * @param startTime the {@code LocalTime} when the meeting starts
	 * @param endTime   the {@code LocalTime} when the meeting ends
	 */
	public MeetingAppt(String title, LocalDate date, LocalTime startTime, LocalTime endTime) {
		super(title);
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Copy constructor. Creates a deep copy of the provided {@code MeetingAppt}.
	 *
	 * @param ma the {@code MeetingAppt} instance to copy
	 */
	public MeetingAppt(MeetingAppt ma) {
		super(ma.getTitle());
		super.setLocation(ma.getLocation());
		super.setRepeat(ma.getRepeat());
		super.setNotes(ma.getNotes());
		super.setUrl(ma.getUrl());

		this.date = ma.date;
		this.startTime = ma.startTime;
		this.endTime = ma.endTime;
	}

	/**
	 * Returns the full date and time of the meeting.
	 *
	 * @return the {@code LocalDate} of the meeting
	 */
	public LocalDate getDate() {
		return date;
	}

	// ESCAPING REFERENCE?
	/**
	 * Sets the full date and time of the meeting.
	 *
	 * @param date the {@code LocalDate} to assign
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Returns the start time of the meeting.
	 *
	 * @return the {@code LocalTime} when the meeting starts
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	// ESCAPING REFERENCE?
	/**
	 * Sets the start time of the meeting.
	 *
	 * @param startTime the {@code LocalTime} to assign as the start time
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Returns the end time of the meeting.
	 *
	 * @return the {@code LocalTime} when the meeting ends
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	// ESCAPING REFERENCE?
	/**
	 * Sets the end time of the meeting.
	 *
	 * @param endTime the {@code LocalTime} to assign as the end time
	 */
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
