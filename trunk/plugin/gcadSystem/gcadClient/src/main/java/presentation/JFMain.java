package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
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

    private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }

    @Override
    protected void startup() {
    	{
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
        {
            fileMenu = new JMenu();
            menuBar.add(fileMenu);
            fileMenu.setName("fileMenu");
        {
                jMenuItem1 = new JMenuItem();
                fileMenu.add(jMenuItem1);
            }
            {
                jMenuItem2 = new JMenuItem();
                fileMenu.add(jMenuItem2);
                jMenuItem2.setAction(getAppActionMap().get("open"));
            }
            {
                jMenuItem3 = new JMenuItem();
                fileMenu.add(jMenuItem3);
                jMenuItem3.setAction(getAppActionMap().get("save"));
            }
        }
        {
            editMenu = new JMenu();
            menuBar.add(editMenu);
            editMenu.setName("editMenu");
        {
                jMenuItem4 = new JMenuItem();
                editMenu.add(jMenuItem4);
                jMenuItem4.setAction(getAppActionMap().get("copy"));
            }
            {
                jMenuItem5 = new JMenuItem();
                editMenu.add(jMenuItem5);
                jMenuItem5.setAction(getAppActionMap().get("cut"));
            }
            {
                jMenuItem6 = new JMenuItem();
                editMenu.add(jMenuItem6);
                jMenuItem6.setAction(getAppActionMap().get("paste"));
            }
            {
                jMenuItem7 = new JMenuItem();
                editMenu.add(jMenuItem7);
                jMenuItem7.setAction(getAppActionMap().get("delete"));
            }
        }
        {
        	menuOption = new JMenu();
        	menuBar.add(menuOption);
        	menuOption.setName("menuOption");
        }
        {
        	menuHelp = new JMenu();
        	menuBar.add(menuHelp);
        	menuHelp.setName("menuHelp");
        	{
        		menuItemAbout = new JMenuItem();
        		menuHelp.add(menuItemAbout);
        		menuItemAbout.setName("menuItemAbout");
        	}
        }
        
        getMainFrame().setJMenuBar(menuBar);
        
        // Get available operations for the logged user
        try {
			ArrayList<Operation> operations = ClientController.getInstance().getAvailableOperations();
	        // Configure available options
	        configureActions(operations);
	        
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRole e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		
        // Show the main frame
        show(topPanel);
    }
    
    // This method is used to configure the actions tab with the available operations
    private void configureActions(ArrayList<Operation> operations) {
//		int nOperations = operations.size() + 1;
//		GridLayout layout = new GridLayout();
//		layout.setColumns(3);
//		if (nOperations % 3 == 0)
//			layout.setRows(nOperations/3);
//		else
//			layout.setRows(nOperations/3 + 1);
//		
//		layout.setHgap(10);
//		layout.setHgap(10);
//		
//		panelActions.setLayout(layout);
//		
//		for (String s : operations.keySet()) {
//			try {
//				image = GraphicsUtilities.loadCompatibleImage(getClass()
//						.getResource(GraphicsUtilities.IMAGES_PATH + s + ".png"));
//
//				JPanel panel = new JPanel();
//
//				JButton button = new JButton();
//				button.setContentAreaFilled(false);
//				button.setFocusPainted(false);
//				button.setBorderPainted(true);
//				
//				button.setIcon(new ImageIcon(image));
//				panel.add(button);
//				button.addMouseListener(new MouseAdapter() {
//					public void mouseExited(MouseEvent evt) {
//						buttonMouseExited(evt);
//						decreaseImageBrightness((JButton) evt.getSource(),
//								image);
//					}
//
//					public void mouseEntered(MouseEvent evt) {
//						buttonMouseEntered(evt);
//						increaseImageBrightness((JButton) evt.getSource(),
//								image);
//					}
//				});
//				
//				JLabel label = new JLabel();
//				label.setText("Show " + s + " view");
//				panel.add(label);
//				panelActions.add(panel);
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(getMainFrame(),
//						e.getLocalizedMessage(),
//						ApplicationInternationalization.getString("Error"),
//						JOptionPane.ERROR_MESSAGE);
//			}
//		}
//		
//		try {
//			image = GraphicsUtilities.loadCompatibleImage(getClass()
//					.getResource(GraphicsUtilities.IMAGES_PATH + "logout.png"));
//
//			JPanel panel = new JPanel();
//
//			JButton button = new JButton();
//			button.setContentAreaFilled(false);
//			button.setFocusPainted(false);
//			button.setBorderPainted(true);
//
//			button.setIcon(new ImageIcon(image));
//			panel.add(button);
//			button.addMouseListener(new MouseAdapter() {
//				public void mouseExited(MouseEvent evt) {
//					buttonMouseExited(evt);
//					decreaseImageBrightness((JButton) evt.getSource(), image);
//				}
//
//				public void mouseEntered(MouseEvent evt) {
//					buttonMouseEntered(evt);
//					increaseImageBrightness((JButton) evt.getSource(), image);
//				}
//			});
//
//			JLabel label = new JLabel();
//			label.setText("Logout");
//			panel.add(label);
//			panelActions.add(panel);
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(getMainFrame(),
//					e.getLocalizedMessage(),
//					ApplicationInternationalization.getString("Error"),
//					JOptionPane.ERROR_MESSAGE);
//		}
	}    
    
    public static void increaseImageBrightness(JButton c, BufferedImage image) {
        float[] factors = new float[] {
            1.15f, 1.15f, 1.15f, 1.15f
        };
        float[] offsets = new float[] {
            0.0f, 0.0f, 0.0f, 0.0f
        };
        RescaleOp op = new RescaleOp(factors, offsets, null);
        BufferedImage brighter = op.filter(image, null);
        c.setIcon(new ImageIcon(brighter));
    }

    public static void decreaseImageBrightness(JButton c, BufferedImage image) {
        c.setIcon(new ImageIcon(image));
    }
    
	@Action 
    public void newTab (){
    	int index = tabPanel.getTabCount();
    }
	
	private void buttonMouseEntered(MouseEvent evt) {
		getMainFrame().setCursor(new Cursor(Cursor.HAND_CURSOR));  
	}
	
	private void buttonMouseExited(MouseEvent evt) {
		getMainFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  
	}

}
