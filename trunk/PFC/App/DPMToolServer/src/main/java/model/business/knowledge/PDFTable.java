package model.business.knowledge;


/**
 * This class represents a PDF Table
 *
 */
public class PDFTable extends PDFElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7541283382146634837L;
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
