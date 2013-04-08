package edu.siu.cs.dagGen;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Test1.class, Test2.class , OutOfBounds.class, SelfDependent.class})
public class AllTests {

}
