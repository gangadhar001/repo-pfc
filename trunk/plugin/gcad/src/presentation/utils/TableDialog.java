package presentation.utils;

import java.util.ArrayList;

import model.business.knowledge.Company;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TableDialog extends Dialog {

	private Company company;
	private TableViewer tableViewer;
	
	public TableDialog(Shell parentShell, Company company) {
		super(parentShell);
		this.company = company;
	}
	
	 protected void configureShell(Shell shell) {
	      super.configureShell(shell);
	      shell.setText("Company Information");
	 }
	
	protected Control createContents(Composite parent) {		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		tableViewer = new TableViewer(composite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.SINGLE);
		String[] titles = { "CIF", "Name", "Information", "Street", "City", "ZIP", "Country"};
		int[] bounds = { 70, 100, 250, 100, 100, 50, 60 };
		
		TableConstructor.initTable(tableViewer, titles, bounds);
		createColumns(titles, bounds);

		ArrayList<Company> input = new ArrayList<Company>();
		input.add(company);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(input);			
		
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		tableViewer.getControl().setLayoutData(gridData);
		
		composite.layout();		
		super.createButtonBar(composite);
		
		return composite;
	}
	
	// This will create the columns for the table
	private void createColumns(String[] titles, int[] bounds) {

		// First column is for the notification state (read/unread)
		TableViewerColumn col = TableConstructor.createTableViewerColumn(tableViewer, titles[0], bounds[0]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Company c = (Company) element;
				return c.getCif();
			}
		});

		// Second column is for the type of the new knowledge
		col = TableConstructor.createTableViewerColumn(tableViewer, titles[1], bounds[1]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Company c = (Company) element;
				return c.getName();
			}
		});

		// Third column is for the title of the new knowledge
		col = TableConstructor.createTableViewerColumn(tableViewer, titles[2], bounds[2]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Company c = (Company) element;
				return c.getInformation();
			}
			
		});

		// Fourth column is for the date of the new knowledge
		col = TableConstructor.createTableViewerColumn(tableViewer, titles[3], bounds[3]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Company c = (Company) element;
				return c.getAddress().getStreet();
			}
			
		});
		
		col = TableConstructor.createTableViewerColumn(tableViewer, titles[4], bounds[4]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Company c = (Company) element;
				return c.getAddress().getCity();
			}
			
		});
		
		col = TableConstructor.createTableViewerColumn(tableViewer, titles[5], bounds[5]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Company c = (Company) element;
				return c.getAddress().getZip();
			}
			
		});
		
		col = TableConstructor.createTableViewerColumn(tableViewer, titles[6], bounds[6]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Company c = (Company) element;
				return c.getAddress().getCountry();
			}
			
		});
	}
}
