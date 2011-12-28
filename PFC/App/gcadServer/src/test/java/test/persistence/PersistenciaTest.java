package test.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PersistenciaTest extends TestCase {

	public static Test suite() {
		TestSuite suite = new
		TestSuite("Persistence Layer Tests");
		suite.addTestSuite(PruebasPersistencia.class);
		return suite;
	}
}
