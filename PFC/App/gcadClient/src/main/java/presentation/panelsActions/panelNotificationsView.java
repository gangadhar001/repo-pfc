package presentation.panelsActions;
import internationalization.ApplicationInternationalization;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

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

import presentation.JFMain;
import presentation.customComponents.ImagePanel;
import presentation.dataVisualization.NotificationsTable;
import presentation.utils.DateUtilities;
import resources.ImagesUtilities;
import bussiness.control.ClientController;
import exceptions.NonPermissionRoleException;
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
public class panelNotificationsView extends ImagePanel {
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
	private int rowSelected;
	
	private JFMain parent;
	
	public panelNotificationsView(JFMain parent) {
		super();
		this.parent = parent;
		try {
			super.setImage(ImagesUtilities.loadCompatibleImage("background.png"));			
			// Get notifications for the actual project
			notifications = ClientController.getInstance().getNotificationsProject();			
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		rowSelected = -1;
		initGUI();
		showNotifications();
	}
	
	// Show notification in the table
	private void showNotifications() {
		Notification n = null;
		for (int i=0; i<notifications.size(); i++) {
			n = notifications.get(i);
			String type = n.getKnowledge().getClass().getSimpleName();
			notificationsTable.setValueAt(type, i, 1);
			notificationsTable.setValueAt(n.getKnowledge().getTitle(), i, 2);
			notificationsTable.setValueAt(n.getKnowledge().getDate(), i, 3);
			notificationsTable.setValueAt(n.getSubject(), i, 4);
			// If the notification state is "unread", colorize the row
			if (notifications.get(i).getState().equals("Unread"))
				notificationsTable.addRowToColorize(i);
		}
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(1008, 601));
			this.setName("this");
			this.setSize(1008, 601);
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.1, 0.01, 0.15};
			thisLayout.rowHeights = new int[] {7, 1, 7};
			thisLayout.columnWeights = new double[] {0.1};
			thisLayout.columnWidths = new int[] {7};
			this.setLayout(thisLayout);
			this.setMinimumSize(new java.awt.Dimension(1008, 601));
			{
				scrollTable = new JScrollPane();
				this.add(scrollTable, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
				scrollTable.setPreferredSize(new java.awt.Dimension(979, 344));
				{					
					notificationsTable = new NotificationsTable(this);
					DefaultTableModel model = new DefaultTableModel(notifications.size(), 5);
					notificationsTable.setModel(model);
					scrollTable.setViewportView(notificationsTable);
					notificationsTable.setShowHorizontalLines(false);
					notificationsTable.setShowVerticalLines(false);
					notificationsTable.setFillsViewportHeight(true);
					notificationsTable.setIntercellSpacing(new Dimension(0,0));
					notificationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					notificationsTable.bound();
					notificationsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent ev) {
							// When select a row, show the details and mark this notification as "read"
							rowSelected = notificationsTable.getSelectedRow();
							if (rowSelected != -1) {
								parent.enableToolbarButton("DeleteNotifications", true);
								notificationsTable.deleteRowToColorize(rowSelected);
								notifications.get(rowSelected).setState("Read");
								try {
									ClientController.getInstance().modifyNotificationState(notifications.get(rowSelected));
								} catch (NotLoggedException e) {
									JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
								} catch (SQLException e) {
									JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
								} catch (NonPermissionRoleException e) {
									JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
								} catch (Exception e) {
									JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
								}
								showDetailRow();
							}
						}
					});
				}			
				
			}
			{
				lblDetail = new JLabel();
				this.add(lblDetail, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
				lblDetail.setName("lblDetail");
				lblDetail.setPreferredSize(new java.awt.Dimension(981, 34));
				lblDetail.setText(ApplicationInternationalization.getString("DetailNotificationsTable"));
			}
			{
				scrollText = new JScrollPane();
				this.add(scrollText, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 10, 10), 0, 0));
				scrollText.setPreferredSize(new java.awt.Dimension(982, 173));
				{
					txtDetails = new JTextArea();
					scrollText.setViewportView(txtDetails);
					scrollText.setViewportView(txtDetails);
				}
				
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showDetailRow() {
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
	
	public void removeRow() {
		notificationsTable.removeRow(rowSelected);
		parent.enableToolbarButton("DeleteNotifications", false);
		notificationsTable.clearSelection();
	}

	public Notification getNotification(int row) {
		return notifications.get(row); 
	}

}
