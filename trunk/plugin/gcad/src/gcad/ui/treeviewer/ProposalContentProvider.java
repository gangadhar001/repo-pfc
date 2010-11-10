package gcad.ui.treeviewer;

import java.util.ArrayList;
import java.util.Date;

import gcad.domain.Answer;
import gcad.domain.Proposal;
import gcad.listeners.IssueEvent;
import gcad.listeners.ProposalListener;
import gcad.proposal.models.ProposalManager;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

public class ProposalContentProvider implements ITreeContentProvider{

	private TreeViewer viewer;
	private ProposalManager manager;
	
	@Override
	public Object[] getElements(Object inputElement) {
		ArrayList<Proposal> a = new ArrayList<Proposal>();
		Proposal root = new Proposal("Raiz", "prueba", new Date(),0);

		root.add(new Answer("Nodo1", "a", new Date()));

		root.add(new Answer("Nodo2", "b", new Date()));
		

		Proposal comp = new Proposal("Raiz2", "prueba2", new Date(),1);

		comp.add(new Answer("Nodo3", "c", new Date()));

		a.add(root);
		a.add(comp);
		
		return a.toArray();

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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
		ArrayList<Proposal> a = new ArrayList<Proposal>();
		Proposal root = new Proposal("Raiz", "prueba", new Date(),0);

		root.add(new Answer("Nodo1", "a", new Date()));

		root.add(new Answer("Nodo2", "b", new Date()));
		

		Proposal comp = new Proposal("Raiz2", "prueba2", new Date(),1);

		comp.add(new Answer("Nodo3", "c", new Date()));

		a.add(root);
		a.add(comp);
		
		return a.toArray();

	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}


}
