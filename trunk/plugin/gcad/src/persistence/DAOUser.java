package persistence;

import java.sql.SQLException;
import java.util.List;

import model.business.knowledge.User;
import persistence.communications.DBConnectionManager;
import persistence.utils.HibernateQuery;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;

public class DAOUser {

	private static final String USER_CLASS = "User";
	
	private static final String COL_LOGIN = "login";
	private static final String COL_PASSWORD = "password";
	
	public static User queryUser(String login, String password) throws IncorrectEmployeeException, SQLException, NonExistentRole {
		HibernateQuery query;
		List<?> data;
		User user = null;
		
			query = new HibernateQuery("From " + USER_CLASS + " Where " + COL_LOGIN + " = ? AND " + COL_PASSWORD + " = ?", login, password);
			data = DBConnectionManager.query(query);
	
			// TODO: ingles. Si no se obtienen datos, es porque no existe el usuario
			if(data.size() == 0) {
				throw new IncorrectEmployeeException("El nombre de usuario o contraseña introducidos no son válidos.");
			} else {
				user = (User) data.get(0);				
			}
		return user;
	}
}