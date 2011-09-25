package presentation;

import internationalization.AppInternationalization;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.EventObject;

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
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import model.business.control.ServerController;

import org.jdesktop.application.Application;

import presentation.auxiliary.CloseWindowListener;
import presentation.auxiliary.IWindowState;
import resources.ImagesUtilities;
import resources.InfiniteProgressPanel;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import communication.ServerConfiguration;

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
 * Main Server UI Window
 */
public class JFServer extends javax.swing.JFrame implements IWindowState {		
	private static final long serialVersionUID = -113838536647924014L;
	
	private ServerController controller;
	private ServerConfiguration configuration;
	private JDConfig frmConfiguration;
	private JDAbout frmAbout;
	private JLabel lblConfigBD;
	private JPanel pnlPanel;
	private JScrollPane scpPanelLog;
	private JLabel lblStatusBar;
	private JButton btnDisconnectToolbar;
	private JLabel lblConnectedClients;
	private JTextArea txtLog;
	private JToolBar toolbar;
	private JPanel panelLogo;

	private int clientsNumber;
	
	private InfiniteProgressPanel glassPane;
	private JButton btnConfigure;
	private JButton btnDisconnect;
	private JButton btnConnect;

	private boolean ok;

	private BufferedImage image;

	private JButton btnConnectToolbar;
	
	{
		 //Set Look & Feel
	    try {
	    	javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
	    } catch(Exception e) {
	    	JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
	    }
//		for(UIManager.LookAndFeelInfo laf:UIManager.getInstalledLookAndFeels()){
//            if("Nimbus".equals(laf.getName()))
//                try {
//                UIManager.setLookAndFeel(laf.getClassName());
//            } catch (Exception ex) {
//            }
//        }
	}
    
	public JFServer(ServerController controller) {
		super();
		initGUI();
		this.controller = controller;
		configuration = new ServerConfiguration();
		updateState();
	}
	
