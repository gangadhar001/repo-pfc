package bussiness.control;

import java.awt.Color;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Project;
import model.business.knowledge.Table;
import model.business.knowledge.Text;
import model.business.knowledge.Title;
import model.business.knowledge.TopicWrapper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

/**
 * Class used to generate a PDF document from the user-entered configuration
 */
public class PDFComposer {
	
	private static List<Project> projects = null;
	
	@SuppressWarnings("rawtypes")
	public static void composePDF (Document doc, DefaultMutableTreeNode root, List<Project> pro) throws DocumentException {
		projects = pro;
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
					generateContent(s, child );
				}
				else if (el instanceof Section) {
					Section s = ((Section)el).addSection(4f, "");
					generateContent(s, child);
				}
			}
			if (child.getUserObject() instanceof Table) {
				PdfPTable table = createTable(projects.get(Integer.parseInt(((Table)child.getUserObject()).getContent())));
				if (el instanceof Chapter) 
					((Chapter)el).add(table);
				else if (el instanceof Section)
					((Section)el).add(table);
			}
		}
	}

	private static PdfPTable createTable(Project p) {
		PdfPTable table = new PdfPTable(5);
		createHeader(table);
		// Take knowledge from project
		TopicWrapper tw;
		try {
			tw = ClientController.getInstance().getTopicsWrapper(p);
			List<Knowledge> knowledge = ClientController.getInstance().getKnowledgeProject(tw);
			for(Knowledge k: knowledge) {
				PdfPCell cell = new PdfPCell(new Paragraph(k.getTitle()));
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(k.getTitle()));
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(k.getDescription()));
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(k.getDate().toLocaleString()));
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(k.getUser().getName() + ", " + k.getUser().getSurname()));
				table.addCell(cell);
				
//				cell = new PdfPCell(new Paragraph(k.getUser().getName() + ", " + k.getUser().getSurname()));
//				table.addCell(cell);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonPermissionRole e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLoggedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		}
		
		return table;
	}

	private static void createHeader(PdfPTable table) {
		Font f = FontFactory.getFont(FontFactory.HELVETICA, 22f, Font.BOLD, new BaseColor(Color.BLACK));
		Paragraph par = new Paragraph("HISTORIC DECISIONS" ,f);		
		PdfPCell header = new PdfPCell(par);
		header.setColspan(5);
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(header);
			
		PdfPCell cell1 = new PdfPCell(new Paragraph("Title"));
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Paragraph("Description"));
		table.addCell(cell2);
		
		PdfPCell cell3 = new PdfPCell(new Paragraph("Date"));
		table.addCell(cell3);
		
		PdfPCell cell4 = new PdfPCell(new Paragraph("Open by"));
		table.addCell(cell4);
		
		PdfPCell cell5 = new PdfPCell(new Paragraph("Approved"));
		table.addCell(cell5);
		
	}

}
