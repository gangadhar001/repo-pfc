package gcad.ui.treeviewer;

import gcad.domain.Answer;
import gcad.domain.Proposal;

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
