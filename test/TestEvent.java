package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Event;
import model.Repeat;

public class TestEvent {

	@Test
	void testConstructor() {

		Event e = new Event("Make App");
		assertTrue(e.getTitle().equals("Make App"));
		assertTrue(e.getLocation().equals(""));
		assertTrue(e.getNotes().equals(""));
		assertTrue(e.getUrl().equals(""));
		assertEquals(e.getRepeatDates().size(), 0);
	}

	@Test
	void testGetTitle() {

		Event e = new Event("Coding Project");
		assertTrue(e.getTitle().equals("Coding Project"));
	}

	@Test
	void testSetTitle() {

		Event e = new Event("Coding Project");
		assertTrue(e.getTitle().equals("Coding Project"));
		e.setTitle("GITHUB RESUME");
		assertTrue(e.getTitle().equals("GITHUB RESUME"));
	}

	@Test
	void getLocation() {

		Event e = new Event("Coding Project");
		assertTrue(e.getLocation().equals(""));
		e.setLocation("Home");
		assertTrue(e.getLocation().equals("Home"));
	}

	@Test
	void setLocation() {

		Event e = new Event("Coding Project");
		e.setLocation("Home");
		assertTrue(e.getLocation().equals("Home"));
		e.setLocation("APPT");
		assertTrue(e.getLocation().equals("APPT"));
		assertFalse(e.getLocation().equals("Home"));
	}

	@Test
	void testGetRepeatDates() {

		Event e = new Event("Coding Project");
		assertEquals(e.getRepeatDates().size(), 0);
	}

	@Test
	void testSetRepeatDates() {

		Event e = new Event("CLASS");
		assertEquals(e.getRepeatDates().size(), 0);

		// TEST valid input
		ArrayList<String> slist = new ArrayList<String>();
		slist.add("EVERYWEEK");
		slist.add("TUe");
		slist.add("ThR");
		assertTrue(e.setRepeatDates(slist));
		assertEquals(e.getRepeatDates().get(Repeat.EVERYWEEK).size(), 2);

		// TEST specType
		ArrayList<String> slist2 = new ArrayList<String>();
		slist2.add("MON"); // false
		slist2.add("TUe");
		assertFalse(e.setRepeatDates(slist2));
		assertEquals(e.getRepeatDates().get(Repeat.EVERYWEEK).size(), 2);

		// TEST isDay
		ArrayList<String> slist3 = new ArrayList<String>();
		slist3.add("CUSTOM");
		slist3.add("MON");
		slist3.add("WED");
		slist3.add("EVERYMONTH"); // false
		assertFalse(e.setRepeatDates(slist3));
		assertEquals(e.getRepeatDates().get(Repeat.EVERYWEEK).size(), 2);

		// TEST is null
		ArrayList<String> slist4 = new ArrayList<String>();
		slist4.add("EVERY2WEEKS");
		slist4.add("SAT");
		slist4.add("SUN");
		slist4.add("IDK"); // false
		assertFalse(e.setRepeatDates(slist4));
		assertEquals(e.getRepeatDates().get(Repeat.EVERYWEEK).size(), 2);

		// TEST EVERYMONTH
		ArrayList<String> slist5 = new ArrayList<String>();
		slist5.add("EVERYMONTH");
		slist5.add("TUe"); // false
		assertFalse(e.setRepeatDates(slist5));
		assertEquals(e.getRepeatDates().get(Repeat.EVERYWEEK).size(), 2);

		// TEST EVERYYEAR
		ArrayList<String> slist6 = new ArrayList<String>();
		slist6.add("EVERYMONTH");
		slist6.add("TUe"); // false
		assertFalse(e.setRepeatDates(slist6));
		assertEquals(e.getRepeatDates().get(Repeat.EVERYWEEK).size(), 2);

		// TEST EVERYMONTH valid
		ArrayList<String> slist7 = new ArrayList<String>();
		slist7.add("EVERYMONTH");
		assertTrue(e.setRepeatDates(slist7));
		assertEquals(e.getRepeatDates().get(Repeat.EVERYMONTH).size(), 1);

		// TEST EVERYYEAR valid
		ArrayList<String> slist8 = new ArrayList<String>();
		slist8.add("EVERYYEAR");
		assertTrue(e.setRepeatDates(slist8));
		assertEquals(e.getRepeatDates().get(Repeat.EVERYYEAR).size(), 1);
	}

