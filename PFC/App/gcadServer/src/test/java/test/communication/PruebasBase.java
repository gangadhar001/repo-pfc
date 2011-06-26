package test.communication;

import java.util.List;

import persistence.utils.HibernateQuery;
import persistence.utils.HibernateSessionFactory;

import communication.DBConnection;
import communication.DBConnectionManager;

import junit.framework.TestCase;

/**
 * Clase base para las pruebas, que prepara y abre la conexión con la
 * base de datos al iniciar las pruebas y la cierra al finalizar.
 */
public class PruebasBase extends TestCase {
	
	protected void setUp() {
		HibernateQuery consulta;
		List<?> datos;
		DBConnection conexion;
		
		try {
			// Ponemos la conexión local con la base de datos
			conexion = new DBConnection();
			DBConnectionManager.addConnection(conexion);
			// Cerramos la sesión de Hibernate para que no haya
			// problemas al reutilizar los objetos de las pruebas
			HibernateSessionFactory.closeSession();
			// Borramos la base de datos (solo hacen falta estos objetos, porque los demas se borran en cascada)
			for(String clase : new String[] { "Project", "LogEntry", "Company", "Address", "User"}) {
				consulta = new HibernateQuery("FROM " + clase);
				datos = DBConnectionManager.query(consulta);
				for(Object objeto : datos) {					
					DBConnectionManager.initTransaction();					
					DBConnectionManager.clearCache(objeto);
					DBConnectionManager.delete(objeto);
					DBConnectionManager.finishTransaction();
				}
			}
		} catch(Exception e) {
			fail(e.toString());
			e.printStackTrace();
		}
	}
	
	protected void tearDown() {
		try {
			// Vaciamos la lista de conexiones
			DBConnectionManager.clear();
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	
}
