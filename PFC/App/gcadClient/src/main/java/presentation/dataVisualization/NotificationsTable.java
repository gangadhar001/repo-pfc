package presentation.dataVisualization;

import internationalization.ApplicationInternationalization;

import javax.swing.table.TableColumnModel;


import presentation.customComponents.CheckBoxListener;
import presentation.customComponents.CustomTable;
import presentation.customComponents.TableListener;
import presentation.panelsActions.panelNotificationsView;
import presentation.utils.DateRenderer;
import presentation.utils.ImageTableNotificationCellRenderer;

public class NotificationsTable extends CustomTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937108279685817136L;
	private panelNotificationsView parentD;
	private CheckBoxListener list;	
	
	public NotificationsTable(panelNotificationsView parent) {
		super(parent);
		this.parentD = parent;
		this.list = new CheckBoxListener(this);
		parentD.setList(list);
	}	
	
	// Set type of each column in the table
	public void bound() {
		addMouseListener(new TableListener(parentD, list));
		getTableHeader().setFont(getFont().deriveFont(getFont().getSize() + 3f));
		setRowHeight(20);
		TableColumnModel cm = getColumnModel();
        cm.getColumn(0).setMaxWidth(25);
        cm.getColumn(0).setHeaderValue("");
        cm.getColumn(0).setCellEditor(getDefaultEditor(Boolean.class));   
        cm.getColumn(0).setCellRenderer(getDefaultRenderer(Boolean.class));  
        cm.getColumn(1).setMaxWidth(60);
        cm.getColumn(1).setHeaderValue(ApplicationInternationalization.getString("TypeNotification"));
        cm.getColumn(1).setCellRenderer(new ImageTableNotificationCellRenderer());
        cm.getColumn(2).setHeaderValue(ApplicationInternationalization.getString("TitleNotification"));
        cm.getColumn(3).setHeaderValue(ApplicationInternationalization.getString("DateNotification"));
        // Convert date to a custom format
        cm.getColumn(3).setMaxWidth(150);
        cm.getColumn(3).setCellRenderer(new DateRenderer());
        cm.getColumn(4).setHeaderValue(ApplicationInternationalization.getString("SubjectNotification"));
	}	

}
