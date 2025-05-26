package model;

public enum Repeat {

//	Repeat -> ENUM of repetition: ex: Every Day, Every Week, None,
//	  Every 2 Weeks, Every MOnth, Every Year, Custom(ENUM of week: MON, TUE, ..., etc.)

	// NEED IT TO CORRESPONG WITH EACH ENUM
	MON(0, "Monday"), TUE(1, "Tuesday"), WED(2, "Wednesday"), THR(3, "Thursday"), FRI(4, "Friday"), SAT(5, "Saturday"),
	SUN(6, "Sunday"), NONE(-1, "None"), EVERYDAY(-1, "Everyday"), EVERYWEEK(-1, "Every Week"),
	EVERY2WEEKS(-1, "Every 2 Weeks"), EVERYMONTH(-1, "Every Month"), EVERYYEAR(-1, "Every Year"), CUSTOM(-1, "Custom");

	public final int DAY;
	public final String TOSTRING;

	Repeat(int i, String string) {
		this.DAY = i;
		this.TOSTRING = string;
	}

	// CONVERT STRING TO ENUM Repeat.
	public static Repeat checkRepeatFromString(String s) {
		try {
			if (Repeat.valueOf(s.toUpperCase()) != null) {
				return Repeat.valueOf(s.toUpperCase());
			}
		} catch (Exception e) {
			// WILL RETURN NULL
		}
		return null;
	}

	// MAKE BETTER, try to use ENUM with natural indexing
	public static Repeat dayOfWeek(int i) {

		if (i >= 0 && i <= 6)
			for (Repeat r : Repeat.values())
				if (r.DAY == i)
					return r;
		return null;
	}

	@Override
	public String toString() {
		return this.TOSTRING;
	}

}
