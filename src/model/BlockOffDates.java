package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlockOffDates {
//	BlockOffDates: use ENUM and need to know times, use HASHMAP<Keys(dates of week) : Values(time blocked off)>
//		Breakfast/Lunch/Dinner Time (BLOCK OFF TIMES/DATES -> sat/sun/etc (LIKE Custom from repeat -> Use same ENUM))

	private HashMap<Repeat, Set<LocalTime>> blockedDates;

	public BlockOffDates() {

		this.blockedDates = new HashMap<Repeat, Set<LocalTime>>();

		// Initialize HashMap to Empty Blocked Dates to each Day in Week.
		for (int i = 0; i < 7; i++)
			this.blockedDates.put(Repeat.dayOfWeek(i), new HashSet<LocalTime>());
	}

	public BlockOffDates(BlockOffDates bod) {
		this.blockedDates = bod.blockedDates;
	}

	public void changeBlockedTimeOfDay(Repeat day, Set<LocalTime> times) {

		// Will naturally replace old day from how put works in HashMap Class.
		if (day.DAY >= 0)
			this.blockedDates.put(day, new HashSet<LocalTime>(times));
	}

	public Map<Repeat, Set<LocalTime>> getBlockedDates() {
		return this.blockedDates;
	}
}
