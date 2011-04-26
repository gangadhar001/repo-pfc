package model.business.control;

import java.sql.SQLException;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

import persistence.DAOProject;

import model.business.knowledge.Project;

import model.business.knowledge.Groups;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Subgroups;

/**
 * This class represents a controller that allows to manage projects.
 */
public class ProjectController {

	// Method used to create a new project and insert it into database
	public static void createProject(long sessionId, Project project) throws SQLException, NonPermissionRole, NotLoggedException {			
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Project.name(), Subgroups.Project.name(), Operations.Add.name()));
		DAOProject.insert(project);
	}
}
