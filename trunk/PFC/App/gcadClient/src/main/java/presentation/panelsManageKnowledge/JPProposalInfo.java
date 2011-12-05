package presentation.panelsManageKnowledge;

import internationalization.ApplicationInternationalization;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import model.business.knowledge.Proposal;

import org.jdesktop.application.Application;

import resources.NotEmptyValidator;

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
public class JPProposalInfo extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1078493997822345023L;
	private JPanel panelProposalInfo;
	private JComboBox cbCategories;
	private JLabel lblCategoryProposal;
	private JLabel lblProposalTitle;
	private JLabel lblDescriptionProposal;
	private JTextField txtTitle;
	private JTextPane txtDescription;
	private JDialog parentD;
	
	public JPProposalInfo(JDialog parent) {
		super();
		this.parentD = parent;
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setLayout(null);
			this.setPreferredSize(new java.awt.Dimension(531, 172));
			this.setSize(531, 172);
			{
				panelProposalInfo = new JPanel();
				this.add(panelProposalInfo, new AnchorConstraint(41, 12, 67, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				panelProposalInfo.setBorder(BorderFactory.createTitledBorder("Proposal Info"));
				panelProposalInfo.setLayout(null);
				panelProposalInfo.setBounds(0, 0, 531, 174);
				panelProposalInfo.setSize(531, 172);
				panelProposalInfo.setPreferredSize(new java.awt.Dimension(531, 172));
				{
					ComboBoxModel cbCategoriesModel = 
						new DefaultComboBoxModel(
								new String[] { "Analysis", "Design", "Development", "Testing"});
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
					lblCategoryProposal.setText(ApplicationInternationalization.getString("lblCategoryAnswer"));
				}
				{
					txtDescription = new JTextPane();
					panelProposalInfo.add(txtDescription, new AnchorConstraint(210, 968, 585, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtDescription.setBounds(122, 58, 392, 65);
					txtDescription.setInputVerifier(new NotEmptyValidator(parentD, txtDescription, ApplicationInternationalization.getString("fieldValidateEmpty")));
				}
				{
					txtTitle = new JTextField();
					panelProposalInfo.add(txtTitle, new AnchorConstraint(148, 968, 242, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtTitle.setBounds(122, 23, 392, 23);
					txtTitle.setInputVerifier(new NotEmptyValidator(parentD, txtTitle, ApplicationInternationalization.getString("fieldValidateEmpty")));
				}
				{
					lblDescriptionProposal = new JLabel();
					panelProposalInfo.add(lblDescriptionProposal, new AnchorConstraint(268, 255, 323, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblDescriptionProposal.setName("lblDescriptionProposal");
					lblDescriptionProposal.setBounds(12, 58, 98, 16);
					lblDescriptionProposal.setText(ApplicationInternationalization.getString("lblDescriptionProposal"));
				}
				{
					lblProposalTitle = new JLabel();
					panelProposalInfo.add(lblProposalTitle, new AnchorConstraint(88, 255, 134, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblProposalTitle.setName("lblProposalTitle");
					lblProposalTitle.setBounds(12, 23, 98, 16);
					lblProposalTitle.setText(ApplicationInternationalization.getString("lblProposalTitle"));
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

	public void fillData(Proposal data) {
		txtTitle.setText(data.getTitle());
		txtDescription.setText(data.getDescription());
		cbCategories.setSelectedItem(data.getCategory().toString());		
	}

}
