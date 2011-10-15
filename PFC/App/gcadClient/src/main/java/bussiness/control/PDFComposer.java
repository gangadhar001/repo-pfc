package bussiness.control;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import model.business.knowledge.Answer;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import presentation.PDFConfiguration;
import presentation.PDFElement;
import presentation.PDFSection;
import presentation.PDFTable;
import presentation.PDFText;
import presentation.PDFTitle;
import presentation.customComponents.HeaderFooter;
import resources.ImagesUtilities;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
	
	public static void createDocument(PDFConfiguration configuration, File path, String headerImagePath, String footImagePath) throws NumberFormatException, RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
	
		// Margin of documents
		float marginTop = 20;
		float marginBottom = 20;
		Image headerImage = getImage(headerImagePath);
		if (headerImage != null)
			marginTop = headerImage.getHeight() + 20;
		Image footImage = getImage(footImagePath);
		if (footImage != null)
			marginBottom = footImage.getHeight() + 20;
		
		Document doc = new Document(PageSize.A4, 20, 20, marginTop, marginBottom);
		
        PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(path + ".pdf"));
        // Event used to add header image and foot image
        HeaderFooter event = new HeaderFooter(headerImage, footImage);
		pdfWriter.setPageEvent(event);				
        
		doc.open();            
		composePDF(doc, configuration);
		doc.close();
				
	}
	
	public static void composePDF (Document doc, PDFConfiguration config) throws NumberFormatException, RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		int count = 1;
		Chapter ch = new Chapter(count);
		ch.setNumberDepth(0);
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
				Paragraph p = new Paragraph(((PDFTitle)element).getTitle().toUpperCase(), f);
				sec.setTitle(p);
			}
			if (element instanceof PDFText) {
				Font f = ((PDFText)element).getFont();
				Paragraph p = new Paragraph(((PDFText)element).getContent(), f);
				sec.add(p);
			}
			else if (element instanceof PDFTable) {
				PdfPTable table = createTable(((PDFTable)element).getProject());
				sec.add(table);
			}
		}
	}

	private static PdfPTable createTable(Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		PdfPTable table = new PdfPTable(6);
		createHeader(table, p);
		TopicWrapper tw;
		// Take knowledge from project
		tw = ClientController.getInstance().getTopicsWrapper(p);
		List<Knowledge> knowledge = ClientController.getInstance().getKnowledgeProject(tw);

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

	private static Image getImage(String path) throws MalformedURLException, IOException, DocumentException {
		Image result = null;
		if (path != null)
			result = Image.getInstance(path);
		return result;
		
	}


}
