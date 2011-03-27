package persistence;

import java.sql.SQLException;
import java.util.List;

import communication.DBConnectionManager;

import model.business.knowledge.User;
import persistence.utils.HibernateQuery;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;

/**
 * This class allows to query users from the database
 */
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

		if(data.size() == 0) {
			throw new IncorrectEmployeeException("El nombre de usuario o contraseña introducidos no son válidos.");
		} else {
			user = (User) data.get(0);				
		}
		return user;
	}
}