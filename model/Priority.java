package model;

public enum Priority {

	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ZERO(0);

	private int prio;

	Priority(int i) {
		prio = i;
	}

	public static Priority fromInteger(int i) {

		for (Priority p : Priority.values()) {
			if (p.prio == i) {
				return p;
			}
		}

		return null;
	}

	public static Priority fromToString(String s) {

		try {
			if (Priority.valueOf(s.toUpperCase()) != null) {
				return Priority.valueOf(s.toUpperCase());
			}
		} catch (Exception e) {
			// WILL RETURN NULL
		}
		return null;
	}

}
