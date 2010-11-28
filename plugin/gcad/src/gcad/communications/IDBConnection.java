package gcad.communications;

import gcad.persistence.SQLCommand;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface that must implement the classes that provide access to a database in order to be used 
 * by the manager of the databases connection.
 */
public interface IDBConnection {
	
	public void open() throws SQLException, IOException;
	
	public void close() throws SQLException;

	public ResultSet query(SQLCommand comando) throws SQLException;
	
	public void execute(SQLCommand comando) throws SQLException;
	
	public void commit() throws SQLException;
	
	public void rollback() throws SQLException;
		
}
