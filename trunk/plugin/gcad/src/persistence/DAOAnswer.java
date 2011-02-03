package persistence;

import java.sql.SQLException;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import persistence.communications.DBConnectionManager;

/**
 * This class allows to query and insert answers into database
 */
public class DAOAnswer {
	
	private static final String ANSWER_TABLE = "answers";
	
	private static final String COL_ID = "id";
	private static final String COL_NAME = "name";
	private static final String COL_DESCRIPTION = "description";
	private static final String COL_DATE = "date";
	private static final String COL_ARGUMENT = "argument";
	private static final String COL_EMPLOYEE_ID = "employeeId";
	private static final String COL_PROPOSAL_ID = "proposalId";
	
	/** 
	 * This method returns all the answers associated with a proposal
	 */
//	public static ArrayList<Answer> queryAnswersFromProposal (int proposalId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
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
			// TODO: necesario hacer esto para que Hibernate actualice correctamente las claves ajenas y referencias
			Proposal aux = DAOProposal.queryProposal(proposalId);
			aux.add(answer);
			DBConnectionManager.insert(answer);
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
	}

	public static void delete(Answer a) throws SQLException {
		// Modificamos la base de datos (automáticamente se
		// borran los datos adicionales si el usuario es médico)
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(a);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	

}
	