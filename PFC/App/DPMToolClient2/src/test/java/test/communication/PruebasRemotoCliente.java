package test.communication;

import java.rmi.NotBoundException;

import junit.framework.TestCase;
import bussiness.control.ClientController;

import communication.CommunicationsUtilities;

/**
 * Pruebas del cliente para su comunicación con el servidor
 */
public class PruebasRemotoCliente extends TestCase {

	private ServerPrueba server;
	
	
	public void setUp() {
		try {
			super.setUp();
			server = new ServerPrueba();
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void tearDown() {
		try {
			super.tearDown();
			// Se detiene el servidor
			server.deactivate(CommunicationsUtilities.getHostIP());
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	// Todas las operaciones de la clase ClientController se redirigen
	// a la clase Servidor, la cual se prueba con los casos de prueba
	// del sistema servidor. Por ello, aqui sólo se prueba que se exporte
	// correctamente el cliente
	public void testInitClient() {
		try {
			// Se activa y exporta el servidor
			server.activate(CommunicationsUtilities.getHostIP());
			// Se inicia el cliente
			ClientController.getInstance().initClient(CommunicationsUtilities.getHostIP(), String.valueOf(server.getPuerto()), "emp1", "emp1");
			assertEquals(server.getClients().size(), 1);
			ClientController.getInstance().closeController();
		}
		catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testDeactivateClient() {
		try {
			// Se activa y exporta el servidor
			server.activate(CommunicationsUtilities.getHostIP());
			// Se inicia el cliente
			ClientController.getInstance().initClient(CommunicationsUtilities.getHostIP(), String.valueOf(server.getPuerto()), "emp1", "emp1");
			assertEquals(server.getClients().size(), 1);
			// Se desconecta el cliente
			ClientController.getInstance().signout();
			assertEquals(server.getClients().size(), 0);
		}
		catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testDeactivateServer() {
		try {
			// Se activa y exporta el servidor
			server.activate(CommunicationsUtilities.getHostIP());
			// Se detiene el servidor y se intenta loguear el cliente
			server.deactivate(CommunicationsUtilities.getHostIP());
			ClientController.getInstance().initClient(CommunicationsUtilities.getHostIP(), String.valueOf(server.getPuerto()), "emp1", "emp1");
			fail("Se esperaba NotBoundException");
		}
		catch(NotBoundException e) { }
		catch(Exception e) { 
			fail("Se esperaba NotBoundException");
		}		
	}
	
	
	
	

}
