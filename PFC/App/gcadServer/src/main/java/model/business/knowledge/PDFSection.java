package model.business.knowledge;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents a PDF Section
 *
 */
public class PDFSection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1257064734210623746L;
	private List<PDFElement> elements;

	public PDFSection(List<PDFElement> elements) {
		super();
		this.elements = elements;
	}

	public List<PDFElement> getElements() {
		return elements;
	}

	public void setElements(List<PDFElement> elements) {
		this.elements = elements;
	}	
	
}
