package persistence.communications;

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
			throw new SQLException("La lista de conexiones est� vac�a.");
		}
		for (IDBConnection conexion : connections) {
			try {
				conexion.initTransaction();
			} catch (Exception ex) {

				throw new SQLException(
						"Error en el acceso a las bases de datos.", ex);

			}
		}
	}

	public static void finishTransaction() throws SQLException {
		SQLException excepcion;
		boolean error;

		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones est� vac�a.");
		}
		error = false;
		excepcion = null;
		for (IDBConnection conexion : connections) {
			try {
				conexion.commit();
			} catch (Exception ex) {
				try {
					conexion.rollback();
				} catch (Exception ex2) {
				}
				error = true;

				excepcion = new SQLException("Error en el acceso a las bases de datos.", ex);

			}
		}
		if (error) {
			throw excepcion;
		}
	}

	public static List<?> query(HibernateQuery query) throws SQLException {
		List<?> data;

		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones est� vac�a.");
		}

		try {
			data = connections.get(0).query(query);
		} catch (Exception ex) {

			throw new SQLException("Error en el acceso a las bases de datos.",
					ex);

		}
		return data;
	}

	public static Object insert(Object object) throws SQLException {
		Object copia;

		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones est� vac�a.");
		}
		copia = null;
		for (IDBConnection conexion : connections) {
			try {
				if (copia == null) {
					copia = conexion.insert(object);
				} else {
					conexion.insert(object);
				}
			} catch (Exception ex) {

				throw new SQLException(
						"Error en el acceso a las bases de datos.", ex);

			}
		}

		return copia;
	}

	public static void update(Object objeto) throws SQLException {
		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones est� vac�a.");
		}
		for (IDBConnection conexion : connections) {
			try {
				conexion.update(objeto);
			} catch (Exception ex) {

				throw new SQLException(
						"Error en el acceso a las bases de datos.", ex);

			}
		}
	}

	public static void delete(Object objeto) throws SQLException {
		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones est� vac�a.");
		}
		for (IDBConnection conexion : connections) {
			try {
				conexion.delete(objeto);
			} catch (Exception ex) {

				throw new SQLException(
						"Error en el acceso a las bases de datos.", ex);

			}
		}
	}

}