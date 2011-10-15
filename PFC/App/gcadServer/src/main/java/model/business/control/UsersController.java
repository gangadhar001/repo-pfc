package model.business.control;

import java.sql.SQLException;
import java.util.List;

import exceptions.IncorrectEmployeeException;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

import persistence.DAOUser;

import model.business.knowledge.Groups;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Project;
import model.business.knowledge.Subgroups;
import model.business.knowledge.User;

public class UsersController {

	public static List<User> getUsers(long sessionId) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Project.name(), Subgroups.Project.name(), Operations.Get.name()));
		
		return DAOUser.getUsers();
	}

	public static void addProjectsUser(long sessionId, User user, Project project) throws SQLException, NonPermissionRoleException, NotLoggedException, IncorrectEmployeeException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Project.name(), Subgroups.Project.name(), Operations.Add.name()));
		
		user.getProjects().add(project);
		DAOUser.update(user);
		SessionController.refreshUserInformation(user);
	}
	
	public static void removeProjectsUser(long sessionId, User user, Project project) throws SQLException, NonPermissionRoleException, NotLoggedException, IncorrectEmployeeException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Project.name(), Subgroups.Project.name(), Operations.Add.name()));
		
		user.getProjects().remove(project);
		DAOUser.update(user);
		SessionController.refreshUserInformation(user);
	}

}
