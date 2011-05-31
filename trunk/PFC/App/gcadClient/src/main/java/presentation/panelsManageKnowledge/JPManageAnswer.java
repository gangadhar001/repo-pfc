package presentation.panelsManageKnowledge;

import internationalization.ApplicationInternationalization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.business.knowledge.Answer;
import model.business.knowledge.Operations;
import model.business.knowledge.Proposal;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.JFMain;

import bussiness.control.ClientController;
import bussiness.control.OperationsUtilities;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class JPManageAnswer extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1064240066762280491L;
	private JTabbedPane tabPanelAnswer;
	private JPanel panelModifyAnswer;
	private JPAnswerInfo panelAnswerInfoModify;
	private JComboBox cbAnswers;
	private JLabel lblAnswerModify;
	private JPAnswerInfo panelAnswerInfoAdd;
	private JButton btnSaveModify;
	private JButton btnCancelModify;
	private JComboBox cbProposals;
	private JLabel lblProposals;
	private JButton btnCancelAdd;
	private JButton btnSaveAnswer;
	private JComboBox cbProposalsAdd;
	private JLabel lblProposal;
	private JPanel panelAddAnswer;
	private ArrayList<Proposal> proposals;
	private JDialog parentD;
	private Object data;
	private String operationToDo;
	private Answer[] answers;
	private JFMain mainFrame;

	public JPManageAnswer(JFMain mainFrame, JDialog parent) {
		super();
		this.parentD = parent;
		// No operation selected
		data = null;
		this.mainFrame = mainFrame;
		initGUI();
	}
	
	public JPManageAnswer(JFMain mainFrame, JDialog parent, Object data, String operationToDo) {
		super();
		this.parentD = parent;
		// Operation selected
		this.data = data;
		this.operationToDo = operationToDo;
		this.mainFrame = mainFrame;
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(460, 288));
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setSize(460, 288);
			{
				tabPanelAnswer = new JTabbedPane();
				this.add(tabPanelAnswer, new AnchorConstraint(1, 1001, 1001, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				tabPanelAnswer.setPreferredSize(new java.awt.Dimension(438, 317));
				{
					panelAddAnswer = new JPanel();
					tabPanelAnswer.addTab(ApplicationInternationalization.getString("tabManageAnswer_Add"), null, panelAddAnswer, null);
					panelAddAnswer.setLayout(null);
					panelAddAnswer.setPreferredSize(new java.awt.Dimension(429, 264));
					panelAddAnswer.setSize(439, 317);
					panelAddAnswer.setName("panelAddProposals");
					{
						btnCancelAdd = new JButton();
						panelAddAnswer.add(btnCancelAdd, new AnchorConstraint(835, 961, 932, 807, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnCancelAdd.setName("btnCancelAdd");
						btnCancelAdd.setBounds(376, 227, 67, 25);
						btnCancelAdd.setAction(getAppActionMap().get("Cancel"));
						btnCancelAdd.setText(ApplicationInternationalization.getString("CancelButton"));
					}
					{
						btnSaveAnswer = new JButton();
						panelAddAnswer.add(btnSaveAnswer, new AnchorConstraint(835, 754, 929, 596, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnSaveAnswer.setName("btnSaveAnswer");
						btnSaveAnswer.setBounds(297, 228, 68, 24);
						btnSaveAnswer.setAction(getAppActionMap().get("Save"));
						btnSaveAnswer.setText(ApplicationInternationalization.getString("btnSave"));
					}
					{
						cbProposalsAdd = new JComboBox();
						panelAddAnswer.add(cbProposalsAdd, new AnchorConstraint(32,726,112,285,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL));
						cbProposalsAdd.setBounds(76, 9, 195, 21);
					}
					{
						lblProposal = new JLabel();
						panelAddAnswer.add(lblProposal, new AnchorConstraint(43,243,102,28,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL));
						lblProposal.setName("lblProposal");
						lblProposal.setBounds(12, 12, 64, 17);
						lblProposal.setText(ApplicationInternationalization.getString("lblProposal"));
					}
					{
						panelAnswerInfoAdd = new JPAnswerInfo(parentD);
						panelAddAnswer.add(panelAnswerInfoAdd);
						panelAnswerInfoAdd.setBounds(12, 44, 431, 172);
					}
				}
				{
					panelModifyAnswer = new JPanel();
					tabPanelAnswer.addTab(ApplicationInternationalization.getString("tabManageAnswer_Modify"), null, panelModifyAnswer, null);
					panelModifyAnswer.setName("panelModifyAnswer");
					panelModifyAnswer.setLayout(null);
					{
						lblProposals = new JLabel();
						panelModifyAnswer.add(lblProposals);
						lblProposals.setBounds(12, 12, 67, 16);
						lblProposals.setName("lblProposals");
						lblProposals.setText(ApplicationInternationalization.getString("lblProposal"));
					}
					{
						cbProposals = new JComboBox();
						panelModifyAnswer.add(cbProposals);
						cbProposals.setBounds(79, 9, 129, 23);
						cbProposals.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								cbProposalsActionPerformed(evt);
							}
						});
					}
					{
						btnCancelModify = new JButton();
						panelModifyAnswer.add(btnCancelModify);
						btnCancelModify.setBounds(376, 227, 67, 25);
						btnCancelModify.setName("btnCancelModify");
						btnCancelModify.setAction(getAppActionMap().get("Cancel"));
						btnCancelModify.setText(ApplicationInternationalization.getString("CancelButton"));
						
					}
					{
						btnSaveModify = new JButton();
						panelModifyAnswer.add(btnSaveModify);
						btnSaveModify.setBounds(297, 228, 68, 24);
						btnSaveModify.setName("btnSaveModify");
						btnSaveModify.setAction(getAppActionMap().get("Modify"));
						btnSaveModify.setText(ApplicationInternationalization.getString("btnModify"));
					}
					{
						lblAnswerModify = new JLabel();
						panelModifyAnswer.add(lblAnswerModify);
						lblAnswerModify.setBounds(242, 12, 65, 17);
						lblAnswerModify.setName("lblAnswerModify");
						lblAnswerModify.setText(ApplicationInternationalization.getString("lblAnswerModify"));
					}
					{
						cbAnswers = new JComboBox();
						panelModifyAnswer.add(cbAnswers);
						cbAnswers.setBounds(307, 9, 136, 23);
						cbAnswers.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								cbAnswersActionPerformed(evt);
							}
						});
					}
					{
						panelAnswerInfoModify = new JPAnswerInfo(parentD);
						panelModifyAnswer.add(panelAnswerInfoModify);
						panelAnswerInfoModify.setBounds(12, 44, 431, 172);
					}
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
			
			setItemsComboProposals();
			setItemsComboAnswers();
			
			// Hide tabs not available 
			List<String> operationsId = OperationsUtilities.getAllOperations(ClientController.getInstance().getAvailableOperations());
			if (!operationsId.contains(Operations.Add.name()))
				tabPanelAnswer.remove(panelAddAnswer);
			if (!operationsId.contains(Operations.Modify.name()))
				tabPanelAnswer.remove(panelModifyAnswer);
			
			// If this panel is invoked by knowledge view, with an item already selected, fill the data
			if (data != null) {
				fillData();
				// Disable tabs not used for that operation
				int indexModify = getIndexTab(ApplicationInternationalization.getString("tabManageAnswer_Modify"));
				int indexAdd = getIndexTab(ApplicationInternationalization.getString("tabManageAnswer_Add"));
				if (operationToDo.equals(Operations.Add.name())) {
					if (indexModify != -1)
						tabPanelAnswer.getTabComponentAt(indexModify).setEnabled(false);
					if (indexAdd != -1)
						tabPanelAnswer.setSelectedIndex(indexAdd);
				}
				else if (operationToDo.equals(Operations.Modify.name())) {
					if (indexAdd != -1)
						tabPanelAnswer.getTabComponentAt(indexAdd).setEnabled(false);
					if (indexModify != -1)
						tabPanelAnswer.setSelectedIndex(indexModify);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cbAnswersActionPerformed(ActionEvent evt) {
		panelAnswerInfoModify.fillData(answers[cbAnswers.getSelectedIndex()]);
	}
	
	private void fillData() {
		// Fill fields with the received answer
		if (operationToDo.equals(Operations.Modify.name())) {
			Answer a = (Answer)data;
			panelAnswerInfoModify.fillData(a);
			try {
				Proposal p = ClientController.getInstance().findParentAnswer(a);
				cbProposals.setSelectedItem(p.getTitle());
				cbProposals.setEnabled(false);	
				
				cbAnswers.setSelectedItem(a.getTitle());
				cbAnswers.setEnabled(false);
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRole e) {
				JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
	
	private void setItemsComboProposals() {
		try {
			// Get Proposals
			proposals = ClientController.getInstance().getProposals();
			if (proposals.size() == 0)
				JOptionPane.showMessageDialog(parentD, ApplicationInternationalization.getString("panelManageAnswer_NotProposals"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			for (int i=0; i<proposals.size(); i++) {
				cbProposalsAdd.insertItemAt(proposals.get(i).getTitle(), i); 
				cbProposals.insertItemAt(proposals.get(i).getTitle(), i);
			}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRole e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	private void setItemsComboAnswers() {
		if (answers != null) {
			for (int i=0; i<answers.length; i++)
				cbAnswers.insertItemAt(answers[i].getTitle(), i);
		}
	}
	
	// When select a proposal, fill the "Answers" combobox
	private void cbProposalsActionPerformed(ActionEvent evt) {
		if (cbProposals.getSelectedIndex() != -1) {
			Proposal p = proposals.get(cbProposals.getSelectedIndex());
			Object[] aux = p.getAnswers().toArray();
			answers = new Answer[aux.length];
			for (int i = 0; i<aux.length; i++)
				answers[i] = (Answer) aux[i];
			setItemsComboAnswers();			
		}
	}
	
	/*** Actions for buttons ***/
	@Action
	public void Cancel () {
		parentD.dispose();
	}	
	
	@Action
	public void Save() {
		Answer newAn = new Answer(panelAnswerInfoAdd.getAnswerTitle(), panelAnswerInfoAdd.getAnswerDescription(), new Date(), panelAnswerInfoAdd.getAnswerArgument());
		try {
			// Create and insert new Answer
			ClientController.getInstance().addAnwser(newAn, proposals.get(cbProposalsAdd.getSelectedIndex()));
			// Notify to main frame the new knowledge
			mainFrame.notifyKnowledgeAdded(newAn, proposals.get(cbProposalsAdd.getSelectedIndex()));
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Action
	public void Modify() {
		Answer newAn = new Answer(panelAnswerInfoModify.getAnswerTitle(), panelAnswerInfoModify.getAnswerDescription(), new Date(), panelAnswerInfoModify.getAnswerArgument());
		try {
			// Modify the old Answer
			ClientController.getInstance().modifyAnswer(newAn, answers[cbAnswers.getSelectedIndex()], proposals.get(cbProposals.getSelectedIndex()));
			// Notify to main frame the new knowledge
			mainFrame.notifyKnowledgeEdited(newAn, answers[cbAnswers.getSelectedIndex()]);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRole e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	private int getIndexTab(String title) {
    	int result = -1;
    	for(int i=0; i<tabPanelAnswer.getTabCount() && result==-1; i++) {
    		if (tabPanelAnswer.getTitleAt(i).equals(title))
    			result=i;
    	}
    	return result;
	}
}
