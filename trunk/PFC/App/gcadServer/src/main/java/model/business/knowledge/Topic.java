package model.business.knowledge;


import java.util.Date;
import java.util.HashSet;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents a Topic
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class Topic extends Knowledge {

	private static final long serialVersionUID = -485599952417592267L;
	
	@XmlElement( name = "Proposal" )
	private Set<Proposal> proposals = new HashSet<Proposal>();
	private Project project;
	
	public Topic () {
		
	}

	public Topic(String title, String description, Date creationDate) {
		super(title, description, creationDate);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Set<Proposal> getProposals() {
		return proposals;
	}

	public void setProposals(Set<Proposal> proposals) {
		this.proposals = proposals;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}


	public void add(Proposal proposal) {
		proposals.add(proposal);
	}
	
	public void remove(Proposal proposal) {
//		// In order to avoid problems of references, check explicit if the proposals are equals
//		for (Proposal p: proposals)
//			if (proposal.equals(p))
				proposals.remove(proposal);
	}	
	
	public String toString() {
		return title;
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
		else if (obj instanceof Topic) {
			Topic other = (Topic) obj;
//			result = (title.equals(other.getTitle()) && date.equals(other.getDate()) &&
//					description.equals(other.getDescription()) && user.equals(other.getUser()) && proposals.equals(other.getProposals()) 
//					&& project.equals(other.getProject()));
			result = (id == other.getId());
			}
		return result;
	}
	
	public int hashCode() {
		return id;
	}
	
	public Object clone () {
		Topic t;
		Set<Proposal> proposals = new HashSet<Proposal>();
		t = new Topic(getTitle(), getDescription(), getDate());
		t.setId(getId());
		if (getProject() != null)
			t.setProject((Project)getProject().clone());
		if (getUser() != null)
			t.setUser((User)getUser().clone());
		for (Proposal p: getProposals())
			proposals.add((Proposal)p.clone());
		t.setProposals(proposals);
		return t;
	}
	
}