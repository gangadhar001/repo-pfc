//package persistence;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import communication.DBConnectionManager;
//
//import model.business.knowledge.Notification;
//import persistence.utils.HibernateQuery;
//import persistence.utils.HibernateSessionFactory;
//
///**
// * This class allows to query and modify notifications from the database
// */
//public class DAONotification {
//
//        private static final String NOTIFICATION_CLASS = "Notification";
//        
//        private static final String COL_PROJECT_ID = "projectId";
//                        
//        public static ArrayList<Notification> queryNotificationsProject(int projectId) throws SQLException {
//                ArrayList<Notification> result = new ArrayList<Notification>();
////
////                HibernateQuery query = new HibernateQuery("From " + NOTIFICATION_CLASS + " Where " + COL_PROJECT_ID + " = ?", projectId);
////                List<?> data = DBConnectionManager.query(query);
////
////                if(data.size() > 0) {
////                        for (Object o: data)
////                                result.add((Notification) ((Notification)o).clone());
////                }
////                
////                // Clear cache
////                for(Object object : data) {
////                        DBConnectionManager.clearCache(object);
////                }
////                
////                return result;
//        	
//        	HibernateQuery query = new HibernateQuery("From " + NOTIFICATION_CLASS + " n Join n.users user where user.id = ?", 1);
//    		List<?> data = DBConnectionManager.query(query);
//
//    		if(data.size() > 0) {
//    			for (Object o: data) {
//    				Object[] res = (Object[]) o;
//    				Notification n = (Notification) ((Notification)res[0]).clone();
//    				// Set state of the notification for that user
////    				setState(n, idUser);
//    				result.add(n);
//    			}
//    		}
//			return result;
//        }
//        
//        public static void insert(Notification n) throws SQLException {
//                try {
//                        DBConnectionManager.initTransaction();
//                        DBConnectionManager.insert(n.clone());
//                } finally {
//                        DBConnectionManager.finishTransaction();
//                }
//        }
//        
//        public static void update(Notification n) throws SQLException {
//                // Get the notification stores in database and update that reference 
//                HibernateQuery query;
//                List<?> data;
//                Notification old = null;
//                
//                try {
//                        query = new HibernateQuery("From " + NOTIFICATION_CLASS + " Where id = ?", n.getId());
//                        data = DBConnectionManager.query(query);
//        
//                        if(data.size() > 0) {
//                                old = (Notification)data.get(0);                                                                        
//                        }
//                        
//                        DBConnectionManager.initTransaction();  
//                        
//                        old.setKnowledge(n.getKnowledge());
//                        old.setState(n.getState());
//                        old.setProject(n.getProject());
//
//                        DBConnectionManager.update(old);
//                } finally {
//                        DBConnectionManager.finishTransaction();
//                }
//                
//                // Clear cache
//                for(Object object : data) {
//                        DBConnectionManager.clearCache(object);
//                }
//        }
//
//        public static void delete(Notification n) throws SQLException {
//                try {
//                        DBConnectionManager.initTransaction();
//                        DBConnectionManager.delete(queryNotifications(n.getId()));
//                } finally {
//                        DBConnectionManager.finishTransaction();
//                }
//        }
//
//        private static Object queryNotifications(int id) throws SQLException {
//                Notification result = null;
//
//                HibernateQuery query = new HibernateQuery("From " + NOTIFICATION_CLASS + " Where id = ?", id);
//                List<?> data = DBConnectionManager.query(query);
//
//                if(data.size() > 0) {
//                        result = ((Notification) ((Notification)data.get(0)).clone());
//                }
//                
//                // Clear cache
//                for(Object object : data) {
//                        DBConnectionManager.clearCache(object);
//                }
//                
//                return result;
//        }
//        
//}

package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.Notification;
import persistence.utils.HibernateQuery;

import communication.DBConnectionManager;
import exceptions.NonExistentNotificationException;

/**
 * This class allows to query and modify notifications from the database
 */
public class DAONotification {

	private static final String NOTIFICATION_CLASS = "Notification";
	
	private static final String COL_PROJECT_ID = "projectId";
	private static final String COL_NOTIFICATION_ID = "idNotification";
	private static final String COL_USER_ID = "idUser";
	private static final String COL_STATE = "state";
	private static final String COL_ID = "id";

