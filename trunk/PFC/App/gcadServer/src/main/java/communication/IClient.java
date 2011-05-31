package communication;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;

/**
 * Interface with operations that can request to customers, and must implement the applications 
 * they want to register on the server.
 */
public interface IClient extends Remote, Serializable  {
	
	public final String CLIENT_NAME = "client";	
	
	public void approachlessServer() throws RemoteException;
	
	public void closeSession() throws RemoteException;

	public void notifyKnowledgeRemoved(Knowledge k)  throws RemoteException;

	public void notifyKnowledgeEdited(Knowledge newK, Knowledge oldK) throws RemoteException;

	public void notifyKnowledgeAdded(Knowledge k) throws RemoteException;	
	
	public void notifyNotificationAvailable(Notification n) throws RemoteException;
	
}
