package gcad.ui.treeviewer;

import gcad.listeners.IssueEvent;
import gcad.listeners.ProposalListener;
import gcad.proposal.models.ProposalManager;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

public class ProposalContentProvider implements IStructuredContentProvider, ProposalListener{

	private TableViewer viewer;
	private ProposalManager manager;
	
	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return manager.getProposals();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TableViewer) viewer;
		if (manager!=null)
			manager.removeProposalManagerListener(this);
		manager = (ProposalManager) newInput;
		if (manager!=null)
			manager.addProposalManagerListener(this);
		
	}

	@Override
	public void add(IssueEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(IssueEvent event) {
		// TODO Auto-generated method stub
		
	}

}
