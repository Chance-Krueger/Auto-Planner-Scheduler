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

/**
 * The {@code Calendar} class represents a personal scheduling system that
 * allows users to manage and organize different types of events, including
 * {@code ProjAssn} (projects or assignments) and {@code MeetingAppt} (meetings
 * or appointments).
 * 
 * <p>
 * The class stores events in a {@code Map<LocalDate, ArrayList<Event>>},
 * enabling efficient lookup and grouping of events by their date. It supports
 * operations to add, remove, and modify event details such as title, location,
 * notes, URL, and repeat schedules.
 * 
 * <p>
 * The calendar is also configurable via a {@code Settings} object, which
 * contains customization options like theme and accent colors, and user-defined
 * blocked time ranges for repeating days.
 * 
 * <p>
 * Event objects are deep copied where appropriate to preserve data
 * encapsulation and prevent unintended external modifications. The class also
 * includes utility methods to interpret dates from event types and validate
 * date presence in the internal data structure.
 * 
 * <p>
 * <b>Instance Variables:</b>
 * <ul>
 * <li>{@code calendar} — A {@code Map} that maps {@code LocalDate} to a list of
 * scheduled {@code Event}s</li>
 * <li>{@code settings} — A {@code Settings} object that stores user
 * preferences, including visual themes and block-off times</li>
 * </ul>
 * 
 * This class is designed to be flexible, type-safe, and extensible for
 * supporting future event types or additional settings.
 *
 * @author Chance Krueger
 */
public class Calendar {

	private HashMap<LocalDate, ArrayList<Event>> calendar; // KEYS-> DATE(00/00/0000) : VALUES-> EVENTS
	private Settings settings;

	public Calendar() {
		this.settings = new Settings();
		this.calendar = new HashMap<LocalDate, ArrayList<Event>>();
	}

	/**
	 * Constructs a new {@code Calendar} object that is a copy of the specified
	 * {@code Calendar} instance. This constructor creates a new calendar with the
	 * same internal calendar and settings as the one provided.
	 * 
	 * @param c the {@code Calendar} instance to copy
	 * @throws NullPointerException if the specified {@code Calendar} is
	 *                              {@code null}
	 */
	public Calendar(Calendar c) {
		this.calendar = c.calendar;
		this.settings = c.settings;
	}

	/**
	 * Returns an unmodifiable view of the calendar map. The map associates each
	 * {@code LocalDate} with a list of {@code Event} objects scheduled for that
	 * date. Modifications to the returned map or its lists are not permitted and
	 * will result in an {@code UnsupportedOperationException}.
	 *
	 * @return an unmodifiable {@code Map} where keys are {@code LocalDate}
	 *         instances and values are {@code ArrayList<Event>}s for the
	 *         corresponding dates
	 */
	public Map<LocalDate, ArrayList<Event>> getCalendar() {
		return Collections.unmodifiableMap(this.calendar);
	}

	/**
	 * Returns a copy of the current {@code Settings} object associated with this
	 * {@code Calendar}. This ensures that the original settings remain encapsulated
	 * and cannot be modified externally.
	 * 
	 * @return a new {@code Settings} instance containing the same configuration as
	 *         the current one
	 */
	public Settings getSettings() {
		return new Settings(settings);
	}

	/**
	 * Adds a {@code ProjAssn} event to the calendar on its due date. If there are
	 * already events scheduled on the same {@code LocalDate}, the new event is
	 * appended to the existing list. Otherwise, a new entry is created for that
	 * date.
	 * 
	 * <p>
	 * A copy of the provided {@code ProjAssn} is stored to preserve encapsulation.
	 * 
	 * @param pj the {@code ProjAssn} (project or assignment) event to be added to
	 *           the calendar
	 * @throws NullPointerException if {@code pj} or its due date is {@code null}
	 */
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

