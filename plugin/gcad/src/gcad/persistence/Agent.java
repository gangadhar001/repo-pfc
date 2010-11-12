package gcad.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Agent Database
 */
public class Agent {

	private static final String NAME_BD = "bdgcad";
	private static final String USER_BD = "gcad";
	private static final String PASS_BD = "gcad";
	
	private static Agent instance = null;
	private Connection connection;
	private String ip;
	private int port;
	
	protected Agent() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	}
	
	public static Agent getAgente() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(instance == null) {
			instance = new Agent();
		}
		return instance;
	}
	
	
	public Connection getConnection() {
		return connection;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void open() throws SQLException {
		String url;
		
		if(connection == null || connection.isClosed()) {
			url = "jdbc:mysql://" + ip + ":" + String.valueOf(port) + "/" + NAME_BD + "?user=" + USER_BD + "&password=" + PASS_BD;
			connection = DriverManager.getConnection(url);
			connection.setAutoCommit(false); 
		}
	}
	
	public void close() throws SQLException {
		connection.close();
	}
	
	public ResultSet query(CommandSQL command) throws SQLException {
		PreparedStatement sentence;
		ResultSet result;

		sentence = command.createStatement(connection);
		result = sentence.executeQuery();
		
		return result;
	}

	public void execute(CommandSQL command) throws SQLException {
		PreparedStatement sentence;
		
		sentence = command.createStatement(connection);
		sentence.executeUpdate();
	}
	
	public void commit() throws SQLException {
		connection.commit();
	}

	public void rollback() throws SQLException {
		connection.rollback();
	}
	
}
