package model.treeviewer;


import gcad.Activator;
import model.business.knowledge.Answer;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class KnowledgeLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		String text = "";
		if (element instanceof Knowledge) {
			return text = ((Knowledge)element).getTitle();
		}
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
