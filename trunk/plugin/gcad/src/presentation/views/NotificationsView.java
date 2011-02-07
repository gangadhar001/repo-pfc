package presentation.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import internationalization.BundleInternationalization;
import model.business.control.Controller;
import model.business.control.PresentationController;
import model.business.knowledge.IActions;
import model.business.knowledge.Notification;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.treeviewer.KnowledgeContentProvider;
import model.treeviewer.KnowledgeLabelProvider;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import presentation.IPresentation;
import presentation.utils.Dialogs;

// TODO: referencia: http://www.vogella.de/articles/EclipseJFaceTable/article.html
public class NotificationsView extends ViewPart implements IPresentation {

protected Label errorLabel;
	
	private Composite parent;
	private ISelection selection;
	private Action deleteAction;
	private boolean visible = false;
	private TableViewer tableViewer;
	private ArrayList<Notification> notifications;
	
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		visible = true;	
		makeActions();
		createToolbar();
		notifications = new ArrayList<Notification>();
		// TODO: se añade a la lista de observados
		PresentationController.attachObserver(this);
		// TODO: se refresca la vista segun este logueado o no 
		updateState(Controller.getInstance().isLogged());
		
	}
	
	private void makeActions() {
		deleteAction = new Action(BundleInternationalization.getString("Actions.Delete")) {
			public void run() {
				deleteNotification();
			}
		};
		//deleteAction.setImageDescriptor(getImageDescriptor("delete.gif"));
	}
	
	protected void deleteNotification() {
		Object objectSelected = null;
		setSelection();
		if (selection!=null) {
			if (Dialogs.showConfirmationMessage(BundleInternationalization.getString("Title.ConfirmDelete"), BundleInternationalization.getString("Message.ConfirmDelete"))) { 
				objectSelected = ((IStructuredSelection)selection).getFirstElement();
				try {
					Controller.getInstance().removeNotification((Notification)objectSelected);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Error", "Debes seleccionar un elemento");	
		}
	}

	@Override
	public void setFocus() {
		if (tableViewer!=null)
			tableViewer.getControl().setFocus();		
	}
	
	/**
     * Create toolbar with actions.
     */
	protected void createToolbar() {
        IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
        mgr.add(deleteAction);
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
			makeTable();
			deleteAction.setEnabled(true);
		}		
	}

	private void makeTable() {
		try {
			// TODO: cambiar el id del proyecto
			notifications = Controller.getInstance().getNotifications();
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
			
			tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.SINGLE);
			createColumns(parent, tableViewer);
			final Table table = tableViewer.getTable();
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			tableViewer.setContentProvider(new ArrayContentProvider());
			tableViewer.setInput(notifications);
			
			GridData gridData = new GridData();
			gridData.verticalAlignment = GridData.FILL;
			gridData.horizontalSpan = 2;
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			tableViewer.getControl().setLayoutData(gridData);
			
			hookSelectionListener();
			
			refreshComposite();
			
			//setFocus();
			//TODO: PlatformUI.getWorkbench().getHelpSystem().setHelp(treeViewer.getControl(), "Proposals Tree");
		// If it is no possible connect to database, shows a error message
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void hookSelectionListener() {
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
	
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				setSelection();
					
			}
		});
	}

	
	protected void setSelection() {
		selection = tableViewer.getSelection();
		
	}

	// This will create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "State", "Type", "Title", "Date"};
		int[] bounds = { 100, 100, 100, 100 };

		// First column is for the notification state (read/unread)
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Notification n = (Notification) element;
				return n.getState();
			}
		});

		// Second column is for the type of the new knowledge
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Notification n = (Notification) element;
				return n.getKnowledge().getClass().getSimpleName();
			}
		});

		// Third column is for the title of the new knowledge
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Notification n = (Notification) element;
				return n.getKnowledge().getTitle();
			}
		});

		// Fourth column is for the date of the new knowledge
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Notification n = (Notification) element;
				return n.getKnowledge().getDate().toString();
			}

//			@Override
//			public Image getImage(Object element) {
//				if (((Person) element).isMarried()) {
//					return CHECKED;
//				} else {
//					return UNCHECKED;
//				}
//			}
		});
		
//		// Fifth column is for the title of the new knowledge
//		col = createTableViewerColumn(titles[2], bounds[2], 2);
//		col.setLabelProvider(new ColumnLabelProvider() {
//			@Override
//			public String getText(Object element) {
//				Notification n = (Notification) element;
//				return p.getKnowledge().getTitle();
//			}
//		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;

	}

	@Override
	public void updateActions(List<String> actionsName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateKnowledge() {
		// TODO Auto-generated method stub
		
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
				tableViewer = null;
				notifications = new ArrayList<Notification>();
			}
		}
	}	
	
	protected void setErrorLabel (String message) {
		errorLabel = new Label(parent, SWT.NULL);
		errorLabel.setText(message);
	}
	
	protected void refreshComposite () {
		parent.layout();
	}
	
	protected void disableActions() {
		deleteAction.setEnabled(false);
	}

}