	/**
	 * Adds a {@code MeetingAppt} event to the calendar on its scheduled date. If
	 * the calendar already contains events on that {@code LocalDate}, the new
	 * meeting is added to the existing list. Otherwise, a new entry is created for
	 * that date.
	 * 
	 * <p>
	 * A copy of the provided {@code MeetingAppt} is stored to maintain
	 * encapsulation.
	 * 
	 * @param ma the {@code MeetingAppt} (meeting appointment) to be added to the
	 *           calendar
	 * @throws NullPointerException if {@code ma} or its date is {@code null}
	 */
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

	/**
	 * Removes the specified {@code Event} from the calendar based on its date. If
	 * the event is found in the list associated with its {@code LocalDate}, it is
	 * removed and the method returns {@code true}. If the date does not exist in
	 * the calendar or the event is not present on that date, the method returns
	 * {@code false}.
	 * 
	 * @param e the {@code Event} to be removed from the calendar
	 * @return {@code true} if the event was successfully removed; {@code false}
	 *         otherwise
	 * @throws NullPointerException if {@code e} is {@code null} or has an invalid
	 *                              date
	 */
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

	/**
	 * Updates the title of the specified {@code Event} in the calendar. Searches
	 * for the event based on its date and equality comparison, and sets its title
	 * to the provided {@code newTitle} if found.
	 *
	 * @param e        the {@code Event} whose title is to be updated
	 * @param newTitle the new title to assign to the event
	 * @return {@code true} if the event was found and its title was successfully
	 *         updated; {@code false} if the event could not be found in the
	 *         calendar
	 * @throws NullPointerException if {@code e} or {@code newTitle} is {@code null}
	 */
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

	/**
	 * Updates the location of the specified {@code Event} in the calendar. Searches
	 * for the event based on its date and equality comparison, and sets its
	 * location to the provided {@code newLocation} if found.
	 *
	 * @param e           the {@code Event} whose location is to be updated
	 * @param newLocation the new location to assign to the event
	 * @return {@code true} if the event was found and its location was successfully
	 *         updated; {@code false} if the event could not be found in the
	 *         calendar
	 * @throws NullPointerException if {@code e} or {@code newLocation} is
	 *                              {@code null}
	 */
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

	/**
	 * Updates the notes associated with the specified {@code Event} in the
	 * calendar. Searches for the event based on its date and equality comparison,
	 * and sets its notes to the provided {@code newNotes} if found.
	 *
	 * @param e        the {@code Event} whose notes are to be updated
	 * @param newNotes the new notes to assign to the event
	 * @return {@code true} if the event was found and its notes were successfully
	 *         updated; {@code false} if the event could not be found in the
	 *         calendar
	 * @throws NullPointerException if {@code e} or {@code newNotes} is {@code null}
	 */
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

	/**
	 * Updates the URL associated with the specified {@code Event} in the calendar.
	 * Searches for the event by its date and using equality comparison, and sets
	 * its URL to the provided {@code newURL} if found.
	 *
	 * @param e      the {@code Event} whose URL is to be updated
	 * @param newURL the new URL to assign to the event
	 * @return {@code true} if the event was found and its URL was successfully
	 *         updated; {@code false} if the event could not be found in the
	 *         calendar
	 * @throws NullPointerException if {@code e} or {@code newURL} is {@code null}
	 */
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

	/**
	 * Updates the repeat dates for the specified {@code Event} in the calendar.
	 * Searches for the event by its date and equality, and if found, attempts to
	 * set the new list of repeat dates. The update is successful only if the event
	 * accepts the new repeat date configuration (e.g., format or content validation
	 * passes).
	 *
	 * @param e    the {@code Event} whose repeat dates are to be updated
	 * @param list an {@code ArrayList<String>} representing the new repeat dates
	 * @return {@code true} if the event was found and its repeat dates were
	 *         successfully updated; {@code false} if the event was not found or the
	 *         update was rejected by the event
	 * @throws NullPointerException if {@code e} or {@code list} is {@code null}
	 */
	public boolean adjustRepeatDates(Event e, String repeat) {

		LocalDate ld = getDateFromEvent(e);

		if (!checkLD(ld))
			return false;

		ArrayList<Event> copy = this.calendar.get(ld);

		for (Event copyE : copy) {
			if (copyE.equals(e)) {
				e.setRepeat(Repeat.checkRepeatFromString(repeat));
			}
		}
		// Couldn't find Event
		return false;
	}

