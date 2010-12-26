package model.graph;

import model.business.knowledge.AbstractProposal;

import org.eclipse.jface.viewers.LabelProvider;

public class GraphLabelProvider extends LabelProvider {
	
	public String getText(Object element) {
		String text = "";
		if (element instanceof AbstractProposal)
			text = ((AbstractProposal)element).getTitle();
		else if (element instanceof Edge)
			text = ((Edge)element).getLabel();
		return text;
	}

}
