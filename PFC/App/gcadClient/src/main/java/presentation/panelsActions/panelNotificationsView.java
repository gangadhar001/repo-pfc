package presentation.panelsActions;
import internationalization.ApplicationInternationalization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.business.knowledge.Notification;

import org.jdesktop.application.Application;
import org.jdesktop.swingx.border.DropShadowBorder;

import presentation.dataVisualization.NotificationsTable;
import presentation.utils.DateUtilities;
import bussiness.control.ClientController;
import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class panelNotificationsView extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3144486182937579912L;
	private NotificationsTable notificationsTable;
	private JScrollPane scrollText;
	private JTextArea txtDetails;
	private JLabel lblDetail;
	private JScrollPane scrollTable;
	private ArrayList<Notification> notifications;
	protected int rowSelected;
	
	public panelNotificationsView() {
		super();
		// Get notifications for the actual project
		try {
			notifications = ClientController.getInstance().getNotifications();			
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRole e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		rowSelected = -1;
		initGUI();
		showNotifications();
	}
	
	private void showNotifications() {
		Notification n = null;
		for (int i=0; i<notifications.size(); i++) {
			n = notifications.get(i);
			String type = n.getKnowledge().getClass().getSimpleName();
			notificationsTable.setValueAt(type, i, 1);
			notificationsTable.setValueAt(n.getKnowledge().getTitle(), i, 2);
			notificationsTable.setValueAt(n.getKnowledge().getDate(), i, 3);
			notificationsTable.setValueAt(n.getKnowledge().getUser().getName(), i, 4);
			// If the notification state is "unread", colorize the row
//			if (notifications.get(i).getState().equals("Unread"))
//				notificationsTable.addRowToColorize(i);
		}
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(902, 402));
			this.setName("this");
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 7};
			thisLayout.columnWeights = new double[] {0.1};
			thisLayout.columnWidths = new int[] {7};
			this.setLayout(thisLayout);
			this.setSize(902, 402);
			{
				scrollTable = new JScrollPane();
				this.add(scrollTable, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(15, 15, 0, 9), 0, 0));
				scrollTable.setPreferredSize(new java.awt.Dimension(872, 190));
				{					
					notificationsTable = new NotificationsTable();
					DefaultTableModel model = new DefaultTableModel(notifications.size(), 5);
					notificationsTable.setModel(model);
					scrollTable.setViewportView(notificationsTable);
					notificationsTable.setShowHorizontalLines(false);
					notificationsTable.setShowVerticalLines(false);
					notificationsTable.setFillsViewportHeight(true);
					notificationsTable.setIntercellSpacing(new Dimension(0,0));
					//					notificationsTable.setPreferredSize(new java.awt.Dimension(844, 159));
					notificationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					notificationsTable.setPreferredSize(new java.awt.Dimension(21, 25));
					notificationsTable.bound();
					notificationsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						
						// When select a row, show the details and mark this notification as "read"
						@Override
						public void valueChanged(ListSelectionEvent e) {
							// TODO: 
							rowSelected = notificationsTable.getSelectedRow();
//							notificationsTable.deleteRowToColorize(rowSelected);
//							showDetailRow();
							
						}
					});
					updateBorder(scrollTable);
				}			
				
			}
			{
				lblDetail = new JLabel();
				this.add(lblDetail, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 15, 0, 0), 0, 0));
				lblDetail.setPreferredSize(new java.awt.Dimension(112, 16));
				lblDetail.setName("lblDetail");
			}
			{
				scrollText = new JScrollPane();
				this.add(scrollText, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 15, 9, 9), 0, 0));
				scrollText.setPreferredSize(new java.awt.Dimension(872, 148));
				scrollText.setSize(882, 148);
				{
					txtDetails = new JTextArea();
					scrollText.setViewportView(txtDetails);
//					txtDetails.setPreferredSize(new java.awt.Dimension(856, 130));
					scrollText.setViewportView(txtDetails);
					txtDetails.setPreferredSize(new java.awt.Dimension(855, 129));
				}
				
				updateBorder(scrollText);
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 protected void showDetailRow() {
		Notification n = notifications.get(rowSelected);
		StringBuilder details = new StringBuilder();
		details.append("Conocimiento añadido en: ");
		details.append(DateUtilities.convert(n.getKnowledge().getDate()));
		details.append("\n");
		details.append("Autor: ");
		details.append(n.getKnowledge().getUser().getName());
		details.append(" : ");
		details.append(n.getKnowledge().getUser().getCompany().toString());
		details.append("\n");
		details.append("Descripción del conocimiento: ");
		details.append(n.getKnowledge().getDescription());
		details.append("\n");
		
		txtDetails.setText(details.toString());
		txtDetails.setCaretPosition(txtDetails.getText().length());
	}

	private void updateBorder(JComponent comp) {
		 comp.setBorder(BorderFactory.createCompoundBorder(new DropShadowBorder(Color.BLACK, 9, 0.5f, 12, false, false, true, true), comp.getBorder()));
//	        } else {
//	            CompoundBorder border = (CompoundBorder)comp.getBorder();
//	            comp.setBorder(border.getInsideBorder());
//	        }
	    }

}
