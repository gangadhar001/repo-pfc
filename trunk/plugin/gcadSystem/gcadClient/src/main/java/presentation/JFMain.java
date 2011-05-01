package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import model.business.knowledge.Company;
import model.business.knowledge.Groups;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Subgroups;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.swingx.util.MailTransportProxy;

import presentation.customComponents.CustomToolBar;
import presentation.customComponents.JPDetailsCompany;
import presentation.customComponents.JPDetailsCompanyGlassPanel;
import presentation.dataVisualization.NotificationsTable;
import presentation.panelsActions.panelKnowledgeView;
import presentation.panelsActions.panelNotificationsView;
import presentation.utils.ImagesUtilities;

import bussiness.control.ClientController;
import bussiness.control.OperationsUtilities;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

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
/**
 * Class used to create and show the main frame
 */
public class JFMain extends SingleFrameApplication {
    private JMenuBar menuBar;
    private JPanel panelActions;
    private JLabel lblStatus;
    private JTabbedPane tabPanel;
    private JMenuItem menuItemAbout;
    private JMenu menuHelp;
    private JMenu menuOption;
    private JPanel statusPanel;
    private JPanel topPanel;
    private JMenuItem jMenuItem7;
    private JMenuItem jMenuItem6;
    private JMenuItem jMenuItem5;
    private JMenuItem jMenuItem4;
    private JMenu editMenu;
    private JMenuItem jMenuItem3;
    private JMenuItem jMenuItem2;
    private JMenuItem jMenuItem1;
    private JMenu fileMenu;
    private JSeparator jSeparator;
    private JToolBar toolBar;
    private JPanel toolBarPanel;
    private JPanel contentPanel;
    
    private BufferedImage image;
    // Groups of operations already show in the UI
    private ArrayList<String> groupsShown = new ArrayList<String>();
    // List of common actions used in toolbar
    private ArrayList<String> toolbarActions = new ArrayList<String>();
    
	private JPDetailsCompany panelDetailsCompany;
	private JPDetailsCompanyGlassPanel view;
	private panelKnowledgeView panelKnowledge;
	private JMenu menuFile;
	private JMenuItem menuFileExit;
	private JMenu menuTools;

