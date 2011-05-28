package presentation.customComponents;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

//REFERENCES: http://www.developerbarn.com/blogs/richyrich/32-using-itextsharp-generate-pdf-header-footer.html
public class HeaderFooter extends PdfPageEventHelper{

	private Image header;
	private Image foot;

	public HeaderFooter (Image header, Image foot) {
		this.header = header;
		this.foot = foot;
	}	
	
	// Method used to set the header and footer on each page
	public void onEndPage(PdfWriter writer, Document document) {
		if (header != null) {
			// Create header table
			PdfPTable headerTbl = new PdfPTable(1);	
			headerTbl.setTotalWidth(document.getPageSize().getWidth());
			headerTbl.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        // Original size of the image
	        header.scalePercent(100f);
	        // Add the image to the table
	        PdfPCell cell = new PdfPCell(header);
	        cell.setBorder(0);
	        cell.setPaddingRight(20f);
	        // Add cell to table
	        headerTbl.addCell(cell);   
	        headerTbl.writeSelectedRows(0,-1, 0, (document.getPageSize().getHeight() - 10),  writer.getDirectContent());
		}
		
		if (foot != null) {
			// Create footer table
			PdfPTable footerTbl = new PdfPTable(1);	
	        footerTbl.setTotalWidth(document.getPageSize().getWidth());
	        footerTbl.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        // Original size of the image
	        foot.scalePercent(100f);
	        // Add the image to the table
	        PdfPCell cell = new PdfPCell(foot);
	        cell.setBorder(0);
	        cell.setPaddingRight(20f);
	        // Add cell to table
	        footerTbl.addCell(cell);   
	        footerTbl.writeSelectedRows(0,-1, 0, (document.bottomMargin() + 10),  writer.getDirectContent());
		}
	}
}
