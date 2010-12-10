package presentation.views;

import exceptions.NoProjectProposalsException;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import model.business.control.KnowledgeController;
import model.business.control.PresentationController;
import model.business.knowledge.AbstractProposal;
import model.business.knowledge.Operations;
import model.business.knowledge.Proposal;
import model.treeviewer.ProposalContentProvider;
import model.treeviewer.ProposalLabelProvider;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
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

import presentation.IPresentation;
import presentation.wizards.NewProposalViewWizardPage;
import presentation.wizards.control.AbstractNewProposalWizardController;
import presentation.wizards.control.NewProposalViewWizard;

/**
 * This class represents the Proposals view, where the different proposals, answers and actions over them are shown
 */
public class ProposalView extends ViewPart implements IPresentation {
	
	protected TreeViewer treeViewer;
	private Label errorLabel;
	private KnowledgeController manager;
	// TODO: se usa para poner los iconos de las acciones en una barra de menus dentro de la vista
	private DrillDownAdapter drillDownAdapter;
	private Action doubleClickAction;
	private Proposal root;
	private Composite parent;
	private Proposal proposalSelected;

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		manager = KnowledgeController.getManager();		
		root = new Proposal();
		// TODO: Expand the tree 
		//treeViewer.setAutoExpandLevel(2);			
		updateState(false);
		
		// TODO: se a�ade a la lista de observados
		PresentationController.attachObserver(this);
	}
	
	/**
	 * This method handles the double click action.
	 * TODO: a�adir/eliminar una nueva propuesta/respuesta
	 */
	private void makeActions() {
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = treeViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				// TODO: si es una propuesta, mostrar el wizard para registrar una nueva propuesta/answer
				if (obj instanceof Proposal) {
					proposalSelected = (Proposal) obj;
					// Show a dialog to choose whether to add a new proposal or a new answer
					MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "New knowledge", null,
							"Choose the type of knowledge to add to the proposal titled: " + proposalSelected.getTitle() , MessageDialog.INFORMATION, new String[] {
									"Proposal", "Answer" }, 0);
					dialog.create();
					int result = dialog.open();
					// New Proposal
					if (result == 0) {
						AbstractNewProposalWizardController wizard = new NewProposalViewWizard(BundleInternationalization.getString("NewProposalWizard"));
						wizard.addPages(new NewProposalViewWizardPage(BundleInternationalization.getString("NewProposalWizardPageTitle")));
						WizardDialog proposalDialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
						proposalDialog.create();
						proposalDialog.open();
					}
				}
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
	
	// When the plug-in connects to database, the Proposal tree is shown
	public void showContentConnected() {
		makeTree();
	}
	
	private void makeTree() {
		try {
			ArrayList<AbstractProposal> proposals = manager.getProposalsTree();
			for (AbstractProposal p: proposals)
				root.add(p);
			establishTree();
		// If it is no possible connect to database, shows a error message
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
	
	// Establish and show the tree
	private void establishTree() {
		if (errorLabel != null)
			errorLabel.dispose();
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(treeViewer);
		treeViewer.setContentProvider(new ProposalContentProvider());
		treeViewer.setLabelProvider(new ProposalLabelProvider());
		treeViewer.setInput(root);
		parent.layout();
		setFocus();
		//TODO: PlatformUI.getWorkbench().getHelpSystem().setHelp(treeViewer.getControl(), "Proposals Tree");
		makeActions();
		hookDoubleClickAction();
	}

	public Proposal getProposalSelected() {
		return proposalSelected;
	}

	@Override
	public void updateOperations(Vector<Operations> availableOperations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProposals() {
		treeViewer.refresh();		
	}

	@Override
	// TODO
	public void updateState(boolean connected) {
		if (!connected) {
			errorLabel = new Label(parent, SWT.NULL);
			errorLabel.setText("NO CONECTADO");
		}
		else {
			makeTree();
		}
		
	}
	
                                                                                                                                   
}