package communication;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.business.knowledge.Knowledge;

/**
 * TODO: cambiar
 * Interface with operations that can request to customers, and must implement the applications 
 * they want to register on the server.
 */
public interface IClient extends Remote, Serializable  {
	
	public final String NOMBRE_CLIENTE = "cliente";	
	
	public void servidorInaccesible() throws RemoteException;
	
	public void cerrarSesion() throws RemoteException;
	
	public void cerrarSesionEliminacion() throws RemoteException;

	public void notifyActionsAllowed(List<String> actionsName) throws RemoteException;

	public void notifyConnection(boolean connected) throws RemoteException;

	public void notifyKnowledgeRemoved(Knowledge k)  throws RemoteException;

	public void notifyKnowledgeEdited(Knowledge k) throws RemoteException;

	public void notifyKnowledgeAdded(Knowledge k) throws RemoteException;	
	
}
