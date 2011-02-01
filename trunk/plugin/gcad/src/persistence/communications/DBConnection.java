package persistence.communications;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;

import persistence.utils.HibernateQuery;
import persistence.utils.HibernateUtil;

/**
 * This class allows to connect with the database
 */
public class DBConnection implements IDBConnection {

	private Session session;
	
	public DBConnection() {
	}
		
	public void changeURL(String ip, int port) {
		HibernateUtil.setDatabaseURL("jdbc:mysql://" + ip + ":" + String.valueOf(port) + "/dbgcad");
	}
	
	public void initTransaction() throws SQLException {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}
		
	public List<?> query(HibernateQuery query) throws SQLException {
		List<?> datosLeidos;
		
		try {
			datosLeidos = query.crearQuery(session).list();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}

		return datosLeidos;
	}

	public Object insert(Object object) throws SQLException {
		try {
			session.save(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
		return object;
	}
//
//	public void update(Object object) throws SQLException {
//		try {
//			HibernateUtil.getSession().update(object);
//		} catch(HibernateException ex) {
//			throw new SQLException(ex.getLocalizedMessage(), ex);
//		}
//	}
//
//	public void delete(Object object) throws SQLException {
//		try {
//			HibernateUtil.getSession().delete(object);
//		} catch(HibernateException ex) {
//			throw new SQLException(ex.getLocalizedMessage(), ex);
//		}
//	}
//	
//	public void clearCache(Object objeto) throws SQLException {
//		try {
//			HibernateUtil.getSession().evict(objeto);
//		} catch(HibernateException ex) {
//			throw new SQLException(ex.getLocalizedMessage(), ex);
//		}
//	}
//
//	
	public void commit() throws SQLException {
		try {
			session.getTransaction().commit();
			closeSession();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void rollback() throws SQLException {
		try {
			session.getTransaction().rollback();
			closeSession();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}
	
	public void closeSession() {
		session.close();
	}

@Override
public void update(Object object) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void delete(Object object) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void clearCache(Object object) throws SQLException {
	// TODO Auto-generated method stub
	
}



}