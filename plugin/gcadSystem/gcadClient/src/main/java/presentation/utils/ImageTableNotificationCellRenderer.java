package presentation.utils;

import java.awt.Component;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

// REFERENCE: http://stackoverflow.com/questions/1291948/adding-an-icon-to-jtable-by-overriding-defaulttablecellrenderer

public class ImageTableNotificationCellRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 582215961691417214L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String knowledgeType = (String)value;
        Icon icon;
		try {
			icon = ImagesUtilities.loadIcon(knowledgeType+".png");
			if(icon == null){
                label.setText(knowledgeType);
                label.setIcon(null);
	        }else{
	                label.setIcon(icon);
	                label.setText(knowledgeType);
	        }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        return label;
		
	}
}