package presentation.utils;

import java.awt.Component;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.business.knowledge.PDFDocument;
import model.business.knowledge.Section;
import model.business.knowledge.Text;
import model.business.knowledge.Title;

public class ImagePDFTreeCellRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 582215961691417214L;

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, hasFocus);
		
		Icon icon = null;
		Object val = ((DefaultMutableTreeNode)value).getUserObject();
		try {
			if (val instanceof PDFDocument) {				
				icon = ImagesUtilities.loadIcon("Trees/pdf_ico.png");
				if(icon != null)
	                setIcon(icon);
			}
			else if (val instanceof Text) {				
				icon = ImagesUtilities.loadIcon("Trees/Text.png");
				if(icon != null)
	                setIcon(icon);
			}
			else if (val instanceof Title) {				
				icon = ImagesUtilities.loadIcon("Trees/Title.png");
				if(icon != null)
	                setIcon(icon);
			}
			else if (val instanceof Section) {				
				icon = ImagesUtilities.loadIcon("Trees/Section.png");
				if(icon != null)
	                setIcon(icon);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        return this;
		
	}
}