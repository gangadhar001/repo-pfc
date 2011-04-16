package persistence;

import java.sql.SQLException;

import communication.DBConnectionManager;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;

/**
 * This class allows to query and modify answers from the database
 */
public class DAOAnswer {
	
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
	