package test.business;


import java.rmi.RemoteException;

import model.business.control.Server;
import model.business.control.ServerController;
import model.business.knowledge.Address;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.ISession;
import model.business.knowledge.User;

import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowInterceptor;


import DPMToolServer.Main;
import test.IDatosPruebas;
import test.communication.DummyClient;
import test.communication.BaseTest;

import communication.DBConnection;
import communication.DBConnectionManager;
import communication.ServerConfiguration;

/**
 * Pruebas del controlador principal del servidor front-end.
 */
public class PruebasControlador extends BaseTest {

	private ServerController controlador;
	
	protected void setUp() {
		User employee;
		User chief;
		Company company;
		Address address;
		
		try {
			// Preparamos la base de datos
			super.setUp();
			// Creamos un administrador de prueba
			DBConnectionManager.clear();
			DBConnectionManager.addConnection(new DBConnection());
			address = new Address("street", "city", "country", "zip", "address");
			company = new Company("456as", "company", "information", address);
			employee = new Employee("12345678L", "emp1", "emp1", "User", "emp", "", "", 2, company);
			chief = new ChiefProject("65413987L", "emp2", "emp2", "User", "chief", "", "", 12, company);
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(address);
			DBConnectionManager.insert(company);
			DBConnectionManager.insert(chief);
			DBConnectionManager.insert(employee);
			DBConnectionManager.finishTransaction();
			// Inicializamos el controlador y la ventana de estado
			controlador = new ServerController();
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
	
	/** Pruebas del controlador */
	public void testControlador() {
		ServerConfiguration configuracion = null;
		DummyClient cliente = null;
		ISession sesion;
		
//		try {
//			
//			// Mostramos y ocultamos la ventana principal
//			WindowInterceptor.run(new Trigger() {
//				public void run() {
//					controlador.showServerWindowUI();					
//				}
//			});			
//			 
//			assertTrue(controlador.getWindow().isVisible());
//			controlador.hideServerWindowUI();
//			assertFalse(controlador.getWindow().isVisible());
//		} catch(Exception e) {
//			fail(e.toString());
//		}
		
		try {
			// Activamos el servidor varias veces para ver si no hay fallos
			configuracion = new ServerConfiguration(IDatosPruebas.IP_BASEDATOS_PRINCIPAL, IDatosPruebas.SCHEMA, IDatosPruebas.USER, IDatosPruebas.PASSWORD, IDatosPruebas.PUERTO_BASEDATOS_PRINCIPAL, IDatosPruebas.PUERTO_ESCUCHA);
			controlador.startServer(configuracion);
			controlador.startServer(configuracion);
			assertTrue(controlador.isServerActivate());
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Iniciamos sesión con un cliente
			cliente = new DummyClient();
			cliente.activate(IDatosPruebas.IP_ESCUCHA_CLIENTES);
			sesion = Server.getInstance().login("emp1", "emp1");
			Server.getInstance().register(sesion.getId(), cliente);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Intentamos activar el servidor en un puerto diferente
			configuracion = new ServerConfiguration(IDatosPruebas.IP_BASEDATOS_PRINCIPAL, IDatosPruebas.SCHEMA, IDatosPruebas.USER, IDatosPruebas.PASSWORD, IDatosPruebas.PUERTO_BASEDATOS_PRINCIPAL, IDatosPruebas.PUERTO_ESCUCHA + 500);
			controlador.startServer(configuracion);
			fail("Se esperaba una RemoteException");
		} catch(RemoteException e) {
		} catch(Exception e) {
			fail("Se esperaba una RemoteException");
		}
		
		try {
			// Desactivamos el servidor dos veces para ver si no hay fallos
			configuracion = new ServerConfiguration(IDatosPruebas.IP_BASEDATOS_PRINCIPAL, IDatosPruebas.SCHEMA, IDatosPruebas.USER, IDatosPruebas.PASSWORD, IDatosPruebas.PUERTO_BASEDATOS_PRINCIPAL, IDatosPruebas.PUERTO_ESCUCHA);
			controlador.stopServer(configuracion);
			controlador.stopServer(configuracion);
			assertFalse(controlador.isServerActivate());
			// Comprobamos que se ha intentado cerrar el único cliente conectado
			Thread.sleep(100);
			assertTrue(cliente.isLlamadoServidorInaccesible());
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
}
