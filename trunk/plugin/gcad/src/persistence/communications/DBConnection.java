package persistence.communications;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import persistence.HibernateQuery;
import persistence.utils.HibernateUtil;

/**
 * This class allows to connect with the database
 */
public class DBConnection implements IDBConnection {
	
	public DBConnection() {
	}
		
	public void changeURL(String ip, int port) {
		HibernateUtil.setDatabaseURL("jdbc:mysql://" + ip + ":" + String.valueOf(port) + "/dbgcad");
	}
	
	public void initTransaction() throws SQLException {
		try {
			HibernateUtil.getSession().beginTransaction();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}
		
	public List<?> query(HibernateQuery query) throws SQLException {
		List<?> datosLeidos;
		
		try {
			HibernateUtil.getSession().beginTransaction();
			datosLeidos = query.crearQuery(HibernateUtil.getSession()).list();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}

		return datosLeidos;
	}

	public void insert(Object object) throws SQLException {
		try {
			HibernateUtil.getSession().save(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void update(Object object) throws SQLException {
		try {
			HibernateUtil.getSession().update(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void delete(Object object) throws SQLException {
		try {
			HibernateUtil.getSession().delete(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}
	
	public void clearCache(Object objeto) throws SQLException {
		try {
			HibernateUtil.getSession().evict(objeto);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	
	public void commit() throws SQLException {
		try {
			HibernateUtil.getSession().getTransaction().commit();
			HibernateUtil.getSession().close();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void rollback() throws SQLException {
		try {
			HibernateUtil.getSession().getTransaction().rollback();
			HibernateUtil.getSession().close();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}
}