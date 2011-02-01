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
import persistence.utils.HibernateUtil;

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
	public static ArrayList<Proposal> queryProposalsTopic(int topicId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {	
		HibernateQuery query;
		List<?> data;
		ArrayList<Proposal> proposals = new ArrayList<Proposal>();
		
		query = new HibernateQuery("From " + PROPOSAL_CLASS + " Where " + COL_TOPIC_ID + " = ?", topicId);
		data = DBConnectionManager.query(query);
		
		// If data are not obtained, it is because there are no proposals for this project
		if(data.size() > 0) {
			proposals = (ArrayList<Proposal>) data;
			for(Object object : proposals) {
				DBConnectionManager.clearCache(object);
			}
		}
		
		return proposals;
	}
	
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
	
	public static void insert (Proposal proposal) throws SQLException {
		// Modificamos la base de datos y copiamos los ids asignados
		Proposal newProposal;
		
		DBConnectionManager.initTransaction();
//		 Session session = HibernateUtil.getSessionFactory().openSession();
//	        session.beginTransaction();
//		Topic lo = (Topic) session.createQuery("From Topic where Id=2").list().get(0);
//	      lo.add(proposal);
	     Topic lo = DAOTopic.queryTopic(2);
	     lo.add(proposal);
	      DBConnectionManager.insert(proposal);
	      DBConnectionManager.finishTransaction();
	      
//	    session.save(proposal);
//	    session.getTransaction().commit();
//	    session.close();
//			
		
	}
//		} finally {
//			DBConnectionManager.finishTransaction();
//		}
//		TopicWrapper t = new TopicWrapper();
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//        Topic lo = (Topic) session.createQuery("From Topic where Id=2").list().get(0);
//        lo.add(proposal);
//      session.save(proposal);
//      session.getTransaction().commit();
	

	public static void delete(Proposal pro) throws SQLException {
	}
}