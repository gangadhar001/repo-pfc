package presentation;
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
public class JPTopicInfo extends javax.swing.JPanel {
	private JPanel panelTopicInfo;
	private JLabel lblTopicTitle;
	private JLabel lblDescriptionTopic;
	private JTextField txtTitle;
	private JTextPane txtDescription;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
	}
	
	public JPTopicInfo() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setLayout(null);
			this.setPreferredSize(new java.awt.Dimension(405, 166));
			this.setSize(439, 317);
			{
				panelTopicInfo = new JPanel();
				this.add(panelTopicInfo, new AnchorConstraint(41, 12, 67, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				panelTopicInfo.setBorder(BorderFactory.createTitledBorder("Topic Info"));
				panelTopicInfo.setLayout(null);
				panelTopicInfo.setBounds(0, 0, 405, 137);
				{
					txtDescription = new JTextPane();
					panelTopicInfo.add(txtDescription, new AnchorConstraint(210, 968, 585, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtDescription.setBounds(122, 58, 270, 65);
				}
				{
					txtTitle = new JTextField();
					panelTopicInfo.add(txtTitle, new AnchorConstraint(148, 968, 242, 281, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					txtTitle.setBounds(122, 23, 270, 23);
				}
				{
					lblDescriptionTopic = new JLabel();
					panelTopicInfo.add(lblDescriptionTopic, new AnchorConstraint(268, 255, 323, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblDescriptionTopic.setName("lblDescriptionTopic");
					lblDescriptionTopic.setBounds(12, 58, 98, 16);
				}
				{
					lblTopicTitle = new JLabel();
					panelTopicInfo.add(lblTopicTitle, new AnchorConstraint(88, 255, 134, 28, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					lblTopicTitle.setName("lblTopicTitle");
					lblTopicTitle.setBounds(12, 23, 98, 16);
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

}
