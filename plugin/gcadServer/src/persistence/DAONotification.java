package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.DBConnectionManager;

import model.business.knowledge.Notification;
import persistence.utils.HibernateQuery;

/**
 * This class allows to query and modify notifications from the database
 */
public class DAONotification {

	private static final String NOTIFICATION_CLASS = "Notification";
	
	private static final String COL_PROJECT_ID = "projectId";
			
	@SuppressWarnings("unchecked")
	public static ArrayList<Notification> queryNotificationsProject(int projectId) throws SQLException {
		ArrayList<Notification> result = new ArrayList<Notification>();

		HibernateQuery query = new HibernateQuery("From " + NOTIFICATION_CLASS + " Where " + COL_PROJECT_ID + " = ?", projectId);
		List<?> data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			result = (ArrayList<Notification>) data;			
		}
		return result;
	}
	
	public static void insert(Notification n) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(n);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	
	public static void update(Notification n) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.update(n.clone());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}

	public static void delete(Notification n) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(n);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	
}
