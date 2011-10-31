package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Interface that must implement the classes that will display, store or process status messages generated by the server.
 */
public interface IConnectionLog extends Remote {

	public void putMessage(String messageType, String message) throws RemoteException, SQLException;
	
	public void putMessage(String user, String messageType, String message) throws RemoteException, SQLException;
	
	public void updateConnectedClients(int n) throws RemoteException, SQLException;
	
}