package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.DBConnectionManager;

import model.business.knowledge.Project;
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
			user = (User) ((User)(data.get(0))).clone();			
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return user;
	}
	
	public static List<User> getUsersProject(Project p) throws SQLException {
		HibernateQuery query;
		List<?> data;
		List<User> result = new ArrayList<User>();

		query = new HibernateQuery("from " + USER_CLASS + " u Join u.projects p where p.id = ?", p.getId());
		data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			for(Object o: data) {
				// The query returns a list where each position
				// is composed of two elements: [user, project]
				Object[] res = (Object[]) o;
				result.add((User) ((User)res[0]).clone());
			}				
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}
}