package presentation.dataVisualization;

import javax.swing.table.TableColumnModel;

import presentation.customComponents.CustomTable;
import presentation.utils.DateRenderer;
import presentation.utils.ImageTableNotificationCellRenderer;

public class NotificationsTable extends CustomTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937108279685817136L;	
	
	public NotificationsTable() {
		super();
	}	
	
	public void bound() {
		getTableHeader().setFont(getFont().deriveFont(getFont().getSize() + 3f));
		setRowHeight(20);
		TableColumnModel cm = getColumnModel();
        cm.getColumn(0).setMaxWidth(24);
        cm.getColumn(0).setHeaderValue("");
        cm.getColumn(1).setHeaderValue("Type");
        cm.getColumn(1).setCellRenderer(new ImageTableNotificationCellRenderer());
        cm.getColumn(2).setHeaderValue("Title");
        cm.getColumn(3).setHeaderValue("Date");
        // Convert date to a custom format
        cm.getColumn(3).setCellRenderer(new DateRenderer());
        cm.getColumn(4).setHeaderValue("Author");
	}	

}
