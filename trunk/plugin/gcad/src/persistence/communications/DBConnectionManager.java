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
		// Iniciamos una transacción que puede estar formada
		// por más de una operación sobre la base de datos

		// Iniciamos una transacción que puede estar formada
		// por más de una operación sobre la base de datos
		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
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

		// Intentamos finalizar la última transacción iniciada
		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
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

				excepcion = new SQLException(
						"Error en el acceso a las bases de datos.", ex);

			}
		}
		if (error) {
			throw excepcion;
		}
	}

	public static List<?> query(HibernateQuery query) throws SQLException {
		List<?> data;

		// TODO: Para hacer una consulta utilizamos sólo la primera conexión

		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
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

		// Insertamos el objeto en todas las conexiones, y nos quedamos
		// con la copia devuelta por la primera conexión
		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
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
		// Actualizamos el objeto en todas las conexiones
		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
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
		// Eliminamos el objeto en todas las conexiones
		if (connections.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
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