package presentation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

import presentation.utils.LanguageDialog;

/** 
 * This class handles the "Login" command from the "Session" menu. 
 * It is used to show the login wizard 
 */
public class ChooseLanguageHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Show a dialog to choose a new language
		LanguageDialog dialog = new LanguageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());		
		dialog.open();
		dialog.close();
		
		return null;
	}
}
