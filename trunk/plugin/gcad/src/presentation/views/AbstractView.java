package presentation.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public abstract class AbstractView extends ViewPart {
	
protected Label errorLabel;
	
	protected Composite parent;
	protected ISelection selection;
	
	protected Action deleteAction;	
	protected boolean visible = false;

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		visible = true;		
	}
	
	protected abstract void makeActions();
	protected abstract void createToolbar();
	protected abstract void setSelection();
	protected abstract void cleanComposite();
	public abstract void setFocus();
	
	protected void setErrorLabel (String message) {
		errorLabel = new Label(parent, SWT.NULL);
		errorLabel.setText(message);
	}
	
	protected void refreshComposite () {
		parent.layout();
	}
	
	protected void disableActions() {
		deleteAction.setEnabled(false);
	}

}
