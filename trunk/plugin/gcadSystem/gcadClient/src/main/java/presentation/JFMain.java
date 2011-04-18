package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import model.business.knowledge.Operation;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import bussiness.control.ClientController;
import bussiness.control.OperationsUtilities;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import exceptions.NonPermissionRole;


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
    private JTabbedPane tabPanel;
    private JMenuItem menuItemAbout;
    private JMenu menuHelp;
    private JMenu menuOption;
    private JLabel lblStatus;
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
    private JButton saveButton;
    private JButton openButton;
    private JButton newButton;
    private JToolBar toolBar;
    private JPanel toolBarPanel;
    private JPanel contentPanel;
    
    private BufferedImage image;
    private ArrayList<String> groupsShown = new ArrayList<String>();

    private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }

    @Override
    protected void startup() {
    	{
    		
    		// TODO: Temporal
    		try {
				ClientController.getInstance().setCurrentProject(2);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
	    	getMainFrame().setSize(923, 528);
    	}
        {
            topPanel = new JPanel();
            BorderLayout panelLayout = new BorderLayout();
            topPanel.setLayout(panelLayout);
            topPanel.setPreferredSize(new java.awt.Dimension(500, 300));
            {
                contentPanel = new JPanel();
                AnchorLayout contentPanelLayout = new AnchorLayout();
                contentPanel.setLayout(contentPanelLayout);
                topPanel.add(contentPanel, BorderLayout.CENTER);
                {
                	tabPanel = new JTabbedPane();
                	
                	contentPanel.add(tabPanel, new AnchorConstraint(1, 1000, 940, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
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
                	}
                }
                {
                	statusPanel = new JPanel();
                	AnchorLayout statusPanelLayout = new AnchorLayout();
                	statusPanel.setLayout(statusPanelLayout);
                	contentPanel.add(statusPanel, new AnchorConstraint(953, 1000, 1001, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                	statusPanel.setPreferredSize(new java.awt.Dimension(907, 21));
                	{
                		lblStatus = new JLabel();
                		statusPanel.add(lblStatus, new AnchorConstraint(0, 742, 785, 6, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
                		lblStatus.setPreferredSize(new java.awt.Dimension(160, 16));
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
                    toolBar = new JToolBar();
                    toolBarPanel.add(toolBar, BorderLayout.CENTER);
                    {
                        newButton = new JButton();
                        toolBar.add(newButton);
                        newButton.setName("newButton");
                        newButton.setFocusable(false);
                        newButton.setAction(getAppActionMap().get("newTab"));
                    }
                    {
                        openButton = new JButton();
                        toolBar.add(openButton);
                        openButton.setAction(getAppActionMap().get("open"));
                        openButton.setName("openButton");
                        openButton.setFocusable(false);
                    }
                    {
                        saveButton = new JButton();
                        toolBar.add(saveButton);
                        saveButton.setAction(getAppActionMap().get("save"));
                        saveButton.setName("saveButton");
                        saveButton.setFocusable(false);
                    }
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
	        // Configure available options
	        configureActions(operations);
	        // Configure menus
	        configureMenu(operations);
	        
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRole e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		
        // Show the main frame
        show(topPanel);
    }
    
    private void configureMenu(List<Operation> operations) {
    	groupsShown = new ArrayList<String>();
    	
    	for(Operation o: operations) {
    		// Add menu entry eith the namem of the operation group
    		if (!groupsShown.contains(o.getGroup())){
    			JMenu menu = new JMenu();
    			menu.setName("menu_"+o.getGroup());
    			menu.setText(o.getGroup());
    			groupsShown.add(o.getGroup());
    			List<String> menuItemsName = OperationsUtilities.getSubgroupsId(operations, o.getGroup());
    			// If there are subgroups, add menu items to manage each subgroup
    			if (menuItemsName.size() > 0){
	    			for (String s: menuItemsName){
	    				if (!s.equals("")){
	    					JMenuItem item = new JMenuItem();
	    					item.setName("mitem_"+s);
	    					item.setAction(getAppActionMap().get(item.getName()));
	    					item.setText("Manage "+s);
	    					menu.add(item);
	    				}
	    			}
	    			menuBar.add(menu);
    			}
    			// If there aren't subgroups, add the operation directly
    			else {
    				for (String s: OperationsUtilities.getOperationsGroupId(operations, o.getGroup())) {
	    				JMenuItem item = new JMenuItem();
						item.setName("mitem_"+s);
						item.setText(s);
						menu.add(item);
    				}
    				menuBar.add(menu);
    			} 			
    		}
    	}
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

	// This method is used to configure the actions tab with the available operations
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
			} catch (IOException e) {
				JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// Finally, load the "Logout" action.
		Operation op = new Operation("Logout", "", null);
		try {
			createComponent(op);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}    
    
    private void createComponent(Operation o) throws IOException {
    	// Load the group image of the operation
		image = GraphicsUtilities.loadCompatibleImage(o.getGroup() + ".png");
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
		GraphicsUtilities.addImageButton(button.getName(), image);
		panel.add(button);
		button.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent evt) {
				getMainFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  				
				GraphicsUtilities.decreaseImageBrightness((JButton) evt.getSource());
			}

			public void mouseEntered(MouseEvent evt) {
				getMainFrame().setCursor(new Cursor(Cursor.HAND_CURSOR));
				GraphicsUtilities.increaseImageBrightness((JButton) evt.getSource());
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
    
    /*** Actions used in menu items ***/
	@Action
    public void mitem_Proposal() {
    	JFKnowledge frameKnowledge = new JFKnowledge("Proposal");
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
    	// Create a new tab in order to store the different Knowledge views (JInternalFrame)
    	int index = tabPanel.getTabCount();
    	
    	JPanel panelKnowledge = new JPanel();
    	AnchorLayout panelKnowledgeLayout = new AnchorLayout();
    	panelKnowledge.setLayout(panelKnowledgeLayout);
		tabPanel.insertTab(ApplicationInternationalization.getString("tabKnowledge"), null, panelKnowledge, null, index);
		tabPanel.setSelectedIndex(index);
		panelKnowledge.setBounds(0, 0, 907, 415);
    	
    }
    
    @Action
    public void btn_Notifications() {
    	// Create a new tab in order to store the different Knowledge views (JInternalFrame)
    	if (!existsTab(ApplicationInternationalization.getString("tabNotification"))) {
	    	int index = tabPanel.getTabCount();
	    	
	    	JPanel panelKnowledge = new JPanel();
	    	AnchorLayout panelKnowledgeLayout = new AnchorLayout();
	    	panelKnowledge.setLayout(panelKnowledgeLayout);
			tabPanel.insertTab(ApplicationInternationalization.getString("tabKnowledge"), null, panelKnowledge, null, index);
			tabPanel.setSelectedIndex(index);
			panelKnowledge.setBounds(0, 0, 907, 415);
    	}
    	else {
    		tabPanel.setSelectedIndex(getIndexTab(ApplicationInternationalization.getString("tabNotification")));
    	}
    }
    
	@Action
    public void btn_Statistics() {
    	// Create a new tab in order to store the different Knowledge views (JInternalFrame)
    	int index = tabPanel.getTabCount();
    	
    	JPanel panelKnowledge = new JPanel();
    	AnchorLayout panelKnowledgeLayout = new AnchorLayout();
    	panelKnowledge.setLayout(panelKnowledgeLayout);
		tabPanel.insertTab(ApplicationInternationalization.getString("tabKnowledge"), null, panelKnowledge, null, index);
		tabPanel.setSelectedIndex(index);
		panelKnowledge.setBounds(0, 0, 907, 415);
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
}
