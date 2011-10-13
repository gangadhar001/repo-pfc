package presentation;

import java.util.List;

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
