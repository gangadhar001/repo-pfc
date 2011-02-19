package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.control.Controller;
import model.business.knowledge.Answer;

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

public class ModifyAnswerMenuWP extends NewAnswerMenuWP {

		
		private Combo cbAnswers;
		private Composite parent;
		private ArrayList<Answer> answers;
		private Group groupAnswerContent;
		
		public ModifyAnswerMenuWP(String pageName) {
			super(pageName);
//			setTitle(BundleInternationalization.getString("NewAnswerWizardPageTitle"));
//			setDescription(BundleInternationalization.getString("NewAnswerWizardPageDescription"));
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
			
			Label answersLabel = new Label(groupLogin, SWT.NULL);
			cbAnswers = new Combo(groupLogin, SWT.DROP_DOWN | SWT.READ_ONLY);
			answersLabel.setText(BundleInternationalization.getString("ProposalLabel")+":");
			try {
				answers = Controller.getInstance().getAnswers();
				if (answers.size() == 0)
					throw new NoProposalsException();
				for (int i=0; i<answers.size(); i++)
					cbAnswers.add(answers.get(i).getTitle()); 
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
			
			cbAnswers.addSelectionListener(new SelectionAdapter() {
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
			if (cbAnswers!= null && cbAnswers.getSelectionIndex()!=-1) {
				Answer a = answers.get(cbAnswers.getSelectionIndex());			

				if (groupAnswerContent != null)
					groupAnswerContent.dispose();			
				groupAnswerContent = new Group(parent, SWT.NONE);
				groupAnswerContent.setLayout(new GridLayout(2,false));
				groupAnswerContent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				groupAnswerContent.setText("Proposal Content");
				super.createControl(groupAnswerContent);
				super.fillData(a);
				parent.layout();
			}
		}
		
		protected void wizardChanged(){
			boolean valid = isValid();
			// Must select a parent proposals
			if (cbAnswers!= null && valid && cbAnswers.getSelectionIndex()==-1) {
				updateStatus(BundleInternationalization.getString("ErrorMessage.ProposalParentNotSelected"));
				valid = false;
			}
			if (valid) { 
				super.wizardChanged();
			}
		}

		public int getItemCbAnswers() {
			return cbAnswers.getSelectionIndex();
		}

		public ArrayList<Answer> getAnswers() {
			return answers;
		}
}
