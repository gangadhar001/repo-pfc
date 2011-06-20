package test.persistence;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import exceptions.IncorrectAddressException;
import exceptions.IncorrectCompanyException;
import exceptions.IncorrectEmployeeException;

import persistence.DAOAddress;
import persistence.DAOCompany;
import persistence.DAOLog;
import persistence.DAOProject;
import persistence.DAOUser;
import persistence.utils.HibernateSessionFactory;

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

import test.communication.PruebasBase;

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
	private Notification not;
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
			users.add(chief);
			users.add(employee);
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
		} catch(IncorrectAddressException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectAddressException");
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
		} catch(IncorrectAddressException e) {
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
	public void testComapny() {
		try {
			// Intentamos buscar una direccion inexistente
			DAOCompany.queryCompany(0);
			fail("Se esperaba una excepción IncorrectCompanyException");
		} catch(IncorrectCompanyException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectCompanyException");
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
			// Comprobamos si los cambios han tenido efecto
			DAOCompany.queryCompany(company.getId());
			fail("Se esperaba una excepción IncorrectCompanyException");
			Company comp = DAOCompany.queryCompany(company.getId());
			assertTrue(chief.getCompany() == null);
		} catch(IncorrectCompanyException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectCompanyException");
		}			
	}
	
}
