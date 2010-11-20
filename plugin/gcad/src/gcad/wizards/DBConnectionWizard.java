package gcad.wizards;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import gcad.communications.DBConfiguration;
import gcad.communications.DBConnection;
import gcad.communications.DBConnectionManager;
import gcad.internationalization.BundleInternationalization;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;

public class DBConnectionWizard extends Wizard {
	
	private DBConnectionWizardPage page;

	public DBConnectionWizard () {
		super();
		setWindowTitle(BundleInternationalization.getString("DBConnectionWizard"));
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		super.addPages();
		page = new DBConnectionWizardPage(BundleInternationalization.getString("DBConnectionPageTitle"));
		addPage(page);
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String IPText = page.getIPText();
		final String portText = page.getPortText();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, IPText, portText);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * The worker method. It will open the database connection
	 */

	private void doFinish(final IProgressMonitor monitor, final String IP, final String port) throws CoreException {		
		monitor.beginTask(BundleInternationalization.getString("MonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("MonitorMessage"));

		DBConfiguration configuration = new DBConfiguration(IP, Integer.parseInt(port));
		DBConnection database;
		monitor.worked(10);
		// TODO: Cerramos las conexiones que pudiera haber abiertas
		// (ignoramos los errores que pudieran producirse)
		try {
			DBConnectionManager.closeConnections();
			monitor.worked(10);
		} catch(SQLException e) { }
		DBConnectionManager.clearConnections();
		monitor.worked(10);
		
		// Creamos una conexión con la base de datos
		try {
			database = new DBConnection();
			// Se lee la IP y el puerto
			database.getAgent().setIp(configuration.getDBip());
			database.getAgent().setPort(configuration.getDBport());
			database.open();
			DBConnectionManager.putConnection(database);
		} catch(SQLException e) {			
			MessageDialog.openError(getShell(), "Error", BundleInternationalization.getString("ErrorMessage.SQLException"));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
