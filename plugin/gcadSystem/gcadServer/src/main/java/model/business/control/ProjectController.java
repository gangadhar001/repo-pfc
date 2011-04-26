package model.business.control;

import java.sql.SQLException;

import persistence.DAOProject;

import model.business.knowledge.Project;

/**
 * This class represents a controller that allows to manage projects.
 */
public class ProjectController {

	// Method used to create a new project and insert it into database
	public static void createProject(Project project) throws SQLException {			
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Project.name(), Subgroups.Project.name(), Operations.Add.name()));
		DAOProject.insert(project);
	}
}
