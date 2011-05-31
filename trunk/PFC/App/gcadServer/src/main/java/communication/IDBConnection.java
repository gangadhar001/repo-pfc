package communication;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import persistence.utils.HibernateQuery;

/**
 * Interface that must implement the classes that provide access to a database, in order to be used 
 * by the manager of the databases connection.
 */
public interface IDBConnection extends Remote {
		
	public List<?> query(HibernateQuery query) throws RemoteException, SQLException;
	
	public List<?> query(String query) throws RemoteException, SQLException;
	
	public void executeUpdate(String query) throws RemoteException, SQLException;
	
	public void initTransaction() throws RemoteException, SQLException;
	
	public Object insert(Object object) throws RemoteException, SQLException;
	
	public void update(Object object) throws RemoteException, SQLException;
	
	public void delete(Object object) throws RemoteException, SQLException;
		
	public void commit() throws RemoteException, SQLException;
	
	public void rollback() throws RemoteException, SQLException;

	public void clearCache(Object object) throws RemoteException, SQLException;
		
}