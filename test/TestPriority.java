package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.Priority;

public class TestPriority {

	private Priority p0 = Priority.ZERO;
	private Priority p1 = Priority.ONE;
	private Priority p2 = Priority.TWO;
	private Priority p3 = Priority.THREE;
	private Priority p4 = Priority.FOUR;
	private Priority p5 = Priority.FIVE;
	private Priority p6 = Priority.SIX;
	private Priority p7 = Priority.SEVEN;
	private Priority p8 = Priority.EIGHT;
	private Priority p9 = Priority.NINE;
	private Priority p10 = Priority.TEN;

	@Test
	void testFromInteger() {
		assertEquals(Priority.fromInteger(0), p0);
		assertEquals(Priority.fromInteger(1), p1);
		assertEquals(Priority.fromInteger(2), p2);
		assertEquals(Priority.fromInteger(3), p3);
		assertEquals(Priority.fromInteger(4), p4);
		assertEquals(Priority.fromInteger(5), p5);
		assertEquals(Priority.fromInteger(6), p6);
		assertEquals(Priority.fromInteger(7), p7);
		assertEquals(Priority.fromInteger(8), p8);
		assertEquals(Priority.fromInteger(9), p9);
		assertEquals(Priority.fromInteger(10), p10);
		assertEquals(Priority.fromInteger(11), null);
		assertEquals(Priority.fromInteger(-1), null);
	}

	@Test
	void testFromToString() {
		assertEquals(Priority.fromToString("ZEro"), p0);
		assertEquals(Priority.fromToString("OnE"), p1);
		assertEquals(Priority.fromToString("tWo"), p2);
		assertEquals(Priority.fromToString("three"), p3);
		assertEquals(Priority.fromToString("fOUR"), p4);
		assertEquals(Priority.fromToString("FIVE"), p5);
		assertEquals(Priority.fromToString("SIx"), p6);
		assertEquals(Priority.fromToString("SEvEN"), p7);
		assertEquals(Priority.fromToString("EIgHT"), p8);
		assertEquals(Priority.fromToString("NInE"), p9);
		assertEquals(Priority.fromToString("TEn"), p10);
		assertEquals(Priority.fromToString("ELeVEN"), null);
		assertEquals(Priority.fromToString("NEGTIVE one"), null);
	}
}
