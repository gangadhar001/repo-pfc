package persistence;

import internationalization.AppInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.DBConnectionManager;
import exceptions.NonExistentTopicException;

import model.business.knowledge.Topic;
import persistence.utils.HibernateQuery;

/**
 * This class allows to query and modify topics from the database
 */
public class DAOTopic {
	
	private static final String TOPIC_CLASS = "Topic";
	
	private static final String COL_PROJECT_ID = "projectId";
			
	public static Topic queryTopic(int id) throws SQLException, NonExistentTopicException {
		HibernateQuery query;
		List<?> data;
		Topic result = null;

		query = new HibernateQuery("From " + TOPIC_CLASS + " Where id = ?", id);
		data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			result = (Topic) ((Topic) data.get(0)).clone();			
		}
		
		else
			throw new NonExistentTopicException(AppInternationalization.getString("NonExistentTopicException"));
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}
	
	public static ArrayList<Topic> queryTopicsProject(int projectId) throws SQLException, NonExistentTopicException {
		HibernateQuery query;
		List<?> data;
		ArrayList<Topic> result = new ArrayList<Topic>();

			query = new HibernateQuery("From " + TOPIC_CLASS + " Where " + COL_PROJECT_ID + " = ?", projectId);
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				for(Object o: data) {
					result.add((Topic)((Topic)o).clone());
				}
			}
			
			else
				throw new NonExistentTopicException(AppInternationalization.getString("NonExistentTopicException"));
			
			// Clear cache
			for(Object object : data) {
				DBConnectionManager.clearCache(object);
			}
			
		return result;
	}
	
	public static void insert (Topic topic) throws SQLException {		
		try {
			DBConnectionManager.initTransaction();
			Topic insertedTopic = (Topic) DBConnectionManager.insert(topic.clone());
			// Set the id
			topic.setId(insertedTopic.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	
	public static void update (Topic topic) throws SQLException, NonExistentTopicException {		
		// Get the proposal stores in database and update that reference 
//		HibernateQuery query;
//		List<?> data;
//		Topic old = null;
//		
		try {
//			query = new HibernateQuery("From " + TOPIC_CLASS + " Where id = ?", topic.getId());
//			data = DBConnectionManager.query(query);
//	
//			if(data.size() > 0) {
//				old = (Topic)data.get(0);									
//			}
//			
//			else
//				throw new NonExistentTopicException(AppInternationalization.getString("NonExistentTopicException"));
//			
			DBConnectionManager.initTransaction();	
//			
//			old.setDate(topic.getDate());
//			old.setProject(topic.getProject());
//			old.setDescription(topic.getDescription());
//			old.setTitle(topic.getTitle());
//			old.setUser(topic.getUser());
//			old.setProposals(topic.getProposals());

			DBConnectionManager.update(topic.clone());
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
//		// Clear cache
//		for(Object object : data) {
//			DBConnectionManager.clearCache(object);
//		}
	}

	public static void delete(Topic topic) throws SQLException, NonExistentTopicException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(queryTopic(topic.getId()));
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}
