package model;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The {@code ProjAssn} class represents a project or assignment event that
 * extends the general {@code Event} class. It includes time-bound attributes
 * such as a due date, estimated time to complete, and a priority level.
 *
 * <p>
 * This class is intended for tasks that require planning and time allocation,
 * such as homework assignments, work projects, or study sessions.
 *
 * <p>
 * <b>Instance Variables:</b>
 * <ul>
 * <li>{@code priority} — a {@code Priority} enum indicating the task's
 * importance</li>
 * <li>{@code time} — a {@code Duration} object representing the estimated time
 * required to complete the task</li>
 * <li>{@code due} — a {@code LocalDateTime} specifying the deadline for the
 * task</li>
 * </ul>
 *
 * <p>
 * This class also provides utility methods for subtracting time from the
 * estimate, and for setting the priority using either a {@code String} or an
 * {@code int}.
 * 
 * @see Event
 * @see model.Calendar
 * @see MeetingAppt
 *
 * @author Chance Krueger
 */

public class ProjAssn extends Event {

//	Project/Assignment(Priority, time it'll take, due date/time, etc), ... extends: Event

	private Priority priority;
	private Duration time;
	private LocalDateTime due;

	/**
	 * Constructs a {@code ProjAssn} event with the given title, priority, estimated
	 * time to complete, and due date.
	 *
	 * @param title    the title of the project or assignment
	 * @param priority the {@code Priority} level of the task
	 * @param time     the {@code Duration} of time estimated to complete the task
	 * @param due      the {@code LocalDateTime} when the task is due
	 */
	public ProjAssn(String title, Priority priority, Duration time, LocalDateTime due) {
		super(title);
		this.priority = priority;
		this.time = time;
		this.due = due;
	}

	/**
	 * Copy constructor. Creates a deep copy of the given {@code ProjAssn} object.
	 *
	 * @param pa the {@code ProjAssn} to copy
	 */
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

	/**
	 * Returns the priority level of the task.
	 *
	 * @return the {@code Priority} of the project or assignment
	 */
	public Priority getPriority() {
		return priority;
	}

	/**
	 * Sets the task's priority using a string representation. Accepts values like
	 * "one", "two", ..., "ten", case-insensitively.
	 *
	 * @param s the string representation of the priority
	 */
	public void setPriorityFromString(String s) {
		Priority p = Priority.fromToString(s);
		if (p != null)
			this.priority = p;
	}

	/**
	 * Sets the task's priority using an integer value (0–10).
	 *
	 * @param i the integer priority level
	 */
	public void setPriorityFromInteger(int i) {
		Priority p = Priority.fromInteger(i);
		if (p != null)
			this.priority = p;
	}

	/**
	 * Returns the estimated time required to complete the task.
	 *
	 * @return a {@code Duration} object representing the time estimate
	 */
	public Duration getTime() {
		return time;
	}

	// ESCAPING REFERENCE?
	/**
	 * Sets the estimated time required to complete the task.
	 *
	 * @param time a {@code Duration} object representing the time estimate
	 */
	public void setTime(Duration time) {
		this.time = time;
	}

	/**
	 * Returns the due date and time of the task.
	 *
	 * @return a {@code LocalDateTime} object representing the due date
	 */
	public LocalDateTime getDue() {
		return due;
	}

	// ESCAPING REFERENCE?
	/**
	 * Sets the due date and time of the task.
	 *
	 * @param due a {@code LocalDateTime} representing the new due date
	 */
	public void setDue(LocalDateTime due) {
		this.due = due;
	}

	/**
	 * Subtracts the given number of hours from the estimated time. If the
	 * subtraction would result in a negative time, the method fails.
	 *
	 * @param time the number of hours to subtract
	 * @return {@code true} if time was successfully subtracted; {@code false}
	 *         otherwise
	 */
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
