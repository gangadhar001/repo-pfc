package model.business.control;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.control.auxiliary.HeaderFooter;
import model.business.knowledge.Answer;
import model.business.knowledge.Groups;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.PDFConfiguration;
import model.business.knowledge.PDFElement;
import model.business.knowledge.PDFTitle;
import model.business.knowledge.PDFText;
import model.business.knowledge.PDFTable;
import model.business.knowledge.PDFSection;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Subgroups;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import resources.ImagesUtilities;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

/**
 * Class used to generate a PDF document from the user-entered configuration
 */
public class PDFComposer {	
	
	public static byte[] createDocument(long sessionId, PDFConfiguration configuration, Image headerImage, Image footImage) throws NumberFormatException, RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.PDFGeneration.name(), Subgroups.PDFGeneration.name(), Operations.Generate.name()));
		
		// Margin of documents
		float marginTop = 20;
		float marginBottom = 20;		
		if (headerImage != null)
			marginTop = headerImage.getHeight() + 20;		
		if (footImage != null)
			marginBottom = footImage.getHeight() + 20;
		
		Document doc = new Document(PageSize.A4, 20, 20, marginTop, marginBottom);
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PdfWriter pdfWriter = PdfWriter.getInstance(doc, buffer);
        
        // Event used to add header image and foot image
        HeaderFooter event = new HeaderFooter(headerImage, footImage);
		pdfWriter.setPageEvent(event);				
        
		doc.open();            
		composePDF(sessionId, doc, configuration);
		doc.close();
		
		return buffer.toByteArray();				
	}
	
	private static void composePDF (long sessionId, Document doc, PDFConfiguration config) throws NumberFormatException, RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		int count = 1;
		// Create the chapter 
		Chapter ch = new Chapter(count);
		ch.setNumberDepth(0);
		// Create the different sections
		for (PDFSection section : config.getSections()) {	
			Section s = ch.addSection(4f, "");
			generateContent(sessionId, s, section);
			doc.add(ch);			
		}
	}

	private static void generateContent(long sessionId, Section sec, PDFSection section) throws NumberFormatException, RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		for(PDFElement element : section.getElements()){
			if (element instanceof PDFTitle) {
				Font f = FontFactory.getFont(FontFactory.HELVETICA, ((PDFTitle)element).getFontSize(), Font.BOLD, new BaseColor(Color.BLACK));
				Paragraph p = new Paragraph(((PDFTitle)element).getTitle().toUpperCase(), f);
				sec.setTitle(p);
			}
			else if (element instanceof PDFText) {
				Font f = FontFactory.getFont(FontFactory.HELVETICA, ((PDFText)element).getFontSize(), Font.BOLD, new BaseColor(Color.BLACK));
				Paragraph p = new Paragraph(((PDFText)element).getContent(), f);
				sec.add(p);
			}
			else if (element instanceof PDFTable) {
				PdfPTable table = createTable(sessionId, ((PDFTable)element).getProject());
				sec.add(table);
			}
		}
	}

	private static PdfPTable createTable(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		PdfPTable table = new PdfPTable(6);
		createHeader(table, p);
		TopicWrapper tw;
		// Take knowledge from project
		tw = Server.getInstance().getTopicsWrapper(sessionId, p);
		List<Knowledge> knowledge = getKnowledgeProject(tw);

		for(Knowledge k: knowledge) {
			Image image = null;
			if (k instanceof Topic)
				image = Image.getInstance(ImagesUtilities.getPathImage("Topic.png"));
			if (k instanceof Proposal)
				image = Image.getInstance(ImagesUtilities.getPathImage("Proposal.png"));
			if (k instanceof Answer)
				image = Image.getInstance(ImagesUtilities.getPathImage("Answer.png"));		
			
			PdfPCell cell = new PdfPCell(image, false);
			setBackgroundColor(k, cell);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(k.getTitle()));
			setBackgroundColor(k, cell);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(k.getDescription()));
			setBackgroundColor(k, cell);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(k.getDate().toString()));
			setBackgroundColor(k, cell);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(k.getUser().getName() + ", " + k.getUser().getSurname()));
			setBackgroundColor(k, cell);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(""));
			table.addCell(cell);
		}
		
		
		return table;
	}
	

	// Get knowledge from a project
	private static List<Knowledge> getKnowledgeProject(TopicWrapper tw) {		
		List<Knowledge> result = new ArrayList<Knowledge>();
		for (Topic t: tw.getTopics()){
				result.add(t);
			for (Proposal p: t.getProposals()){
					result.add(p);
				for (Answer a: p.getAnswers()) {
						result.add(a);
				}
			}
		}
		return result;		
	}

	private static void setBackgroundColor(Knowledge k, PdfPCell cell) {
		if (k instanceof Topic)
			cell.setBackgroundColor(BaseColor.YELLOW);
		if (k instanceof Proposal)
			cell.setBackgroundColor(BaseColor.CYAN);
		if (k instanceof Proposal)
			cell.setBackgroundColor(BaseColor.PINK);
		
	}

	private static void createHeader(PdfPTable table, Project p) {
		Font f = FontFactory.getFont(FontFactory.HELVETICA, 22f, Font.BOLD, new BaseColor(Color.BLACK));
		Paragraph par = new Paragraph("DECISIONS ON PROJECT " + p.getName()  ,f);		
		PdfPCell header = new PdfPCell(par);
		header.setColspan(6);
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(header);
			
		PdfPCell cell1 = new PdfPCell(new Paragraph("Type"));
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Paragraph("Title"));
		table.addCell(cell2);
		
		PdfPCell cell3 = new PdfPCell(new Paragraph("Description"));
		table.addCell(cell3);
		
		PdfPCell cell4 = new PdfPCell(new Paragraph("Date"));
		table.addCell(cell4);
		
		PdfPCell cell5 = new PdfPCell(new Paragraph("Open by"));
		table.addCell(cell5);
		
		PdfPCell cell6 = new PdfPCell(new Paragraph("Approved"));
		table.addCell(cell6);
		
	}

	

}
