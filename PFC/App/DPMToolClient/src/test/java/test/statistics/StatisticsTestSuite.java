package test.statistics;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StatisticsTestSuite extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new
		TestSuite("Statistics Test");
		suite.addTestSuite(StatisticsTest.class);
		return suite;
	}

}
