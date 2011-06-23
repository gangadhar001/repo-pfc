package test.utils;
import test.communication.ComunicacionesTest;
import test.control.DominioTest;
import test.persistence.PersistenciaTest;
import test.presentation.PresentacionTest;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Suite de pruebas para el servidor
 */
public class SuitePruebas {
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(SuitePruebas.suite());
	}

	public static Test suite() {
		TestSuite suite;
		
		suite = new TestSuite("Pruebas completas para el servidor front-end");
		suite.addTest(DominioTest.suite());
		suite.addTest(PersistenciaTest.suite());
		suite.addTest(ComunicacionesTest.suite());
		suite.addTest(PresentacionTest.suite());
		return suite;
	}
	
}
