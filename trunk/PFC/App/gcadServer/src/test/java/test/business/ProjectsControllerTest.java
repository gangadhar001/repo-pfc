package test.business;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.business.control.Server;
import model.business.control.CBR.Attribute;
import model.business.knowledge.Address;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.ISession;
import model.business.knowledge.Project;
import model.business.knowledge.User;
import persistence.DAOProject;
import test.communication.PruebasBase;

import communication.DBConnection;
import communication.DBConnectionManager;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;


public class ProjectsControllerTest extends PruebasBase {	

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
	
	public void testCreateProject() {		
		ISession session = null;
		try {
			// Se intenta crear un proyecto con una sesion invalida
			Server.getInstance().createProject(-15, project);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta crear un proyecto sin tener permiso
			session = Server.getInstance().login("emp1", "emp1");
			Server.getInstance().createProject(session.getId(), project);
			fail("se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) { 
		} catch(Exception e) {
			fail("se esperaba NonPermissionRoleException");
		}
		
		try {
			// Se intenta crear un proyecto
			session = Server.getInstance().login("emp2", "emp2");
			Project p = Server.getInstance().createProject(session.getId(), project);
			assertEquals(p, project);
			assertTrue(Server.getInstance().getProjects(session.getId()).size() == 1);
			assertEquals(Server.getInstance().getProjects(session.getId()).get(0), project);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Se intenta crear otro proyecto con el mismo nombre
			DAOProject.insert(project);
			fail("Se esperaba SQLException");
		} catch(SQLException e) {
		} catch(Exception e) {
			fail("Se esperaba SQLException");
		}	
	}
	
	public void testUpdateProject() {		
		ISession session = null;
		try {
			// Se intenta actualizar un proyecto con una sesion invalida
			Server.getInstance().updateProject(-15, project);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta actualizar un proyecto sin tener permiso
			session = Server.getInstance().login("emp1", "emp1");
			Server.getInstance().updateProject(session.getId(), project);
			fail("se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) { 
		} catch(Exception e) {
			fail("se esperaba NonPermissionRoleException");
		}
		
		try {
			// Se actualiza un proyecto 
			session = Server.getInstance().login("emp2", "emp2");
			Project p = Server.getInstance().createProject(session.getId(), project);
			project.setId(p.getId());
			project.setName("new Name");
			Server.getInstance().updateProject(session.getId(), project);
			assertEquals(Server.getInstance().getProjects(session.getId()).get(0), project);
		} catch (NonPermissionRoleException e) { 
		} catch(Exception e) {
			fail("se esperaba NonPermissionRoleException");
		}
		
	}
	
	public void testGetUsersProject() {		
		ISession session = null;
		try {
			// Se intentan obtener los usuarios de un proyecto con una sesión invalida
			Server.getInstance().getUsersProject(-15, project);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}	
		
		try {
			// Se intentan obtener los usuarios de un proyecto
			DAOProject.insert(project);
			session = Server.getInstance().login("emp2", "emp2");
			Server.getInstance().addProjectsUser(session.getId(), employee, project);
			assertTrue(employee.getProjects().size() == 1);
			List<User> users = Server.getInstance().getUsersProject(session.getId(), project);
			assertTrue(users.size() == 1);
			assertEquals(users.get(0), employee);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Se intentan obtener el proyecto de la sesión actual
			session = Server.getInstance().login("emp2", "emp2");
			assertEquals(Server.getInstance().getCurrentProject(session.getId()), 0);
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testRemoveUsersProject() {
		ISession session = null;
		try {
			// Se intentan eliminar usuarios con una sesión inválida
			Server.getInstance().removeProjectsUser(-15, null, null);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intentan eliminar usuarios sin tener permiso
			session = Server.getInstance().login("emp1", "emp1");
			Server.getInstance().removeProjectsUser(session.getId(), chief, project);
			fail("se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) { 
		} catch(Exception e) {
			fail("se esperaba NonPermissionRoleException");
		}
		
		try {
			// Se intentan eliminar usuarios
			DAOProject.insert(project);
			session = Server.getInstance().login("emp2", "emp2");
			Server.getInstance().addProjectsUser(session.getId(), employee, project);
			assertTrue(employee.getProjects().size() == 1);
			List<User> users = Server.getInstance().getUsersProject(session.getId(), project);
			assertTrue(users.size() == 1);
			assertEquals(users.get(0), employee);
			Server.getInstance().removeProjectsUser(session.getId(), employee, project);
			assertTrue(employee.getProjects().size() == 0);
			users = Server.getInstance().getUsersProject(session.getId(), project);
			assertTrue(users.size() == 0);
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testGetProjects() {		
		ISession session = null;
		try {
			// Se intentan obtener los proyectos con una sesión invalida
			Server.getInstance().getProjects(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intentan obtener los proyectos de un usuario con una sesión invalida
			Server.getInstance().getProjectsFromCurrentUser(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intentan obtener todos los proyectos
			session = Server.getInstance().login("emp2", "emp2");
			List<Project> projects = Server.getInstance().getProjects(session.getId());
			assertTrue(projects.size() == 0);
			DAOProject.insert(project);
			projects = Server.getInstance().getProjects(session.getId());
			assertTrue(projects.size() == 1);
			assertEquals(projects.get(0), project);
		} catch(Exception e) {
			fail(e.toString());
		}	
		
		try {
			// Se intentan obtener todos los proyectos de un usuario
			Server.getInstance().addProjectsUser(session.getId(), chief, project);
			List<Project> projects = Server.getInstance().getProjectsFromCurrentUser(session.getId());
			assertTrue(projects.size() == 1);
			assertEquals(projects.get(0), project);
		} catch(Exception e) {
			fail(e.toString());
		}	
	}
	
	public void testGetAttributesProject() {		
		try {
			// Se intentan obtener los atributos del proyecto
			List<Attribute> atts = Server.getInstance().getAttributesFromProject(project);
			// $VRc is temporary created when using eclemma plugin coverage
			assertTrue(atts.size() == 11 || atts.size() == 12);
		} catch(Exception e) {
			fail(e.toString());
		}	
	}
}
