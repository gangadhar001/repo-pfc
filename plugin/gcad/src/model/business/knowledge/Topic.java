package model.business.knowledge;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType( XmlAccessType.FIELD )
public class Topic implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -485599952417592267L;
	
	private int id;
	private String title;
	private Date creationDate;
	@XmlElement( name = "Proposal" )
	private Set<Proposal> proposals = new HashSet<Proposal>();
	private Project project;
	
	public Topic () {
		
	}

	public Topic(String title, Date creationDate) {
		this.title = title;
		this.creationDate = creationDate;
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

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void add(Proposal proposal) {
		proposals.add(proposal);
	}
	
	public void remove(Proposal proposal) {
		proposals.remove(proposal);
	}	
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		Proposal p;
		result.append("Topic:\n");
		result.append("      " + title + "\n");
		result.append("      " + creationDate + "\n");
		result.append("      Project:\n");
		result.append("      " + project + "\n");
		result.append("      Proposals:\n");
		for (Iterator<Proposal> i = proposals.iterator(); i.hasNext(); ) {
			p = (Proposal) i.next();
			result.append("      " + p + "\n");
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
		Topic other = (Topic) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (id != other.id)
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (proposals == null) {
			if (other.proposals != null)
				return false;
		} else if (!proposals.equals(other.proposals))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}