	private void initGUI() {
		try {
			this.glassPane = new InfiniteProgressPanel(AppInternationalization.getString("glassConnect"));
	    	setGlassPane(glassPane);
	    	
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.setTitle(AppInternationalization.getString("JFServer_Title"));
			this.setPreferredSize(new java.awt.Dimension(565, 527));
			this.setMinimumSize(new java.awt.Dimension(500, 300));
			setLocationRelativeTo(null);
			{
				pnlPanel = new JPanel();
				AnchorLayout pnlPanelLayout = new AnchorLayout();
				getContentPane().add(pnlPanel, BorderLayout.CENTER);
				pnlPanel.setLayout(pnlPanelLayout);
				pnlPanel.setPreferredSize(new java.awt.Dimension(542, 327));
				{
					lblConnectedClients = new JLabel();
					pnlPanel.add(lblConnectedClients, new AnchorConstraint(889, 286, 36, 10, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
					lblConnectedClients.setText(AppInternationalization.getString("ConnectedClients_label"));
					lblConnectedClients.setName("lblClientesConectados");
					lblConnectedClients.setPreferredSize(new java.awt.Dimension(147, 16));
				}
				{
					scpPanelLog = new JScrollPane();
					pnlPanel.add(scpPanelLog, new AnchorConstraint(80, 982, 774, 9, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
					scpPanelLog.setPreferredSize(new java.awt.Dimension(530, 282));
					scpPanelLog.setMinimumSize(new java.awt.Dimension(346, 155));
					{
						txtLog = new JTextArea();
						scpPanelLog.setViewportView(txtLog);
						txtLog.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
						txtLog.setFont(new java.awt.Font("Tahoma",0,12));
						txtLog.setName("txtLog");
						txtLog.setEditable(false);

					}
				}
				{
					lblStatusBar = new JLabel();
					pnlPanel.add(lblStatusBar, new AnchorConstraint(941, 487, 12, 10, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
					lblStatusBar.setText(AppInternationalization.getString("StatusBar_Label"));
					lblStatusBar.setPreferredSize(new java.awt.Dimension(257, 16));
					lblStatusBar.setName("lblStatusBar");
				}
				{
					btnDisconnect = new JButton();
					pnlPanel.add(btnDisconnect, new AnchorConstraint(795, 10, 853, 795, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					btnDisconnect.setText(AppInternationalization.getString("btn_Disconnect_Text"));
					btnDisconnect.setEnabled(false);
					btnDisconnect.setName("btnDisconnect");
					btnDisconnect.setPreferredSize(new java.awt.Dimension(103, 27));
					btnDisconnect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnDisconnectActionPerformed(evt);
						}
					});
				}
				{
					btnConnect = new JButton();
					pnlPanel.add(btnConnect, new AnchorConstraint(798, 128, 851, 587, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					btnConnect.setText(AppInternationalization.getString("btn_Connect_Text"));
					btnConnect.setName("btnConnect");
					btnConnect.setPreferredSize(new java.awt.Dimension(99, 25));
					btnConnect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnConnectActionPerformed(evt);
						}
					});
				}
				{
					lblConfigBD = new JLabel();
					pnlPanel.add(lblConfigBD, new AnchorConstraint(939, 980, 13, 498, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL));
					lblConfigBD.setText(AppInternationalization.getString("DB_Main_Label") + " IP XXX.XXX.XXX.XXX, puerto XXXXX");
					lblConfigBD.setHorizontalAlignment(SwingConstants.TRAILING);
					lblConfigBD.setName("lblConfigDB");
					lblConfigBD.setPreferredSize(new java.awt.Dimension(265, 16));
				}
				{
					toolbar = new JToolBar();
					pnlPanel.add(toolbar, new AnchorConstraint(1, 1000, 71, 0, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
					
					{
						btnConnectToolbar = new JButton();
						toolbar.add(btnConnectToolbar);
						configureToolbarButton(btnConnectToolbar, "connect");
						btnConnectToolbar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnConnectActionPerformed(evt);
							}
						});
						btnConnectToolbar.setToolTipText(AppInternationalization.getString("Tooltip_Connect"));
					}
					{
						btnDisconnectToolbar = new JButton();
						toolbar.add(btnDisconnectToolbar);
						configureToolbarButton(btnDisconnectToolbar, "disconnect");
						btnDisconnectToolbar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnDisconnectActionPerformed(evt);
							}
						});
						btnDisconnectToolbar.setToolTipText(AppInternationalization.getString("Tooltip_Disconnect"));
					}
					{
						toolbar.addSeparator();
						btnConfigure = new JButton();
						toolbar.add(btnConfigure);
						configureToolbarButton(btnConfigure, "configure");
						btnConfigure.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								mniConfigureActionPerformed(evt);
							}
						});
						btnConfigure.setToolTipText(AppInternationalization.getString("Tooltip_Configure"));
					}
				}
				{
					panelLogo = new JPanel();
					pnlPanel.add(panelLogo, new AnchorConstraint(32, 982, 172, 10, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
					panelLogo.setPreferredSize(new java.awt.Dimension(529, 48));
				}
			}

			this.addWindowListener(new WindowAdapter() { 
				public void windowClosing(WindowEvent evt) {    
					thisWindowClosing(evt);
				}
			});

			toolbar.setPreferredSize(new java.awt.Dimension(549, 50));
			getRootPane().setDefaultButton(btnConnect);

			pack();
			this.setSize(565, 527);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@SuppressWarnings("unused")
	protected void mnuChangeActionPerformed(ActionEvent evt) {
		JDLanguages lang = new JDLanguages(this);
		lang.setLocationRelativeTo(this);
		lang.setModal(true);
		lang.setVisible(true);		
	}

	//$hide>>$
	
	private JButton configureToolbarButton(JButton button, String action) throws MalformedURLException, IOException {
	    	button.setBorder(BorderFactory.createEmptyBorder(5,10,4,10));
	    	button.setFocusPainted(true);
	    	button.setHorizontalTextPosition(SwingConstants.CENTER);
	    	button.setName(action);
	    	button.setVerticalTextPosition(SwingConstants.BOTTOM);
	    	button.setRequestFocusEnabled(false);
	    	image = ImagesUtilities.loadCompatibleImage("toolbar/" + action + ".png");
	    	button.setIcon(new ImageIcon(image));
	    	button.setRolloverEnabled(true);
	    	// Save button icon
			ImagesUtilities.addImageButton(button.getName(), image);
	    	button.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent evt) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  				
					ImagesUtilities.decreaseImageBrightness((JButton) evt.getSource());
				}

				public void mouseEntered(MouseEvent evt) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
					ImagesUtilities.increaseImageBrightness((JButton) evt.getSource());
				}
			});
	    	
	    	button.setSize(25, 50);
	    	
