package model;

public enum Repeat {

//	Repeat -> ENUM of repetition: ex: Every Day, Every Week, None,
//	  Every 2 Weeks, Every MOnth, Every Year, Custom(ENUM of week: MON, TUE, ..., etc.)

	// NEED IT TO CORRESPONG WITH EACH ENUM
	MON, TUE, WED, THR, FRI, SAT, SUN, NONE, EVERYDAY, EVERYWEEK, EVERY2WEEKS, EVERYMONTH, EVERYYEAR, CUSTOM;

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

		if (i == 0)
			return Repeat.MON;
		else if (i == 1)
			return Repeat.TUE;
		else if (i == 2)
			return Repeat.WED;
		else if (i == 3)
			return Repeat.THR;
		else if (i == 4)
			return Repeat.FRI;
		else if (i == 5)
			return Repeat.SAT;
		else if (i == 6)
			return Repeat.SUN;
		return null;
	}

	// METHOD OVERLOADING
	public String toString(Repeat r) {

		// MULPTIPLE IF STATEMENTS TO RETURN PROPER STRING, (USE SWITCH STATEMENTS)

		return null;
	}

}
