package presentation.views;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.control.Controller;
import model.business.control.PresentationController;
import model.business.knowledge.IActions;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.treeviewer.KnowledgeContentProvider;
import model.treeviewer.KnowledgeLabelProvider;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import presentation.IPresentation;
import presentation.utils.Dialogs;

/**
 * This class represents the Proposals view, where the different proposals, answers and actions over them are shown
 */
public class HierarchicalView extends AbstractKnowledgeView implements IPresentation {
	
	private TreeViewer treeViewer;
		
	private Action doubleClickAction;	
	
	private TopicWrapper topicWrapper;
	
	private Object selectedItem;

	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		this.makeActions();
		super.createToolbar();
		// Se inicializa la ra�z del �rbol
		topicWrapper = new TopicWrapper();
		// TODO: se a�ade a la lista de observados
		PresentationController.attachObserver(this);
		// TODO: se refresca la vista segun este logueado o no 
		updateState(Controller.getInstance().isLogged());
	}
	
	@Override
	protected void makeActions() {
		super.makeActions();
		doubleClickAction = new Action() {
			public void run() {
				selection = treeViewer.getSelection();
				selectedItem = ((IStructuredSelection)selection).getFirstElement();
				// TODO: si es un topic, se a�ade una propuesta. Si es una propuesta, se a�ade una respuesta
				if (selectedItem instanceof Topic) {
					addProposal((Topic)selectedItem);
				}
				else if (selectedItem instanceof Proposal) {
					addAnswer((Proposal)selectedItem);
				}
			}
		};
	}
	
	protected void addKnowledge() {
		Object objectSelected = null;
		setSelection();
		if (selection!=null) {
			objectSelected = ((IStructuredSelection)selection).getFirstElement();
			try {
				super.createKnowledge(objectSelected);
			} catch (SQLException e) {
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
		// TODO: si no se selecciona nada, se a�ade un topic
		else {
			addTopic();
		}
	}	
	
	protected void deleteKnowledge() {
		Object objectSelected = null;
		setSelection();
		if (selection!=null) {
			if (Dialogs.showConfirmationMessage(BundleInternationalization.getString("Title.ConfirmDelete"), BundleInternationalization.getString("Message.ConfirmDelete"))) { 
				objectSelected = ((IStructuredSelection)selection).getFirstElement();
				super.removeKnowledge(objectSelected);
			}
		}
		else {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Error", "Debes seleccionar un elemento");	
		}
	}
	
	protected void setSelection() {
		selection = treeViewer.getSelection();
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
				setSelection();
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
			topicWrapper = Controller.getInstance().getTopicsWrapper();
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
			
			treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE);
			treeViewer.setContentProvider(new KnowledgeContentProvider());
			treeViewer.setLabelProvider(new KnowledgeLabelProvider());
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
	public void updateKnowledge() {
		// Si no habia nada seleccionado, esta es una nueva raiz del arbol
		/*if (proposalSelected == null)
			root.add(newKnowledge);*/
		// si se ha a�adido una nueva 
		treeViewer.refresh();
		refreshComposite();
	}

	@Override
	public void updateState(boolean connected) {
		// Si no est� conectado a la base de datos, se limpia el arbol y se establece la etiqueta de mensaje
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
			
	protected void cleanComposite () {
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
	
	
                                                                                                                                   
}
