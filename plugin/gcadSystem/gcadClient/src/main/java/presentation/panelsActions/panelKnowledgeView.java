package presentation.panelsActions;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreeSelectionModel;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;
import model.business.knowledge.TopicWrapper;

import org.jdesktop.application.Application;
import org.jdesktop.swingx.border.DropShadowBorder;

import presentation.JFMain;
import presentation.customComponents.JPDetailsCompany;
import presentation.customComponents.JPDetailsCompanyGlassPanel;
import presentation.dataVisualization.KnowledgeTree;
import presentation.dataVisualization.UserInfTable;
import bussiness.control.ClientController;


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
public class panelKnowledgeView extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3144486182937579912L;
	private ArrayList<Notification> notifications;
	private JPanel panel;
	private JScrollPane scrollTable;
	private JScrollPane scrollTree;
	private UserInfTable userInfTable;
	private JLabel lblUserInf;
	private JPanel panelTable;
	private JPanel panelGraph;
	private JPanel panelTree;
	protected int rowSelected;
	private TopicWrapper topicWrapper;
	private KnowledgeTree tree;
	protected Knowledge knowledgeSelected;
	protected int column;
	protected int row;
	private JFMain parent;
	private JPDetailsCompany panelDetailsCompany;
	private JPDetailsCompanyGlassPanel view;
	
	public panelKnowledgeView(JFMain parent) {
		super();
		this.parent = parent;
		// Get notifications for the actual project
		try {
			notifications = ClientController.getInstance().getNotifications();			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rowSelected = -1;
		initGUI();
		showTree();
	}
	
	private void showTree() {
		try {
			topicWrapper = ClientController.getInstance().getTopicsWrapper();		
			tree = new KnowledgeTree(topicWrapper);
			scrollTree.setViewportView(tree);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.setPreferredSize(new java.awt.Dimension(184, 390));
			tree.addTreeSelectionListener(new TreeSelectionListener() {				
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// Get selected element in the tree
				if (!(e.getPath().getLastPathComponent() instanceof TopicWrapper)) {
					knowledgeSelected = (Knowledge) e.getPath().getLastPathComponent();
					showUserInfo();
				}
			}
		});
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void showUserInfo() {
		userInfTable.setValueAt(knowledgeSelected.getUser().getName(), 0, 1);
		userInfTable.setValueAt(knowledgeSelected.getUser().getSurname(), 0, 2);
		userInfTable.setValueAt(knowledgeSelected.getUser().getEmail(), 0, 3);
		String company = knowledgeSelected.getUser().getCompany().getName() + ", " + knowledgeSelected.getUser().getCompany().getAddress().getCountry();
		userInfTable.setValueAt(company, 0, 4);
		
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(902, 402));
			this.setName("this");
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.1};
			thisLayout.rowHeights = new int[] {7};
			thisLayout.columnWeights = new double[] {0.5, 0.15};
			thisLayout.columnWidths = new int[] {7, 7};
			this.setLayout(thisLayout);
			this.setSize(902, 402);
			{
				panelTree = new JPanel();
				GridBagLayout panelTreeLayout = new GridBagLayout();
				panelTree.setLayout(panelTreeLayout);
				{
					scrollTree = new JScrollPane();
					panelTree.add(scrollTree, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					scrollTree.setPreferredSize(new java.awt.Dimension(61, 18));
					
				}
				this.add(panelTree, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				panelTreeLayout.rowWeights = new double[] {0.1};
				panelTreeLayout.rowHeights = new int[] {7};
				panelTreeLayout.columnWeights = new double[] {0.1};
				panelTreeLayout.columnWidths = new int[] {7};
			}
			{
				panel = new JPanel();
				GridBagLayout panelLayout = new GridBagLayout();
				this.add(panel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				panelLayout.rowWeights = new double[] {1.0, 0.25};
				panelLayout.rowHeights = new int[] {7, 7};
				panelLayout.columnWeights = new double[] {0.1};
				panelLayout.columnWidths = new int[] {7};
				panel.setLayout(panelLayout);
				{
					panelGraph = new JPanel();
					panel.add(panelGraph, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 0, 0));
				}
				{
					panelTable = new JPanel();
					GridBagLayout panelTableLayout = new GridBagLayout();
					panel.add(panelTable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					panelTableLayout.rowWeights = new double[] {0.05, 1.0};
					panelTableLayout.rowHeights = new int[] {7, 7};
					panelTableLayout.columnWeights = new double[] {0.1};
					panelTableLayout.columnWidths = new int[] {7};
					panelTable.setLayout(panelTableLayout);
					{
						lblUserInf = new JLabel();
						panelTable.add(lblUserInf, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
						lblUserInf.setName("lblUserInf");
					}
					{
						scrollTable = new JScrollPane();
						panelTable.add(scrollTable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(3, 5, 0, 0), 0, 0));
						{
							
							userInfTable = new UserInfTable();
							scrollTable.setViewportView(userInfTable);
							DefaultTableModel model = new DefaultTableModel(1, 5);
							userInfTable.setModel(model);
							userInfTable.setShowHorizontalLines(false);
							userInfTable.setShowVerticalLines(false);
							userInfTable.setFillsViewportHeight(true);
							userInfTable.setIntercellSpacing(new Dimension(0,0));
							userInfTable.setPreferredSize(new java.awt.Dimension(631, 32));
							userInfTable.bound();							
							userInfTable.addMouseListener(new MouseListener() {								
								@Override
								public void mouseReleased(MouseEvent e) {									
								}
								
								@Override
								public void mousePressed(MouseEvent e) {
									// If make click over the image, show the company details
									int colClick = userInfTable.getColumnModel().getColumnIndexAtX(e.getX());
									int rowClick = userInfTable.rowAtPoint(e.getPoint());
									if (rowClick == 0 && colClick == 4 && knowledgeSelected != null) {
										TableColumnModel cm = userInfTable.getColumnModel();
										int leftLimit =  cm.getColumn(0).getWidth() + cm.getColumn(1).getWidth() + cm.getColumn(2).getWidth() + cm.getColumn(3).getWidth();
										int rightLimit =  leftLimit + 10;
										// Show dialog with company details
										if (e.getX() >= leftLimit && e.getX() <= rightLimit) { ;
											parent.fadeIn();
										}	
									}
								}
								
								@Override
								public void mouseExited(MouseEvent e) {
								}
								
								@Override
								public void mouseEntered(MouseEvent e) {
								}
								
								@Override
								public void mouseClicked(MouseEvent e) {									
								}
							});
						}
					}
				}
			}
			updateBorder(scrollTree);
			updateBorder(scrollTable);
			
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	 protected void showDetailRow() {
//		Notification n = notifications.get(rowSelected);
//		StringBuilder details = new StringBuilder();
//		details.append("Conocimiento añadido en: ");
//		details.append(DateUtilities.convert(n.getKnowledge().getDate()));
//		details.append("\n");
//		details.append("Autor: ");
//		details.append(n.getKnowledge().getUser().getName());
//		details.append(" : ");
//		details.append(n.getKnowledge().getUser().getCompany().toString());
//		details.append("\n");
//		details.append("Descripción del conocimiento: ");
//		details.append(n.getKnowledge().getDescription());
//		details.append("\n");
//		
//		txtDetails.setText(details.toString());
//		txtDetails.setCaretPosition(txtDetails.getText().length());
//	}

	private void updateBorder(JComponent comp) {
		 comp.setBorder(BorderFactory.createCompoundBorder(new DropShadowBorder(Color.BLACK, 9, 0.5f, 12, false, false, true, true), comp.getBorder()));
//	        } else {
//	            CompoundBorder border = (CompoundBorder)comp.getBorder();
//	            comp.setBorder(border.getInsideBorder());
//	        }
	    }

}
