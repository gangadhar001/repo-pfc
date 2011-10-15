package model.business.knowledge;


/**
 * This class represents a PDF Text
 *
 */
public class PDFText extends PDFElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6728190661404996655L;
	private String content;
	private float fontSize;

	public PDFText(String content) {
		super();
		this.content = content;
		fontSize = 12f;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getFontSize() {
		return fontSize;
	}
}
