import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
import com.itextpdf.text.pdf.PdfWriter;


public class Prueba {
	 private static File ruta_destino=null;
	private static Document mipdf;
	    
	    /* metodo que hace uso de la clase itext para manipular archivos PDF*/
	    public static void crear_PDF(){
	        //abre ventana de dialogo "guardar"
	        Colocar_Destino();
	        //si destino es diferente de null
	        if(ruta_destino!=null){
	            try {
	                // se crea instancia del documento
	                mipdf = new Document();
	                // se establece una instancia a un documento pdf
	                PdfWriter.getInstance(mipdf, new FileOutputStream(ruta_destino + ".pdf"));
	                mipdf.open();// se abre el documento
	                mipdf.addTitle("Titulo"); // se añade el titulo
	                mipdf.addAuthor("Autor"); // se añade el autor del documento
	                mipdf.addSubject("Asunto"); //se añade el asunto del documento
	                //mipdf.add(new Paragraph("esto es un párrafo del PDF")); // se añade el contendio del PDF
	                
	                Chapter s= new Chapter(new Paragraph("Seccion 1"), 1);
	                Section a = s.addSection("");
	                a.setTitle(new Paragraph("Hola"));
	                mipdf.add(s);
	                
	              
	                
	              //mipdf.add(Tabla_Simple());
		             mipdf.add(Tabla_compleja());
		                       
		             System.out.println("documento pdf creado");
		         
	            
	                mipdf.close(); //se cierra el PDF&
	            } catch (DocumentException ex) {
	               ex.printStackTrace();
	            } catch (FileNotFoundException ex) {
	            	ex.printStackTrace();
	            }            
	        }        
	    }
	    /* abre la ventana de dialogo GUARDAR*/
	    public static void Colocar_Destino(){
	       FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo PDF","pdf","PDF");
	       JFileChooser fileChooser = new JFileChooser();       
	       fileChooser.setFileFilter(filter);
	       int result = fileChooser.showSaveDialog(null);
	       if ( result == JFileChooser.APPROVE_OPTION ){   
	           ruta_destino = fileChooser.getSelectedFile().getAbsoluteFile();
	        }
	    }  
	    
	    	
	         
	         public PdfPTable Tabla_Simple(){    
	             //creamos la tabla con 3 columnas
	             PdfPTable mitablasimple=new PdfPTable(3);
	             //añadimos contenido a las celdas
	           mitablasimple.addCell("Enero");
	             mitablasimple.addCell("Febrero");
	             mitablasimple.addCell("Marzo");
	             mitablasimple.addCell("Abril");
	             mitablasimple.addCell("Mayo");
	             mitablasimple.addCell("Junio");
	             mitablasimple.addCell("Julio");
	             mitablasimple.addCell("Agosto");
	             mitablasimple.addCell("Septiembre");
	             mitablasimple.addCell("Octubre");
	             //retornamos la tabla
	             return mitablasimple;        
	         }
	        
	         public static PdfPTable Tabla_compleja(){ 
	             //creamos una tabla con 3 columnas
	             PdfPTable mitablacompleja=new PdfPTable(3);
	             //añadimos texto con formato a la primera celda
	             PdfPCell celda =new PdfPCell (new Paragraph("Historial de Observaciones",
	                     FontFactory.getFont("arial",   // fuente
	                     22,                            // tamaño
	                     Font.BOLD,                   // estilo
	                     BaseColor.RED)));             // color
	             //unimos esta celda con otras 2
	             celda.setColspan(3);
	             //alineamos el contenido al centro
	             celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	             // añadimos un espaciado
	             celda.setPadding (12.0f);
	             //colocamos un color de fondo
	             celda.setBackgroundColor(BaseColor.GRAY);
	             //se añade a la tabla
	             mitablacompleja.addCell(celda);
	             
	             //fila 2
	             celda = new PdfPCell(new Paragraph ("AUDITORIA DE SISTEMAS"));
	             celda.setColspan(2);
	             celda.setBackgroundColor(BaseColor.GREEN);
	             mitablacompleja.addCell(celda);        
	             celda = new PdfPCell(new Paragraph ("Aprobado"));        
	             celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
	             mitablacompleja.addCell(celda);
	             //fila 3        
	             celda = new PdfPCell(new Paragraph ("COMPILADORES"));
	             celda.setColspan(2);
	             celda.setBackgroundColor(BaseColor.YELLOW);
	             mitablacompleja.addCell(celda);        
	             celda = new PdfPCell(new Paragraph ("Reprobado"));        
	             celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
	             mitablacompleja.addCell(celda);
	             //fila 4        
	             celda = new PdfPCell(new Paragraph ("Prog. Bajo Nivel"));
	             celda.setColspan(2);
	             celda.setBackgroundColor(BaseColor.CYAN);
	             mitablacompleja.addCell(celda);        
	             celda = new PdfPCell(new Paragraph ("Eximido"));        
	             celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
	             mitablacompleja.addCell(celda);
	             //fila 5
	             mitablacompleja.addCell("Conclusion");
	             celda = new PdfPCell(new Paragraph ("¡¡¡GET A LIFE!!!"));
	             celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	             celda.setColspan(2);
	             celda.setBackgroundColor(BaseColor.ORANGE);
	             mitablacompleja.addCell(celda);
	             // se retorna la tabla
	    return mitablacompleja;    
	         }
	    
	public static void main(String[] args) {
//		crear_PDF();
		StringBuffer ad = new StringBuffer();
		ad.append("asadasfsfsadsadsa,dsadsadsadsad+sadsa,das+");
		if (ad.toString().endsWith("+"))
			ad = ad.replace(ad.length() - 1, ad.length(), "");
		System.out.println("Valor: " + ad.toString());
	}

}
