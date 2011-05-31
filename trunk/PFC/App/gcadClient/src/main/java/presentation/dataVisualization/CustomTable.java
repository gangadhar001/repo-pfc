package presentation.dataVisualization;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class CustomTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937108279685817136L;

	private boolean showDifferentColors = false;
	private Color STRIPED_COLOR = new Color(237, 242, 249);
	// List used to store the row that has an associated color
	private List<Integer> listRowsColor = new ArrayList<Integer>();
	
	public CustomTable() {
		super();
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void addRowToColorize (int row) {
		if (!listRowsColor.contains(row)) {
			listRowsColor.add(row);
			repaint();
		}
	}
	
	public void deleteRowToColorize (int row) {
		if (listRowsColor.contains(row)) {
			listRowsColor.remove((Object)row);
			repaint();
		}
	}
		
	// Method use to display a custom notification table, with different colors.
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
		
		// Paint color rows
		if (start < getRowCount())
			colorizeRows(g, start, Math.min(getRowCount(), end));
			
		// Paint component
		Graphics gr = g.create();
		getUI().paint(gr, this);
		gr.dispose();		
	}
	
	// Colorize the rows that has associated one color
	private void colorizeRows(Graphics g, int start, int end) {
		if (listRowsColor.size() > 0) {
			int rowHeight = getRowHeight();
			int width = getWidth();
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			for (int row = start; row < end; row++) {
				if (listRowsColor.contains(row)) {
                    int x = 4;
                    int y = rowHeight * row + 2;
                    int w = width - 8;
                    int h = rowHeight - 4;
                    int arcSize = 12;
                    ((Graphics2D)g).setPaint(new GradientPaint(x, y, Color.BLUE, x, y + h, new Color(200, 175, 170)));
                    g.fillRoundRect(x, y, w, h, arcSize, arcSize);
                    g.setColor(new Color(250, 220, 220));
				}
			}
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}		
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
