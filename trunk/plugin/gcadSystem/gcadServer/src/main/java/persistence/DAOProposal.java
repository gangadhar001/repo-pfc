package persistence;

import java.sql.SQLException;
import java.util.List;

import communication.DBConnectionManager;

import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import persistence.utils.HibernateQuery;

/**
 * This class allows to query and modify proposals from the database
 */
public class DAOProposal {
	
	private static final String PROPOSAL_CLASS = "Proposal";
	
//	private static final String COL_ID = "id";
//	private static final String COL_NAME = "name";
//	private static final String COL_DESCRIPTION = "description";
//	private static final String COL_DATE = "date";
//	private static final String COL_CATEGORY = "category";
//	private static final String COL_EMPLOYEE_ID = "employeeId";
//	private static final String COL_TOPIC_ID = "topicId";
	
	
	public static Proposal queryProposal(int id) throws SQLException {
		HibernateQuery query;
		List<?> data;
		Proposal result = null;

			query = new HibernateQuery("From " + PROPOSAL_CLASS + " Where id = ?", id);
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				result= (Proposal) data.get(0);
				// Borramos los objetos leídos de la caché
				
			}
		return result;
	}
	
	public static void insert(Proposal proposal, int idParent) throws SQLException {		
		try {
			DBConnectionManager.initTransaction();
			// It's necessary to query first the parent topic of the answer, in order to Hibernate
			// can update all the references and foreign key properly.
			Topic aux = DAOTopic.queryTopic(idParent);
			aux.add(proposal);
			DBConnectionManager.insert(proposal);
		} finally {
			DBConnectionManager.finishTransaction();
		}

	}
	
	public static void update(Proposal p) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.update(p.clone());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	
	public static void delete(Proposal pro) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(pro);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}