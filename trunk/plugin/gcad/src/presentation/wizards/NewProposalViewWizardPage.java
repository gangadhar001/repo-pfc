package presentation.wizards;

import internationalization.BundleInternationalization;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This abstract class represents a New Proposal Wizard Page when it is shown since the "Proposals" view
 */
public class NewProposalViewWizardPage extends AbstractNewKnowledgeWizardPage {
	
	private Combo categoryCb;
	
	public NewProposalViewWizardPage(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewProposalWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewProposalWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		Label categoryLabel = new Label(getContainerParent(), SWT.NONE);
		categoryCb = new Combo(getContainerParent(), SWT.DROP_DOWN | SWT.READ_ONLY);
		categoryLabel.setText(BundleInternationalization.getString("CategoryLabel")+":");
		categoryCb.setItems(new String[] { "Analysis", "Design" });
		categoryCb.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				wizardChanged();
				
			}
		});	
	}
	
	protected void wizardChanged(){
		super.wizardChanged();
		boolean valid = isValid();
		// Must select a category
		if (valid && categoryCb.getSelectionIndex()==-1) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.CategoryNotSelected"));
			valid = false;
		}
		if (valid) 
			super.updateStatus(null);
	}
	
	public String getItemCategory() {
		return categoryCb.getItem(categoryCb.getSelectionIndex());
	}
	
			
}
