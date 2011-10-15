package model.business.knowledge;

import java.util.List;

/**
 * This class represents a PDF Section
 *
 */
public class PDFSection {

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
