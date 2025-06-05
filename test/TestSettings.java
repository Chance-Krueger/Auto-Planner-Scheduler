package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import model.BlockOffDates;
import model.Repeat;
import model.Settings;

public class TestSettings {

	@Test
	void testConstructor() {
		Settings s = new Settings();
		assertEquals(s.getThemeColor(), Color.DARK_GRAY);
		assertEquals(s.getAccentColor(), Color.LIGHT_GRAY);
		assertEquals(s.getBod().getBlockedDates().size(), 7);
	}

	@Test
	void testGetBod() {
		Settings s = new Settings();
		assertEquals(s.getBod().getBlockedDates().size(), 7);
	}

	@Test
	void testSetBod() {
		Settings s = new Settings();
		assertEquals(s.getBod().getBlockedDates().size(), 7);

		BlockOffDates bfd = makeBFD();

		assertEquals(bfd.getBlockedDates().get(Repeat.MON).size(), 1);
		assertEquals(bfd.getBlockedDates().get(Repeat.TUE).size(), 1);
		assertEquals(bfd.getBlockedDates().get(Repeat.WED).size(), 1);
		assertEquals(bfd.getBlockedDates().get(Repeat.THR).size(), 1);
		assertEquals(bfd.getBlockedDates().get(Repeat.FRI).size(), 0);
		assertEquals(bfd.getBlockedDates().get(Repeat.SAT).size(), 0);
		assertEquals(bfd.getBlockedDates().get(Repeat.SUN).size(), 0);

		s.setBod(bfd);
		assertEquals(s.getBod().getBlockedDates().get(Repeat.MON).size(), 1);
		assertEquals(s.getBod().getBlockedDates().get(Repeat.TUE).size(), 1);
		assertEquals(s.getBod().getBlockedDates().get(Repeat.WED).size(), 1);
		assertEquals(s.getBod().getBlockedDates().get(Repeat.THR).size(), 1);
		assertEquals(s.getBod().getBlockedDates().get(Repeat.FRI).size(), 0);
		assertEquals(s.getBod().getBlockedDates().get(Repeat.SAT).size(), 0);
		assertEquals(s.getBod().getBlockedDates().get(Repeat.SUN).size(), 0);

	}

	@Test
	void testGetThemeColor() {
		Settings s = new Settings();
		assertEquals(s.getThemeColor(), Color.DARK_GRAY);
	}

	@Test
	void testSetThemeColor() {
		Settings s = new Settings();
		assertEquals(s.getThemeColor(), Color.DARK_GRAY);
		s.setThemeColor(Color.BLACK);
		assertEquals(s.getThemeColor(), Color.BLACK);
	}

	@Test
	void testGetAccentColor() {
		Settings s = new Settings();
		assertEquals(s.getAccentColor(), Color.LIGHT_GRAY);
	}

	@Test
	void testSetAccentColor() {
		Settings s = new Settings();
		assertEquals(s.getAccentColor(), Color.LIGHT_GRAY);
		s.setAccentColor(Color.WHITE);
		assertEquals(s.getAccentColor(), Color.WHITE);
	}

	@Test
	void testChangeBlockedTimeOfDay() {
		Settings s = new Settings();
		assertEquals(s.getBod().getBlockedDates().size(), 7);

		Set<LocalTime> s2 = new HashSet<LocalTime>();
		Set<LocalTime> s3 = new HashSet<LocalTime>();

		s2.add(LocalTime.of(16, 30));
		s2.add(LocalTime.of(17, 0));
		s3.add(LocalTime.of(12, 30));
		s3.add(LocalTime.of(14, 0));

		ArrayList<Set<LocalTime>> times = new ArrayList<Set<LocalTime>>();
		times.add(s3);
		times.add(s2);

		s.changeBlockedTimeOfDay(0, times);
		s.changeBlockedTimeOfDay(Repeat.TUE, times);

		assertEquals(s.getBod().getBlockedDates().get(Repeat.MON).size(), 2);
		assertEquals(s.getBod().getBlockedDates().get(Repeat.TUE).size(), 2);
		assertEquals(s.getBod().getBlockedDates().get(Repeat.WED).size(), 0);
	}

	private BlockOffDates makeBFD() {
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

		return bfd;
	}

}
