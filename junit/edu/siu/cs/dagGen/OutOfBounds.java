package edu.siu.cs.dagGen;

import static org.junit.Assert.*;

import java.io.FileReader;

import org.junit.Test;

public class OutOfBounds {

	@Test
	public void throwsErrorChild() {
		try {
			FileReader file = new FileReader("files/test3_2.dam");
			DAMParser parse = new DAMParser(file);
			parse.startParse();
			parse.getResult();
			fail("Did not catch exception");
		} catch(DAMOutOfBounds e) {
			
		} catch(Exception e) {
			fail("Caught wrong exception");
		}
	}
	
	@Test
	public void throwsErrorParent() {
		try {
			FileReader file = new FileReader("files/test3.dam");
			DAMParser parse = new DAMParser(file);
			parse.startParse();
			parse.getResult();
			fail("Did not catch exception");
		} catch(DAMOutOfBounds e) {
			
		} catch(Exception e) {
			fail("Caught wrong exception");
		}
	}

}
