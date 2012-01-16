package presentation.utils;

import internationalization.ApplicationInternationalization;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.business.knowledge.Answer;
import model.business.knowledge.KnowledgeStatus;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import resources.ImagesUtilities;

public class ImageKnowledgeTreeCellRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 582215961691417214L;
	private boolean showInCBR;
	private KnowledgeStatus filter;

	public ImageKnowledgeTreeCellRenderer() {		
	}
	
	public ImageKnowledgeTreeCellRenderer(boolean showInCBR, KnowledgeStatus filter) {
		this.showInCBR = showInCBR;
		this.filter = filter;
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, hasFocus);
		
		Icon icon = null;
		Object val = ((DefaultMutableTreeNode)value).getUserObject();
		try {
			if (val instanceof Topic) {				
				icon = ImagesUtilities.loadIcon("Trees/Topic.png");
				if(icon != null)
	                setIcon(icon);
				if (showInCBR && filter.equals(((Topic)val).getStatus())) {
					setForeground(new Color(79,153,183));
				}
			}
			if (val instanceof Proposal) {				
				icon = ImagesUtilities.loadIcon("Trees/Proposal.png");
				if(icon != null)
	                setIcon(icon);
				if (showInCBR && filter.equals(((Proposal)val).getStatus())) {
					setForeground(new Color(79,153,183));
				}
			}
			if (val instanceof Answer) {				
				icon = ImagesUtilities.loadIcon("Trees/Answer.png");
				if(icon != null)
	                setIcon(icon);
				if (showInCBR && filter.equals(((Answer)val).getStatus())){
					setForeground(new Color(79,153,183));
				}
			}
			setTextSelectionColor(Color.BLACK);
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}        
        return this;
		
	}
}