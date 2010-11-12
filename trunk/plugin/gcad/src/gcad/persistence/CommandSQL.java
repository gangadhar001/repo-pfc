package gcad.persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Abstract class that represents a query or modification of a database.
 * 
 */
public abstract class CommandSQL implements Serializable {

	private static final long serialVersionUID = -3054901238912047586L;

	protected String sentence;
	protected Object[] params;
	
	public CommandSQL(String sentence, Object... params) {
		this.sentence = sentence;
		this.params = params;
	}
	
	public String getSentence() {
		return sentence;
	}

	public Object[] getParams() {
		return params;
	}

	public abstract PreparedStatement createStatement(Connection bd) throws SQLException;

}
