package presentation.utils;

import java.awt.Component;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

public class ImageTreeCellRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 582215961691417214L;

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, hasFocus);
		
		Icon icon = null;
		try {
			if (value instanceof Topic) {				
				icon = GraphicsUtilities.loadIcon("Topic.png");
				if(icon != null)
	                setIcon(icon);
			}
			if (value instanceof Proposal) {				
				icon = GraphicsUtilities.loadIcon("Proposal.png");
				if(icon != null)
	                setIcon(icon);
			}
			if (value instanceof Answer) {				
				icon = GraphicsUtilities.loadIcon("answer.png");
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