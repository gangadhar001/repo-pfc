package model.treeviewer;


import model.business.knowledge.Knowledge;

import org.eclipse.jface.viewers.LabelProvider;

public class KnowledgeLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		String text = "";
		if (element instanceof Knowledge) {
			return text = ((Knowledge)element).getTitle();
		}
		return text;
	}


}
