package test.communication;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ComunicacionesTest extends TestCase {

	public static Test suite() {
		TestSuite suite = new
		TestSuite("Comunication Layer Tests");
		suite.addTestSuite(PruebasRemotoServidor.class);
		suite.addTestSuite(PruebasConexiones.class);
		return suite;
	}
}
