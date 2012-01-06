package test.business;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import model.business.control.Server;
import model.business.knowledge.Address;
import model.business.knowledge.Categories;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.ISession;
import model.business.knowledge.Notification;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.User;
import persistence.DAOProposal;
import persistence.DAOUser;
import test.IDatosPruebas;
import test.communication.DummyClient;
import test.communication.BaseTest;

import communication.DBConnection;
import communication.DBConnectionManager;

import exceptions.NonExistentNotificationException;
import exceptions.NotLoggedException;


public class NotificationsControllerTest extends BaseTest {	

	private User chief, employee;
	private Company company;
	private Address address;
	private Project project;
	private Proposal pro;
	private Topic topic;
	private Notification not;
	private DummyClient chiefClient;
	
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
			pro = new Proposal("pro", "desc", new Date(), Categories.Analysis);
			topic = new Topic("pro", "desc", new Date());
			not = new Notification(topic, "Unread", project, "subject", new HashSet<User>());
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(address);
			DBConnectionManager.insert(company);
			DBConnectionManager.insert(chief);
			DBConnectionManager.insert(employee);
			DBConnectionManager.insert(project);
			topic.setUser(chief);
			topic.setProject(project);
			DBConnectionManager.insert(topic);	
			pro.setUser(chief);
			DAOProposal.insert(pro, topic.getId());
			chiefClient = new DummyClient();
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
	
	public void testGetNotificationsProject() {		
		ISession session = null;
		try {
			// Se intentan obtener las notificaciones de un proyecto con una sesion invalida
			Server.getInstance().getNotificationsProject(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intentan obtener las notificaciones de un proyecto sin haber establecido el proyecto actual
			session = Server.getInstance().login("emp2", "emp2");
			List<Notification> nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 0);
		} catch (Exception e) { 
			fail(e.toString());
		}		
		
		try {
			// Se intentan obtener las notificaciones de un proyecto
			chief.getProjects().add(project);
			DAOUser.update(chief);		
			Server.getInstance().setCurrentProject(session.getId(), project.getId());
			List<Notification> nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 0);			
			Server.getInstance().createNotification(session.getId(), not);
			nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testGetNotificationsUser() {		
		ISession session = null;
		try {
			// Se intentan obtener las notificaciones de un usuario con una sesion invalida
			Server.getInstance().getNotificationsUser(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intentan obtener las notificaciones de un usuario sin haber establecido el proyecto actual
			session = Server.getInstance().login("emp2", "emp2");
			List<Notification> nots = Server.getInstance().getNotificationsUser(session.getId());
			assertTrue(nots.size() == 0);
		} catch (Exception e) { 
			fail(e.toString());
		}		
		
		try {
			// Se intentan obtener las notificaciones de un usuario
			not.getUsers().add(chief);
			Server.getInstance().setCurrentProject(session.getId(), project.getId());
			List<Notification> nots = Server.getInstance().getNotificationsUser(session.getId());
			assertTrue(nots.size() == 0);	
			Server.getInstance().createNotification(session.getId(), not);
			nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testInsertNotification() {
		ISession session = null;
		try {
			// Se intenta insertar una notificacion con una sesion invalida
			Server.getInstance().createNotification(-15, not);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta insertar una notificacion
			session = Server.getInstance().login("emp2", "emp2");
			chiefClient.activate(IDatosPruebas.IP_ESCUCHA_CLIENTES);
			Server.getInstance().register(session.getId(), chiefClient);
			Server.getInstance().setCurrentProject(session.getId(), project.getId());
			not.getUsers().add(chief);
			Server.getInstance().createNotification(session.getId(), not);
			List<Notification> nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(chiefClient.getUltimoDato(), not);
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testUpdateNotification() {
		ISession session = null;
		try {
			// Se intenta modificar una notificacion con una sesion invalida
			Server.getInstance().modifyNotification(-15, not);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta modificar una notificacion inexistente
			session = Server.getInstance().login("emp2", "emp2");
			Server.getInstance().modifyNotification(session.getId(), not);
			fail("se esperaba NonExistentNotificationException");
		} catch (NonExistentNotificationException e) { 
		} catch(Exception e) {
			fail("se esperaba NonExistentNotificationException");
		}	
		
		try {
			// Se intenta cambiar el estado de la notificación sin estar logueado
			Server.getInstance().modifyNotificationState(-15, not);
			Server.getInstance().getNotificationsUser(session.getId());
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}	
		
		try {
			// Se intenta insertar una notificacion
			Server.getInstance().setCurrentProject(session.getId(), project.getId());
			Server.getInstance().createNotification(session.getId(), not);
			List<Notification> nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
			// Se añade un usuario a la notificacion y se actualiza
			not.getUsers().add(chief);
			not.getUsers().add(employee);
			Server.getInstance().modifyNotification(session.getId(), not);
			nots = Server.getInstance().getNotificationsUser(session.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
			// Se modifica el estado de la notificacion para este usuario
			not.setState("Read");
			Server.getInstance().modifyNotificationState(session.getId(), not);
			nots = Server.getInstance().getNotificationsUser(session.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
			// Se comprueba que no ha afectado al otro usuario el cambio de estado
			session = Server.getInstance().login("emp1", "emp1");
			Server.getInstance().setCurrentProject(session.getId(), project.getId());
			nots = Server.getInstance().getNotificationsUser(session.getId());
			assertTrue(nots.size() == 1);
			assertFalse(nots.get(0).getState().startsWith("R"));
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testDeleteNotification() {
		ISession session = null;
		try {
			// Se intenta eliminar una notificacion con una sesion invalida
			Server.getInstance().deleteNotification(-15, not);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta eliminar una notificacion con una sesion invalida
			Server.getInstance().deleteNotificationFromUser(-15, not);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			session = Server.getInstance().login("emp2", "emp2");
			Server.getInstance().setCurrentProject(session.getId(), project.getId());
			List<Notification> nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 0);
			Server.getInstance().createNotification(session.getId(), not);
			nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
			// Se elimina una notificación
			Server.getInstance().deleteNotification(session.getId(), not);
			nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 0);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			Server.getInstance().createNotification(session.getId(), not);
			not.getUsers().add(chief);
			Server.getInstance().modifyNotification(session.getId(), not);
			List<Notification> nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
			// Se elimina una notificación
			Server.getInstance().deleteNotificationFromUser(session.getId(), not);
			// Se ha lanzado el trigger de la base de datos, por lo que ya no existe esta notificacion
			nots = Server.getInstance().getNotificationsProject(session.getId());
			assertTrue(nots.size() == 0);
		} catch(Exception e) {
			fail(e.toString());
		}
	}
}
