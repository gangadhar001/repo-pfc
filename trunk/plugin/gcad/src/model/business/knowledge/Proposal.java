package model.business.knowledge;


import internationalization.BundleInternationalization;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * This class represents a Proposal
 */

@XmlAccessorType( XmlAccessType.FIELD )
public class Proposal extends AbstractKnowledge {
	
	// TODO: si hay herencia con categorias de analisis y diseño, hay que cambiarlo a protected
	
	// This attribute indicates if the proposal has been accepted or not yet.
	private int state;
	
	private Categories category;
	
	// A proposal may has and answers
	@XmlElement( name = "Answer" )
    private ArrayList<Answer> answers;
	
	public Proposal() {
		super();
		answers = new ArrayList<Answer>();
	}
	
	public Proposal(String title, String description, Date date, Categories category, int state) {
		super(title, description, date);
		this.state = state;
		this.category = category;
		answers = new ArrayList<Answer>();
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	
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
		return BundleInternationalization.getString("Title") +": " + super.getTitle() 
		+ " " + BundleInternationalization.getString("Description") +": " + super.getDescription() 
		+ " " + BundleInternationalization.getString("Date") +": " + super.getDate()
		+ " " + BundleInternationalization.getString("Category") +": " + getCategory()
		+ " " + BundleInternationalization.getString("State") +": "  + this.state;
	}
	
}
