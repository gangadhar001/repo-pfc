package presentation.CBR;

import internationalization.ApplicationInternationalization;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

import bussiness.control.ClientController;

import presentation.JFMain;
import presentation.customComponents.panelProjectInformation;
import resources.ImagesUtilities;
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
	private JButton btnCleanCreate;
	private JButton btnCancel;
	private JButton btnForward;
	
	private InfiniteProgressPanel glassPane;
	private JFMain parentD;
	private Project caseToEval;
		
	public JDConfigProject(JFMain main) {
		super(main.getMainFrame());
		this.parentD = main;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
                  closeWin();
			}
         });
		
		initGUI();
	}

	protected void closeWin() {
		this.dispose();
		
	}
	
	private ActionMap getAppActionMap() {
	        return Application.getInstance().getContext().getActionMap(this);
	}
	
	private void initGUI() {
		try {
			setIconImage(ImagesUtilities.loadCompatibleImage("icono.png"));
		} catch (IOException e1) { }
		
		try {
			setResizable(false);
			this.glassPane = new InfiniteProgressPanel(ApplicationInternationalization.getString("glassProcessing"));
	        this.setGlassPane(glassPane);
	        
		
			this.setTitle(ApplicationInternationalization.getString("configProjectTitle"));
			this.setSize(540, 590);
			{
				getContentPane().setLayout(null);
				this.setPreferredSize(new java.awt.Dimension(540, 590));
				{
					lblProjects = new JLabel();
					getContentPane().add(lblProjects);
					lblProjects.setBounds(322, 17, 75, 16);
					lblProjects.setName("lblProjects");
					lblProjects.setText(ApplicationInternationalization.getString("lblProjects"));
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
					cbProjects.setEnabled(false);
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
					btnForward.setBounds(360, 526, 81, 20);
					btnForward.setName("btnForward");
					btnForward.setAction(getAppActionMap().get("Next"));
					btnForward.setText(ApplicationInternationalization.getString("btnNext"));
					btnForward.setSize(77, 23);
				}
				{
					btnCancel = new JButton();
					getContentPane().add(btnCancel);
					btnCancel.setBounds(446, 526, 77, 23);
					btnCancel.setName("btnCancel");
					btnCancel.setAction(getAppActionMap().get("Cancel"));
					btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
				}
				{
					panelProjectInformationCreate = new panelProjectInformation();
					getContentPane().add(panelProjectInformationCreate);
					panelProjectInformationCreate.setBounds(6, 49, 520, 471);
				}
				
				panelProjectInformationCreate.showData(new Project(), true, false, 360);
				
				// Config Simil Panel
				panelConfigSimil = new panelConfigSimil(this);
				getContentPane().add(panelConfigSimil);
				panelConfigSimil.setBounds(6, 5, 520, 515);
				panelConfigSimil.setSize(520, 557);
				panelConfigSimil.setVisible(false);
			}
			{
				btnCleanCreate = new JButton();
				getContentPane().add(btnCleanCreate);
				btnCleanCreate.setBounds(10, 527, 77, 23);
				btnCleanCreate.setAction(getAppActionMap().get("Clean"));
				btnCleanCreate.setText(ApplicationInternationalization.getString("btnClear"));
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}	
	
	@Action
	public void Next() {
		if (panelProjectInformationCreate.isValidData(true)) {
			chkProject.setVisible(false);
			cbProjects.setVisible(false);
			lblProjects.setVisible(false);
			btnCleanCreate.setVisible(false);
			panelProjectInformationCreate.setVisible(false);
			this.setTitle(ApplicationInternationalization.getString("configSimiTitle"));
			if (caseToEval == null)
				caseToEval = panelProjectInformationCreate.getProject();
			panelConfigSimil.setProject(caseToEval);
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
		if (panelConfigSimil != null)
			panelConfigSimil.Clean();
		panelProjectInformationCreate.clean(false);
		chkProject.setSelected(false);
		caseToEval = null;
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
		chkProject.setVisible(true);
		cbProjects.setVisible(true);
		lblProjects.setVisible(true);
		btnCleanCreate.setVisible(true);
		this.validate();
		this.repaint();		
	}	
	
	private void chkProjectItemStateChanged() {
		boolean selected = chkProject.isSelected();
		if (!selected) {
			cbProjects.setSelectedIndex(-1);
			cbProjects.setEnabled(false);
			panelProjectInformationCreate.clean(false);
		}
		else {
			fillCbProjects();
			cbProjects.setSelectedIndex(0);
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
		if (p!= null && cbProjects.getSelectedIndex()!= -1) {
			panelProjectInformationCreate.showData(p, true, false, 360);
			caseToEval = p;
		}
		
	}

	public JFMain getParentD() {
		return parentD;
	}
	
	
}
