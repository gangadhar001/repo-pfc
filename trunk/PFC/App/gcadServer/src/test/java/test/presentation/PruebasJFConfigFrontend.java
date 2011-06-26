package test.presentation;

import javax.swing.JTextField;

import org.uispec4j.Button;
import org.uispec4j.PasswordField;
import org.uispec4j.TextBox;
import org.uispec4j.Window;

import presentation.JDConfig;

import communication.ServerConfiguration;

/**
 * Pruebas de la ventana de configuración del servidor front-end. 
 */
public class PruebasJFConfigFrontend extends org.uispec4j.UISpecTestCase {

	private JDConfig ventana;
	private Window winVentana;
	private Button btnAceptar;
	private Button btnCancelar;
	private TextBox txtIPBDPrincipal;
	private TextBox txtPuertoBDPrincipal;
	private TextBox txtSchema;
	private TextBox txtUser;
	private PasswordField txtPassword;
	private TextBox txtPuertoFrontend;
	private JTextField jtxtIPBDPrincipal;
	private JTextField jtxtPuertoBDPrincipal;	
	
	public void setUp() {
		try {
			// Creamos la ventana de configuración
			ventana = new JDConfig(null);
			// Obtenemos los componentes de la ventana
			winVentana = new Window(ventana);
			btnAceptar = winVentana.getButton("btnOK");
			btnCancelar = winVentana.getButton("btnCancel");
			txtIPBDPrincipal = winVentana.getTextBox("txtDBIP");
			txtPuertoBDPrincipal = winVentana.getTextBox("txtDBPort");
			txtPuertoFrontend = winVentana.getTextBox("txtServerPort");
			txtSchema = winVentana.getTextBox("txtSchema");
			txtUser = winVentana.getTextBox("txtUsername");
			txtPassword = winVentana.getPasswordField("txtPassword");		
			
			jtxtIPBDPrincipal = (JTextField)txtIPBDPrincipal.getAwtComponent();
			jtxtPuertoBDPrincipal = (JTextField)txtPuertoBDPrincipal.getAwtComponent();			
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void tearDown() {
		try {
			// Liberamos los recursos usados por la ventana
			ventana.dispose();
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	/** Pruebas con configuraciones no válidas */
	public void testConfiguracionesInvalidas() {
		ServerConfiguration configOriginal;

		try {
			// Obtenemos la configuración por defecto
			configOriginal = new ServerConfiguration();
			// Ponemos IPs de BDs incorrectas para ver que no se aceptan
			txtIPBDPrincipal.setText("300.0.0.300");
			btnAceptar.click();
			assertEquals(configOriginal, ventana.getConfiguration());
			assertTrue(jtxtIPBDPrincipal.getSelectionStart() == 0 && jtxtIPBDPrincipal.getSelectionEnd() == "300.0.0.300".length());
			txtIPBDPrincipal.setText("127.0.0.1");
			// Ponemos puertos de BD incorrectos para ver que no se aceptan
			txtPuertoBDPrincipal.setText("1234567");
			btnAceptar.click();
			assertEquals(configOriginal, ventana.getConfiguration());
			assertTrue(jtxtPuertoBDPrincipal.getSelectionStart() == 0 && jtxtPuertoBDPrincipal.getSelectionEnd() == "1234567".length());
			txtPuertoBDPrincipal.setText("3306");		
			// Ponemos puertos de escucha inválidos para ver que no se aceptan
			txtPuertoFrontend.setText("1.5");
			btnAceptar.click();
			assertEquals(configOriginal, ventana.getConfiguration());
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	/** Pruebas con configuraciones válidas */
	public void testConfiguracionesValidas() {
		ServerConfiguration configOriginal;
		
		try {
			// Obtenemos la configuración por defecto
			configOriginal = new ServerConfiguration();
			// Ponemos valores válidos pero cancelamos la ventana
			txtIPBDPrincipal.setText("192.168.240.120");
			txtPuertoBDPrincipal.setText("8001");			
			txtPuertoFrontend.setText("4500");
			txtSchema.setText("bdgcad");
			txtUser.setText("gcad");
			txtPassword.setPassword("gcad");
			btnCancelar.click();
			assertEquals(configOriginal, ventana.getConfiguration());			
			// Ponemos valores válidos y aceptamos la ventana
			txtIPBDPrincipal.setText("10.1.2.3");
			txtPuertoBDPrincipal.setText("1829");
			txtSchema.setText("bdgcad");
			txtUser.setText("gcad");
			txtPassword.setPassword("gcad");
			txtPuertoFrontend.setText("8001");
			btnAceptar.click();
			assertEquals(new ServerConfiguration("10.1.2.3", "bdgcad", "gcad", "gcad", 1829, 8001), ventana.getConfiguration());
			// Ponemos valores válidos con espacios y aceptamos la ventana
			txtIPBDPrincipal.setText("10.5.6.7  ");
			txtPuertoBDPrincipal.setText("  4190");			
			txtPuertoFrontend.setText("  57340  ");
			txtSchema.setText(" bdgcad ");
			txtUser.setText("gcad ");
			txtPassword.setPassword("gcad");
			btnAceptar.click();
			assertEquals(new ServerConfiguration("10.5.6.7", "bdgcad", "gcad", "gcad", 4190, 57340), ventana.getConfiguration());
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
}
