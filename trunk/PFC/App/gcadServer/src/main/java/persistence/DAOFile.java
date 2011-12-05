package persistence;

import internationalization.AppInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.File;
import persistence.utils.HibernateQuery;

import communication.DBConnectionManager;

import exceptions.NonExistentFileException;

/**
 * This class allows to query and modify answers from the database
 */
public class DAOFile {
	
	private static final String FILE_CLASS = "File";
	
	private static final String COL_ID = "id";
	private static final String COL_KNOWLEDGE_ID = "knowledgeId";	
	
	public static void insert(File file) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			File f = (File) DBConnectionManager.insert(file.clone());
			file.setId(f.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
	
	public static File queryFile(int id) throws SQLException, NonExistentFileException {
		HibernateQuery query;
		List<?> data;
		File result = null;

		query = new HibernateQuery("from " + FILE_CLASS + " WHERE " + COL_ID + " = ?", id);
		data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			result = ((File) ((File)data.get(0)).clone());			
		}
		else
			throw new NonExistentFileException(AppInternationalization.getString("NonExistentFileException"));
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}
	
	public static List<File> queryAllFiles(int knowledgeId) throws SQLException {
		HibernateQuery query;
		List<?> data;
		List<File> result = new ArrayList<File>();

		query = new HibernateQuery("from " + FILE_CLASS + " WHERE " + COL_KNOWLEDGE_ID + " = ?", knowledgeId);
		data = DBConnectionManager.query(query);

		if(data.size() > 0) {
			for(Object o: data) {
				result.add((File) ((File)o).clone());
			}				
		}	
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return result;
	}
	
	public static void delete(File f) throws SQLException, NonExistentFileException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(queryFile(f.getId()));
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}	

}
	