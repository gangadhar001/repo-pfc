package persistence.communications;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.classic.Session;

import persistence.utils.HibernateQuery;
import persistence.utils.HibernateUtil;

/**
 * This class represents a manager that allows to access and synchronize changes
 * in several databases
 */
public class DBConnectionManager {

	private static IDBConnection connection = null;

	public static void initializateConnection(String ip, int port) {
		connection = new DBConnection();
		connection.changeURL(ip, port);
	}

	public static void initTransaction() throws SQLException {
		// Iniciamos una transacción que puede estar formada
		// por más de una operación sobre la base de datos

		try {
			connection.initTransaction();
		} catch (Exception ex) {
			throw new SQLException("Error en el acceso a las bases de datos.", ex);
		}

	}

	public static void finishTransaction() throws SQLException {
		SQLException excepcion;
		boolean error;

		// Intentamos finalizar la última transacción iniciada

		error = false;
		excepcion = null;

		try {
			connection.commit();
		} catch (Exception ex) {
			try {
				connection.rollback();
			} catch (Exception ex2) {
			}
			error = true;
			excepcion = new SQLException(
					"Error en el acceso a las bases de datos.", ex);
		}

		if (error) {
			throw excepcion;
		}
	}

	public static List<?> query(HibernateQuery query) throws SQLException {
		List<?> data;

		// TODO: Para hacer una consulta utilizamos sólo la primera conexión

		try {
			data = connection.query(query);
		} catch (Exception ex) {
			throw new SQLException("Error en el acceso a las bases de datos.", ex);

		}
		return data;
	
	}

	public static Object insert(Object object) throws SQLException {
		Object copy;

		// Insertamos el objeto en todas las conexiones, y nos quedamos
		// con la copia devuelta por la primera conexión
		copy = null;
		try {
			if (copy == null) {
				copy = connection.insert(object);
			} else {
				connection.insert(object);
			}
		} catch (Exception ex) {

			throw new SQLException("Error en el acceso a las bases de datos.",
					ex);

		}

		return copy;
	}

	public static void update(Object object) throws SQLException {
		// Actualizamos el objeto en todas las conexiones

		try {
			connection.update(object);
		} catch (Exception ex) {
			throw new SQLException("Error en el acceso a las bases de datos.",
					ex);
		}

	}

	public static void delete(Object object) throws SQLException {
		// Eliminamos el objeto en todas las conexiones

		try {
			connection.delete(object);
		} catch (Exception ex) {
			throw new SQLException("Error en el acceso a las bases de datos.",
					ex);
		}

	}

	public static void clearCache(Object object) throws SQLException {
		// Borramos el objeto de la caché de todas las conexiones

		try {
			// Borramos el objeto
			connection.clearCache(object);
		} catch (Exception ex) {
			throw new SQLException("Error en el acceso a las bases de datos.",
					ex);
		}

	}

	public static void closeConnection() {
		connection.closeSession();
		
	}

}