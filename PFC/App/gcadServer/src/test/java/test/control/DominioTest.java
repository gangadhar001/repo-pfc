package test.control;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DominioTest extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new
		TestSuite("Tests para la capa de dominio");
		suite.addTestSuite(PruebasControlador.class);
		suite.addTestSuite(PruebasSesiones.class);
		return suite;
	}

}
