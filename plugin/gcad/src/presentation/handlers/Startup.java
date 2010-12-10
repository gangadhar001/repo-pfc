package presentation.handlers;


import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.PlatformUI;

import persistence.communications.DBConnectionManager;
import presentation.wizards.control.LoginWizard;

/**
 * This class controls the perspective changed event, to show the database connection wizard
 */
public class Startup implements IStartup {
	
	private final static String PERSPECTIVE_ID = "gcad.perspective.KMPerspective";

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
				// The database connection wizard is shown when the knowledge perspective is activated and it is shown 
				if (workbenchWindow != null) {
					if (workbenchWindow.getActivePage().getPerspective().getId().equals(PERSPECTIVE_ID)) {
						if (!DBConnectionManager.thereAreConnections()) 
							showWizardDBConnection();
					}
					
					workbenchWindow.addPerspectiveListener(new PerspectiveAdapter() {
						public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspectiveDescriptor) {
							if (perspectiveDescriptor.getId().equals(PERSPECTIVE_ID)) {
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
		LoginWizard bdwizard = new LoginWizard();
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
