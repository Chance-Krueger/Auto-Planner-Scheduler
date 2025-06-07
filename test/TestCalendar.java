package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import model.Calendar;
import model.Event;
import model.MeetingAppt;
import model.Priority;
import model.ProjAssn;
import model.Repeat;

public class TestCalendar {

	@Test
	void testConstructor() {

		Calendar c = new Calendar();
		assertEquals(c.getCalendar().size(), 0);
		assertEquals(c.getSettings().getThemeColor(), Color.DARK_GRAY);
		assertEquals(c.getSettings().getAccentColor(), Color.LIGHT_GRAY);
	}

	@Test
	void testCopyConstructor() {

	}

	@Test
	void testAddEventToCalendarProjAssn() {

		Calendar c = new Calendar();

		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);

		c.addEventToCalendar(pa);
		assertEquals(c.getCalendar().size(), 1);
		ArrayList<Event> e = c.getCalendar().get(t.toLocalDate());
		assertEquals(e.size(), 1);
		assertTrue(e.get(0).getTitle().equals("This Test Class"));

		Priority p2 = Priority.SIX;
		Duration d2 = Duration.ofHours(20);
		ProjAssn pa2 = new ProjAssn("This Test Class 2", p2, d2, t);

		c.addEventToCalendar(pa2);
		assertEquals(c.getCalendar().size(), 1);
		ArrayList<Event> e2 = c.getCalendar().get(t.toLocalDate());
		assertEquals(e2.size(), 2);
		assertTrue(e2.get(1).getTitle().equals("This Test Class 2"));
	}

	@Test
	void testAddEventToCalendarMeetingAppt() {

		Calendar c = new Calendar();

		LocalDateTime d = LocalDateTime.now();
		LocalTime st = LocalTime.now();
		LocalTime et = LocalTime.now();
		MeetingAppt ma = new MeetingAppt("Test Cases", d, st, et);

		c.addEventToCalendar(ma);
		assertEquals(c.getCalendar().size(), 1);
		ArrayList<Event> e = c.getCalendar().get(d.toLocalDate());
		assertEquals(e.size(), 1);
		assertTrue(e.get(0).getTitle().equals("Test Cases"));

		MeetingAppt ma2 = new MeetingAppt("Test Cases 2", d, st, et);

		c.addEventToCalendar(ma2);
		assertEquals(c.getCalendar().size(), 1);
		ArrayList<Event> e2 = c.getCalendar().get(d.toLocalDate());
		assertEquals(e2.size(), 2);
		assertTrue(e2.get(1).getTitle().equals("Test Cases 2"));

	}

	@Test
	void testRemoveEvent() {

		Calendar c = new Calendar();

		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);

		c.addEventToCalendar(pa);
		assertEquals(c.getCalendar().size(), 1);
		ArrayList<Event> e = c.getCalendar().get(t.toLocalDate());
		assertEquals(e.size(), 1);
		assertTrue(e.get(0).getTitle().equals("This Test Class"));

		Priority p2 = Priority.SIX;
		Duration d2 = Duration.ofHours(20);
		ProjAssn pa2 = new ProjAssn("This Test Class 2", p2, d2, t);

		c.addEventToCalendar(pa2);
		assertEquals(c.getCalendar().size(), 1);
		ArrayList<Event> e2 = c.getCalendar().get(t.toLocalDate());
		assertEquals(e2.size(), 2);
		assertTrue(e2.get(1).getTitle().equals("This Test Class 2"));

		LocalDateTime de = LocalDateTime.now();
		LocalTime st = LocalTime.now();
		LocalTime et = LocalTime.now();
		MeetingAppt ma = new MeetingAppt("Test Cases", de, st, et);

		c.addEventToCalendar(ma);
		assertEquals(c.getCalendar().size(), 1);
		ArrayList<Event> e3 = c.getCalendar().get(de.toLocalDate());
		assertEquals(e3.size(), 3);
		assertTrue(e3.get(2).getTitle().equals("Test Cases"));

		MeetingAppt ma2 = new MeetingAppt("Test Cases 2", de, st, et);

		c.addEventToCalendar(ma2);
		assertEquals(c.getCalendar().size(), 1);
		ArrayList<Event> e4 = c.getCalendar().get(de.toLocalDate());
		assertEquals(e4.size(), 4);
		assertTrue(e4.get(3).getTitle().equals("Test Cases 2"));

		assertTrue(c.removeEvent(ma2));
		assertFalse(c.removeEvent(new MeetingAppt("Test Cases 3", de, st, et)));
		assertFalse(c.removeEvent(null));

	}

	@Test
	void testAdjustTitle() {

		Calendar c = createCal();

		LocalDateTime t3 = LocalDateTime.now();
		Priority p3 = Priority.FIVE;
		Duration d3 = Duration.ofHours(50);
		ProjAssn pa3 = new ProjAssn("TESTING", p3, d3, t3);
		c.addEventToCalendar(pa3);

		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);

		ArrayList<Event> e = c.getCalendar().get(t.toLocalDate());
		assertTrue(e.contains(pa));
		c.adjustTitle(pa, "THIS IS NEW TITLE!");
		pa.setTitle("THIS IS NEW TITLE!");
		ArrayList<Event> e2 = c.getCalendar().get(t.toLocalDate());
		assertTrue(e2.contains(pa));
		assertTrue(e2.get(0).getTitle().equals("THIS IS NEW TITLE!"));

		LocalDateTime t2 = LocalDateTime.of(1, 1, 1, 1, 1);
		Priority p2 = Priority.FIVE;
		Duration d2 = Duration.ofHours(50);
		ProjAssn pa2 = new ProjAssn("This Test Class", p2, d2, t2);
		assertFalse(c.adjustTitle(pa2, "FAILED"));

	}

	@Test
	void testAdjustLocation() {

		Calendar c = createCal();

		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);

		ArrayList<Event> e = c.getCalendar().get(t.toLocalDate());
		assertTrue(e.contains(pa));
		c.adjustLocation(pa, "Home");
		ArrayList<Event> e2 = c.getCalendar().get(t.toLocalDate());
		assertFalse(e2.contains(new ProjAssn(pa)));
		assertTrue(e2.get(0).getLocation().equals("Home"));

		assertFalse(c.adjustLocation(null, null));
	}

	@Test
	void testAdjustNotes() {
		Calendar c = createCal();

		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);

		ArrayList<Event> e = c.getCalendar().get(t.toLocalDate());
		assertTrue(e.contains(pa));
		c.adjustNotes(pa, "Hello World");
		ArrayList<Event> e2 = c.getCalendar().get(t.toLocalDate());
		assertFalse(e2.contains(new ProjAssn(pa)));
		assertTrue(e2.get(0).getNotes().equals("Hello World"));

		assertFalse(c.adjustNotes(null, null));

	}

	@Test
	void testAdjustURL() {
		Calendar c = createCal();

		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);

		ArrayList<Event> e = c.getCalendar().get(t.toLocalDate());
		assertTrue(e.contains(pa));
		c.adjustURL(pa, "www.github.com");
		ArrayList<Event> e2 = c.getCalendar().get(t.toLocalDate());
		assertFalse(e2.contains(new ProjAssn(pa)));
		assertTrue(e2.get(0).getUrl().equals("www.github.com"));

		assertFalse(c.adjustURL(null, null));

	}

	@Test
	void testAdjustRepeatDates() {
		Calendar c = createCal();

		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);

		c.addEventToCalendar(pa);

		// TEST valid input
		ArrayList<String> slist = new ArrayList<String>();
		slist.add("EVERYWEEK");
		slist.add("TUe");
		slist.add("ThR");

		assertTrue(c.adjustRepeatDates(pa, slist));
		assertEquals(pa.getRepeatDates().get(Repeat.EVERYWEEK).size(), 2);

		assertFalse(c.adjustRepeatDates(null, null));

	}

	@Test
	void testAdjustThemeColor() {
		Calendar c = new Calendar();
		assertEquals(c.getSettings().getThemeColor(), Color.DARK_GRAY);
		c.adjustThemeColor(Color.BLACK);
		assertEquals(c.getSettings().getThemeColor(), Color.BLACK);
	}

	@Test
	void testAdjustAccentColor() {
		Calendar c = new Calendar();
		assertEquals(c.getSettings().getAccentColor(), Color.LIGHT_GRAY);
		c.adjustAccentColor(Color.WHITE);
		assertEquals(c.getSettings().getAccentColor(), Color.WHITE);
	}

	@Test
	void testAdjustBlockOffDates() {
		Calendar c = new Calendar();
		assertEquals(c.getSettings().getBod().getBlockedDates().size(), 7);

		Set<String> s2 = new HashSet<String>();
		Set<String> s3 = new HashSet<String>();

		s2.add(LocalTime.of(16, 30).toString());
		s2.add(LocalTime.of(17, 0).toString());
		s3.add(LocalTime.of(12, 30).toString());
		s3.add(LocalTime.of(14, 0).toString());

		ArrayList<Set<String>> times = new ArrayList<Set<String>>();
		times.add(s3);
		times.add(s2);

		assertTrue(c.adjustBlockOffDates("TUE", times));
		assertTrue(c.adjustBlockOffDates("MON", times));

		assertEquals(c.getSettings().getBod().getBlockedDates().get(Repeat.MON).size(), 2);
		assertEquals(c.getSettings().getBod().getBlockedDates().get(Repeat.TUE).size(), 2);
		assertEquals(c.getSettings().getBod().getBlockedDates().get(Repeat.WED).size(), 0);
		assertTrue(c.adjustBlockOffDates(null, new ArrayList<Set<String>>()));
		assertFalse(c.adjustBlockOffDates(null, times));
		times.add(s3);
		assertFalse(c.adjustBlockOffDates(null, times));
		ArrayList<Set<String>> times2 = new ArrayList<Set<String>>();
		Set<String> s4 = new HashSet<String>();
		Set<String> s5 = new HashSet<String>();

		s4.add(LocalTime.of(16, 30).toString());
		s4.add(LocalTime.of(17, 0).toString());
		s5.add(LocalTime.of(12, 30).toString());
		s5.add(LocalTime.of(14, 0).toString());
		s5.add(LocalTime.of(13, 0).toString());
		times2.add(s5);
		times2.add(s4);
		assertFalse(c.adjustBlockOffDates("MON", times2));

	}

	private Calendar createCal() {

		Calendar c = new Calendar();

		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);

		c.addEventToCalendar(pa);

		Priority p2 = Priority.SIX;
		Duration d2 = Duration.ofHours(20);
		ProjAssn pa2 = new ProjAssn("This Test Class 2", p2, d2, t);

		c.addEventToCalendar(pa2);

		LocalDateTime de = LocalDateTime.now();
		LocalTime st = LocalTime.now();
		LocalTime et = LocalTime.now();
		MeetingAppt ma = new MeetingAppt("Test Cases", de, st, et);

		c.addEventToCalendar(ma);
		MeetingAppt ma2 = new MeetingAppt("Test Cases 2", de, st, et);
		c.addEventToCalendar(ma2);

		return c;
	}

}