	@Test
	void testGetNotes() {
		Event e = new Event("Vacation To Bonaire");
		assertTrue(e.getNotes().equals(""));
		e.setNotes("Bring Scuba Gear");
		assertTrue(e.getNotes().equals("Bring Scuba Gear"));
	}

	@Test
	void testSetNotes() {
		Event e = new Event("Vacation To Bonaire");
		e.setNotes("");
		assertTrue(e.getNotes().equals(""));
		e.setNotes("Bring Depth Gauge");
		assertTrue(e.getNotes().equals("Bring Depth Gauge"));
	}

	@Test
	void testGetUrl() {
		Event e = new Event("Apply to Internship");
		assertTrue(e.getUrl().equals(""));
	}

	@Test
	void testSetUrl() {
		Event e = new Event("Apply to Internship");
		assertTrue(e.getUrl().equals(""));
		e.setUrl("https://www.IWantAnInternship.com/ChanceKrueger");
		assertTrue(e.getUrl().equals("https://www.IWantAnInternship.com/ChanceKrueger"));
	}

	@Test
	void testToString() {
		Event e = new Event("This will be a String");
		assertTrue(e.toString().equals(e.getTitle()));
		e.setLocation("Comipler");
		assertTrue(e.toString().equals(e.getTitle() + " at " + e.getLocation()));

	}

	@Test
	void testCopyConstructor() {
		Event e = new Event("Make App");
		e.setLocation("Everywhere");
		e.setNotes("Whenever you have time and your Laptop, DO WORK");
		e.setUrl("GITHUB.com");
		assertTrue(e.getTitle().equals("Make App"));
		assertTrue(e.getLocation().equals("Everywhere"));
		assertTrue(e.getNotes().equals("Whenever you have time and your Laptop, DO WORK"));
		assertTrue(e.getUrl().equals("GITHUB.com"));
		assertEquals(e.getRepeatDates().size(), 0);

		Event e2 = new Event(e);
		assertTrue(e.getTitle().equals(e2.getTitle()));
		assertTrue(e.getLocation().equals(e2.getLocation()));
		assertTrue(e.getNotes().equals(e2.getNotes()));
		assertTrue(e.getUrl().equals(e2.getUrl()));
		assertEquals(e.getRepeatDates().size(), 0);
	}

	@Test
	void testEquals() {
		Event e = new Event("Make App");
		e.setLocation("Everywhere");
		e.setNotes("Whenever you have time and your Laptop, DO WORK");
		e.setUrl("GITHUB.com");

		Event e2 = new Event(e);
		assertTrue(e.equals(e2));

		Event e3 = new Event("Make App");
		assertFalse(e.equals(e3));

		Event e4 = new Event("Make App");
		e4.setLocation("Everywhere");
		assertFalse(e.equals(e4));

		Event e5 = new Event("Make App");
		e5.setLocation("Everywhere");
		e5.setNotes("Whenever you have time and your Laptop, DO WORK");
		assertFalse(e.equals(e5));

		Event e6 = new Event("Make App");
		e6.setLocation("Everywhere");
		e6.setNotes("Whenever you have time and your Laptop, DO WORK");
		e6.setUrl("GITHUB.com");
		assertTrue(e.equals(e6));

		Event e7 = new Event("Make Prog");
		assertFalse(e.equals(e7));

		Event e8 = new Event("Make App");
		e8.setLocation("Everywhere");
		e8.setNotes("Whenever you have time and your Laptop, DO WORK");
		e8.setUrl("GITHUB.com");
		ArrayList<String> l = new ArrayList<String>();
		l.add("EVERYWEEK");
		l.add("MON");
		e8.setRepeatDates(l);
		assertFalse(e.equals(e8));

	}
}
