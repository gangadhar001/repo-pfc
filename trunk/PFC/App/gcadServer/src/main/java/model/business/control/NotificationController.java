package model.business.control;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.knowledge.Groups;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Subgroups;
import persistence.DAONotification;
import exceptions.NonExistentNotificationException;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

/**
 * This class represents a controller that allows to manage notifications.
 */
public class NotificationController {

	public static ArrayList<Notification> getNotificationsProject(long sessionId, int projectId) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notifications.name(), Operations.Get.name()));

		return DAONotification.queryNotificationsProject(projectId);
	}
	
	public static ArrayList<Notification> getNotificationsUser(long sessionId, int userId, int projectId) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notifications.name(), Operations.Get.name()));

		return DAONotification.queryNotificationsUser(userId, projectId);
	}
	
	public static void insertNotification(long sessionId, Notification n) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notifications.name(), Operations.Add.name()));
	
		DAONotification.insert(n);
	}

	public static void update(long sessionId, Notification n) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentNotificationException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notifications.name(), Operations.Modify.name()));

		DAONotification.update(n);
	}
	
	public static void updateState(long sessionId, int userId, Notification n) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notifications.name(), Operations.Modify.name()));

		DAONotification.updateState(n, userId);
	}
	
	public static void deleteNotification(long sessionId, Notification notification) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notifications.name(), Operations.Delete.name()));
		
		DAONotification.delete(notification);
	}		
	

}
