package model.business.control;

import java.sql.SQLException;
import java.util.List;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

import persistence.DAOProject;
import persistence.DAOUser;

import model.business.knowledge.Project;

import model.business.knowledge.Groups;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Subgroups;
import model.business.knowledge.User;

/**
 * This class represents a controller that allows to manage and retrieve information of a project.
 */
public class ProjectController {

	// Method used to create a new project and insert it into database
	public static void createProject(long sessionId, Project project) throws SQLException, NonPermissionRole, NotLoggedException {			
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Project.name(), Subgroups.Project.name(), Operations.Add.name()));
		
		DAOProject.insert(project);
	}
	
	// Method used to retrieve developers that participate in a project
	public static List<User> getUsersProject(long sessionId, Project p) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Project.name(), Subgroups.Project.name(), Operations.Get.name()));
		
		List<User> result = null;
		result = DAOUser.getUsersProject(p);
		return result;
	}

	public static List<Project> getProjects(long sessionId) throws NonPermissionRole, NotLoggedException, SQLException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Project.name(), Subgroups.Project.name(), Operations.Get.name()));
		
		return DAOProject.getProjects();
	}
}
