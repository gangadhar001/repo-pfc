package persistence;

import java.sql.SQLException;

import model.business.knowledge.Project;
import persistence.communications.DBConnectionManager;

/**
 * This class allows to query and modify projects from the database
 */
public class DAOProject {

//	private static final String PROJECT_TABLE = "projects";
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
			DBConnectionManager.insert(project);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}