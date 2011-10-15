package model.business.knowledge;

/**
 * This class represents a PDF Table
 *
 */
public class PDFTable extends PDFElement {

	private Project project;

	public PDFTable(Project project) {
		super();
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
