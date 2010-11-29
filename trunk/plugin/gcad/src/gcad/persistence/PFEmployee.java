package gcad.persistence;

import gcad.communications.DBConnectionManager;
import gcad.domain.knowledge.ChiefProject;
import gcad.domain.knowledge.Employee;
import gcad.domain.knowledge.User;
import gcad.domain.knowledge.UserRole;
import gcad.exceptions.IncorrectEmployeeException;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public static User queryUser(String login, String password) throws IncorrectEmployeeException, SQLException {
		SQLCommand command;
		ResultSet data;
		User user;
		
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
			switch(UserRole.values()[data.getInt(COL_ROL)]) {
			case Employee:
				user = new Employee();
				break;
			case ChiefProject:
				user = new ChiefProject();
				break;
			default:
				data.close();
				throw new IncorrectEmployeeException("El tipo del usuario con login " + login + " es inválido.");
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
