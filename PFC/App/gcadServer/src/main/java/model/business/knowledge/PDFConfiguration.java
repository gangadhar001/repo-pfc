package model.business.knowledge;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents the configuration used to generate a PDF File
 *
 */
public class PDFConfiguration implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7189876391103733835L;
	/**
	 * 
	 */
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
					// Title must be the first element in a section
					valid = (section.getElements().get(0) instanceof PDFTitle);
					if (valid) {
						for(PDFElement element: section.getElements()) {
							if (valid) {
								if (element instanceof PDFText) {
									valid = ((PDFText)element).getContent().length() > 0;
								}
								else if (element instanceof PDFTable) {
									valid = ((PDFTable)element).getProject() != null;
								}
								else if (element instanceof PDFTitle) {
									valid = ((PDFTitle)element).getTitle().length() > 0;
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
