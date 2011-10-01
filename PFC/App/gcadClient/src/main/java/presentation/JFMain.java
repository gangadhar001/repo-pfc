package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import model.business.knowledge.Company;
import model.business.knowledge.Groups;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import presentation.customComponents.CustomMenubar;
import presentation.customComponents.ImagePanel;
import presentation.customComponents.JPDetailsCompany;
import presentation.customComponents.JPDetailsCompanyGlassPanel;
import presentation.panelsActions.panelKnowledgeView;
import presentation.panelsActions.panelNotificationsView;
import presentation.panelsActions.panelPDFGeneration;
import presentation.panelsActions.panelStatisticsGeneration;
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
    
	private JPDetailsCompany panelDetailsCompany;
	private JPDetailsCompanyGlassPanel companyDetailGlassPanel;
	
	// List of operations allowed to an user, depends on its role
	private List<Operation> operations;

	private JLabel lblAction;
	private JLabel lblPort;
	private JPanel statusBar;
	private JToolBar toolbar;
	private JPanel mainPanel;
	private JLabel lblRole;

	private panelKnowledgeView panelKnowledge;
	private panelNotificationsView panelNotifications;
	private panelStatisticsGeneration panelStatistics;
	private panelPDFGeneration panelPDF;
	
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
				getMainFrame().getContentPane().add(statusBar, new AnchorConstraint(942, 1000, 999, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				statusBar.setPreferredSize(new java.awt.Dimension(1008, 39));
				statusBar.setLayout(statusLayout);
			}
			{
				toolbar = new JToolBar();
				getMainFrame().getContentPane().add(toolbar, new AnchorConstraint(0, 1000, 71, 0, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				toolbar.setPreferredSize(new java.awt.Dimension(1008, 31));
			}
			{
				mainPanel = new ImagePanel(ImagesUtilities.loadCompatibleImage("background.jpg"));
				mainPanel.setLayout(null);
				getMainFrame().getContentPane().add(mainPanel, new AnchorConstraint(45, 1000, 936, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				mainPanel.setPreferredSize(new java.awt.Dimension(1008, 615));				
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
				statusBar.add(lblPort, new AnchorConstraint(940, 7, 4, 778, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
				lblPort.setPreferredSize(new java.awt.Dimension(238, 10));
			}
			{
				lblRole = new JLabel();
				statusBar.add(lblRole, new AnchorConstraint(954, 7, 18, 773, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
				lblRole.setPreferredSize(new java.awt.Dimension(238, 18));
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
    
    private JButton createToolbarButton(String id) {
    	JButton button = new JButton();
    	button.setBorder(BorderFactory.createEmptyBorder(5,10,4,10));
    	button.setFocusPainted(false);
    	button.setHorizontalTextPosition(SwingConstants.CENTER);
    	button.setAction(getAppActionMap().get(id));
    	button.setText("");
    	button.setVerticalTextPosition(SwingConstants.BOTTOM);
    	button.setRequestFocusEnabled(false);
    	button.setName(id);
    	button.setToolTipText(ApplicationInternationalization.getString("Tooltip_" + id));
    	BufferedImage image = null;
    	try {
			image = ImagesUtilities.loadCompatibleImage("Toolbars/" + id + ".png");
			button.setIcon(new ImageIcon(image));
		} catch (Exception e) { }	  
    	button.setRolloverEnabled(true);
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
        
    	return button;
    }
        
    // Methods used to show and hide the glass panel, with "fade" effect
    public void fadeIn(Company c) {
		companyDetailGlassPanel.fadeIn(c);
	}

	public void fadeOut() {		
		companyDetailGlassPanel.fadeOut();		
	}
		
	 // Method to add specific button for knowledge view to the toolbar
    public void createToolbarKnowledgeView() {
		toolbar.removeAll();

		// Includes operation "add", "modify", and "delete", if the user has
		// permissions
		List<String> availableOps = OperationsUtilities.getOperationsGroupId(operations, Groups.Knowledge.name());
		if (availableOps.contains(Operations.Add.name()))
			toolbar.add(createToolbarButton(Operations.Add.name() + Groups.Knowledge.name()));
		if (availableOps.contains(Operations.Modify.name()))
			toolbar.add(createToolbarButton(Operations.Modify.name() + Groups.Knowledge.name()));
		if (availableOps.contains(Operations.Delete.name()))
			toolbar.add(createToolbarButton(Operations.Delete.name() + Groups.Knowledge.name()));

	}
    
    // Method to add specific button for Statistics view to the toolbar
    public void createToolbarStatisticsView() {
    	toolbar.removeAll();
    	
        // Includes operation "generate", if the user has permissions
        List<String> availableOps = OperationsUtilities.getOperationsGroupId(operations, Groups.Statistics.name());
        if (availableOps.contains(Operations.Generate.name()))
        	toolbar.add(createToolbarButton(Operations.Add.name()+Groups.Statistics.name()));
                
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

	public void showKnowledgeView() {
		mainPanel.setVisible(false);
		// Remove other views, if any
		clearViews();
		createToolbarKnowledgeView();
		panelKnowledge = new panelKnowledgeView(this);
	
		getMainFrame().getContentPane().add(panelKnowledge, new AnchorConstraint(45, 1000, 942, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		panelKnowledge.setPreferredSize(new java.awt.Dimension(1008, 619));			
	}
	
	public void showNotificationsView() {
		mainPanel.setVisible(false);
		clearViews();
		//createToolbarNotificationsView();
		panelNotifications = new panelNotificationsView();
		
		getMainFrame().getContentPane().add(panelNotifications, new AnchorConstraint(45, 1000, 925, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		panelNotifications.setPreferredSize(new java.awt.Dimension(1008, 607));	
	}

	public void showStatisticsView() {
		mainPanel.setVisible(false);
		clearViews();
		createToolbarStatisticsView();
		panelStatistics = new panelStatisticsGeneration();
		
		getMainFrame().getContentPane().add(panelStatistics, new AnchorConstraint(45, 1000, 925, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		panelStatistics.setPreferredSize(new java.awt.Dimension(1008, 607));
	}
	
	private void clearViews() {
		Component component = null;
		for(Component c: getMainFrame().getContentPane().getComponents()) {
			// only exists one at same time
			if (c instanceof panelNotificationsView || c instanceof panelKnowledgeView || c instanceof panelStatisticsGeneration)
				component = c;
		}
		if (component != null)
			getMainFrame().getContentPane().remove(component);
	}

	public void showPDFView() {
		mainPanel.setVisible(false);
		clearViews();
//		createToolbarStatisticsView();
		panelPDF = new panelPDFGeneration();
		
		getMainFrame().getContentPane().add(panelPDF, new AnchorConstraint(45, 1000, 925, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		panelPDF.setPreferredSize(new java.awt.Dimension(1008, 607));
		
	}

}
