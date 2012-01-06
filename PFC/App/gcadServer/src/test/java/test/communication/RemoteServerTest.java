package test.communication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.CaseEval;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.AnswerArgument;
import model.business.knowledge.Categories;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.File;
import model.business.knowledge.ISession;
import model.business.knowledge.KnowledgeStatus;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.PDFConfiguration;
import model.business.knowledge.PDFSection;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.User;
import persistence.DAOAddress;
import persistence.DAOAnswer;
import persistence.DAOCompany;
import persistence.DAOProposal;
import persistence.DAOTopic;
import persistence.DAOUser;

import communication.CommunicationsUtilities;
import communication.ExportedServer;
/**
 * Pruebas del objeto remoto exportado por el servidor front-end para
 * conectarse con la base de datos y la ventana de estado.
 */
public class RemoteServerTest extends BaseTest {
	
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
			address = new Address("street", "city", "country", "zip", "code");
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
		DummyClient cliente;
		Project project = null;
		Proposal pro;
		Answer ans;
		Topic topic;
		Notification not;
		
		
		try {
			// Probamos las operaciones de gestión de sesiones
			sessionEmployee = conexion.login(employee.getLogin(), employee.getPassword());
			sessionChief = conexion.login(chief.getLogin(), chief.getPassword());
			cliente = new DummyClient();
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
			project.setName("new Name");
			conexion.updateProject(sessionChief.getId(), project);
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
			// Refrescar sesiones
			conexion.signout(sessionChief.getId());
			conexion.signout(sessionEmployee.getId());
			sessionEmployee = conexion.login(employee.getLogin(), employee.getPassword());
			sessionChief = conexion.login(chief.getLogin(), chief.getPassword());
			// Otras operaciones
			conexion.addProjectsUser(sessionChief.getId(), chief, project);			
			assertEquals(conexion.getProjects(sessionChief.getId()).size(), 1);
			assertEquals(conexion.getUsersProject(sessionChief.getId(), project).size(), 2);
			assertEquals(conexion.getProjectsFromCurrentUser(sessionChief.getId()).size(), 1);
			
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Probamos las operaciones de gestión de conocimiento
			// Crear topic, proposal y answer
			pro = new Proposal("pro", "desc", new Date(), Categories.Analysis);
			ans = new Answer("ans", "desc", new Date(), AnswerArgument.Agree.name());
			topic = new Topic("pro", "desc", new Date());	
			byte [] content = new byte[10];
			conexion.setCurrentProject(sessionChief.getId(), project.getId());
			conexion.setCurrentProject(sessionEmployee.getId(), project.getId());
			conexion.addTopic(sessionChief.getId(), topic);
			conexion.addProposal(sessionEmployee.getId(), pro, topic);
			conexion.addAnswer(sessionEmployee.getId(), ans, pro);
			topic.setStatus(KnowledgeStatus.Accepted);
			conexion.changeStatusKnowledge(sessionChief.getId(), topic);
			// Modificar topic, proposal y answer
			Topic newTopic = (Topic) topic.clone();
			Proposal newPro = (Proposal) pro.clone();
			Answer newAns = (Answer) ans.clone();
			newPro.setTitle("newPro");
			newAns.setTitle("newAnswer");
			newTopic.setTitle("newTopic");
			conexion.modifyTopic(sessionChief.getId(), newTopic, topic);
			conexion.modifyProposal(sessionEmployee.getId(), newPro, pro, topic);
			conexion.modifyAnswer(sessionEmployee.getId(), newAns, ans, pro);
			assertEquals(newTopic, DAOTopic.queryTopic(newTopic.getId()));
			assertEquals(newPro, DAOProposal.queryProposal(newPro.getId()));
			assertEquals(newAns, DAOAnswer.queryAnswer(newAns.getId()));
			conexion.attachFile(sessionEmployee.getId(), pro, new File("file", content));
			// Eliminar
			conexion.deleteAnswer(sessionChief.getId(), newAns);
			conexion.deleteProposal(sessionChief.getId(), newPro);
			conexion.deleteTopic(sessionChief.getId(), newTopic);
			assertEquals(conexion.getProposals(sessionChief.getId()).size(), 0);
			assertEquals(conexion.getAnswers(sessionChief.getId()).size(), 0);
			assertEquals(conexion.getTopicsWrapper(sessionChief.getId()).getTopics().size(), 0);
			assertEquals(conexion.getTopicsWrapper(sessionChief.getId(), project).getTopics().size(), 0);
			// Auxiliares
			Proposal p = conexion.findParentAnswer(sessionEmployee.getId(), newAns);
			assertEquals(p, null);
			Topic t = conexion.findParentProposal(sessionEmployee.getId(), newPro);
			assertEquals(t, null);
		} catch(Exception e) {
			fail(e.toString());
		}		
		
