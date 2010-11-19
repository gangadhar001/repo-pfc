package gcad.startup;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.Perspective;
import org.eclipse.ui.internal.WorkbenchPage;

public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		PlatformUI.getWorkbench().getDisplay();
		/*
		06.
		* The registration of the listener should have been done in the UI thread
		07.
		* since  PlatformUI.getWorkbench().getActiveWorkbenchWindow() returns null
		08.
		* if it is called outside of the UI thread.
		09.
		* */
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {		
				final IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					if (workbenchWindow != null) {
						workbenchWindow.addPerspectiveListener(new PerspectiveAdapter() {
							public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspectiveDescriptor) {
								super.perspectiveActivated(page, perspectiveDescriptor);
								if (workbenchWindow.getActivePage() instanceof WorkbenchPage) {
									WorkbenchPage workbenchPage = (WorkbenchPage) workbenchWindow.getActivePage();
									// Get the perspective
									Perspective perspective = workbenchPage.findPerspective(perspectiveDescriptor);
									System.out.println(perspective);
								}
					
							}
						});
					}
			}});
							

	}
}
