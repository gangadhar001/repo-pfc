package test;
import test.communication.CommunicationTestSuite;
import test.business.DominioTest;
import test.persistence.PersistenceTestSuite;
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
		suite.addTest(PersistenceTestSuite.suite());
		suite.addTest(CommunicationTestSuite.suite());
		suite.addTest(PresentacionTest.suite());
		return suite;
	}
	
}
