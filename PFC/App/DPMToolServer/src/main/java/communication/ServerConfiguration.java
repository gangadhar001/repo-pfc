package communication;

/**
 * This class represents the server configuration, includes database configuration
 */
public class ServerConfiguration {

	private String DBIp;
	private int DBPort;
	private String DBSchema;
	private String DBUser;
	private String DBPassword;
	
	private int serverPort;
	
	public ServerConfiguration() {
		DBIp = "127.0.0.1";
		DBPort = 3306;
		DBSchema = "bdgcad";
		DBUser = "gcad";
		DBPassword = "gcad";
		serverPort = 2995;		
	} 	
	
	public ServerConfiguration(String dBIp, String dBSchema, String dBUser, String dBPassword, int dBPort, int serverPort) {
		super();
		DBIp = dBIp;
		DBSchema = dBSchema;
		DBUser = dBUser;
		DBPassword = dBPassword;
		DBPort = dBPort;
		this.serverPort = serverPort;
	}

	public String getDBIp() {
		return DBIp;
	}

	public void setDBIp(String dBIp) {
		DBIp = dBIp;
	}

	public String getDBSchema() {
		return DBSchema;
	}

	public void setDBSchema(String dBSchema) {
		DBSchema = dBSchema;
	}

	public String getDBUser() {
		return DBUser;
	}

	public void setDBUser(String dBUser) {
		DBUser = dBUser;
	}

	public String getDBPassword() {
		return DBPassword;
	}

	public void setDBPassword(String dBPassword) {
		DBPassword = dBPassword;
	}

	public int getDBPort() {
		return DBPort;
	}

	public void setDBPort(int dBPort) {
		DBPort = dBPort;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerConfiguration other = (ServerConfiguration) obj;
		if (DBIp == null) {
			if (other.DBIp != null)
				return false;
		} else if (!DBIp.equals(other.DBIp))
			return false;
		if (DBPassword == null) {
			if (other.DBPassword != null)
				return false;
		} else if (!DBPassword.equals(other.DBPassword))
			return false;
		if (DBPort != other.DBPort)
			return false;
		if (DBSchema == null) {
			if (other.DBSchema != null)
				return false;
		} else if (!DBSchema.equals(other.DBSchema))
			return false;
		if (DBUser == null) {
			if (other.DBUser != null)
				return false;
		} else if (!DBUser.equals(other.DBUser))
			return false;
		if (serverPort != other.serverPort)
			return false;
		return true;
	}	
}
