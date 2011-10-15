package model.business.knowledge;

import java.awt.Color;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

/**
 * This class represents a PDF Text
 *
 */
public class PDFText extends PDFElement {

	private String content;
	private Font font;

	public PDFText(String content) {
		super();
		this.content = content;
		font = FontFactory.getFont(FontFactory.HELVETICA, 12f, Font.BOLD, new BaseColor(Color.BLACK));
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	
	
}
