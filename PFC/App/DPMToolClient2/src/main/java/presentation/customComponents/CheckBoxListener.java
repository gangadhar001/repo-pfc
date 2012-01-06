package presentation.customComponents;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;

import presentation.panelsActions.panelNotificationsView;

public class CheckBoxListener implements ItemListener {

	private CustomTable table;
	private panelNotificationsView parent;

	public CheckBoxListener(CustomTable table, panelNotificationsView parent) {
		this.table = table;
		this.parent = parent;
	}
	
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();
		if (source instanceof AbstractButton == false)
			return;
		boolean checked = e.getStateChange() == ItemEvent.SELECTED;
		for (int x = 0; x < table.getRowCount(); x++) {
			table.setValueAt(new Boolean(checked), x, 0);
		}		
		if (!checked)
			parent.clearSelection();
		else
			parent.enableToolbarButtons(true);
	}

}
