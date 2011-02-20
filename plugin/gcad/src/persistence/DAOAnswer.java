package persistence;

import java.sql.SQLException;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import persistence.communications.DBConnectionManager;

/**
 * This class allows to query and modify answers from the database
 */
public class DAOAnswer {
	
	/** 
	 * This method returns all the answers associated with a proposal
	 */
//	TODO: public static ArrayList<Answer> queryAnswersFromProposal (int proposalId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
//		SQLCommand command;
//		ResultSet data;
//		AbstractKnowledge answer;
//		ArrayList<Answer> result = new ArrayList<Answer>();
//		
//		// Consultamos la base de datos
//		command = new SQLCommandSentence("SELECT * FROM " + ANSWER_TABLE
//				+ " WHERE " + COL_PROPOSAL_ID + " = ?", proposalId);
//		data = DBConnectionManager.query(command);
//		
//		while (data.next()) {		
//				answer = new Answer();
//				answer.setId(data.getInt(COL_ID));
//				answer.setTitle(data.getString(COL_NAME));
//				answer.setDescription(data.getString(COL_DESCRIPTION));
//				answer.setDate(new Date(data.getTimestamp(COL_DATE).getTime()));
//				// TODO: set argument
//				result.add((Answer) answer);
//		}
//		data.close();
//		
//		return result;
//	}

	public static void insert(Answer answer, int proposalId) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			// It's necessary to query first the parent proposal of the answer, in order to Hibernate
			// can update all the references and foreign key properly.
			Proposal aux = DAOProposal.queryProposal(proposalId);
			aux.add(answer);
			DBConnectionManager.insert(answer);
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
	}
	
	public static void update(Answer answer) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.update(answer.clone());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}

	public static void delete(Answer a) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(a);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	

}
	