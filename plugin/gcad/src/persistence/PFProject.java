package persistence;


import java.sql.ResultSet;
import java.sql.SQLException;

import persistence.commands.SQLCommand;
import persistence.commands.SQLCommandSentence;
import persistence.communications.DBConnectionManager;

import model.business.knowledge.Project;
import model.business.knowledge.Proposal;

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
		SQLCommand command;
		ResultSet data;
		
		// Insert the new project into database 
		command = new SQLCommandSentence("INSERT INTO " + PROJECT_TABLE
				+ " (" + COL_NAME + ", " + COL_DESCRIPTION
				+ ", "	+ COL_START_DATE + ", " + COL_END_DATE
				+ ", " + COL_BUDGET + ", " + COL_QUANTITY_LINES
				+ ", " + COL_DOMAIN + ", " + COL_PROG_LANGUAGE
				+ ", " + COL_ESTIMATED_HOURS 
 				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
 				project.getName(), project.getDescription(), project.getStartDate(), project.getEndDate(), project.getBudget(), project.getQuantityLines(), project.getDomain(), project.getProgLanguage(), project.getEstimatedHours());
		DBConnectionManager.execute(command);
		
		// Retrieve the autonumber id assigned to the new project
		command = new SQLCommandSentence("SELECT LAST_INSERT_ID()");			
		data = DBConnectionManager.query(command);
		data.next();
		project.setId(data.getInt("LAST_INSERT_ID()"));
		data.close();
	}
}