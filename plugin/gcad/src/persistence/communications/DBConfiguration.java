package gcad.communications;

/**
 * This class represents the configuration of the database connection
 */
public class DBConfiguration {

	private String DBip;
	private int DBport;
	
	public DBConfiguration() {
		DBip = "127.0.0.1";
		DBport = 3306;
	}
	
	public DBConfiguration(String DBip, int DBport) {
		this.DBip = DBip;
		this.DBport = DBport;
	}

	public String getDBip() {
		return DBip;
	}

	public void setDBip(String dBip) {
		DBip = dBip;
	}

	public int getDBport() {
		return DBport;
	}

	public void setDBport(int dBport) {
		DBport = dBport;
	}
		
}
