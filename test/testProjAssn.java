package test;

import java.sql.Time;

import org.junit.jupiter.api.Test;

import model.ProjAssn;

public class testProjAssn {

	Time t = new Time(0);
//	ProjAssn pa = new ProjAssn();

	@Test
	void testConstructor() {
		System.out.println(t.toString());
		t.setHours(50);
		
	}
}
