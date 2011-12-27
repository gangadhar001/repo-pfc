package presentation.panelsManageKnowledge;

import internationalization.ApplicationInternationalization;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.business.knowledge.Topic;

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
public class JPTopicInfo extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -429329559937627955L;
	private JPanel panelTopicInfo;
	private JTextArea txtDescription;
	private JLabel lblTopicTitle;
	private JLabel lblDescriptionTopic;
	private JTextField txtTitle;
	private JDialog parentD;
	
	public JPTopicInfo(JDialog parent) {
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
				panelTopicInfo = new JPanel();
				this.add(panelTopicInfo, new AnchorConstraint(41, 12, 67, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				panelTopicInfo.setBorder(BorderFactory.createTitledBorder("Topic Info"));
				panelTopicInfo.setLayout(null);
				panelTopicInfo.setBounds(0, 0, 405, 137);
				panelTopicInfo.setSize(531, 172);
				panelTopicInfo.setPreferredSize(new java.awt.Dimension(531, 172));
				{
					txtDescription = new JTextArea();
					panelTopicInfo.add(txtDescription);
					txtDescription.setBounds(122, 56, 392, 105);
					//txtDescription.setInputVerifier(new NotEmptyValidator(parentD, txtDescription, ApplicationInternationalization.getString("fieldValidateEmpty")));
				}
				{
					txtTitle = new JTextField();
					//txtTitle.setInputVerifier(new NotEmptyValidator(parentD, txtTitle, ApplicationInternationalization.getString("fieldValidateEmpty")));
					panelTopicInfo.add(txtTitle, new AnchorConstraint(148, 968, 242, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtTitle.setBounds(122, 23, 392, 23);
				}
				{
					lblDescriptionTopic = new JLabel();
					panelTopicInfo.add(lblDescriptionTopic, new AnchorConstraint(268, 255, 323, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblDescriptionTopic.setName("lblDescriptionTopic");
					lblDescriptionTopic.setBounds(12, 58, 98, 16);
					lblDescriptionTopic.setText(ApplicationInternationalization.getString("lblDescriptionTopic"));
				}
				{
					lblTopicTitle = new JLabel();
					panelTopicInfo.add(lblTopicTitle, new AnchorConstraint(88, 255, 134, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblTopicTitle.setName("lblTopicTitle");
					lblTopicTitle.setBounds(12, 23, 98, 16);
					lblTopicTitle.setText(ApplicationInternationalization.getString("lblTitleTopic"));
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getTopicTitle() {
		return txtTitle.getText().trim();
	}
	
	public String getTopicDescription() {
		return txtDescription.getText().trim();
	}

	public void fillData(Topic data) {
		txtTitle.setText(data.getTitle());
		txtDescription.setText(data.getDescription());
		
	}
	
	public boolean validData() {
		boolean result = true;
		if (txtDescription.getText().isEmpty() || txtTitle.getText().isEmpty()) {
			result = false;
		}
		return result;
	}

}
