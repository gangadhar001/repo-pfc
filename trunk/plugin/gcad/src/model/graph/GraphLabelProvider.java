package model.graph;

import gcad.Activator;
import model.business.knowledge.Answer;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * This class represents a label provider for the graph. It allows to get the text or set 
 * an image for a node
 */
public class GraphLabelProvider extends LabelProvider {
	
	public String getText(Object element) {
		String text = "";
		if (element instanceof Knowledge)
			text = ((Knowledge)element).getTitle();
		return text;
	}
	
	public Image getImage(Object element) {
		Image ima = null;
		if (element instanceof Topic) {
			ima = Activator.getImageDescriptor("resources/images/topic2.gif").createImage();
		}
		else if (element instanceof Proposal) {
			ima = Activator.getImageDescriptor("resources/images/proposal2.png").createImage();
		}
		else if (element instanceof Answer) {
			ima = Activator.getImageDescriptor("resources/images/answer.png").createImage();
		}
		return ima;
	}


}