//	    	button.setContentAreaFilled(false);
	    	return button;
	    }
	
	@SuppressWarnings("unused")
	private void mniAboutActionPerformed(ActionEvent evt) {
		frmAbout = new JDAbout(this);
		frmAbout.addVentanaCerradaListener(new CloseWindowListener() {
			public void closeWindow(EventObject evt) {    
				frmAboutClosewindow(evt);
			}
		});
		frmAbout.setLocationRelativeTo(this);
		frmAbout.setModal(true);
		frmAbout.setVisible(true);
	}
	
	@SuppressWarnings("unused")
	private void frmAboutClosewindow(EventObject evt) {
		frmAbout.dispose();
	}
	
	@SuppressWarnings("unused")
	private void mniConfigureActionPerformed(ActionEvent evt) {
		// Create configuration Dialog
		frmConfiguration = new JDConfig(this);
		frmConfiguration.addWindowCloseListener(new CloseWindowListener() {
			public void closeWindow(EventObject evt) {    
				frmConfigureCloseWindow(evt);
			}
		});

		frmConfiguration.setConfiguration(configuration);
		frmConfiguration.setLocationRelativeTo(this);
		frmConfiguration.setModal(true);
		frmConfiguration.setVisible(true);
	}

	@SuppressWarnings("unused")
	private void frmConfigureCloseWindow(EventObject evt) {
		configuration = frmConfiguration.getConfiguration();
		updateState();
		frmConfiguration.dispose();
	}
	
	@SuppressWarnings("unused")
	private void btnConnectActionPerformed(ActionEvent evt) {
		activateServer();
	}
	
	@SuppressWarnings("unused")
	private void btnDisconnectActionPerformed(ActionEvent evt) {
		confirmDisableServer();
	}

	@SuppressWarnings("unused")
	private void thisWindowClosing(WindowEvent evt) {
		closeServer();
	}
	
	@SuppressWarnings("unused")
	private void mniConnectActionPerformed(ActionEvent evt) {
		activateServer();
	}
	
	@SuppressWarnings("unused")
	private void mniDisconnectActionPerformed(ActionEvent evt) {
		disableServer();
	}

	@SuppressWarnings("unused")
	private void mniExitActionPerformed(ActionEvent evt) {
		closeServer();
	}
	
	private void updateState() {
		if(controller != null && controller.isServerActivate()) {
			lblStatusBar.setText(AppInternationalization.getString("StatusBarConnect_Label") + "(" + controller.getIPServidor() + ":" + String.valueOf(configuration.getServerPort()) + ").");
		} else {
			lblStatusBar.setText(AppInternationalization.getString("StatusBar_Label"));
		}
		lblConfigBD.setText(AppInternationalization.getString("DB_Main_Label") + " " + configuration.getDBIp() + ":" + String.valueOf(configuration.getDBPort()));
		}
	
	
	private boolean activateServer() {
		ok = false;
		// Invoke a new thread in order to show the panel with the loading
		// spinner
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Blue color for spinner
				glassPane.setColorB(241);
				glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						perform(configuration);
					}
				}, "Performer");
				performer.start();
			}
		});			
		return ok;
	}
	  
	 // Method used to start the server
	private void perform(ServerConfiguration configuration) {
		try {
			// Activate server
			controller.startServer(configuration);
			// Update UI Window
			btnConnectToolbar.setEnabled(false);
			btnConnect.setEnabled(false);
//			mniConnect.setEnabled(false);
//			mniConfigure.setEnabled(false);
			btnConfigure.setEnabled(false);
			btnDisconnectToolbar.setEnabled(true);
			btnDisconnect.setEnabled(true);
//			mniDisconnect.setEnabled(true);
			updateState();
			ok = true;
			glassPane.stop();
		} catch(SQLException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(MalformedURLException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(RemoteException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
			disableServer();
		}			
	}   
	
	private boolean disableServer() {
		boolean ok;
		
		ok = false;
		try {
			// Disconnect clients disconnect and stop the server 
			controller.stopServer(configuration);

			btnConnectToolbar.setEnabled(true);
			btnConnect.setEnabled(true);
//			mniConnect.setEnabled(true);
//			mniConfigure.setEnabled(true);
			btnConfigure.setEnabled(true);
			btnDisconnectToolbar.setEnabled(false);
			btnDisconnect.setEnabled(false);
//			mniDisconnect.setEnabled(false);
			updateState();
			ok = true;		
		} catch(MalformedURLException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		}
		
		return ok;
	}
	
	private void confirmDisableServer() {
		if(controller.isServerActivate()) {
			if(controller.getNumberConnectedClients() > 0) {
				if(JOptionPane.showConfirmDialog(this, AppInternationalization.getString("CloseServer_Message"), AppInternationalization.getString("Warning_Text"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					disableServer();
				}
			} else {
				disableServer();
			}
		}
	}
	
	private void closeServer() {
		boolean exit;
		
		exit = false;
		if(controller.isServerActivate()) {
			if(JOptionPane.showConfirmDialog(this, AppInternationalization.getString("CloseServer_Message"), AppInternationalization.getString("Warning_Text"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				if(disableServer()) {
					exit = true;
				}
			}
		} else {
			exit = true;
		}
		
		if(exit) {
			setVisible(false);
			dispose();
			System.exit(0);
		}
	}
	
	/*** Public Methods ***/	
	public void putMessage(String message) {
		txtLog.setText(txtLog.getText() + message + "\n");
		txtLog.setCaretPosition(txtLog.getDocument().getLength());
	}

	public String getMessages() {
		return txtLog.getText();
	}
	
	public void updateConnectedClients(int clientsNumber) {
		this.clientsNumber = clientsNumber;
		if(clientsNumber == 1) {
			lblConnectedClients.setText(clientsNumber + " " + AppInternationalization.getString("Connected_Client"));
		} else {
			lblConnectedClients.setText(clientsNumber + " " + AppInternationalization.getString("Connected_Clients"));
		}
	}
	
	public int getClientesEscuchando() {
		return clientsNumber;
	}
	//$hide<<$
	
}
