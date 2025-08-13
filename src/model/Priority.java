package model;

/**
 * The {@code Priority} enum represents a priority level system ranging from
 * {@code ZERO (0)} to {@code TEN (10)}. Each constant is associated with an
 * integer value, which can be used for comparison, sorting, or user input
 * mapping.
 *
 * <p>
 * This enum provides utility methods for converting from integers or strings to
 * the corresponding {@code Priority} constant. These methods support flexible
 * parsing and help prevent invalid input from crashing the program.
 *
 * <p>
 * Intended for use in scheduling or task management systems where priority
 * levels influence order or importance.
 *
 * @author Chance Krueger
 */

public enum Priority {

	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ZERO(0);

	private int prio;

	/**
	 * Constructs a {@code Priority} constant with the specified integer value.
	 *
	 * @param i the integer value representing the priority level
	 */
	Priority(int i) {
		prio = i;
	}

	/**
	 * Returns the {@code Priority} constant that corresponds to the given integer.
	 *
	 * @param i the integer priority value (0–10)
	 * @return the matching {@code Priority} constant, or {@code null} if no match
	 *         is found
	 */
	public static Priority fromInteger(int i) {
		for (Priority p : Priority.values()) {
			if (p.prio == i) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Returns the {@code Priority} constant that matches the given string. The
	 * match is case-insensitive and based on the enum constant name (e.g., "one",
	 * "TWO", etc.).
	 *
	 * @param s the string representation of the priority level
	 * @return the matching {@code Priority} constant, or {@code null} if input is
	 *         invalid
	 */
	public static Priority fromToString(String s) {
		try {
			if (Priority.valueOf(s.toUpperCase()) != null) {
				return Priority.valueOf(s.toUpperCase());
			}
		} catch (Exception e) {
			// Will return null on invalid input
		}
		return null;
	}

	public int getValue() {
		return this.prio;
	}
}
