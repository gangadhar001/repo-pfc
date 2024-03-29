package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.List;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;

import model.business.knowledge.Company;
import model.business.knowledge.File;
import model.business.knowledge.Groups;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jfree.chart.ChartPanel;
import org.jfree.ui.ExtensionFileFilter;

import presentation.CBR.JDConfigProject;
import presentation.customComponents.CustomMenubar;
import presentation.customComponents.ImagePanel;
import presentation.customComponents.JPDetailsCompany;
import presentation.customComponents.JPDetailsCompanyGlassPanel;
import presentation.dataVisualization.InternalFStatistics;
import presentation.panelsActions.panelKnowledgeView;
import presentation.panelsActions.panelNotificationsView;
import presentation.panelsActions.panelPDFGeneration;
import presentation.panelsActions.panelStatisticsGeneration;
import resources.CursorUtilities;
import resources.ImagesUtilities;
import bussiness.control.ClientController;
import bussiness.control.OperationsUtilities;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

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
	private JButton btnDownloadAttached;
	private JProgressBar jProgressBar;
	private JLabel lblStatus;
	private JPanel statusBar;
	private JToolBar toolbar;
	private static JPanel mainPanel;
	private JLabel lblRole;

	private panelKnowledgeView panelKnowledge;
	private panelNotificationsView panelNotifications;
	private panelStatisticsGeneration panelStatistics;
	private panelPDFGeneration panelPDF;
	private CustomMenubar menuBar;
	
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
        
        hide(getMainView());
        
        try {
			getMainFrame().setIconImage(ImagesUtilities.loadCompatibleImage("icono.png"));
		} catch (IOException e1) { }
        
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
				getMainFrame().getContentPane().add(statusBar, new AnchorConstraint(932, 1000, 999, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				statusBar.setPreferredSize(new java.awt.Dimension(1008, 46));
				statusBar.setLayout(statusLayout);
				{
					btnDownloadAttached = new JButton();
					statusBar.add(btnDownloadAttached, new AnchorConstraint(228, 148, 728, 116, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					btnDownloadAttached.setPreferredSize(new java.awt.Dimension(32, 23));
					btnDownloadAttached.setName("btnDownloadAttached");
					btnDownloadAttached.setAction(getAppActionMap().get("DownloadFiles"));
					btnDownloadAttached.setText("");
					btnDownloadAttached.setContentAreaFilled(false);
					try {
						btnDownloadAttached.setIcon(ImagesUtilities.loadIcon("menus/download.png"));
					}
					catch (Exception e) {}
					btnDownloadAttached.setToolTipText(ApplicationInternationalization.getString("tooltipDownload"));
				}
				{
					jProgressBar = new JProgressBar();
					statusBar.add(jProgressBar, new AnchorConstraint(362, 269, 712, 108, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					jProgressBar.setPreferredSize(new java.awt.Dimension(162, 14));
					jProgressBar.setIndeterminate(true);
					jProgressBar.setVisible(false);
				}
				{
					lblStatus = new JLabel();
					lblStatus.setVerticalAlignment(SwingConstants.CENTER);
					lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
					statusBar.add(lblStatus, new AnchorConstraint(212, 450, 712, 7, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					lblStatus.setPreferredSize(new java.awt.Dimension(447, 20));
					lblStatus.setName("lblStatus");
				}
			}
			{
				toolbar = new JToolBar();
				getMainFrame().getContentPane().add(toolbar, new AnchorConstraint(0, 1000, 71, 0, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				toolbar.setPreferredSize(new java.awt.Dimension(1008, 38));
			}
			{
				mainPanel = new ImagePanel(ImagesUtilities.loadCompatibleImage("background.png"));
				mainPanel.setLayout(null);
				getMainFrame().getContentPane().add(mainPanel, new AnchorConstraint(55, 1000, 935, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				mainPanel.setPreferredSize(new java.awt.Dimension(1008, 607));				
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
				lblAction.setVisible(false);
			}
			{
				lblPort = new JLabel();
				lblPort.setVerticalAlignment(SwingConstants.CENTER);
				lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
				statusBar.add(lblPort, new AnchorConstraint(940, 7, 4, 778, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
				lblPort.setPreferredSize(new java.awt.Dimension(272, 18));
			}
			{
				lblRole = new JLabel();
				lblRole.setVerticalAlignment(SwingConstants.CENTER);
				lblRole.setHorizontalAlignment(SwingConstants.RIGHT);
				statusBar.add(lblRole, new AnchorConstraint(954, 5, 25, 773, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
				lblRole.setPreferredSize(new java.awt.Dimension(361, 21));
			}     

	        // Create menu bar
			menuBar = new CustomMenubar(this);
			getMainFrame().setJMenuBar(menuBar);
			
			lblAction.setVisible(false);			
			showStatusBar(ApplicationInternationalization.getString("programInitialized"));
			
    	} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
    }    
    
    public void show() {
    	show(getMainFrame());
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
			if (image != null) {
				button.setIcon(new ImageIcon(image));
				// Save button icon
				ImagesUtilities.addImageButton(button.getName(), image);
			}			
		} catch (Exception e) { }	  
		
    	button.setRolloverEnabled(true);  
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
		CursorUtilities.showWaitCursor(getMainFrame());
		companyDetailGlassPanel.setLocation(companyDetailGlassPanel.getLocation().x, companyDetailGlassPanel.getLocation().y + 500);
		companyDetailGlassPanel.fadeIn(c);
	}

	public void fadeOut() {		
		CursorUtilities.showDefaultCursor(getMainFrame());
		companyDetailGlassPanel.fadeOut();		
	}
		
	 // Method to add specific button for knowledge view to the toolbar
    public void createToolbarKnowledgeView() {
		toolbar.removeAll();

		// Includes operation "add", "modify", and "delete", if the user has
		// permissions
		List<String> availableOps = OperationsUtilities.getOperationsGroupId(operations, Groups.Knowledge.name());
		if (availableOps.contains(Operations.Add.name())) {
			JButton but = createToolbarButton(Operations.Add.name() + Groups.Knowledge.name());
			but.setEnabled(false);
			toolbar.add(but);
		}
		if (availableOps.contains(Operations.Modify.name())) {
			JButton but = createToolbarButton(Operations.Modify.name() + Groups.Knowledge.name());
          	but.setEnabled(false);
          	toolbar.add(but);      
		}
		if (availableOps.contains(Operations.Delete.name())) {
			JButton but = createToolbarButton(Operations.Delete.name() + Groups.Knowledge.name());
          	but.setEnabled(false);
          	toolbar.add(but);  
		}
		
		// Add toogle button for advanced view
		toolbar.addSeparator();
		JToggleButton toggle = new JToggleButton();
		toggle.setToolTipText(ApplicationInternationalization.getString("toogleAdvancedView"));
		try {
			toggle.setIcon(ImagesUtilities.loadIcon("Toolbars/Advanced.png"));
		}
		catch (Exception e) { }
		toggle.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				showAdvancedView(((JToggleButton)e.getSource()).isSelected());
				
			}
		});
		toolbar.add(toggle);
	}
    
    protected void showAdvancedView(boolean selected) { 
    	if (panelKnowledge != null) {
    		panelKnowledge.showAdvancedView(selected);
    	}
	}

	// Method to add specific button for Statistics view to the toolbar
    public void createToolbarStatisticsView() {
    	toolbar.removeAll();
    	
        // Includes operation "generate", if the user has permissions
        List<String> availableOps = OperationsUtilities.getOperationsGroupId(operations, Groups.Statistics.name());
        if (availableOps.contains(Operations.Generate.name()))
        	toolbar.add(createToolbarButton(Operations.Generate.name()+Groups.Statistics.name()));                
    }
    
    // Method to add specific button for Statistics view to the toolbar
    public void createToolbarNotificationView() {
    	toolbar.removeAll();
    	
        // Includes operations
    	List<String> availableOps = OperationsUtilities.getOperationsGroupId(operations, Groups.Notifications.name());
		if (availableOps.contains(Operations.MarkRead.name())) {
			JButton but = createToolbarButton(Operations.MarkRead.name() + Groups.Notifications.name());
          	but.setEnabled(false);
          	toolbar.add(but);      
		}
		if (availableOps.contains(Operations.MarkUnread.name())) {
			JButton but = createToolbarButton(Operations.MarkUnread.name() + Groups.Notifications.name());
          	but.setEnabled(false);
          	toolbar.add(but);      
		}
		if (availableOps.contains(Operations.Delete.name())) {
			JButton but = createToolbarButton(Operations.Delete.name() + Groups.Notifications.name());
          	but.setEnabled(false);
          	toolbar.add(but);  
		}                
    }
	
    // Method to add specific button for PDFGen view to the toolbar
    public void createToolbarPDFGenView() {
    	toolbar.removeAll();
    	
        // Includes operation "generate", if the user has permissions
        List<String> availableOps = OperationsUtilities.getOperationsGroupId(operations, Groups.PDFGeneration.name());
        if (availableOps.contains(Operations.Generate.name())) {
        	toolbar.add(createToolbarButton(Operations.Add.name()+"Section"));
        	JButton but = createToolbarButton(Operations.Delete.name()+"Section");
          	but.setEnabled(false);
          	toolbar.add(but);      
        	toolbar.addSeparator();
        	toolbar.add(createToolbarButton("CompilePDF"));
        }
    }
    
	/*** Actions for specific toolbar buttons. ***/
	@Action
	public void AddKnowledge() {
		if (panelKnowledge != null)
			panelKnowledge.operationAdd();
	}
	
	@Action
	public void ModifyKnowledge() {
		if (panelKnowledge != null)
			panelKnowledge.operationModify();
	}
	
	@Action
	public void DeleteKnowledge() {
		if (panelKnowledge != null)
			panelKnowledge.operationDelete();
	}
	
	@Action
	public void GenerateStatistics() {
		JDStatistics jds = new JDStatistics();
		jds.setModal(true);
		jds.setLocationRelativeTo(getMainFrame());
		jds.setVisible(true);
		// Take the generated chart and show it in the view of statistics
        ChartPanel chartPanel = jds.getChartPanel();
        if (chartPanel != null) {           
            InternalFStatistics internalFrameStatistic = new InternalFStatistics();
            internalFrameStatistic.addChartPanel(jds.getProject(), jds.getType(), jds.showProjectName(), chartPanel);
            panelStatistics.addStatistic(internalFrameStatistic);            
        }
	}	
	
	@Action
	public void AddSection() {
		if (panelPDF != null)
			panelPDF.addSection();
	}
	
	@Action
	public void DeleteSection() {
		if (panelPDF != null)
			panelPDF.deleteSection();
	}
	
	@Action
	public void CompilePDF() {
		if (panelPDF != null)
			panelPDF.compile();
	}
	
	@Action
	public void MarkReadNotifications() {
		if (panelNotifications != null)
			panelNotifications.markRead();
	}
	
	@Action
	public void MarkUnreadNotifications() {
		if (panelNotifications != null)
			panelNotifications.markUnread();
	}
	
	@Action
	public void DeleteNotifications() {
		if (panelNotifications != null)
			panelNotifications.removeRow();
	}
	
	@Action
	public void DownloadFiles() {
		if (panelKnowledge != null) {
			Set<File> files = panelKnowledge.getAttachedFiles();
			// Select folder to save all files
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setDialogTitle(ApplicationInternationalization.getString("chooseFolder"));
			//fileChooser.setAcceptAllFileFilterUsed(false);
			int result = fileChooser.showOpenDialog(getMainFrame());
			if (result == JFileChooser.APPROVE_OPTION) {
				CursorUtilities.showWaitCursor(getMainFrame());
				// Save files
				for(File f: files){
					FileOutputStream outFile;
					try {
						outFile = new FileOutputStream(fileChooser.getSelectedFile().getAbsolutePath() + System.getProperty("file.separator") + f.getFileName());
						outFile.write(f.getContent());
						outFile.flush();
						outFile.close();
						CursorUtilities.showDefaultCursor(getMainFrame());
						JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("savedAttachedFiles"), ApplicationInternationalization.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
						CursorUtilities.showDefaultCursor(getMainFrame());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
						CursorUtilities.showDefaultCursor(getMainFrame());
					}					
				}
			}
		}
	}
	
	/*** Methods used to update the views with changes made in other client.
	 * Only refresh the actual and visible view  ***/
	public void notifyKnowledgeEdited(Knowledge newK, Knowledge oldK) {
		if (panelKnowledge != null && panelKnowledge.isVisible()) {
			panelKnowledge.notifyKnowledgeEdited(newK, oldK);
		}
	}

	public void notifyKnowledgeRemoved(Knowledge k) {
		if (panelKnowledge != null && panelKnowledge.isVisible()) {
			panelKnowledge.notifyKnowledgeRemoved(k);
		}				
	}

	public void notifyKnowledgeAdded(Knowledge k, Knowledge parentK) {
		if (panelKnowledge != null && panelKnowledge.isVisible()) {
			panelKnowledge.notifyKnowledgeAdded(k, parentK);
		}			
	}

	// When closes session, return to the login frame
	public void forceCloseSession() {
		JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("message_ForceCloseSession"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		closeSessionConfirm();
		menuBar.disableMenuItemsLogin();
		
	}

	// When the server goes offline, then return to the login frame
	public void approachlessServer() {
		JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("message_approachlessServer"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		ClientController.getInstance().closeSessionApproachlessServer();
		showStatusBar(ApplicationInternationalization.getString("NotUserLogged"));
		lblRole.setText("");
		lblPort.setText("");
		lblAction.setVisible(false);
		menuBar.disableMenuItemsLogin();		
	}	

	public void closeSessionConfirm() {
		try {
			showStatusBar(ApplicationInternationalization.getString("NotUserLogged"));
			lblRole.setText("");
			lblPort.setText("");
			lblAction.setVisible(false);
			clearViews();
			mainPanel.setVisible(true);
			// Close session
			ClientController.getInstance().signout();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		// Ignore
		} catch (NotLoggedException e) {
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
		} catch (ClassCastException e) {
			// Ignore
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void showKnowledgeView() {
		if (!contains(getMainFrame().getContentPane().getComponents(), panelKnowledge)) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					startProgressBar();
					Thread performer = new Thread(new Runnable() {
						public void run() {
							createKnowledgeView();
						}					
					}, "Performer");
					performer.start();
				}
			});
		}
	}
	
	public void showNotificationsView() {
		if (!contains(getMainFrame().getContentPane().getComponents(), panelNotifications)) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					startProgressBar();
					Thread performer = new Thread(new Runnable() {
						public void run() {
							createNotificationsView();
						}					
					}, "Performer");
					performer.start();
				}
			});
		}
	}

	public void showStatisticsView() {
		if (!contains(getMainFrame().getContentPane().getComponents(), panelStatistics)) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					startProgressBar();
					Thread performer = new Thread(new Runnable() {
						public void run() {
							createStatisticsView();
						}
	
						
					}, "Performer");
					performer.start();
				}
			});
		}
	}
	
	private void createStatisticsView() {
		clearViews();
		if (panelStatistics == null)
			panelStatistics = new panelStatisticsGeneration();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { }		
		
		getMainFrame().getContentPane().add(panelStatistics, new AnchorConstraint(61, 1000, 925, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		panelStatistics.setPreferredSize(new java.awt.Dimension(1008, 596));
		mainPanel.setVisible(false);
		
		createToolbarStatisticsView();
		
		stopProgressBar();
	}
	
	private void createKnowledgeView() {		
		// Remove other views, if any
		clearViews();
		panelKnowledge = new panelKnowledgeView(this);		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { }
		
		getMainFrame().getContentPane().add(panelKnowledge, new AnchorConstraint(58, 1000, 929, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		panelKnowledge.setPreferredSize(new java.awt.Dimension(1008, 601));
		mainPanel.setVisible(false);
		createToolbarKnowledgeView();
		//panelKnowledge.activateToolbarButtons(false);
		
		stopProgressBar();
		
		if (panelKnowledge.getTopicWrapper().getTopics().size() == 0)
			JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("emptyTopics"), ApplicationInternationalization.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void createNotificationsView() {		
		// Remove other views, if any
		clearViews();
		
		panelNotifications = new panelNotificationsView(this);		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { }
		
		getMainFrame().getContentPane().add(panelNotifications, new AnchorConstraint(58, 1000, 941, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		panelNotifications.setPreferredSize(new java.awt.Dimension(1008, 609));		
		mainPanel.setVisible(false);
		createToolbarNotificationView();
		
		stopProgressBar();
	}
	
	private void createPdfView() 
	{
		clearViews();
		if (panelPDF == null)
			panelPDF = new panelPDFGeneration(this);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { }
		
		getMainFrame().getContentPane().add(panelPDF, new AnchorConstraint(60, 1000, 935, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		panelPDF.setPreferredSize(new java.awt.Dimension(1008, 609));
		mainPanel.setVisible(false);
		
		createToolbarPDFGenView();
		
		stopProgressBar();
	}
		
	private void clearViews() {
		Component component = null;
		for(Component c: getMainFrame().getContentPane().getComponents()) {
			// Only exists one at same time
			if (c instanceof panelNotificationsView || c instanceof panelKnowledgeView || c instanceof panelStatisticsGeneration || c instanceof panelPDFGeneration)
				component = c;
		}
		if (component != null)
			getMainFrame().getContentPane().remove(component);
	}

	public void showPDFView() {		
		if (!contains(getMainFrame().getContentPane().getComponents(), panelPDF)) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					startProgressBar();
					Thread performer = new Thread(new Runnable() {
						public void run() {
							createPdfView();
						}					
					}, "Performer");
					performer.start();
				}
			});	
		}
	}

	// Method to enable or disable a toolbar button
	public void enableToolbarButton(String name, boolean enabled) {
		boolean found = false;
		Component[] components = toolbar.getComponents();
		for(int i = 0; i< components.length && !found; i++) {
			if (components[i].getName().equals(name)) {
				found = true;
				((JButton)components[i]).setEnabled(enabled);
			}
		}		
	}

	public void manageKnowledgeFromMenu() {
		JDKnowledge frameKnowledge = new JDKnowledge(this);
		frameKnowledge.setLocationRelativeTo(getMainFrame());
		frameKnowledge.setModal(true);
		frameKnowledge.setVisible(true);			
	}
	
	private void stopProgressBar() {
		CursorUtilities.showDefaultCursor(getMainFrame());
		jProgressBar.setVisible(false);
		lblStatus.setText(ApplicationInternationalization.getString("viewCreated"));
	}
	
	private void startProgressBar() {
		btnDownloadAttached.setVisible(false);
		CursorUtilities.showWaitCursor(getMainFrame());
		jProgressBar.setVisible(true);
		lblStatus.setText(ApplicationInternationalization.getString("creatingView"));
		lblStatus.setVisible(true);
	}

	public void manageProjectFromMenu() {
		JDManageProject dialogProject = new JDManageProject(getMainFrame());
		dialogProject.setLocationRelativeTo(getMainFrame());
		dialogProject.setModal(true);
		dialogProject.setVisible(true);		
	}

	public void manageCBRFromMenu() {
		JDConfigProject jdc = new JDConfigProject(this);
		jdc.setModal(true);
		jdc.setLocationRelativeTo(null);
		jdc.setVisible(true);
		
	}

	public void notifyNotificationAvailable(Notification n) {
		if (panelNotifications != null && panelNotifications.isVisible()) {
			panelNotifications.notifyNotificationAvailable(n);
		}		
	}
	
	public JButton getBtnDownloadAttached() {
		return btnDownloadAttached;
	}

	public void manageExportInformation() {
		JDExport export = new JDExport(this);
		export.setModal(true);
		export.setLocationRelativeTo(null);
		export.setVisible(true);
	}

	public void showStatusBar(String message) {
		lblStatus.setText(message);
		lblStatus.setVisible(true);		
	}

	public void hideStatusBar() {
		lblStatus.setText("");
		lblStatus.setVisible(false);	
		btnDownloadAttached.setVisible(false);
	}
	
	private boolean contains(Component[] components, Component panel) {
		boolean found = false;
		if (panel != null){
			for(int i=0; i<components.length && !found; i++) {
				if (components[i].equals(panel))
					found = true;
			}
		}
		return found;
	}
	
	public void export(Project p) {
		byte[] baos;
		java.io.File f;
		
		try {
			JFileChooser fc = new JFileChooser();
			ExtensionFileFilter filter = new ExtensionFileFilter("XML File", "XML");
			fc.setFileFilter(filter);
			fc.showSaveDialog(getMainFrame());
			if ((f = fc.getSelectedFile()) != null)  {
				CursorUtilities.showWaitCursor(getMainFrame());
				baos = ClientController.getInstance().exportInformation(p);		
				if (baos != null) {
					String path = f.getAbsolutePath();
					if (!path.endsWith("."))
						path += ".xml";
			        FileOutputStream fos = new FileOutputStream(path);
			        fos.write(baos);
			        CursorUtilities.showDefaultCursor(getMainFrame());
			        //showStatusBar(ApplicationInternationalization.getString("exportStatus"));				        
			        JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("exportSuccessfully"), ApplicationInternationalization.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("informationExport"), ApplicationInternationalization.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (JAXBException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public List<Operation> getOperations() throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		operations = ClientController.getInstance().getAvailableOperations();
		return operations;
	}

	public void showLoginLabels() {
		lblAction.setVisible(false);
		showStatusBar(ApplicationInternationalization.getString("UserLogued"));
		lblPort.setText(ApplicationInternationalization.getString("lblStatusClient") + " " + ClientController.getInstance().getClientIP() + ":" + String.valueOf(ClientController.getInstance().getPort()));
		lblRole.setText(ApplicationInternationalization.getString("lblStatusLogged") + " " + ClientController.getInstance().getUserLogin() + "@" + ClientController.getInstance().getRole() +
				".   " + ApplicationInternationalization.getString("activeProject") + ": " + ClientController.getInstance().getCurrentProjectName());		
	}

	public panelKnowledgeView getPanelKnowledge() {
        return panelKnowledge;
	}
	
	public static void clearContent() {
		for(Component c: Application.getInstance(JFMain.class).getMainFrame().getContentPane().getComponents()) {
			Application.getInstance(JFMain.class).getMainFrame().getContentPane().remove(c);
		}
		Application.getInstance(JFMain.class).getMainFrame().getContentPane().add(mainPanel);
		mainPanel.setVisible(true);
		Application.getInstance(JFMain.class).showLoginLabels();
	}
	
}
