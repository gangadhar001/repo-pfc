package model.business.control;

import java.sql.SQLException;

import persistence.DAOProject;

import model.business.knowledge.Project;

/**
 * This class represents a controller that allows to manage projects.
 */
public class ProjectController {

	// TODO: traducir
	// M�todo para registrar un nuevo beneficiario en el sistema
	public static void createProject(Project project) throws SQLException {			
		/* Consultamos si ya existe otro proyecto con los mismos datos
		existe = PFProject.exists(project);
		if(existe) {
			throw new ExistingProject("Ya existe una persona en el sistema registrada con el NIF " + beneficiario.getNif() + "."); 
		}*/
		
		// A�adimos el proyecto al sistema
		DAOProject.insert(project);
	}
}
