package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import model.business.knowledge.Company;
import model.business.knowledge.Groups;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Subgroups;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import presentation.customComponents.CustomToolBar;
import presentation.customComponents.JPDetailsCompany;
import presentation.customComponents.JPDetailsCompanyGlassPanel;
import presentation.panelsActions.panelKnowledgeView;
import presentation.panelsActions.panelNotificationsView;
import presentation.utils.ImagesUtilities;
import bussiness.control.ClientController;
import bussiness.control.OperationsUtilities;
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
    private JPanel statusPanel;
    private JToolBar toolBar;
    private JPanel toolBarPanel;

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
	private panelNotificationsView panelNotifications;
	private List<Operation> operations;

    private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }

    @Override
    protected void startup() {
		try {			
			getMainFrame().setPreferredSize(new java.awt.Dimension(1067, 625));
			getMainFrame().setMinimumSize(new java.awt.Dimension(1067, 625));
			{	
				// Set glass panel
				panelDetailsCompany = new JPDetailsCompany(this);				
				view = new JPDetailsCompanyGlassPanel(panelDetailsCompany);
				getMainFrame().setGlassPane(view);
			}			
			// TODO: Temporal
			ClientController.getInstance().setCurrentProject(2);
			
			GridBagLayout mainFrameLayout = new GridBagLayout();
			getMainFrame().setTitle(ApplicationInternationalization.getString("titleMain"));
			mainFrameLayout.rowWeights = new double[] {0.01, 0.8, 0.05};
			mainFrameLayout.rowHeights = new int[] {7, 7, 7};
			mainFrameLayout.columnWeights = new double[] {0.1};
			mainFrameLayout.columnWidths = new int[] {7};
			getMainFrame().getContentPane().setLayout(mainFrameLayout);
			{
				statusPanel = new JPanel();
				getMainFrame().getContentPane().add(statusPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				GridBagLayout statusPanelLayout = new GridBagLayout();
				statusPanelLayout.rowWeights = new double[] {0.1};
				statusPanelLayout.rowHeights = new int[] {7};
				statusPanelLayout.columnWeights = new double[] {0.1};
				statusPanelLayout.columnWidths = new int[] {7};
				statusPanel.setLayout(statusPanelLayout);
				statusPanel.setPreferredSize(new java.awt.Dimension(907, 21));
				{
					lblStatus = new JLabel();
					statusPanel.add(lblStatus, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.VERTICAL, new Insets(0, 4, 0, 0), 0, 0));
					lblStatus.setName("lblStatus");
					lblStatus.setText(ApplicationInternationalization.getString("lblSatusBar"));
				}
			}
			{
				tabPanel = new JTabbedPane();
				getMainFrame().getContentPane().add(tabPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				tabPanel.setName("tabPanel");
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
				toolBarPanel = new JPanel();
				getMainFrame().getContentPane().add(toolBarPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				BorderLayout jPanel1Layout = new BorderLayout();
				toolBarPanel.setLayout(jPanel1Layout);
				{
					toolBar = new CustomToolBar();
					toolBarPanel.add(toolBar, BorderLayout.CENTER);
					toolBar.setPreferredSize(new java.awt.Dimension(958, 50));
					toolBar.setSize(958, 50);
				}
			}
			
	    	getMainFrame().setSize(1067, 625);
	        menuBar = new JMenuBar();        
	        getMainFrame().setJMenuBar(menuBar);
	        
	        // Get available operations for the logged user
			operations = ClientController.getInstance().getAvailableOperations();
	        // Configure available groups of actions
	        configureActions();
	        // Configure menus
	        configureMenu();			
		
			createCommonToolbar();
			
	        // Show the main frame
	        show(getMainFrame());     
	        
    	} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
    }

	// This method is used to configure the main actions tab with the available groups of operations
    private void configureActions() {
    	
		int nOperations = operations.size() + 1;
		groupsShown = new ArrayList<String>();
		// Layout used in main tab
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
    private void configureMenu() {
    	groupsShown = new ArrayList<String>();
    	
    	// First, add "File" Menu 
    	menuFile = new JMenu();
    	menuBar.add(menuFile);
    	menuFile.setName("menuFile");
    	menuFile.setText(ApplicationInternationalization.getString("menuFile"));
    	{
    		menuFileExit = new JMenuItem();
    		menuFile.add(menuFileExit);
    		menuFileExit.setName("menuFileExit");
    		menuFileExit.setText(ApplicationInternationalization.getString("menuItemExit"));
    	}
    	
    	// Add "Tools" menu
    	menuTools = new JMenu();
    	menuBar.add(menuTools);
    	menuTools.setName("menuTools");
    	menuTools.setText(ApplicationInternationalization.getString("menuTools"));
    	
    	// Add menu items to "Tools" menu. Each menu item is a group of operations
    	for(String s: OperationsUtilities.getSubgroupsId(operations, Groups.Knowledge.name())) {
    		// Add "Tool" menu entry used to manage PDF Generation or Knowledge
    		if (s.equals(Subgroups.Proposal.name()) || s.equals(Subgroups.Answer.name()) || s.equals(Subgroups.Topic.name())) {
    			if (!groupsShown.contains("manage"+Groups.Knowledge.name())) {
		    		JMenuItem item = new JMenuItem();
					item.setName("menuItem_manage"+Groups.Knowledge.name());
					item.setAction(getAppActionMap().get(item.getName()));
					item.setText(ApplicationInternationalization.getString("manage"+Groups.Knowledge.name()));
					menuTools.add(item);
					
					toolbarActions.add("Manage" + Groups.Knowledge.name());
					
					groupsShown.add("manage"+Groups.Knowledge.name());
    			}
    		}
    		else if (s.equals(Subgroups.PDFGeneration.name())) {
    			if (!groupsShown.contains("manage"+Groups.PDFGeneration.name())) {
    				JMenuItem item = new JMenuItem();
					item.setName("menuItem_manage"+Groups.PDFGeneration.name());
					item.setAction(getAppActionMap().get(item.getName()));
					item.setText(ApplicationInternationalization.getString("manage"+Groups.PDFGeneration.name()));
					menuTools.add(item);
					
					toolbarActions.add("Manage" + Groups.Knowledge.name());
					
					groupsShown.add("manage"+Groups.PDFGeneration.name());
    			}
    		}
    	}
    	
    	toolbarActions.add("Separator");
    	
    	// Finally, add "Help" menu
    	menuHelp = new JMenu();
    	menuBar.add(menuHelp);
    	menuHelp.setName("menuHelp");
    	menuHelp.setText(ApplicationInternationalization.getString("menuHelp"));
    	{
    		menuItemAbout = new JMenuItem();
    		menuHelp.add(menuItemAbout);
    		menuItemAbout.setName("menuItemAbout");
    		menuItemAbout.setText(ApplicationInternationalization.getString("menuItemAbout"));
    	}
    }
    
    private void createComponent(Operation o) throws IOException {
    	// Load the group image of the operation
		image = ImagesUtilities.loadCompatibleImage("Groups/" + o.getGroup() + ".png");	
		
		JButton button = new JButton();
		// Set the group id as the name of the button
		button.setName("btn_"+o.getGroup());
		button.setContentAreaFilled(false);
		button.setBorder(BorderFactory.createEmptyBorder(5,10,4,10));
    	button.setFocusPainted(false);
    	button.setHorizontalTextPosition(SwingConstants.CENTER);
    	button.setVerticalTextPosition(SwingConstants.BOTTOM);
    	button.setRequestFocusEnabled(false);
		// Set the corresponding action for the name of the button
		button.setAction(getAppActionMap().get(o.getGroup()));
		button.setText(ApplicationInternationalization.getString("toolbar"+o.getGroup()));		
		button.setIcon(new ImageIcon(image));
		button.setToolTipText(ApplicationInternationalization.getString("toolbar"+o.getGroup()+"Tooltip"));
		// Save button icon
		ImagesUtilities.addImageButton(button.getName(), image);
		
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
					((JButton)c).setContentAreaFilled(false);
				JButton buttonPressed = ((JButton)e.getSource()); 
				buttonPressed.setContentAreaFilled(true);				
			}
		});
	
		panelActions.add(button);
    }    
    
    // Method used to show in the toolbar the common buttons for all tabs
    private void createCommonToolbar() throws MalformedURLException, IOException {
    	// Clear toolbar
		toolBar.removeAll();
		// Add common buttons
		for (String id: toolbarActions) {
			if (id.equals("Separator"))
				toolBar.addSeparator();
			else {
				toolBar.add(createToolbarButton(id));
			}
		}
	}
    
    private JButton createToolbarButton(String id) throws MalformedURLException, IOException {
    	JButton button = new JButton();
    	button.setBorder(BorderFactory.createEmptyBorder(5,10,4,10));
    	button.setFocusPainted(false);
    	button.setHorizontalTextPosition(SwingConstants.CENTER);
    	button.setVerticalTextPosition(SwingConstants.BOTTOM);
    	button.setRequestFocusEnabled(false);
    	button.setAction(getAppActionMap().get(id));
    	button.setText(ApplicationInternationalization.getString("toolbar"+id));
    	button.setToolTipText(ApplicationInternationalization.getString("toolbar"+id+"Tooltip"));
    	button.setIcon(ImagesUtilities.loadIcon("Toolbars/" + id + ".png"));
    	button.setContentAreaFilled(false);
    	return button;
    }
    
    /*** Actions used to manage knowledge and PDF ***/
	@Action
    public void menuItem_manageKnowledge() {
		// Invoke JFKnowledge without arguments (no operation, no data)
    	JDKnowledge frameKnowledge = new JDKnowledge();
    	frameKnowledge.setLocationRelativeTo(getMainFrame());
    	frameKnowledge.setModal(true);
    	frameKnowledge.setVisible(true);
    }
	
	@Action
	public void menuItem_managePDFGeneration() {
		JDPdf framePDF = new JDPdf();
		framePDF.setLocationRelativeTo(getMainFrame());
		framePDF.setModal(true);
		framePDF.setVisible(true);
	}
    
    /*** Actions used in buttons of the main tab and toolbar. Used to show different views  ***/
    @Action
    public void Knowledge() throws MalformedURLException, IOException {
    	// Create a new tab in order to store the Knowledge view
    	int index = tabPanel.getTabCount();

    	if (!existsTab(ApplicationInternationalization.getString("tabKnowledge"))) {
    		panelKnowledge = new panelKnowledgeView(this);
    		tabPanel.insertTab(ApplicationInternationalization.getString("tabKnowledge"), null, panelKnowledge, null, index);
    		tabPanel.setSelectedIndex(index);
    	}
    	else {
    		tabPanel.setSelectedIndex(getIndexTab(ApplicationInternationalization.getString("tabKnowledge")));
    	}
    	createToolbarKnowledgeView();

    }
    
    // Method to add specific button for knowledge to the toolbar
    private void createToolbarKnowledgeView() throws MalformedURLException, IOException {
    	toolBar.removeAll();
		createCommonToolbar();
		toolBar.addSeparator();
		// Includes operation "add", "modify", and "delete", if the user has permissions
		List<String> availableOps = OperationsUtilities.getOperationsGroupId(operations, Groups.Knowledge.name());
		if (availableOps.contains(Operations.Add.name()))
			toolBar.add(createToolbarButton(Operations.Add.name()+Groups.Knowledge.name()));
		if (availableOps.contains(Operations.Modify.name()))
			toolBar.add(createToolbarButton(Operations.Modify.name()+Groups.Knowledge.name()));
		if (availableOps.contains(Operations.Delete.name()))
			toolBar.add(createToolbarButton(Operations.Delete.name()+Groups.Knowledge.name()));
		
	}

    /*** Actions used to show different tabs***/ 
	@Action
    public void Notifications() {
    	int index = tabPanel.getTabCount();
    	
    	// Create a new tab in order to show the Notification view 
    	if (!existsTab(ApplicationInternationalization.getString("tabNotification"))) {
    		panelNotifications = new panelNotificationsView();
	    	tabPanel.insertTab(ApplicationInternationalization.getString("tabNotification"), null, panelNotifications, null, index);
	    	tabPanel.setSelectedIndex(index);
    	}
    	else {
    		tabPanel.setSelectedIndex(getIndexTab(ApplicationInternationalization.getString("tabNotification")));
    	}
    }
    
	@Action
    public void Statistics() {
    	// Create a new tab in order to store the different Knowledge views (JInternalFrame)
    	int index = tabPanel.getTabCount();
    	// TODO: prueba
    	JDStatistics sta = new JDStatistics(getMainFrame());
    	sta.setModal(true);
    	sta.setVisible(true);
    	
//		tabPanel.insertTab(ApplicationInternationalization.getString("tabKnowledge"), null, panelKnowledge, null, index);
//		tabPanel.setSelectedIndex(index);
    }
	
	/*** Actions for specific toolbar buttons. This actions are used when an specific tab is shown ***/
	@Action
	public void AddKnowledge() {
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
