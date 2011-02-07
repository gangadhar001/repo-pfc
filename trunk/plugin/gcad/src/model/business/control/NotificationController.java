package model.business.control;

import java.sql.SQLException;
import java.util.ArrayList;

import persistence.DAONotification;

import model.business.knowledge.Notification;

public class NotificationController {

	public static ArrayList<Notification> getNotifications(int currentActiveProject) throws SQLException {
		return DAONotification.queryNotificationsProject(currentActiveProject);
	}

	public static void deleteNotification(Notification notification) throws SQLException {
		DAONotification.delete(notification);
	}

}
