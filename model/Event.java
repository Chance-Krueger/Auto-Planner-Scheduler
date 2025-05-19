package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Event {

//	Project/Assignment(Priority, time it'll take, due date, due time(optional?), etc), ... extends: Event
//	Meeting/Appointment(When, start/end times), ..., extends: Event
//	
//	Event (Title: String, Location(Optional): String, repeat(Optional): Repeat, 
//		   Notes(Optional): String, URL(Optional): String)

	private String title;
	private String location;
	private HashMap<Repeat, ArrayList<Repeat>> repeatDates;
	private String notes;
	private String url;

	public Event(String title) {
		this.title = title;
		this.location = "";
		this.repeatDates = new HashMap<Repeat, ArrayList<Repeat>>();
		this.notes = "";
		this.url = "";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Map<Repeat, ArrayList<Repeat>> getRepeatDates() {
		return Collections.unmodifiableMap(this.repeatDates);
	}

	/*
	 * 
	 * setRepeatDates(ArrayList<String> repeatRequirements) takes an ArrayList of
	 * Strings. It will check to see if the first index in the List is an Actual
	 * repeat type instead of a date or a word that is not able to be in the ENUM.
	 * If it is then it will go through the rest of the ArrayList to see if its
	 * valid (ERROR CHECKING). If it is, it'll add the Date with the specific repeat
	 * type into the HashMap, where the repeat type is the keys and the specific
	 * date is the value.
	 * 
	 * @returns a boolean. Either true if there was no errors seen, false if there
	 * was an error seen. Errors seen could either be from 1. the first index in the
	 * ArrayList is not a specific type of repeat or 2. everything from index 1: is
	 * not in the Days of the Week.
	 */
	public boolean setRepeatDates(ArrayList<String> repeatRequirements) {

		Repeat first = Repeat.checkRepeatFromString(repeatRequirements.get(0));

		HashMap<Repeat, ArrayList<Repeat>> c = new HashMap<Repeat, ArrayList<Repeat>>();
		ArrayList<Repeat> cl = new ArrayList<Repeat>();

		boolean specType = ((first == Repeat.CUSTOM) || (first == Repeat.EVERY2WEEKS) || (first == Repeat.EVERYDAY)
				|| (first == Repeat.EVERYMONTH) || (first == Repeat.EVERYWEEK) || (first == Repeat.EVERYYEAR));

		if (first == Repeat.EVERYYEAR || first == Repeat.EVERYMONTH) {
			if (repeatRequirements.size() != 1) {
				return false;
			}
			cl.add(Repeat.NONE);
			c.put(first, cl);
			this.repeatDates = c;
			return true;
		}

		if (!specType)
			return false;

		for (int i = 1; i < repeatRequirements.size(); i++) {
			Repeat r = Repeat.checkRepeatFromString(repeatRequirements.get(i));

			boolean isDay = ((r == Repeat.MON) || (r == Repeat.TUE) || (r == Repeat.WED) || (r == Repeat.THR)
					|| (r == Repeat.FRI) || (r == Repeat.SAT) || (r == Repeat.SUN));

			if (r == null)
				return false;
			else if (isDay)
				cl.add(r);
			else
				return false;
		}
		c.put(first, cl);
		this.repeatDates = c;
		return true;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUrl() {
		return url;
	}

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

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
