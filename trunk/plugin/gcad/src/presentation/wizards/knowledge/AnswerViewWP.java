package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;
import model.business.knowledge.Answer;
import model.business.knowledge.Knowledge;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This class represents an Answer Wizard Page (to create or modify it)
 */
public class AnswerViewWP extends AbstractKnowledgeWP {
	
	private Text argumentText;
	
	public AnswerViewWP(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewAnswerWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewAnswerWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 1;
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		super.createControl(container);
		
		Label argumentLabel = new Label(container, SWT.NULL);
		argumentText = new Text(container, SWT.BORDER | SWT.SINGLE);
		argumentLabel.setText(BundleInternationalization.getString("ArgumentLabel")+":");
		argumentText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		argumentText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});
		
		container.layout();
		wizardChanged();		
	}
	
	protected void wizardChanged(){
		super.wizardChanged();
		boolean valid = isValid();
		if (valid && argumentText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.ArgumentEmpty"));
			valid = false;
		}
		if (valid) 
			super.updateStatus(null);
	}
	
	protected void fillData(Knowledge k) {
		if (k!=null) {
			super.fillData(k);
			if (k instanceof Answer) {
				argumentText.setText(((Answer)k).getArgument());
				wizardChanged();
			}
		}
	}	
			
}
