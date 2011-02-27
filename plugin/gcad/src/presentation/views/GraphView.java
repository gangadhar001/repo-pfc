package presentation.views;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.control.Controller;
import model.business.control.PresentationController;
import model.business.knowledge.IActions;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.graph.GraphLabelProvider;
import model.graph.MyGraphViewer;
import model.graph.NodeContentProvider;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;

import presentation.IPresentation;
import presentation.utils.Dialogs;

public class GraphView extends KnowledgeView implements IPresentation, IZoomableWorkbenchPart {

	private MyGraphViewer graphViewer;
	
	private boolean isEdge;
	
	private Object objectSelected;
	
	private InformationView userView;
	
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		
		// Control the resize event
		parent.addControlListener(new ControlAdapter() {		
	        @Override
	        public void controlResized(final ControlEvent e) {
	            if (graphViewer!=null)
	            	graphViewer.applyLayout();
	        }
	    });
		
		// This view is added to the observer, in order to update it when a change is produced
		PresentationController.attachObserver(this); 
		updateState(Controller.getInstance().isLogged());
		
		
	}
	
	/*** Method used to add new knowledge ***/
	protected void addKnowledge() {
		setSelection();
		if (objectSelected!=null) {
			try {
				super.createKnowledge(objectSelected);
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
		else if (!isEdge){
			addTopic();
		}
	}
	
	/*** Method used to modify new knowledge ***/
	protected void modifyKnowledge() {
		setSelection();
		if (objectSelected!=null) {
			modifyKnowledge(objectSelected);
		}
	}
	
	/*** Method used to delete knowledge ***/
	protected void deleteKnowledge() {
		setSelection();
		if (objectSelected!=null) {
			if (Dialogs.showConfirmationMessage(BundleInternationalization.getString("Title.ConfirmDelete"), BundleInternationalization.getString("Message.ConfirmDelete"))) { 
				super.removeKnowledge(objectSelected);
			}
		}
		else {
			// TODO
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Error", "Debes seleccionar un elemento");	
		}		
	}
		
	private void showGraph() {
		cleanComposite();

		graphViewer = new MyGraphViewer(parent, SWT.BORDER);
		graphViewer.setContentProvider(new NodeContentProvider());
		graphViewer.setLabelProvider(new GraphLabelProvider());
		graphViewer.setInput(deployedGraphNodes());
		
		// Control double click in an element
		graphViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {			
				setSelection();
				if (objectSelected != null && !isEdge) {
					if (objectSelected instanceof Topic)
						addProposal((Topic)objectSelected);
					else if (objectSelected instanceof Proposal)
						addAnswer((Proposal)objectSelected);
				}			
				graphViewer.applyLayout();
				refreshComposite();
			}
		});

		// Control click in an element
		graphViewer.getGraph().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 setSelection();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		LayoutAlgorithm layout = setLayout();
		graphViewer.setLayoutAlgorithm(layout, true);
		graphViewer.applyLayout();
		fillToolBar();

		refreshComposite();		
	}
	
	protected void setSelection() {
		@SuppressWarnings("rawtypes")
		List selectionList = graphViewer.getGraph().getSelection();
		isEdge = false;
		objectSelected = null;
		// Take the element selected (no edges, only nodes)
		if (selectionList.size() > 0) {
			if (selectionList.get(0) instanceof GraphNode) {
				objectSelected = ((GraphNode) selectionList.get(0)).getData();
				try {
					userView = (InformationView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("gcad.view.UserInformation");
					userView.refresh((Knowledge)objectSelected);
				} catch (PartInitException e) {
					// TODO:
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Error", "Debes seleccionar un elemento");
				}
			}
			else
				isEdge = true;
		}
	}
	
	private ArrayList<Object> deployedGraphNodes() {
		ArrayList<Object> graphNodes = new ArrayList<Object>();
		ArrayList<Topic> topics;
		try {
			topics = Controller.getInstance().getTopicsWrapper().getTopics();
			for (Topic t: topics) {
				graphNodes.add(t);
				for (Proposal p: t.getProposals()) {
					graphNodes.add(p);
					graphNodes.addAll(p.getAnswers());
				}
			}
		} catch (SQLException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
		} catch (InstantiationException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
		} catch (IllegalAccessException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
		} catch (ClassNotFoundException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
		}
		return graphNodes;
		
	}
	
	@Override
	public void setFocus() {
		if (graphViewer!=null)
			graphViewer.getControl().setFocus();
	}
	
	private LayoutAlgorithm setLayout() {
		LayoutAlgorithm layout;
		// layout = new
		// SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		//layout = new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		// layout = new
		// GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		 layout = new HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		// layout = new
		// RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		return layout;

	}
	
	private void fillToolBar() {
		ZoomContributionViewItem toolbarZoomContributionViewItem = new ZoomContributionViewItem(
				this);
		IActionBars bars = getViewSite().getActionBars();
		bars.getMenuManager().add(toolbarZoomContributionViewItem);

	}

	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		return graphViewer;
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
			showGraph();
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
		// Clean composite parent. 	
		if (parent.getChildren().length > 0) {
			// Clean label
			if (errorLabel != null) {
				errorLabel.dispose();
				errorLabel = null;
			}
			// Clean tree
			else {
				parent.getChildren()[0].dispose();
				graphViewer = null;
			}
		}
	}
	
	protected void disableActions() {
		super.disableActions();
		addAction.setEnabled(false);
	}
		
	public void dispose () {
		super.dispose();
		visible = false;
		PresentationController.detachObserver(this);			
	}

	@Override
	public void updateKnowledgeAdded(Knowledge k) {
		graphViewer.setInput(deployedGraphNodes());		
		
	}

	@Override
	public void updateKnowledgeEdited(Knowledge k) {
		graphViewer.setInput(deployedGraphNodes());		
		
	}

	@Override
	public void updateKnowledgeRemoved(Knowledge k) {
		graphViewer.setInput(deployedGraphNodes());
		
	}
	
}
