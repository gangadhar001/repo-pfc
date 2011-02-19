package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.control.Controller;
import model.business.knowledge.Proposal;

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

public class ModifyProposalMenuWP extends NewProposalMenuWP {

	private Combo cbProposals;
	private Composite parent;
	private ArrayList<Proposal> proposals;
	private Group groupProposalContent;
	
	public ModifyProposalMenuWP(String pageName) {
		super(pageName);
//		setTitle(BundleInternationalization.getString("NewAnswerWizardPageTitle"));
//		setDescription(BundleInternationalization.getString("NewAnswerWizardPageDescription"));
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
		Group groupLogin = new Group(container, SWT.NONE);
		groupLogin.setLayout(new GridLayout(2,false));
		groupLogin.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		groupLogin.setText("Login information");
		
		Label proposalsLabel = new Label(groupLogin, SWT.NULL);
		cbProposals = new Combo(groupLogin, SWT.DROP_DOWN | SWT.READ_ONLY);
		proposalsLabel.setText(BundleInternationalization.getString("ProposalLabel")+":");
		try {
			proposals = Controller.getInstance().getProposals();
			if (proposals.size() == 0)
				throw new NoProposalsException();
			for (int i=0; i<proposals.size(); i++)
				cbProposals.add(proposals.get(i).getTitle()); 
		} catch (SQLException e1) {
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
		} catch (NoProposalsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cbProposals.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fillDataProposal();
				wizardChanged();
				
			}
		});
		wizardChanged();
		setControl(container);
	}
	
	private void fillDataProposal() {
		if (cbProposals!= null && cbProposals.getSelectionIndex()!=-1) {
			Proposal p = proposals.get(cbProposals.getSelectionIndex());			

			if (groupProposalContent != null)
				groupProposalContent.dispose();			
			groupProposalContent = new Group(parent, SWT.NONE);
			groupProposalContent.setLayout(new GridLayout(2,false));
			groupProposalContent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			groupProposalContent.setText("Proposal Content");
			super.createControl(groupProposalContent);
			super.fillData(p);
			parent.layout();
		}
	}
	
	protected void wizardChanged(){
		boolean valid = isValid();
		// Must select a parent proposals
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
