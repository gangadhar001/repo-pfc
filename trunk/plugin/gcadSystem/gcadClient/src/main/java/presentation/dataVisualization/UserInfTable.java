package presentation.dataVisualization;

import javax.swing.table.TableColumnModel;

import presentation.utils.ImageTableUserCellRenderer;

public class UserInfTable extends CustomTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937108279685817136L;	
	
	public UserInfTable() {
		super();
	}	
	
	public void bound() {
		getTableHeader().setFont(getFont().deriveFont(getFont().getSize() + 3f));
		setRowHeight(20);
		TableColumnModel cm = getColumnModel();
        cm.getColumn(0).setMaxWidth(24);
        cm.getColumn(0).setHeaderValue("");
        cm.getColumn(1).setHeaderValue("Name");
        cm.getColumn(2).setHeaderValue("Surname");
        cm.getColumn(3).setHeaderValue("E-mail");
        cm.getColumn(4).setHeaderValue("Company");
        cm.getColumn(4).setCellRenderer(new ImageTableUserCellRenderer());
	}	
	
	

}