    private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }

    @Override
    protected void startup() {
    	{    		
		try {			
			getMainFrame().setPreferredSize(new java.awt.Dimension(902, 402));
			getMainFrame().setMinimumSize(new java.awt.Dimension(902, 402));
			{	
				// Set glass panel
				panelDetailsCompany = new JPDetailsCompany(this);				
				view = new JPDetailsCompanyGlassPanel(panelDetailsCompany);
				getMainFrame().setGlassPane(view);
			}			
			// TODO: Temporal
			ClientController.getInstance().setCurrentProject(2);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		
    	getMainFrame().setSize(902, 402);
    	}
        {
            topPanel = new JPanel();
            BorderLayout panelLayout = new BorderLayout();
            topPanel.setLayout(panelLayout);
            topPanel.setPreferredSize(new java.awt.Dimension(500, 300));
            {
                contentPanel = new JPanel();
                GridBagLayout contentPanelLayout = new GridBagLayout();
                contentPanelLayout.rowWeights = new double[] {0.1, 0.1};
                contentPanelLayout.rowHeights = new int[] {7, 7};
                contentPanelLayout.columnWeights = new double[] {0.1};
                contentPanelLayout.columnWidths = new int[] {7};
                contentPanel.setLayout(contentPanelLayout);
                topPanel.add(contentPanel, BorderLayout.CENTER);
                contentPanel.setPreferredSize(new java.awt.Dimension(886, 211));
                {
                	tabPanel = new JTabbedPane();
                	
                	contentPanel.add(tabPanel, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 2, 28, 0), 0, 0));
                	tabPanel.setPreferredSize(new java.awt.Dimension(907, 415));
                	{
                		panelActions = new JPanel();
                		GridLayout panelActionsLayout = new GridLayout(1, 1);
                		panelActionsLayout.setHgap(5);
                		panelActionsLayout.setVgap(5);
                		panelActionsLayout.setColumns(1);
                		panelActions.setLayout(panelActionsLayout);
                		tabPanel.addTab(ApplicationInternationalization.getString("tabActions"), null, panelActions, null);
                		panelActions.setBounds(0, 0, 907, 415);
                		panelActions.setName("panelActions");
                		panelActions.setPreferredSize(new java.awt.Dimension(902, 402));
                	}
                }
                {
                	statusPanel = new JPanel();
                	GridBagLayout statusPanelLayout = new GridBagLayout();
                	statusPanelLayout.rowWeights = new double[] {0.1};
                	statusPanelLayout.rowHeights = new int[] {7};
                	statusPanelLayout.columnWeights = new double[] {0.1};
                	statusPanelLayout.columnWidths = new int[] {7};
                	statusPanel.setLayout(statusPanelLayout);
                	contentPanel.add(statusPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(280, 0, 0, 0), 0, 0));
                	statusPanel.setPreferredSize(new java.awt.Dimension(907, 21));
                	{
                		lblStatus = new JLabel();
                		statusPanel.add(lblStatus, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(0, 4, 0, 0), 0, 0));
                		lblStatus.setName("lblStatus");
                	}
                }
            }
            {
                toolBarPanel = new JPanel();
                topPanel.add(toolBarPanel, BorderLayout.NORTH);
                BorderLayout jPanel1Layout = new BorderLayout();
                toolBarPanel.setLayout(jPanel1Layout);
                {
                    toolBar = new CustomToolBar();
                    toolBarPanel.add(toolBar, BorderLayout.CENTER);
                    toolBar.setPreferredSize(new java.awt.Dimension(886, 37));
                }
                {
                    jSeparator = new JSeparator();
                    toolBarPanel.add(jSeparator, BorderLayout.SOUTH);
                }
            }
        }
        menuBar = new JMenuBar();        
        getMainFrame().setJMenuBar(menuBar);
        
        // Get available operations for the logged user
        try {
			List<Operation> operations = ClientController.getInstance().getAvailableOperations();
	        // Configure available groups of actions
	        configureActions(operations);
	        // Configure menus
	        configureMenu(operations);
	        
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRole e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	
		createCommonToolbar();
		
        // Show the main frame
        show(topPanel);        
    }

	// This method is used to configure the main actions tab with the available groups of operations
    private void configureActions(List<Operation> operations) {
    	
		int nOperations = operations.size() + 1;
		groupsShown = new ArrayList<String>();
		GridLayout layout = new GridLayout();
		layout.setColumns(3);
		if (nOperations % 3 == 0)
			layout.setRows(nOperations/3);
		else
			layout.setRows(nOperations/3 + 1);
		
		layout.setHgap(10);
		layout.setHgap(10);
		
		panelActions.setLayout(layout);
		
		for (Operation o : operations) {
			if (!groupsShown.contains(o.getGroup()))
			try {
				createComponent(o);
				groupsShown.add(o.getGroup());
				toolbarActions.add(o.getGroup());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		
		toolbarActions.add("Separator");
		
		// Finally, load the "Logout" action.
		Operation op = new Operation(Groups.Logout.name(), Subgroups.Logout.name(), Operations.Logout.name());
		try {
			createComponent(op);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}    
    
	// Method used to configure menus, according with the available user operations.
    private void configureMenu(List<Operation> operations) {
    	groupsShown = new ArrayList<String>();
    	
    	// First, add "File" Menu 
    	menuFile = new JMenu();
    	menuBar.add(menuFile);
    	menuFile.setName("menuFile");
    	{
    		menuFileExit = new JMenuItem();
    		menuFile.add(menuFileExit);
    		menuFileExit.setName("menuFileExit");
    	}
    	
    	// Add "Tools" menu
    	menuTools = new JMenu();
    	menuBar.add(menuTools);
    	menuTools.setName("menuTools");
    	// Add menu items to "Tools" menu. Each menu item is a group of operations

    	for(Operation o: operations) {
    		// Add menu entry with the name of the operation group
    		JMenuItem item = new JMenuItem();
			item.setName("mitem_manage"+o.getGroup());
			item.setAction(getAppActionMap().get(item.getName()));
			item.setText("Manage "+o.getGroup());
			menuTools.add(item);
			
			toolbarActions.add(item.getText());
    		
//    		if (!groupsShown.contains(o.getGroup())){
//    			JMenu menu = new JMenu();
//    			menu.setName("menu_"+o.getGroup());
//    			menu.setText(o.getGroup());
//    			groupsShown.add(o.getGroup());
//    			List<String> menuItemsName = OperationsUtilities.getSubgroupsId(operations, o.getGroup());
//    			// If there are subgroups, add menu items to manage each subgroup
//    			if (menuItemsName.size() > 0){
//	    			for (String s: menuItemsName){
//	    				if (!s.equals("")){
//	    					JMenuItem item = new JMenuItem();
//	    					item.setName("mitem_"+s);
//	    					item.setAction(getAppActionMap().get(item.getName()));
//	    					item.setText("Manage "+s);
//	    					menu.add(item);
//	    				}
//	    			}
//	    			menuBar.add(menu);
//    			}
//    			// If there aren't subgroups, add the operation directly
//    			else {
//    				for (String s: OperationsUtilities.getOperationsGroupId(operations, o.getGroup())) {
//	    				JMenuItem item = new JMenuItem();
//						item.setName("mitem_"+s);
//						item.setText(s);
//						menu.add(item);
//    				}
//    				menuBar.add(menu);
//    			} 			
//    		}
    	}
    	
    	toolbarActions.add("Separator");
    	
    	// Finally, add "Help" menu
    	menuHelp = new JMenu();
    	menuBar.add(menuHelp);
    	menuHelp.setName("menuHelp");
    	{
    		menuItemAbout = new JMenuItem();
    		menuHelp.add(menuItemAbout);
    		menuItemAbout.setName("menuItemAbout");
    	}
    }
    
    private void createComponent(Operation o) throws IOException {
    	// Load the group image of the operation
		image = ImagesUtilities.loadCompatibleImage(o.getGroup() + ".png");
		JPanel panel = new JPanel();
		
		JButton button = new JButton();
		// Set the group id as the name of the button
		button.setName("btn_"+o.getGroup());
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorderPainted(true);	
		// Set the corresponding action for the name of the button
		button.setAction(getAppActionMap().get(button.getName()));
		button.setText("");		
		button.setIcon(new ImageIcon(image));
		// Save button icon
		ImagesUtilities.addImageButton(button.getName(), image);
		panel.add(button);
		
		button.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent evt) {
				getMainFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  				
				ImagesUtilities.decreaseImageBrightness((JButton) evt.getSource());
			}

			public void mouseEntered(MouseEvent evt) {
				getMainFrame().setCursor(new Cursor(Cursor.HAND_CURSOR));
				ImagesUtilities.increaseImageBrightness((JButton) evt.getSource());
			}
		});
		
		button.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// Clean selection from other buttons
				for (Component c: panelActions.getComponents())
					if (c instanceof JPanel) 
						((JButton)(((JPanel)c).getComponent(0))).setContentAreaFilled(false);
				JButton buttonPressed = ((JButton)e.getSource()); 
				buttonPressed.setContentAreaFilled(true);				
			}
		});
		
		JLabel label = new JLabel();
		label.setText("Show " + o.getGroup() + " view");
		panel.add(label);
		panelActions.add(panel);
    }    
    
    private JButton createToolbarButton(String text) {
    	JButton button = new JButton();
    	button.setText(text);
    	button.setBorder(BorderFactory.createEmptyBorder(5,10,4,10));
    	button.setFocusPainted(false);
    	button.setHorizontalTextPosition(SwingConstants.CENTER);
    	button.setVerticalTextPosition(SwingConstants.BOTTOM);
    	button.setRequestFocusEnabled(false);
    	button.setAction(getAppActionMap().get("add_Knowledge"));
    	return button;
    }
    
    /*** Actions used in menu items ***/
	@Action
    public void mitem_Proposal() {
    	JFKnowledge frameKnowledge = new JFKnowledge("Proposal", null, null);
    	frameKnowledge.setLocationRelativeTo(getMainFrame());
    	frameKnowledge.setVisible(true);
    }
    
    @Action
    public void mitem_Answer() {
    	
    }
    
    @Action
    public void mitem_Topic() {
    	
    }    
    
    /*** Actions used in buttons of the main tab ***/
    @Action
    public void btn_Knowledge() {
    	// Create a new tab in order to store the different Knowledge views
    	int index = tabPanel.getTabCount();

    	if (!existsTab(ApplicationInternationalization.getString("tabKnowledge"))) {
    		panelKnowledge = new panelKnowledgeView(this);
    		tabPanel.insertTab(ApplicationInternationalization.getString("tabKnowledge"), null, panelKnowledge, null, index);
    		tabPanel.setSelectedIndex(index);
    	}
    	else {
    		tabPanel.setSelectedIndex(getIndexTab(ApplicationInternationalization.getString("tabKnowledge")));
    	}

    }
    
    @Action
    public void btn_Notifications() {
    	int index = tabPanel.getTabCount();
    	
    	// Create a new tab in order to show the Notification view 
    	tabPanel.insertTab(ApplicationInternationalization.getString("tabNotification"), null, new panelNotificationsView(), null, index);
//    	tabPanel.setSelectedIndex(getIndexTab(ApplicationInternationalization.getString("tabNotification")));
    	tabPanel.setSelectedIndex(index);
    }
    
	@Action
    public void btn_Statistics() {
    	// Create a new tab in order to store the different Knowledge views (JInternalFrame)
    	int index = tabPanel.getTabCount();

//		tabPanel.insertTab(ApplicationInternationalization.getString("tabKnowledge"), null, panelKnowledge, null, index);
//		tabPanel.setSelectedIndex(index);
    }
	
	/*** Actions for toolbar buttons ***/
	@Action
	public void add_knowledge() {
		if (panelKnowledge != null)
			panelKnowledge.operationAdd();
	}
	
    
    private boolean existsTab(String title) {
    	boolean found = false;
    	for(int i=0; i<tabPanel.getTabCount() && !found; i++) {
    		if (tabPanel.getTitleAt(i).equals(title))
    			found=true;
    	}
    	return found;
    }
    
    private int getIndexTab(String title) {
    	int result = -1;
    	for(int i=0; i<tabPanel.getTabCount() && result==-1; i++) {
    		if (tabPanel.getTitleAt(i).equals(title))
    			result=i;
    	}
    	return result;
	}
    
    // Methods used to show and hide the glass panel, with "fade" effect
    public void fadeIn(Company c) {
		view.fadeIn(c);
		}

	public void fadeOut() {		
		view.fadeOut();		
	}
}
