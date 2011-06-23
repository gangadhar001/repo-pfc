package test.control;

import communication.DBConnection;
import communication.DBConnectionManager;

import test.communication.PruebasBase;
import exceptions.NonExistentAddressException;
import exceptions.WSResponseException;
import model.business.control.Server;
import model.business.control.ServerController;
import model.business.knowledge.Address;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Coordinates;
import model.business.knowledge.Employee;
import model.business.knowledge.ISession;
import model.business.knowledge.User;


public class GeoCoderTest extends PruebasBase {	

	private User chief;
	private Company company;
	private Address address;
	
	protected void setUp() {	
		try {
			// Preparamos la base de datos
			super.setUp();
			// Creamos un administrador de prueba
			DBConnectionManager.clear();
			DBConnectionManager.addConnection(new DBConnection());
			address = new Address("street", "city", "country", "zip");
			company = new Company("456as", "company", "information", address);
			chief = new ChiefProject("65413987L", "emp2", "emp2", "User", "chief", "", "", 12, company);
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(address);
			DBConnectionManager.insert(company);
			DBConnectionManager.insert(chief);
			DBConnectionManager.finishTransaction();
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	protected void tearDown() {
		try {
			// Cerramos la base de datos
			super.tearDown();
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testGeoocoordinatesTest() {		
		ISession session = null;
		try {
			// Se pasa una direccion invalida
			session = Server.getInstance().login("emp2", "emp2");
			Address add = new Address();	
			Server.getInstance().getCoordinates(session.getId(), add);
			fail("se esperaba WSResponseException");
		} catch (WSResponseException e) { 
		} catch(Exception e) {
			fail("se esperaba WSResponseException");
		}
		
		try {
			// Se pasa una direccion inexistente
			Address add = new Address("\"0\"", null, null, null);
			Server.getInstance().getCoordinates(session.getId(), add);
			fail("se esperaba NonExistentAddressException");
		} catch (NonExistentAddressException e) { 
		} catch(Exception e) {
			fail("se esperaba NonExistentAddressException");
		}
		
		try {
			// Se pasa una direccion
			Address add = new Address("La Mata", "Ciudad Real", "Spain", "13004");
			Coordinates coor = Server.getInstance().getCoordinates(session.getId(), add);
			assertEquals(coor.getLatitude(), "38.981249");
			assertEquals(coor.getLongitude(), "-3.908505");
		} catch(Exception e) {
			fail(e.toString());
		}
	}
}
