package presentation;

import communication.ServerConfiguration;
import internationalization.AppInternationalization;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import javax.swing.BorderFactory;
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
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import model.business.control.ServerController;
import org.jdesktop.application.Application;

import presentation.auxiliary.CloseWindowListener;
import presentation.auxiliary.IWindowState;
import resources.InfiniteProgressPanel;

import java.util.EventObject;

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
	private JMenuItem mniDisconnect;
	private JMenuItem mniConnect;
	private JPanel pnlPanel;
	private JScrollPane scpPanelLog;
	private JLabel lblStatusBar;
	private JButton btnDisconnect;
	private JLabel lblConnectedClients;
	private JButton btnConnect;
	private JMenuBar mnbMenus;
	private JTextArea txtLog;
	private JMenuItem mniAcercaDe;
	private JMenuItem mniConfigure;
	private JMenuItem mniExit;
	private JSeparator sepSeparator;
	private JMenu mnuFile;
	private JMenu mnuHelp;
	private JToolBar toolbar;
	private JPanel panelLogo;
	private JMenu mnuOption;

	private int clientsNumber;
	
	private InfiniteProgressPanel glassPane;

	private boolean ok;
	
	{
		 //Set Look & Feel
	    try {
	    	javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
	    } catch(Exception e) {
	    	JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
	    }
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
			this.setPreferredSize(new java.awt.Dimension(506, 409));
			this.setMinimumSize(new java.awt.Dimension(500, 300));
			setLocationRelativeTo(null);
			{
				pnlPanel = new JPanel();
				getContentPane().add(pnlPanel, BorderLayout.CENTER);
				GridBagLayout pnlPanelLayout = new GridBagLayout();
				pnlPanelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1};
				pnlPanelLayout.rowHeights = new int[] {7, 7, 7, 7, 7};
				pnlPanelLayout.columnWeights = new double[] {0.1};
				pnlPanelLayout.columnWidths = new int[] {7};
				pnlPanel.setLayout(pnlPanelLayout);
				pnlPanel.setPreferredSize(new java.awt.Dimension(542, 327));
				{
					lblConnectedClients = new JLabel();
					pnlPanel.add(lblConnectedClients, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(9, 5, 0, 0), 0, 0));
					lblConnectedClients.setText(AppInternationalization.getString("ConnectedClients_label"));
					lblConnectedClients.setName("lblClientesConectados");
					lblConnectedClients.setPreferredSize(new java.awt.Dimension(145, 16));
				}
				{
					scpPanelLog = new JScrollPane();
					pnlPanel.add(scpPanelLog, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
					scpPanelLog.setPreferredSize(new java.awt.Dimension(541, 241));
					scpPanelLog.setMinimumSize(new java.awt.Dimension(346, 155));
					{
						txtLog = new JTextArea();
						scpPanelLog.setViewportView(txtLog);
						txtLog.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
						txtLog.setEditable(false);
						txtLog.setFont(new java.awt.Font("Tahoma",0,12));
					}
				}
				{
					lblStatusBar = new JLabel();
					pnlPanel.add(lblStatusBar, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 5, 7, 0), 0, 0));
					lblStatusBar.setText(AppInternationalization.getString("StatusBar_Label"));
					lblStatusBar.setName("lblBarraEstado");
					lblStatusBar.setPreferredSize(new java.awt.Dimension(288, 14));
				}
				{
					btnDisconnect = new JButton();
					pnlPanel.add(btnDisconnect, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
					btnDisconnect.setText(AppInternationalization.getString("btn_Disconnect_Text"));
					btnDisconnect.setPreferredSize(new java.awt.Dimension(116, 30));
					btnDisconnect.setEnabled(false);
					btnDisconnect.setName("btnDesconectar");
					btnDisconnect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnDisconnectActionPerformed(evt);
						}
					});
				}
				{
					btnConnect = new JButton();
					pnlPanel.add(btnConnect, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 109), 0, 0));
					btnConnect.setText(AppInternationalization.getString("btn_Connect_Text"));
					btnConnect.setPreferredSize(new java.awt.Dimension(110, 30));
					btnConnect.setName("btnConectar");
					btnConnect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnConnecActionPerformed(evt);
						}
					});
				}
				{
					lblConfigBD = new JLabel();
					pnlPanel.add(lblConfigBD, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 7, 5), 0, 0));
					lblConfigBD.setText(AppInternationalization.getString("DB_Main_Label") + "IP XXX.XXX.XXX.XXX, puerto XXXXX");
					lblConfigBD.setHorizontalAlignment(SwingConstants.TRAILING);
					lblConfigBD.setName("lblConfigBD");
					lblConfigBD.setPreferredSize(new java.awt.Dimension(267, 210));
				}
				{
					toolbar = new JToolBar();
					pnlPanel.add(toolbar, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					panelLogo = new JPanel();
					pnlPanel.add(panelLogo, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				}
			}
			this.addWindowListener(new WindowAdapter() { 
				public void windowClosing(WindowEvent evt) {    
					thisWindowClosing(evt);
				}
			});
			{
				mnbMenus = new JMenuBar();
				setJMenuBar(mnbMenus);
				{
					mnuFile = new JMenu();
					mnbMenus.add(mnuFile);
					mnuFile.setText(AppInternationalization.getString("FileMenu_Label"));
					{
						mniConnect = new JMenuItem();
						mnuFile.add(mniConnect);
						mniConnect.setText(AppInternationalization.getString("mniConnect_Label"));
						mniConnect.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								mniConnectActionPerformed(evt);
							}
						});
					}
					{
						mniDisconnect = new JMenuItem();
						mnuFile.add(mniDisconnect);
						mniDisconnect.setText(AppInternationalization.getString("mniDisconnect_Label"));
						mniDisconnect.setEnabled(false);
						mniDisconnect.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								mniDisconnectActionPerformed(evt);
							}
						});
					}
					{
						sepSeparator = new JSeparator();
						mnuFile.add(sepSeparator);
					}
					{
						mniExit = new JMenuItem();
						mnuFile.add(mniExit);
						mniExit.setText(AppInternationalization.getString("mniExit_Label"));
						mniExit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								mniExitActionPerformed(evt);
							}
						});
					}
				}
				{
					mnuOption = new JMenu();
					mnbMenus.add(mnuOption);
					mnuOption.setText(AppInternationalization.getString("mnuOptions_Label"));
					{
						mniConfigure = new JMenuItem();
						mnuOption.add(mniConfigure);
						mniConfigure.setText(AppInternationalization.getString("mniConfigure_Label"));
						mniConfigure.setName("mniConfigurar");
						mniConfigure.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								mniConfigureActionPerformed(evt);
							}
						});
					}
				}
				{
					mnuHelp = new JMenu();
					mnbMenus.add(mnuHelp);
					mnuHelp.setText(AppInternationalization.getString("mnuHelp_Label"));
					{
						mniAcercaDe = new JMenuItem();
						mnuHelp.add(mniAcercaDe);
						mniAcercaDe.setText(AppInternationalization.getString("mniAbout_Label"));
						mniAcercaDe.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								mniAboutActionPerformed(evt);
							}
						});
					}
				}
			}
			pack();
			this.setSize(506, 409);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//$hide>>$
	
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
	
	private void frmAboutClosewindow(EventObject evt) {
		frmAbout.dispose();
	}
	
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

	private void frmConfigureCloseWindow(EventObject evt) {
		configuration = frmConfiguration.getConfiguration();
		updateState();
		frmConfiguration.dispose();
	}
	
	private void btnConnecActionPerformed(ActionEvent evt) {
		activateServer();
	}
	
	private void btnDisconnectActionPerformed(ActionEvent evt) {
		confirmDisableServer();
	}

	private void thisWindowClosing(WindowEvent evt) {
		closeServer();
	}
	
	private void mniConnectActionPerformed(ActionEvent evt) {
		activateServer();
	}
	
	private void mniDisconnectActionPerformed(ActionEvent evt) {
		disableServer();
	}

	private void mniExitActionPerformed(ActionEvent evt) {
		closeServer();
	}
	
	private void updateState() {
		if(controller != null && controller.isServerActivate()) {
			lblStatusBar.setText(AppInternationalization.getString("StatusBarConnect_Label") + controller.getIPServidor() + ":" + String.valueOf(configuration.getServerPort()) + ").");
		} else {
			lblStatusBar.setText(AppInternationalization.getString("StatusBar_Label") + "(" + controller.getIPServidor() + ":" + String.valueOf(configuration.getServerPort()) + ").");
		}
		lblConfigBD.setText(AppInternationalization.getString("DB_Main_Label") + configuration.getDBIp() + ":" + String.valueOf(configuration.getDBPort()));
		}
	
	
	private boolean activateServer() {
		ok = false;
		// Invoke a new thread in order to show the panel with the loading
		// spinner
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
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
			btnConnect.setEnabled(false);
			mniConnect.setEnabled(false);
			mniConfigure.setEnabled(false);
			btnDisconnect.setEnabled(true);
			mniDisconnect.setEnabled(true);
			updateState();
			ok = true;
			glassPane.stop();
		} catch(SQLException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(MalformedURLException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(UnknownHostException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(NotBoundException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(RemoteException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
			disableServer();
		} catch (InstantiationException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
		} catch (IllegalAccessException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
		} catch (ClassNotFoundException e) {
			glassPane.stop();
			putMessage("Error: " + e.getLocalizedMessage());
		}		
	}   
	
	private boolean disableServer() {
		boolean ok;
		
		ok = false;
		try {
			// Disconnect clients disconnect and stop the server 
			controller.stopServer(configuration);

			btnDisconnect.setEnabled(false);
			mniDisconnect.setEnabled(false);
			btnConnect.setEnabled(true);
			mniConnect.setEnabled(true);
			mniConfigure.setEnabled(true);
			updateState();
			ok = true;
		} catch(SQLException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(RemoteException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(MalformedURLException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(UnknownHostException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		}
		
		return ok;
	}
	
	private void confirmDisableServer() {
		if(controller.isServerActivate()) {
			if(controller.getNumberConnectedClients() > 0) {
				if(JOptionPane.showConfirmDialog(this, AppInternationalization.getString("CloseServer_Message"), AppInternationalization.getString("Warning_Text"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
//				if(Dialogos.mostrarDialogoPregunta(this, "Aviso", "Si desconecta el servidor front-end se perderá la conexión con los clientes.\n¿Realmente quiere desconectarlo?")) {
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
			lblConnectedClients.setText(clientsNumber + " cliente conectado.");
		} else {
			lblConnectedClients.setText(clientsNumber + " clientes conectados.");
		}
	}
	
	public int getClientesEscuchando() {
		return clientsNumber;
	}
	//$hide<<$
	
}
