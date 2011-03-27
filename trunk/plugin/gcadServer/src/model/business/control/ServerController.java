package model.business.control;

import internationalization.BundleInternationalization;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import presentation.JFServer;

import communication.ServerConfiguration;
import communication.DBConnection;
import communication.DBConnectionManager;
import communication.ExportedServer;
import communication.CommunicationsUtilities;

/**
 * This class is used to control the server, showing the server window configuration and exporting and unexporting
 * the instance
 */
public class ServerController {
	
	private ExportedServer serverInstanceExported;
	private DBConnection databaseConnection;
	private JFServer serverWindowUI;
	private String serverIP;
	private boolean isServerActivate;

	public ServerController() {
		serverInstanceExported = null;
		isServerActivate = false;
		serverIP = null;
		serverWindowUI = new JFServer(this);
	}
		
	public void showServerWindowUI() {
		serverWindowUI.setLocationRelativeTo(null);
		serverWindowUI.setVisible(true);
	}

	public void hideServerWindowUI() {
		serverWindowUI.setVisible(false);
	}

	public void startServer(ServerConfiguration configuration) throws RemoteException, MalformedURLException, UnknownHostException, NotBoundException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		serverIP = CommunicationsUtilities.getHostIP();
		boolean ok;
		
		// Indicate to RMI that it have to use the given IP as IP of this host in remote communications.
		// This instruction is necessary because if the computer belongs to more than one network, RMI may take a private IP as the host IP 
		// and incoming communications won't work 
		System.setProperty("java.rmi.server.hostname", serverIP);

		DBConnectionManager.clear();
		
		// Create databse connection
		databaseConnection = new DBConnection();
		databaseConnection.setIp(configuration.getDBIp());
		databaseConnection.setPort(configuration.getDBPort());
		databaseConnection.setSchema(configuration.getDBSchema());
		databaseConnection.setUser(configuration.getDBUser());
		databaseConnection.setPassword(configuration.getDBPassword());
		ok = databaseConnection.testConexion();
		if(!ok) {
			throw new SQLException(BundleInternationalization.getString("TestDBConnection_Exception"));
		}
		DBConnectionManager.addConnection(databaseConnection);
		
		// Añadimos las conexiones que mostrarán los mensaje del servidor
		// en su ventana principal y los guardará en la base de datos
//		logFrontend = new ConexionLogVentana();
//		logFrontend.ponerVentana(ventana);
//		logBD = new ConexionLogBD();
//		GestorConexionesLog.ponerConexion(logFrontend);
//		GestorConexionesLog.ponerConexion(logBD);
		
		
		// Create server instance and start it in "listen" mode.
		try {
			serverInstanceExported = ExportedServer.getServer();
			serverInstanceExported.activate(serverIP, configuration.getServerPort());
		} catch(RemoteException e) {
			throw new RemoteException(BundleInternationalization.getString("ServerController_ActivateServer_Error") + serverIP + ":" + String.valueOf(configuration.getServerPort()) + ".");
		}
		
//		// Mostramos un mensaje indicando que el servidor está activo
//		if(configuracion.isRespaldoActivado()) {
//			GestorConexionesLog.ponerMensaje(ITiposMensajeLog.TIPO_INFO, "=== Servidor iniciado ===");
//		} else {
//			GestorConexionesLog.ponerMensaje(ITiposMensajeLog.TIPO_INFO, "=== Servidor iniciado (servidor de respaldo deshabilitado) ===");
//		}
//		GestorConexionesLog.actualizarClientesEscuchando(0);
		
		isServerActivate = true;
	}

	public void stopServer(ServerConfiguration configuration) throws RemoteException, MalformedURLException, UnknownHostException, SQLException {
		// When disconnect the server, it ignores all errors in order to put down the server, 
		// even if do not have access to clients who had registered (eg, because the network is down), 
		// the database server has stopped or changed the IP of server
		
		// Generamos un mensaje indicando que el servidor está inactivo
//		try {
//			GestorConexionesLog.ponerMensaje(ITiposMensajeLog.TIPO_INFO, "=== Servidor detenido ===");
//		} catch(RemoteException e) {
//		} catch(SQLException e) {
//		}
		
		// TODO: Notificamos a los clientes que el servidor ha sido desconectado
		try {
			SessionController.desconectarClientes();
		} catch(RemoteException e) {
		}
		
		DBConnectionManager.clear();	
		
		isServerActivate = false;
	}
	
	public JFServer getVentana() {
		return serverWindowUI;
	}
	
	public boolean isServerActivate() {
		return isServerActivate;
	}

	public String getIPServidor() {
		return serverIP;
	}
	
//	public int getNumeroClientesConectados() {
//		return GestorSesiones.getClientes().size();
//	}

}
