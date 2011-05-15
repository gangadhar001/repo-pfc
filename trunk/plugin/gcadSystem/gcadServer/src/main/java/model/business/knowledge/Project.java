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
		return name;
	}
	
	public String getInfo() {
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
		boolean result = false;
		if (this == obj)
			result = true;
		else if (obj == null)
			result = false;
		else if (getClass() != obj.getClass())
			result = false;
		else if (obj instanceof Project) {
			Project other = (Project) obj;
//			result = (name.equals(other.getName()) && description.equals(other.getDescription()) &&
//					startDate.equals(other.getStartDate()) && endDate.equals(other.getEndDate()) && budget == other.getBudget()
//					&& quantityLines == other.getQuantityLines() && domain.equals(other.getDomain()) && progLanguage.equals(other.getProgLanguage())
//					&& estimatedHours == other.getEstimatedHours());
			result = (id == other.getId());
			}
		
		return result;
	}	
	
	public int hashCode() {
		return id;
	}
	
	public Object clone() {
		Project p;
		p = new Project(getName(), getDescription(), getStartDate(), getEndDate(), getBudget(), getQuantityLines(), getDomain(), getProgLanguage(), getEstimatedHours());
		p.setId(getId());
		return p;
	}
}
