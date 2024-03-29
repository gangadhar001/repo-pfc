package presentation.views;

import gcad.Activator;
import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.control.Controller;
import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import presentation.wizards.control.knowledge.ModifyAnswerViewWC;
import presentation.wizards.control.knowledge.ModifyProposalViewWC;
import presentation.wizards.control.knowledge.ModifyTopicViewWC;
import presentation.wizards.control.knowledge.NewAnswerViewWC;
import presentation.wizards.control.knowledge.NewProposalViewWC;
import presentation.wizards.control.knowledge.NewTopicWC;
import presentation.wizards.knowledge.AnswerViewWP;
import presentation.wizards.knowledge.ModifyAnswerWP;
import presentation.wizards.knowledge.ModifyProposalWP;
import presentation.wizards.knowledge.ModifyTopicWP;
import presentation.wizards.knowledge.ProposalViewWP;

/** This class represents an abstract class for the views that shown the available knowledge, in a tree
 * or in a graph
 */
public abstract class KnowledgeView extends AbstractView {	
	
	protected Action addAction;
	protected Action editAction;
	
	protected ArrayList<String> actionsAllowed;

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		makeActions();
		createToolbar();
		actionsAllowed = new ArrayList<String>();
	}
	
	protected void makeActions() {
		addAction = new Action(BundleInternationalization.getString("Actions.Add")) {
            public void run() { 
            	addKnowledge();
            }
		};
		addAction.setImageDescriptor(Activator.getImageDescriptor("resources/images/add.png"));

		editAction = new Action(BundleInternationalization.getString("Actions.Edit")) {
			public void run() {
				modifyKnowledge();
			}
		};
		editAction.setImageDescriptor(Activator.getImageDescriptor("resources/images/edit.png"));
		
		deleteAction = new Action(BundleInternationalization.getString("Actions.Delete")) {
			public void run() {
				deleteKnowledge();
			}
		};
		deleteAction.setImageDescriptor(Activator.getImageDescriptor("resources/images/remove.png"));
	}
	
	/**
     * Create toolbar with actions.
     */
	protected void createToolbar() {
        IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
        mgr.add(addAction);
        mgr.add(editAction);
        mgr.add(deleteAction);
    }
    
	protected abstract void addKnowledge();	
	protected abstract void modifyKnowledge();
	protected abstract void deleteKnowledge();

	protected void createKnowledge(Object obj) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (obj instanceof Topic) {
			addProposal((Topic)obj);
		}
		else if (obj instanceof Proposal) {
			addAnswer((Proposal)obj);				
		}
		else {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), BundleInternationalization.getString("NotAddKnowledgeToAnswer"));
		}
	}
	
	protected void modifyKnowledge(Object obj) {
		if (obj instanceof Topic) {
			modifyTopic((Topic)obj);
		}
		else if (obj instanceof Proposal) {
			modifyProposal((Proposal)obj);				
		}
		else if (obj instanceof Answer) {
			modifyAnswer((Answer)obj);
		}
	}
	
	private void modifyAnswer(Answer answer) {
		ModifyAnswerViewWC wizardModifyAnswerController = new ModifyAnswerViewWC(BundleInternationalization.getString("ModifyAnswerWizard"), answer);
		wizardModifyAnswerController.addPages(new ModifyAnswerWP(BundleInternationalization.getString("ModifyAnswerWizardPageTitle"), answer));
		showWizardDialog(wizardModifyAnswerController);			
	}

	private void modifyProposal(Proposal proposal) {
		ModifyProposalViewWC wizardModifyProposalController = new ModifyProposalViewWC(BundleInternationalization.getString("ModifyAnswerWizard"), proposal);
		wizardModifyProposalController.addPages(new ModifyProposalWP(BundleInternationalization.getString("ModifyAnswerWizardPageTitle"), proposal));
		showWizardDialog(wizardModifyProposalController);		
	}

	private void modifyTopic(Topic topic) {
		ModifyTopicViewWC wizardModifyTopicController = new ModifyTopicViewWC(BundleInternationalization.getString("ModifyAnswerWizard"), topic);
		wizardModifyTopicController.addPages(new ModifyTopicWP(BundleInternationalization.getString("ModifyAnswerWizardPageTitle"), topic));
		showWizardDialog(wizardModifyTopicController);			
	}

	protected void removeKnowledge(Object obj) {
		try {
			if (obj instanceof Topic)
				Controller.getInstance().deleteTopic((Topic)obj);
			else if (obj instanceof Proposal)							
				Controller.getInstance().deleteProposal((Proposal)obj);
			else
				Controller.getInstance().deleteAnswer((Answer)obj);
		} catch (SQLException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), BundleInternationalization.getString("Error"), e.getMessage());
		}				
	}
	
	protected void addTopic() {
		NewTopicWC wizarTopicController = new NewTopicWC(BundleInternationalization.getString("NewTopicWizardPageTitle"));
		showWizardDialog(wizarTopicController);
	}
	
	protected void addProposal(Topic topicSelected) {
		NewProposalViewWC wizardAbstractProposalController = new NewProposalViewWC(BundleInternationalization.getString("NewProposalWizard"), topicSelected);
		wizardAbstractProposalController.addPages(new ProposalViewWP(BundleInternationalization.getString("NewProposalWizardPageTitle")));
		showWizardDialog(wizardAbstractProposalController);
	}
	
	protected void addAnswer(Proposal proposalSelected) {
		NewAnswerViewWC wizardAbstractProposalController = new NewAnswerViewWC(BundleInternationalization.getString("NewAnswerWizard"), proposalSelected);
		wizardAbstractProposalController.addPages(new AnswerViewWP(BundleInternationalization.getString("NewAnswerWizardPageTitle")));
		showWizardDialog(wizardAbstractProposalController);
	}
	
	private void showWizardDialog(Wizard wizard) {
		WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
		wizardDialog.create();
		wizardDialog.open();
	}		
	
}
