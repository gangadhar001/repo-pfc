package presentation.views;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.control.Controller;
import model.business.control.KnowledgeController;
import model.business.control.PresentationController;
import model.business.knowledge.AbstractProposal;
import model.business.knowledge.IActions;
import model.business.knowledge.Proposal;
import model.treeviewer.ProposalContentProvider;
import model.treeviewer.ProposalLabelProvider;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import presentation.IPresentation;
import presentation.wizards.NewAnswerViewWizardPage;
import presentation.wizards.NewProposalViewWizardPage;
import presentation.wizards.control.AbstractNewKnowledgeWizardController;
import presentation.wizards.control.NewAnswerViewWizardController;
import presentation.wizards.control.NewProposalViewWizardController;
import exceptions.NoProjectProposalsException;

/**
 * This class represents the Proposals view, where the different proposals, answers and actions over them are shown
 */
public class ProposalView extends ViewPart implements IPresentation {
	
	private TreeViewer treeViewer;
	private Label errorLabel;
	
	private AbstractNewKnowledgeWizardController wizardAbstractProposal;
	private WizardDialog wizardDialog;

	// TODO: se usa para poner los iconos de las acciones en una barra de menus dentro de la vista
	private DrillDownAdapter drillDownAdapter;
	private Action doubleClickAction;
	private Action addAction;
	private Action editAction;
	private Action deleteAction;
	private Proposal root;
	private Composite parent;
	private Proposal proposalSelected;
	private boolean visible = false;
	private ArrayList<String> actionsAllowed;
	
	private ISelection selection;

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		visible = true;
		
		// TODO: se añade a la lista de observados
		PresentationController.attachObserver(this);
		// Se inicializa la raíz del árbol
		root = new Proposal();
		// Se crean las acciones del toolbar y se define el listener para el doble clic
		makeActions();
		createToolbar();
		
		// TODO: Expand the tree 
		//treeViewer.setAutoExpandLevel(2);
		
