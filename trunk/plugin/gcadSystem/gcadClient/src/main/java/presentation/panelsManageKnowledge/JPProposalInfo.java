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
public class JPProposalInfo extends javax.swing.JPanel {
	private JPanel panelProposalInfo;
	private JComboBox cbCategories;
	private JLabel lblCategoryProposal;
	private JLabel lblProposalTitle;
	private JLabel lblDescriptionProposal;
	private JTextField txtTitle;
	private JTextPane txtDescription;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
	}
	
	public JPProposalInfo() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setLayout(null);
			this.setPreferredSize(new java.awt.Dimension(431, 174));
			this.setSize(405, 166);
			{
				panelProposalInfo = new JPanel();
				this.add(panelProposalInfo, new AnchorConstraint(41, 12, 67, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				panelProposalInfo.setBorder(BorderFactory.createTitledBorder("Proposal Info"));
				panelProposalInfo.setLayout(null);
				panelProposalInfo.setBounds(0, 0, 405, 166);
				panelProposalInfo.setPreferredSize(new java.awt.Dimension(431, 174));
				panelProposalInfo.setSize(431, 174);
				{
					ComboBoxModel cbCategoriesModel = 
						new DefaultComboBoxModel(
								new String[] { "Analysis", "Design" });
					cbCategories = new JComboBox();
					panelProposalInfo.add(cbCategories, new AnchorConstraint(614, 722, 708, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					cbCategories.setModel(cbCategoriesModel);
					cbCategories.setBounds(122, 135, 184, 23);
				}
				{
					lblCategoryProposal = new JLabel();
					panelProposalInfo.add(lblCategoryProposal, new AnchorConstraint(628, 255, 683, 28, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					lblCategoryProposal.setName("lblCategoryProposal");
					lblCategoryProposal.setBounds(12, 139, 104, 15);
				}
				{
					txtDescription = new JTextPane();
					panelProposalInfo.add(txtDescription, new AnchorConstraint(210, 968, 585, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtDescription.setBounds(122, 58, 270, 65);
				}
				{
					txtTitle = new JTextField();
					panelProposalInfo.add(txtTitle, new AnchorConstraint(148, 968, 242, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtTitle.setBounds(122, 23, 270, 23);
				}
				{
					lblDescriptionProposal = new JLabel();
					panelProposalInfo.add(lblDescriptionProposal, new AnchorConstraint(268, 255, 323, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblDescriptionProposal.setName("lblDescriptionProposal");
					lblDescriptionProposal.setBounds(12, 58, 98, 16);
				}
				{
					lblProposalTitle = new JLabel();
					panelProposalInfo.add(lblProposalTitle, new AnchorConstraint(88, 255, 134, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblProposalTitle.setName("lblProposalTitle");
					lblProposalTitle.setBounds(12, 23, 98, 16);
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getProposalTitle() {
		return txtTitle.getText().trim();
	}
	
	public String getProposalDescription() {
		return txtDescription.getText().trim();
	}
	
	public String getProposalCategory() {
		return cbCategories.getSelectedItem().toString();
	}

}