	private static final String NOTIFICATIONS_USERS = "notificationsUsers";
	private static final String NOTIFICATIONS = "notifications";
			
	// Get all the notifications of that user in that project
	public static ArrayList<Notification> queryNotificationsUser(int idUser, int projectId) throws SQLException, NonExistentNotificationException {
		ArrayList<Notification> result = new ArrayList<Notification>();

		HibernateQuery query = new HibernateQuery("From " + NOTIFICATION_CLASS + " n Join n.users user where user.id = ? AND " + COL_PROJECT_ID + " = ?", idUser, projectId);
		List<?> data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			for (Object o: data) {
				Object[] res = (Object[]) o;
				Notification n = (Notification) ((Notification)res[0]).clone();
				// Set state of the notification for that user
				setState(n, idUser);
				result.add(n);
			}
		}
		else
			throw new NonExistentNotificationException();
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}
	
	// Get all the notifications in that project
	public static ArrayList<Notification> queryNotificationsProject(int projectId) throws SQLException, NonExistentNotificationException {
		ArrayList<Notification> result = new ArrayList<Notification>();

		HibernateQuery query = new HibernateQuery("From " + NOTIFICATION_CLASS + " Where " + COL_PROJECT_ID + " = ?", projectId);
		List<?> data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			for (Object o: data) {
				Notification n = (Notification) ((Notification)o).clone();
				setState(n);
				result.add(n);
			}
		}
		else
			throw new NonExistentNotificationException();
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}
	
	// Set the state of the notification
	private static void setState(Notification n, int idUser) throws SQLException, NonExistentNotificationException {
		String query = "SELECT " + COL_STATE + " FROM " + NOTIFICATIONS_USERS + " WHERE " + COL_NOTIFICATION_ID + " = " + n.getId() + " AND " + COL_USER_ID + " = " + idUser;
		List<?> data = DBConnectionManager.query(query);
		String state = "";		
		if (data.get(0).toString().startsWith("R")) state = "Read";
		else state = "Unread";
		n.setState(state);
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
	}
	
	// Set the state of the notification
	private static void setState(Notification n) throws SQLException, NonExistentNotificationException {
		String query = "SELECT " + COL_STATE + " FROM " + NOTIFICATIONS_USERS + " WHERE " + COL_NOTIFICATION_ID + " = " + n.getId();
		List<?> data = DBConnectionManager.query(query);
		String state = "";		
		if (data.get(0).toString().startsWith("R")) state = "Read";
		else state = "Unread";
		n.setState(state);
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
	}
	
	// Create a new notification
	public static void insert(Notification n) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			Notification newNot = (Notification) DBConnectionManager.insert(n.clone());
			n.setId(newNot.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	
	// Update the notification
	public static void update(Notification n) throws SQLException, NonExistentNotificationException {
		// Get the notification stores in database and update that reference 
		HibernateQuery query;
		List<?> data;
		Notification old = null;
		
		try {
			query = new HibernateQuery("From " + NOTIFICATION_CLASS + " Where " + COL_ID + " = ?", n.getId());
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				old = (Notification)data.get(0);									
			}
			else
				throw new NonExistentNotificationException();
			
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
	
	// Update the state of the notification for that user
	public static void updateState(Notification n, int userId) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			String query = "UPDATE " + NOTIFICATIONS_USERS + " SET " + COL_STATE + " = '" + n.getState() + "' WHERE " + COL_NOTIFICATION_ID + " = " + n.getId() + " AND " + COL_USER_ID + " = " + userId;
			DBConnectionManager.executeUpdate(query);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}

	// Delete notification
	public static void delete(Notification n) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			String query = "Delete From " + NOTIFICATIONS + " where " + COL_ID + " = " + n.getId();
			DBConnectionManager.executeUpdate(query);
		} finally {
			DBConnectionManager.finishTransaction();
		}		
	}
	
	// Delete notification from one user
	public static void deleteFromUser(Notification n, int userId) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			String query = "Delete From " + NOTIFICATIONS_USERS + " where " + COL_USER_ID + " = " + userId + " AND notificationsUsers.idNotification = " + n.getId();
			DBConnectionManager.executeUpdate(query);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	
}
