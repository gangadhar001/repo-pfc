package presentation.customComponents;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

// REFERENCE: http://www.java2s.com/Code/Java/Swing-Components/CheckListExample2.htm
public class CheckListRenderer extends JCheckBox implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 952332163716577022L;

	public CheckListRenderer() {
		setBackground(UIManager.getColor("List.textBackground"));
		setForeground(UIManager.getColor("List.textForeground"));
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
		setEnabled(list.isEnabled());
		setSelected(((CheckableItem) value).isSelected());
		setFont(list.getFont());		
		setText(value.toString());
		return this;
	}
}