	/**
	 * Updates the theme color used in the calendar settings. This changes the
	 * overall visual theme color as configured by the user.
	 *
	 * @param c the new {@code Color} to be used as the theme color
	 * @throws NullPointerException if {@code c} is {@code null}
	 */
	// PARAM BE A STRING?
	public void adjustThemeColor(Color c) {
		this.settings.setThemeColor(c);
	}

	/**
	 * Updates the accent color used in the calendar settings. The accent color
	 * typically affects highlights, buttons, or other interface details.
	 *
	 * @param c the new {@code Color} to be used as the accent color
	 * @throws NullPointerException if {@code c} is {@code null}
	 */
	// PARAM BE A STRING?
	public void adjustAccentColor(Color c) {
		this.settings.setAccentColor(c);
	}

	/**
	 * Adjusts the block-off times for a specific day of the week in the user's
	 * calendar settings. Each element in {@code times} represents a single time
	 * string (in {@code HH:mm} format) to be blocked off. The method validates the
	 * input, converts the strings to {@code LocalTime} objects, and updates the
	 * corresponding blocked times for the specified day.
	 *
	 * <p>
	 * Key validation includes:
	 * <ul>
	 * <li>{@code day} must be a valid day recognized by
	 * {@code Repeat.checkRepeatFromString()}.</li>
	 * <li>{@code times} must not be {@code null}; it can be empty, in which case
	 * the day remains unmodified.</li>
	 * <li>Each string in {@code times} must represent a valid time in {@code HH:mm}
	 * format.</li>
	 * </ul>
	 *
	 * @param day   the name of the day of the week (e.g., "Monday", "Friday"); must
	 *              be recognized by {@code Repeat.checkRepeatFromString()}
	 * @param times a {@code Set<String>} where each string represents a time to
	 *              block off
	 * @return {@code true} if the block-off times were successfully validated and
	 *         updated; {@code false} if the day is invalid
	 * @throws NullPointerException   if {@code day} or {@code times} is
	 *                                {@code null}
	 * @throws DateTimeParseException if any time string cannot be parsed as a
	 *                                {@code LocalTime}
	 */
	public boolean adjustBlockOffDates(String day, Set<String> times) {

		// List has nothing in it
		if (times.size() <= 0)
			return true;

		BlockOffDates bod = new BlockOffDates(this.settings.getBod());

		Repeat r = Repeat.checkRepeatFromString(day);
		// day is not a legal day of week
		if (r == null)
			return false;

		Set<LocalTime> set = new HashSet<LocalTime>();

		// DO CHECK TO SEE IF TIMES IS LEGAL
		for (String s : times) {

			LocalTime l1 = LocalTime.parse(s);

			set.add(l1);
		}

		// PUT NEW SET INTO HASHMAP
		bod.changeBlockedTimeOfDay(r, set);
		this.settings.setBod(bod);
		return true;
	}

	/**
	 * Extracts the {@code LocalDate} associated with the given {@code Event}.
	 * Supports {@code ProjAssn} and {@code MeetingAppt} event types by accessing
	 * their respective due or scheduled dates. If the event is of an unsupported
	 * type, the method returns {@code null}.
	 *
	 * @param e the {@code Event} from which to extract the date
	 * @return the {@code LocalDate} representing the event's date; or {@code null}
	 *         if the event type is unsupported
	 * @throws NullPointerException if {@code e} is {@code null}
	 */
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

	/**
	 * Checks whether the specified {@code LocalDate} is valid and exists as a key
	 * in the calendar. This method returns {@code false} if the date is
	 * {@code null} or if there are no events scheduled for that date.
	 *
	 * @param ld the {@code LocalDate} to check
	 * @return {@code true} if the date is non-null and present in the calendar;
	 *         {@code false} otherwise
	 */
	private boolean checkLD(LocalDate ld) {
		// Event was an event and not something specific.
		if (ld == null)
			return false;

		if (!this.calendar.containsKey(ld))
			return false;
		return true;
	}
}
