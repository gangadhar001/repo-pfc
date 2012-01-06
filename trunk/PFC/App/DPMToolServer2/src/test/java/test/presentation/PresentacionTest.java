package test.presentation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PresentacionTest extends TestCase {

	public static Test suite() {
		TestSuite suite = new
		TestSuite("Presentation Layer Tests");
		suite.addTestSuite(PruebasJDConfigFrontend.class);;
		suite.addTestSuite(PruebasJFServidorFrontend.class);
		suite.addTestSuite(PruebasValidacion.class);
		return suite;
	}
}
