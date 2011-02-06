package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.Notification;
import persistence.communications.DBConnectionManager;
import persistence.utils.HibernateQuery;

public class DAONotification {

	private static final String NOTIFICATION_CLASS = "Notification";
	
	private static final String COL_PROJECT_ID = "projectId";
			
	public static ArrayList<Notification> queryNotificationsProject(int projectId) throws SQLException {
		ArrayList<Notification> result = new ArrayList<Notification>();

		HibernateQuery query = new HibernateQuery("From " + NOTIFICATION_CLASS + " Where " + COL_PROJECT_ID + " = ?", projectId);
		List<?> data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			result = (ArrayList<Notification>) data;
			// Borramos los objetos leídos de la caché
			
		}
		return result;
	}
}
