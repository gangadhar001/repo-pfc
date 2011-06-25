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
		suite.addTestSuite(GeoCoderTest.class);
		suite.addTestSuite(UsersControllerTest.class);
		suite.addTestSuite(ProjectsControllerTest.class);
		suite.addTestSuite(NotificationsControllerTest.class);
		suite.addTestSuite(KnowledgeControllerTest.class);
		return suite;
	}

}
