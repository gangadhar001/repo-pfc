package gcad.wizards;

import gcad.communications.DBConfiguration;
import gcad.communications.DBConnection;
import gcad.communications.DBConnectionManager;
import gcad.internationalization.BundleInternationalization;

import java.sql.SQLException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

public abstract class AbstractNewProposalWizard extends Wizard {

	public AbstractNewProposalWizard (String wizardTitle) {
		super();
		setWindowTitle(wizardTitle);
		setNeedsProgressMonitor(true);
	}
	
	public void addPages(WizardPage page) {
		super.addPages();
		addPage(page);
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	/*public boolean performFinish() {
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
	}*/
	
	/**
	 * The worker method. It will create and insert in the database a new proposal
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

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
}
