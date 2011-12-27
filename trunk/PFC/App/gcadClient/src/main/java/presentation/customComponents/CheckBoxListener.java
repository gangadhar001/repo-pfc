package presentation.customComponents;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;

public class CheckBoxListener implements ItemListener {

	private CustomTable table;

	public CheckBoxListener(CustomTable table) {
		this.table = table;
	}
	
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();
		if (source instanceof AbstractButton == false)
			return;
		boolean checked = e.getStateChange() == ItemEvent.SELECTED;
		for (int x = 0; x < table.getRowCount(); x++) {
			table.setValueAt(new Boolean(checked), x, 0);
		}		
	}

}
