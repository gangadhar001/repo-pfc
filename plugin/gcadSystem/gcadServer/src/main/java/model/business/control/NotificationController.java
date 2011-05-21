package model.business.control;

import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

import persistence.DAONotification;

import model.business.knowledge.Groups;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Subgroups;

/**
 * This class represents a controller that allows to manage notifications.
 */
public class NotificationController {

	public static ArrayList<Notification> getNotifications(long sessionId, int currentActiveProject) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notification.name(), Operations.Get.name()));

		return DAONotification.queryNotificationsProject(currentActiveProject);
	}

	public static void deleteNotification(long sessionId, Notification notification) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notification.name(), Operations.Delete.name()));
		
		DAONotification.delete(notification);
	}

}
