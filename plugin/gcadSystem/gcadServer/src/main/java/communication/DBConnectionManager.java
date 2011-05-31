package communication;

import internationalization.AppInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.utils.HibernateQuery;

/**
 * This class represents a manager that allows to access and synchronize changes
 * in several databases
 */
public class DBConnectionManager {

	private static ArrayList<IDBConnection> connections = new ArrayList<IDBConnection>();

	public static void addConnection(IDBConnection conn) {
		if (!connections.contains(conn))
			connections.add(conn);
	}

	public static void clear() {
		connections.clear();
	}

	public static void initTransaction() throws SQLException {
		if (connections.size() == 0) {
			throw new SQLException(AppInternationalization.getString("EmptyConnections_Exception"));
		}
		for (IDBConnection connection : connections) {
			try {
				connection.initTransaction();
			} catch (Exception ex) {

				throw new SQLException(AppInternationalization.getString("ErrorDB_Exception"), ex);
			}
		}
	}

	public static void finishTransaction() throws SQLException {
		SQLException excepcion;
		boolean error;

		if (connections.size() == 0) {
			throw new SQLException(AppInternationalization.getString("EmptyConnections_Exception"));
		}
		error = false;
		excepcion = null;
		for (IDBConnection connection : connections) {
			try {
				connection.commit();
			} catch (Exception ex) {
				try {
					connection.rollback();
				} catch (Exception ex2) {
				}
				error = true;

				excepcion = new SQLException(AppInternationalization.getString("ErrorDB_Exception"), ex);

			}
		}
		if (error) {
			throw excepcion;
		}
	}

	public static List<?> query(HibernateQuery query) throws SQLException {
		List<?> data;

		if (connections.size() == 0) {
			throw new SQLException(AppInternationalization.getString("EmptyConnections_Exception"));
		}

		try {
			data = connections.get(0).query(query);
		} catch (Exception ex) {

			throw new SQLException(AppInternationalization.getString("ErrorDB_Exception"), ex);

		}
		return data;
	}
	
	public static List<?> query(String query) throws SQLException {
		List<?> data;

		if (connections.size() == 0) {
			throw new SQLException(AppInternationalization.getString("EmptyConnections_Exception"));
		}

		try {
			data = connections.get(0).query(query);
		} catch (Exception ex) {

			throw new SQLException(AppInternationalization.getString("ErrorDB_Exception"), ex);

		}
		return data;
		
	}

	public static Object insert(Object object) throws SQLException {
		Object copy;

		if (connections.size() == 0) {
			throw new SQLException(AppInternationalization.getString("EmptyConnections_Exception"));
		}
		copy = null;
		for (IDBConnection connection : connections) {
			try {
				if (copy == null) {
					copy = connection.insert(object);
				} else {
					connection.insert(object);
				}
			} catch (Exception ex) {

				throw new SQLException(AppInternationalization.getString("ErrorDB_Exception"), ex);
			}
		}

		return copy;
	}

	public static void update(Object objeto) throws SQLException {
		if (connections.size() == 0) {
			throw new SQLException(AppInternationalization.getString("EmptyConnections_Exception"));
		}
		for (IDBConnection connection : connections) {
			try {
				connection.update(objeto);
			} catch (Exception ex) {

				throw new SQLException(AppInternationalization.getString("ErrorDB_Exception"), ex);
			}
		}
	}

	public static void delete(Object objeto) throws SQLException {
		if (connections.size() == 0) {
			throw new SQLException(AppInternationalization.getString("EmptyConnections_Exception"));
		}
		for (IDBConnection connection : connections) {
			try {
				connection.delete(objeto);
			} catch (Exception ex) {

				throw new SQLException(AppInternationalization.getString("ErrorDB_Exception"), ex);
			}
		}
	}
	
	public static void executeUpdate(String query) throws SQLException {
		if (connections.size() == 0) {
			throw new SQLException(AppInternationalization.getString("EmptyConnections_Exception"));
		}
		for (IDBConnection connection : connections) {
			try {
				connection.executeUpdate(query);
			} catch (Exception ex) {

				throw new SQLException(AppInternationalization.getString("ErrorDB_Exception"), ex);
			}
		}		
	}

	public static void clearCache(Object object) throws SQLException {
		if (connections.size() == 0) {
			throw new SQLException(AppInternationalization.getString("EmptyConnections_Exception"));
		}
		for (IDBConnection connection : connections) {
			try {
				connection.clearCache(object);
			} catch (Exception ex) {

				throw new SQLException(AppInternationalization.getString("ErrorDB_Exception"), ex);
			}
		}		
	}

}