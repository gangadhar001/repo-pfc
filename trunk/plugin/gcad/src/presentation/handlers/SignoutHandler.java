package presentation.handlers;


import internationalization.BundleInternationalization;

import java.sql.SQLException;

import model.business.control.Controller;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

import persistence.communications.DBConnectionManager;

public class SignoutHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//TODO: al cerrar sesión, se pregunta si se desea cerrar y se cierran las conexiones
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	

	

}
