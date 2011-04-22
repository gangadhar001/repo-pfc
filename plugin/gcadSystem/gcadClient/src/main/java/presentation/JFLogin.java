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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.SingleFrameApplication;

import presentation.customComponents.InfiniteProgressPanel;
import presentation.utils.GraphicsUtilities;
import presentation.utils.validation.IPValidator;
import presentation.utils.validation.NotEmptyValidator;
import presentation.utils.validation.PortValidator;
import presentation.utils.validation.UserNameValidator;
import bussiness.control.ClientController;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import communication.CommunicationsUtilities;

import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;
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
	private static final int HEIGHT = 75;
	
	private InfiniteProgressPanel glassPane;

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
             public void willExit(EventObject event) {} 
         }); 
    	
    	this.glassPane = new InfiniteProgressPanel();
    	getMainFrame().setGlassPane(glassPane);
    	
    	{
	    	getMainFrame().setSize(350, 301);
	    	getMainFrame().setResizable(false);
	    	getMainFrame().setTitle(ApplicationInternationalization.getString("title"));
    	}
    	
    	BorderLayout mainFrameLayout = new BorderLayout();
    	getMainFrame().getContentPane().setLayout(mainFrameLayout);
    	getMainFrame().setPreferredSize(new java.awt.Dimension(350, 301));
    	getMainFrame().setMinimumSize(new java.awt.Dimension(350, 301));
    	getMainFrame().setMaximumSize(new java.awt.Dimension(0, 0));

    	{
    		topPanel = new JPanel();
    		getMainFrame().getContentPane().add(topPanel, BorderLayout.CENTER);
    		AnchorLayout topPanelLayout = new AnchorLayout();
    		topPanel.setLayout(topPanelLayout);
    		topPanel.setPreferredSize(new java.awt.Dimension(338, 284));
    		{
    			btnLogin = new JButton();
    			topPanel.add(btnLogin, new AnchorConstraint(804, 110, 17, 578, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
    			btnLogin.setPreferredSize(new java.awt.Dimension(75, 23));
    			btnLogin.setName("btnLogin");
    			btnLogin.setAction(getAppActionMap().get("loginAction"));
    			btnLogin.setText(ApplicationInternationalization.getString("LoginButton"));
    		}
    		{
    			btnCancel = new JButton();
    			topPanel.add(btnCancel, new AnchorConstraint(804, 26, 17, 790, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
    			btnCancel.setPreferredSize(new java.awt.Dimension(74, 24));
    			btnCancel.setName("btnCancel");
    			btnLogin.setText(ApplicationInternationalization.getString("CancelButton"));
    			btnCancel.setAction(getAppActionMap().get("cancelAction"));
    		}
    		{
    			serverPanel = new JPanel();
    			AnchorLayout jPanel1Layout = new AnchorLayout();
    			topPanel.add(serverPanel, new AnchorConstraint(186, 975, 575, 10, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
    			serverPanel.setLayout(jPanel1Layout);
    			serverPanel.setPreferredSize(new java.awt.Dimension(326, 40));
    			serverPanel.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("groupServer")));
    			{
    				txtServerPort = new JTextField();
    				txtServerPort.setInputVerifier(new PortValidator(getMainFrame(), txtServerPort, "El puerto debe contener solo numeros en el rango x-x"));
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
    				txtServerIP.setInputVerifier(new IPValidator(getMainFrame(), txtServerIP, "La IP no puede ser vacia o no tiene el formato adecuado"));
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
						btnExpand.setIcon(GraphicsUtilities.loadIcon("Expand_Vertical.png"));
    				} catch (MalformedURLException e) {
    					JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
    				} catch (IOException e) {
    					JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
    				}  
    				serverPanel.add(btnExpand, new AnchorConstraint(20, 6, 7, 934, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
    				btnExpand.setPreferredSize(new java.awt.Dimension(25, 24));
    				btnExpand.addMouseListener(new MouseAdapter() {
    					public void mouseClicked(MouseEvent evt) {
    						btnExpandMouseClicked(evt);
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
    				txtPass = new JPasswordField();
    				txtPass.setInputVerifier(new NotEmptyValidator(getMainFrame(), txtPass, "Field cannot be null."));
    				userDataPanel.add(txtPass, new AnchorConstraint(561, 983, 809, 351, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
    				txtPass.setPreferredSize(new java.awt.Dimension(206, 24));    				
    			}
    			{
    				txtUserName = new JTextField();
    				txtUserName.setInputVerifier(new UserNameValidator(getMainFrame(), txtUserName, "User name must contain alfanumeric characters"));
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
    	
    	getMainFrame().getRootPane().setDefaultButton(btnLogin);
        show(topPanel);
    }
    
    private void setServerOptionVisible (boolean b) {
		lblIP.setVisible(b);
		lblPort.setVisible(b);
		txtServerIP.setVisible(b);
		txtServerPort.setVisible(b);		
	}
    
    private void btnExpandMouseClicked(MouseEvent evt) {
    	// Expand/contract the server options panel
    	Icon buttonIcon;
    	if (!isServerPanelExpanded){    
    		serverPanel.setPreferredSize(new java.awt.Dimension(serverPanel.getWidth(), serverPanel.getHeight() + HEIGHT));
    		getMainFrame().setSize(getMainFrame().getWidth(), getMainFrame().getHeight() +HEIGHT);    		
    		setServerOptionVisible(true);
    		try {
				buttonIcon = GraphicsUtilities.loadIcon("Contract_Vertical.png");
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
				buttonIcon = GraphicsUtilities.loadIcon("Expand_Vertical.png");
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
		if (txtUserName.getText().length() > 0
				&& txtPass.getPassword().length > 0) {
			// If the server panel is not expanded, take the default local IP
			// and port
			if (!isServerPanelExpanded) {
				ip = CommunicationsUtilities.getHostIP();
				port = DEFAULT_PORT;
			} else {
				ip = txtServerIP.getText();
				port = txtServerPort.getText();
			}

			// Invoke a new thread in order to show the panel with the loading
			// spinner
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
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
    
    // Method used to make login and show the loading spinner panel
	private void perform(String user, String pass, String ip, String port) {
		try {
			getMainFrame().setEnabled(false);
			// Simulates long task
			Thread.sleep(4000);
			// Login
			ClientController.getInstance().initClient(ip, port, user, pass);
			glassPane.stop();
		} catch (InterruptedException e) {
			glassPane.stop();
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		catch (RemoteException e) {
			glassPane.stop();
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (MalformedURLException e) {
			glassPane.stop();
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotBoundException e) {
			glassPane.stop();
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (IncorrectEmployeeException e) {
			glassPane.stop();
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			glassPane.stop();
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonExistentRole e) {
			glassPane.stop();
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			glassPane.stop();
			JOptionPane.showMessageDialog(getMainFrame(), e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} finally {
			getMainFrame().setEnabled(true);
			getMainFrame().requestFocus();
		}
	}   
}
