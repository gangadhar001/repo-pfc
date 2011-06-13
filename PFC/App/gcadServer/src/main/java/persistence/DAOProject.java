package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.utils.HibernateQuery;

import communication.DBConnectionManager;

import model.business.knowledge.Project;

/**
 * This class allows to query and modify projects from the database
 */
public class DAOProject {

	private static final String PROJECT_CLASS = "Project";
//	
//	private static final String COL_ID = "id";
//	private static final String COL_NAME = "name";
//	private static final String COL_DESCRIPTION = "description";
//	private static final String COL_START_DATE = "startDate";
//	private static final String COL_END_DATE = "endDate";
//	private static final String COL_BUDGET = "budget";
//	private static final String COL_QUANTITY_LINES = "quantityLines";
//	private static final String COL_DOMAIN = "domain";
//	private static final String COL_PROG_LANGUAGE = "progLanguage";
//	private static final String COL_ESTIMATED_HOURS = "estimatedHours";
	
	
	public static void insert (Project project) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			Project p = (Project) DBConnectionManager.insert(project.clone());
			project.setId(p.getId());
		} finally {
			DBConnectionManager.finishTransaction();
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
	
}