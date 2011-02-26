package presentation.handlers;

import internationalization.BundleInternationalization;

import java.sql.SQLException;

import model.business.control.Controller;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import presentation.utils.Dialogs;
import presentation.utils.LoginWizardDialog;
import presentation.wizards.control.login.LoginWizardController;

/** 
 * This class handles the "Login" command from the "Session" menu. 
 * It is used to show the login wizard 
 */
public class LoginHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		boolean logged = Controller.getInstance().isLogged();
		boolean result = false;
		// If the user is already logged, show a dialog to confirm to open a new session
		if (logged) {
			result =Dialogs.showConfirmationMessage(BundleInternationalization.getString("Title.ConfirmNewLogin"), BundleInternationalization.getString("Message.ConfirmLogin")); 
			if (result) {
				try {
					Controller.getInstance().signout();
					Controller.getInstance().notifySignOut();
				} catch (SQLException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", e.getMessage());
				}		
			}
		}
		if (result || !logged) {
			LoginWizardController wizard = new LoginWizardController();
			LoginWizardDialog dialog = new LoginWizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
			dialog.create();
			dialog.open();
		}
		return null;

	}


}
