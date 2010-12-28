package model.business.control;

import java.sql.SQLException;

import persistence.PFProject;

import exceptions.InvalidSessionException;

import model.business.knowledge.Operations;
import model.business.knowledge.Project;


public class ProjectController {

	// TODO: traducir
	// Método para registrar un nuevo beneficiario en el sistema
	public static void createProject(long idSesion, Project project) throws InvalidSessionException, SQLException {			
		// Comprobamos si se tienen permisos para realizar la operación
		//TODO: descomentar SessionManager.checkPermission(idSesion, Operations.CreateProject);
		
		/* Consultamos si ya existe otro proyecto con los mismos datos
		existe = PFProject.exists(project);
		if(existe) {
			throw new ExistingProject("Ya existe una persona en el sistema registrada con el NIF " + beneficiario.getNif() + "."); 
		}*/
		
		// Añadimos el proyecto al sistema
		PFProject.insert(project);
	}
}
