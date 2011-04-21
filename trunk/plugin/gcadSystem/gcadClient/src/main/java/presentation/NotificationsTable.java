package presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import presentation.utils.DateUtilities;

public class NotificationsTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937108279685817136L;

	private boolean showDifferentColors = false;
	private Color STRIPED_COLOR = new Color(237, 242, 249);
	
	public NotificationsTable() {
		setName("notificationsTable");		
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	void bound() {
		getTableHeader().setFont(getFont().deriveFont(getFont().getSize() + 3f));
		setRowHeight(30);
		TableColumnModel cm = getColumnModel();
        cm.getColumn(0).setMaxWidth(24);
        cm.getColumn(1).setPreferredWidth(400);
        cm.getColumn(1).setMaxWidth(400);
        cm.getColumn(0).setHeaderValue("");
        cm.getColumn(1).setHeaderValue("Subject");
        cm.getColumn(2).setHeaderValue("Date");
        // Convert date to a custom format
        cm.getColumn(2).setCellRenderer(new DateRenderer());
	}
	
	// Method use to display a custom table, with diferent colors.
	protected void paintComponent(Graphics g) {
		Rectangle rec = g.getClipBounds();
		int rowH = getRowHeight();
		int width = getWidth();
		int start = rec.y / rowH;
		int end = (rec.y + rec.height) / rowH + 1;
		
		g.setColor(Color.WHITE);
		g.fillRect(rec.x, rec.y, rec.width, rec.height);
		
		if (showDifferentColors)  {
			g.setColor(STRIPED_COLOR);
			// In a striped table, every two rows change color
			for (int i = start / 2*2; i<end; i+=2){
				g.fillRect(0, rowH*i, width, rowH);
			}
		}			
			
		// Paint component
		Graphics gr = g.create();
		getUI().paint(gr, this);
		gr.dispose();		
	}
	
	// Methods used to show the pop-up with actions
	private void showPopup(MouseEvent e) {
		getPopupMenu().show(this, e.getX(), e.getY());
	}
	
	protected void processMouseEvent(MouseEvent e) {
		super.processMouseEvent(e);
		if (!e.isConsumed() && e.isPopupTrigger())
			showPopup(e);
	}
	
	private JPopupMenu getPopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		JCheckBoxMenuItem striped = new JCheckBoxMenuItem("Table Striping");
		striped.setSelected(showDifferentColors);
		striped.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				showDifferentColors = ((JCheckBoxMenuItem)e.getSource()).isSelected();
				repaint();
			}
		});
		menu.add(striped);
		return menu;
	}

	protected void setStripedTable() {
		repaint();		
	}


	private static class DateRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 582215961691417214L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			return super.getTableCellRendererComponent(table, DateUtilities.convert((Date)value), isSelected, hasFocus, row, column);
			
		}
	}
}
