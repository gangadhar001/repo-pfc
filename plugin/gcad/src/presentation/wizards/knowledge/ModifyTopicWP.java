package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.control.Controller;
import model.business.knowledge.Topic;

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

import exceptions.NoTopicsException;

/**
 * This class represents a Wizard Page used to modify a Topic, when it is invoke since the menu
 */
public class ModifyTopicWP extends TopicViewWP {

	private Composite parent;
	private Group groupContent;
	private Combo cbTopics;
	private ArrayList<Topic> topics;
	private Topic oldTopic;
	
	public ModifyTopicWP(String pageName, Topic topic) {
		super(pageName);
		setTitle(BundleInternationalization.getString("ModifyTopicWizardPageTitle"));
		setDescription(BundleInternationalization.getString("ModifyTopicWizardPageDescription"));
		this.oldTopic = topic;
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
		Group groupTopics = new Group(container, SWT.NONE);
		groupTopics.setLayout(new GridLayout(2,false));
		groupTopics.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupTopics.setText(BundleInternationalization.getString("GroupProposals"));
		
		Label topicsLabel = new Label(groupTopics, SWT.NULL);
		cbTopics = new Combo(groupTopics, SWT.DROP_DOWN | SWT.READ_ONLY);
		topicsLabel.setText(BundleInternationalization.getString("TopicLabel")+":");
		try {
			topics = Controller.getInstance().getTopicsWrapper().getTopics();
			if (topics.size() == 0)
				throw new NoTopicsException();
			for (int i=0; i<topics.size(); i++)
				cbTopics.add(topics.get(i).getTitle()); 
		} catch (InstantiationException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (IllegalAccessException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (ClassNotFoundException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (NoTopicsException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (SQLException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
		
		cbTopics.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fillDataTopic();
				wizardChanged();
				
			}
		});
		
		// If old topic is not null (the wizard is invoke since the Graph or
		// Hierarchical View), it is selected in the combobox, and the rest of
		// fields are filled
		if (oldTopic != null) {
			cbTopics.select(topics.indexOf(oldTopic));
			cbTopics.setEnabled(false);
			fillDataTopic();
		}
		
		wizardChanged();
		setControl(container);
	}
	
	private void fillDataTopic() {
		if (cbTopics!= null && cbTopics.getSelectionIndex()!=-1) {
			Topic t = topics.get(cbTopics.getSelectionIndex());
			// Clear group
			if (groupContent != null)
				groupContent.dispose();
			
			groupContent = new Group(parent, SWT.NONE);
			groupContent.setLayout(new GridLayout(2,false));
			groupContent.setLayoutData(new GridData(GridData.FILL_BOTH));
			groupContent.setText(BundleInternationalization.getString("GroupProposalContent"));
			super.createControl(groupContent);
			super.fillData(t);
			parent.layout();
		}
	}
	
	protected void wizardChanged(){
		boolean valid = isValid();
		if (cbTopics!= null && valid && cbTopics.getSelectionIndex()==-1) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.ProposalParentNotSelected"));
			valid = false;
		}
		if (valid) { 
			super.wizardChanged();
		}
	}

	public int getItemCbTopic() {
		return cbTopics.getSelectionIndex();
	}

	public ArrayList<Topic> getTopics() {
		return topics;
	}	

}
