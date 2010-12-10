package persistence.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase que representa una consulta o modificación sobre una base de
 * datos escrita en forma de sentencia SQL.
 */
public class SQLCommandSentence extends SQLCommand {

	private static final long serialVersionUID = 3132897363533160582L;

	public SQLCommandSentence(String sentence, Object... params) {
		super(sentence, params);
	}
	
	public PreparedStatement createStatement(Connection bd) throws SQLException {
		PreparedStatement prepared;
		int i;
		
		prepared = bd.prepareStatement(sentence);
		for(i = 0; i < params.length; i++) {
			prepared.setObject(i + 1, params[i]);
		}
		return prepared;
	}

}
