package persistence;

import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;

import internationalization.BundleInternationalization;

import java.sql.ResultSet;
import java.sql.SQLException;

import persistence.commands.SQLCommand;
import persistence.commands.SQLCommandSentence;
import persistence.communications.DBConnectionManager;

import model.business.knowledge.ChiefProject;
import model.business.knowledge.Employee;
import model.business.knowledge.User;
import model.business.knowledge.UserRole;

public class PFEmployee {

	private static final String EMPLOYEE_TABLE = "employees";
	
	private static final String COL_ID = "id";
	private static final String COL_NIF = "nif";
	private static final String COL_LOGIN = "login";
	private static final String COL_PASSWORD = "password";
	private static final String COL_ROL = "rol";
	private static final String COL_NAME = "name";
	private static final String COL_SURNAME = "surname";
	private static final String COL_EMAIL = "email";
	private static final String COL_TELEPHONE = "telephone";
	private static final String COL_PROJECT_ID = "projectId";
	
	public static User queryUser(String login, String password) throws IncorrectEmployeeException, SQLException, NonExistentRole {
		SQLCommand command;
		ResultSet data;
		User user = null;
		
		command = new SQLCommandSentence("SELECT * FROM " + EMPLOYEE_TABLE
				+ " WHERE " + COL_LOGIN + " = ? AND " + COL_PASSWORD + " = ?",
				login, password);
		data = DBConnectionManager.query(command);
		data.next();
		
		// TODO: ingles. Si no se obtienen datos, es porque no existe el usuario
		if(data.getRow() == 0) {
			data.close();
			throw new IncorrectEmployeeException("El nombre de usuario o contraseña introducidos no son válidos.");
		} else {
			// Creamos un usuario del tipo adecuado
			try {
				switch(UserRole.values()[data.getInt(COL_ROL)]) {
				case Employee:
					user = new Employee();
					break;
				case ChiefProject:
					user = new ChiefProject();
					break;
				}
			// If the role id is out of the bound, it means that that role does not exist in the system
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new NonExistentRole(BundleInternationalization.getString("Exception.NonExistentRole") + data.getInt(COL_ROL));
			}
			// Establecemos los datos del usuario
			user.setNif(data.getString(COL_NIF));
			user.setLogin(data.getString(COL_LOGIN));
			user.setPassword(data.getString(COL_PASSWORD));
			user.setName(data.getString(COL_NAME));
			user.setSurname(data.getString(COL_SURNAME));
			user.setEmail(data.getString(COL_EMAIL));
			user.setTelephone(data.getString(COL_TELEPHONE));

			// TODO: proyectos
			data.close();
		}
		
		return user;
	}
}