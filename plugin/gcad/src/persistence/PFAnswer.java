package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import persistence.commands.SQLCommand;
import persistence.commands.SQLCommandSentence;
import persistence.communications.DBConnectionManager;

import model.business.knowledge.AbstractKnowledge;
import model.business.knowledge.Answer;

/**
 * This class allows to query and insert answers into database
 */
public class PFAnswer {
	
	private static final String ANSWER_TABLE = "answers";
	
	private static final String COL_ID = "id";
	private static final String COL_NAME = "name";
	private static final String COL_DESCRIPTION = "description";
	private static final String COL_DATE = "date";
	private static final String COL_ARGUMENT = "argument";
	private static final String COL_EMPLOYEE_ID = "proposalId";
	private static final String COL_PROPOSAL_ID = "proposalId";
	
	/** 
	 * This method returns all the answers associated with a proposal
	 */
	public static ArrayList<Answer> queryAnswersFromProposal (int proposalId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		SQLCommand command;
		ResultSet data;
		AbstractKnowledge answer;
		ArrayList<Answer> result = new ArrayList<Answer>();
		
		// Consultamos la base de datos
		command = new SQLCommandSentence("SELECT * FROM " + ANSWER_TABLE
				+ " WHERE " + COL_PROPOSAL_ID + " = ?", proposalId);
		data = DBConnectionManager.query(command);
		
		while (data.next()) {		
				answer = new Answer();
				answer.setId(data.getInt(COL_ID));
				answer.setTitle(data.getString(COL_NAME));
				answer.setDescription(data.getString(COL_DESCRIPTION));
				answer.setDate(new Date(data.getTimestamp(COL_DATE).getTime()));
				// TODO: set argument
				result.add((Answer) answer);
		}
		data.close();
		
		return result;
	}

	public static void insert(Answer answer, int proposalId) throws SQLException {
		SQLCommand command;
		ResultSet data;
		
		// TODO: Cambiar con variables (no constantes). Añadir en pro o en contra
		// Insert the new answer into database 
		command = new SQLCommandSentence("INSERT INTO " + ANSWER_TABLE
				+ " (" + COL_NAME + ", " + COL_DESCRIPTION
				+ ", " + COL_DATE
				+ ", " + COL_EMPLOYEE_ID
				+ ", " + COL_PROPOSAL_ID
				+ ") VALUES (?, ?, ?, ?, ?)",
				answer.getTitle(), answer.getDescription(), answer.getDate(), 3, proposalId);
		DBConnectionManager.execute(command);
		
		// Retrieve the autonumber id assigned to the new answer
		command = new SQLCommandSentence("SELECT LAST_INSERT_ID()");			
		data = DBConnectionManager.query(command);
		data.next();
		answer.setId(data.getInt("LAST_INSERT_ID()"));
		data.close();
		
	}
	

}
	