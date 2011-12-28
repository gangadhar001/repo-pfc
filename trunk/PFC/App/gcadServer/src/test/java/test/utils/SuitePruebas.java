package test.utils;
import test.communication.ComunicacionesTest;
import test.business.DominioTest;
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
		
		suite = new TestSuite("Test Suite for DPMTool - Server");
		suite.addTest(DominioTest.suite());
		suite.addTest(PersistenciaTest.suite());
		suite.addTest(ComunicacionesTest.suite());
		suite.addTest(PresentacionTest.suite());
		return suite;
	}
	
}
