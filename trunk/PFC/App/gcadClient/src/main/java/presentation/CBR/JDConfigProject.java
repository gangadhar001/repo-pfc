package presentation.CBR;

import internationalization.ApplicationInternationalization;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.customComponents.panelProjectInformation;
import resources.InfiniteProgressPanel;

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
public class JDConfigProject extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6500564343875421781L;
	private panelProjectInformation panelProjectInformationCreate;
	private JPanel panelCreateProject;
	private panelConfigSimil panelConfigSimil;
	private JButton btnCancel;
	private JButton btnForward;
	
	private InfiniteProgressPanel glassPane;
		
	public JDConfigProject(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
	        return Application.getInstance().getContext().getActionMap(this);
	}
	
	private void initGUI() {
		try {
			this.setResizable(false);
			this.setTitle(ApplicationInternationalization.getString("configProjectTitle"));
			{
				getContentPane().setLayout(null);
				{
					// Create Project Panel
					panelCreateProject = new JPanel();
					getContentPane().add(panelCreateProject);
					panelConfigSimil.setBounds(14, 37, 380, 441);
					panelCreateProject.setLayout(null);
					{
						panelProjectInformationCreate = new panelProjectInformation(this);
						panelCreateProject.add(panelProjectInformationCreate);
						panelProjectInformationCreate.setBounds(12, 12, 533, 450);
					}
				}
				
				panelProjectInformationCreate.showData(new Project(), true);
				
				// Config Simil Panel
				panelConfigSimil = new panelConfigSimil(this);
				getContentPane().add(panelConfigSimil);
				panelConfigSimil.setBounds(14, 37, 380, 441);
				panelConfigSimil.setVisible(false);

			}
			{
				btnForward = new JButton();
				getContentPane().add(btnForward);
				btnForward.setBounds(335, 502, 57, 23);
				btnForward.setName("btnForward");
				btnForward.setAction(getAppActionMap().get("Next"));
				btnForward.setText(ApplicationInternationalization.getString("bntNext"));
			}
			{
				btnCancel = new JButton();
				getContentPane().add(btnCancel);
				btnCancel.setBounds(265, 502, 50, 23);
				btnCancel.setName("btnCancel");
				btnCancel.setAction(getAppActionMap().get("Cancel"));
				btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
			}
			this.setSize(415, 570);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}	
	
	@Action
	public void Next() {
		panelCreateProject.setVisible(false);
		this.setTitle(ApplicationInternationalization.getString("configSimiTitle"));
		panelConfigSimil.setVisible(true);
		panelConfigSimil.setProject(panelProjectInformationCreate.getProject());
	}
	
	@Action
	public void Cancel() {
		this.dispose();
	}

	public JPanel getPanelCreateProject() {
		return panelCreateProject;
	}

	public void setPanelCreateProject(JPanel panelCreateProject) {
		this.panelCreateProject = panelCreateProject;
	}

	public InfiniteProgressPanel getGlassPane() {
		return glassPane;
	}	
}