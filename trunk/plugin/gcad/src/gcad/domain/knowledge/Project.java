package gcad.domain.knowledge;

import java.util.Date;

/**
 * This class represents a Project
 */
public class Project {

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
	
	public Project() {
	}
		
	public Project(String name, String description, Date startDate,
			Date endDate, double budget, int quantityLines, String domain,
			String progLanguage, int estimatedHours) {
		super();
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
	
	
	
}
