package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.DBConnectionManager;

import model.business.knowledge.Notification;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.User;
import persistence.utils.HibernateQuery;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRoleException;

/**
 * This class allows to query users from the database
 */
public class DAOUser {

	private static final String USER_CLASS = "User";
	private static final String USERS_PROJECTS = "usersProjects";
	
	private static final String COL_LOGIN = "login";
	private static final String COL_PASSWORD = "password";
	private static final String COL_IDUSER = "idUser";
	private static final String COL_IDPROJECT = "idProject";
	
	public static User queryUser(String login, String password) throws IncorrectEmployeeException, SQLException {
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
	
	public static List<User> getUsers() throws SQLException, IncorrectEmployeeException {
		HibernateQuery query;
		List<?> data;
		List<User> result = new ArrayList<User>();
		
		query = new HibernateQuery("From " + USER_CLASS);
		data = DBConnectionManager.query(query);

		// TODO: 
		if(data.size() == 0) {
			throw new IncorrectEmployeeException("No hay usuarios registrados en el sistema");
		} else {
			for(Object o: data)
				result.add((User) ((User)(o)).clone());			
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
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

	public static void update(User user) throws SQLException, IncorrectEmployeeException {
		// Get the proposal stores in database and update that reference 
		HibernateQuery query;
		List<?> data;
		User oldUser = null;
		
		try {
			query = new HibernateQuery("From " + USER_CLASS + " Where nif = ?", user.getNif());
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				oldUser = (User)data.get(0);									
			}
			else
				throw new IncorrectEmployeeException("El nombre de usuario o contraseña introducidos no son válidos.");
			
			DBConnectionManager.initTransaction();	
			
			oldUser.setNif(user.getNif());
			oldUser.setName(user.getName());
			oldUser.setEmail(user.getEmail());
			oldUser.setCompany(user.getCompany());
			oldUser.setLogin(user.getLogin());
			oldUser.setPassword(user.getPassword());
			oldUser.setProjects(user.getProjects());
			oldUser.setSeniority(user.getSeniority());
			oldUser.setSurname(user.getSurname());
			oldUser.setTelephone(user.getTelephone());

			DBConnectionManager.update(oldUser);
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}		
	}
	
//	public static void updateProject(User u, Project p) throws SQLException {;		
//		try {				
//			DBConnectionManager.initTransaction();
//			String query = "INSERT INTO " + USERS_PROJECTS + " VALUES (" + u.getId() + ", " + p.getId() + ")";
//			DBConnectionManager.executeUpdate(query);
//			
//		} finally {
//			DBConnectionManager.finishTransaction();
//		}
//	}

	public static void insert(User user) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			User newUser = (User) DBConnectionManager.insert(user.clone());
			user.setId(newUser.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
	}
	
	public static void delete(User user) throws SQLException, IncorrectEmployeeException {
		try {
			DBConnectionManager.initTransaction();
			// Get the proposal stores in database and delete that reference 
			DBConnectionManager.delete(queryUser(user.getLogin(), user.getPassword()));
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}