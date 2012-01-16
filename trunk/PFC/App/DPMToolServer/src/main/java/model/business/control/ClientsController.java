package model.business.control;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Hashtable;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;
import communication.IClient;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

/**
 * This class represents an observer pattern. It is used to register clients connected to the server
 */
public class ClientsController {

	private static Hashtable<Long, IClient> clients = new Hashtable<Long, IClient>();
	
	public static void disconnectClients() throws RemoteException {
		try {
			// Close all the clients connected to the server
			for(Long id : clients.keySet()) {
				// Notify the clients that the server has been disconnected
				clients.get(id).approachlessServer();
			}
		} finally {
			// Clear table
			clients = new Hashtable<Long, IClient>();
		}
	}
	
	public static void attach(long sessionID, IClient client) {
		clients.put(sessionID, client);
	}

	public static void detach(long sessionID) {
		clients.remove(sessionID);
	}
		
	public static void notifyKnowledgeAdded(long sessionId, Knowledge k, Knowledge parentK) throws RemoteException, Exception {
		// Notify the clients (except the client that launched the operation) about the operation, in order to refresh their view (if it is necessary)
		for(Long id : clients.keySet()) {
			int projectId = KnowledgeController.getProjectFromKnowledge(sessionId, parentK).getId();
			if (id != sessionId && SessionController.getSession(id).getCurrentActiveProject() == projectId)
				clients.get(id).notifyKnowledgeAdded(k, parentK);
		}
	}
	
	public static void notifyKnowledgeEdited(long sessionId, Knowledge newK, Knowledge oldK) throws RemoteException, Exception {
		// Notify the clients (except the client that launched the operation) about the operation, in order to refresh their view (if it is necessary)
		for(Long id : clients.keySet()) {
			int projectId = KnowledgeController.getProjectFromKnowledge(sessionId, oldK).getId();
			if (id != sessionId && SessionController.getSession(id).getCurrentActiveProject() == projectId)
				clients.get(id).notifyKnowledgeEdited(newK, oldK);
		}
	}
	
	public static void notifyKnowledgeRemoved(long sessionId, Knowledge k) throws RemoteException, Exception {
		// Notify the clients (except the client that launched the operation) about the operation, in order to refresh their view (if it is necessary)
		for(Long id : clients.keySet()) {
			// Check same project
			int projectId = KnowledgeController.getProjectFromKnowledge(sessionId, k).getId();
			if (id != sessionId && SessionController.getSession(id).getCurrentActiveProject() == projectId)
				clients.get(id).notifyKnowledgeRemoved(k);
		}
	}
	
	public static void notifyNotificationAvailable(long sessionId, Notification n) throws RemoteException, NotLoggedException {
		// Notify the clients about the operation, in order to refresh their view (if it is necessary)
		for(Long id : clients.keySet()) 
			// Check same project
			if (id != sessionId && SessionController.getSession(id).getCurrentActiveProject() == n.getProject().getId())
				clients.get(id).notifyNotificationAvailable(n);
	}
	
	public static IClient getClient(long id) {
		return clients.get(id);
	}
	
	public static int getClients() {
		return clients.size();
	}

}

