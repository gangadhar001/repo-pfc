package presentation.handlers;

import java.sql.SQLException;

import internationalization.BundleInternationalization;

import model.business.control.Controller;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

import presentation.wizards.control.LoginWizardController;


public class LoginHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//TODO: al iniciar sesión, si ya se está conectado, se pregunta si desea iniciar de nuevo
		boolean logged = Controller.getInstance().isLogged();
		boolean result = false;
		if (logged) {
			MessageBox mb = new MessageBox(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			mb.setText(BundleInternationalization.getString("Title.ConfirmNewLogin"));
			mb.setMessage(BundleInternationalization.getString("Message.ConfirmLogin"));
			result = (mb.open()== SWT.OK);
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
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
			dialog.create();
			dialog.open();
		}
		return null;

	}


}
