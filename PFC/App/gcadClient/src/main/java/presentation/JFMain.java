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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.business.knowledge.Company;
import model.business.knowledge.Groups;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jfree.chart.ChartPanel;

import presentation.CBR.JDConfigSimil;
import presentation.CBR.JDRetrievalCases;
import presentation.customComponents.CustomMenubar;
import presentation.customComponents.CustomToolBar;
import presentation.customComponents.ImagePanel;
import presentation.customComponents.JPDetailsCompany;
import presentation.customComponents.JPDetailsCompanyGlassPanel;
import presentation.dataVisualization.InternalFStatistics;
import presentation.panelsActions.panelKnowledgeView;
import presentation.panelsActions.panelNotificationsView;
import presentation.panelsActions.panelStatisticsView;
import resources.ImagesUtilities;
import bussiness.control.ClientController;
import bussiness.control.OperationsUtilities;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
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

    private BufferedImage image;
    
	private JPDetailsCompany panelDetailsCompany;
	private JPanel jPanel1;
	private JPDetailsCompanyGlassPanel companyDetailGlassPanel;
	
	// List of operations allowed to an user, depends on its role
	private List<Operation> operations;

	private JLabel lblAction;
	private JLabel lblPort;
	private JPanel viewPanel;
	private JPanel statusBar;
	private JToolBar toolbar;
	private JPanel mainPanel;
	private JLabel lblRole;
	
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 728;	

    private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }

    @Override
    protected void startup() {
    	
    	// Listener to confirm exit
        addExitListener(new ExitListener() {
             public boolean canExit(EventObject event) {
                 return JOptionPane.showConfirmDialog(getMainFrame(),
                         ApplicationInternationalization.getString("Dialog_CloseFrame_Message")) == JOptionPane.YES_OPTION;
             }
             public void willExit(EventObject event) {
            	 quit();
             }
         });
    	
    	try {			
			getMainFrame().setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
			getMainFrame().setMinimumSize(new java.awt.Dimension(WIDTH, HEIGHT));
			getMainFrame().setSize(WIDTH, HEIGHT);
			{	
				// Set glass panel
				panelDetailsCompany = new JPDetailsCompany(this);				
				companyDetailGlassPanel = new JPDetailsCompanyGlassPanel(panelDetailsCompany);
				getMainFrame().setGlassPane(companyDetailGlassPanel);
			}

			// Set Title		  
			getMainFrame().setTitle(ApplicationInternationalization.getString("titleMain"));
			AnchorLayout mainFrameLayout = new AnchorLayout();
			getMainFrame().getContentPane().setLayout(mainFrameLayout);
			{
				AnchorLayout statusLayout = new AnchorLayout();
				statusBar = new JPanel();
				getMainFrame().getContentPane().add(statusBar, new AnchorConstraint(935, 1000, 999, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				statusBar.setPreferredSize(new java.awt.Dimension(1008, 44));
				statusBar.setLayout(statusLayout);
			}
			{
				toolbar = new JToolBar();
				getMainFrame().getContentPane().add(toolbar, new AnchorConstraint(0, 1000, 71, 0, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				toolbar.setPreferredSize(new java.awt.Dimension(1008, 31));
			}
			{
				mainPanel = new ImagePanel(ImagesUtilities.loadCompatibleImage("background.jpg"));
				AnchorLayout mainPanelLayout = new AnchorLayout();
				mainPanel.setLayout(mainPanelLayout);
				getMainFrame().getContentPane().add(mainPanel, new AnchorConstraint(37, 1000, 925, 0, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				mainPanel.setPreferredSize(new java.awt.Dimension(1008, 601));
				{
					viewPanel = new JPanel();
					mainPanel.add(viewPanel, new AnchorConstraint(20, 985, 980, 12, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					viewPanel.setPreferredSize(new java.awt.Dimension(981, 577));
					viewPanel.setName("viewPanel");
				}
			}
			{
				lblAction = new JLabel();
				statusBar.add(
						lblAction,
						new AnchorConstraint(942, 205, 14, 21,
								AnchorConstraint.ANCHOR_NONE,
								AnchorConstraint.ANCHOR_NONE,
								AnchorConstraint.ANCHOR_ABS,
								AnchorConstraint.ANCHOR_ABS));
				lblAction.setPreferredSize(new java.awt.Dimension(203, 20));
			}
			{
				lblPort = new JLabel();
				statusBar.add(lblPort, new AnchorConstraint(940, 7, 8, 778, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
				lblPort.setPreferredSize(new java.awt.Dimension(238, 12));
			}
			{
				lblRole = new JLabel();
				statusBar.add(lblRole, new AnchorConstraint(954, 7, 26, 773, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
				lblRole.setPreferredSize(new java.awt.Dimension(238, 10));
			}     


	        // Get available operations for the logged user
			operations = ClientController.getInstance().getAvailableOperations();
			
	        // Create menu bar
			CustomMenubar menuBar = new CustomMenubar(this, operations);
			getMainFrame().setJMenuBar(menuBar);
			
			lblPort.setText(ApplicationInternationalization.getString("lblStatusClient") + " " + ClientController.getInstance().getClientIP() + ":" + String.valueOf(ClientController.getInstance().getPort()));
			lblRole.setText(ApplicationInternationalization.getString("lblStatusLogged") + " " + ClientController.getInstance().getUserLogin() + "@" + ClientController.getInstance().getRole());
			lblAction.setVisible(false);
			
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
    
    private JButton createToolbarButton(String id) throws MalformedURLException, IOException {
    	JButton button = new JButton();
    	button.setAction(getAppActionMap().get(id));
     	button.setIcon(ImagesUtilities.loadIcon("Toolbars/" + id + ".png"));
    	return button;
    }
    
//    private boolean existsTab(String title) {
//    	boolean found = false;
//    	for(int i=0; i<tabPanel.getTabCount() && !found; i++) {
//    		if (tabPanel.getTitleAt(i).equals(title))
//    			found=true;
//    	}
//    	return found;
//    }
//    
//    private int getIndexTab(String title) {
//    	int result = -1;
//    	for(int i=0; i<tabPanel.getTabCount() && result==-1; i++) {
//    		if (tabPanel.getTitleAt(i).equals(title))
//    			result=i;
//    	}
//    	return result;
//	}
    
    // Methods used to show and hide the glass panel, with "fade" effect
    public void fadeIn(Company c) {
		companyDetailGlassPanel.fadeIn(c);
	}

	public void fadeOut() {		
		companyDetailGlassPanel.fadeOut();		
	}
	
//	private void tabPanelStateChanged(ChangeEvent evt) {
//		try {
//			int index = pane.getSelectedIndex();
//			createCommonToolbar();
//			if (tabPanel.getTitleAt(index).equals(ApplicationInternationalization.getString("tabStatistics")))
//				createToolbarStatisticsView();
//			else if (tabPanel.getTitleAt(index).equals(ApplicationInternationalization.getString("tabKnowledge")))
//				createToolbarKnowledgeView();
//		} catch (MalformedURLException e) {
//			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
//		}
//	}
	
	 // Method to add specific button for knowledge view to the toolbar
    private void createToolbarKnowledgeView() throws MalformedURLException, IOException {
        toolbar.removeAll();
      
                // Includes operation "add", "modify", and "delete", if the user has permissions
                List<String> availableOps = OperationsUtilities.getOperationsGroupId(operations, Groups.Knowledge.name());
                if (availableOps.contains(Operations.Add.name()))
                	toolbar.add(createToolbarButton(Operations.Add.name()+Groups.Knowledge.name()));
                if (availableOps.contains(Operations.Modify.name()))
                	toolbar.add(createToolbarButton(Operations.Modify.name()+Groups.Knowledge.name()));
                if (availableOps.contains(Operations.Delete.name()))
                	toolbar.add(createToolbarButton(Operations.Delete.name()+Groups.Knowledge.name()));
                
        }
    
    // Method to add specific button for Statistics view to the toolbar
    private void createToolbarStatisticsView() throws MalformedURLException, IOException {
    	toolbar.removeAll();
               
                // Includes operation "generate", if the user has permissions
                List<String> availableOps = OperationsUtilities.getOperationsGroupId(operations, Groups.Statistics.name());
                if (availableOps.contains(Operations.Generate.name()))
                	toolbar.add(createToolbarButton(Operations.Generate.name()+Groups.Statistics.name()));
                
        }

    
    /*** Actions used in buttons of the main tab and toolbar, used to show different views  ***/
    @Action
    public void Knowledge() throws MalformedURLException, IOException {
//    	// Create a new tab in order to store the Knowledge view
//    	int index = tabPanel.getTabCount();
//
//    	else {
//    		tabPanel.setSelectedIndex(getIndexTab(ApplicationInternationalization.getString("tabKnowledge")));
//    	}
    }
    
	@Action
    public void Notifications() {
//    	int index = tabPanel.getTabCount();
//    	
//    	// Create a new tab in order to show the Notification view 
//    	else {
//    		tabPanel.setSelectedIndex(getIndexTab(ApplicationInternationalization.getString("tabNotification")));
//    	}
    }
    
	@Action
    public void Statistics() throws MalformedURLException, IOException {
//    	// Create a new tab in order to store the different Statistics frames (JInternalFrame)
//    	int index = tabPanel.getTabCount();
//    	
//    	else {
//    		tabPanel.setSelectedIndex(getIndexTab(ApplicationInternationalization.getString("tabStatistics")));
//    	}
    }
	
	/*** Actions for specific toolbar buttons. This actions are used when an specific tab is shown ***/
	@Action
	public void AddKnowledge() {
//		if (panelKnowledge != null)
//			panelKnowledge.operationAdd();
	}
	
	@Action
	public void DeleteKnowledge() {
//		if (panelKnowledge != null)
//			panelKnowledge.operationDelete();
	}
	
	@Action
	public void GenerateStatistics() {
		
	}	
	
	/*** Methods used to update the views with changes made in other client.
	 * Only refresh the actual and visible view  ***/
	public void notifyKnowledgeAdded(Knowledge k) {
//		int index = tabPanel.getSelectedIndex();
//		if(tabPanel.getTitleAt(index).equals(ApplicationInternationalization.getString("tabKnowledge")))
//			panelKnowledge.notifyKnowledgeAdded(k);		
	}

	public void notifyKnowledgeEdited(Knowledge newK, Knowledge oldK) {
//		int index = tabPanel.getSelectedIndex();
//		if(tabPanel.getTitleAt(index).equals(ApplicationInternationalization.getString("tabKnowledge")))
//			panelKnowledge.notifyKnowledgeEdited(newK, oldK);				
	}

	public void notifyKnowledgeRemoved(Knowledge k) {
//		int index = tabPanel.getSelectedIndex();
//		if(tabPanel.getTitleAt(index).equals(ApplicationInternationalization.getString("tabKnowledge")))
//			panelKnowledge.notifyKnowledgeRemoved(k);				
	}

	/*** Methods used to update the views with local changes ***/
	public void notifyKnowledgeAdded(Knowledge k, Knowledge parentK) {
//		int index = tabPanel.getSelectedIndex();
//		if(tabPanel.getTitleAt(index).equals(ApplicationInternationalization.getString("tabKnowledge")))
//			panelKnowledge.notifyKnowledgeAdded(k, parentK);	
		
	}

	// When closes session, return to the login frame
	public void forceCloseSession() {
		JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("message_ForceCloseSession"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		ClientController.getInstance().closeMainFrame();
		
	}

	// When the server goes offline, then return to the login frame
	public void approachlessServer() {
		JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("message_approachlessServer"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		ClientController.getInstance().closeMainFrame();		
	}	

	private void closeSessionConfirm() {
		try {
			lblAction.setVisible(false);
			lblRole.setText(ApplicationInternationalization.getString("lblStatusClose"));
			lblPort.setText("");
			// Close session
			ClientController.getInstance().signout();
			ClientController.getInstance().closeMainFrame();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void quit() {
		try {
			if (ClientController.getInstance().isLogged()) {
				// Close session
	       	 	closeSessionConfirm();
			}
	       	ClientController.getInstance().closeController();			
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotBoundException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (ClassCastException e) {
			// Ignore
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

}
