package model.business.control;

import java.sql.SQLException;

import model.business.knowledge.LogEntry;

import persistence.DAOLog;

import communication.IConnectionLog;


/**
 * Class that receives the messages generated by the server and stored it into the database.
 */
public class DBLogManager implements IConnectionLog {

	public DBLogManager() {
	}
	
	public void putMessage(String messageType, String message) throws SQLException {
		LogEntry entry = new LogEntry(null, messageType, message);
		DAOLog.insert(entry);
	}
	
	public void putMessage(String user, String messageType, String message) throws SQLException {
		LogEntry entry = new LogEntry(user, messageType, message);
		DAOLog.insert(entry);
	}

	@Override
	public void updateConnectedClients(int n) {
		// These messages are not stored in the database		
	}

}