package gcad.wizards;

import gcad.communications.DBConfiguration;
import gcad.communications.DBConnection;
import gcad.communications.DBConnectionManager;
import gcad.internationalization.BundleInternationalization;
import gcad.views.ProposalView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * This abstract class represents a DB Connection Wizard
 */
public class LoginWizard extends Wizard {
	
	private final static String PROPOSAL_VIEW_ID = "gcad.view.proposals";
	
	private LoginWizardPage page;

	public LoginWizard () {
		super();
		setWindowTitle(BundleInternationalization.getString("LoginWizardTitle"));
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		super.addPages();
		page = new LoginWizardPage(BundleInternationalization.getString("LoginWizardTitle"));
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
					// It is only possible to throw this exception type
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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
			// When the plug-in is connected to database, the active views are refreshed
			boolean activeProposalsView = false;
			IViewReference[] references;
			for (IWorkbenchPage page: PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages()){
				references = page.getViewReferences();
				for (IViewReference reference : references)
					if (reference.getId().equals(PROPOSAL_VIEW_ID))
						activeProposalsView = true;
			}
			// If the proposals view is visible, it is updated
			if (activeProposalsView) {
				ProposalView pView = (ProposalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("gcad.view.proposals");
				pView.showContentConnected();
			}
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
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
	 */
	private void doFinish(final IProgressMonitor monitor, final String IP, final String port) throws CoreException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {		
		monitor.beginTask(BundleInternationalization.getString("DBMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("DBMonitorMessage"));

		// Create the configuration of the database connection
		DBConfiguration configuration = new DBConfiguration(IP, Integer.parseInt(port));
		DBConnection database;
		monitor.worked(10);
		// Close older connections (ignore errors)
		try {
			DBConnectionManager.closeConnections();
			monitor.worked(10);
		} catch(SQLException e) { }
		DBConnectionManager.clearConnections();
		monitor.worked(10);
		
		// Create and initializate a new database connection
		database = new DBConnection();
		// Se lee la IP y el puerto
		database.getAgent().setIp(configuration.getDBip());
		database.getAgent().setPort(configuration.getDBport());
		// Open the connection
		database.open();
		DBConnectionManager.putConnection(database);
		
	}
}
