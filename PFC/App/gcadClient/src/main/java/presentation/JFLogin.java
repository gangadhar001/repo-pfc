package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.SingleFrameApplication;

import presentation.customComponents.panelChooseProject;

import resources.IPValidator;
import resources.ImagesUtilities;
import resources.InfiniteProgressPanel;
import resources.NotEmptyValidator;
import resources.PortValidator;
import resources.UserNameValidator;
import bussiness.control.ClientController;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import communication.CommunicationsUtilities;

import exceptions.IncorrectEmployeeException;
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
 * Class used to create and show a Login Window
 */
public class JFLogin extends SingleFrameApplication {
    private JPanel topPanel;
    private JPanel logoPanel;
    private JPanel userDataPanel;
    private JPanel serverPanel;
    private JButton btnLogin;
    private JButton btnCancel;
    private JButton btnExpand;
	private boolean isServerPanelExpanded = false;
	private JLabel lblIP;
	private JTextField txtServerIP;
	private JLabel lblPort;
	private JPasswordField txtPass;
	private JTextField txtUserName;
	private JLabel lblPass;
	private JLabel lblUserName;
	private JTextField txtServerPort;	
	
	private static final String DEFAULT_PORT = "2995";
	private JMenu menuFile;
	private JMenuBar jMenuBar;
	private static final int HEIGHT = 75;
	
	private InfiniteProgressPanel glassPane;
	private panelChooseProject projectpanel;

	/**
     * Returns the action map used by this application.
     * Actions defined using the Action annotation
     * are returned by this method
    */
	private ApplicationActionMap getAppActionMap() {
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
        
        this.glassPane = new InfiniteProgressPanel(ApplicationInternationalization.getString("glassLogin"));
        getMainFrame().setGlassPane(glassPane);

        {
                getMainFrame().setSize(350, 301);
                getMainFrame().setResizable(false);
                getMainFrame().setTitle(ApplicationInternationalization.getString("titleLogin"));
        }

        BorderLayout mainFrameLayout = new BorderLayout();
        getMainFrame().getContentPane().setLayout(mainFrameLayout);
        getMainFrame().setPreferredSize(new java.awt.Dimension(350, 341));
        getMainFrame().setMinimumSize(new java.awt.Dimension(350, 341));
        getMainFrame().setMaximumSize(new java.awt.Dimension(0, 0));
        {
        	jMenuBar = new JMenuBar();
        	getMainFrame().setJMenuBar(jMenuBar);
        	{
        		menuFile = new JMenu();
        		jMenuBar.add(menuFile);
        		menuFile.setName("menuFile");
        		menuFile.setText(ApplicationInternationalization.getString("fileMenu"));
        		{
        			JMenuItem menuLogin = new JMenuItem();
        			menuFile.add(menuLogin);
        			menuLogin.setAction(getAppActionMap().get("loginAction"));
        			menuLogin.setText(ApplicationInternationalization.getString("titleLogin"));
        			menuFile.addSeparator();
        			JMenuItem menuExit = new JMenuItem();
        			menuFile.add(menuExit);
        			menuExit.setAction(getAppActionMap().get("Exit"));
        			menuExit.setText(ApplicationInternationalization.getString("exitAction"));
        		}
        	}
        	
        	{
        		JMenu menuWindow = new JMenu();
        		jMenuBar.add(menuWindow);
        		menuWindow.setName("menuWindow");
        		menuWindow.setText(ApplicationInternationalization.getString("windowMenu"));
        		{
        			JMenuItem menuChange = new JMenuItem();
        			menuWindow.add(menuChange);
        			menuChange.setAction(getAppActionMap().get("ChangeLanguage"));
        			menuChange.setText(ApplicationInternationalization.getString("changeMenu"));
        		}
        	}
        }

        {
                topPanel = new JPanel();
                getMainFrame().getContentPane().add(topPanel, BorderLayout.CENTER);
                AnchorLayout topPanelLayout = new AnchorLayout();
                topPanel.setLayout(topPanelLayout);
                topPanel.setPreferredSize(new java.awt.Dimension(338, 284));
                createLoginPanel();
                addButtons();
        }
        
        show(topPanel);
    }
    
