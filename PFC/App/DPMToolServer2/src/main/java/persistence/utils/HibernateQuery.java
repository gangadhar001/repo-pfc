package persistence.utils;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * This abstract class represents a query from a database
 */
public class HibernateQuery implements Serializable {

	private static final long serialVersionUID = 8743373028825629890L;

	protected String sentence;
	protected Object[] params;
	
	public HibernateQuery(String sentence, Object... params) {
		this.sentence = sentence;
		this.params = params;
	}

	public String getSentence() {
		return sentence;
	}

	public Object[] getParams() {
		return params;
	}
	
	public Query createQuery(Session session) throws HibernateException {
		Query consulta;
		int i;
		
		consulta = session.createQuery(sentence);
		for(i = 0; i < params.length; i++) {
			consulta.setParameter(i, params[i]);
		}
		return consulta;
	}

}
