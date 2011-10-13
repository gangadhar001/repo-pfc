package bussiness.control;

import java.awt.Color;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Project;
import model.business.knowledge.TopicWrapper;
import presentation.PDFConfiguration;
import presentation.PDFElement;
import presentation.PDFSection;
import presentation.PDFTable;
import presentation.PDFText;
import presentation.PDFTitle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

/**
 * Class used to generate a PDF document from the user-entered configuration
 */
public class PDFComposer {	
	public static void composePDF (Document doc, PDFConfiguration config) throws NumberFormatException, RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		int count = 1;
		Chapter ch = new Chapter(count);
		for (PDFSection section : config.getSections()) {	
			Section s = ch.addSection(4f, "");
			generateContent(s, section);
			doc.add(ch);
			
		}
	}

	private static void generateContent(Section sec, PDFSection section) throws NumberFormatException, RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		for(PDFElement element : section.getElements()){
			if (element instanceof PDFTitle) {
				Font f = ((PDFTitle)element).getFont();
				Paragraph p = new Paragraph(((PDFTitle)element).getTitle(), f);
				sec.setTitle(p);
			}
			if (element instanceof PDFText) {
				Font f = ((PDFText)element).getFont();
				Paragraph p = new Paragraph(((PDFText)element).getContent(), f);
				sec.setTitle(p);
			}
			else if (element instanceof PDFTable) {
				PdfPTable table = createTable(((PDFTable)element).getProject());
				sec.add(table);
			}
		}
	}

	private static PdfPTable createTable(Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		PdfPTable table = new PdfPTable(5);
		createHeader(table);
		// Take knowledge from project
		TopicWrapper tw;
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
