package model.business.knowledge;


/**
 * This class represents a PDF Title
 *
 */
public class PDFTitle extends PDFElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8885195870975827909L;
	private String title;
	private float fontSize;

	public PDFTitle(String title) {
		super();
		fontSize = 16f;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getFontSize() {
		return fontSize;
	}


}
