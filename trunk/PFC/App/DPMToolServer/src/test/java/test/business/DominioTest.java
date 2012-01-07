package test.business;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DominioTest extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new
		TestSuite("Business and Control Layer Tests");
		suite.addTestSuite(PruebasControlador.class);
		suite.addTestSuite(PruebasSesiones.class);
		suite.addTestSuite(PDFTest.class);
		suite.addTestSuite(ExportTest.class);
		suite.addTestSuite(GeoCoderTest.class);
		suite.addTestSuite(UsersControllerTest.class);
		suite.addTestSuite(ProjectsControllerTest.class);
		suite.addTestSuite(CBRTest.class);
		suite.addTestSuite(NotificationsControllerTest.class);
		suite.addTestSuite(KnowledgeControllerTest.class);
		suite.addTestSuite(LanguageTest.class);
		return suite;
	}

}
