package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.control.Controller;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import exceptions.NoTopicsException;

/**
 * This abstract class represents a New Proposal Wizard Page when it is shown since the "Knowledge" menu
 */
public class NewProposalMenuWP extends ProposalViewWP {
	
	private Combo cbTopics;	
	private ArrayList<Topic> topics;

	public NewProposalMenuWP(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewProposalWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewProposalWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {				
		super.createControl(parent);

		Label categoryLabel = new Label(getContainerParent(), SWT.NONE);
		cbTopics = new Combo(getContainerParent(), SWT.DROP_DOWN | SWT.READ_ONLY);
		categoryLabel.setText(BundleInternationalization.getString("TopicLabel")+":");
		try {
			topics = Controller.getInstance().getTopicsWrapper().getTopics();
			if (topics.size() == 0)
				throw new NoTopicsException();
			for (int i=0; i<topics.size(); i++)
				cbTopics.add(topics.get(i).getTitle()); 
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
		} catch (NoTopicsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cbTopics.addSelectionListener(new SelectionAdapter() {
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
		// Must select a parent topic
		if (cbTopics!= null && valid && cbTopics.getSelectionIndex()==-1) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.TopicParentNotSelected"));
			valid = false;
		}
		if (valid) 
			super.updateStatus(null);
	}
	
	protected void fillData(Knowledge k) {
		if (k!=null) {
			super.fillData(k);
			if (k instanceof Proposal) {
				int index = searchTopicIndex((Proposal)k);
				if (index!=-1) {
					cbTopics.select(index);
				}
			}
		}
	}

	private int searchTopicIndex(Proposal p) {
		boolean found = false;
		int result = -1;
		for (int i=0; i<topics.size() && !found; i++) {
			if (topics.get(i).getProposals().contains(p)) {
				found = true;
				result = i;
			}
		}
		return result;
	}
	
	public int getItemCbTopics() {
		return cbTopics.getSelectionIndex();
	}

	public ArrayList<Topic> getTopics() {
		return topics;
	}
	
}
