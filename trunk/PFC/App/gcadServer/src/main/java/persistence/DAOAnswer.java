package persistence;

import internationalization.AppInternationalization;

import java.sql.SQLException;
import java.util.List;

import persistence.utils.HibernateQuery;

import communication.DBConnectionManager;
import exceptions.NonExistentAnswerException;
import exceptions.NonExistentProposalException;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;

/**
 * This class allows to query and modify answers from the database
 */
public class DAOAnswer {
	
	private static final String PROPOSAL_CLASS = "Proposal";
	private static final String ANSWER_CLASS = "Answer";
	
	public static void insert(Answer answer, int proposalId) throws SQLException, NonExistentProposalException {
		HibernateQuery query;
		List<?> data;
		Proposal aux = null;
		Answer a;
		try {			
			// It's necessary to query first the parent proposal of the answer, in order to Hibernate
			// can update all the references and foreign key properly.
			query = new HibernateQuery("From " + PROPOSAL_CLASS + " Where id = ?", proposalId);
			data = DBConnectionManager.query(query);

			if(data.size() > 0) {
				aux = (Proposal) data.get(0);			
			}
			
			else 
				throw new NonExistentProposalException(AppInternationalization.getString("NonExistentProposalException"));
				
			// Set the topic parent to the proposal
			a = (Answer)answer.clone();
			DBConnectionManager.initTransaction();
			aux.add(a);
			Answer newAnswer = (Answer) DBConnectionManager.insert(a);
			// Set new id
			answer.setId(newAnswer.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
	}
	
	public static void update(Answer answer) throws SQLException, NonExistentAnswerException {
		// Get the answer stores in database and update that reference 
		HibernateQuery query;
		List<?> data;
		Answer old = null;
		
		try {
			query = new HibernateQuery("From " + ANSWER_CLASS + " Where id = ?", answer.getId());
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				old = (Answer)data.get(0);									
			}
			
			else 
				throw new NonExistentAnswerException(AppInternationalization.getString("NonExistentAnswerException"));
			
			DBConnectionManager.initTransaction();	
			
			old.setArgument(answer.getArgument());
			old.setDate(answer.getDate());
			old.setDescription(answer.getDescription());
			old.setTitle(answer.getTitle());
			old.setUser(answer.getUser());	
			old.setId(answer.getId());

			DBConnectionManager.update(old);
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
	}

	public static void delete(Answer a) throws SQLException, NonExistentAnswerException {
		try {
			DBConnectionManager.initTransaction();
			// Get the answer stores in database and delete that reference 
			DBConnectionManager.delete(queryAnswer(a.getId()));
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}

	public static Answer queryAnswer(int id) throws SQLException, NonExistentAnswerException {
		HibernateQuery query;
		List<?> data;
		Answer answer = null;
	
		query = new HibernateQuery("From " + ANSWER_CLASS + " Where id = ?", id);
		data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			answer = (Answer) ((Answer)data.get(0)).clone();									
		}
		else 
			throw new NonExistentAnswerException(AppInternationalization.getString("NonExistentAnswerException"));
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return answer;
	}
	

}
	