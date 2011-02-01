package persistence.utils;

import java.net.URL;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;



/**
 * TODO: referencia: http://javatutoriales.blogspot.com/2009/05/hibernate-parte-1-persistiendo-objetos.html
 *
 */
public class HibernateUtil {

	private static String databaseURL ="";

	private static Configuration configuration;
	private static URL configFile = HibernateUtil.class.getClassLoader().getResource("/hibernate.cfg.xml");
	private static SessionFactory sessionFactory;
    
    /**
	 * Sets database URL (overrides Hibernate configuration).
	 */
	public static void setDatabaseURL(String databaseURL) {
		HibernateUtil.databaseURL  = databaseURL;
		buildSessionFactory();
	}
	
	/**
	 *  Rebuild hibernate session factory
	 *
	 */
	public static void buildSessionFactory() {
		Properties props;
		try {
			props = new Properties();
			props.setProperty("connection.url", databaseURL);
			configuration = new Configuration();
			configuration.configure(configFile);
			configuration.addProperties(props);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 *  Close the single hibernate session instance.
	 */


    public static SessionFactory getSessionFactory() 
    { 
        return sessionFactory; 
    } 
}