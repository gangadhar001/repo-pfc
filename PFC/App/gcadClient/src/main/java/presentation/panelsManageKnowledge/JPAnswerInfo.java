package presentation.panelsManageKnowledge;

import internationalization.ApplicationInternationalization;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.business.knowledge.Answer;
import model.business.knowledge.AnswerArgument;

import org.jdesktop.application.Application;

import com.cloudgarden.layout.AnchorConstraint;

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
public class JPAnswerInfo extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7654096440940167508L;
	private JPanel panelAnswerInfo;
	private JComboBox cbArgument;
	private JLabel lblArgumentAnswer;
	private JLabel lblAnswerTitle;
	private JLabel lblDescriptionAnswer;
	private JTextField txtTitle;
	private JTextArea txtDescription;
	
	public JPAnswerInfo() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setLayout(null);
			this.setPreferredSize(new java.awt.Dimension(531, 172));
			this.setSize(531, 172);
			{
				panelAnswerInfo = new JPanel();
				this.add(panelAnswerInfo, new AnchorConstraint(41, 12, 67, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				panelAnswerInfo.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("answerInfo")));
				panelAnswerInfo.setLayout(null);
				panelAnswerInfo.setBounds(0, 0, 431, 174);
				panelAnswerInfo.setPreferredSize(new java.awt.Dimension(531, 172));
				panelAnswerInfo.setSize(531, 172);
				{
					ComboBoxModel cbCategoriesModel = 
						new DefaultComboBoxModel(
								new String[] { ApplicationInternationalization.getString("answerAgree"), ApplicationInternationalization.getString("answerDisagree"), ApplicationInternationalization.getString("answerNeutral") });
					cbArgument = new JComboBox();
					panelAnswerInfo.add(cbArgument, new AnchorConstraint(614, 722, 708, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					cbArgument.setModel(cbCategoriesModel);
					cbArgument.setBounds(122, 135, 184, 23);
				}
				{
					lblArgumentAnswer = new JLabel();
					panelAnswerInfo.add(lblArgumentAnswer, new AnchorConstraint(628, 255, 683, 28, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					lblArgumentAnswer.setName("lblCategoryProposal");
					lblArgumentAnswer.setBounds(12, 139, 104, 15);
					lblArgumentAnswer.setText(ApplicationInternationalization.getString("lblArgumentAnswer"));
				}
				{
					txtDescription = new JTextArea();
					//txtDescription.setInputVerifier(new NotEmptyValidator(parentD, txtDescription, ApplicationInternationalization.getString("fieldValidateEmpty")));
					panelAnswerInfo.add(txtDescription, new AnchorConstraint(210, 968, 585, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtDescription.setBounds(122, 58, 392, 65);
				}
				{
					txtTitle = new JTextField();
					//txtTitle.setInputVerifier(new NotEmptyValidator(parentD, txtTitle, "FallO" + ApplicationInternationalization.getString("fieldValidateEmpty")));
					panelAnswerInfo.add(txtTitle, new AnchorConstraint(148, 968, 242, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtTitle.setBounds(122, 23, 392, 23);
				}
				{
					lblDescriptionAnswer = new JLabel();
					panelAnswerInfo.add(lblDescriptionAnswer, new AnchorConstraint(268, 255, 323, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblDescriptionAnswer.setName("lblDescriptionProposal");
					lblDescriptionAnswer.setBounds(12, 58, 98, 16);
					lblDescriptionAnswer.setText(ApplicationInternationalization.getString("lblDescriptionAnswer"));
					
				}
				{
					lblAnswerTitle = new JLabel();
					panelAnswerInfo.add(lblAnswerTitle, new AnchorConstraint(88, 255, 134, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblAnswerTitle.setName("lblProposalTitle");
					lblAnswerTitle.setBounds(12, 23, 98, 16);
					lblAnswerTitle.setText(ApplicationInternationalization.getString("lblAnswerTitle"));
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getAnswerTitle() {
		return txtTitle.getText().trim();
	}
	
	public String getAnswerDescription() {
		return txtDescription.getText().trim();
	}
	
	public String getAnswerArgument() {
		return AnswerArgument.values()[cbArgument.getSelectedIndex()].name();
	}

	public void fillData(Answer data) {
		txtTitle.setText(data.getTitle());
		txtDescription.setText(data.getDescription());
		cbArgument.setSelectedItem(data.getArgument().toString());		
	}

	public boolean validData() {
		boolean result = true;
		if (txtDescription.getText().isEmpty() || txtTitle.getText().isEmpty() || cbArgument.getSelectedIndex() == -1) {
			result = false;
		}
		return result;
	}

}
