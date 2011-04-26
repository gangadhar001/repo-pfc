package model.business.control;

import java.sql.SQLException;
import java.util.ArrayList;

import persistence.DAONotification;

import model.business.knowledge.Notification;

/**
 * This class represents a controller that allows to manage notifications.
 */
public class NotificationController {

	public static ArrayList<Notification> getNotifications(int currentActiveProject) throws SQLException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notification.name(), Subgroups.Notification.name(), Operations.Get.name()));

		return DAONotification.queryNotificationsProject(currentActiveProject);
	}

	public static void deleteNotification(Notification notification) throws SQLException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notification.name(), Subgroups.Notification.name(), Operations.Delete.name()));

		DAONotification.delete(notification);
	}

}
