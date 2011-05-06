package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.DBConnectionManager;

import model.business.knowledge.LogEntry;
import persistence.utils.HibernateQuery;

/**
 * This class allows to query and modify topics from the database
 */
public class DAOLog {
	
	private static final String LOG_CLASS = "LogEntry";
			
	public static List<LogEntry> queryLog(int id) throws SQLException {
		HibernateQuery query;
		List<?> data;
		List<LogEntry> result = new ArrayList<LogEntry>();

		query = new HibernateQuery("From " + LOG_CLASS);
		data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			for(Object le: data)
				result.add((LogEntry) ((LogEntry)le).clone());	
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}

	public static void insert (LogEntry log) throws SQLException {		
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(log.clone());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}	
}
