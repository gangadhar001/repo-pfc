/**
 * Class used to generate a PDF document from the user-entered configuration
 */
public class PDFComposer {	
	
	public static byte[] createDocument(long sessionId, PDFConfiguration configuration, Image headerImage, Image footImage) throws NumberFormatException, RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		.....
		
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
	
	.....
}
