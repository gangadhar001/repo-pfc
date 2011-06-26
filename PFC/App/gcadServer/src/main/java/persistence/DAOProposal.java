package persistence;

import internationalization.AppInternationalization;

import java.sql.SQLException;
import java.util.List;

import communication.DBConnectionManager;
import exceptions.NonExistentProposalException;
import exceptions.NonExistentTopicException;

import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import persistence.utils.HibernateQuery;

/**
 * This class allows to query and modify proposals from the database
 */
public class DAOProposal {
	
	private static final String PROPOSAL_CLASS = "Proposal";
	private static final String TOPIC_CLASS = "Topic";
	
	
	public static Proposal queryProposal(int id) throws SQLException, NonExistentProposalException {
		HibernateQuery query;
		List<?> data;
		Proposal result = null;

			query = new HibernateQuery("From " + PROPOSAL_CLASS + " Where id = ?", id);
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				result = (Proposal) ((Proposal)data.get(0)).clone();
				// Clear cache
				for(Object object : data) {
					DBConnectionManager.clearCache(object);
				}				
			}
			
			else 
				throw new NonExistentProposalException(AppInternationalization.getString("NonExistentProposalException"));
				
		return result;
	}
	
	
	public static void insert(Proposal proposal, int idParent) throws SQLException, NonExistentTopicException {
		HibernateQuery query;
		List<?> data;
		Topic aux = null;
		Proposal p;
		try {			
			// It's necessary to query first the parent topic of the proposal, in order to Hibernate
			// can update all the references and foreign key properly.
			query = new HibernateQuery("From " + TOPIC_CLASS + " Where id = ?", idParent);
			data = DBConnectionManager.query(query);

			if(data.size() > 0) {
				aux = (Topic) data.get(0);			
			}
			
			else 
				throw new NonExistentTopicException(AppInternationalization.getString("NonExistentTopicException"));
			
			p = (Proposal)proposal.clone();
			DBConnectionManager.initTransaction();
			// Set the topic parent to the proposal
			aux.add(p);
			Proposal newProposal = (Proposal) DBConnectionManager.insert(p);
			// Set new id
			proposal.setId(newProposal.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
	}
	
	public static void update(Proposal p) throws SQLException, NonExistentProposalException {		
		// Get the proposal stores in database and update that reference 
		HibernateQuery query;
		List<?> data;
		Proposal old = null;
		
		try {
			query = new HibernateQuery("From " + PROPOSAL_CLASS + " Where id = ?", p.getId());
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				old = (Proposal)data.get(0);									
			}
			
			else 
				throw new NonExistentProposalException(AppInternationalization.getString("NonExistentProposalException"));
			
			DBConnectionManager.initTransaction();	
			
			old.setAnswers(p.getAnswers());
			old.setCategory(p.getCategory());
			old.setDate(p.getDate());
			old.setDescription(p.getDescription());
			old.setTitle(p.getTitle());
			old.setUser(p.getUser());	
			old.setId(p.getId());

			DBConnectionManager.update(old);
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
	}
	
	public static void delete(Proposal pro) throws SQLException, NonExistentProposalException {
		try {
			DBConnectionManager.initTransaction();
			// Get the proposal stores in database and delete that reference 
			DBConnectionManager.delete(queryProposal(pro.getId()));
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}