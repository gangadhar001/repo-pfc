package model.business.knowledge;

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
public class Proposal extends AbstractKnowledge {
	
	private Categories category;
	
	// A proposal may have answers
	@XmlElement( name = "Answer" )
    private Set<Answer> answers = new HashSet<Answer>();;
		
	public Proposal(String title, String description, Date date, Categories category, int state, Employee e) {
		super(title, description, date, e);
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
	
}
