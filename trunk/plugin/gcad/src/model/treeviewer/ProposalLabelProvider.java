package model.treeviewer;


import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;

import org.eclipse.jface.viewers.LabelProvider;

public class ProposalLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		if (element instanceof Answer) {
			return ((Answer)element).getInformation();
		}
		return ((Proposal) element).toString();
	}


}
