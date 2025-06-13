package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import model.BlockOffDates;
import model.Repeat;

public class TestBlockOffDates {

	@Test
	void testConstructor() {
		BlockOffDates bfd = new BlockOffDates();
		assertEquals(bfd.getBlockedDates().size(), 7);
	}

	@Test
	void testChangeBlockedTimeOfDay() {

		BlockOffDates bfd = new BlockOffDates();

		ArrayList<Set<LocalTime>> times = new ArrayList<Set<LocalTime>>();

		Set<LocalTime> s2 = new HashSet<LocalTime>();
		Set<LocalTime> s3 = new HashSet<LocalTime>();
		Set<LocalTime> s4 = new HashSet<LocalTime>();
		Set<LocalTime> s5 = new HashSet<LocalTime>();

		s2.add(LocalTime.of(16, 30));
		s2.add(LocalTime.of(17, 0));

		times.add(s2);
		bfd.changeBlockedTimeOfDay(Repeat.MON, times);

		s3.add(LocalTime.of(11, 0));
		s3.add(LocalTime.of(11, 30));
		ArrayList<Set<LocalTime>> times1 = new ArrayList<Set<LocalTime>>();
		times1.add(s3);
		bfd.changeBlockedTimeOfDay(Repeat.TUE, times1);

		s4.add(LocalTime.of(7, 30));
		s4.add(LocalTime.of(9, 0));
		ArrayList<Set<LocalTime>> times2 = new ArrayList<Set<LocalTime>>();
		times2.add(s4);
		bfd.changeBlockedTimeOfDay(Repeat.WED, times2);

		s5.add(LocalTime.of(11, 30));
		s5.add(LocalTime.of(13, 0));
		ArrayList<Set<LocalTime>> times3 = new ArrayList<Set<LocalTime>>();
		times3.add(s5);
		bfd.changeBlockedTimeOfDay(Repeat.THR, times3);

		assertEquals(bfd.getBlockedDates().get(Repeat.MON).size(), 1);
		assertEquals(bfd.getBlockedDates().get(Repeat.TUE).size(), 1);
		assertEquals(bfd.getBlockedDates().get(Repeat.WED).size(), 1);
		assertEquals(bfd.getBlockedDates().get(Repeat.THR).size(), 1);
		assertEquals(bfd.getBlockedDates().get(Repeat.FRI).size(), 0);
		assertEquals(bfd.getBlockedDates().get(Repeat.SAT).size(), 0);
		assertEquals(bfd.getBlockedDates().get(Repeat.SUN).size(), 0);
	}

	@Test
	void testCopyConstructor() {

		BlockOffDates bfd = new BlockOffDates();

		ArrayList<Set<LocalTime>> times = new ArrayList<Set<LocalTime>>();

		Set<LocalTime> s2 = new HashSet<LocalTime>();
		Set<LocalTime> s3 = new HashSet<LocalTime>();
		Set<LocalTime> s4 = new HashSet<LocalTime>();
		Set<LocalTime> s5 = new HashSet<LocalTime>();

		s2.add(LocalTime.of(16, 30));
		s2.add(LocalTime.of(17, 0));

		times.add(s2);
		bfd.changeBlockedTimeOfDay(Repeat.MON, times);

		s3.add(LocalTime.of(11, 0));
		s3.add(LocalTime.of(11, 30));
		ArrayList<Set<LocalTime>> times1 = new ArrayList<Set<LocalTime>>();
		times1.add(s3);
		bfd.changeBlockedTimeOfDay(Repeat.TUE, times1);

		s4.add(LocalTime.of(7, 30));
		s4.add(LocalTime.of(9, 0));
		ArrayList<Set<LocalTime>> times2 = new ArrayList<Set<LocalTime>>();
		times2.add(s4);
		bfd.changeBlockedTimeOfDay(Repeat.WED, times2);

		s5.add(LocalTime.of(11, 30));
		s5.add(LocalTime.of(13, 0));
		ArrayList<Set<LocalTime>> times3 = new ArrayList<Set<LocalTime>>();
		times3.add(s5);
		bfd.changeBlockedTimeOfDay(Repeat.THR, times3);
		
		BlockOffDates bfd1 = new BlockOffDates(bfd);

		assertEquals(bfd1.getBlockedDates().get(Repeat.MON).size(), 1);
		assertEquals(bfd1.getBlockedDates().get(Repeat.TUE).size(), 1);
		assertEquals(bfd1.getBlockedDates().get(Repeat.WED).size(), 1);
		assertEquals(bfd1.getBlockedDates().get(Repeat.THR).size(), 1);
		assertEquals(bfd1.getBlockedDates().get(Repeat.FRI).size(), 0);
		assertEquals(bfd1.getBlockedDates().get(Repeat.SAT).size(), 0);
		assertEquals(bfd1.getBlockedDates().get(Repeat.SUN).size(), 0);
	}

}
