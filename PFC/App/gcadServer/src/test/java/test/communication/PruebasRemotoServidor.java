package test.communication;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import persistence.DAOAddress;
import persistence.DAOAnswer;
import persistence.DAOCompany;
import persistence.DAONotification;
import persistence.DAOProposal;
import persistence.DAOTopic;
import persistence.DAOUser;

import model.business.knowledge.Address;
import model.business.knowledge.Answer;
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

import communication.CommunicationsUtilities;
import communication.ExportedServer;
/**
 * Pruebas del objeto remoto exportado por el servidor front-end para
 * conectarse con la base de datos y la ventana de estado.
 */
public class PruebasRemotoServidor extends PruebasBase {
	
	private ExportedServer conexion;
	private User employee;
	private User chief;
	private Company company;
	private Address address;
	
	
	public void setUp() {
		try {
			// Limpiamos la base de datos
			super.setUp();
			// Creamos el objeto remoto exportado por el servidor front-end
			conexion = ExportedServer.getServer();
			// Creamos un usuario
			address = new Address("street", "city", "country", "zip");
			company = new Company("456as", "company", "information", address);
			employee = new Employee("12345678L", "emp1", "emp1", "User", "emp", "", "", 2, company);
			chief = new ChiefProject("65413987L", "emp2", "emp2", "User", "chief", "", "", 12, company);
			DAOAddress.insert(address);
			DAOCompany.insert(company);
			DAOUser.insert(employee);
			DAOUser.insert(chief);
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void tearDown() {
		try {
			// Cerramos la base de datos
			super.tearDown();
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	/** Pruebas de las operaciones */	
	public void testOperaciones() {		
		ISession sessionEmployee = null;
		ISession sessionChief = null;
		ClientePrueba cliente;
		Project project = null;
		Proposal pro;
		Answer ans;
		Topic topic;
		Notification not;
		
		// Todas las operaciones de la clase RemotoServidorFrontend se redirigen
		// a la clase ServidorFrontend, la cual se prueba con los casos de prueba
		// de los gestores; por eso aquí no se contempla más que algunos ejemplos
		
		try {
			// Probamos las operaciones de gestión de sesiones
			sessionEmployee = conexion.login(employee.getLogin(), employee.getPassword());
			sessionChief = conexion.login(chief.getLogin(), chief.getPassword());
			cliente = new ClientePrueba();
			cliente.activate(CommunicationsUtilities.getHostIP());
			conexion.register(sessionChief.getId(), cliente);
			conexion.signout(sessionChief.getId());
			sessionChief = conexion.login(chief.getLogin(), chief.getPassword());
		} catch(Exception e) {
			fail(e.toString());
		}

		try {
			// Probamos las operaciones de gestión de proyectos
			project = new Project("project", "desc", new Date(), new Date(), 203.36, 458, "bank", "java", 557);
			conexion.createProject(sessionChief.getId(), project);
			Set<Project> projects = new HashSet<Project>();
			projects.add(project);
			chief.setProjects(projects);
			employee.setProjects(projects);
			DAOUser.update(chief);
			DAOUser.update(employee);			
			assertEquals(chief, DAOUser.queryUser(chief.getLogin(), chief.getPassword()));
			assertEquals(employee, DAOUser.queryUser(employee.getLogin(), employee.getPassword()));
			conexion.setCurrentProject(sessionChief.getId(), project.getId());
			conexion.setCurrentProject(sessionEmployee.getId(), project.getId());
			// Refresh sessions with new projects of users
			conexion.signout(sessionChief.getId());
			conexion.signout(sessionEmployee.getId());
			sessionEmployee = conexion.login(employee.getLogin(), employee.getPassword());
			sessionChief = conexion.login(chief.getLogin(), chief.getPassword());
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Probamos las operaciones de gestión de conocimiento
			// Crear topic, proposal y answer
			pro = new Proposal("pro", "desc", new Date(), Categories.Analysis);
			ans = new Answer("ans", "desc", new Date(), "Pro");
			topic = new Topic("pro", "desc", new Date());	
			conexion.setCurrentProject(sessionChief.getId(), project.getId());
			conexion.addTopic(sessionChief.getId(), topic);
			conexion.addProposal(sessionEmployee.getId(), pro, topic);
			conexion.addAnswer(sessionEmployee.getId(), ans, pro);
			// Modificar topic, proposal y answer
			Topic newTopic = (Topic) topic.clone();
			Proposal newPro = (Proposal) pro.clone();
			Answer newAns = (Answer) ans.clone();
			newPro.setDescription("newDesc");
			newAns.setDescription("newDesc");
			newTopic.setDescription("newDesc");
			conexion.modifyTopic(sessionChief.getId(), newTopic, topic);
			conexion.modifyProposal(sessionEmployee.getId(), newPro, pro, topic);
			conexion.modifyAnswer(sessionEmployee.getId(), newAns, ans, pro);
			assertEquals(newTopic, DAOTopic.queryTopic(newTopic.getId()));
			assertEquals(newPro, DAOProposal.queryProposal(newPro.getId()));
			assertEquals(newAns, DAOAnswer.queryAnswer(newAns.getId()));
			// Eliminar
			conexion.deleteAnswer(sessionChief.getId(), newAns);
			conexion.deleteProposal(sessionChief.getId(), newPro);
			conexion.deleteTopic(sessionChief.getId(), newTopic);
		} catch(Exception e) {
			fail(e.toString());
		}		
		
		try {
			// TODO: llamarlo desde la conexion
			// Probamos las operaciones de gestión de notificaciones
			topic = new Topic("pro", "desc", new Date());
			conexion.addTopic(sessionChief.getId(), topic);
			conexion.setCurrentProject(sessionChief.getId(), project.getId());
			Set<User> users = new HashSet<User>();			
			// Crear notificación			
			not = new Notification(topic, "Unread", project, "subject", users);
			DAONotification.insert(not);
			assertEquals(conexion.getNotificationsProject(sessionChief.getId()).size(), 1);
			assertEquals(not, conexion.getNotificationsProject(sessionChief.getId()).get(0));			
			not.getUsers().add(employee);
			not.getUsers().add(chief);
			DAONotification.update(not);
			assertEquals(conexion.getNotificationsProject(sessionChief.getId()).get(0), not);
			// Modificar notificacion
			DAONotification.updateState(not, chief.getId());
			String state = conexion.getNotificationsProject(sessionChief.getId()).get(0).getState();
			assertEquals(state, not.getState());
			// Eliminar
			conexion.deleteNotification(sessionChief.getId(), not);
		} catch(Exception e) {
			fail(e.toString());
		}		
		
		try {
			// Probamos a duplicar la compañia
			DAOCompany.insert(company);
			fail("Se esperaba SQLException");
		} catch(SQLException e) {
		} catch(Exception e) {
			fail("Se esperaba SQLException");
		}
		
	}

}
