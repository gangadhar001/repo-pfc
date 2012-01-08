package presentation;

import internationalization.AppInternationalization;
import internationalization.ApplicationInternationalization;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;

import presentation.customComponents.CustomMenubar;
import presentation.customComponents.panelChooseProject;
import resources.ImagesUtilities;
import resources.InfiniteProgressPanel;
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
public class JDLogin extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8249758465749822474L;
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
	private static final int HEIGHT = 75;
	
	private InfiniteProgressPanel glassPane;
	private panelChooseProject projectpanel;
	private CustomMenubar menuBar;


	/**
     * Returns the action map used by this application.
     * Actions defined using the Action annotation
     * are returned by this method
    */
	private ApplicationActionMap getAppActionMap() {
		return Application.getInstance().getContext().getActionMap(this);
	}
	
    public JDLogin(CustomMenubar menuBar) {
    	this.menuBar = menuBar;
    	
    	// Listener to confirm exit
    	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
                  closeWin();
			}
         });
        
		this.setTitle(AppInternationalization.getString("JFConfig_Title"));
        this.glassPane = new InfiniteProgressPanel(ApplicationInternationalization.getString("glassLogin"));
        this.setGlassPane(glassPane);

        {
                this.setSize(360, 230);
                this.setResizable(false);
                this.setTitle(ApplicationInternationalization.getString("titleLogin"));
        }

        getContentPane().setLayout(null);
        {
        	userDataPanel = new JPanel();
        	getContentPane().add(userDataPanel);
        	AnchorLayout userDataPanelLayout = new AnchorLayout();
        	userDataPanel.setLayout(userDataPanelLayout);
        	userDataPanel.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("groupUser")));
        	userDataPanel.setBounds(10, 6, 340, 97);
        	{
        		txtPass = new JPasswordField();
	        	//txtPass.setInputVerifier(new NotEmptyValidator(this, txtPass, ApplicationInternationalization.getString("loginValidateEmpty")));
	        	userDataPanel.add(txtPass, new AnchorConstraint(561, 983, 809, 351, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
	        	txtPass.setPreferredSize(new java.awt.Dimension(206, 24));
        	}
        	{
        		txtUserName = new JTextField();
        		//txtUserName.setInputVerifier(new UserNameValidator(this, txtUserName, ApplicationInternationalization.getString("loginValidateUser")));
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
        	btnLogin = new JButton();
        	getContentPane().add(btnLogin);
        	btnLogin.setName("btnLogin");
    		btnLogin.setBounds(173, 159, 80, 26);
    		btnLogin.setAction(getAppActionMap().get("loginAction"));
    		btnLogin.setText(ApplicationInternationalization.getString("LoginButton"));
        }
        {
        	btnCancel = new JButton();
        	getContentPane().add(btnCancel);
        	btnCancel.setName("btnCancel");
        	btnCancel.setBounds(264, 159, 80, 26);
        	btnCancel.setAction(getAppActionMap().get("cancelAction"));
        	btnCancel.setText(ApplicationInternationalization.getString("btnCancel"));
        }
        {
        	projectpanel = new panelChooseProject();
        	getContentPane().add(projectpanel);
        	projectpanel.setSize(340, 97);
        	projectpanel.setBounds(10, 6, 340, 97);
        }
        {
        	serverPanel = new JPanel();
        	getContentPane().add(serverPanel);
        	AnchorLayout jPanel1Layout = new AnchorLayout();
        	serverPanel.setLayout(jPanel1Layout);
        	serverPanel.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("groupServer")));
        	serverPanel.setBounds(12, 107, 338, 40);
        	{
        		txtServerPort = new JTextField();
        		//txtServerPort.setInputVerifier(new PortValidator(this, txtServerPort, ApplicationInternationalization.getString("loginValidatePort") + "[1 - 65535]"));
        		serverPanel.add(txtServerPort, new AnchorConstraint(61, 6, 638, 295, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
        		txtServerPort.setPreferredSize(new java.awt.Dimension(207, 20));
        		txtServerPort.setName("txtServerPort");
        	}
        	{
        		lblPort = new JLabel();
        		serverPanel.add(lblPort, new AnchorConstraint(61, 232, 608, 20, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
        		lblPort.setPreferredSize(new java.awt.Dimension(100, 20));
        		lblPort.setName("lblPort");
        		lblPort.setText(ApplicationInternationalization.getString("lblPort"));
        	}
        	{
        		txtServerIP = new JTextField();
        		//txtServerIP.setInputVerifier(new IPValidator(this, txtServerIP, ApplicationInternationalization.getString("loginValidateIP")));
        		serverPanel.add(txtServerIP, new AnchorConstraint(33, 6, 361, 295, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
        		txtServerIP.setPreferredSize(new java.awt.Dimension(207, 20));
        	}
        	{
        		lblIP = new JLabel();
        		serverPanel.add(lblIP, new AnchorConstraint(34, 245, 361, 6, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
        		lblIP.setName("lblIP");
        		lblIP.setPreferredSize(new java.awt.Dimension(75, 20));
        		lblIP.setText(ApplicationInternationalization.getString("lblIP"));
        	}
        	
        	setServerOptionVisible(false);
        	
        	{
        		btnExpand = new JButton();
        		try {
        			btnExpand.setIcon(ImagesUtilities.loadIcon("Expand_Vertical.png"));
        		} catch (MalformedURLException e) {
        			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
        		} catch (IOException e) {
        			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
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
        
        txtServerIP.setText(CommunicationsUtilities.getHostIP());
		txtServerPort.setText(DEFAULT_PORT);
        
        this.getRootPane().setDefaultButton(btnLogin);
        projectpanel.setVisible(false);
    }
    
    protected void closeWin() {
    	this.dispose();    	
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
    		serverPanel.setSize(new java.awt.Dimension(serverPanel.getWidth(), serverPanel.getHeight() + HEIGHT));
    		this.setSize(this.getWidth(), this.getHeight() + HEIGHT); 
    		btnLogin.setBounds(173, 230, 80, 26);
    		btnCancel.setBounds(264, 230, 84, 26);
    		setServerOptionVisible(true);
    		try {
				buttonIcon = ImagesUtilities.loadIcon("Contract_Vertical.png");
				btnExpand.setIcon(buttonIcon);
	    		isServerPanelExpanded = true;
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}    		
    	}
    	else {
    		serverPanel.setSize(new java.awt.Dimension(serverPanel.getWidth(), serverPanel.getHeight() -HEIGHT));
    		this.setSize(this.getWidth(), this.getHeight() - HEIGHT);    		
    		setServerOptionVisible(false);
    		btnLogin.setBounds(173, 159, 80, 26);
    		btnCancel.setBounds(264, 159, 80, 26);
    		try {
				buttonIcon = ImagesUtilities.loadIcon("Expand_Vertical.png");
				btnExpand.setIcon(buttonIcon);
	    		isServerPanelExpanded = false;
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
    	}
    	this.validate();
    	this.repaint();
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
		else {
			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("emptyLoginInfo"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
    
    @Action 
    public void cancelAction() {
    	this.dispose();
    }
    
    @Action
	public void Exit() {
		if (JOptionPane.showConfirmDialog(this, ApplicationInternationalization.getString("Dialog_CloseFrame_Message"), ApplicationInternationalization.getString("Dialog_CloseFrame_Message"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
			quit();
	}
    
    @Action
    public void acceptAction() {
    	try {
    		int index = projectpanel.getProjectId();
    		if (index == -1) {
    			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("JFLogin_NotSelectedProject"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
    		}
    		else {
    			ClientController.getInstance().setCurrentProject(index);
    			ClientController.getInstance().setCurrentProjectName(projectpanel.getProject().getName());
    			menuBar.enableMenuItemsLogin();
    			this.dispose();
    		}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
    }
    
    @Action
    public void backwardAction() {
    	try {
			ClientController.getInstance().signout();
			this.dispose();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
    	
    	
    }
    
    // Method used to make login and show the loading spinner panel
	private void perform(String user, String pass, String ip, String port) {
		try {
			this.setEnabled(false);
			// Simulates long task
			Thread.sleep(2000);
			// Login
			ClientController.getInstance().initClient(ip, port, user, pass);
			glassPane.stop();
			this.setEnabled(true);
			this.requestFocus();
			
			// Show panel used to choose a project
			chooseProject();			
			
		} catch (InterruptedException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		catch (RemoteException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (MalformedURLException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotBoundException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (IncorrectEmployeeException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			glassPane.stop();
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} finally {
			this.setEnabled(true);
			this.requestFocus();
		}
	}

	// Shows panel used to choose a project
	private void chooseProject() throws RemoteException, NotLoggedException, Exception {
		// Clean the login panel and displays the panel to choose the project
		userDataPanel.setVisible(false);
		serverPanel.setVisible(false);
		this.setTitle(ApplicationInternationalization.getString("titleChooseProject"));
		
		projectpanel.setProjects(ClientController.getInstance().getProjectsFromCurrentUser());
		projectpanel.setVisible(true);

		// Change name and actions of the buttons
		btnLogin.setAction(getAppActionMap().get("acceptAction"));
		btnLogin.setText(ApplicationInternationalization.getString("btnAccept"));
		btnCancel.setAction(getAppActionMap().get("backwardAction"));
		btnCancel.setText(ApplicationInternationalization.getString("btnCancel"));
		
		this.repaint();
	}  
	
	public void quit() {
		try {
			if (ClientController.getInstance().isLogged()) {
				// Close session
	       	 	closeSessionConfirm();		       	 	
			}
			ClientController.getInstance().closeController();
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (ClassCastException e) {
			// Ignore
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void closeSessionConfirm() {
		try {
			// Close session
			ClientController.getInstance().signout();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("serverDisconnect"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) { 
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
}
