package presentation.views;

import internationalization.BundleInternationalization;

import java.awt.Color;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import model.business.control.Controller;
import model.business.control.PresentationController;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;
import model.business.knowledge.Proposal;
import model.business.knowledge.User;

import org.eclipse.draw2d.GridData;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import presentation.IPresentation;


public class UserInfView extends ViewPart implements IPresentation{


	private Label errorLabel;
	
	private Composite parent;
	private boolean visible = false;
	private TableViewer tableViewer;
	private Object selectedItem;
	private ArrayList<User> users;
	
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		visible = true;	
		users = new ArrayList<User>(); 
		// TODO: se a�ade a la lista de observados
		PresentationController.attachObserver(this);
		// TODO: se refresca la vista segun este logueado o no 
		updateState(Controller.getInstance().isLogged());
		
	}
	
//	public void createPartControl(Composite parent) {
		
//		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//		// row keys...
//		String series1 = "First";
//		String series2 = "Second";
//
//		// column keys...
//		String category1 = "Label 1";
//		String category2 = "Label 2";
//		String category3 = "Label  3";
//
//		dataset.addValue(1.0, series1, category1);
//		dataset.addValue(4.0, series1, category2);
//		dataset.addValue(3.0, series1, category3);
//
//		dataset.addValue(5.0, series2, category1);
//		dataset.addValue(7.0, series2, category2);
//		dataset.addValue(6.0, series2, category3);
//
//		
//		JFreeChart jfreechart = ChartFactory.createBarChart("", "l", "v", dataset, PlotOrientation.VERTICAL, false, true, false);
//			
//		CategoryPlot  plot = (CategoryPlot) jfreechart.getPlot();
//		plot.setInsets(new RectangleInsets(0, 0, 0, 0));
//			        plot.setBackgroundPaint(Color.lightGray);
//					plot.setDomainGridlinePaint(Color.white);
//					plot.setDomainGridlinesVisible(true);
//					plot.setRangeGridlinePaint(Color.white);
//			        /*final double angle = 290D;
//			        pieplot3d.setStartAngle(angle);
//			        pieplot3d.setDirection(Rotation.CLOCKWISE);*/
//			       // final float foreground = 0.5F;
//			       // pieplot3d.setForegroundAlpha(foreground);
//		
//			        // creates the chart component
//			        Composite embeddedComposite = new Composite(parent, SWT.EMBEDDED);
//			        embeddedComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
//			        Frame fileTableFrame = SWT_AWT.new_Frame(embeddedComposite);
//		        ChartPanel panel = new ChartPanel(jfreechart);
//			        panel.setPopupMenu(null);
//		      fileTableFrame.add(panel);
//		      
//		    /*  try {
//				ChartUtilities.saveChartAsPNG(new File ("D:\\UCLM\\5�Curso\\PFC\\repo-pfc\\diagrama.png"), jfreechart, 500, 500);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}*/
		
//	}

	@Override
	public void setFocus() {
		if (tableViewer!=null)
			tableViewer.getControl().setFocus();		
	}
	
	@Override
	public void updateState(boolean connected) {
		// Si no est� conectado a la base de datos, se limpia el arbol y se establece la etiqueta de mensaje
		if (!connected) {
			cleanComposite();
			//disableActions();
			setErrorLabel(BundleInternationalization.getString("ErrorMessage.NotConnected"));
			refreshComposite();
		}
		
		// Si esta logueado y esta vista es visible, se crea el arbol
		else if (visible) {
			makeTable();
		}		
	}

	private void makeTable() {
		try {
			//User u = Controller.getInstance().getUserKnowledge(knowledge);
			cleanComposite();
			
			tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.SINGLE);
			createColumns(parent, tableViewer);
			final Table table = tableViewer.getTable();
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			tableViewer.setContentProvider(new ArrayContentProvider());
			tableViewer.setInput(users);
			
			GridData gridData = new GridData();
			gridData.verticalAlignment = GridData.FILL;
			gridData.horizontalSpan = 2;
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			tableViewer.getControl().setLayoutData(gridData);
						
			refreshComposite();
			
			//setFocus();
			//TODO: PlatformUI.getWorkbench().getHelpSystem().setHelp(treeViewer.getControl(), "Proposals Tree");
		// If it is no possible connect to database, shows a error message
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// This will create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Name", "Surname", "Email", "Company"};
		int[] bounds = { 100, 100, 100, 100 };

		// First column is for the notification state (read/unread)
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				User u = (User) element;
				return u.getName();
			}
		});

		// Second column is for the type of the new knowledge
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				User u = (User) element;
				return u.getSurname();
			}
		});

		// Third column is for the title of the new knowledge
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				User u = (User) element;
				return u.getEmail();
			}
		});

		// Fourth column is for the date of the new knowledge
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				User u = (User) element;
				return u.getCompany().toString();
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
	
	public void refresh(Knowledge k) {
		users.clear();
		users.add(k.getUser());
		tableViewer.refresh();
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
				users = new ArrayList<User>();
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
}