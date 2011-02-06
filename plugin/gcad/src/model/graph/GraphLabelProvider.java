package model.graph;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Topic;

import org.eclipse.jface.viewers.LabelProvider;

public class GraphLabelProvider extends LabelProvider {
	
	public String getText(Object element) {
		String text = "";
		if (element instanceof Topic)
			text = ((Topic)element).getTitle();
		if (element instanceof Knowledge)
			text = ((Knowledge)element).getTitle();
		else if (element instanceof Edge)
			text = ((Edge)element).getLabel();
		return text;
	}

}
