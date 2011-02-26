package presentation.handlers;

import internationalization.BundleInternationalization;

import java.sql.SQLException;

import model.business.control.Controller;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

/** 
 * This class handles the "Sign out" command from the "Session" menu. 
 * It is used to show the signout wizard 
 */
public class SignoutHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// If the user want to close the session, show a dialog to confirm it
		MessageBox mb = new MessageBox(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
        mb.setText(BundleInternationalization.getString("Title.ConfirmSignOut"));
        mb.setMessage(BundleInternationalization.getString("Message.ConfirmSignOut"));
        int result = mb.open();
		if (result == SWT.OK)
			try {
				Controller.getInstance().signout();
				// Notify that the user has signed out
				Controller.getInstance().notifySignOut();
			} catch (SQLException e) {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
			}
		return null;
	}
	

	

}
