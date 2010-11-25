package gcad.wizards;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import gcad.communications.DBConfiguration;
import gcad.communications.DBConnection;
import gcad.communications.DBConnectionManager;
import gcad.internationalization.BundleInternationalization;
import gcad.views.ProposalView;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

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
				} catch (SQLException e) {
					// TODO: solo se puede lanzar esta excepcion, por lo que se encapsula en ella
					throw new InvocationTargetException(e);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
			//TODO: refrescar las vistas activas
			boolean activeProposalsView = false;
			// Tomar las vistas de la perspectiva activa
			IViewReference[] references;
			for (IWorkbenchPage page: PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages()){
				references = page.getViewReferences();
				for (IViewReference reference : references)
					if (reference.getId().equals("gcad.view.proposals"))
						activeProposalsView = true;
			}
			// Si esta activa (visible), se refresca al conectarse a la base de datos
			if (activeProposalsView) {
				ProposalView pView = (ProposalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("gcad.view.proposals");
				pView.showContentConnected();
			}
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			//MessageDialog.openError(getShell(), "Error", BundleInternationalization.getString("ErrorMessage.SQLException"));
			//e.printStackTrace();
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * The worker method. It will open the database connection
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */

	private void doFinish(final IProgressMonitor monitor, final String IP, final String port) throws CoreException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {		
		monitor.beginTask(BundleInternationalization.getString("DBMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("DBMonitorMessage"));

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

			database = new DBConnection();
			// Se lee la IP y el puerto
			database.getAgent().setIp(configuration.getDBip());
			database.getAgent().setPort(configuration.getDBport());
			database.open();
			DBConnectionManager.putConnection(database);
		
	}
}
