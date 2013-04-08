package edu.siu.cs.dagGen;

import static org.junit.Assert.*;

import org.junit.Test;

public class SelfDependent {

	@Test
	public void test() {
		try {
			DAGParse p = new DAGParse();
			DAGFunctions.integrityCheck(p.parseDependancies("junit/files/test4.dam"));
			fail("Did not catch exception");
		} catch (DAGSelfDependent e) {
			
		} catch (Exception e) {
			fail("Caught Wrong Exception" + e.getMessage());
		}
	}

}
