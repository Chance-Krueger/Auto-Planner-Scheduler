
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import model.Repeat;

public class TestRepeat {

	@Test
	void testCheckRepeatFromString() {
		Repeat mon = Repeat.checkRepeatFromString("MoN");
		assertEquals(mon, Repeat.MON);

		Repeat tue = Repeat.checkRepeatFromString("Tue");
		assertEquals(tue, Repeat.TUE);

		Repeat wed = Repeat.checkRepeatFromString("wED");
		assertEquals(wed, Repeat.WED);

		Repeat thr = Repeat.checkRepeatFromString("thr");
		assertEquals(thr, Repeat.THR);

		Repeat fri = Repeat.checkRepeatFromString("FRI");
		assertEquals(fri, Repeat.FRI);

		Repeat sat = Repeat.checkRepeatFromString("SAT");
		assertEquals(sat, Repeat.SAT);

		Repeat sun = Repeat.checkRepeatFromString("SUN");
		assertEquals(sun, Repeat.SUN);

		Repeat none = Repeat.checkRepeatFromString("NONE");
		assertEquals(none, Repeat.NONE);

		Repeat ed = Repeat.checkRepeatFromString("EVERYDAY");
		assertEquals(ed, Repeat.EVERYDAY);

		Repeat ew = Repeat.checkRepeatFromString("EVERYWEEK");
		assertEquals(ew, Repeat.EVERYWEEK);

		Repeat e2w = Repeat.checkRepeatFromString("EVERY2WEEKS");
		assertEquals(e2w, Repeat.EVERY2WEEKS);

		Repeat em = Repeat.checkRepeatFromString("EVERYMONTH");
		assertEquals(em, Repeat.EVERYMONTH);

		Repeat ey = Repeat.checkRepeatFromString("EVERYYEAR");
		assertEquals(ey, Repeat.EVERYYEAR);

		Repeat c = Repeat.checkRepeatFromString("CUSTOM");
		assertEquals(c, Repeat.CUSTOM);
	}

	@Test
	void testDayOfWeek() {
		assertEquals(Repeat.dayOfWeek(0), Repeat.MON);
		assertEquals(Repeat.dayOfWeek(1), Repeat.TUE);
		assertEquals(Repeat.dayOfWeek(2), Repeat.WED);
		assertEquals(Repeat.dayOfWeek(3), Repeat.THR);
		assertEquals(Repeat.dayOfWeek(4), Repeat.FRI);
		assertEquals(Repeat.dayOfWeek(5), Repeat.SAT);
		assertEquals(Repeat.dayOfWeek(6), Repeat.SUN);
		assertEquals(Repeat.dayOfWeek(7), null);
		assertEquals(Repeat.dayOfWeek(-1), null);
	}

	@Test
	void testToString() {
		Repeat mon = Repeat.checkRepeatFromString("MoN");
		assertTrue(mon.toString().equals(mon.TOSTRING));

		Repeat tue = Repeat.checkRepeatFromString("Tue");
		assertTrue(tue.toString().equals(tue.TOSTRING));

		Repeat wed = Repeat.checkRepeatFromString("wED");
		assertTrue(wed.toString().equals(wed.TOSTRING));

		Repeat thr = Repeat.checkRepeatFromString("thr");
		assertTrue(thr.toString().equals(thr.TOSTRING));

		Repeat fri = Repeat.checkRepeatFromString("FRI");
		assertTrue(fri.toString().equals(fri.TOSTRING));

		Repeat sat = Repeat.checkRepeatFromString("SAT");
		assertTrue(sat.toString().equals(sat.TOSTRING));

		Repeat sun = Repeat.checkRepeatFromString("SUN");
		assertTrue(sun.toString().equals(sun.TOSTRING));

		Repeat none = Repeat.checkRepeatFromString("NONE");
		assertTrue(none.toString().equals(none.TOSTRING));

		Repeat ed = Repeat.checkRepeatFromString("EVERYDAY");
		assertTrue(ed.toString().equals(ed.TOSTRING));

		Repeat ew = Repeat.checkRepeatFromString("EVERYWEEK");
		assertTrue(ew.toString().equals(ew.TOSTRING));

		Repeat e2w = Repeat.checkRepeatFromString("EVERY2WEEKS");
		assertTrue(e2w.toString().equals(e2w.TOSTRING));

		Repeat em = Repeat.checkRepeatFromString("EVERYMONTH");
		assertTrue(em.toString().equals(em.TOSTRING));

		Repeat ey = Repeat.checkRepeatFromString("EVERYYEAR");
		assertTrue(ey.toString().equals(ey.TOSTRING));

		Repeat c = Repeat.checkRepeatFromString("CUSTOM");
		assertTrue(c.toString().equals(c.TOSTRING));
	}

}
