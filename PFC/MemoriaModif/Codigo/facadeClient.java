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

	public void notifyKnowledgeAdded(Knowledge k, Knowledge parentK) throws RemoteException;	
	
	public void notifyNotificationAvailable(Notification n) throws RemoteException;
	
}