		// TODO: se refresca la vista segun este logueado o no 
		updateState(Controller.getInstance().isLogged());
	}
	
	/**
	 * This method handles the double click action.
	 * TODO: añadir/eliminar una nueva propuesta/respuesta
	 */
	private void makeActions() {
		addAction = new Action(BundleInternationalization.getString("Actions.Add")) {
            public void run() { 
            	addAbstractProposal();
            }
		};
		//addAction.setImageDescriptor(getImageDescriptor("add.gif"));

		deleteAction = new Action(BundleInternationalization.getString("Actions.Delete")) {
			public void run() {
				deleteAbstractProposal();
			}
		};
		//deleteAction.setImageDescriptor(getImageDescriptor("delete.gif"));
		
		doubleClickAction = new Action() {
			public void run() {
				selection = treeViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				// TODO: si es una propuesta, mostrar el wizard para registrar una nueva propuesta/answer
				if (obj instanceof Proposal) {
					addKnowledge((Proposal)obj);
				}
			}
		};
	}
	
	/**
     * Create toolbar.
     */
    private void createToolbar() {
            IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
            mgr.add(addAction);
            mgr.add(deleteAction);
    }
    
	private int showDialogTypeProposal() {
		MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "New knowledge", null,
				"Choose the type of knowledge to add to the proposal titled: " + proposalSelected.getTitle() , MessageDialog.INFORMATION, new String[] {
						"Proposal", "Answer" }, 0);
		dialog.create();
		int result = dialog.open();
		return result;
	}
	
	private void addAbstractProposal() {
		selection = treeViewer.getSelection();
		Proposal pro = null;
		if (selection!=null) {
			Object obj = ((IStructuredSelection)selection).getFirstElement();
			if (obj instanceof Proposal) {
				pro = (Proposal)obj;
			}
		}
		if (pro!=null)
			addKnowledge(pro);
		else
			MessageDialog.openWarning(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Warning", "Debes seleccionar una propuesta");
	}
	
	private void deleteAbstractProposal() {
		
	}
	
	private void addKnowledge(Proposal pro) {
		int result;
		proposalSelected = pro;
		// Show a dialog to choose whether to add a new proposal or a new answer
		result = showDialogTypeProposal();
		// New Proposal
		if (result == 0) 
			addProposal();
		// New Answer
		else if (result == 1)
			addAnswer();
	}
	
	private void addProposal() {
		wizardAbstractProposal = new NewProposalViewWizardController(BundleInternationalization.getString("NewProposalWizard"), proposalSelected);
		wizardAbstractProposal.addPages(new NewProposalViewWizardPage(BundleInternationalization.getString("NewProposalWizardPageTitle")));
		showWizardDialog(wizardAbstractProposal);
	}
	
	private void addAnswer() {
		wizardAbstractProposal = new NewAnswerViewWizardController(BundleInternationalization.getString("NewAnswerWizard"), proposalSelected);
		wizardAbstractProposal.addPages(new NewAnswerViewWizardPage(BundleInternationalization.getString("NewAnswerWizardPageTitle")));
		showWizardDialog(wizardAbstractProposal);
	}
	
	private void showWizardDialog(Wizard wizard) {
		wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
		wizardDialog.create();
		wizardDialog.open();
	}

	
	// Listener for Double Click Action
	private void hookDoubleClickAction() {
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	
	private void hookSelectionListener() {
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				selection = treeViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				// TODO: si es una propuesta, habilitar todas las acciones posibles
				if (obj instanceof Proposal) {
					
				}
				
			}
		});
	}

	@Override
	public void setFocus() {
		if (treeViewer!=null)
			treeViewer.getControl().setFocus();


	}
		
	private void makeTree() {
		try {
			// TODO: llamarlo a traves del controlador
			ArrayList<AbstractProposal> proposals = KnowledgeController.getProposalsTree();
			for (AbstractProposal p: proposals)
				root.add(p);
			cleanComposite();
			
			treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
			drillDownAdapter = new DrillDownAdapter(treeViewer);
			treeViewer.setContentProvider(new ProposalContentProvider());
			treeViewer.setLabelProvider(new ProposalLabelProvider());
			treeViewer.setInput(root);
			hookDoubleClickAction();
			hookSelectionListener();
			
			refreshComposite();
			
			//setFocus();
			//TODO: PlatformUI.getWorkbench().getHelpSystem().setHelp(treeViewer.getControl(), "Proposals Tree");
		// If it is no possible connect to database, shows a error message
		} catch (SQLException e) {			
			setErrorLabel(e.getLocalizedMessage());
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
	
	public Proposal getProposalSelected() {
		return proposalSelected;
	}

	@Override
	public void updateProposals() {
		treeViewer.refresh();		
	}

	@Override
	public void updateState(boolean connected) {
		// Si no está conectado a la base de datos, se limpia el arbol y se establece la etiqueta de mensaje
		if (!connected) {
			cleanComposite();
			disableActions();
			setErrorLabel(BundleInternationalization.getString("ErrorMessage.NotConnected"));
			refreshComposite();
		}
		
		// Si esta logueado y esta vista es visible, se crea el arbol
		else if (visible) {
			makeTree();
			// Se habilitan las acciones segun los permisos
			if (actionsAllowed.contains(IActions.NEW_PROPOSAL) || actionsAllowed.contains(IActions.NEW_ANSWER))
				addAction.setEnabled(true);
			if (actionsAllowed.contains(IActions.DELETE_PROPOSAL) || actionsAllowed.contains(IActions.DELETE_ANSWER))
				deleteAction.setEnabled(true);
		}
	}
	
	@Override
	public void updateActions(List<String> actionsName) {
		actionsAllowed = (ArrayList<String>) actionsName;		
	}
	
	private void setErrorLabel (String message) {
		errorLabel = new Label(parent, SWT.NULL);
		errorLabel.setText(message);
	}
	
	private void refreshComposite () {
		parent.layout();
	}
		
	private void cleanComposite () {
		// Clean composite parent. 
		// TODO: controlar si no hay nada en el composite	
		if (parent.getChildren().length > 0) {
			// Clean label
			if (errorLabel != null) {
				errorLabel.dispose();
				errorLabel = null;
			}
			// Clean tree
			else {
				parent.getChildren()[0].dispose();
				treeViewer = null;
				root = new Proposal();
			}
		}
	}
	
	public void dispose () {
		super.dispose();
		visible = false;
		PresentationController.detachObserver(this);			
	}
	
	private void disableActions() {
		addAction.setEnabled(false);
		deleteAction.setEnabled(false);
	}
	

	
                                                                                                                                   
}
