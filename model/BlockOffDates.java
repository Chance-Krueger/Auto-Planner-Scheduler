package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BlockOffDates {
//	BlockOffDates: use ENUM and need to know times, use HASHMAP<Keys(dates of week) : Values(time blocked off)>
//		Breakfast/Lunch/Dinner Time (BLOCK OFF TIMES/DATES -> sat/sun/etc (LIKE Custom from repeat -> Use same ENUM))

	private HashMap<Repeat, ArrayList<Time>> blockedDates;

	public BlockOffDates() {

		// Initialize HashMap to Empty Blocked Dates to each Day in Week.
		for (int i = 0; i < 7; i++)
			this.blockedDates.put(Repeat.dayOfWeek(i), new ArrayList<Time>());
	}

	public void changeBlockedTimeOfDay(Repeat day, ArrayList<Time> times) {

		// Will naturally replace old day from how put works in HashMap Class.
		this.blockedDates.put(day, times);
	}

	public Map<Repeat, ArrayList<Time>> getBlockedDates() {
		return Collections.unmodifiableMap(this.blockedDates);
	}
}
