package presentation;

import java.util.List;

public class PDFConfiguration {
	
	private List<PDFSection> sections;

	public PDFConfiguration(List<PDFSection> sections) {
		super();
		this.sections = sections;
	}	
	
	public List<PDFSection> getSections() {
		return sections;
	}

	public void setSections(List<PDFSection> sections) {
		this.sections = sections;
	}

	public boolean isValid() {
		boolean valid = true;
		for(PDFSection section : sections) {
			if (valid) {
				valid = section.getElements().size() > 0;
				if (valid) {
					for(PDFElement element: section.getElements()) {
						if (valid) {
							// Title must be the first element in a section
							valid = (element instanceof PDFTitle);
							if (valid) {
								if (element instanceof PDFText) {
									valid = ((PDFText)element).getContent().length() > 0;
								}
								else if (element instanceof PDFTable) {
									valid = ((PDFTable)element).getProject() != null;
								}
							}
						}
					}
				}
			}
		}
		return valid;
	}
	

}
