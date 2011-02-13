package persistence.communications;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import persistence.utils.HibernateQuery;
import persistence.utils.HibernateSessionFactory;

/**
 * This class allows to connect with the database
 */
public class DBConnection implements IDBConnection {

	private String ip;
	private int port;
	private String schema;
	
	public DBConnection() {
	}
		
	public DBConnection(String ip, int port, String schema) {
		this.ip = ip;
		this.port = port;
		this.schema = schema;
		changeURL();
	}
		
	public void setIp(String ip) {
		this.ip = ip;
		changeURL();
	}

	public void setPort(int port) {
		this.port = port;
		changeURL();
	}

	public void setSchema(String schema) {
		this.schema = schema;
		changeURL();
	}

	private void changeURL() {
		HibernateSessionFactory.setDatabaseURL("jdbc:mysql://" + ip + ":" + String.valueOf(port) + "/" + schema);
	}
	
	public void initTransaction() throws SQLException {
		try {
			HibernateSessionFactory.getSession().beginTransaction();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}
		
	public List<?> query(HibernateQuery query) throws SQLException {
		List<?> datosLeidos;
		
		try {
			HibernateSessionFactory.getSession().beginTransaction();
			datosLeidos = query.crearQuery(HibernateSessionFactory.getSession()).list();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}

		return datosLeidos;
	}

	public Object insert(Object object) throws SQLException {
		try {
			HibernateSessionFactory.getSession().save(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
		return object;
	}

	public void update(Object object) throws SQLException {
		try {
			HibernateSessionFactory.getSession().update(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void delete(Object object) throws SQLException {
		try {
			HibernateSessionFactory.getSession().delete(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}
	
	public void commit() throws SQLException {
		try {
			HibernateSessionFactory.getSession().flush();
			HibernateSessionFactory.getSession().getTransaction().commit();
			HibernateSessionFactory.closeSession();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void rollback() throws SQLException {
		try {
			HibernateSessionFactory.getSession().flush();
			HibernateSessionFactory.getSession().getTransaction().rollback();
			HibernateSessionFactory.closeSession();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}
}