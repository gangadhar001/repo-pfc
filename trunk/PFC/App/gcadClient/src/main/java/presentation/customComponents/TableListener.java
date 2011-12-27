package presentation.customComponents;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import presentation.panelsActions.panelNotificationsView;

public class TableListener extends MouseAdapter {

	private panelNotificationsView parent;
	private CheckBoxListener list;
	private CustomTable table;

	public TableListener(panelNotificationsView parent, CheckBoxListener list) {
		this.parent = parent;
		this.list = list;
		this.table = parent.getNotificationTable();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int checkedCount = 0;
		parent.getChkAll().removeItemListener(list);		
		for (int i = 0; i < table.getRowCount(); i++) {
			if (((Boolean) table.getValueAt(i, 0)).booleanValue())
				checkedCount++;
		}
		if (checkedCount == table.getRowCount()) {
			parent.getChkAll().setSelected(true);
		}
		if (checkedCount != table.getRowCount()) {
			parent.getChkAll().setSelected(false);
		}
		
		if (checkedCount == 0)
			parent.clearSelection();
		else
			parent.enableToolbarButtons(true);
			
		parent.getChkAll().addItemListener(list);
	}

}
