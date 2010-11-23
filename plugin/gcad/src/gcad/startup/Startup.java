package gcad.startup;

import gcad.communications.DBConnectionManager;
import gcad.wizards.DBConnectionWizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.PlatformUI;

public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		PlatformUI.getWorkbench().getDisplay();
		/** The registration of the listener should have been done in the UI thread	
		* since PlatformUI.getWorkbench().getActiveWorkbenchWindow() returns null
		* if it is called outside of the UI thread.
		**/
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {		
				final IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				// TODO: se está activa la perspectiva de KM, se muestra el wizard para conectar a la base de datos
				// Lo mismo se hace cuando se cambia a esta perspectiva
				if (workbenchWindow != null) {
					if (workbenchWindow.getActivePage().getPerspective().getId().equals("gcad.perspective.KMPerspective")) {
						if (!DBConnectionManager.thereAreConnections()) 
							showWizardDBConnection();
					}
					
					workbenchWindow.addPerspectiveListener(new PerspectiveAdapter() {
						public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspectiveDescriptor) {
							if (perspectiveDescriptor.getId().equals("gcad.perspective.KMPerspective")) {
								if (!DBConnectionManager.thereAreConnections())
									showWizardDBConnection();
							}
						}
					});
				}
		}});
	}
	
	private void showWizardDBConnection () {
		// TODO: se reduce la altura de la ventana
		DBConnectionWizard bdwizard = new DBConnectionWizard();
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), bdwizard); 
		/*{
			@Override
	        protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setSize(450, 350);
			
			
			}	
		};*/

        dialog.create();
        dialog.open();
	}
}
