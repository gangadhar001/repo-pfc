package presentation.views;

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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import presentation.wizards.NewAnswerViewWizardPage;
import presentation.wizards.NewProposalViewWizardPage;
import presentation.wizards.control.NewAnswerViewWizardController;
import presentation.wizards.control.NewProposalViewWizardController;
import presentation.wizards.control.NewTopicWizardController;

public abstract class AbstractKnowledgeView extends ViewPart {
	
	protected Label errorLabel;
	
	protected Composite parent;
	protected ISelection selection;
	
	protected Action addAction;
	protected Action editAction;
	protected Action deleteAction;
	
	protected boolean visible = false;
	protected ArrayList<String> actionsAllowed;

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		visible = true;				
	}
	
	
	/**
	 * This method handles the double click action.
	 * TODO: añadir/eliminar una nueva propuesta/respuesta
	 */
	protected void makeActions() {
		addAction = new Action(BundleInternationalization.getString("Actions.Add")) {
            public void run() { 
            	addKnowledge();
            }
		};
		//addAction.setImageDescriptor(getImageDescriptor("add.gif"));

		deleteAction = new Action(BundleInternationalization.getString("Actions.Delete")) {
			public void run() {
				deleteKnowledge();
			}
		};
		//deleteAction.setImageDescriptor(getImageDescriptor("delete.gif"));
	}
	
	/**
     * Create toolbar with actions.
     */
	protected void createToolbar() {
        IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
        mgr.add(addAction);
        mgr.add(deleteAction);
    }
    
	abstract protected void addKnowledge();	
	abstract protected void deleteKnowledge();
	abstract protected void setSelection();
	abstract protected void cleanComposite(); 


	protected void createKnowledge(Object obj) {
		if (obj instanceof Topic) {
			addProposal((Topic)obj);
		}
		else if (obj instanceof Proposal) {
			addAnswer((Proposal)obj);				
		}
		else {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Error", "No se puede añadir nuevo conocimiento a una respuesta");
		}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	protected void addTopic() {
		NewTopicWizardController wizarTopicController = new NewTopicWizardController();
		showWizardDialog(wizarTopicController);
	}
	
	protected void addProposal(Topic topicSelected) {
		NewProposalViewWizardController wizardAbstractProposalController = new NewProposalViewWizardController(BundleInternationalization.getString("NewProposalWizard"), topicSelected);
		wizardAbstractProposalController.addPages(new NewProposalViewWizardPage(BundleInternationalization.getString("NewProposalWizardPageTitle")));
		showWizardDialog(wizardAbstractProposalController);
	}
	
	protected void addAnswer(Proposal proposalSelected) {
		NewAnswerViewWizardController wizardAbstractProposalController = new NewAnswerViewWizardController(BundleInternationalization.getString("NewAnswerWizard"), proposalSelected);
		wizardAbstractProposalController.addPages(new NewAnswerViewWizardPage(BundleInternationalization.getString("NewAnswerWizardPageTitle")));
		showWizardDialog(wizardAbstractProposalController);
	}
	
	private void showWizardDialog(Wizard wizard) {
		WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
		wizardDialog.create();
		wizardDialog.open();
	}	
	
	protected void setErrorLabel (String message) {
		errorLabel = new Label(parent, SWT.NULL);
		errorLabel.setText(message);
	}
	
	protected void refreshComposite () {
		parent.layout();
	}
	
	protected void disableActions() {
		addAction.setEnabled(false);
		deleteAction.setEnabled(false);
	}

}
