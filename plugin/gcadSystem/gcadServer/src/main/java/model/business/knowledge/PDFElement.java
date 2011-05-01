package model.business.knowledge;

import java.awt.Font;
import java.io.Serializable;

public abstract class PDFElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 473646533601266673L;
	private String content;
	private Font font;
	
	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}	
	
}
