package model.business.knowledge;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * This class represents a Project from a Company
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class Project implements Serializable {

	private static final long serialVersionUID = -2054443822372394331L;
	
	private int id;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private double budget;
	private int quantityLines;
	private String domain;
	private String progLanguage;
	private int estimatedHours;
		
	public Project () {
		
	}

	public Project(String name, String description, Date startDate,
			Date endDate, double budget, int quantityLines, String domain,
			String progLanguage, int estimatedHours) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.budget = budget;
		this.quantityLines = quantityLines;
		this.domain = domain;
		this.progLanguage = progLanguage;
		this.estimatedHours = estimatedHours;
	}

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public double getBudget() {
		return budget;
	}
	
	public void setBudget(double budget) {
		this.budget = budget;
	}

	public int getQuantityLines() {
		return quantityLines;
	}

	public void setQuantityLines(int quantityLines) {
		this.quantityLines = quantityLines;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getProgLanguage() {
		return progLanguage;
	}

	public void setProgLanguage(String progLanguage) {
		this.progLanguage = progLanguage;
	}

	public int getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(int estimatedHours) {
		this.estimatedHours = estimatedHours;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Project:\n");		
		result.append("      " + name + "\n");
		result.append("      " + description + "\n");
		result.append("      " + startDate + "\n");
		result.append("      " + endDate + "\n");
		result.append("      " + budget + "\n");
		result.append("      " + quantityLines + "\n");
		result.append("      " + domain + "\n");
		result.append("      " + progLanguage + "\n");
		result.append("      " + estimatedHours + "\n");
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
		Project other = (Project) obj;
		if (Double.doubleToLongBits(budget) != Double
				.doubleToLongBits(other.budget))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (estimatedHours != other.estimatedHours)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (progLanguage == null) {
			if (other.progLanguage != null)
				return false;
		} else if (!progLanguage.equals(other.progLanguage))
			return false;
		if (quantityLines != other.quantityLines)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}	
	
	public Object clone() {
		Project p;
		p = new Project(getName(), getDescription(), getStartDate(), getEndDate(), getBudget(), getQuantityLines(), getDomain(), getProgLanguage(), getEstimatedHours());
		p.setId(getId());
		return p;
	}
}
