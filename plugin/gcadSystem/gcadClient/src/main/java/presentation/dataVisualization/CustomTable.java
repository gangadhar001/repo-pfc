package presentation.dataVisualization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import presentation.utils.DateRenderer;

public class CustomTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937108279685817136L;

	private boolean showDifferentColors = false;
	private Color STRIPED_COLOR = new Color(237, 242, 249);
	
	public CustomTable() {
		super();
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
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
}
