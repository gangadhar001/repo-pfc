package presentation.panelsManageKnowledge;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import javax.swing.WindowConstants;
import org.jdesktop.application.Application;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

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
	private JLabel lblCategoryAnswer;
	private JLabel lblAnswerTitle;
	private JLabel lblDescriptionAnswer;
	private JTextField txtTitle;
	private JTextPane txtDescription;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
	}
	
	public JPAnswerInfo() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setLayout(null);
			this.setPreferredSize(new java.awt.Dimension(431, 174));
			this.setSize(405, 166);
			{
				panelAnswerInfo = new JPanel();
				this.add(panelAnswerInfo, new AnchorConstraint(41, 12, 67, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				panelAnswerInfo.setBorder(BorderFactory.createTitledBorder("Answer Info"));
				panelAnswerInfo.setLayout(null);
				panelAnswerInfo.setBounds(0, 0, 431, 174);
				panelAnswerInfo.setPreferredSize(new java.awt.Dimension(431, 174));
				{
					ComboBoxModel cbCategoriesModel = 
						new DefaultComboBoxModel(
								new String[] { "Analysis", "Design" });
					cbArgument = new JComboBox();
					panelAnswerInfo.add(cbArgument, new AnchorConstraint(614, 722, 708, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					cbArgument.setModel(cbCategoriesModel);
					cbArgument.setBounds(122, 135, 184, 23);
				}
				{
					lblCategoryAnswer = new JLabel();
					panelAnswerInfo.add(lblCategoryAnswer, new AnchorConstraint(628, 255, 683, 28, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					lblCategoryAnswer.setName("lblCategoryProposal");
					lblCategoryAnswer.setBounds(12, 139, 104, 15);
				}
				{
					txtDescription = new JTextPane();
					panelAnswerInfo.add(txtDescription, new AnchorConstraint(210, 968, 585, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtDescription.setBounds(122, 58, 270, 65);
				}
				{
					txtTitle = new JTextField();
					panelAnswerInfo.add(txtTitle, new AnchorConstraint(148, 968, 242, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtTitle.setBounds(122, 23, 270, 23);
				}
				{
					lblDescriptionAnswer = new JLabel();
					panelAnswerInfo.add(lblDescriptionAnswer, new AnchorConstraint(268, 255, 323, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblDescriptionAnswer.setName("lblDescriptionProposal");
					lblDescriptionAnswer.setBounds(12, 58, 98, 16);
				}
				{
					lblAnswerTitle = new JLabel();
					panelAnswerInfo.add(lblAnswerTitle, new AnchorConstraint(88, 255, 134, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblAnswerTitle.setName("lblProposalTitle");
					lblAnswerTitle.setBounds(12, 23, 98, 16);
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
	
	public String getAnswerCategory() {
		return cbArgument.getSelectedItem().toString();
	}

}
