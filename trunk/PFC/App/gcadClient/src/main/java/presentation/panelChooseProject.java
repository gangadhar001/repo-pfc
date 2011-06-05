package presentation;

import internationalization.ApplicationInternationalization;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import model.business.knowledge.Project;

import org.jdesktop.application.Application;

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
public class panelChooseProject extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -565665707597623793L;
	private JLabel lblChoose;
	private JComboBox cbProjects;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
		
	public panelChooseProject() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(326, 97));
			this.setLayout(null);
			{
				lblChoose = new JLabel();
				this.add(lblChoose);
				lblChoose.setBounds(12, 38, 103, 16);
				lblChoose.setName("lblChoose");
				lblChoose.setText(ApplicationInternationalization.getString("lblChooseProject"));
			}
			{
				cbProjects = new JComboBox();
				this.add(cbProjects);
				cbProjects.setBounds(120, 34, 179, 24);
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Set the projects of the logged user
	public void setProjects(List<Project> projects) {
		for(Project p : projects) {
			cbProjects.addItem(p);
		}
		cbProjects.setSelectedIndex(0);
	}

	public int getProjectId() {
		return ((Project)cbProjects.getSelectedItem()).getId();
	}

}
