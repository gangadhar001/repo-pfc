package gcad.ui.treeviewer;

import java.util.ArrayList;
import java.util.Date;

import javax.crypto.spec.OAEPParameterSpec;

import gcad.domain.Answer;
import gcad.domain.Proposal;
import gcad.proposal.models.ProposalManager;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

public class ProposalContentProvider implements ITreeContentProvider{

	private TreeViewer viewer;
	private ProposalManager manager;
	private static Object[] NO_ELEMENT = new Object[0];
	
	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TreeViewer) viewer;
		/*if (manager!=null)
			manager.removeProposalManagerListener(this);
		manager = (ProposalManager) newInput;
		if (manager!=null)
			manager.addProposalManagerListener(this);
		*/
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] childrens = NO_ELEMENT;
		if (parentElement instanceof Proposal) {
			Proposal proposal = (Proposal) parentElement;
			childrens = proposal.getProposals().toArray();
		}
		return childrens;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}


}
