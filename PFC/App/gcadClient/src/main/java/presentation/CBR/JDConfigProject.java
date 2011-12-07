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
			this.glassPane = new InfiniteProgressPanel(ApplicationInternationalization.getString("glassProcessing"));
	        this.setGlassPane(glassPane);
	        
			this.setResizable(false);
			this.setTitle(ApplicationInternationalization.getString("configProjectTitle"));
			this.setSize(540, 595);
			{
				getContentPane().setLayout(null);
				this.setPreferredSize(new java.awt.Dimension(540, 595));
				{
					btnForward = new JButton();
					getContentPane().add(btnForward);
					btnForward.setBounds(358, 535, 81, 23);
					btnForward.setName("btnForward");
					btnForward.setAction(getAppActionMap().get("Next"));
					btnForward.setText(ApplicationInternationalization.getString("btnNext"));
				}
				{
					btnCancel = new JButton();
					getContentPane().add(btnCancel);
					btnCancel.setBounds(444, 535, 80, 23);
					btnCancel.setName("btnCancel");
					btnCancel.setAction(getAppActionMap().get("Cancel"));
					btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
				}
				{
					panelProjectInformationCreate = new panelProjectInformation(this);
					getContentPane().add(panelProjectInformationCreate);
					panelProjectInformationCreate.setBounds(6, 12, 520, 505);
				}
				
				panelProjectInformationCreate.showData(new Project(), true);
				
				// Config Simil Panel
				panelConfigSimil = new panelConfigSimil(this);
				getContentPane().add(panelConfigSimil);
				panelConfigSimil.setBounds(6, 12, 520, 557);
				panelConfigSimil.setVisible(false);
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}	
	
	@Action
	public void Next() {
		panelProjectInformationCreate.setVisible(false);
		this.setTitle(ApplicationInternationalization.getString("configSimiTitle"));
		panelConfigSimil.setProject(panelProjectInformationCreate.getProject());
		panelConfigSimil.setVisible(true);
		btnCancel.setVisible(false);
		btnForward.setVisible(false);
		this.validate();
		this.repaint();
	}
	
	@Action
	public void Cancel() {
		this.dispose();
	}

	public JPanel getPanelCreateProject() {
		return panelProjectInformationCreate;
	}

	public InfiniteProgressPanel getGlassPane() {
		return glassPane;
	}

	public void back() {
		this.setTitle(ApplicationInternationalization.getString("configProjectTitle"));
		panelConfigSimil.setVisible(false);
		panelProjectInformationCreate.setVisible(true);
		btnCancel.setVisible(true);
		btnForward.setVisible(true);
		this.validate();
		this.repaint();		
	}	
}
