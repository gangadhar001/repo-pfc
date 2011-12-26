package presentation.CBR;

import internationalization.ApplicationInternationalization;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

import bussiness.control.ClientController;

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
	private JLabel lblProjects;
	private JComboBox cbProjects;
	private JCheckBox chkProject;
	private JButton btnClean;
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
			this.setSize(540, 625);
			{
				getContentPane().setLayout(null);
				this.setPreferredSize(new java.awt.Dimension(540, 625));
				{
					lblProjects = new JLabel();
					getContentPane().add(lblProjects);
					lblProjects.setBounds(322, 17, 37, 16);
					lblProjects.setName("lblProjects");
				}
				{
					cbProjects = new JComboBox();
					getContentPane().add(cbProjects);
					cbProjects.setBounds(403, 12, 119, 23);
					cbProjects.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							cbProjectsActionPerformed();
						}
					});
				}
				{
					chkProject = new JCheckBox();
					getContentPane().add(chkProject);
					chkProject.setBounds(12, 12, 196, 17);
					chkProject.setText(ApplicationInternationalization.getString("checkProject"));
					chkProject.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) {
							chkProjectItemStateChanged();
						}
					});

				}
				{
					btnForward = new JButton();
					getContentPane().add(btnForward);
					btnForward.setBounds(358, 565, 81, 23);
					btnForward.setName("btnForward");
					btnForward.setAction(getAppActionMap().get("Next"));
					btnForward.setText(ApplicationInternationalization.getString("btnNext"));
				}
				{
					btnCancel = new JButton();
					getContentPane().add(btnCancel);
					btnCancel.setBounds(446, 565, 77, 23);
					btnCancel.setName("btnCancel");
					btnCancel.setAction(getAppActionMap().get("Cancel"));
					btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
				}
				{
					panelProjectInformationCreate = new panelProjectInformation();
					getContentPane().add(panelProjectInformationCreate);
					panelProjectInformationCreate.setBounds(6, 49, 520, 505);
				}
				
				panelProjectInformationCreate.showData(new Project(), true);
				
				// Config Simil Panel
				panelConfigSimil = new panelConfigSimil(this);
				getContentPane().add(panelConfigSimil);
				panelConfigSimil.setBounds(6, 5, 520, 580);
				panelConfigSimil.setVisible(false);
			}
			{
				btnClean = new JButton();
				getContentPane().add(btnClean);
				btnClean.setBounds(6, 565, 81, 23);
				btnClean.setAction(getAppActionMap().get("Clean"));
				btnClean.setText(ApplicationInternationalization.getString("btnClear"));
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}	
	
	@Action
	public void Next() {
		if (panelProjectInformationCreate.isValidData()) {
			panelProjectInformationCreate.setVisible(false);
			this.setTitle(ApplicationInternationalization.getString("configSimiTitle"));
			panelConfigSimil.setProject(panelProjectInformationCreate.getProject());
			panelConfigSimil.setVisible(true);
			btnCancel.setVisible(false);
			btnForward.setVisible(false);
			this.validate();
			this.repaint();
		}
	}
	
	@Action
	public void Cancel() {
		this.dispose();
	}
	
	@Action
	public void Clean() {
		panelConfigSimil.clean();
		panelProjectInformationCreate.clean();
		chkProject.setSelected(false);
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
	
	private void chkProjectItemStateChanged() {
		boolean selected = chkProject.isSelected();
		if (!selected) {
			cbProjects.setSelectedIndex(-1);
			cbProjects.setEnabled(false);
			// panelProjectInformationCreate.clean();
		}
		else {
			fillCbProjects();
			cbProjects.setSelectedIndex(-1);
			cbProjects.setEnabled(true);
		}
	}

	// Fill projects combobox
	private void fillCbProjects() {
		List<Project> projects;
		try {
			projects = ClientController.getInstance().getProjects();
			for(Project p: projects) {
				cbProjects.addItem(p);
			}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cbProjectsActionPerformed() {
		Project p = (Project) cbProjects.getSelectedItem();
		if (p!= null && cbProjects.getSelectedIndex()!= -1)
			panelProjectInformationCreate.showData(p, true);
	}
}
