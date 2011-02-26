package presentation.views;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.control.Controller;
import model.business.control.PresentationController;
import model.business.knowledge.IActions;
import model.business.knowledge.Knowledge;
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
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import presentation.IPresentation;
import presentation.KMPerspective;
import presentation.utils.Dialogs;

/**
 * This class represents the Hierarchical view of knowledge, where the different topics, proposals and answers
 * are shown in a tree
 */
public class HierarchicalView extends KnowledgeView implements IPresentation {
	
	private TreeViewer treeViewer;		
	private Action doubleClickAction;	
	private TopicWrapper topicWrapper;	
	private Object selectedItem;
	private UserInfView userView;
	
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		addDoubleClickAction();
		topicWrapper = new TopicWrapper();
		// This view is added to the observer, in order to update it when a change is produced
		PresentationController.attachObserver(this);
		updateState(Controller.getInstance().isLogged());
	}
		
	private void addDoubleClickAction() {
		doubleClickAction = new Action() {
			public void run() {
				setSelection();
				selectedItem = ((IStructuredSelection)selection).getFirstElement();
				// When a knowledge is selected, all its information is shown in another view
				refreshInfView();
				
				// Add new knowledge to the element selected
				try {
					createKnowledge(selectedItem);
				} catch (SQLException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
				} catch (InstantiationException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
				} catch (IllegalAccessException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
				} catch (ClassNotFoundException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
				}
			}
		};
	}
	
	/*** Method used to show in another view all the information about a knowledge that has been selected ***/
	private void refreshInfView(){
		try {
			userView = (UserInfView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(KMPerspective.USER_INF_VIEW_ID);
			userView.refresh((Knowledge)selectedItem);
		} catch (PartInitException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
		}
	}
	
	/*** Method used to add new knowledge ***/
	protected void addKnowledge() {
		setSelection();
		if (selection!=null) {
			selectedItem = ((IStructuredSelection)selection).getFirstElement();
			try {
				createKnowledge(selectedItem);
			} catch (SQLException e) {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
			} catch (InstantiationException e) {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
			} catch (IllegalAccessException e) {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
			} catch (ClassNotFoundException e) {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
			}
		}
		// If any element is not selected, can be add a topic
		else {
			addTopic();
		}
	}	
	
	/*** Method used to modify new knowledge ***/
	protected void modifyKnowledge() {
		setSelection();
		if (selection!=null) {
			selectedItem = ((IStructuredSelection)selection).getFirstElement();
			modifyKnowledge(selectedItem);
		}
	}
	
	/*** Method used to delete knowledge ***/
	protected void deleteKnowledge() {
		setSelection();
		if (selection!=null) {
			if (Dialogs.showConfirmationMessage(BundleInternationalization.getString("Title.ConfirmDelete"), BundleInternationalization.getString("Message.ConfirmDelete"))) { 
				selectedItem = ((IStructuredSelection)selection).getFirstElement();
				removeKnowledge(selectedItem);
			}
		}
		else {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), BundleInternationalization.getString("SelectItemMandatory"));	
		}
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
				selectedItem = ((IStructuredSelection)selection).getFirstElement();
				refreshInfView();				
			}			
		});
	}
		
	private void makeTree() {
		try {
			cleanComposite();
			
			topicWrapper = Controller.getInstance().getTopicsWrapper();			
			treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE);
			treeViewer.setContentProvider(new KnowledgeContentProvider());
			treeViewer.setLabelProvider(new KnowledgeLabelProvider());
			treeViewer.setInput(topicWrapper);
			hookDoubleClickAction();
			hookSelectionListener();
			
			refreshComposite();
			
			//TODO: PlatformUI.getWorkbench().getHelpSystem().setHelp(treeViewer.getControl(), "Proposals Tree");
		// If it is no possible connect to database, shows a error message
		} catch (SQLException e) {			
			setErrorLabel(e.getLocalizedMessage());
		} catch (InstantiationException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
		} catch (IllegalAccessException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
		} catch (ClassNotFoundException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
		}

	}

	@Override
	public void updateState(boolean connected) {
		// If the user is not logged yet, the tree is cleaned
		if (!connected) {
			cleanComposite();
			disableActions();
			setErrorLabel(BundleInternationalization.getString("ErrorMessage.NotConnected"));
			refreshComposite();
		}
		
		else if (visible) {
			makeTree();
			// The actions are enabled according to user role permissions
			// TODO: añadir las acciones de editar y luego controlar si se puede de verdad (quizar solo se puede añadir una respuesta pero no un topic)
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
	
	@Override
	public void setFocus() {
		if (treeViewer!=null)
			treeViewer.getControl().setFocus();
	}
	
	protected void setSelection() {
		selection = treeViewer.getSelection();
	}
	
	protected void disableActions() {
		super.disableActions();
		addAction.setEnabled(false);
	}
	
	private void refresh() {
		treeViewer.refresh();
		refreshComposite();
	}
	
	public void dispose () {
		super.dispose();
		visible = false;
		PresentationController.detachObserver(this);			
	}

	@Override
	public void updateKnowledgeAdded(Knowledge k) {
		refresh();		
	}

	@Override
	public void updateKnowledgeEdited(Knowledge k) {
		refresh();		
	}

	@Override
	public void updateKnowledgeRemoved(Knowledge k) {
		refresh();		
	}	
                                                                                                                                   
}
