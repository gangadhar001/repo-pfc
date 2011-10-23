package model.business.control;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import communication.IConnectionLog;

/**
 * Manager which sends the messages generated by the server to the classes that need them, 
 * either for storage in a database or display on screen.
 */
public class LogManager {

	private static ArrayList<IConnectionLog> connections = new ArrayList<IConnectionLog>();
	
	public static void putConnection(IConnectionLog connection) {
		if(!connections.contains(connection)) {
			connections.add(connection);
		}
	}

	public static void clearConnections() {
		connections.clear();
	}
	
	public static void putMessage(String messageType, String message) throws RemoteException, SQLException {
		for(IConnectionLog log : connections) {
			log.putMessage(messageType, message);
		}
	}

	public static void putMessage(String user, String messageType, String message) throws RemoteException, SQLException {
		for(IConnectionLog log : connections) {
			log.putMessage(user, messageType, message);
		}
	}

	public static void updateConnectedClients(int clients) throws RemoteException, SQLException {
		for(IConnectionLog log : connections) {
			log.updateConnectedClients(clients);
		}
	}
	
}