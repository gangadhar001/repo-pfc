package presentation;

import internationalization.BundleInternationalization;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.EventListenerList;

import presentation.auxiliary.CloseWindowListener;
import presentation.auxiliary.Dialogos;
import presentation.auxiliary.Validation;

import communication.ServerConfiguration;

import exceptions.InvalidIPException;
import exceptions.InvalidPortException;

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
 * UI Window used to configure server
 */
	public class JFConfig extends javax.swing.JFrame {
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			Dialogos.showErrorDialog(this, "Error", e.getMessage());
		}
	}

	private static final long serialVersionUID = -7821595354878872893L;
	
	private EventListenerList listenerList;
	private ServerConfiguration configuration;

	private JPanel pnlPanel;
	private JButton btnOK;
	private JButton btnCancel;
	private JPanel pnlServer;
	private JPanel pnlDB;
	private JTextField txtServerPort;
	private JTextField txtDBPort;
	private JPasswordField txtPassword;
	private JLabel lblPassword;
	private JTextField txtUsername;
	private JTextField txtSchema;
	private JLabel lblUserName;
	private JLabel lblSchema;
	private JTextField txtDBIP;
	private JLabel lblServerPort;
	private JLabel lblDBPort;
	private JLabel lblDBIP;

	public JFConfig() {
		super();
		initGUI();
		setConfiguration(new ServerConfiguration());
		listenerList = new EventListenerList();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.setTitle(BundleInternationalization.getString("JFConfig_Title"));
			this.setResizable(false);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					thisWindowClosing(evt);
				}
			});
			{
				pnlPanel = new JPanel();
				getContentPane().add(pnlPanel, BorderLayout.CENTER);
				pnlPanel.setLayout(null);
				pnlPanel.setPreferredSize(new java.awt.Dimension(231, 190));
				{
					btnOK = new JButton();
					pnlPanel.add(btnOK);
					btnOK.setText(BundleInternationalization.getString("btn_OK_Text"));
					btnOK.setName("btnOK");
					btnOK.setDefaultCapable(true);
					btnOK.setBounds(60, 285, 82, 30);
					getRootPane().setDefaultButton(btnOK);
					btnOK.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnAceptarActionPerformed(evt);
						}
					});
				}
				{
					btnCancel = new JButton();
					pnlPanel.add(btnCancel);
					btnCancel.setText(BundleInternationalization.getString("btn_Cancel_Text"));
					btnCancel.setName("btnCancel");
					btnCancel.setBounds(152, 285, 82, 30);
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnCancelActionPerformed(evt);
						}
					});
				}
				{
					pnlDB = new JPanel();
					pnlPanel.add(pnlDB);
					pnlDB.setBorder(BorderFactory.createTitledBorder(BundleInternationalization.getString("DB_Group_Text")));
					pnlDB.setLayout(null);
					pnlDB.setBounds(10, 10, 224, 185);
					{
						txtDBPort = new JTextField();
						pnlDB.add(txtDBPort);
						txtDBPort.setName("txtPuertoBDPrincipal");
						txtDBPort.setBounds(100, 51, 108, 20);
					}
					{
						txtDBIP = new JTextField();
						pnlDB.add(txtDBIP);
						txtDBIP.setName("txtIPBDPrincipal");
						txtDBIP.setBounds(100, 22, 108, 20);
					}
					{
						lblDBPort = new JLabel();
						pnlDB.add(lblDBPort);
						lblDBPort.setText(BundleInternationalization.getString("DBPort_Label"));
						lblDBPort.setBounds(12, 54, 68, 14);
					}
					{
						lblDBIP = new JLabel();
						pnlDB.add(lblDBIP);
						lblDBIP.setText(BundleInternationalization.getString("DBIP_Label"));
						lblDBIP.setBounds(12, 25, 68, 14);
					}
					{
						lblSchema = new JLabel();
						pnlDB.add(lblSchema);
						lblSchema.setText(BundleInternationalization.getString("Schema_Label"));
						lblSchema.setBounds(12, 88, 68, 14);
					}
					{
						lblUserName = new JLabel();
						pnlDB.add(lblUserName);
						lblUserName.setText(BundleInternationalization.getString("Username_Label"));
						lblUserName.setBounds(12, 119, 78, 14);
					}
					{
						txtSchema = new JTextField();
						pnlDB.add(txtSchema);
						txtSchema.setBounds(100, 85, 108, 20);
					}
					{
						txtUsername = new JTextField();
						pnlDB.add(txtUsername);
						txtUsername.setBounds(100, 116, 108, 20);
					}
					{
						lblPassword = new JLabel();
						pnlDB.add(lblPassword);
						lblPassword.setText(BundleInternationalization.getString("Password_Label"));
						lblPassword.setBounds(12, 153, 74, 14);
					}
					{
						txtPassword = new JPasswordField();
						pnlDB.add(txtPassword);
						txtPassword.setBounds(100, 150, 108, 20);
					}
				}
				{
					pnlServer = new JPanel();
					pnlPanel.add(pnlServer);
					pnlServer.setBorder(BorderFactory.createTitledBorder(BundleInternationalization.getString("Server_Group_Text")));
					pnlServer.setLayout(null);
					pnlServer.setFont(new java.awt.Font("Tahoma",1,11));
					pnlServer.setBounds(10, 206, 224, 55);
					{
						txtServerPort = new JTextField();
						pnlServer.add(txtServerPort);
						txtServerPort.setBounds(114, 20, 79, 20);
						txtServerPort.setName("txtPuertoFrontend");
					}
					{
						lblServerPort = new JLabel();
						pnlServer.add(lblServerPort);
						lblServerPort.setText(BundleInternationalization.getString("Server_Port_Label"));
						lblServerPort.setBounds(11, 23, 97, 14);
					}
				}
			}
			pack();
			this.setSize(250, 363);
		} catch(Exception e) {
			Dialogos.showErrorDialog(this, "Error", e.getMessage());
		}
	}
	
	//$hide>>$
	
	private void thisWindowClosing(WindowEvent evt) {
		closeWindow();
	}

	

	private void btnAceptarActionPerformed(ActionEvent evt) {
		boolean valid;
		
		// Check database configuration values
		valid = true;
		try {
			Validation.checkIPAddress(txtDBIP.getText().trim());
			Validation.checkPort(txtDBPort.getText().trim());
		} catch(InvalidIPException ex) {
			Dialogos.showErrorDialog(this, BundleInternationalization.getString(("Error")), ex.getLocalizedMessage());
			txtDBIP.selectAll();
			txtDBIP.grabFocus();
			valid = false;
		} catch(InvalidPortException ex) {
			Dialogos.showErrorDialog(this, BundleInternationalization.getString(("Error")), ex.getLocalizedMessage());
			txtDBPort.selectAll();
			txtDBPort.grabFocus();
			valid = false;
		}
		
		
		// Check server configuration
		if(valid) {
			try {
				Validation.checkPort(txtServerPort.getText().trim());
			} catch(InvalidPortException e) {
				Dialogos.showErrorDialog(this, BundleInternationalization.getString(("Error")), e.getLocalizedMessage());
				txtServerPort.selectAll();
				txtServerPort.grabFocus();
				valid = false;
			}
		}
		
		if(valid) {
			try {
				configuration.setDBIp(txtDBIP.getText().trim());
				configuration.setDBPort(Integer.parseInt(txtDBPort.getText().trim()));
				configuration.setDBSchema(txtSchema.getText().trim());
				configuration.setDBUser(txtUsername.getText().trim());
				configuration.setDBPassword(new String(txtPassword.getPassword()));				
				
				configuration.setServerPort(Integer.parseInt(txtServerPort.getText().trim()));
			} catch(NumberFormatException e) { }

			closeWindow();
		}
	}
	
	private void btnCancelActionPerformed(ActionEvent evt) {
		closeWindow();
	}
	
	private void closeWindow() {
		Object[] listeners;
		int i;
		
		// Notify that window closes
		listeners = listenerList.getListenerList();
		for(i = 0; i < listeners.length; i += 2) {
			if(listeners[i] == CloseWindowListener.class) {
				((CloseWindowListener)listeners[i + 1]).closeWindow(new EventObject(this));
			}
		}
	}

	/*** Public methods ***/
	public ServerConfiguration getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(ServerConfiguration configuration) {
		// Cambia la configuración aceptada y mostrada en la ventana
		this.configuration = configuration;
		txtDBIP.setText(configuration.getDBIp());
		txtDBPort.setText(String.valueOf(configuration.getDBPort()));
		
		txtServerPort.setText(String.valueOf(configuration.getServerPort()));
	}
	
	public void addWindowCloseListener(CloseWindowListener listener) {
		listenerList.add(CloseWindowListener.class, listener);
	}

	public void removeWindowCloseListener(CloseWindowListener listener) {
		listenerList.remove(CloseWindowListener.class, listener);
	}

	//$hide<<$

}
