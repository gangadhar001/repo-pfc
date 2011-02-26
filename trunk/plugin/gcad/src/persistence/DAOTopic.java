package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.Topic;
import persistence.communications.DBConnectionManager;
import persistence.utils.HibernateQuery;

/**
 * This class allows to query and modify topics from the database
 */
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
			}
		return result;
	}
	
	public static void insert (Topic topic) throws SQLException {		
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(topic);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	
	public static void update (Topic topic) throws SQLException {		
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.update(topic.clone());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}

	public static void delete(Topic topic) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(topic);
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}
