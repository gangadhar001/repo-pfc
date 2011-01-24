package persistence.communications;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.HibernateQuery;

/**
 * This class represents a manager that allows to access and synchronize changes in several databases
 */
public class DBConnectionManager {

	private static ArrayList<IDBConnection> connections = new ArrayList<IDBConnection>();
		
	public static void initializateConnection(DBConfiguration configuration) {
		DBConnection dbconnection = new DBConnection();
		dbconnection.changeURL(configuration.getDBip(), configuration.getDBport());
		putConnection(dbconnection);
	}	
	
	public static void putConnection(IDBConnection connection) {
		if(!connections.contains(connection)) {
			connections.add(connection);
		}
	}

	public static void clearConnections() {
		connections.clear();
	}
	
	public static void initTransaction() throws SQLException {
		// Iniciamos una transacción que puede estar formada
		// por más de una operación sobre la base de datos
		if(connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		for(IDBConnection connection : connections) {
			try {
				connection.initTransaction();
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso a las bases de datos.", ex);
			}
		}
	}
	
	public static void finishTransaction() throws SQLException {
		SQLException excepcion;
		boolean error;
		
		// Intentamos finalizar la última transacción iniciada
		if(connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		error = false;
		excepcion = null;
		for(IDBConnection connection : connections) {
			try {
				connection.commit();
			} catch(Exception ex) {
				try {
					connection.rollback();
				} catch(Exception ex2) {
				}
				error = true;
				excepcion = new SQLException("Error en el acceso a las bases de datos.", ex);
			}
		}
		if(error) {
			throw excepcion;
		}
	}
	
	public static List<?> query(HibernateQuery query) throws SQLException {
		List<?> data;
		
		// TODO: Para hacer una consulta utilizamos sólo la primera conexión
		if(connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		try {
			data = connections.get(0).query(query);
		} catch(Exception ex) {		
			throw new SQLException("Error en el acceso a las bases de datos.", ex);
			
		}
		
		return data;
	}
	
	public static void insert(Object object) throws SQLException {
		// Insertamos el objeto en todas las conexiones, y nos quedamos
		// con la copia devuelta por la primera conexión
		if(connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		for(IDBConnection connection : connections) {
			try {
				connection.insert(object);
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso a las bases de datos.", ex);
			}
		}
	}
	
	public static void update(Object object) throws SQLException {
		// Actualizamos el objeto en todas las conexiones
		if(connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		for(IDBConnection connection : connections) {
			try {
				connection.update(object);
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso a las bases de datos.", ex);
			}
		}
	}
	
	public static void delete(Object object) throws SQLException {
		// Eliminamos el objeto en todas las conexiones
		if(connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		for(IDBConnection connection : connections) {
			try {
				connection.delete(object);
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso a las bases de datos.", ex);
			}
		}
	}
	
	public static void clearCache(Object object) throws SQLException {
		// Borramos el objeto de la caché de todas las conexiones
		if(connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		for(IDBConnection conexion : connections) {
			try {
				// Borramos el objeto
				conexion.clearCache(object);
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso a las bases de datos.", ex);
			}
		}
	}
	
	public static boolean thereAreConnections () {
		return !connections.isEmpty();
	}
	
}