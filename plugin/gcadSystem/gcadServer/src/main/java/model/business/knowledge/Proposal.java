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
public class Proposal extends Knowledge implements Serializable {
	
	private static final long serialVersionUID = -404860160279086681L;
	
	private Categories category;	
	// A proposal may have answers
	@XmlElement( name = "Answer" )
    private Set<Answer> answers = new HashSet<Answer>();
		
    public Proposal () {		
	}
    
    public Proposal(String title, String description, Date date, Categories category) {
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
	public String toString() {
		return title;
	}	
	
	public String getInfo () {
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
		boolean result = false;
		if (this == obj)
			result = true;
		else if (obj == null)
			result = false;
		else if (getClass() != obj.getClass())
			result = false;
		else if (obj instanceof Proposal) {
			Proposal other = (Proposal) obj;
//			result = (title.equals(other.getTitle()) && date.equals(other.getDate()) &&
//					description.equals(other.getDescription()) && user.equals(other.getUser()) && answers.equals(other.getAnswers()) 
//					&& category.equals(other.getCategory()));
//			}
			result = (id == other.getId());
		}
		return result;
	}	
	
	public int hashCode() {
		return id;
	}
	
	public Object clone () {
		Proposal p;
		HashSet<Answer> answers = new HashSet<Answer>();
		p = new Proposal(getTitle(), getDescription(), getDate(), getCategory());
		p.setId(getId());
		p.setUser((User)getUser().clone());
		for (Answer a: getAnswers())
			answers.add((Answer)a.clone());
		p.setAnswers(answers);
		return p;
	}	
	
}
