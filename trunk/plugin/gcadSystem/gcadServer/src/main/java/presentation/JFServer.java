package presentation;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import communication.ServerConfiguration;

import internationalization.BundleInternationalization;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import model.business.control.ServerController;

import presentation.auxiliary.Dialogos;
import presentation.auxiliary.IVentanaEstado;
import presentation.auxiliary.CloseWindowListener;
import presentation.auxiliary.IWindowState;


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
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			Dialogos.showErrorDialog(this, "Error", e.getMessage());
		}
	}
	
	private static final long serialVersionUID = -113838536647924014L;
	
	private ServerController controller;
	private ServerConfiguration configuration;
	private JFConfig frmConfiguration;
	private JFAbout frmAbout;
	private JLabel lblConfigBD;
	private JMenuItem mniDisconnect;
	private JMenuItem mniConnect;
	private JPanel pnlPanel;
	private JScrollPane scpPanelLog;
	private JLabel lblStatusBar;
	private JButton btnDisconnect;
	private JLabel lblConnectedClients;
	private JButton btnConnect;
	private JButton btnExit;
	private JMenuBar mnbMenus;
	private JTextArea txtLog;
	private JMenuItem mniAcercaDe;
	private JMenuItem mniConfigure;
	private JMenuItem mniExit;
	private JSeparator sepSeparator;
	private JMenu mnuFile;
	private JMenu mnuHelp;
	private JMenu mnuOption;

	private int clientsNumber;
	
	public JFServer(ServerController controlador) {
		super();
		initGUI();
		this.controller = controlador;
		configuration = new ServerConfiguration();
		updateState();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.setTitle(BundleInternationalization.getString("JFServer_Title"));
			this.setPreferredSize(new java.awt.Dimension(577, 409));
			this.setMinimumSize(new java.awt.Dimension(500, 300));
			setLocationRelativeTo(null);
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
					mnuFile.setText(BundleInternationalization.getString("FileMenu_Label"));
					{
						mniConnect = new JMenuItem();
						mnuFile.add(mniConnect);
						mniConnect.setText(BundleInternationalization.getString("mniConnect_Label"));
						mniConnect.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								mniConnectActionPerformed(evt);
							}
						});
					}
					{
						mniDisconnect = new JMenuItem();
						mnuFile.add(mniDisconnect);
						mniDisconnect.setText(BundleInternationalization.getString("mniDisconnect_Label"));
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
						mniExit.setText(BundleInternationalization.getString("mniExit_Label"));
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
					mnuOption.setText(BundleInternationalization.getString("mnuOptions_Label"));
					{
						mniConfigure = new JMenuItem();
						mnuOption.add(mniConfigure);
						mniConfigure.setText(BundleInternationalization.getString("mniConfigure_Label"));
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
					mnuHelp.setText(BundleInternationalization.getString("mnuHelp_Label"));
					{
						mniAcercaDe = new JMenuItem();
						mnuHelp.add(mniAcercaDe);
						mniAcercaDe.setText(BundleInternationalization.getString("mniAbout_Label"));
						mniAcercaDe.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								mniAboutActionPerformed(evt);
							}
						});
					}
				}
			}
			{
				pnlPanel = new JPanel();
				AnchorLayout pnlPanelLayout = new AnchorLayout();
				getContentPane().add(pnlPanel, BorderLayout.CENTER);
				pnlPanel.setLayout(pnlPanelLayout);
				pnlPanel.setPreferredSize(new java.awt.Dimension(542, 327));
				{
					lblConnectedClients = new JLabel();
					pnlPanel.add(lblConnectedClients, new AnchorConstraint(870, 277, 915, 18, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					lblConnectedClients.setText(BundleInternationalization.getString("ConnectedClients_label"));
					lblConnectedClients.setName("lblClientesConectados");
					lblConnectedClients.setPreferredSize(new java.awt.Dimension(145, 16));
				}
				{
					scpPanelLog = new JScrollPane();
					pnlPanel.add(scpPanelLog, new AnchorConstraint(158, 983, 847, 18, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
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
					pnlPanel.add(lblStatusBar, new AnchorConstraint(930, 532, 970, 18, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					lblStatusBar.setText(BundleInternationalization.getString("StatusBar_Label"));
					lblStatusBar.setName("lblBarraEstado");
					lblStatusBar.setPreferredSize(new java.awt.Dimension(288, 14));
				}
				{
					btnDisconnect = new JButton();
					pnlPanel.add(btnDisconnect, new AnchorConstraint(38, 444, 124, 237, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					btnDisconnect.setText(BundleInternationalization.getString("btn_Disconnect_Text"));
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
					pnlPanel.add(btnConnect, new AnchorConstraint(38, 214, 124, 18, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					btnConnect.setText(BundleInternationalization.getString("btn_Connect_Text"));
					btnConnect.setPreferredSize(new java.awt.Dimension(110, 30));
					btnConnect.setName("btnConectar");
					btnConnect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnConnecActionPerformed(evt);
						}
					});
				}
				{
					btnExit = new JButton();
					pnlPanel.add(btnExit, new AnchorConstraint(38, 983, 124, 870, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					btnExit.setText(BundleInternationalization.getString("btn_Exit_Text"));
					btnExit.setPreferredSize(new java.awt.Dimension(63, 30));
					btnExit.setName("btnSalir");
					btnExit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnExitActionPerformed(evt);
						}
					});
				}
				{
					lblConfigBD = new JLabel();
					pnlPanel.add(lblConfigBD, new AnchorConstraint(930, 983, 970, 507, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					lblConfigBD.setText(BundleInternationalization.getString("DB_Main_Label") + "IP XXX.XXX.XXX.XXX, puerto XXXXX");
					lblConfigBD.setHorizontalAlignment(SwingConstants.TRAILING);
					lblConfigBD.setName("lblConfigBD");
					lblConfigBD.setPreferredSize(new java.awt.Dimension(267, 210));
				}
			}
			pack();
		} catch(Exception e) {
			Dialogos.showErrorDialog(this, "Error", e.getMessage());
		}
	}
	
	//$hide>>$
	
	private void mniAboutActionPerformed(ActionEvent evt) {
		frmAbout = new JFAbout();
		frmAbout.addVentanaCerradaListener(new CloseWindowListener() {
			public void closeWindow(EventObject evt) {    
				frmAboutClosewindow(evt);
			}
		});
		frmAbout.setLocationRelativeTo(this);
		this.setEnabled(false);
		frmAbout.setVisible(true);
	}
	
	private void frmAboutClosewindow(EventObject evt) {
		// Reactivamos la ventana 
		setEnabled(true);
		frmAbout.setVisible(false);
		frmAbout.dispose();
		frmAbout = null;
	}
	
	private void mniConfigureActionPerformed(ActionEvent evt) {
		// Creamos la ventana de configuración
		frmConfiguration = new JFConfig();
		frmConfiguration.addWindowCloseListener(new CloseWindowListener() {
			public void closeWindow(EventObject evt) {    
				frmConfigureCloseWindow(evt);
			}
		});

		setEnabled(false);
		frmConfiguration.setConfiguration(configuration);
		frmConfiguration.setLocationRelativeTo(this);
		frmConfiguration.setVisible(true);
	}

	private void frmConfigureCloseWindow(EventObject evt) {
		setEnabled(true);
		frmConfiguration.setVisible(false);
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
	
	private void btnExitActionPerformed(ActionEvent evt) {
		closeServer();
	}
	
	private void updateState() {
		if(controller != null && controller.isServerActivate()) {
			lblStatusBar.setText(BundleInternationalization.getString("StatusBarConnect_Label") + controller.getIPServidor() + ":" + String.valueOf(configuration.getServerPort()) + ").");
		} else {
			lblStatusBar.setText(BundleInternationalization.getString("StatusBar_Label") + "(" + controller.getIPServidor() + ":" + String.valueOf(configuration.getServerPort()) + ").");
		}
		lblConfigBD.setText(BundleInternationalization.getString("DB_Main_Label") + configuration.getDBIp() + ":" + String.valueOf(configuration.getDBPort()));
		}
	
	
	private boolean activateServer() {
		boolean ok;
		
		ok = false;
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
		} catch(SQLException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(MalformedURLException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(UnknownHostException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(NotBoundException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		} catch(RemoteException e) {
			putMessage("Error: " + e.getLocalizedMessage());
			// Si se produce un fallo de RMI al conectar el
			// servidor, lo desconectamos para hacer el "unexport"
			// y que se pueda conectar de nuevo más tarde
			disableServer();
		} catch (InstantiationException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		} catch (IllegalAccessException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		} catch (ClassNotFoundException e) {
			putMessage("Error: " + e.getLocalizedMessage());
		}
		
		return ok;
	}
	
	private boolean disableServer() {
		boolean ok;
		
		ok = false;
		try {
			// Desconectamos a los clientes, detenemos el servidor frontend y la conexión con el de respaldo
			controller.stopServer(configuration);
			// Cambiamos el estado de la ventana
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
//			TODO: if(controlador.getNumeroClientesConectados() > 0) {
//				if(Dialogos.mostrarDialogoPregunta(this, "Aviso", "Si desconecta el servidor front-end se perderá la conexión con los clientes.\n¿Realmente quiere desconectarlo?")) {
//					desactivarServidor();
//				}
//			} else {
				disableServer();
			}
		}
	
	
	private void closeServer() {
		boolean exit;
		
		exit = false;
		if(controller.isServerActivate()) {
			if(Dialogos.showQuestionDialog(this, BundleInternationalization.getString("Warning_Text"), BundleInternationalization.getString("CloseServer_Message"))) {
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
