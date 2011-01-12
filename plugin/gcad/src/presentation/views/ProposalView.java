package presentation.views;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.business.control.Controller;
import model.business.control.KnowledgeController;
import model.business.control.PresentationController;
import model.business.knowledge.AbstractKnowledge;
import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.IActions;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
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
	private TopicWrapper topicWrapper;
	private Composite parent;
	private Object selectedItem;
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
		topicWrapper = new TopicWrapper();
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
            	//addAbstractProposal();
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
				selectedItem = ((IStructuredSelection)selection).getFirstElement();
				// TODO: si es un topic, se añade una propuesta. Si es una propuesta, se añade una respuesta
				if (selectedItem instanceof Topic) {
					addProposal();
				}
				else if (selectedItem instanceof Proposal) {
					addAnswer();
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
    
	/*private int showDialogTypeProposal() {
		MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "New knowledge", null,
				"Choose the type of knowledge to add to the proposal titled: " + proposalSelected.getTitle() , MessageDialog.INFORMATION, new String[] {
						"Proposal", "Answer" }, 0);
		dialog.create();
		int result = dialog.open();
		return result;
	}*/
	
	/*private void addAbstractProposal() {
		selection = treeViewer.getSelection();
		Proposal pro = null;
		if (selection!=null) {
			Object obj = ((IStructuredSelection)selection).getFirstElement();
			if (obj instanceof Proposal) {
				pro = (Proposal)obj;
			}
		}
		// El nuevo conocimiento se añade a una propuesta existente
		if (pro!=null)
			addKnowledgeParent(pro);
		else {
			// Se muestra un diálogo para confirmar que se quiere añadir una nueva propuesta raiz
			boolean result = MessageDialog.openQuestion(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Question", "Debes seleccionar una propuesta");
			if (result) {
				addProposal();
			}
		}
			
	}*/
	
	private void deleteAbstractProposal() {
		
	}
	
	/*private void addKnowledgeParent(Proposal pro) {
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
	}*/
	
	private void addProposal() {
		wizardAbstractProposal = new NewProposalViewWizardController(BundleInternationalization.getString("NewProposalWizard"), (Topic)selectedItem);
		wizardAbstractProposal.addPages(new NewProposalViewWizardPage(BundleInternationalization.getString("NewProposalWizardPageTitle")));
		showWizardDialog(wizardAbstractProposal);
	}
	
	private void addAnswer() {
		wizardAbstractProposal = new NewAnswerViewWizardController(BundleInternationalization.getString("NewAnswerWizard"), (Proposal)selectedItem);
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
			// TODO: cambiar el id del proyecto
			topicWrapper = Controller.getKnowledgeTreeProject(2);
			//ArrayList<Topic> topics = KnowledgeController.getKnowledgeTreeProject(2);
						
			/*Topic t1 = new Topic("Tema 1");
			Topic t2 = new Topic("Tema 2");
			
			Proposal p1 = new Proposal("P1", "D1", new Date(), Categories.Analysis, 0);
			Proposal p2 = new Proposal("P2", "D2", new Date(), Categories.Design, 1);
			Proposal p3 = new Proposal("P3", "D3", new Date(), Categories.Analysis, 0);
			
			Answer a1 = new Answer("A1", "A1", new Date());
			Answer a2 = new Answer("A2", "A2", new Date());
			
			p1.add(a1);
			p1.add(a2);
			
			t1.add(p1);
			t1.add(p2);
			t2.add(p3);
			
			TopicWrapper t = new TopicWrapper();
			t.add(t1);
			t.add(t2);*/
			cleanComposite();
			
			treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
			drillDownAdapter = new DrillDownAdapter(treeViewer);
			treeViewer.setContentProvider(new ProposalContentProvider());
			treeViewer.setLabelProvider(new ProposalLabelProvider());
			treeViewer.setInput(topicWrapper);
			hookDoubleClickAction();
			hookSelectionListener();
			
			refreshComposite();
			
			//setFocus();
			//TODO: PlatformUI.getWorkbench().getHelpSystem().setHelp(treeViewer.getControl(), "Proposals Tree");
		// If it is no possible connect to database, shows a error message
		} catch (SQLException e) {			
			setErrorLabel(e.getLocalizedMessage());
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
	
	/*public Proposal getProposalSelected() {
		return proposalSelected;
	}*/

	@Override
	public void updateProposals(AbstractKnowledge newKnowledge) {
		// Si no habia nada seleccionado, esta es una nueva raiz del arbol
		/*if (proposalSelected == null)
			root.add(newKnowledge);
		// si se ha añadido una nueva 
		treeViewer.refresh();
		refreshComposite();*/
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
				topicWrapper = new TopicWrapper();
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
