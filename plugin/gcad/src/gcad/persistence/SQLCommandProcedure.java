package gcad.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class representing a query or modification of a database, 
 * using a stored procedure.
 */
public class SQLCommandProcedure extends SQLCommand {

	private static final long serialVersionUID = -6249425775295737777L;

	public SQLCommandProcedure(String sentence, Object... params) {
		super(sentence, params);
	}
	
	public PreparedStatement createStatement(Connection bd) throws SQLException {
		CallableStatement callable;
		int i;
		
		callable = bd.prepareCall(sentence);
		for(i = 0; i < params.length; i++) {
			callable.setObject(i + 1, params[i]);
		}
		return callable;
	}

}
