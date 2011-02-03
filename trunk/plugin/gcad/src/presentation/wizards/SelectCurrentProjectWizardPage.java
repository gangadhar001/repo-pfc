package presentation.wizards;


import internationalization.BundleInternationalization;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This abstract class represents a DB Connection Wizard Page
 */
public class SelectCurrentProjectWizardPage extends WizardPage {
	
	private Combo cbProjects;
	
	public SelectCurrentProjectWizardPage(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("SelectCurrentProjectWizardPageTitle"));
		setDescription(BundleInternationalization.getString("SelectCurrentProjectWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		container.setLayout(layout);				
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		Label label = new Label(container, SWT.NONE);
		cbProjects = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		cbProjects.setLayoutData(gd);
		label.setText(BundleInternationalization.getString("ChooseProjectLabel")+":");
		
		// TODO: terminar
		
		/*projects = Controller.getInstance().getProjectsUser();
		if (projects.size() == 0)
			throw new NoProposalsException();
		for (int i=0; i<proposals.size(); i++)
			cbProjects.add(proposals.get(i).getTitle());*/
		
		cbProjects.add("1");
		cbProjects.add("2");
		
		cbProjects.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				wizardChanged();
				
			}
		});
		
		wizardChanged();
		setControl(container);

	}
	
	/** 
	 * This method validates the topic name
	 */
	private void wizardChanged() {
		
		boolean valid = true;

		if (cbProjects!= null && valid && cbProjects.getSelectionIndex()==-1) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.ProposalParentNotSelected"));
			valid = false;
		}
		if (valid) 
			updateStatus(null);
	}
		
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
		
	}

	public int getItemCbProjects() {
		return Integer.parseInt(cbProjects.getItem(cbProjects.getSelectionIndex()));
	}	
	
}
