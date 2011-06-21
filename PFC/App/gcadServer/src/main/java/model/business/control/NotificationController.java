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

	public static ArrayList<Notification> getNotificationsProject(long sessionId, int projectId) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentNotificationException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notifications.name(), Operations.Get.name()));

		return DAONotification.queryNotificationsProject(projectId);
	}
	
	public static ArrayList<Notification> getNotificationsUser(long sessionId, int userId, int projectId) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentNotificationException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notifications.name(), Operations.Get.name()));

		return DAONotification.queryNotificationsUser(userId, projectId);
	}
	
	public static void insertNotification(long sessionId, Notification n) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Notifications.name(), Subgroups.Notifications.name(), Operations.Add.name()));
	
		DAONotification.insert(n);
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
	
//		Notification n;
//		try {
//			n = new Notification(KnowledgeController.getProposals(sessionId).get(0));
//			List<User> users = Server.getInstance().getUsersProject(sessionId, Server.getInstance().getProjects(sessionId).get(1));
//			HashSet<User> u = new HashSet<User>();
//				for (User us: users) u.add(us);
//			n.setUsers(u);
//			n.setSubject("as");
//			n.setState("Unread");
//			n.setProject(Server.getInstance().getProjects(sessionId).get(1));
//				DAONotification.insert(n);
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	

		
	

}
