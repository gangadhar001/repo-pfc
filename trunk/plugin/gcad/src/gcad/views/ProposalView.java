package gcad.views;

import gcad.domain.Proposal;
import gcad.proposals.models.ProposalManager;
import gcad.ui.treeviewer.ProposalContentProvider;
import gcad.ui.treeviewer.ProposalLabelProvider;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;


public class ProposalView extends ViewPart {
	
	protected TreeViewer treeViewer;
	private ProposalManager manager = ProposalManager.getManager();
	// TODO: se usa para poner los iconos de las acciones en una barra de menus dentro de la vista
	private DrillDownAdapter drillDownAdapter;
	private Action doubleClickAction;

	@Override
	public void createPartControl(Composite parent) {	
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(treeViewer);
		treeViewer.setContentProvider(new ProposalContentProvider());
		treeViewer.setLabelProvider(new ProposalLabelProvider());
		// Expand the tree 
		//treeViewer.setAutoExpandLevel(2);			
		Proposal root = new Proposal();
		manager.makeProposalsTree(root);
		// TODO: ejemplo para crear un arbol. Coger el arbol del manager 
		// Object[] b = manager.getProposals();
		// Nodo raiz del arbol
		/*
		
		Proposal p = new Proposal("raiz", "prueba1", new Date(),1);

		p.add(new Answer("Nodo1", "a", new Date()));

		p.add(new Answer("Nodo2", "b", new Date()));
		

		Proposal comp = new Proposal("Raiz2", "prueba2", new Date(),1);

		comp.add(new Answer("Nodo3", "c", new Date()));
		root.add(p);
		root.add(comp);*/
		treeViewer.setInput(root);
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(treeViewer.getControl(), "Proposals Tree");
		makeActions();
		hookDoubleClickAction();
	}
	
	private void makeActions() {
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = treeViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				MessageDialog.openInformation(treeViewer.getControl().getShell(), "Proposals View", "Double-click detected on "+obj.toString());
			}
		};
	}
	
	// Listener for Double Click Action
	private void hookDoubleClickAction() {
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();


	}
                                                                                                                                   
}
