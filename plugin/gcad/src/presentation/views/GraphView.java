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
import model.graph.GraphLabelProvider;
import model.graph.MyGraphViewer;
import model.graph.NodeContentProvider;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
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

public class GraphView extends AbstractKnowledgeView implements IPresentation, IZoomableWorkbenchPart {

	private MyGraphViewer graphViewer;
	
	private boolean isEdge;
	
	private Object objectSelected;
	
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		super.makeActions();
		super.createToolbar();
		PresentationController.attachObserver(this);
		// TODO: se refresca la vista segun este logueado o no 
		updateState(Controller.getInstance().isLogged());
	}
    	
	//TODO: controlar si se selecciona una arista al añadir/eliminar, para mostrar mensaje de error
	
	protected void addKnowledge() {
		setSelection();
		if (objectSelected!=null) {
			super.createKnowledge(objectSelected);
		}
		// TODO: si no se selecciona nada, se añade un topic
		else if (!isEdge){
			addTopic();
		}
	}
	
	protected void deleteKnowledge() {
		setSelection();
		if (objectSelected!=null) {
			if (Dialogs.showConfirmationMessage(BundleInternationalization.getString("Title.ConfirmDelete"), BundleInternationalization.getString("Message.ConfirmDelete"))) { 
				super.removeKnowledge(objectSelected);
			}
		}
		else {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Error", "Debes seleccionar un elemento");	
		}		
	}
		
	private void showGraph() {
		cleanComposite();

		graphViewer = new MyGraphViewer(parent, SWT.BORDER);
		graphViewer.setContentProvider(new NodeContentProvider());
		graphViewer.setLabelProvider(new GraphLabelProvider());
		graphViewer.setInput(deployedGraphNodes());
		graphViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				try {
					// TODO: cambiar. Doble clic en un elemento. Segun eso, se
					// añade el objeto adecuado
					graphViewer.setInput(Controller.getInstance()
							.getTopicsWrapper().getTopics());
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
				graphViewer.applyLayout();
				parent.layout();

			}
		});

		graphViewer.getGraph().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO: se coge el nodo seleccionado. Mirar si hay alguno
				// seleccionado y no es una arista
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
	
	@SuppressWarnings("unchecked")
	protected void setSelection() {
		// TODO: si hay algo seleccionado que no sea arista, se toma
		List selectionList = graphViewer.getGraph().getSelection();
		isEdge = false;
		objectSelected = null;
		if (selectionList.size() > 0) {
			if (selectionList.get(0) instanceof GraphNode)
				objectSelected = ((GraphNode) selectionList.get(0)).getData();
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

	// TODO: cambiar
	@Override
	public void updateKnowledge() {
		graphViewer.setInput(deployedGraphNodes());		
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
			showGraph();
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
				graphViewer = null;
			}
		}
	}
		
	public void dispose () {
		super.dispose();
		visible = false;
		PresentationController.detachObserver(this);			
	}

	
}
