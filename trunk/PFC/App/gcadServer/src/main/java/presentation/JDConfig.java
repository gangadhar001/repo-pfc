package presentation;

import internationalization.AppInternationalization;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.EventListenerList;

import org.jdesktop.application.Application;

import presentation.auxiliary.CloseWindowListener;
import presentation.auxiliary.Validation;
import resources.IPValidator;
import resources.NotEmptyValidator;
import resources.PortValidator;

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
public class JDConfig extends javax.swing.JDialog {

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

	private JFrame frame;

	public JDConfig(JFrame frame) {
		super(frame);
		this.frame = frame;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.setTitle(AppInternationalization.getString("JFConfig_Title"));
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					thisWindowClosing(evt);
				}
			});
			{
				pnlPanel = new JPanel();
				getContentPane().add(pnlPanel, BorderLayout.CENTER);
				pnlPanel.setLayout(null);
				pnlPanel.setPreferredSize(new java.awt.Dimension(275, 313));
				{
					btnOK = new JButton();
					pnlPanel.add(btnOK);
					btnOK.setText(AppInternationalization.getString("btn_OK_Text"));
					btnOK.setName("btnOK");
					btnOK.setDefaultCapable(true);
					btnOK.setBounds(108, 277, 75, 25);
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
					btnCancel.setText(AppInternationalization.getString("btn_Cancel_Text"));
					btnCancel.setName("btnCancel");
					btnCancel.setBounds(189, 277, 75, 25);
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnCancelActionPerformed(evt);
						}
					});
				}
				{
					pnlDB = new JPanel();
					pnlPanel.add(pnlDB);
					pnlDB.setBorder(BorderFactory.createTitledBorder(AppInternationalization.getString("DB_Group_Text")));
					pnlDB.setLayout(null);
					pnlDB.setBounds(10, 10, 255, 185);
					{
						txtDBPort = new JTextField();
						pnlDB.add(txtDBPort);
						txtDBPort.setName("txtDBPort");
						txtDBPort.setBounds(100, 51, 108, 20);
						txtDBPort.setSize(144, 20);
						txtDBPort.setInputVerifier(new PortValidator(frame, txtDBPort, AppInternationalization.getString("ValidatePort") + "[1 - 65535]"));
					}
					{
						txtDBIP = new JTextField();
						pnlDB.add(txtDBIP);
						txtDBIP.setName("txtDBIP");
						txtDBIP.setBounds(100, 22, 144, 20);
						txtDBIP.setInputVerifier(new IPValidator(frame, txtDBIP, AppInternationalization.getString("ValidateIP")));
					}
					{
						lblDBPort = new JLabel();
						pnlDB.add(lblDBPort);
						lblDBPort.setText(AppInternationalization.getString("DBPort_Label"));
						lblDBPort.setBounds(12, 54, 68, 14);
					}
					{
						lblDBIP = new JLabel();
						pnlDB.add(lblDBIP);
						lblDBIP.setText(AppInternationalization.getString("DBIP_Label"));
						lblDBIP.setBounds(12, 25, 68, 14);
					}
					{
						lblSchema = new JLabel();
						pnlDB.add(lblSchema);
						lblSchema.setText(AppInternationalization.getString("Schema_Label"));
						lblSchema.setBounds(12, 88, 68, 14);
					}
					{
						lblUserName = new JLabel();
						pnlDB.add(lblUserName);
						lblUserName.setText(AppInternationalization.getString("Username_Label"));
						lblUserName.setBounds(12, 119, 78, 14);
					}
					{
						txtSchema = new JTextField();
						pnlDB.add(txtSchema);
						txtSchema.setName("txtSchema");
						txtSchema.setBounds(100, 85, 108, 20);
						txtSchema.setSize(144, 20);
						txtSchema.setInputVerifier(new NotEmptyValidator(this, txtSchema, AppInternationalization.getString("ValidateEmpty")));
					}
					{
						txtUsername = new JTextField();
						pnlDB.add(txtUsername);
						txtUsername.setName("txtUsername");
						txtUsername.setBounds(100, 116, 108, 20);
						txtUsername.setSize(144, 20);
						txtUsername.setInputVerifier(new NotEmptyValidator(this, txtUsername, AppInternationalization.getString("ValidateEmpty")));
					}
					{
						lblPassword = new JLabel();
						pnlDB.add(lblPassword);
						lblPassword.setText(AppInternationalization.getString("Password_Label"));
						lblPassword.setBounds(12, 153, 74, 14);
					}
					{
						txtPassword = new JPasswordField();
						pnlDB.add(txtPassword);
						txtPassword.setName("txtPassword");
						txtPassword.setBounds(100, 150, 108, 20);
						txtPassword.setSize(144, 20);
						txtPassword.setInputVerifier(new NotEmptyValidator(this, txtPassword, AppInternationalization.getString("ValidateEmpty")));
					}
				}
				{
					pnlServer = new JPanel();
					pnlPanel.add(pnlServer);
					pnlServer.setBorder(BorderFactory.createTitledBorder(AppInternationalization.getString("Server_Group_Text")));
					pnlServer.setLayout(null);
					pnlServer.setFont(new java.awt.Font("Tahoma",1,11));
					pnlServer.setBounds(10, 206, 255, 55);
					{
						txtServerPort = new JTextField();
						pnlServer.add(txtServerPort);
						txtServerPort.setBounds(100, 20, 108, 20);
						txtServerPort.setName("txtServerPort");
						txtServerPort.setSize(144, 20);
						txtServerPort.setInputVerifier(new PortValidator(frame, txtServerPort, AppInternationalization.getString("loginValidatePort") + "[1 - 65535]"));
					}
					{
						lblServerPort = new JLabel();
						pnlServer.add(lblServerPort);
						lblServerPort.setText(AppInternationalization.getString("Server_Port_Label"));
						lblServerPort.setBounds(11, 23, 97, 14);
					}
				}
			}
			setConfiguration(new ServerConfiguration());
			listenerList = new EventListenerList();
			
			pack();
			this.setSize(291, 353);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void thisWindowClosing(WindowEvent evt) {
		closeWindow();
	}	

	private void btnAceptarActionPerformed(ActionEvent evt) {	
		boolean valid = true;
		try {
			Validation.comprobarDireccionIP(txtDBIP.getText().trim());
			Validation.comprobarPuerto(txtDBPort.getText().trim());
			Validation.comprobarPuerto(txtServerPort.getText().trim());
		} catch(InvalidIPException ex) {
			// TODO
//			Dialogos.mostrarDialogoError(this, "Error", "La dirección IP de la base de datos principal tiene un formato incorrecto.");
			txtDBIP.selectAll();
			txtDBIP.grabFocus();
			valid = false;
		} catch(InvalidPortException ex) {
//			Dialogos.mostrarDialogoError(this, "Error", "El puerto de la base de datos principal debe ser un número entre " + String.valueOf(Validation.PUERTO_MINIMO) + " y " + String.valueOf(Validation.PUERTO_MAXIMO) + ".");
			txtDBPort.selectAll();
			txtDBPort.grabFocus();
			valid = false;
		}
		if (valid && txtSchema.getText().length() == 0)
			//ERROR
			valid = false;
		if (valid && txtUsername.getText().length() == 0)
			//ERROR
			valid = false;
		if (valid && new String(txtPassword.getPassword()).length() == 0)
			//ERROR
			valid = false;
		if (valid) {
			try {
				configuration.setDBIp(txtDBIP.getText().trim());
				configuration.setDBPort(Integer.parseInt(txtDBPort.getText().trim()));
				configuration.setDBSchema(txtSchema.getText().trim());
				configuration.setDBUser(txtUsername.getText().trim());
				configuration.setDBPassword(new String(txtPassword.getPassword()));					
				configuration.setServerPort(Integer.parseInt(txtServerPort.getText().trim()));
				closeWindow();		
			} catch(NumberFormatException e) { }
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
		this.configuration = configuration;
		txtDBIP.setText(configuration.getDBIp());
		txtDBPort.setText(String.valueOf(configuration.getDBPort()));
		txtSchema.setText(configuration.getDBSchema());
		txtUsername.setText(configuration.getDBUser());
		txtPassword.setText(configuration.getDBPassword());
		
		txtServerPort.setText(String.valueOf(configuration.getServerPort()));
	}
	
	public void addWindowCloseListener(CloseWindowListener listener) {
		listenerList.add(CloseWindowListener.class, listener);
	}

	public void removeWindowCloseListener(CloseWindowListener listener) {
		listenerList.remove(CloseWindowListener.class, listener);
	}
}
