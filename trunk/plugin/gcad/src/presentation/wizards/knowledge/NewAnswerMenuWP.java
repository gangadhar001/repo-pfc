package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.control.Controller;
import model.business.knowledge.Answer;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Proposal;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import exceptions.NoProposalsException;

/**
 * This class represents a New Answer Wizard Page when it is invoke since the "Knowledge" menu
 */
public class NewAnswerMenuWP extends AnswerViewWP {
	
	private Combo cbProposals;	
	private ArrayList<Proposal> proposals;

	public NewAnswerMenuWP(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewAnswerWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewAnswerWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {		
		super.createControl(parent);
	
		Label proposalsLabel = new Label(getContainerParent(), SWT.NONE);
		cbProposals = new Combo(getContainerParent(), SWT.DROP_DOWN | SWT.READ_ONLY);
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
				wizardChanged();
				
			}
		});
		getContainerParent().layout();
		wizardChanged();
	}
	
	protected void wizardChanged(){
		super.wizardChanged();
		boolean valid = isValid();
		// Must select a parent proposal
		if (cbProposals!= null && valid && cbProposals.getSelectionIndex()==-1) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.ProposalParentNotSelected"));
			valid = false;
		}
		if (valid) 
			super.updateStatus(null);
	}
	
	protected void fillData(Knowledge k) {
		if (k!=null) {
			super.fillData(k);
			if (k instanceof Answer) {
				int index = searchProposalIndex((Answer)k);
				if (index!=-1) {
					cbProposals.select(index);
				}
			}
		}
	}
	
	private int searchProposalIndex(Answer a) {
		boolean found = false;
		int result = -1;
		for (int i=0; i<proposals.size() && !found; i++) {
			if (proposals.get(i).getAnswers().contains(a)) {
				found = true;
				result = i;
			}
		}
		return result;
	}

	public int getItemCbProposals() {
		return cbProposals.getSelectionIndex();
	}

	public ArrayList<Proposal> getProposals() {
		return proposals;
	}
	
}
