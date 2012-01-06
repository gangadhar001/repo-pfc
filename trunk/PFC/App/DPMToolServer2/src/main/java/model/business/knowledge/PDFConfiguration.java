package model.business.knowledge;

import internationalization.AppInternationalization;

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
	private String errormessage;

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
		int cont = 1;
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
									if (!valid)
										errormessage = AppInternationalization.getString("textNotEmpty")+ " " + String.valueOf(cont);
								}
								else if (element instanceof PDFTable) {
									valid = ((PDFTable)element).getProject() != null;
									if (!valid)
										errormessage = AppInternationalization.getString("tableNotEmpty")+ " " + String.valueOf(cont);
								}
								else if (element instanceof PDFTitle) {
									valid = ((PDFTitle)element).getTitle().length() > 0;
									if (!valid)
										errormessage = AppInternationalization.getString("titleNotEmpty")+ " " + String.valueOf(cont);
								}
							}
						}
					}
					else
						errormessage = AppInternationalization.getString("titleNotFirstElement")+ " " + String.valueOf(cont);
				}
				else
					errormessage = AppInternationalization.getString("sectionNotEmpty")+ " " + String.valueOf(cont);
				
				cont++;
			}
		}
		return valid;
	}
	
	public String getErrorMessage() {
		return errormessage;
	}
	

}
