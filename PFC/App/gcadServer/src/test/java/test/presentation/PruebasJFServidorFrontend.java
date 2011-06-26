package test.presentation;

import model.business.control.ServerController;

import org.uispec4j.Button;
import org.uispec4j.MenuItem;
import org.uispec4j.TextBox;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

import communication.ServerConfiguration;

import presentation.JFServer;


/**
 * Pruebas de la ventana principal del servidor front-end.
 */
public class PruebasJFServidorFrontend extends org.uispec4j.UISpecTestCase {

	private JFServer ventana;
	private ServerController controlador;
	private Window winVentana;
	private Button btnConectar;
	private Button btnDesconectar;
	private Button btnDisconnectToolbar;
	private Button btnConnectToolbar;
	private TextBox lblConfigBD;
	private MenuItem mniConectar;
	private MenuItem mniDesconectar;
	private MenuItem mniConfigurar;
	private MenuItem mniAcercaDe;
	private MenuItem mniSalir;

	public void setUp() {
		try {
			// Creamos el controlador y con él la ventana de estado
			controlador = new ServerController();
			ventana = controlador.getWindow();
			// Obtenemos los componentes de la ventana
			winVentana = new Window(ventana);
			btnConectar = winVentana.getButton("btnConnect");
			lblConfigBD = winVentana.getTextBox("lblConfigDB");
			btnDesconectar = winVentana.getButton("btnDisconnect");
			btnDisconnectToolbar = winVentana.getButton("disconnect");
			btnConnectToolbar = winVentana.getButton("connect");
			mniSalir = winVentana.getMenuBar().getMenu("Archivo").getSubMenu("Salir");
			mniConectar = winVentana.getMenuBar().getMenu("Archivo").getSubMenu("mniConnect");
			mniDesconectar = winVentana.getMenuBar().getMenu("Archivo").getSubMenu("mniDisconnect");
			mniAcercaDe = winVentana.getMenuBar().getMenu("Ayuda").getSubMenu("Acerca de");
			mniConfigurar = winVentana.getMenuBar().getMenu("Opciones").getSubMenu("Configurar");
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
	
	/** Pruebas de las acciones de conectar y desconectar */
	public void testConectarDesconectar() {
		try {
			// Comprobamos que el servidor está desactivado
			assertFalse(controlador.isServerActivate());
			assertTrue(btnConectar.isEnabled());
			assertTrue(mniConectar.isEnabled());
			assertTrue(mniConfigurar.isEnabled());
			assertFalse(btnDesconectar.isEnabled());
			assertFalse(mniDesconectar.isEnabled());
			// Activamos el servidor
			btnConectar.click();
			Thread.sleep(2000);
			assertTrue(controlador.isServerActivate());
			assertFalse(btnConectar.isEnabled());
			assertFalse(mniConectar.isEnabled());
			assertFalse(mniConfigurar.isEnabled());
			assertTrue(btnDesconectar.isEnabled());
			assertTrue(mniDesconectar.isEnabled());
			// Desactivamos el servidor
			btnDesconectar.click();
			assertFalse(controlador.isServerActivate());
			assertTrue(btnConectar.isEnabled());
			assertTrue(mniConectar.isEnabled());
			assertTrue(mniConfigurar.isEnabled());
			assertFalse(btnDesconectar.isEnabled());
			assertFalse(mniDesconectar.isEnabled());
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Activamos el servidor por menú
			mniConectar.click();
			Thread.sleep(2000);
			assertTrue(controlador.isServerActivate());
			assertFalse(btnConectar.isEnabled());
			assertFalse(mniConectar.isEnabled());
			assertFalse(mniConfigurar.isEnabled());
			assertTrue(btnDesconectar.isEnabled());
			assertTrue(mniDesconectar.isEnabled());
			// Desactivamos el servidor por menú
			mniDesconectar.click();
			assertFalse(controlador.isServerActivate());
			assertTrue(btnConectar.isEnabled());
			assertTrue(mniConectar.isEnabled());
			assertTrue(mniConfigurar.isEnabled());
			assertFalse(btnDesconectar.isEnabled());
			assertFalse(mniDesconectar.isEnabled());
			
			// Activamos el servidor por toolbar
			btnConnectToolbar.click();
			Thread.sleep(2000);
			assertTrue(controlador.isServerActivate());
			assertFalse(btnConectar.isEnabled());
			assertFalse(mniConectar.isEnabled());
			assertFalse(mniConfigurar.isEnabled());
			assertTrue(btnDesconectar.isEnabled());
			assertTrue(mniDesconectar.isEnabled());
			// Desactivamos el servidor por toolbar
			btnDisconnectToolbar.click();
			assertFalse(controlador.isServerActivate());
			assertTrue(btnConectar.isEnabled());
			assertTrue(mniConectar.isEnabled());
			assertTrue(mniConfigurar.isEnabled());
			assertFalse(btnDesconectar.isEnabled());
			assertFalse(mniDesconectar.isEnabled());
			
			// Activamos el servidor e intentamos salir pero
			// cancelamos el cuadro de diálogo de confirmación
			mniConectar.click();
			Thread.sleep(2000);
			WindowInterceptor.init(mniSalir.triggerClick())
		    .process(new WindowHandler() {
		    	public Trigger process(Window window) {
		    		return window.getButton("No").triggerClick();
		    	}
		    }).run();
			// Comprobamos que el servidor sigue activo
			assertTrue(controlador.isServerActivate());			
		} catch(Exception e) {
			fail(e.toString());
		}
		
	}
	
	/** Pruebas de la ventana de configuración */
	public void testConfigurar() {
		String ip;
		
		try {
			// Abrimos la ventana de configuración desde menú y cambiamos un puerto
			WindowInterceptor.init(mniConfigurar.triggerClick()).process(new WindowHandler(){
		        public Trigger process(Window window) {
		        	window.getTextBox("txtDBPort").setText("8888");
		            return window.getButton("OK").triggerClick();
		          }
		        }).run();
			
			// Comprobamos que el cambio se ha reflejado en la ventana
			ip = (new ServerConfiguration()).getDBIp();
			assertEquals(lblConfigBD.getText(), "BD Principal en " + ip + ":8888");
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	/** Pruebas de la ventana 'Acerca de' */
	public void testAcercaDe() {		
		try {
			WindowInterceptor.init(mniAcercaDe.triggerClick()).process(new WindowHandler(){
		        public Trigger process(Window window) {
		            return window.getButton("Aceptar").triggerClick();
		          }
		        }).run();			
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
}
