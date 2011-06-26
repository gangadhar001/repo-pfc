package test.persistence;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.IMessageTypeLog;
import model.business.knowledge.LogEntry;
import model.business.knowledge.Notification;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.User;
import persistence.DAOAddress;
import persistence.DAOAnswer;
import persistence.DAOCompany;
import persistence.DAOLog;
import persistence.DAONotification;
import persistence.DAOProject;
import persistence.DAOProposal;
import persistence.DAOTopic;
import persistence.DAOUser;
import test.communication.PruebasBase;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentAddressException;
import exceptions.NonExistentAnswerException;
import exceptions.NonExistentCompanyException;
import exceptions.NonExistentNotificationException;
import exceptions.NonExistentProjectException;
import exceptions.NonExistentProposalException;
import exceptions.NonExistentTopicException;

/**
 * Pruebas de las clases de persistencia.
 */
public class PruebasPersistencia extends PruebasBase {
	
	private Address address;
	private Employee employee;
	private Company company;
	private ChiefProject chief;
	private Project project, project2;
	private Proposal pro;
	private Topic topic;
	private Answer ans;
	private Set<User> users;
	private Notification not, not2;
	private HashSet<Project> projects;
	private User emp;
	private LogEntry logEntry1, logEntry2, logEntry3;

	@SuppressWarnings("deprecation")
	protected void setUp() {
		try {
			// Preparamos la base de datos
			super.setUp();
			// Creamos objetos de prueba
			address = new Address("street", "city", "country", "zip");
			company = new Company("456as", "company", "information", address);
			employee = new Employee("12345678L", "emp1", "emp1", "User1", "emp1", "", "", 2, company);
			chief = new ChiefProject("65413987L", "emp3", "emp3", "User3", "chief1", "", "", 12, company);
			project = new Project("project", "desc1", new Date(), new Date(), 203.36, 458, "bank", "java", 557);
			project2 = new Project("project2", "desc2", new Date(), new Date(), 53683.36, 45128, "bank", "C#", 5571);
			projects = new HashSet<Project>();
			pro = new Proposal("pro", "desc", new Date(), Categories.Analysis);
			ans = new Answer("ans", "desc", new Date(), "Pro");
			topic = new Topic("pro", "desc", new Date());
			users = new HashSet<User>();
			topic.setUser(chief);
			topic.setProject(project);
			pro.setUser(chief);
			ans.setUser(chief);
			not = new Notification(topic, "Unread", project, "subject", users);
			logEntry1 = new LogEntry(employee.getLogin(), new Timestamp(109, 11, 1, 10, 10, 10, 0), IMessageTypeLog.CREATE, "Entrada CREATE.");
			logEntry2 = new LogEntry(null, new Timestamp(109, 5, 25, 7, 30, 0, 0), IMessageTypeLog.READ, "Entrada READ.");
			logEntry3 = new LogEntry(chief.getLogin(), new Timestamp(109, 9, 4, 8, 0, 0, 0), "message", "Entrada UPDATE.");
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
	
	/** Pruebas de la tabla de usuarios */
	public void testUsers() {
		List<User> users;
		
		try {
			// Intentamos buscar un usuario inexistente
			DAOUser.queryUser("log", "log");
			fail("Se esperaba una excepción IncorrectEmployeeException");
		} catch(IncorrectEmployeeException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectEmployeeException");
		}
		
		try {
			// Intentamos actualizar un usuario inexistente
			DAOUser.update(chief);
			fail("Se esperaba una excepción IncorrectEmployeeException");
		} catch(IncorrectEmployeeException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectEmployeeException");
		}
		
		try {
			// Añadimos los usuarios
			DAOAddress.insert(address);
			DAOCompany.insert(company);
			DAOUser.insert(employee);
			DAOUser.insert(chief);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Modificamos un beneficiario
			employee.setSurname("V. L.");
			employee.setEmail("nuevocorreo@terra.com");
			employee.setName("John");
			employee.setTelephone("612312333");
			employee.setSeniority(12);
			DAOUser.update(employee);
			// Comprobamos si los cambios han tenido efecto
			emp = DAOUser.queryUser(employee.getLogin(), employee.getPassword());
			assertEquals(employee, emp);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Obtenemos la lista de usuarios
			users = DAOUser.getUsers();
			assertTrue(users.size() == 2);
			assertTrue((users.get(0).equals(employee) && users.get(1).equals(chief)) 
			           || (users.get(1).equals(employee) && users.get(0).equals(chief))); 
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Añadimos el usuario a proyectos
			DAOProject.insert(project);
			DAOProject.insert(project2);
			projects.add(project);
			employee.setProjects(projects);
			Set<Project> pr = employee.getProjects();
			pr.add(project2);
			employee.setProjects(pr);
			DAOUser.update(employee);
			emp = DAOUser.queryUser(employee.getLogin(), employee.getPassword());
			assertTrue(pr.size() == 2);
			assertEquals(employee.getProjects().size(), pr.size());
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Obtenemos la lista de usuarios de un proyecto
			users = DAOUser.getUsersProject(project);
			assertTrue(users.size() == 1);
			assertTrue(users.get(0).equals(employee)); 
		} catch(Exception e) {
			fail(e.toString());
		}		
		
		try {
			// Eliminamos un usuario
			DAOUser.delete(employee);
			// Comprobamos si los cambios han tenido efecto
			DAOUser.queryUser(employee.getLogin(), employee.getPassword());
			fail("Se esperaba una excepción IncorrectEmployeeException");
		} catch(IncorrectEmployeeException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectEmployeeException");
		}
		
		try {
			// Comprobamos que los usuarios no borrados siguen existiendo
			emp = DAOUser.queryUser(chief.getLogin(), chief.getPassword());
			assertEquals(chief, emp);			
		} catch(Exception e) {
			fail(e.toString());
		}		

		try {
			// Intentamos insertar un usuario con un NIF existente
			chief.setNif(employee.getNif());
			DAOUser.insert(chief);
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}		
	}

	/** Pruebas de la tabla de direccion */
	public void testAddress() {
		try {
			// Intentamos buscar una direccion inexistente
			DAOAddress.queryAddress(0);
			fail("Se esperaba una excepción IncorrectAddressException");
		} catch(NonExistentAddressException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectAddressException");
		}
		
		try {
			// Intentamos actualizar una dirección inexistente
			DAOAddress.update(address);
			fail("Se esperaba una excepción NonExistentAddressException");
		} catch(NonExistentAddressException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentAddressException");
		}
		
		try {
			// Añadimos la direccion
			DAOAddress.insert(address);
			DAOCompany.insert(company);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Modificamos la direccion
			address.setCity("L.A");
			address.setZip("13170");
			address.setCountry("Spain");
			address.setStreet("street");
			DAOAddress.update(address);
			// Comprobamos si los cambios han tenido efecto
			Address add = DAOAddress.queryAddress(address.getId());
			assertEquals(address, add);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Eliminamos una direccion
			DAOAddress.delete(address);
			// Comprobamos si los cambios han tenido efecto
			DAOAddress.queryAddress(address.getId());
			fail("Se esperaba una excepción IncorrectAddressException");
			Company comp = DAOCompany.queryCompany(company.getId());
			assertTrue(comp.getAddress() == null);
		} catch(NonExistentAddressException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectAddressException");
		}			
	}
		
	/** Pruebas de la tabla de entradas del log */
	public void testEntradasLog() {
		List<LogEntry> log;
		
		try {
			// Consultamos las entradas sin haber ninguna en
			// el log para ver si se devuelve una lista vacía
			log = DAOLog.queryLog();
			assertTrue(log != null && log.size() == 0);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Insertamos varios usuarios
			DAOAddress.insert(address);
			DAOCompany.insert(company);
			DAOUser.insert(employee);
			DAOUser.insert(chief);
			// Insertamos nuevas entradas válidas
			// (y repetidas, que se permite)
			DAOLog.insert(logEntry1);
			DAOLog.insert(logEntry2);
			DAOLog.insert((LogEntry)logEntry1.clone());
		} catch(Exception e) {
			fail(e.toString());
		}

		try {
			// Comprobamos que las entradas se hayan añadido bien
			log = DAOLog.queryLog();
			assertTrue(log.size() == 3);
			assertTrue((log.get(0).equals(logEntry1) && log.get(1).equals(logEntry2)
			           || (log.get(0).equals(logEntry2) && log.get(1).equals(logEntry1))));
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Intentamos insertar una entrada con un tipo
			// de mensaje o acción no permitido
			DAOLog.insert(logEntry3);
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}
	}
	
	/** Pruebas de la tabla de compañias */
	public void testCompany() {
		try {
			// Intentamos buscar una direccion inexistente
			DAOCompany.queryCompany(0);
			fail("Se esperaba una excepción IncorrectCompanyException");
		} catch(NonExistentCompanyException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectCompanyException");
		}
		
		try {
			// Intentamos actualizar una compañia inexistente
			DAOCompany.update(company);
			fail("Se esperaba una excepción NonExistentCompanyException");
		} catch(NonExistentCompanyException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentCompanyException");
		}
		
		try {
			// Añadimos una compañia
			DAOAddress.insert(address);
			DAOCompany.insert(company);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Modificamos la compañia
			company.setName("L.A");
			company.setCif("13170");
			company.setInformation("L.A in Spain");
			DAOCompany.update(company);
			// Comprobamos si los cambios han tenido efecto
			Company comp = DAOCompany.queryCompany(company.getId());
			assertEquals(company, comp);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Eliminamos una compañia
			// Primero, se inserta un usuario para comprobar que luego su compañia es nula
			DAOUser.insert(chief);
			// Se elimina la compañia
			DAOCompany.delete(company);
			DAOCompany.queryCompany(company.getId());
			fail("Se esperaba una excepción IncorrectCompanyException");			
		} catch(NonExistentCompanyException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectCompanyException");
		}			
	}
	
	/** Pruebas de la tablas de conocimiento */
	public void testKnowledge() {
		try {
			// Intentamos buscar una respuesta inexistente
			DAOAnswer.queryAnswer(0);
			fail("Se esperaba una excepción NonExistentAnswers");
		} catch(NonExistentAnswerException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentAnswers");
		}
		
		try {
			// Intentamos buscar una propuesta inexistente
			DAOProposal.queryProposal(0);
			fail("Se esperaba una excepción NonExistentProposal");
		} catch(NonExistentProposalException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentProposal");
		}
		
		try {
			// Intentamos buscar un topic inexistente
			DAOTopic.queryTopic(0);
			fail("Se esperaba una excepción NonExistentTopic");
		} catch(NonExistentTopicException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentTopic");
		}
		
		try {
			// Intentamos actualizar una respuesta inexistente
			DAOAnswer.update(ans);
			fail("Se esperaba una excepción NonExistentAnswers");
		} catch(NonExistentAnswerException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentAnswers");
		}
		
		try {
			// Intentamos actualizar una propuesta inexistente
			DAOProposal.update(pro);
			fail("Se esperaba una excepción NonExistentProposal");
		} catch(NonExistentProposalException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentProposal");
		}
		
		try {
			// Intentamos actualizar un topic inexistente
			DAOTopic.update(topic);
			fail("Se esperaba una excepción NonExistentTopic");
		} catch(NonExistentTopicException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentTopic");
		}
		
		try {
			DAOProject.insert(project);
			DAOAddress.insert(address);
			DAOCompany.insert(company);
			DAOUser.insert(chief);
			// Añadimos un topic, una propuesta, y una respuesta
			DAOTopic.insert(topic);
			DAOProposal.insert(pro, topic.getId());
			DAOAnswer.insert(ans, pro.getId());
		} catch(Exception e) {
			fail(e.toString());
		}
		
		// Prueba para comprobar el numero de topics en un proyecto
		try {
			List<Topic> topics = DAOTopic.queryTopicsProject(project.getId());
			assertTrue(topics.size() == 1);
			assertEquals(topics.get(0), topic);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Modificamos un topic, una propuesta, y una respuesta
			Topic newTopic = (Topic) topic.clone();
			Proposal newPro = (Proposal) pro.clone();
			Answer newAns = (Answer) ans.clone();
			newPro.setDescription("newDesc");
			newAns.setDescription("newDesc");
			newTopic.setDescription("newDesc");
			DAOTopic.update(newTopic);
			DAOProposal.update(newPro);
			DAOAnswer.update(newAns);
			assertEquals(newTopic, DAOTopic.queryTopic(newTopic.getId()));
			assertEquals(newPro, DAOProposal.queryProposal(newPro.getId()));
			assertEquals(newAns, DAOAnswer.queryAnswer(newAns.getId()));
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Eliminar
			DAOAnswer.delete(ans);
			DAOProposal.delete(pro);
			DAOTopic.delete(topic);
			DAOTopic.queryTopicsProject(project.getId());
			fail("Se esperaba una excepción NonExistentTopic");
		} catch(NonExistentTopicException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentTopic");
		}		
		
		// Comprobamos que se han eliminado correctamente
		try {
			DAOAnswer.queryAnswer(0);
			fail("Se esperaba una excepción NonExistentAnswers");
		} catch(NonExistentAnswerException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentAnswers");
		}
		
		try {
			DAOProposal.queryProposal(0);
			fail("Se esperaba una excepción NonExistentProposal");
		} catch(NonExistentProposalException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentProposal");
		}
		
		try {
			DAOTopic.queryTopic(0);
			fail("Se esperaba una excepción NonExistentTopic");
		} catch(NonExistentTopicException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentTopic");
		}
		
		// Prueba a insertar una propuesta en un topic inexistente
		try {
			DAOProposal.insert(pro, topic.getId());
			fail("Se esperaba una excepción NonExistentTopic");
		} catch(NonExistentTopicException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentTopic");
		}
		
		// Prueba a insertar una respuesta en una propuesta inexistente
		try {
			DAOAnswer.insert(ans, pro.getId());
			fail("Se esperaba una excepción NonExistentProposal");
		} catch(NonExistentProposalException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentProposal");
		}
	}
	
	/** Pruebas de la tabla de proyectos */
	public void testProjects() {
		try {
			// Intentamos buscar un proyecto inexistente
			DAOProject.queryProject(0);
			fail("Se esperaba una excepción NonExistentProjectException");
		} catch(NonExistentProjectException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentProjectException");
		}
	
		try {
			// Intentamos actualizar un usuario inexistente
			DAOProject.update(project);
			fail("Se esperaba una excepción NonExistentProjectException");
		} catch(NonExistentProjectException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentProjectException");
		}
		
		try {
			// Insertamos un proyecto
			DAOProject.insert(project);
			List<Project> projects = DAOProject.getProjects();
			assertTrue(projects.size() == 1);
			assertEquals(projects.get(0), project);
			Project p = DAOProject.queryProject(project.getId());
			assertEquals(p, project);
		} catch(Exception e) {
			fail(e.toString());
		}	
		
		try {
			// Modificamos el proyecto
			Project newPro = (Project) project.clone();
			newPro.setDescription("description");
			newPro.setDomain("defense");
			newPro.setEndDate(new Timestamp(new Date().getTime()));
			newPro.setEstimatedHours(15871);
			newPro.setName("project name");
			DAOProject.update(newPro);
			assertEquals(newPro, DAOProject.queryProject(newPro.getId()));
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Eliminar
			DAOProject.delete(project);
			DAOProject.queryProject(project.getId());
			fail("Se esperaba una excepción NonExistentProjectException");
		} catch(NonExistentProjectException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentProjectException");
		}			
	}
	
	/** Pruebas de la tablas de notificaciones */
	public void testNotifications() {
		// Insertamos objetos necesarios para las pruebas
		try {
			DAOProject.insert(project);
			DAOAddress.insert(address);
			DAOCompany.insert(company);
			DAOUser.insert(chief);
			DAOUser.insert(employee);
			// Añadimos un topic, una propuesta, y una respuesta
			DAOTopic.insert(topic);
		} catch(Exception e) {
			fail(e.toString());
		}	
		
		try {
			// Intentamos actualizar una notificacion inexistente
			DAONotification.update(not);
			fail("Se esperaba una excepción NonExistentNotificationException");
		} catch(NonExistentNotificationException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NonExistentNotificationException");
		}
		
		try {
			// Añadimos la notificacion
			DAONotification.insert(not);
			List<Notification> nots = DAONotification.queryNotificationsProject(project.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
			not.getUsers().add(chief);
			not.getUsers().add(employee);
			DAONotification.update(not);
			nots = DAONotification.queryNotificationsUser(chief.getId(), project.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
			nots = DAONotification.queryNotificationsUser(employee.getId(), project.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Añadimos una nueva notificacion
			Set<User> auxUsers = new HashSet<User>();
			auxUsers.add(employee);
			not2 = new Notification(topic, "Read", project, "subject", auxUsers);
			DAONotification.insert(not2);
			List<Notification> nots = DAONotification.queryNotificationsProject(project.getId());
			assertTrue(nots.size() == 2);
			assertTrue((nots.get(0).equals(not) && nots.get(1).equals(not2)) || (nots.get(0).equals(not2) && nots.get(1).equals(not)));
			nots = DAONotification.queryNotificationsUser(chief.getId(), project.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
			nots = DAONotification.queryNotificationsUser(employee.getId(), project.getId());
			assertTrue(nots.size() == 2);
			assertTrue((nots.get(0).equals(not) && nots.get(1).equals(not2)) || (nots.get(0).equals(not2) && nots.get(1).equals(not)));
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Modificamos el estado de una notificacion
			not.setState("Read");
			DAONotification.updateState(not, chief.getId());
			// Se comprueba que se ha actualizado el estado para ese usuario y no para el otro
			List<Notification> nots = DAONotification.queryNotificationsUser(chief.getId(), project.getId());
			assertEquals (not, nots.get(0));
		    nots = DAONotification.queryNotificationsUser(employee.getId(), project.getId());
			assertTrue((not.equals(nots.get(0)) && !not.equals(nots.get(1))) || (not.equals(nots.get(1)) && !not.equals(nots.get(0))));
		} catch(Exception e) {
			fail(e.toString());
		}		

		try {
			// Eliminar la notificacion de un usuario
			DAONotification.deleteFromUser(not2, employee.getId());
			// Se habra ejecutado el trigger, porque solo exisitia la notificacion para ese usuario, por lo que se debe haber eliminado
			List<Notification> nots = DAONotification.queryNotificationsProject(project.getId());
			assertTrue(nots.size() == 1);
			assertEquals(nots.get(0), not);
		} catch(Exception e) {
			fail(e.toString());
		}	
		
		try {
			// Eliminar la otra notificacion
			DAONotification.delete(not);
			List<Notification> nots = DAONotification.queryNotificationsProject(project.getId());
			assertTrue(nots.size() == 0);		
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
}
