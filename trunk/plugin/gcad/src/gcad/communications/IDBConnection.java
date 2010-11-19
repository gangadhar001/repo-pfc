package gcad.communications;

import gcad.persistence.SQLCommand;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TODO: Interfaz que deben implementar las clases que proporcionen acceso a una
 * base de datos para poder ser utilizadas por el gestor de conexiones de
 * bases de datos.
 */
public interface IDBConnection {
	
	public void open() throws SQLException;
	
	public void close() throws SQLException;

	public ResultSet query(SQLCommand comando) throws SQLException;
	
	public void execute(SQLCommand comando) throws SQLException;
	
	public void commit() throws SQLException;
	
	public void rollback() throws SQLException;
		
}
