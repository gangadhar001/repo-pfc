package persistence.communications;


import java.sql.SQLException;
import java.util.List;

import persistence.utils.HibernateQuery;

/**
 * Interface that must implement the classes that provide access to a database in order to be used 
 * by the manager of the databases connection.
 */
public interface IDBConnection {
		
	public List<?> query(HibernateQuery query) throws SQLException;
	
	public void initTransaction() throws SQLException;
	
	public Object insert(Object object) throws SQLException;
	
	public void update(Object object) throws SQLException;
	
	public void delete(Object object) throws SQLException;
		
	public void commit() throws SQLException;
	
	public void rollback() throws SQLException;
		
}