    private void addButtons() {
        btnLogin = new JButton();
        topPanel.add(btnLogin, new AnchorConstraint(804, 97, 10, 578, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
        btnLogin.setPreferredSize(new java.awt.Dimension(75, 23));
        btnLogin.setName("btnLogin");
        btnLogin.setAction(getAppActionMap().get("loginAction"));
        btnLogin.setText(ApplicationInternationalization.getString("LoginButton"));
        btnCancel = new JButton();
        topPanel.add(btnCancel, new AnchorConstraint(804, 8, 9, 790, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
        btnCancel.setPreferredSize(new java.awt.Dimension(74, 24));
        btnCancel.setName("btnCancel");
        btnCancel.setAction(getAppActionMap().get("cancelAction"));
        btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));		
        
        btnLogin.setEnabled(true);
        getMainFrame().getRootPane().setDefaultButton(btnLogin);
	}

	private void createLoginPanel() {
	    {
        serverPanel = new JPanel();
        AnchorLayout jPanel1Layout = new AnchorLayout();
        topPanel.add(serverPanel, new AnchorConstraint(186, 975, 575, 10, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
        serverPanel.setLayout(jPanel1Layout);
        serverPanel.setPreferredSize(new java.awt.Dimension(326, 40));
        serverPanel.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("groupServer")));
        {
                txtServerPort = new JTextField();
                txtServerPort.setInputVerifier(new PortValidator(getMainFrame(), txtServerPort, ApplicationInternationalization.getString("loginValidatePort") + "[1 - 65535]"));
                serverPanel.add(txtServerPort, new AnchorConstraint(61, 6, 638, 295, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
                txtServerPort.setPreferredSize(new java.awt.Dimension(207, 20));
                txtServerPort.setName("txtServerPort");
        }
        {
                lblPort = new JLabel();
                serverPanel.add(lblPort, new AnchorConstraint(64, 232, 608, 20, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
                lblPort.setPreferredSize(new java.awt.Dimension(88, 14));
                lblPort.setName("lblPort");
                lblPort.setText(ApplicationInternationalization.getString("lblPort"));
        }
        {
                txtServerIP = new JTextField();
                txtServerIP.setInputVerifier(new IPValidator(getMainFrame(), txtServerIP, ApplicationInternationalization.getString("loginValidateIP")));
                serverPanel.add(txtServerIP, new AnchorConstraint(33, 6, 361, 295, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
                txtServerIP.setPreferredSize(new java.awt.Dimension(207, 20));
        }
        {
                lblIP = new JLabel();
                serverPanel.add(lblIP, new AnchorConstraint(34, 245, 361, 6, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
                lblIP.setName("lblIP");
                lblIP.setPreferredSize(new java.awt.Dimension(75, 19));
                lblIP.setText(ApplicationInternationalization.getString("lblIP"));
        }

       			setServerOptionVisible(false);

        {
                btnExpand = new JButton();
                try {
                	btnExpand.setIcon(ImagesUtilities.loadIcon("Expand_Vertical.png"));
                } catch (MalformedURLException e) {
                        JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                        JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
                }
                serverPanel.add(btnExpand, new AnchorConstraint(20, 6, 7, 934, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
                btnExpand.setPreferredSize(new java.awt.Dimension(25, 24));
                btnExpand.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent evt) {
                                btnExpandMouseClicked();
                        }
                });
        }
	    }
	    {
        userDataPanel = new JPanel();
        AnchorLayout userDataPanelLayout = new AnchorLayout();
        topPanel.add(userDataPanel, new AnchorConstraint(78, 52, 343, 10, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
        userDataPanel.setLayout(userDataPanelLayout);
        userDataPanel.setPreferredSize(new java.awt.Dimension(326, 97));
        userDataPanel.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("groupUser")));
        {
                //TODO: validar Password
                txtPass = new JPasswordField();
                txtPass.setInputVerifier(new NotEmptyValidator(getMainFrame(), txtPass, ApplicationInternationalization.getString("loginValidateEmpty")));
                userDataPanel.add(txtPass, new AnchorConstraint(561, 983, 809, 351, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                txtPass.setPreferredSize(new java.awt.Dimension(206, 24));
        }
        {
                txtUserName = new JTextField();
                txtUserName.setInputVerifier(new UserNameValidator(getMainFrame(), txtUserName, ApplicationInternationalization.getString("loginValidateUser")));
                userDataPanel.add(txtUserName, new AnchorConstraint(283, 983, 500, 351, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                txtUserName.setPreferredSize(new java.awt.Dimension(206, 21));
                txtUserName.setName("txtUserName");
        }
        {
                lblPass = new JLabel();
                userDataPanel.add(lblPass, new AnchorConstraint(623, 305, 757, 19, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                lblPass.setPreferredSize(new java.awt.Dimension(93, 13));
                lblPass.setName("lblPass");
                lblPass.setText(ApplicationInternationalization.getString("lblPass"));
        }
        {
                lblUserName = new JLabel();
                userDataPanel.add(lblUserName, new AnchorConstraint(324, 338, 458, 23, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                lblUserName.setPreferredSize(new java.awt.Dimension(103, 13));
                lblUserName.setName("lblUserName");
                lblUserName.setText(ApplicationInternationalization.getString("lblUser"));
        }
	    }
	    {
        logoPanel = new JPanel();
        AnchorLayout logoPanelLayout = new AnchorLayout();
        topPanel.add(logoPanel, new AnchorConstraint(11, 975, 293, 10, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
        logoPanel.setLayout(logoPanelLayout);
        logoPanel.setPreferredSize(new java.awt.Dimension(310, 61));
	    }
	}

	private void setServerOptionVisible (boolean b) {
		lblIP.setVisible(b);
		lblPort.setVisible(b);
		txtServerIP.setVisible(b);
		txtServerPort.setVisible(b);		
	}
    
    private void btnExpandMouseClicked() {
    	// Expand/contract the server options panel
    	Icon buttonIcon;
    	if (!isServerPanelExpanded){    
    		serverPanel.setPreferredSize(new java.awt.Dimension(serverPanel.getWidth(), serverPanel.getHeight() + HEIGHT));
    		getMainFrame().setSize(getMainFrame().getWidth(), getMainFrame().getHeight() +HEIGHT);    		
    		setServerOptionVisible(true);
    		try {
				buttonIcon = ImagesUtilities.loadIcon("Contract_Vertical.png");
				btnExpand.setIcon(buttonIcon);
	    		isServerPanelExpanded = true;
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}    		
    	}
    	else {
    		serverPanel.setPreferredSize(new java.awt.Dimension(serverPanel.getWidth(), serverPanel.getHeight() -HEIGHT));
    		getMainFrame().setSize(getMainFrame().getWidth(), getMainFrame().getHeight() -HEIGHT);    		
    		setServerOptionVisible(false);
    		try {
				buttonIcon = ImagesUtilities.loadIcon("Expand_Vertical.png");
				btnExpand.setIcon(buttonIcon);
	    		isServerPanelExpanded = false;
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
    	}
    }
    
    /*** Actions for buttons ***/
    @Action
	public void loginAction() {
		final String ip;
		final String port;
		// Check if input data is valid
		if (txtUserName.getText().length() > 0 && txtPass.getPassword().length > 0) {
			// If the server panel is not expanded, take the default local IP
			// and port
			if (!isServerPanelExpanded) {
				ip = CommunicationsUtilities.getHostIP();
				port = DEFAULT_PORT;
				txtServerIP.setText(ip);
				txtServerPort.setText(port);
			} else {
				ip = txtServerIP.getText();
				port = txtServerPort.getText();
			}

		
			btnLogin.setEnabled(false);
			// Invoke a new thread in order to show the panel with the loading
			// spinner
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					glassPane.setColorB(241);
					glassPane.start();
					Thread performer = new Thread(new Runnable() {
						public void run() {
							perform(txtUserName.getText(), new String(txtPass.getPassword()), ip, port);
						}
					}, "Performer");
					performer.start();
				}
			});
		}
	}
    
    @Action 
    public void cancelAction() {
    	getMainFrame().dispose();
    }
    
    @Action
	public void Exit() {
		if (JOptionPane.showConfirmDialog(getMainFrame(), ApplicationInternationalization.getString("Dialog_CloseFrame_Message"), ApplicationInternationalization.getString("Dialog_CloseFrame_Message"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
			quit();
	}
    
    @Action
    public void ChangeLanguage() {
    	JDLanguages lang = new JDLanguages(this);
		lang.setLocationRelativeTo(getMainFrame());
		lang.setModal(true);
		lang.setVisible(true);
    }
    
    @Action
    public void acceptAction() {
    	try {
    		int index = projectpanel.getProjectId();
    		if (index == -1) {
    			JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("JFLogin_NotSelectedProject"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
    		}
    		else {
    			ClientController.getInstance().setCurrentProject(index);
    			ClientController.getInstance().showMainFrame();
    		}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
    }
    
    @Action
    public void backwardAction() {
    	// Clean the panel to choose the project and displays the login panel
    	topPanel.removeAll();
    	createLoginPanel();
    	getMainFrame().setTitle(ApplicationInternationalization.getString("titleLogin"));

		// Change name and actions of the buttons
		addButtons();
    	btnCancel.setAction(getAppActionMap().get("cancelAction"));
    	btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
    	btnLogin.setAction(getAppActionMap().get("loginAction"));
    	btnLogin.setText(ApplicationInternationalization.getString("LoginButton"));
    }
    
    // Method used to make login and show the loading spinner panel
	private void perform(String user, String pass, String ip, String port) {
		try {
			getMainFrame().setEnabled(false);
			// Simulates long task
			Thread.sleep(2000);
			// Login
			ClientController.getInstance().initClient(ip, port, user, pass);
			glassPane.stop();
			getMainFrame().setEnabled(true);
			getMainFrame().requestFocus();
			
			// Show panel used to choose a project
			chooseProject();			
			
		} catch (InterruptedException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		catch (RemoteException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (MalformedURLException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotBoundException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (IncorrectEmployeeException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} finally {
			getMainFrame().setEnabled(true);
			getMainFrame().requestFocus();
		}
	}

	// Shows panel used to choose a project
	private void chooseProject() throws RemoteException, NotLoggedException, Exception {
		// Clean the login panel and displays the panel to choose the project
		topPanel.removeAll();
		getMainFrame().setTitle(ApplicationInternationalization.getString("titleChooseProject"));
		
		// Create the panel the first time
		if (projectpanel == null) {
			projectpanel = new panelChooseProject();
		}
		// TODO: crear un panel aparte para el logo
//		AnchorLayout logoPanelLayout = new AnchorLayout();
//		logoPanel.setLayout(logoPanelLayout);
//		logoPanel.setBounds(6, 8, 332, 53);
//		projectpanel.add(logoPanel);
		projectpanel.setProjects(ClientController.getInstance().getProjectsFromCurrentUser());
    	topPanel.add(projectpanel, new AnchorConstraint(1, 998, 836, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
    	projectpanel.setPreferredSize(new java.awt.Dimension(343, 228));

		// Change name and actions of the buttons
    	addButtons();
		btnLogin.setAction(getAppActionMap().get("acceptAction"));
		btnLogin.setText(ApplicationInternationalization.getString("btnAccept"));
		btnCancel.setAction(getAppActionMap().get("backwardAction"));
		btnCancel.setText(ApplicationInternationalization.getString("btnBackward"));
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
	
	private void closeSessionConfirm() {
		try {
			// Close session
			ClientController.getInstance().signout();
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
	
	// When closes session, return to the login frame
	public void forceCloseSession() {
		JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("message_ForceCloseSession"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		try {
			ClientController.getInstance().restartLoginFrame();
		} catch (InterruptedException e) {
		}
		
	}

	// When the server goes offline, then return to the login frame
	public void approachlessServer() {
		JOptionPane.showMessageDialog(getMainFrame(), ApplicationInternationalization.getString("message_approachlessServer"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		try {
			ClientController.getInstance().restartLoginFrame();
		} catch (InterruptedException e) {
		}	
	}	
}
