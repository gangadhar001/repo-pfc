package test.business;

import model.business.control.Server;
import resources.Language;
import test.communication.BaseTest;

public class LanguageTest extends BaseTest {
	
	private Server server;

	protected void setUp() {	
		try {
			// Preparamos la base de datos
			super.setUp();
			server = new Server();
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testSetLanguage() {
		try {
			Language lan = new Language("Spanish", "es-ES");
			server.setDefaultLanguage(lan);
			fail("se esperaba NullPointerException");
		} catch (NullPointerException e) { 
		} catch(Exception e) {
			fail("se esperaba NullPointerException");
		}
		
		try {
			Language lan = new Language("Spanish", "es_ES");
			server.setDefaultLanguage(lan);
		} catch(Exception e) {
			fail(e.toString());
		}
	}


}
