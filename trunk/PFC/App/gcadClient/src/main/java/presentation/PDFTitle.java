package presentation;

import java.awt.Color;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

public class PDFTitle extends PDFElement {
	
	private String title;
	private Font font;

	public PDFTitle(String title) {
		super();
		font = FontFactory.getFont(FontFactory.HELVETICA, 16f, Font.BOLD, new BaseColor(Color.BLACK));
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}



}
