package presentation.panelsActions;

import internationalization.ApplicationInternationalization;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;

import org.jdesktop.application.Application;

import com.ibm.icu.text.SimpleDateFormat;

import presentation.JFMain;
import presentation.customComponents.CustomTable;
import presentation.customComponents.ImagePanel;
import presentation.dataVisualization.NotificationsTable;
import presentation.utils.TextPaneUtilities;
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
	private JCheckBox chkAll;
	private JScrollPane scrollText;
	private JTextPane txtDetails;
	private JLabel lblDetail;
	private JScrollPane scrollTable;
	private ArrayList<Notification> notifications;
	private int rowSelected;
	
	private JFMain parent;
	private ItemListener list;
	
	public panelNotificationsView(JFMain parent) {
		super();
		this.parent = parent;
		try {
			super.setImage(ImagesUtilities.loadCompatibleImage("background.png"));			
			// Get notifications for the actual project
			notifications = ClientController.getInstance().getNotificationsUser();		
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
			String type = "";
			String title = "";
			Date date = null;
			String subject = "";
			try {
				 type = n.getKnowledge().getClass().getSimpleName();
				 title = n.getKnowledge().getTitle();
				 date = n.getKnowledge().getDate();
				 subject = n.getSubject();
			}
			catch (NullPointerException e) {
				// If knowledge is null, means that this knowledge has been removed, making it necessary to 
				// parse the subject of the notification in order to extract the knowledge information
				Object [] information = parseSubject(n.getSubject());
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				type = information[0].toString();
				title = information[1].toString();
				try {
					date = format.parse(information[2].toString());
				} catch (ParseException e1) {
					date = new Date();
				}
				subject = information[3].toString();
			}
			notificationsTable.setValueAt(new Boolean(false), i, 0);
			notificationsTable.setValueAt(type, i, 1);
			notificationsTable.setValueAt(title, i, 2);
			notificationsTable.setValueAt(date, i, 3);
			notificationsTable.setValueAt(subject, i, 4);
			notificationsTable.setValueAt(n.getId(), i, 5);
			notificationsTable.getColumnModel().getColumn(5).setMaxWidth(0);
			notificationsTable.getColumnModel().getColumn(5).setMinWidth(0);
			notificationsTable.getColumnModel().getColumn(5).setPreferredWidth(0);
			// If the notification state is "unread", colorize the row
			if (notifications.get(i).getState().equals("Unread"))
				notificationsTable.addRowToColorize(i);
		}
	}

	// Method used to parse the notification subject when its knowledge has been removed
	private Object[] parseSubject(String subject) {
		Object[] result = new Object[6];
		int typeStart = subject.indexOf("Type");
		int titleStart = subject.indexOf("Title");
		int dateStart = subject.indexOf("Date");
		int authorStart = subject.indexOf("Author");
		int companyStart = subject.indexOf("Company");
		String type = subject.substring(typeStart + new String("Type: ").length(), titleStart - 1);
		String title = subject.substring(titleStart + new String("Title: ").length(), dateStart - 1);
		String date = subject.substring(dateStart + new String("Date: ").length(), authorStart - 1);
		String author = subject.substring(authorStart + new String("Author: ").length(), companyStart - 1);
		String company = subject.substring(companyStart + new String("Company: ").length());
		String sub = subject.substring(0, typeStart -1);
		result[0] = type; result[1] = title; result[2] = date; result[3] = sub; result[4] = author; result[5] = company;
		return result;
		
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(1008, 601));
			this.setName("this");
			this.setSize(1008, 601);
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.01, 1.1, 0.01, 1.25};
			thisLayout.rowHeights = new int[] {7, 7, 7, 7};
			thisLayout.columnWeights = new double[] {0.1};
			thisLayout.columnWidths = new int[] {7};
			this.setLayout(thisLayout);
			this.setMinimumSize(new java.awt.Dimension(1008, 601));
			{
				scrollTable = new JScrollPane();
				this.add(scrollTable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
				scrollTable.setPreferredSize(new java.awt.Dimension(988, 349));
				{					
					notificationsTable = new NotificationsTable(this);
					DefaultTableModel model = new DefaultTableModel(notifications.size(), 6);
					notificationsTable.setModel(model);
					scrollTable.setViewportView(notificationsTable);
					notificationsTable.setShowHorizontalLines(false);
					notificationsTable.setShowVerticalLines(false);
					notificationsTable.setFillsViewportHeight(true);
					notificationsTable.setIntercellSpacing(new Dimension(10,0));
					notificationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					notificationsTable.bound();
					notificationsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent ev) {
							int previousRow = rowSelected;
							rowSelected = notificationsTable.getSelectedRow();
							if (rowSelected != -1) {
								if (rowSelected != previousRow) {
									enableToolbarButtons(true);
									markRead(rowSelected, getNotification(rowSelected));
								}
								else 
									clearSelection();
							}
							else
								clearSelection();
						}
					});
				}			
				
			}
			{
				chkAll = new JCheckBox();
				this.add(chkAll, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 10, 0, 0), 0, 0));
				chkAll.setName("chkAll");
				chkAll.setFont(new Font(chkAll.getFont().getName(), Font.BOLD, chkAll.getFont().getSize()));
				chkAll.setText(ApplicationInternationalization.getString("chkAll"));
				chkAll.addItemListener(list);
				if (notifications!= null && notifications.size()==0)
					chkAll.setEnabled(false);
			}
			{
				lblDetail = new JLabel();
				this.add(lblDetail, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
				lblDetail.setName("lblDetail");
				lblDetail.setFont(new Font(chkAll.getFont().getName(), Font.BOLD, 16));
				lblDetail.setPreferredSize(new java.awt.Dimension(981, 34));
				lblDetail.setText(ApplicationInternationalization.getString("DetailNotificationsTable"));
			}
			{
				scrollText = new JScrollPane();
				this.add(scrollText, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 10, 10), 0, 0));
				scrollText.setPreferredSize(new java.awt.Dimension(982, 173));
				{
					txtDetails = new JTextPane();
					scrollText.setViewportView(txtDetails);
				}
				
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void markRead(int row, Notification n) {
		// When select a row, show the details and mark this notification as "read"
		if (row != -1) {
			// Change color
			notificationsTable.deleteRowToColorize(row);
			n.setState("Read");
			try {
				ClientController.getInstance().modifyNotificationState(n);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRoleException e) {
				JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
			showDetailRow(n);
		}
		else
			clearSelection();
		
	}
	
	protected void markUnread(int row, Notification n) {
		// When select a row, show the details and mark this notification as "read"
		if (row != -1) {
			// Change color
			notificationsTable.addRowToColorize(row);
			n.setState("Unread");
			try {
				ClientController.getInstance().modifyNotificationState(n);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRoleException e) {
				JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(parent.getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
			showDetailRow(n);
		}
		else
			clearSelection();
		
	}

	private void showDetailRow(Notification n) {
		if (!chkAll.isSelected()) {
			txtDetails.setText("");		
			Knowledge k = n.getKnowledge();		
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			txtDetails.setFont(new Font(lblDetail.getFont().getName(), Font.PLAIN, 12));
			// Create text and styles for TextPane
			if (k != null) {		
				String[] initString = {
					n.getSubject(),
					"\n\n",
					ApplicationInternationalization.getString("modifiedDate_Notification") + ": ",
					format.format(k.getDate()),
					"\n\n",
					ApplicationInternationalization.getString("knowledgeInformation_Notification"),
					"\n",
					"    " + ApplicationInternationalization.getString("titleKnowledge_Notification") + ": ",
					k.getTitle(),
					"\n",
					"    " + ApplicationInternationalization.getString("knowledgeDescription_Notification") + ": ",
					k.getDescription(),
					"\n",
					"    " + ApplicationInternationalization.getString("knowledgeStatus_Notification") + ": ",
					ApplicationInternationalization.getString(k.getStatus().name()),
					"\n",
					"    " + ApplicationInternationalization.getString("filesStatus_Notification") + ": ",
					String.valueOf(k.getFiles().size()),
					"\n\n",
					
					ApplicationInternationalization.getString("knowledgeAuthor_Notification"),
					"\n",
					"    " + ApplicationInternationalization.getString("authorName_Notification") + ": ",
					k.getUser().getName() + ", " + k.getUser().getSurname(),
					"\n",
					"    " + ApplicationInternationalization.getString("authorRole_Notification") + ": ",
					ApplicationInternationalization.getString(k.getUser().getRole().name()),
					"\n",
					"    " + ApplicationInternationalization.getString("authorSeniority_Notification") + ": ",
					String.valueOf(k.getUser().getSeniority()),
					"\n",
					"    " + ApplicationInternationalization.getString("authorCompany_Notification") + ": ",
					k.getUser().getCompany().getName() + " (" + k.getUser().getCompany().getAddress().getCity() +
								", " + k.getUser().getCompany().getAddress().getCountry() + ")"
				};
			
				String[] initStyles = { 
						"bold", "regular",
						"bold", "regular", "regular",
						"bold", "regular",
						"bold", "regular", "regular",
						"bold", "regular", "regular",					
						"bold", "regular", "regular",					
						"bold", "regular", "regular",					
						"bold", "regular",					
						"bold", "regular", "regular",
						"bold", "regular", "regular",
						"bold", "regular", "regular",
						"bold", "regular"
				};		
				
				TextPaneUtilities.setStyledText(txtDetails, initString, initStyles);
				//txtDetails.setCaretPosition(txtDetails.getText().length());
			}
			
			else {
				Object [] information = parseSubject(n.getSubject());
				String title = information[1].toString();
				Date date = null;
				try {
					date = format.parse(information[2].toString());
				} catch (ParseException e1) {
					date = new Date();
				}
				String subject = information[3].toString();		
				String author = information[4].toString();
				String company = information[5].toString();
				String[] initString = {
						subject,
						"\n\n",
						ApplicationInternationalization.getString("modifiedDate_Notification") + ": ",
						format.format(date),
						"\n\n",
						ApplicationInternationalization.getString("knowledgeDescription_Notification") + ": ",
						"\n",
						"    " + ApplicationInternationalization.getString("titleKnowledge_Notification") + ": ",
						title,
						"\n\n",
						
						ApplicationInternationalization.getString("knowledgeAuthor_Notification"),
						"\n",
						"    " + ApplicationInternationalization.getString("authorName_Notification") + ": ",
						author,
						"\n",
						"    " + ApplicationInternationalization.getString("authorCompany_Notification") + ": ",
						company
					};
				
					String[] initStyles = { 
							"bold", "regular",
							"regular", "regular", "regular",
							"bold", "regular",
							"bold", "regular", "regular",
							
							"bold", "regular", 
							"bold", "regular", "regular",
							"bold", "regular"
					};	
	
				TextPaneUtilities.setStyledText(txtDetails, initString, initStyles);
				//txtDetails.setCaretPosition(txtDetails.getText().length());
			}
		}
	}
	
	public void removeRow() {
		for (int i = 0; i < notificationsTable.getRowCount(); i++) {
			if (((Boolean) notificationsTable.getValueAt(i, 0)).booleanValue()){
				notificationsTable.removeRow(i);
			}
		}
		clearSelection();
		if (notificationsTable.getRowCount() == 0)
			chkAll.setSelected(false);
	}

	public Notification getNotification(int row) {
		Notification n = null;
		boolean found = false;
		for(int i = 0; i < notifications.size() && !found; i++) {
			if (notifications.get(i).getId() == Integer.parseInt(notificationsTable.getValueAt(row, 5).toString())) {
				n = notifications.get(i);
				found = true;
			}
		}
		return n;
	}

	public JCheckBox getChkAll() {
		return chkAll;
	}

	public CustomTable getNotificationTable() {
		return notificationsTable;
	}

	public void setList(ItemListener list) {
		this.list = list;
	}

	public void clearSelection() {
		notificationsTable.clearSelection();
		txtDetails.setText("");	
		enableToolbarButtons(false);
	}

	// Method used to mark rows as "Read"
	public void markRead() {
		for (int i = 0; i < notificationsTable.getRowCount(); i++) {
			if (((Boolean) notificationsTable.getValueAt(i, 0)).booleanValue()){
				Notification n = getNotification(i); 
				if (n.getState() != "Read")
					markRead(i, n);
			}
		}
		
	}
	
	// Method used to mark rows as "Unread"
	public void markUnread() {
		for (int i = 0; i < notificationsTable.getRowCount(); i++) {
			if (((Boolean) notificationsTable.getValueAt(i, 0)).booleanValue()){
				Notification n = getNotification(i);
				if (n.getState() != "Unread")
					markUnread(i, n);
			}
		}		
	}
	
	public void enableToolbarButtons(boolean value) {
		parent.enableToolbarButton("DeleteNotifications", value);
		parent.enableToolbarButton("MarkReadNotifications", value);
		parent.enableToolbarButton("MarkUnreadNotifications", value);
	}

	public List<Notification> getNotifications() {
		return notifications;
	}
	
	
	
	

}
