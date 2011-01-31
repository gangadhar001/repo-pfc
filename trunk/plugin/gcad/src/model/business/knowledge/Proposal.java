package model.business.knowledge;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * This class represents a Proposal
 */

@XmlAccessorType( XmlAccessType.FIELD )
public class Proposal extends AbstractKnowledge implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -404860160279086681L;

	private Categories category;
	
	// A proposal may have answers
	@XmlElement( name = "Answer" )
    private Set<Answer> answers = new HashSet<Answer>();
		
    public Proposal () {
		
	}

    public Proposal(String title, String description, Date date, Categories category, int state) {
		super(title, description, date);
		this.category = category;
	}
    
	public Set<Answer> getAnswers() {
		return answers;
	}
	
	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}
	
	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public void add(Answer answer) {
		answers.add(answer);
		
	}
	
	public void remove(Answer answer) {
		answers.remove(answer);
		
	}
	
	@Override
	public String toString () {
		StringBuffer result = new StringBuffer();
		Answer a;
		result.append("Proposal:\n");
		result.append("      " + title + "\n");
		result.append("      " + description + "\n");
		result.append("      " + date + "\n");
		result.append("      Answers:\n");
		for (Iterator<Answer> i = answers.iterator(); i.hasNext(); ) {
			a = (Answer) i.next();
			result.append("      " + a + "\n");
		}			
		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proposal other = (Proposal) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		return true;
	}
	
	public Object clone () {
		return this;
	}
	
}
