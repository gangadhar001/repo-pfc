package gcad.communications;

import gcad.persistence.Agent;
import gcad.persistence.SQLCommand;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class allows to connect with the database, using the Agent
 */
public class DBConnection implements IDBConnection {

	private Agent agent;
	
	public DBConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		agent = Agent.getAgent();
	}
	
	public Agent getAgent() {
		return agent;
	}
	
	
	// Agent's methods

	public void open() throws SQLException, IOException {
		agent.open();
	}

	public void close() throws SQLException {
		agent.close();
	}

	public ResultSet query(SQLCommand command) throws SQLException {
		return agent.query(command);
	}

	public void execute(SQLCommand command) throws SQLException {
		agent.execute(command);
	}

	public void commit() throws SQLException {
		agent.commit();
	}

	public void rollback() throws SQLException {
		agent.rollback();
	}

}
