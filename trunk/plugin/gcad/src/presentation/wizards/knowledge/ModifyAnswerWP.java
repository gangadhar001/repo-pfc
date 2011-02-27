package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.control.Controller;
import model.business.knowledge.Answer;

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

import exceptions.NoAnswersException;

/**
 * This class represents a Wizard Page used to modify an Answer, when it is invoke since the menu
 */
public class ModifyAnswerWP extends NewAnswerMenuWP {

	private Combo cbAnswers;
	private Composite parent;
	private ArrayList<Answer> answers;
	private Group groupAnswerData;
	private Answer oldAnswer;

	public ModifyAnswerWP(String pageName, Answer oldAnswer) {
		super(pageName);
		setTitle(BundleInternationalization.getString("ModifyAnswerWizardPageTitle"));
		setDescription(BundleInternationalization.getString("ModifyAnswerWizardPageDescription"));
		this.oldAnswer = oldAnswer;
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
		Group groupAnswers = new Group(container, SWT.NONE);
		groupAnswers.setLayout(new GridLayout(2, false));
		groupAnswers.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupAnswers.setText(BundleInternationalization.getString("GroupAnswers"));

		Label answersLabel = new Label(groupAnswers, SWT.NULL);
		cbAnswers = new Combo(groupAnswers, SWT.DROP_DOWN | SWT.READ_ONLY);
		answersLabel.setText(BundleInternationalization.getString("AnswerLabel") + ":");
		try {
			answers = Controller.getInstance().getAnswers();
			if (answers.size() == 0)
				throw new NoAnswersException();
			for (int i = 0; i < answers.size(); i++)
				cbAnswers.add(answers.get(i).getTitle());
		} catch (SQLException e) {

		} catch (InstantiationException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (IllegalAccessException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (ClassNotFoundException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (NoAnswersException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}

		cbAnswers.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fillDataAnswer();
				wizardChanged();
			}
		});

		// If old answer is not null (the wizard is invoke since the Graph or
		// Hierarchical View), it is selected in the combobox, and the rest of
		// fields are filled
		if (oldAnswer != null) {
			cbAnswers.select(answers.indexOf(oldAnswer));
			cbAnswers.setEnabled(false);
			fillDataAnswer();
		}

		wizardChanged();
		setControl(container);
	}

	private void fillDataAnswer() {
		if (cbAnswers != null && cbAnswers.getSelectionIndex() != -1) {
			Answer a = answers.get(cbAnswers.getSelectionIndex());
			// Clear group
			if (groupAnswerData != null)
				groupAnswerData.dispose();

			groupAnswerData = new Group(parent, SWT.NONE);
			groupAnswerData.setLayout(new GridLayout(2, false));
			groupAnswerData.setLayoutData(new GridData(GridData.FILL_BOTH));
			groupAnswerData.setText(BundleInternationalization.getString("GroupAnswerContent"));
			super.createControl(groupAnswerData);
			super.fillData(a);
			parent.layout();
		}
	}

	protected void wizardChanged() {
		boolean valid = isValid();
		if (cbAnswers != null && valid && cbAnswers.getSelectionIndex() == -1) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.AnswerNotSelected"));
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
