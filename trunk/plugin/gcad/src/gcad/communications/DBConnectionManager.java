package gcad.communications;

import gcad.persistence.SQLCommand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * TODO: Gestor que permite acceder y modificar de forma sincronizada varias
 * bases de datos (locales o remotas).
 */
public class DBConnectionManager {

	private static ArrayList<IDBConnection> connections = new ArrayList<IDBConnection>();
	
	public static void putConnection(IDBConnection connection) {
		if(!connections.contains(connection)) {
			connections.add(connection);
		}
	}

	public static void clearConnections() {
		connections.clear();
	}
	
	public static void closeConnections() throws SQLException {
		// TODO: Cerramos todas las conexiones con bases de datos
		for(IDBConnection connection : connections) 
			connection.close();	
	}
	
	public static ResultSet query(SQLCommand command) throws SQLException {
		ResultSet datos;
		
			// TODO: Para hacer una consulta utilizamos la primera conexión
			if(connections.size() == 0) {
				throw new SQLException("La lista de conexiones está vacía.");
			}
			datos = connections.get(0).query(command);
		return datos;
	}
	
	public static void execute(SQLCommand command) throws SQLException {
		ArrayList<IDBConnection> usedConnections;
	
		// TODO: Para hacer una modificación accedemos a todas las bases de
		// datos, y si alguna falla revertimos los cambios de las anteriores
		if(connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		usedConnections = new ArrayList<IDBConnection>();
		for(IDBConnection conexion : connections) {
			try {
				conexion.execute(command);
				usedConnections.add(conexion);
			} catch(Exception ex) {
				// TODO: Deshacemos los cambios en las conexiones
				for(IDBConnection conexionUsada : usedConnections) {
					conexionUsada.rollback();
				}
				if(conexion instanceof DBConnection) {
					throw new SQLException("Error en el acceso a la base de datos principal.", ex);
				} else {
					throw new SQLException("Error en el acceso a las bases de datos.", ex);
				}
			}
		}
		// Aplicamos los cambios en todas las conexiones
		for(IDBConnection conexion : connections) {
			conexion.commit();
		}
		
	}
	
	public static boolean thereAreConnections () {
		return !connections.isEmpty();
	}
	
}
