package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import model.Priority;
import model.ProjAssn;

public class TestProjAssn {

	@Test
	void testConstructor() {
		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);
		assertEquals(pa.getTime().toHours(), 50);
		assertEquals(pa.getPriority(), Priority.FIVE);
		assertEquals(pa.getDue(), t);
		assertTrue(pa.getTitle().equals("This Test Class"));
		assertTrue(pa.getUrl().equals(""));
	}

	@Test
	void testCopyConstructor() {
		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);
		assertEquals(pa.getTime().toHours(), 50);
		assertEquals(pa.getPriority(), Priority.FIVE);
		assertEquals(pa.getDue(), t);
		assertTrue(pa.getTitle().equals("This Test Class"));
		assertTrue(pa.getUrl().equals(""));

		ProjAssn pa2 = new ProjAssn(pa);
		assertEquals(pa2.getTime().toHours(), 50);
		assertEquals(pa2.getPriority(), Priority.FIVE);
		assertEquals(pa2.getDue(), t);
		assertTrue(pa2.getTitle().equals("This Test Class"));
		assertTrue(pa2.getUrl().equals(""));
	}

	@Test
	void testSetPriorityFromString() {
		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);
		assertEquals(pa.getPriority(), Priority.FIVE);
		pa.setPriorityFromString("FouR");
		assertEquals(pa.getPriority(), Priority.FOUR);
	}

	@Test
	void testSetPriorityFromInteger() {
		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);
		assertEquals(pa.getPriority(), Priority.FIVE);
		pa.setPriorityFromInteger(4);
		assertEquals(pa.getPriority(), Priority.FOUR);
	}

	@Test
	void testSetTime() {
		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);
		assertEquals(pa.getTime().toHours(), 50);
		Duration d2 = Duration.ofHours(40);
		pa.setTime(d2);
		assertEquals(pa.getTime().toHours(), 40);
	}

	@Test
	void testSetDue() {
		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);
		assertEquals(pa.getTime().toHours(), 50);
		LocalDateTime t2 = LocalDateTime.of(2025, 05, 25, 07, 15);
		pa.setDue(t2);
		assertEquals(pa.getDue(), t2);
	}

	@Test
	void testSubtractTime() {
		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);
		assertTrue(pa.subtractTime(10));
		assertEquals(pa.getTime().toHours(), 40);
		assertFalse(pa.subtractTime(41));
		assertEquals(pa.getTime().toHours(), 40);
		assertTrue(pa.subtractTime(40));
		assertEquals(pa.getTime().toHours(), 0);

	}

	@Test
	void testToString() {
		LocalDateTime t = LocalDateTime.now();
		Priority p = Priority.FIVE;
		Duration d = Duration.ofHours(50);
		ProjAssn pa = new ProjAssn("This Test Class", p, d, t);
		LocalDateTime t2 = LocalDateTime.of(2025, 05, 25, 07, 15);
		pa.setDue(t2);
		assertTrue(pa.toString().equals("This Test Class is Due MAY/25/2025 at: 7:15. With an "
				+ "estimated amount of time of 50 hours and has a Pritority Level five."));
	}
}
