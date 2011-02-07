package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.Topic;
import persistence.communications.DBConnectionManager;
import persistence.utils.HibernateQuery;

public class DAOTopic {
	
	private static final String TOPIC_CLASS = "Topic";
	
	private static final String COL_PROJECT_ID = "projectId";
			
	public static Topic queryTopic(int id) throws SQLException {
		HibernateQuery query;
		List<?> data;
		Topic result = null;

			query = new HibernateQuery("From " + TOPIC_CLASS + " Where id = ?", id);
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				result= (Topic) data.get(0);
				// Borramos los objetos leídos de la caché
				
			}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Topic> queryTopicsProject(int projectId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		HibernateQuery query;
		List<?> data;
		ArrayList<Topic> result = new ArrayList<Topic>();

			query = new HibernateQuery("From " + TOPIC_CLASS + " Where " + COL_PROJECT_ID + " = ?", projectId);
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				result = (ArrayList<Topic>) data;
				// Borramos los objetos leídos de la caché
				
			}
		return result;
	}
	
	/*private static ArrayList<Topic> getTopics(ResultSet data) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<Topic> topics = new ArrayList<Topic>();
		ArrayList<Proposal> proposals;
		Topic topic;
		
		do {
			 topic = new Topic();
             topic.setId(data.getInt(COL_ID));
             topic.setTitle(data.getString(COL_NAME));
             // TODO: set el project del topic
             // Set proposals of this topic
             proposals = PFProposal.queryProposalsTopic(topic.getId());
             topic.setProposals(proposals);
             topics.add(topic);
		} while(data.next());
		data.close();
		
		return topics;
	}*/
	
	public static void insert (Topic topic) throws SQLException {		
		// Modificamos la base de datos y copiamos los ids asignados
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(topic);
			//topic.setId(newTopic.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}

	public static void delete(Topic topic) throws SQLException {
		// Modificamos la base de datos (automáticamente se
		// borran los datos adicionales si el usuario es médico)
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(topic);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}
