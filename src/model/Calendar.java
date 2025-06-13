package model;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Calendar {

	private HashMap<LocalDate, ArrayList<Event>> calendar; // KEYS-> DATE(00/00/0000) : VALUES-> EVENTS
	private Settings settings;

	public Calendar() {
		this.settings = new Settings();
		this.calendar = new HashMap<LocalDate, ArrayList<Event>>();
	}

	public Calendar(Calendar c) {
		this.calendar = c.calendar;
		this.settings = c.settings;
	}

	public Map<LocalDate, ArrayList<Event>> getCalendar() {
		return Collections.unmodifiableMap(this.calendar);
	}

	public Settings getSettings() {
		return new Settings(settings);
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

		if (adjusted.remove(e)) {
			this.calendar.put(ld, adjusted);
			return true;
		}

		return false;

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
				if (e.setRepeatDates(list))
					return true;
				return false;
			}
		}
		// Couldn't find Event
		return false;
	}

	// PARAM BE A STRING?
	public void adjustThemeColor(Color c) {
		this.settings.setThemeColor(c);
	}

	// PARAM BE A STRING?
	public void adjustAccentColor(Color c) {
		this.settings.setAccentColor(c);
	}

	public boolean adjustBlockOffDates(String day, ArrayList<Set<String>> times) {

		// List has nothing in it
		if (times.size() <= 0)
			return true;

		// List is not Even and can't have start/end times
		if (times.size() % 2 != 0)
			return false;

		BlockOffDates bod = new BlockOffDates(this.settings.getBod());

		Repeat r = Repeat.checkRepeatFromString(day);
		// day is not a legal day of week
		if (r == null)
			return false;

		ArrayList<Set<LocalTime>> newList = new ArrayList<Set<LocalTime>>();

		// DO CHECK TO SEE IF TIMES IS LEGAL
		for (Set<String> s : times) {

			// Current Set is not legal (has to be 2, start/end times)
			if (s.size() != 2)
				return false;

			String[] timesArray = null;
			try {
				timesArray = s.toArray(new String[0]);
			} catch (Exception e) {
				// Was not a String (Should never hit since the parameter is String)
				return false;
			}

			LocalTime l1 = LocalTime.parse(timesArray[0]);
			LocalTime l2 = LocalTime.parse(timesArray[1]);

			// THE TIMES ARE NOT IN ORDER
			if (l1.isAfter(l2)) {
				l1 = LocalTime.parse(timesArray[1]);
				l2 = LocalTime.parse(timesArray[0]);
			}

			Set<LocalTime> set = new HashSet<LocalTime>();
			set.add(l1);
			set.add(l2);
			// CONVERT EACH STRING OF TIMES TO LocalTime
			newList.add(set);
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
		else
			// Is an event and not either a ProjAssn or MeetingAppt.
			return null;

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
