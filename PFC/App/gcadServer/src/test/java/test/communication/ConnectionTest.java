package test.communication;
import java.sql.SQLException;
import java.util.List;


import model.business.control.DBLogManager;
import model.business.control.LogManager;
import model.business.control.WindowsLogManager;
import model.business.knowledge.Address;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.IMessageTypeLog;
import model.business.knowledge.LogEntry;
import model.business.knowledge.User;

import org.uispec4j.UISpecTestCase;

import persistence.utils.HibernateQuery;
import presentation.JFServer;

import communication.DBConnection;
import communication.DBConnectionManager;


/**
 * Pruebas de los Gestores de conexiones de base de datos y de estado del
 * servidor.
 */
public class ConnectionTest extends UISpecTestCase {

	private DBConnection conexionBD;
	private DBLogManager conexionLogBD;
	private WindowsLogManager conexionLogVentana;
	private JFServer ventana;
	
	protected void setUp() {
		try {
			super.setUp();
			// Inicializamos las conexiones con las bases de datos
			// y las ventanas de estado de los servidores
			conexionBD = new DBConnection();
			conexionLogBD = new DBLogManager();
			conexionLogVentana = new WindowsLogManager();
			ventana = new JFServer(null);
			conexionLogVentana.putWindow(ventana);
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	protected void tearDown() {
		// No es necesario ningún código de finalización
	}
	
	/** Pruebas de las conexiones con las bases de datos */
	public void testConexionBD() {
		User employee, emp;
		Address address;
		Company company;
		
		try {
			// Intentamos ejecutar un comando sin ninguna base de datos configurada
			DBConnectionManager.clear();
			DBConnectionManager.query(new HibernateQuery("FROM User"));
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
			assertEquals("No hay ninguna conexión con la base de datos", e.getMessage());
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}
		
		try {
			// Intentamos ejecutar un comando sin ninguna base de datos configurada
			DBConnectionManager.clear();
			DBConnectionManager.query("SELECT * FROM Users");
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
			assertEquals("No hay ninguna conexión con la base de datos", e.getMessage());
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}

		try {
			// Intentamos inicializar la transaccion sin ninguna base de datos configurada
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(new Address("street", "city", "country", "zip", "code"));
			DBConnectionManager.finishTransaction();
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
			assertEquals("No hay ninguna conexión con la base de datos", e.getMessage());
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}
		
		try {
			// Intentamos insertar un objeto sin ninguna base de datos configurada
			DBConnectionManager.insert(new Address("street", "city", "country", "zip", "code"));
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
			assertEquals("No hay ninguna conexión con la base de datos", e.getMessage());
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}
		
		try {
			// Intentamos finalizar la transaccion sin ninguna base de datos configurada
			DBConnectionManager.finishTransaction();
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
			assertEquals("No hay ninguna conexión con la base de datos", e.getMessage());
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}
		

		try {
			// Intentamos actualizar un objeto sin ninguna base de datos configurada
			DBConnectionManager.update(new Address("street", "city", "country", "zip", "code"));
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
			assertEquals("No hay ninguna conexión con la base de datos", e.getMessage());
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}
		
		try {
			// Intentamos actualizar un objeto sin ninguna base de datos configurada
			DBConnectionManager.executeUpdate("UPDATE FROM Users");
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
			assertEquals("No hay ninguna conexión con la base de datos", e.getMessage());
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}
		

		try {
			// Intentamos eliminar un objeto sin ninguna base de datos configurada
			DBConnectionManager.delete(new Address("street", "city", "country", "zip", "code"));
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
			assertEquals("No hay ninguna conexión con la base de datos", e.getMessage());
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}
		
		try {
			// Intentamos eliminar la cache sin ninguna base de datos configurada
			DBConnectionManager.clearCache(new Address("street", "city", "country", "zip", "code"));
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
			assertEquals("No hay ninguna conexión con la base de datos", e.getMessage());
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}

		try {
			// Configuramos y borramos la base de datos principal
			DBConnectionManager.clear();
			DBConnectionManager.addConnection(conexionBD);
			borrarBaseDatos();
			// Ejecutamos varias operaciones sobre la base de datos
			address = new Address("street", "city", "country", "zip", "code");
			company = new Company("456as", "company", "information", address);
			employee = new Employee("12345678L", "emp1", "emp1", "User", "emp", "", "", 2, company);
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(address);
			DBConnectionManager.finishTransaction();
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(company);
			DBConnectionManager.finishTransaction();
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(employee);
			DBConnectionManager.finishTransaction();		
			employee.setSurname("Surname");
			DBConnectionManager.initTransaction();
			DBConnectionManager.update(employee);
			DBConnectionManager.finishTransaction();
			emp = (User)DBConnectionManager.query(new HibernateQuery("FROM User WHERE nif='12345678L'")).get(0);
			assertEquals(emp.getSurname(), "Surname");
			DBConnectionManager.clearCache(employee);
			DBConnectionManager.clearCache(emp);
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	/** Pruebas de las conexiones para actualizar el estado del servidor */
	public void testConexionLog() {
		List<?> datos;
		String[] lineas;
		
		try {
			// Configuramos el almacenamiento de los mensajes en la base de datos
			LogManager.clearConnections();
			LogManager.putConnection(conexionLogBD);
			DBConnectionManager.clear();
			DBConnectionManager.addConnection(conexionBD);
			borrarBaseDatos();
			// Comprobamos que ahora no hay ningún mensaje en la BD
			assertTrue(DBConnectionManager.query(new HibernateQuery("FROM LogEntry")).size() == 0);
			// Generamos nuevos mensajes y cambiamos los clientes a la escucha
			LogManager.putMessage(null, IMessageTypeLog.INFO, "Mensaje de prueba");
			LogManager.putMessage(IMessageTypeLog.UPDATE, "Otro mensaje de prueba");
			LogManager.updateConnectedClients(3);
			// Comprobamos que ahora hay dos mensajes en la BD
			datos = DBConnectionManager.query(new HibernateQuery("FROM LogEntry"));
			assertTrue(datos.size() == 2);
			assertTrue(((LogEntry)datos.get(0)).getMessage().equals("Mensaje de prueba") || ((LogEntry)datos.get(0)).getMessage().equals("Otro mensaje de prueba"));
			assertTrue(((LogEntry)datos.get(1)).getMessage().equals("Mensaje de prueba") || ((LogEntry)datos.get(1)).getMessage().equals("Otro mensaje de prueba"));
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Configuramos la visualización de los mensajes en el servidor front-end
			LogManager.clearConnections();
			LogManager.putConnection(conexionLogVentana);
			// Obtenemos los mensajes actuales de la ventana
			assertEquals(ventana.getMessages(), "");
			assertTrue(ventana.getClientesEscuchando() == 0);
			// Generamos nuevos mensajes y cambiamos los clientes a la escucha
			LogManager.putMessage("prueba", IMessageTypeLog.INFO, "Mensaje de prueba");
			LogManager.putMessage(IMessageTypeLog.UPDATE, "Otro mensaje de prueba");
			LogManager.updateConnectedClients(3);
			// Comprobamos que la ventana se ha actualizado
			lineas = ventana.getMessages().split("\n");
			assertTrue(lineas[0].endsWith("Mensaje de prueba"));
			assertTrue(lineas[1].endsWith("Otro mensaje de prueba"));
			assertTrue(ventana.getClientesEscuchando() == 3);
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	private void borrarBaseDatos() {
		HibernateQuery consulta;
		List<?> datos;		
		try {
			for(String clase : new String[] { "Project", "LogEntry", "Company", "Address", "User"}) {
				consulta = new HibernateQuery("FROM " + clase);
				datos = DBConnectionManager.query(consulta);
				for(Object objeto : datos) {					
					DBConnectionManager.initTransaction();					
					DBConnectionManager.clearCache(objeto);
					DBConnectionManager.delete(objeto);
					DBConnectionManager.finishTransaction();
				}
			}
		} catch(Exception e) {
			fail(e.toString());
		}
	}

}
