package project;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ProjectHeader;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.*;
import net.sf.mpxj.reader.ProjectReader;

public class PruebaProject {

	public static void read() {
		ProjectReader reader = new MPPReader ();
		try {
			ProjectFile project = reader.read("Proyect1.mpp");
			
			// Recursos
			for (Resource resource : project.getAllResources())
			{
			   System.out.println("Resource: " + resource.getName() + " (Unique ID=" + resource.getUniqueID() + ")");
			}
			
			// Aqui esta toda la informacion: fechas, asunto, autor, comentarios, etc.
			ProjectHeader a = project.getProjectHeader();
			System.out.println(a);
			
		} catch (MPXJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		read();
	}
}
