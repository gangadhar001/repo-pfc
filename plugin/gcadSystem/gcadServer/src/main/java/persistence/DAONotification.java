package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.DBConnectionManager;

import model.business.knowledge.Notification;
import model.business.knowledge.Proposal;
import persistence.utils.HibernateQuery;

/**
 * This class allows to query and modify notifications from the database
 */
public class DAONotification {

	private static final String NOTIFICATION_CLASS = "Notification";
	
	private static final String COL_PROJECT_ID = "projectId";
			
	public static ArrayList<Notification> queryNotificationsProject(int projectId) throws SQLException {
		ArrayList<Notification> result = new ArrayList<Notification>();

		HibernateQuery query = new HibernateQuery("From " + NOTIFICATION_CLASS + " Where " + COL_PROJECT_ID + " = ?", projectId);
		List<?> data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			for (Object o: data)
				result.add((Notification) ((Notification)o).clone());
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}
	
	public static void insert(Notification n) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(n.clone());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	
	public static void update(Notification n) throws SQLException {
		// Get the notification stores in database and update that reference 
		HibernateQuery query;
		List<?> data;
		Notification old = null;
		
		try {
			query = new HibernateQuery("From " + NOTIFICATION_CLASS + " Where id = ?", n.getId());
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				old = (Notification)data.get(0);									
			}
			
			DBConnectionManager.initTransaction();	
			
			old.setKnowledge(n.getKnowledge());
			old.setState(n.getState());
			old.setProject(n.getProject());

			DBConnectionManager.update(old);
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
	}

	public static void delete(Notification n) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(queryNotifications(n.getId()));
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}

	private static Object queryNotifications(int id) throws SQLException {
		Notification result = null;

		HibernateQuery query = new HibernateQuery("From " + NOTIFICATION_CLASS + " Where id = ?", id);
		List<?> data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			result = ((Notification) ((Notification)data.get(0)).clone());
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}
	
}
