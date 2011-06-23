package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.utils.HibernateQuery;

import communication.DBConnectionManager;
import exceptions.NonExistentAddressException;
import exceptions.NonExistentProjectException;

import model.business.knowledge.Address;
import model.business.knowledge.Project;

/**
 * This class allows to query and modify projects from the database
 */
public class DAOProject {

	private static final String PROJECT_CLASS = "Project";
	
	private static final String COL_ID = "id";
	
	public static void insert (Project project) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			Project p = (Project) DBConnectionManager.insert(project.clone());
			project.setId(p.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	
	public static void update(Project p) throws SQLException, NonExistentProjectException {
		// Get the proposal stores in database and update that reference 
		HibernateQuery query;
		List<?> data;
		Project oldPro = null;
		
		try {
			query = new HibernateQuery("From " + PROJECT_CLASS + " Where " + COL_ID + " = ?", p.getId());
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				oldPro = (Project)data.get(0);									
			}
			else
				throw new NonExistentProjectException();
			
			DBConnectionManager.initTransaction();	
			
			oldPro.setBudget(p.getBudget());
			oldPro.setDescription(p.getDescription());
			oldPro.setDomain(p.getDomain());
			oldPro.setEndDate(p.getEndDate());
			oldPro.setEstimatedHours(p.getEstimatedHours());
			oldPro.setId(p.getId());
			oldPro.setName(p.getName());
			oldPro.setProgLanguage(p.getProgLanguage());
			oldPro.setQuantityLines(p.getQuantityLines());
			oldPro.setStartDate(p.getStartDate());

			DBConnectionManager.update(oldPro);
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}		
	}

	public static List<Project> getProjects() throws SQLException {
		HibernateQuery query;
		List<?> data;
		List<Project> result = new ArrayList<Project>();

		query = new HibernateQuery("from " + PROJECT_CLASS);
		data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			for(Object o: data) {
				result.add((Project) ((Project)o).clone());
			}				
		}		
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}
	
	public static Project queryProject(int id) throws SQLException, NonExistentProjectException {
		HibernateQuery query;
		List<?> data;
		Project result = null;

		query = new HibernateQuery("from " + PROJECT_CLASS);
		data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			result = ((Project) ((Project)data.get(0)).clone());			
		}
		else
			throw new NonExistentProjectException();
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}
	
	public static void delete(Project p) throws SQLException, NonExistentProjectException {
		try {
			DBConnectionManager.initTransaction();
			// Get the proposal stores in database and delete that reference 
			DBConnectionManager.delete(queryProject(p.getId()));
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}