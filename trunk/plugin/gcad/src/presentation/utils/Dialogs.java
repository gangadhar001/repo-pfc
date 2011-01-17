package presentation.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

public class Dialogs {

	public static boolean showConfirmationMessage(String title, String message) {
		MessageBox mb = new MessageBox(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
		mb.setText(title);
		mb.setMessage(message);
		return (mb.open()== SWT.OK);
	}
}
