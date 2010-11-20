package gcad;

import java.sql.SQLException;

import gcad.communications.DBConfiguration;
import gcad.communications.DBConnection;
import gcad.communications.DBConnectionManager;
import gcad.wizards.DBConnectionWizard;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "gcad";

	// The shared instance
	private static Activator plugin;

	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		// TODO: cambiar la conexion con la base de datos al wizard correspondiente
		/*DBConfiguration configuration = new DBConfiguration();
		DBConnection database;
		// Cerramos las conexiones que pudiera haber abiertas
		// (ignoramos los errores que pudieran producirse)
		try {
			DBConnectionManager.closeConnections();
		} catch(SQLException e) {
		}
		DBConnectionManager.clearConnections();
		
		// Creamos una conexión con la base de datos
		try {
			database = new DBConnection();
			database.getAgent().setIp(configuration.getDBip());
			database.getAgent().setPort(configuration.getDBport());
			database.open();
		} catch(SQLException e) {
			throw new SQLException("No se puede establecer una conexión con el servidor de la base de datos principal (IP " + configuration.getDBip() + ", puerto " + String.valueOf(configuration.getDBport()) + ").");
		}
		DBConnectionManager.putConnection(database);*/
		
		
		
		
		// TODO: añadir el listener de la perspectiva
		/*IWorkbenchWindow a = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IPerspectiveDescriptor b = a.getActivePage().getPerspective();
		System.out.println(b);*/
			
		/*	@Override
			public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId) {
			}
			
			@Override
			public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
				// TODO: cuando se activa la perspectiva de KM y no se ha conectado a ninguna base de datos,
				// se muestra ese wizard
				if (perspective.getId().equals("gcad.perspective.KMPerspective")){
					if (!DBConnectionManager.thereAreConnections()) {
						DBConnectionWizard bdwizard = new DBConnectionWizard();
						WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), bdwizard);
				        dialog.create();
				        dialog.open();
					}
				}
			}
		});*/
		
		super.start(context);
		plugin = this;
			
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		DBConnectionManager.closeConnections();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
