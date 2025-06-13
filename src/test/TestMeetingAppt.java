package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import model.MeetingAppt;

public class TestMeetingAppt {

	@Test
	void testConstructor() {
		LocalDateTime d = LocalDateTime.now();
		LocalTime st = LocalTime.now();
		LocalTime et = LocalTime.now();
		MeetingAppt ma = new MeetingAppt("Test Cases", d, st, et);

		assertTrue(ma.getTitle().equals("Test Cases"));
		assertEquals(ma.getDate(), d);
		assertEquals(ma.getStartTime(), st);
		assertEquals(ma.getEndTime(), et);
	}
	
	@Test
	void testCopyConstructor() {
		LocalDateTime d = LocalDateTime.now();
		LocalTime st = LocalTime.now();
		LocalTime et = LocalTime.now();
		MeetingAppt ma = new MeetingAppt("Test Cases", d, st, et);

		assertTrue(ma.getTitle().equals("Test Cases"));
		assertEquals(ma.getDate(), d);
		assertEquals(ma.getStartTime(), st);
		assertEquals(ma.getEndTime(), et);
		
		MeetingAppt ma2 = new MeetingAppt(ma);
		assertTrue(ma2.getTitle().equals("Test Cases"));
		assertEquals(ma2.getDate(), d);
		assertEquals(ma2.getStartTime(), st);
		assertEquals(ma2.getEndTime(), et);
	}

	@Test
	void testSetDate() {
		LocalDateTime d = LocalDateTime.now();
		LocalTime st = LocalTime.now();
		LocalTime et = LocalTime.now();
		MeetingAppt ma = new MeetingAppt("Test Cases", d, st, et);
		assertEquals(ma.getDate(), d);
		LocalDateTime d2 = LocalDateTime.of(1, 1, 1, 1, 1);
		ma.setDate(d2);
		assertEquals(ma.getDate(), d2);
	}

	@Test
	void testStartTime() {
		LocalDateTime d = LocalDateTime.now();
		LocalTime st = LocalTime.now();
		LocalTime et = LocalTime.now();
		MeetingAppt ma = new MeetingAppt("Test Cases", d, st, et);
		assertEquals(ma.getStartTime(), st);
		LocalTime st2 = LocalTime.of(1, 1, 1, 1);
		ma.setStartTime(st2);
		assertEquals(ma.getStartTime(), st2);

	}

	@Test
	void testEndTime() {
		LocalDateTime d = LocalDateTime.now();
		LocalTime st = LocalTime.now();
		LocalTime et = LocalTime.now();
		MeetingAppt ma = new MeetingAppt("Test Cases", d, st, et);
		assertEquals(ma.getEndTime(), et);
		LocalTime et2 = LocalTime.of(0, 0, 0, 0);
		ma.setEndTime(et2);
		assertEquals(ma.getEndTime(), et2);

	}

	@Test
	void testToString() {
		LocalDateTime d = LocalDateTime.now();
		LocalTime st = LocalTime.now();
		LocalTime et = LocalTime.now();
		MeetingAppt ma = new MeetingAppt("Test Cases", d, st, et);
		assertTrue(ma.toString()
				.equals(ma.getTitle() + " is on " + ma.getDate().getMonth() + "/" + ma.getDate().getDayOfMonth() + "/"
						+ ma.getDate().getYear() + " and starts from " + ma.getStartTime().getHour() + ":"
						+ ma.getStartTime().getMinute() + " and will wend at " + ma.getEndTime().getHour() + ":"
						+ ma.getEndTime().getMinute() + "."));
	}
}
