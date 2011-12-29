package test;

import test.communication.ComunicacionesTest;
import test.statistics.StatisticsTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Suite de pruebas para el cliente
 */
public class SuitePruebas {
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(SuitePruebas.suite());
	}

	public static Test suite() {
		TestSuite suite;
		
		suite = new TestSuite("Test Suite for DPMTool - Client");
		suite.addTest(StatisticsTestSuite.suite());
		suite.addTest(ComunicacionesTest.suite());
		return suite;
	}
	
}
