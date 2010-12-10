package persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import persistence.commands.SQLCommand;

/**
 * Agent Database
 * Singleton Pattern is applied
 */
public class Agent {
	
	private static Agent instance = null;
	private Connection connection;
	private String ip;
	private int port;
	
	protected Agent() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	}
	
	public static Agent getAgent() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
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

	public void open() throws SQLException, IOException {
		String url;
		// Read database configuration from properties file
		Properties configFile = new Properties();
		configFile.load(this.getClass().getClassLoader().getResourceAsStream("/databaseConfiguration.properties"));
		// Open connection
		if(connection == null || connection.isClosed()) {
			url = "jdbc:mysql://" + ip + ":" + String.valueOf(port) + "/" + configFile.getProperty("DBName") + "?user=" + configFile.getProperty("DBUser") + "&password=" + configFile.getProperty("DBPassword");
			connection = DriverManager.getConnection(url);
			connection.setAutoCommit(false); 
		}
	}
	
	public void close() throws SQLException {
		connection.close();
	}
	
	public ResultSet query(SQLCommand command) throws SQLException {
		PreparedStatement sentence;
		ResultSet result;

		sentence = command.createStatement(connection);
		result = sentence.executeQuery();
		
		return result;
	}

	public void execute(SQLCommand command) throws SQLException {
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