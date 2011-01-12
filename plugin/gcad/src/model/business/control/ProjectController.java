package model.business.control;

import java.sql.SQLException;

import persistence.PFProject;

import model.business.knowledge.Project;


public class ProjectController {

	// TODO: traducir
	// Método para registrar un nuevo beneficiario en el sistema
	public static void createProject(Project project) throws SQLException {			
		/* Consultamos si ya existe otro proyecto con los mismos datos
		existe = PFProject.exists(project);
		if(existe) {
			throw new ExistingProject("Ya existe una persona en el sistema registrada con el NIF " + beneficiario.getNif() + "."); 
		}*/
		
		// Añadimos el proyecto al sistema
		PFProject.insert(project);
	}
}
