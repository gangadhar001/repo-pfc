package presentation.customComponents;

import internationalization.ApplicationInternationalization;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.business.knowledge.Notification;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

import presentation.panelsActions.panelNotificationsView;

import bussiness.control.ClientController;

/** 
 * Custom Knowledge Table
 *
 */
public class CustomTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937108279685817136L;

	private boolean showDifferentColors = false;
	private Color STRIPED_COLOR = new Color(237, 242, 249);
	// List used to store the row that has an associated color
	private List<Integer> listRowsColor = new ArrayList<Integer>();

	private int selectedRowPopup;
	private panelNotificationsView parent;
	
	public CustomTable(panelNotificationsView parent) {
		super();
		selectedRowPopup = -1;
		this.parent = parent;
		this.setAutoCreateRowSorter(true);
		this.setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
	}
	
	public boolean isCellEditable(int row, int column) {
		boolean result = false;
		if (column == 0)
			result = true;
		return result;
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
                    Color c = new Color(155, 199, 216);
                    ((Graphics2D)g).setPaint(new GradientPaint(x, y, c, x, y + h, new Color(237, 242, 249)));
                    g.fillRoundRect(x, y, w, h, arcSize, arcSize);
                    g.setColor(new Color(237, 242, 249));
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
		if (!e.isConsumed() && e.isPopupTrigger()) {
			Point p = new Point(e.getX(), e.getY());
			selectedRowPopup = rowAtPoint(p);
			showPopup(e);
		}
	}
	
	// Show the popup menu
	private JPopupMenu getPopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		JCheckBoxMenuItem striped = new JCheckBoxMenuItem(ApplicationInternationalization.getString("TableStriping"));
		striped.setSelected(showDifferentColors);
		striped.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				showDifferentColors = ((JCheckBoxMenuItem)e.getSource()).isSelected();
				repaint();
			}
		});
		JMenuItem itemDelete = new JMenuItem(ApplicationInternationalization.getString("DeleteRow"));
		itemDelete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeRow(selectedRowPopup);
				repaint();
			}
		});
		
		JMenu itemMark = new JMenu(ApplicationInternationalization.getString("MarkRow"));
		JMenuItem itemMarkRead = new JMenuItem(ApplicationInternationalization.getString("MarkRowRead"));
		JMenuItem itemMarkUnread = new JMenuItem(ApplicationInternationalization.getString("MarkRowUnread"));
		
		itemMarkRead.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				markRead(selectedRowPopup);
				repaint();
			}
		});
		
		itemMarkUnread.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				markUnread(selectedRowPopup);
				repaint();
			}
		});
		
		menu.add(itemMark);
		menu.addSeparator();
		menu.add(itemDelete);
		menu.addSeparator();
		menu.add(striped);
		return menu;
	}

	// Alternate row colors
	protected void setStripedTable() {
		repaint();		
	}
	
	// Remove a row
	public void removeRow (int row) {
		if (row != -1) {
			try {		
				Notification n = parent.getNotification(row);
				DefaultTableModel model = (DefaultTableModel)getModel();
				model.removeRow(row);
				ClientController.getInstance().removeNotificationFromUser(n);
				parent.getNotifications().remove(n);
				parent.clearSelection();
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRoleException e) {
				JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	// Mark a row as "Read"
	public void markRead (int row) {
		try {
			parent.getNotification(row).setState("Read");
			ClientController.getInstance().modifyNotificationState(parent.getNotification(row));
			// Change color of row
			deleteRowToColorize(row);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	// Mark a row as "Unread"
	public void markUnread (int row) {
		try {
			parent.getNotification(row).setState("Unread");
			ClientController.getInstance().modifyNotificationState(parent.getNotification(row));
			// Change color of row
			addRowToColorize(row);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
}
