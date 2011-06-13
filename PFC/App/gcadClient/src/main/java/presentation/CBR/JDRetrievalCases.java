package presentation.CBR;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;

import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.panelProjectInformation;

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
public class JDRetrievalCases extends javax.swing.JDialog {
	private panelProjectInformation currentPanel;
	private JButton btnBackward;
	private JButton btnForward;
	private JLabel lblNumberCases;

	/**
	* Auto-generated main method to display this JDialog
	*/		
	
	private List<Project> cases;
	private int currentProject;
	
	public JDRetrievalCases(JFrame frame) {
		super(frame);
		cases = new ArrayList<Project> ();
		currentProject = 1;
		Project p1 = new Project("p1", "pa", new Date(), new Date(), 2000.1, 100, "aa", "C#", 20);
		Project p2 = new Project("p2", "pb", new Date(), new Date(), 202575, 100, "aa", "Java", 20);
		cases.add(p1);
		cases.add(p2);
		initGUI();
		currentPanel.showData(cases.get(currentProject-1), false);
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	private void initGUI() {		
		try {
			{
				getContentPane().setLayout(null);
				{
					currentPanel = new panelProjectInformation();
					getContentPane().add(currentPanel);
					currentPanel.setBounds(0, 0, 328, 410);
				}
				{
					lblNumberCases = new JLabel();
					getContentPane().add(lblNumberCases);
					lblNumberCases.setBounds(117, 422, 79, 16);
					lblNumberCases.setName("lblNumberCases");
					lblNumberCases.setText("Case " + currentProject+"/"+cases.size());
				}
				{
					btnForward = new JButton();
					getContentPane().add(btnForward);
					btnForward.setBounds(196, 419, 45, 23);
					btnForward.setAction(getAppActionMap().get("Forward"));
					btnForward.setName("btnForward");
				}
				{
					btnBackward = new JButton();
					getContentPane().add(btnBackward);
					btnBackward.setBounds(65, 419, 43, 23);
					btnBackward.setAction(getAppActionMap().get("Backward"));
					btnBackward.setName("btnBackward");
					btnBackward.setEnabled(false);
				}
			}
			this.setSize(551, 542);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action
	public void Forward() {
		if (currentProject < cases.size()) {
			currentProject++;
			btnBackward.setEnabled(true);
			currentPanel.showData(cases.get(currentProject-1), false);
			lblNumberCases.setText("Case " + currentProject+"/"+cases.size());
		}
		if(currentProject == cases.size()) {
			btnForward.setEnabled(false);
		}
	}
	
	@Action
	public void Backward() {
		if (currentProject > 0) {
			currentProject--;
			btnForward.setEnabled(true);
			currentPanel.showData(cases.get(currentProject-1), false);
			lblNumberCases.setText("Case " + currentProject+"/"+cases.size());
		}
		if(currentProject == 1) {
			btnBackward.setEnabled(false);
		}
	}
}
