package bussiness.control;

import java.awt.Color;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import model.business.knowledge.Text;
import model.business.knowledge.Title;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;

/**
 * Class used to generate a PDF document from the user-entered configuration
 */
public class PDFComposer {
	
	@SuppressWarnings("rawtypes")
	public static void composePDF (Document doc, DefaultMutableTreeNode root) throws DocumentException {
		Enumeration children = root.children();
		int count = 1;
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
			if (child.getUserObject() instanceof model.business.knowledge.Section) {
				Chapter ch = new Chapter(count);
				generateContent(ch, child);
				doc.add(ch);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private static void generateContent(Element el, DefaultMutableTreeNode parentNode) {
		Enumeration children = parentNode.children();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
			if (child.getUserObject() instanceof Title) {
				Font f = FontFactory.getFont(FontFactory.HELVETICA, 16f, Font.BOLD, new BaseColor(Color.BLACK));
				Paragraph p = new Paragraph(((Title)child.getUserObject()).getContent(), f);
				if (el instanceof Chapter) 
					((Chapter)el).setTitle(p);
				else if (el instanceof Section)
					((Section)el).setTitle(p);
			}
			if (child.getUserObject() instanceof Text) {
				Font f = FontFactory.getFont(FontFactory.HELVETICA, 12f, Font.NORMAL, new BaseColor(Color.BLACK));
				Paragraph p = new Paragraph(((Text)child.getUserObject()).getContent(), f);
				if (el instanceof Chapter) 
					((Chapter)el).add(p);
				else if (el instanceof Section)
					((Section)el).add(p);
			}
			if (child.getUserObject() instanceof model.business.knowledge.Section) {
				if (el instanceof Chapter) {
					Section s = ((Chapter)el).addSection(4f, "");
					generateContent(s, child);
				}
				else if (el instanceof Section) {
					Section s = ((Section)el).addSection(4f, "");
					generateContent(s, child);
				}
			}
//			if (child.getUserObject() instanceof Table) {
//				
//			}
		}
	}

}
