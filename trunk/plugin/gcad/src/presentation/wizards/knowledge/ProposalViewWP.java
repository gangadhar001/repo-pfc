package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;
import model.business.knowledge.Categories;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Proposal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This abstract class represents a New Proposal Wizard Page when it is shown since the "Proposals" view
 */
public class ProposalViewWP extends AbstractKnowledgeWP {
	
	private Combo categoryCb;
	
	public ProposalViewWP(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewProposalWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewProposalWizardPageDescription"));
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
		
		Label categoryLabel = new Label(container, SWT.NONE);
		categoryCb = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		categoryLabel.setText(BundleInternationalization.getString("CategoryLabel")+":");
		String [] items = new String[Categories.values().length];
		for (int i=0; i<Categories.values().length; i++) 
			items[i] = Categories.values()[i].name();		
		categoryCb.setItems(items);
		categoryCb.addSelectionListener(new SelectionAdapter() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				wizardChanged();
			}
		});
		container.layout();
		wizardChanged();
		
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
	
	protected void fillData(Knowledge k) {
		if (k!=null) {
			super.fillData(k);
			if (k instanceof Proposal) {
				categoryCb.select(((Proposal)k).getCategory().ordinal());
				wizardChanged();
			}
		}
	}
	
	public String getItemCategory() {
		return categoryCb.getItem(categoryCb.getSelectionIndex());
	}
	
			
}
