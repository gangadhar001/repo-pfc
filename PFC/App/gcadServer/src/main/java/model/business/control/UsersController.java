package model.business.control;

import java.sql.SQLException;
import java.util.List;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

import persistence.DAOUser;

import model.business.knowledge.Groups;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Project;
import model.business.knowledge.Subgroups;
import model.business.knowledge.User;

public class UsersController {

	public static List<User> getUsers(long sessionId) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Project.name(), Subgroups.Project.name(), Operations.Get.name()));
		
		return DAOUser.getUsers();
	}

	public static void addProjectsUser(long sessionId, User user, Project project) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Project.name(), Subgroups.Project.name(), Operations.Add.name()));
		
		DAOUser.updateProject(user, project);		
	}

}
