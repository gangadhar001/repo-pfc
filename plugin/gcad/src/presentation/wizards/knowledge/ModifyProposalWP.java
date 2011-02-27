package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.control.Controller;
import model.business.knowledge.Proposal;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import exceptions.NoProposalsException;

/**
 * This class represents a Wizard Page used to modify a Proposal, when it is invoke since the menu
 */
public class ModifyProposalWP extends NewProposalMenuWP {

	private Combo cbProposals;
	private Composite parent;
	private ArrayList<Proposal> proposals;
	private Group groupContent;
	private Proposal oldProposal;
	
	public ModifyProposalWP(String pageName, Proposal proposal) {
		super(pageName);
		setTitle(BundleInternationalization.getString("ModifyProposalWizardPageTitle"));
		setDescription(BundleInternationalization.getString("ModifyProposalWizardPageDescription"));
		this.oldProposal = proposal;
	}
	
	@Override
	public void createControl(Composite parent) {		
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 1;
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.parent = container;
		Group groupProposals = new Group(container, SWT.NONE);
		groupProposals.setLayout(new GridLayout(2,false));
		groupProposals.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupProposals.setText(BundleInternationalization.getString("GroupProposals"));
		
		Label proposalsLabel = new Label(groupProposals, SWT.NULL);
		cbProposals = new Combo(groupProposals, SWT.DROP_DOWN | SWT.READ_ONLY);
		proposalsLabel.setText(BundleInternationalization.getString("ProposalLabel")+":");
		try {
			proposals = Controller.getInstance().getProposals();
			if (proposals.size() == 0)
				throw new NoProposalsException();
			for (int i=0; i<proposals.size(); i++)
				cbProposals.add(proposals.get(i).getTitle()); 
		} catch (InstantiationException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (IllegalAccessException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (ClassNotFoundException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (NoProposalsException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (SQLException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
		
		cbProposals.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fillDataProposal();
				wizardChanged();
				
			}
		});
		
		// If old proposal is not null (the wizard is invoke since the Graph or
		// Hierarchical View), it is selected in the combobox, and the rest of
		// fields are filled
		if (oldProposal != null) {
			cbProposals.select(proposals.indexOf(oldProposal));
			cbProposals.setEnabled(false);
			fillDataProposal();
		}
		
		wizardChanged();
		setControl(container);
	}
	
	private void fillDataProposal() {
		if (cbProposals!= null && cbProposals.getSelectionIndex()!=-1) {
			Proposal p = proposals.get(cbProposals.getSelectionIndex());
			// Clear group
			if (groupContent != null)
				groupContent.dispose();
			
			groupContent = new Group(parent, SWT.NONE);
			groupContent.setLayout(new GridLayout(2,false));
			groupContent.setLayoutData(new GridData(GridData.FILL_BOTH));
			groupContent.setText(BundleInternationalization.getString("GroupProposalContent"));
			super.createControl(groupContent);
			super.fillData(p);
			parent.layout();
		}
	}
	
	protected void wizardChanged(){
		boolean valid = isValid();
		if (cbProposals!= null && valid && cbProposals.getSelectionIndex()==-1) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.ProposalParentNotSelected"));
			valid = false;
		}
		if (valid) { 
			super.wizardChanged();
		}
	}

	public int getItemCbProposals() {
		return cbProposals.getSelectionIndex();
	}

	public ArrayList<Proposal> getProposals() {
		return proposals;
	}	

}
