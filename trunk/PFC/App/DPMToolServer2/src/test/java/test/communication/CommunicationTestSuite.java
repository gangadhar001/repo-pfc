package test.communication;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CommunicationTestSuite extends TestCase {

	public static Test suite() {
		TestSuite suite = new
		TestSuite("Comunication Layer Tests");
		suite.addTestSuite(RemoteServerTest.class);
		suite.addTestSuite(ConnectionTest.class);
		return suite;
	}
}
