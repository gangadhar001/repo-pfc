package test.business;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.business.control.Server;
import model.business.control.SessionController;
import model.business.knowledge.Address;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.ISession;
import model.business.knowledge.Project;
import model.business.knowledge.User;
import persistence.DAOUser;
import test.communication.PruebasBase;

import communication.DBConnection;
import communication.DBConnectionManager;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;


public class UsersControllerTest extends PruebasBase {	

	private User chief, employee;
	private Company company;
	private Address address;
	private Project project;
	
	protected void setUp() {	
		try {
			// Preparamos la base de datos
			super.setUp();
			// Creamos un administrador de prueba
			DBConnectionManager.clear();
			DBConnectionManager.addConnection(new DBConnection());
			address = new Address("street", "city", "country", "zip", "address");
			company = new Company("456as", "company", "information", address);
			employee = new Employee("12345678L", "emp1", "emp1", "User1", "emp1", "", "", 2, company);
			chief = new ChiefProject("65413987L", "emp2", "emp2", "User", "chief", "", "", 12, company);
			project = new Project("project", "desc1", new Date(), new Date(), 203.36, 458, "bank", "java", 557);
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(address);
			DBConnectionManager.insert(company);
			DBConnectionManager.insert(chief);
			DBConnectionManager.insert(employee);
			DBConnectionManager.insert(project);
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
	
	public void testGetUsers() {
		ISession session = null;
		try {
			// Se intenta obtener todos los usuarios con una sesion invalida
			Server.getInstance().getUsers(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta obtener el usuario logueado con una sesion invalida
			Server.getInstance().getLoggedUser(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta obtener todos los usuarios
			session = Server.getInstance().login("emp2", "emp2");
			List<User> users = Server.getInstance().getUsers(session.getId());
			assertTrue(users.size() == 2);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Se intenta obtener el usuario logueado 
			User user = Server.getInstance().getLoggedUser(session.getId());
			assertEquals(user, chief);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Se intenta duplicar el usuario, para forzar la excepción
			DAOUser.insert(chief);
			fail("se esperaba SQLException");
		} catch (SQLException e) { 
		} catch(Exception e) {
			fail("se esperaba SQLException");
		}
	}
	
	public void testUpdateUsers() {		
		ISession session = null, session2;
		try {
			// Se intenta actualizar el usuario pasando una sesión invalida
			Server.getInstance().addProjectsUser(-15, employee, project);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta obtener todos los usuarios sin tener permiso
			session = Server.getInstance().login("emp1", "emp1");
			Server.getInstance().addProjectsUser(session.getId(), employee, project);
			fail("se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) { 
		} catch(Exception e) {
			fail("se esperaba NonPermissionRoleException");
		}
		
		try {
			// Se intenta actualizar el usuario con un nuevo proyecto
			assertTrue(employee.getProjects().size() == 0);
			session2 = Server.getInstance().login("emp2", "emp2");
			Server.getInstance().addProjectsUser(session2.getId(), employee, project);
			assertTrue(employee.getProjects().size() == 1);
			// Se comprueba si se ha actualizado el usuario de la sesion con el nuevo proyecto
			assertEquals(SessionController.getSession(session.getId()).getUser(), employee);
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
}
