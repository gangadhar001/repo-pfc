package model.business.control;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import model.business.knowledge.Knowledge;
import communication.IClient;

/**
 * TODO: esta clase es el observador del servidor. Se registran los clientes (plugin) This class represents an observer pattern
 */
public class ClientsController {

	private static Hashtable<Long, IClient> clients = new Hashtable<Long, IClient>();
	private static List<String> actions = new ArrayList<String>();
	
	public static void disconnectClients() throws RemoteException {
		try {
			// Cerramos todos los clientes que hay conectados al servidor
			for(Long id : clients.keySet()) {
				// Notificamos al cliente que el servidor ha sido desconectado
				clients.get(id).servidorInaccesible();
			}
		} finally {
			// Reseteamos la tabla de sesiones y clientes (lo hacemos aquí,
			// y no iteración a iteración en el bucle anterior, para evitar
			// el problema de tablas mutantes)
			clients = new Hashtable<Long, IClient>();
		}
	}
	
	public static void attach(long sessionID, IClient client) {
		clients.put(sessionID, client);
		// Each time you add a view to the observer, update the permissions. 
		// This is done so that if a view is opened after making login, the view has the allowed actions well configured
//		observer.updateActions(actions);
	}
//	
	public static void detach(long sessionID) {
		clients.remove(sessionID);
	}
//	
//	public static void detachObservers() {
//		observers.clear();
//	}
//	
	public static void notifyConnection(boolean connected) throws RemoteException {
		for(Long id : clients.keySet()) 
			// Notificamos al cliente que el servidor ha sido desconectado
			clients.get(id).notifyConnection(connected);
	}
		
	public static void notifyKnowledgeAdded(Knowledge k) throws RemoteException {
		for(Long id : clients.keySet()) 
			// Notificamos al cliente que el servidor ha sido desconectado
			clients.get(id).notifyKnowledgeAdded(k);
	}
	
	public static void notifyKnowledgeEdited(Knowledge k) throws RemoteException {
		for(Long id : clients.keySet()) 
			// Notificamos al cliente que el servidor ha sido desconectado
			clients.get(id).notifyKnowledgeEdited(k);
	}
	
	public static void notifyKnowledgeRemoved(Knowledge k) throws RemoteException {
		for(Long id : clients.keySet()) 
			// Notificamos al cliente que el servidor ha sido desconectado
			clients.get(id).notifyKnowledgeRemoved(k);
	}
	
//	public static void notifyActionsAllowed(List<String> actionsName) throws RemoteException {
//		actions = actionsName;
//		for(Long id : clients.keySet()) 
//			// Notificamos al cliente que el servidor ha sido desconectado
//			clients.get(id).notifyActionsAllowed(actionsName);
//	}

	public static IClient getClient(long id) {
		return clients.get(id);
	}

}

