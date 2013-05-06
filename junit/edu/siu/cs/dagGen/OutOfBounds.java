package edu.siu.cs.dagGen;

import static org.junit.Assert.*;

import java.io.FileReader;

import org.junit.Test;

public class OutOfBounds {

	@Test
	public void throwsErrorChild() {
		try {
			FileReader file = new FileReader("junit/files/test3_2.dam");
			DAGFileParser parse = new DAGFileParser(file);
			parse.startParse();
			parse.getResult();
			fail("Did not catch exception");
		} catch(DAGOutOfBounds e) {
			
		} catch(Exception e) {
			fail("Caught wrong exception");
		}
	}
	
	@Test
	public void throwsErrorParent() {
		try {
			FileReader file = new FileReader("junit/files/test3.dam");
			DAGFileParser parse = new DAGFileParser(file);
			parse.startParse();
			parse.getResult();
			fail("Did not catch exception");
		} catch(DAGOutOfBounds e) {
			
		} catch(Exception e) {
			fail("Caught wrong exception");
		}
	}

}
