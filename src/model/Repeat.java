package model;

/**
 * The {@code Repeat} enum represents various repeat patterns for calendar
 * events. It includes both standard recurrence types (e.g., {@code EVERYDAY},
 * {@code EVERYMONTH}) and day-specific options (e.g., {@code MON}, {@code TUE},
 * etc.), as well as customization options like {@code CUSTOM} or {@code NONE}.
 *
 * <p>
 * Each enum constant is associated with:
 * <ul>
 * <li>{@code DAY} — an integer representing the day of the week (0 = Monday, 6
 * = Sunday)</li>
 * <li>{@code TOSTRING} — a display-friendly string label</li>
 * </ul>
 *
 * <p>
 * Utility methods are provided to convert from strings or integers to
 * corresponding {@code Repeat} values.
 * 
 * @see Settings
 * @see BlockOffDates
 * @see java.time.DayOfWeek
 * @see java.util.EnumSet
 * 
 * @author Chance Krueger
 */
public enum Repeat {

	MON(1, "Monday"), TUE(2, "Tuesday"), WED(3, "Wednesday"), THR(4, "Thursday"), FRI(5, "Friday"), SAT(6, "Saturday"),
	SUN(0, "Sunday"),

	NONE(-1, "None"), EVERYDAY(-1, "Everyday"), EVERYWEEK(-1, "Every Week"), EVERY2WEEKS(-1, "Every 2 Weeks"),
	EVERYMONTH(-1, "Every Month"), EVERYYEAR(-1, "Every Year"), CUSTOM(-1, "Custom");

	/**
	 * Integer representation of the day (0–6 for days of week, -1 for non-day
	 * values)
	 */
	public final int DAY;

	/** Readable string representation of the repeat pattern */
	public final String TOSTRING;

	/**
	 * Constructs a {@code Repeat} constant with the given day index and label.
	 *
	 * @param i      the integer value representing the day of the week (or -1 if
	 *               not applicable)
	 * @param string the display-friendly name of the repeat pattern
	 */
	Repeat(int i, String string) {
		this.DAY = i;
		this.TOSTRING = string;
	}

	/**
	 * Returns the {@code Repeat} enum corresponding to the given string. Matching
	 * is case-insensitive and based on enum constant names.
	 *
	 * @param s the string representation of a repeat type (e.g., "mon",
	 *          "everyweek")
	 * @return the matching {@code Repeat} constant, or {@code null} if the input is
	 *         invalid
	 */
	public static Repeat checkRepeatFromString(String s) {
		try {
			return Repeat.valueOf(s.toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns the {@code Repeat} enum that corresponds to a day of the week index.
	 *
	 * @param i an integer from 0 (Monday) to 6 (Sunday)
	 * @return the corresponding {@code Repeat} constant, or {@code null} if the
	 *         index is out of range
	 */
	public static Repeat dayOfWeek(int i) {
		if (i >= 0 && i <= 6) {
			for (Repeat r : Repeat.values()) {
				if (r.DAY == i) {
					return r;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the human-readable label for this repeat pattern.
	 *
	 * @return the string label of the repeat type
	 */
	@Override
	public String toString() {
		return this.TOSTRING;
	}
}
