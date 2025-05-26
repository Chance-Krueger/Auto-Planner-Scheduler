package model;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Calendar {

//	+ Calendar 
//		- HashMap<Date : ArrayList<Event>(Maybe not an ArrayList?)>
//		- Bunch of getters and setters, and talk with Event Class
//		- Need to also talk to BlockOffDates, Be an instance variable
//		- Settings settings; -> instance VAR

	private HashMap<LocalDate, ArrayList<Event>> calendar;
	private Settings settings;

	public Calendar() {
		this.settings = new Settings();
		this.calendar = new HashMap<LocalDate, ArrayList<Event>>();
	}

	public Calendar(Calendar c) {
		this.calendar = c.calendar;
		this.settings = c.settings;
	}

	public void addEventToCalendar(ProjAssn pj) {

		LocalDate ld = LocalDate.of(pj.getDue().getYear(), pj.getDue().getMonth(), pj.getDue().getDayOfMonth());
		ArrayList<Event> listOfEvents = new ArrayList<Event>();

		if (!this.calendar.containsKey(ld)) {
			listOfEvents.add(new ProjAssn(pj));
		} else {
			listOfEvents = this.calendar.get(ld);
			listOfEvents.add(new ProjAssn(pj));
		}
		this.calendar.put(ld, listOfEvents);
	}

	public void addEventToCalendar(MeetingAppt ma) {

		LocalDate ld = LocalDate.of(ma.getDate().getYear(), ma.getDate().getMonth(), ma.getDate().getDayOfMonth());
		ArrayList<Event> listOfEvents = new ArrayList<Event>();

		if (!this.calendar.containsKey(ld)) {
			listOfEvents.add(new MeetingAppt(ma));
			this.calendar.put(ld, listOfEvents);
		} else {
			listOfEvents = this.calendar.get(ld);
			listOfEvents.add(new MeetingAppt(ma));
		}
		this.calendar.put(ld, listOfEvents);
	}

	public boolean removeEvent(Event e) {

		LocalDate ld = getDateFromEvent(e);

		if (!checkLD(ld))
			return false;

		ArrayList<Event> adjusted = this.calendar.get(ld);
		adjusted.remove(e);
		this.calendar.put(ld, adjusted);
		return true;

	}

	public boolean adjustTitle(Event e, String newTitle) {

		LocalDate ld = getDateFromEvent(e);

		if (!checkLD(ld))
			return false;

		ArrayList<Event> copy = this.calendar.get(ld);

		for (Event copyE : copy) {
			if (copyE.equals(e)) {
				copyE.setTitle(newTitle);
				return true;
			}
		}
		// Couldn't find Event
		return false;
	}

	public boolean adjustLocation(Event e, String newLocation) {

		LocalDate ld = getDateFromEvent(e);

		if (!checkLD(ld))
			return false;

		ArrayList<Event> copy = this.calendar.get(ld);

		for (Event copyE : copy) {
			if (copyE.equals(e)) {
				copyE.setLocation(newLocation);
				return true;
			}
		}
		// Couldn't find Event
		return false;
	}

	public boolean adjustNotes(Event e, String newNotes) {

		LocalDate ld = getDateFromEvent(e);

		if (!checkLD(ld))
			return false;

		ArrayList<Event> copy = this.calendar.get(ld);

		for (Event copyE : copy) {
			if (copyE.equals(e)) {
				copyE.setNotes(newNotes);
				return true;
			}
		}
		// Couldn't find Event
		return false;
	}

	public boolean adjustURL(Event e, String newURL) {
		LocalDate ld = getDateFromEvent(e);

		if (!checkLD(ld))
			return false;

		ArrayList<Event> copy = this.calendar.get(ld);

		for (Event copyE : copy) {
			if (copyE.equals(e)) {
				copyE.setUrl(newURL);
				return true;
			}
		}
		// Couldn't find Event
		return false;
	}

	public boolean adjustRepeatDates(Event e, ArrayList<String> list) {

		LocalDate ld = getDateFromEvent(e);

		if (!checkLD(ld))
			return false;

		ArrayList<Event> copy = this.calendar.get(ld);

		for (Event copyE : copy) {
			if (copyE.equals(e)) {
				if (copyE.setRepeatDates(list))
					return true;
				return false;
			}
		}
		// Couldn't find Event
		return false;
	}

	public void adjustThemeColor(Color c) {
		this.settings.setThemeColor(c);
	}

	public void adjustAccentColor(Color c) {
		this.settings.setAccentColor(c);
	}

	public boolean adjustBlockOffDates(String day, ArrayList<String> times) {

		if (times.size() <= 0)
			return true;

		BlockOffDates bod = new BlockOffDates(this.settings.getBod());

		Repeat r = Repeat.checkRepeatFromString(day);
		if (r == null)
			return false;

		ArrayList<LocalTime> newList = new ArrayList<LocalTime>();

		// DO CHECK TO SEE IF TIMES IS LEGAL
		for (int index = 0; index < times.size(); index++) {
			if (index + 1 < times.size()) {
				LocalTime l1 = LocalTime.parse(times.get(index));
				LocalTime l2 = LocalTime.parse(times.get(index + 1));
				// THE TIMES ARE NOT IN ORDER
				if (l1.compareTo(l2) < 0) {
					return false;
				} else {
					// CONVERT EACH STRING OF TIMES TO LocalTime
					newList.add(l1);
				}
			} else {
				newList.add(LocalTime.parse(times.get(index)));
			}
		}
		// PUT NEW ARRAYLIST INTO HASHMAP
		bod.changeBlockedTimeOfDay(r, newList);
		return true;
	}

	private LocalDate getDateFromEvent(Event e) {

		LocalDate ld = null;

		if (e instanceof ProjAssn)
			ld = LocalDate.of(((ProjAssn) (e)).getDue().getYear(), ((ProjAssn) (e)).getDue().getMonth(),
					((ProjAssn) (e)).getDue().getDayOfMonth());
		else if (e instanceof MeetingAppt)
			ld = LocalDate.of(((MeetingAppt) (e)).getDate().getYear(), ((MeetingAppt) (e)).getDate().getMonth(),
					((MeetingAppt) (e)).getDate().getDayOfMonth());

		return ld;
	}

	private boolean checkLD(LocalDate ld) {
		// Event was an event and not something specific.
		if (ld == null)
			return false;

		if (!this.calendar.containsKey(ld))
			return false;
		return true;
	}
}
