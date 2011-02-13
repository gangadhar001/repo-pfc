package presentation.views;

import gcad.Activator;
import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.control.Controller;
import model.business.control.PresentationController;
import model.business.knowledge.Notification;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import presentation.IPresentation;
import presentation.utils.Dialogs;
import presentation.utils.TableConstructor;

// TODO: referencia: http://www.vogella.de/articles/EclipseJFaceTable/article.html

public class NotificationsView extends AbstractView implements IPresentation {

	private static TableViewer tableViewer;
	private ArrayList<Notification> notifications;
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		makeActions();
		createToolbar();
		notifications = new ArrayList<Notification>();
		// TODO: se añade a la lista de observados
		PresentationController.attachObserver(this);
		// TODO: se refresca la vista segun este logueado o no 
		updateState(Controller.getInstance().isLogged());
		
	}
	
	protected void makeActions() {
		deleteAction = new Action(BundleInternationalization.getString("Actions.Delete")) {
			public void run() {
				deleteNotification();
			}
		};
		//deleteAction.setImageDescriptor(getImageDescriptor("delete.gif"));
	}
	
	private void deleteNotification() {
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
	
	/**
     * Create toolbar with actions.
     */
	protected void createToolbar() {
        IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
        mgr.add(deleteAction);
    }

	@Override
	public void setFocus() {
		if (tableViewer!=null && !tableViewer.getControl().isDisposed())
			tableViewer.getControl().setFocus();		
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
			
			cleanComposite();			
			tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.SINGLE);
			String[] titles = { "State", "Type", "Title", "Date"};
			int[] bounds = { 100, 100, 100, 100 };
			
			TableConstructor.initTable(tableViewer, titles, bounds);
			createColumns(titles, bounds);
			
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
	
	private static void createColumns(String[] titles, int[] bounds) {

		TableViewerColumn col = TableConstructor.createTableViewerColumn(tableViewer, titles[0], bounds[0]);
		
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Notification n = (Notification) element;
				return n.getState();
			}
			
			public Image getImage(Object element) {
				Image image;
				if (((Notification) element).getState().equals("Read"))
					image = Activator.getImageDescriptor("resources/images/mail-read.png").createImage();
				else
					image = Activator.getImageDescriptor("resources/images/mail-unread.png").createImage();
				return image;
			}
		});

		// Second column is for the type of the new knowledge
		col = TableConstructor.createTableViewerColumn(tableViewer, titles[1], bounds[1]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Notification n = (Notification) element;
				return n.getKnowledge().getClass().getSimpleName();
			}
		});

		// Third column is for the title of the new knowledge
		col = TableConstructor.createTableViewerColumn(tableViewer, titles[2], bounds[2]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Notification n = (Notification) element;
				return n.getKnowledge().getTitle();
			}
		});

		// Fourth column is for the date of the new knowledge
		col = TableConstructor.createTableViewerColumn(tableViewer, titles[3], bounds[3]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Notification n = (Notification) element;
				return n.getKnowledge().getDate().toString();
			}
		});
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
	
	public void dispose () {
		super.dispose();
		visible = false;
		PresentationController.detachObserver(this);			
	}

	@Override
	public void updateActions(List<String> actionsName) { }
	
	@Override
	public void updateKnowledge() { }
	
}
