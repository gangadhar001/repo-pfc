package persistence;


import java.sql.SQLException;

import model.business.knowledge.Project;
import persistence.communications.DBConnectionManager;

public class PFProject {

	private static final String PROJECT_TABLE = "projects";
	
	private static final String COL_ID = "id";
	private static final String COL_NAME = "name";
	private static final String COL_DESCRIPTION = "description";
	private static final String COL_START_DATE = "startDate";
	private static final String COL_END_DATE = "endDate";
	private static final String COL_BUDGET = "budget";
	private static final String COL_QUANTITY_LINES = "quantityLines";
	private static final String COL_DOMAIN = "domain";
	private static final String COL_PROG_LANGUAGE = "progLanguage";
	private static final String COL_ESTIMATED_HOURS = "estimatedHours";
	
	
	public static void insert (Project project) throws SQLException {
		// Modificamos la base de datos y copiamos los ids asignados
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(project);
			//topic.setId(newTopic.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}