		try {
			// Probamos las operaciones de gestión de notificaciones
			assertEquals(conexion.getNotificationsUser(sessionChief.getId()).size(), 3);
			topic = new Topic("pro", "desc", new Date());
			conexion.addTopic(sessionChief.getId(), topic);
			conexion.setCurrentProject(sessionChief.getId(), project.getId());
			Set<User> users = new HashSet<User>();			
			// Crear notificación			
			not = new Notification(topic, "Unread", project, "subject", users);
			conexion.createNotification(sessionChief.getId(), not);
			assertEquals(conexion.getNotificationsProject(sessionChief.getId()).size(), 5);
			assertEquals(not, conexion.getNotificationsProject(sessionChief.getId()).get(4));			
			not.getUsers().add(employee);
			not.getUsers().add(chief);
			conexion.modifyNotification(sessionChief.getId(), not);
			assertEquals(conexion.getNotificationsProject(sessionChief.getId()).get(4), not);
			// Modificar notificacion
			conexion.modifyNotificationState(sessionChief.getId(), not);
			String state = conexion.getNotificationsProject(sessionChief.getId()).get(4).getState();
			assertEquals(state, not.getState());
			// Eliminar
			conexion.deleteNotificationFromUser(sessionChief.getId(), not);
			conexion.deleteNotification(sessionChief.getId(), not);
			conexion.removeProjectsUser(sessionChief.getId(), chief, project);
			assertEquals(conexion.getUsersProject(sessionChief.getId(), project).size(), 1);
			assertEquals(conexion.getProjectsFromCurrentUser(sessionChief.getId()).size(), 0);
		} catch(Exception e) {
			fail(e.toString());
		}		
		
		try {
			// Probamos las operaciones de CBR
			project = new Project("name","description", new Date(), new Date(), 1212.12, 1212, "bank", "java", 12131);
			List<Attribute> at = conexion.getAttributesFromProject(project);
			assertTrue(at.size() > 10);
			List<CaseEval> result = conexion.executeAlgorithm(sessionChief.getId(), EnumAlgorithmCBR.NN, project, new ConfigCBR(), 0);
			assertEquals(result.size(), 1);
		} catch(Exception e) {
			fail(e.toString());
		}	
		
		try {
			// Probamos las operaciones de PDF y exportar
			conexion.exportInformation(sessionChief.getId(), project);
			conexion.composePDF(sessionChief.getId(), new PDFConfiguration(new ArrayList<PDFSection>()), null, null);
			// Excepcion al intentar crear un documento vacio
			fail("Se esperaba Exception");
		} catch (Exception e) { }
		
		try {
			// Probamos las operaciones auxiliares
			ArrayList<Operation> operations = conexion.getAvailableOperations(sessionEmployee.getId());
			assertEquals(operations.size(), 3);
			User u = conexion.getLoggedUser(sessionChief.getId());
			assertEquals(u, chief);
			Address ad = new Address("Mata", "Ciudad Real", "Spain", "13004", "es_ES");
			conexion.getCoordinates(sessionChief.getId(), ad);
			conexion.getCurrentProject(sessionEmployee.getId());
			
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
