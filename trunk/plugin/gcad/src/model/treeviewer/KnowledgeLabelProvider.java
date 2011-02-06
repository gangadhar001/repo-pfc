package model.treeviewer;


import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

import org.eclipse.jface.viewers.LabelProvider;

public class KnowledgeLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		String text = "";
		if (element instanceof Answer) {
			return text = ((Answer)element).toString();
		}
		else if (element instanceof Topic) {
			return text = ((Topic)element).toString();
		}
		else if (element instanceof Proposal)
			text = ((Proposal) element).toString();
		return text;
	}


}
