package presentation.views;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import model.business.control.Controller;
import model.business.control.KnowledgeController;
import model.business.control.PresentationController;
import model.business.knowledge.AbstractProposal;
import model.business.knowledge.Answer;
import model.business.knowledge.Operations;
import model.business.knowledge.Proposal;
import model.graph.GraphLabelProvider;
import model.graph.MyGraphViewer;
import model.graph.NodeContentProvider;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import presentation.IPresentation;
import exceptions.NoProjectProposalsException;

public class GraphView extends ViewPart implements IPresentation, IZoomableWorkbenchPart {

	protected MyGraphViewer graphViewer;
	private Label errorLabel;

	private Composite parent;
	private Proposal proposalSelected;
	private boolean visible = false;
	private Proposal root;
	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
		visible = true;
		
		// TODO: se añade a la lista de observados
		PresentationController.attachObserver(this);
				
		root = new Proposal(); 
		
		// TODO: mirar si se ha iniciado sesion previamente 
		updateState(Controller.getInstance().isLogged());		
	}
	
	@Override
	public void setFocus() {
		if (graphViewer!=null)
			graphViewer.getControl().setFocus();
	}
	
	// When the plug-in connects to database, the Proposal tree is shown
	public void showContentConnected() {
		showGraph();
	}
	
	private void showGraph() {
		if (errorLabel != null)
			errorLabel.dispose();
		
		graphViewer = new MyGraphViewer(parent, SWT.BORDER);
		
		graphViewer.setContentProvider(new NodeContentProvider());
		graphViewer.setLabelProvider(new GraphLabelProvider());

		// TODO: llamarlo a traves del controlador
		ArrayList<AbstractProposal> proposals;
		try {
			proposals = KnowledgeController.getProposalsTree();
			for (AbstractProposal p: proposals)
				root.add(p);
			graphViewer.setInput(root.getProposals());
			graphViewer.addDoubleClickListener(new IDoubleClickListener() {
				
				@Override
				public void doubleClick(DoubleClickEvent event) {
					Answer a = new Answer("aa","",new Date());
					root.add(a);
					graphViewer.setInput(root.getProposals());
					graphViewer.applyLayout();
					parent.layout();
					
				}
			});
			
			graphViewer.getGraph().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO: se coge el nodo seleccionado. Mirar si hay alguno seleccionado y no es una arista
					AbstractProposal a = ((AbstractProposal)((GraphNode) graphViewer.getGraph().getSelection().get(0)).getData());
					System.out.println(a);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("b");
					
				}
			});
			
			graphViewer.getGraph().addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
					
			LayoutAlgorithm layout = setLayout();
			graphViewer.setLayoutAlgorithm(layout, true);
			graphViewer.applyLayout();
			fillToolBar();
			parent.layout();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public Proposal getProposalSelected() {
		return proposalSelected;
	}

	@Override
	public void updateOperations(Vector<Operations> availableOperations) {
		// TODO Habilitar/deshabilitar acciones segun operaciones
		
	}
	
	// TODO: cambiar
	@Override
	public void updateProposals() {
		graphViewer.refresh();		
	}

	@Override
	public void updateState(boolean connected) {
		if (!connected) {
			errorLabel = new Label(parent, SWT.NULL);
			errorLabel.setText(BundleInternationalization.getString("ErrorMessage.NotConnected"));
		}
		else
			showGraph();		
	}
	
	public void dispose () {
		super.dispose();
		visible = false;
		PresentationController.detachObserver(this);			
	}
}
