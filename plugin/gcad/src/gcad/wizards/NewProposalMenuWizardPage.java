package gcad.wizards;

import gcad.domain.control.KnowledgeManager;
import gcad.domain.knowledge.AbstractProposal;
import gcad.exceptions.NoProjectProposalsException;
import gcad.internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This abstract class represents a New Proposal Wizard Page when it is shown since the "Knowledge" menu
 */
public class NewProposalMenuWizardPage extends AbstractNewProposalWizardPage {
	
	private Combo cbProposals;
	private ArrayList<AbstractProposal> proposals;

	public NewProposalMenuWizardPage(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewProposalWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewProposalWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		KnowledgeManager manager = KnowledgeManager.getManager();
		Label categoryLabel = new Label(getContainerParent(), SWT.NONE);
		cbProposals = new Combo(getContainerParent(), SWT.DROP_DOWN | SWT.READ_ONLY);
		categoryLabel.setText(BundleInternationalization.getString("ProposalLabel")+":");
		try {
			proposals = manager.getProposals();
			for (int i=0; i<proposals.size(); i++)
				cbProposals.add(proposals.get(i).getTitle()); 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoProjectProposalsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cbProposals.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				wizardChanged();
				
			}
		});
		wizardChanged();
	}
	
	protected void wizardChanged(){
		super.wizardChanged();
		boolean valid = isValid();
		// Must select a parent proposal
		if (cbProposals!= null && valid && cbProposals.getSelectionIndex()==-1) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.ParentNotSelected"));
			valid = false;
		}
		if (valid) 
			super.updateStatus(null);
	}

	public ArrayList<AbstractProposal> getProposals() {
		return proposals;
	}

	public int getItemCbProposals() {
		return cbProposals.getSelectionIndex();
	}
	
	

}
