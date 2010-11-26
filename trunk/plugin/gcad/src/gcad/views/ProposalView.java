package gcad.views;

import gcad.domain.Proposal;
import gcad.exceptions.NoProjectProposalsException;
import gcad.internationalization.BundleInternationalization;
import gcad.proposals.models.ProposalManager;
import gcad.ui.treeviewer.ProposalContentProvider;
import gcad.ui.treeviewer.ProposalLabelProvider;
import gcad.wizards.AbstractNewProposalWizard;
import gcad.wizards.NewProposalViewWizard;
import gcad.wizards.NewProposalViewWizardPage;

import java.sql.SQLException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;


public class ProposalView extends ViewPart {
	
	protected TreeViewer treeViewer;
	private Label errorLabel;
	private ProposalManager manager;
	// TODO: se usa para poner los iconos de las acciones en una barra de menus dentro de la vista
	private DrillDownAdapter drillDownAdapter;
	private Action doubleClickAction;
	private Proposal root;
	private Composite parent;
	private Proposal proposalSelected;

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		manager = ProposalManager.getManager();		
		root = new Proposal();
		// Expand the tree 
		//treeViewer.setAutoExpandLevel(2);			
		// TODO: si no hay error en el acceso a la base de datos, se muestra el arbol.
		// Si no, se muestra el mensaje de error
		makeTree();
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
		
		
		// Create the help context id for the viewer's control
		
	}
	
	private void makeActions() {
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = treeViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				// TODO: si es una propuesta, mostrar el wizard para registrar una nueva propuesta/answer
				if (obj instanceof Proposal) {
					proposalSelected = (Proposal) obj;
					AbstractNewProposalWizard wizard = new NewProposalViewWizard(BundleInternationalization.getString("NewProposalWizard"));
					wizard.addPages(new NewProposalViewWizardPage(BundleInternationalization.getString("NewProposalWizardPageTitle")));
					WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
					dialog.create();
					dialog.open();
				}
				//MessageDialog.openInformation(treeViewer.getControl().getShell(), "Proposals View", "Double-click detected on "+obj.toString());
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
		if (treeViewer!=null)
			treeViewer.getControl().setFocus();


	}
	
	public void showContentConnected() {
		makeTree();
	}
	
	private void makeTree() {
		try {
			manager.makeProposalsTree(root);
			establishTree();
		// TODO: no se puede conectar con la base de datos
		} catch (SQLException e) {			
			errorLabel = new Label(parent, SWT.NULL);
			errorLabel.setText(e.getLocalizedMessage());
		} catch (NoProjectProposalsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void establishTree() {
		if (errorLabel != null)
			errorLabel.dispose();
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(treeViewer);
		treeViewer.setContentProvider(new ProposalContentProvider());
		treeViewer.setLabelProvider(new ProposalLabelProvider());
		treeViewer.setInput(root);
		//treeViewer.refresh();
		//parent.redraw(0, 0, parent.getBounds().width, parent.getBounds().height, true);
		parent.layout();
		setFocus();
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(treeViewer.getControl(), "Proposals Tree");
		makeActions();
		hookDoubleClickAction();
	}

	public void refresh() {
		treeViewer.refresh();
		
	}

	public Proposal getProposalSelected() {
		return proposalSelected;
	}
	
                                                                                                                                   
}
