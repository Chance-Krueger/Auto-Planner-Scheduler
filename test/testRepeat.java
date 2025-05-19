
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.Repeat;

public class testRepeat {

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
	void testToString() {

	}

}
