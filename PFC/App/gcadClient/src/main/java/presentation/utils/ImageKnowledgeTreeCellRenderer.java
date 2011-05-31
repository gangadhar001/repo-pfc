package presentation.utils;

import internationalization.ApplicationInternationalization;

import java.awt.Component;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import resources.ImagesUtilities;

public class ImageKnowledgeTreeCellRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 582215961691417214L;

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, hasFocus);
		
		Icon icon = null;
		Object val = ((DefaultMutableTreeNode)value).getUserObject();
		try {
			if (val instanceof Topic) {				
				icon = ImagesUtilities.loadIcon("Trees/Topic.png");
				if(icon != null)
	                setIcon(icon);
			}
			if (val instanceof Proposal) {				
				icon = ImagesUtilities.loadIcon("Trees/Proposal.png");
				if(icon != null)
	                setIcon(icon);
			}
			if (val instanceof Answer) {				
				icon = ImagesUtilities.loadIcon("Trees/Answer.png");
				if(icon != null)
	                setIcon(icon);
			}
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}        
        return this;
		
	}
}