package communication;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import persistence.utils.HibernateQuery;
import persistence.utils.HibernateSessionFactory;

/**
 * This class allows to connect with the database
 */
public class DBConnection implements IDBConnection {

	private String ip;
	private int port;
	private String schema;
	private String user;
	private String password;
	
	public DBConnection() {
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
		changeURL();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
		changeURL();
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
		changeURL();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
		changeURL();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		changeURL();
	}

	private void changeURL() {
		// Set the connection string.
		// Default take Schema=gcad; user=gcad; pass=gcad
		if (schema == null)
			schema = "gcad";
		if (user == null)
			user = "gcad";
		if (password == null)
			password = "gcad";
		HibernateSessionFactory.setDatabaseURL("jdbc:mysql://" + ip + ":" + String.valueOf(port) + "/" + schema);
		// Set the user and password
		HibernateSessionFactory.setUsername(user);
		HibernateSessionFactory.setPassword(password);
	}
	
	public void initTransaction() throws RemoteException, SQLException {
		try {
			HibernateSessionFactory.getSession().beginTransaction();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}
		
	public List<?> query(HibernateQuery query) throws RemoteException, SQLException {
		List<?> data;
		
		try {
			HibernateSessionFactory.getSession().beginTransaction();
			data = query.createQuery(HibernateSessionFactory.getSession()).list();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}

		return data;
	}

	// Method uses to execute a SQL string query
	@Override
	public List<?> query(String query) throws RemoteException, SQLException {
		List<?> data;
		
		try {
			HibernateSessionFactory.getSession().beginTransaction();
			data = HibernateSessionFactory.getSession().createSQLQuery(query).list();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}

		return data;
	}

	// Method uses to execute a SQL string query update
	@Override
	public void executeUpdate(String query) throws RemoteException, SQLException {
		try {
			HibernateSessionFactory.getSession().createSQLQuery(query).executeUpdate();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
		
	}
	
	public Object insert(Object object) throws RemoteException, SQLException {
		try {
			HibernateSessionFactory.getSession().save(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
		return object;
	}

	public void update(Object object) throws RemoteException, SQLException {
		try {
			HibernateSessionFactory.getSession().update(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void delete(Object object) throws RemoteException, SQLException {
		try {
			HibernateSessionFactory.getSession().delete(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}
	
	public void commit() throws RemoteException, SQLException {
		try {
			HibernateSessionFactory.getSession().flush();
			HibernateSessionFactory.getSession().getTransaction().commit();
			HibernateSessionFactory.closeSession();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void rollback() throws RemoteException, SQLException {
		try {
			HibernateSessionFactory.getSession().flush();
			HibernateSessionFactory.getSession().getTransaction().rollback();
			HibernateSessionFactory.closeSession();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	@SuppressWarnings("deprecation")
	public boolean testConexion() {
		Session sesion;
		boolean ok;
		
		try {
			sesion = HibernateSessionFactory.getSession();
			ok = sesion.connection().isValid(2000);
		} catch(SQLException ex) {
			ok = false;
		} catch(HibernateException ex) {
			ok = false;
		}
		return ok;
	}

	@Override
	public void clearCache(Object object) throws SQLException {
		try {
			HibernateSessionFactory.getSession().evict(object);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
		
	}
}