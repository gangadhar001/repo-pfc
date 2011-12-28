package test.business;

import model.business.control.Server;
import model.business.knowledge.Address;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Coordinates;
import model.business.knowledge.ISession;
import model.business.knowledge.User;
import test.communication.PruebasBase;

import communication.DBConnection;
import communication.DBConnectionManager;

import exceptions.NonExistentAddressException;
import exceptions.WSResponseException;


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
			address = new Address("street", "city", "country", "zip", "address");
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
			Address add = new Address(null, null, null, null, null);
			Server.getInstance().getCoordinates(session.getId(), add);
			fail("se esperaba WSResponseException");
		} catch (WSResponseException e) { 
		} catch(Exception e) {
			fail("se esperaba WSResponseException");
		}
		
		try {
			// Se pasa una direccion
			Address add = new Address("La Mata", "Ciudad Real", "Spain", "13004", "address");
			Coordinates coor = Server.getInstance().getCoordinates(session.getId(), add);
			assertEquals(coor.getLatitude(), "38.981249");
			assertEquals(coor.getLongitude(), "-3.908505");
		} catch(Exception e) {
			fail(e.toString());
		}
	}
}
