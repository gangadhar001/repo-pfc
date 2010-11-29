package gcad.communications;

import gcad.internationalization.BundleInternationalization;
import gcad.persistence.SQLCommand;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class represents a manager that allows to access and synchronize changes in several databases
 */
public class DBConnectionManager {

	private static ArrayList<IDBConnection> connections = new ArrayList<IDBConnection>();
	
	public static void initializate(DBConfiguration configuration) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		DBConnection database = new DBConnection();
		database.getAgent().setIp(configuration.getDBip());
		database.getAgent().setPort(configuration.getDBport());
		// Open the connection
		database.open();
		putConnection(database);
		
	}
	
	public static void putConnection(IDBConnection connection) {
		if(!connections.contains(connection)) {
			connections.add(connection);
		}
	}

	public static void clearConnections() {
		connections.clear();
	}
	
	public static void closeConnections() throws SQLException {
		// Close all databases connections
		for(IDBConnection connection : connections) 
			connection.close();	
	}
	
	public static ResultSet query(SQLCommand command) throws SQLException {
		ResultSet datos;
		
			// Use the first connection for make a query
			if(connections.size() == 0) {
				throw new SQLException(BundleInternationalization.getString("ErrorMessage.NoConnections"));
			}
			datos = connections.get(0).query(command);
		return datos;
	}
	
	public static void execute(SQLCommand command) throws SQLException {
		ArrayList<IDBConnection> usedConnections;
	
		// In order to make an update, we access to all databases and if one of them fails, 
		// we revert the changes
		if(connections.size() == 0) {
			throw new SQLException(BundleInternationalization.getString("ErrorMessage.NoConnections"));
		}
		usedConnections = new ArrayList<IDBConnection>();
		for(IDBConnection conexion : connections) {
			try {
				conexion.execute(command);
				usedConnections.add(conexion);
			} catch(Exception ex) {
				// Undo the changes
				for(IDBConnection conexionUsada : usedConnections) {
					conexionUsada.rollback();
				}
				if(conexion instanceof DBConnection) {
					throw new SQLException(BundleInternationalization.getString("ErrorMessage.FailAccess"), ex);
				} else {
					throw new SQLException(BundleInternationalization.getString("ErrorMessage.FailAccess"), ex);
				}
			}
		}

		for(IDBConnection conexion : connections) {
			conexion.commit();
		}
		
	}
	
	public static boolean thereAreConnections () {
		return !connections.isEmpty();
	}

	
	
}
