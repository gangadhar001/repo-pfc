package test.control;

import java.util.ArrayList;
import java.util.Vector;

import persistence.DAOAddress;
import persistence.DAOCompany;
import persistence.DAOUser;

import model.business.control.ClientsController;
import model.business.control.Server;
import model.business.knowledge.Address;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.ISession;
import model.business.knowledge.Operation;
import model.business.knowledge.Session;
import model.business.knowledge.User;
import model.business.knowledge.UserRole;

import communication.ExportedServer;
import exceptions.IncorrectEmployeeException;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

import test.communication.ClientePrueba;
import test.communication.PruebasBase;
import test.utils.IDatosPruebas;


/**
 * Pruebas del Gestor de Sesiones.
 */
public class PruebasSesiones extends PruebasBase {
	
	private Server servidor;
	private User employee;
	private User chief;
	private Company company;
	private Address address;
	private ClientePrueba cliente1;
	
	protected void setUp() {
		try {
			// Preparamos la base de datos
			super.setUp();
			// Obtenemos el servidor frontend, que se utilizará para llamar
			// a los métodos del gestor y así probar las dos clases a la vez
			servidor = Server.getInstance();
			// Creamos objetos de prueba
			address = new Address("street", "city", "country", "zip");
			company = new Company("456as", "company", "information", address);
			employee = new Employee("12345678L", "emp1", "emp1", "User", "emp", "", "", 2, company);
			chief = new ChiefProject("65413987L", "emp2", "emp2", "User", "chief", "", "", 12, company);
			DAOAddress.insert(address);
			DAOCompany.insert(company);
			DAOUser.insert(employee);
			DAOUser.insert(chief);
			cliente1 = new ClientePrueba();
			cliente1.activate(IDatosPruebas.IP_ESCUCHA_CLIENTES);
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
	
	/** Pruebas de la operación que inicia una nueva sesión */
	public void testIdentificar() {
		ISession sesion = null, sesion2;
		
		try {
			// Intentamos identificarnos en el sistema con un usuario inexistente
			servidor.login("administrador", "admin");
			fail("Se esperaba una excepción IncorrectEmployeeException");
		} catch(IncorrectEmployeeException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectEmployeeException");
		}

		try {
			// Intentamos identificarnos en el sistema con una contraseña errónea
			servidor.login("emp1", "nniimmddaa");
			fail("Se esperaba una excepción IncorrectEmployeeException");
		} catch(IncorrectEmployeeException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectEmployeeException");
		}

		try {
			// Nos identificamos en el sistema con un usuario correcto
			// y comprobamos que la sesión tiene el rol correcto
			sesion = servidor.login("emp1", "emp1");
			assertNotNull(sesion);
			assertEquals(UserRole.Employee.ordinal(), sesion.getRole());
		} catch(Exception e) {
			fail("No se esperaba ninguna excepcion " + e.getMessage());
		}
		
		try {
			// Nos identificamos en el sistema con un usuario correcto
			sesion = servidor.login("emp2", "emp2");
			assertNotNull(sesion);
			assertEquals(UserRole.ChiefProject.ordinal(), sesion.getRole());
			// Esta espera evita que la prueba falle de vez en cuando,
			// probablemente porque no da tiempo a almacenarse la sesión
			Thread.sleep(50);
			// Iniciamos una nueva sesión con el mismo usuario
			sesion2 = servidor.login("emp2", "emp2");
			assertNotNull(sesion);
			assertTrue(sesion.getId() != sesion2.getId());
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Intentamos realizar una operación con la sesión inicial
			servidor.getAnswers(sesion.getId());
			fail("Se esperaba una excepción NotLoggedException");
		} catch(NotLoggedException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NotLoggedException");
		}
	}
	
	/** Pruebas de las operaciones que registran y liberar clientes en el sistema */
	public void testRegistrarLiberar() {
		ISession sesion = null;
	
		try {
			// Intentamos registrar un cliente con una sesión no válida
			servidor.register(-12345, cliente1);
			fail("Se esperaba una excepción NotLoggedException");
		} catch(NotLoggedException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NotLoggedException");
		}

		try {
			// Iniciamos una sesión como administrador y registramos un cliente
			sesion = servidor.login("emp2", "emp2");
			servidor.register(sesion.getId(), cliente1);
			assertNotNull(ClientsController.getClient(sesion.getId()));
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Volvemos a iniciar sesión como adminisrador para
			// ver que se cierra la sesión anterior
			sesion = servidor.login("emp2", "emp2");
			Thread.sleep(100);
			assertTrue(cliente1.isLlamadoCerrarSesion());
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Intentamos liberar un cliente con una sesión no válida
			servidor.signout(-12345);
			fail("Se esperaba una excepción NotLoggedException");
		} catch(NotLoggedException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NotLoggedException");
		}

		try {
			// Liberamos el cliente registrado del sistema
			servidor.signout(sesion.getId());
			assertNull(ClientsController.getClient(sesion.getId()));
		} catch(Exception e) {
			fail(e.toString());
		}
	}

	/** Pruebas de la operación que consulta las operaciones disponibles */
	public void testOperacionesDisponibles() {
		ArrayList<Operation> operaciones;
		ISession sesion;
		
		try {
			// Intentamos obtener las operaciones disponibles de una sesión no válida
			servidor.getAvailableOperations(-1278);
			fail("Se esperaba una excepción NotLoggedException");
		} catch(NotLoggedException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción NotLoggedException");
		}
		
		try {
			// Iniciamos una sesión como administrador
			sesion = servidor.login("emp1", "emp1");
			// Obtenemos las operaciones disponibles
			operaciones = servidor.getAvailableOperations(sesion.getId());
			assertNotNull(operaciones);
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	/** Pruebas de las operaciones de la clase Sesion */
	public void testClaseSesion() {
		Session sesion, sesionB;
		
		try {
			// Comprobamos que el método equals funciona bien
			sesion = new Session(100, employee);
			sesionB = new Session(100, employee);
			assertTrue(sesion.equals(sesionB));
			sesionB.setUser(chief);
			assertFalse(sesion.equals(sesionB));
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
}
