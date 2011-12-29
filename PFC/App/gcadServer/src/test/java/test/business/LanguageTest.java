package test.business;

import java.util.Date;

import communication.DBConnection;
import communication.DBConnectionManager;
import exceptions.NotLoggedException;

import model.business.control.Server;
import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.AnswerArgument;
import model.business.knowledge.Categories;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import resources.Language;
import test.IDatosPruebas;
import test.communication.ClientePrueba;
import test.communication.PruebasBase;

public class LanguageTest extends PruebasBase {
	
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
