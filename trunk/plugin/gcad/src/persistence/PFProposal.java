package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import persistence.commands.SQLCommand;
import persistence.commands.SQLCommandSentence;
import persistence.communications.DBConnectionManager;

/**
 * This class allows to query and insert proposals into database
 */
public class PFProposal {
	
	private static final String PROPOSAL_TABLE = "proposals";
	
	private static final String COL_ID = "id";
	private static final String COL_NAME = "name";
	private static final String COL_DESCRIPTION = "description";
	private static final String COL_DATE = "date";
	private static final String COL_CATEGORY = "category";
	private static final String COL_EMPLOYEE_ID = "employeeId";
	private static final String COL_TOPIC_ID = "topicId";
	
	/** 
	 * This method returns all proposals and answers hierarchy of a project, keeping the tree hierarchy.
	 */
	public static ArrayList<Proposal> queryProposalsTopic(int topicId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {	
		SQLCommand command;
		ResultSet data;
		ArrayList<Proposal> proposals = new ArrayList<Proposal>();
		
		command = new SQLCommandSentence("SELECT * FROM " + PROPOSAL_TABLE
				+ " WHERE " + COL_TOPIC_ID + " = ?", topicId);
		data = DBConnectionManager.query(command);
		data.next();
		
		// If data are not obtained, it is because there are no proposals for this project
		if(data.getRow() > 0) {
			proposals = getProposals(data);
		}
		
		return proposals;
	}
	
	private static ArrayList<Proposal> getProposals(ResultSet data) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<Proposal> proposals = new ArrayList<Proposal>();
		ArrayList<Answer> answers;
		Proposal proposal;
		
		do {
			proposal = new Proposal();
			proposal.setId(data.getInt(COL_ID));
			proposal.setTitle(data.getString(COL_NAME));
			proposal.setDescription(data.getString(COL_DESCRIPTION));
			proposal.setDate(new Date(data.getTimestamp(COL_DATE).getTime()));
			proposal.setCategory(Categories.valueOf(data.getString(COL_CATEGORY)));
			// Set answers of this proposal
			answers = PFAnswer.queryAnswersFromProposal(proposal.getId());
			for (Answer a: answers) {
				proposal.add(a);
			}
			proposals.add(proposal);
		} while(data.next());
		data.close();
		
		return proposals;
	}
	
	public static void insert (Proposal proposal, int topicId) throws SQLException {
		SQLCommand command;
		ResultSet data;
		
		// TODO: Cambiar con variables (no constantes)
		// Insert the new proposal into database 
		command = new SQLCommandSentence("INSERT INTO " + PROPOSAL_TABLE
				+ " (" + COL_NAME + ", " + COL_DESCRIPTION
				+ ", " + COL_DATE + ", " + COL_CATEGORY
				+ ", " + COL_EMPLOYEE_ID
				+ ", " + COL_TOPIC_ID
				+ ") VALUES (?, ?, ?, ?, ?, ?)",
				proposal.getTitle(), proposal.getDescription(), proposal.getDate(), proposal.getCategory().toString(), 3, topicId);
		DBConnectionManager.execute(command);
		
		// Retrieve the autonumber id assigned to the new proposal
		command = new SQLCommandSentence("SELECT LAST_INSERT_ID()");			
		data = DBConnectionManager.query(command);
		data.next();
		proposal.setId(data.getInt("LAST_INSERT_ID()"));
		data.close();
	}

	public static void delete(Proposal pro) throws SQLException {
		SQLCommand command;
		
		command = new SQLCommandSentence("DELETE FROM " + PROPOSAL_TABLE
				+ " WHERE " + COL_ID + " = ? ", 
				pro.getId());
		DBConnectionManager.execute(command);
		
	}
}