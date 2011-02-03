package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;

import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import persistence.communications.DBConnectionManager;
import persistence.utils.HibernateQuery;


/**
 * This class allows to query and insert proposals into database
 */
public class DAOProposal {
	
	private static final String PROPOSAL_CLASS = "Proposal";
	
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
	@SuppressWarnings("unchecked")
//	public static ArrayList<Proposal> queryProposalsTopic(int topicId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {	
//		HibernateQuery query;
//		List<?> data;
//		ArrayList<Proposal> proposals = new ArrayList<Proposal>();
//		
//		query = new HibernateQuery("From " + PROPOSAL_CLASS + " Where " + COL_TOPIC_ID + " = ?", topicId);
//		data = DBConnectionManager.query(query);
//		
//		// If data are not obtained, it is because there are no proposals for this project
//		if(data.size() > 0) {
//			proposals = (ArrayList<Proposal>) data;
//			
//		}
//		
//		return proposals;
//	}
	
	/*private static ArrayList<Proposal> getProposals(ResultSet data) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
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
	}*/
	
	public static Proposal queryProposal(int id) throws SQLException {
		HibernateQuery query;
		List<?> data;
		Proposal result = null;

			query = new HibernateQuery("From " + PROPOSAL_CLASS + " Where Id = ?", id);
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				result= (Proposal) data.get(0);
				// Borramos los objetos leídos de la caché
				
			}
		return result;
	}
	
	public static void insert(Proposal proposal, int idParent) throws SQLException {
		// Modificamos la base de datos y copiamos los ids asignados
		Proposal newProposal;
		
		try {
			DBConnectionManager.initTransaction();
			// TODO: necesario hacer esto para que Hibernate actualice correctamente las claves ajenas y referencias
			Topic aux = DAOTopic.queryTopic(idParent);
			aux.add(proposal);
			DBConnectionManager.insert(proposal);
		} finally {
			DBConnectionManager.finishTransaction();
		}

	}
	
	public static void delete(Proposal pro) throws SQLException {
		// Modificamos la base de datos (automáticamente se
		// borran los datos adicionales si el usuario es médico)
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(pro);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}