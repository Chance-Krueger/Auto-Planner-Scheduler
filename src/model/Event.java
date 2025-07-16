package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Event} class serves as an abstract representation of a general
 * calendar event. It provides shared fields and behavior for more specific
 * event types such as {@code ProjAssn} (project/assignment) and
 * {@code MeetingAppt} (meeting/appointment), which extend this class.
 * 
 * <p>
 * Each event contains the following optional and required attributes:
 * <ul>
 * <li><b>title</b> — a required {@code String} that names the event</li>
 * <li><b>location</b> — an optional {@code String} describing the event's
 * location</li>
 * <li><b>repeatDates</b> — a {@code Map<Repeat, ArrayList<Repeat>>} that stores
 * the recurrence type and specific days</li>
 * <li><b>notes</b> — an optional {@code String} for user-entered notes</li>
 * <li><b>url</b> — an optional {@code String} containing a link related to the
 * event</li>
 * </ul>
 *
 * <p>
 * The class provides methods to update each attribute, as well as a utility
 * method {@code setRepeatDates(List<String>)} that parses user-supplied repeat
 * patterns and validates them into structured {@code Repeat} enums. This method
 * includes internal error-checking for input formatting and value correctness.
 * 
 * <p>
 * Equality between events is defined based on all fields being equal, including
 * the deep content of the {@code repeatDates} map.
 *
 * <p>
 * Note: While this class is not explicitly abstract, it is designed to be
 * extended by more specific types of events with date/time information.
 *
 * @see Repeat
 * @see ProjAssn
 * @see MeetingAppt
 * 
 * @author Chance Krueger
 */

public class Event {

//	Project/Assignment(Priority, time it'll take, due date, due time(optional?), etc), ... extends: Event
//	Meeting/Appointment(When, start/end times), ..., extends: Event
//	
//	Event (Title: String, Location(Optional): String, repeat(Optional): Repeat, 
//		   Notes(Optional): String, URL(Optional): String)

	private String title;
	private String location;
	private Repeat repeat;
	private String notes;
	private String url;

	public Event(String title) {
		this.title = title;
		this.location = "";
		this.repeat = Repeat.NONE;
		this.notes = "";
		this.url = "";
	}

	/**
	 * Constructs a new {@code Event} as a copy of the specified {@code Event}.
	 * Copies all field values including title, location, notes, URL, and repeat
	 * date mappings.
	 * 
	 * <p>
	 * <b>Note:</b> This constructor performs a shallow copy of the
	 * {@code repeatDates} map. If the caller or other objects mutate the original
	 * {@code repeatDates} map or its internal lists, those changes will reflect in
	 * the copy. For full immutability, a deep copy is recommended.
	 *
	 * @param e the {@code Event} to copy
	 * @throws NullPointerException if {@code e} is {@code null}
	 */
	public Event(Event e) {
		this.title = e.title;
		this.location = e.location;
		this.repeat = e.repeat;
		this.notes = e.notes;
		this.url = e.url;
	}

	/**
	 * Gets the event title.
	 *
	 * @return the title of the event
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the event title.
	 *
	 * @param title the new title of the event
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the event location.
	 *
	 * @return the location of the event
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the event location.
	 *
	 * @param location the new location of the event
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the repeat dates map.
	 *
	 * @return an unmodifiable map of repeat types and their values
	 */
	public Repeat getRepeat() {
		return this.repeat;
	}

	// ESCAPING REFERENCE?
	/**
	 * Sets the repeat dates map.
	 *
	 * @param repeatDates2 the map of repeat types and values to set
	 */
	public void setRepeat(Repeat repeatDates2) {
		this.repeat = repeatDates2;
	}

	/**
	 * Sets the repeat schedule for this event based on a list of {@code String}
	 * inputs. The first element in the list must represent a valid repeat pattern
	 * type from the {@code Repeat} enum (e.g., {@code EVERYWEEK}, {@code CUSTOM},
	 * etc.). The remaining elements, if applicable, must represent valid days of
	 * the week (e.g., {@code MON}, {@code TUE}).
	 * 
	 * <p>
	 * The method performs input validation to ensure the repeat type and its
	 * associated parameters are recognized. If the repeat type is
	 * {@code EVERYMONTH} or {@code EVERYYEAR}, no additional days are expected, and
	 * the method succeeds if the list contains exactly one element.
	 * 
	 * <p>
	 * If all input values are valid, the internal {@code repeatDates} map is
	 * updated with the new configuration.
	 * 
	 * @param repeatRequirements an {@code ArrayList<String>} where the first
	 *                           element specifies the repeat type and the remaining
	 *                           elements specify repeat days (if applicable)
	 * @return {@code true} if the input is valid and the repeat schedule was
	 *         successfully set; {@code false} if the input format is invalid or
	 *         contains unrecognized values
	 * @throws NullPointerException if {@code repeatRequirements} is {@code null} or
	 *                              contains {@code null} entries
	 * @see Repeat
	 */
//	public boolean setRepeatDates(ArrayList<String> repeatRequirements) {
//
//		Repeat first = Repeat.checkRepeatFromString(repeatRequirements.get(0));
//
//		HashMap<Repeat, ArrayList<Repeat>> c = new HashMap<Repeat, ArrayList<Repeat>>();
//		ArrayList<Repeat> cl = new ArrayList<Repeat>();
//
//		boolean specType = ((first == Repeat.CUSTOM) || (first == Repeat.EVERY2WEEKS) || (first == Repeat.EVERYDAY)
//				|| (first == Repeat.EVERYMONTH) || (first == Repeat.EVERYWEEK) || (first == Repeat.EVERYYEAR));
//
//		if (first == Repeat.EVERYYEAR || first == Repeat.EVERYMONTH) {
//			if (repeatRequirements.size() != 1) {
//				return false;
//			}
//			cl.add(Repeat.NONE);
//			c.put(first, cl);
//			this.repeatDates = c;
//			return true;
//		}
//
//		if (!specType)
//			return false;
//
//		for (int i = 1; i < repeatRequirements.size(); i++) {
//			Repeat r = Repeat.checkRepeatFromString(repeatRequirements.get(i));
//
//			boolean isDay = ((r == Repeat.MON) || (r == Repeat.TUE) || (r == Repeat.WED) || (r == Repeat.THR)
//					|| (r == Repeat.FRI) || (r == Repeat.SAT) || (r == Repeat.SUN));
//
//			if (r == null)
//				return false;
//			else if (isDay)
//				cl.add(r);
//			else
//				return false;
//		}
//		c.put(first, cl);
//		this.repeatDates = c;
//		return true;
//	}

	/**
	 * Gets the notes for the event.
	 *
	 * @return the notes associated with the event
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes for the event.
	 *
	 * @param notes the new notes for the event
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the URL for the event.
	 *
	 * @return the URL associated with the event
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the URL for the event.
	 *
	 * @param url the new URL for the event
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {

		String string = this.title;

		if (!location.equals(""))
			string += " at " + location;

		return string;
	}

	@Override
	public boolean equals(Object e) {
		return ((Event) (e)).title.equals(this.title) && ((Event) (e)).location.equals(this.location)
				&& ((Event) (e)).notes.equals(this.notes) && ((Event) (e)).url.equals(this.url)
				&& ((Event) (e)).repeat.equals(this.repeat);
	}
}
