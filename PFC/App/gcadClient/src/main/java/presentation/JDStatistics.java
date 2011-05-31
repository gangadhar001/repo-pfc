package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.business.knowledge.Project;
import model.business.knowledge.User;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdom.JDOMException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DefaultPieDataset;

import bussiness.control.ClientController;
import bussiness.control.StatisticsGenerator;
import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

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
public class JDStatistics extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6797556749108051231L;
	private JPanel panelCharts;
	private JScrollPane jScrollPane1;
	private JLabel lblUser;
	private JComboBox cbProjects;
	private JLabel lblProject;
	private JTextArea txtDescription;
	private JScrollPane jScrollPane2;
	private JComboBox cbRange;
	private JComboBox cbUsers;
	private JLabel lblDate;
	private JLabel lblDescription;
	private JComboBox cbCharts;
	private JLabel lblCharType;
	private JButton btnCreate;
	private JButton btnCancel;
	private JCheckBox chk3D;
	private JLabel lblEffect;
	private JRadioButton rbHideLegend;
	private JRadioButton rbShowLegend;
	private JPanel panelCustomize;
	private JPanel panelConfiguration;
	private ButtonGroup buttonGroup;
	private JLabel lblLegend;
	private JTextField txtTitleChart;
	private JLabel lblTitleChart;
	private JPanel panelConfigurationChart;
	private String selectedChartType;
	private ChartInfo selectedChart;
	private boolean oneProject;
	private boolean oneUser;
	private boolean percentage;
	private boolean historical;
	private String resource;
	private JFrame frame;
	private ChartPanel chartPanel;
	
	// This class is used to store the id, name and description of a chart.
	private class ChartInfo {
		private String Id;
		private String name;
		private String desc;
		
		public ChartInfo(String id, String name, String desc) {
			this.Id = id;
			this.name = name;
			this.desc = desc;
		}	
		
		public String getId() {
			return Id;
		}
		
		public String getDescription() {
			return desc;
		}
		
		public String toString() {
			return name;
		}		
	}
		
	/**
	* Auto-generated main method to display this JDialog
	*/
	public JDStatistics(JFrame frame) {
		super(frame);
		this.frame = frame;
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	private void initGUI() {
		try {
			{
				btnCreate = new JButton();
				getContentPane().add(btnCreate);
				btnCreate.setBounds(333, 378, 88, 23);
				btnCreate.setName("btnCreate");
				btnCreate.setAction(getAppActionMap().get("Generate"));
				btnCreate.setText(ApplicationInternationalization.getString("GenerateButton"));
			}
			{
				btnCancel = new JButton();
				getContentPane().add(btnCancel);
				btnCancel.setBounds(432, 378, 79, 23);
				btnCancel.setName("btnCancel");
				btnCancel.setAction(getAppActionMap().get("Cancel"));
				btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
			}
			{
				getContentPane().setLayout(null);
				{
					jScrollPane1 = new JScrollPane();
					getContentPane().add(jScrollPane1);
					jScrollPane1.setBounds(12, 12, 157, 352);
					{
						panelCharts = new JPanel();
						jScrollPane1.setViewportView(panelCharts);
						panelCharts.setBounds(12, 12, 151, 286);
						panelCharts.setPreferredSize(new java.awt.Dimension(148, 255));
					}
				}
				{
					panelConfigurationChart = new JPanel();
					getContentPane().add(panelConfigurationChart);
					panelConfigurationChart.setBounds(175, 12, 335, 360);
					panelConfigurationChart.setLayout(null);
					{
						panelConfiguration = new JPanel();
						panelConfigurationChart.add(panelConfiguration);
						panelConfiguration.setBounds(12, 0, 322, 240);
						panelConfiguration.setLayout(null);
						panelConfiguration.setBorder(BorderFactory.createTitledBorder(""));
						panelConfiguration.setLayout(null);
						panelConfiguration.add(getLblCharType());
						panelConfiguration.add(getCbCharts());
						panelConfiguration.add(getJScrollPane2());
						panelConfiguration.add(getLblDescription());
						panelConfiguration.add(getLblProject());
						panelConfiguration.add(getCbProjects());
						panelConfiguration.add(getLblUser());
						panelConfiguration.add(getLblDate());
						panelConfiguration.add(getCbUsers());
						panelConfiguration.add(getCbRange());
					}
					{
						panelCustomize = new JPanel();
						panelConfigurationChart.add(panelCustomize);
						panelCustomize.setBounds(12, 252, 322, 100);
						panelCustomize.setLayout(null);
						panelCustomize.setBorder(BorderFactory.createTitledBorder(""));
						{
							lblLegend = new JLabel();
							panelCustomize.add(lblLegend);
							lblLegend.setBounds(11, 39, 57, 16);
							lblLegend.setName("lblLegend");
							lblLegend.setText(ApplicationInternationalization.getString("legendChart"));
						}
						{
							txtTitleChart = new JTextField();
							panelCustomize.add(txtTitleChart);
							txtTitleChart.setBounds(86, 10, 228, 23);
						}
						{
							lblTitleChart = new JLabel();
							panelCustomize.add(lblTitleChart);
							lblTitleChart.setBounds(11, 10, 45, 16);
							lblTitleChart.setName("lblTitleChart");
							lblTitleChart.setText(ApplicationInternationalization.getString("titleChart"));
						}
						{
							rbShowLegend = new JRadioButton();
							panelCustomize.add(rbShowLegend);
							rbShowLegend.setBounds(86, 37, 75, 20);
							rbShowLegend.setName("rbShowLegend");
							rbShowLegend.setText(ApplicationInternationalization.getString("showLegendChart"));
						}
						{
							rbHideLegend = new JRadioButton();
							panelCustomize.add(rbHideLegend);
							rbHideLegend.setBounds(161, 37, 85, 20);
							rbHideLegend.setName("rbHideLegend");
							rbHideLegend.setText(ApplicationInternationalization.getString("hideLegendChart"));
						}
						{
							lblEffect = new JLabel();
							panelCustomize.add(lblEffect);
							lblEffect.setBounds(11, 66, 77, 21);
							lblEffect.setName("lblEffect");
							lblEffect.setText(ApplicationInternationalization.getString("effectChart"));
						}
						{
							chk3D = new JCheckBox();
							panelCustomize.add(chk3D);
							chk3D.setBounds(88, 64, 52, 23);
							chk3D.setName("chk3D");
							chk3D.setText(ApplicationInternationalization.getString("chk3D"));
						}
					}
					getButtonGroup().add(rbHideLegend);
					getButtonGroup().add(rbShowLegend);
					
					// Add buttons for each type of chart
					createChartButtons();
									
					setEnabledPanelConfiguration(false);
					setEnabledPanelCustomize(false);
					
				}
			}
			this.setSize(538, 451);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void setEnabledPanelCustomize(boolean b) {
		txtTitleChart.setEnabled(b);
		rbHideLegend.setEnabled(b);
		rbShowLegend.setEnabled(b);			
	}

	private void setEnabledPanelConfiguration(boolean value) {
		cbProjects.setEnabled(value);
		cbUsers.setEnabled(value);
		cbRange.setEnabled(value);
		cbCharts.setEnabled(value);
	}

	private void createChartButtons() throws JDOMException, IOException {
		// Get types of chart from XML
		List<String> types = StatisticsGenerator.getInstance().getChartsTypes();
		for (String s: types) {		
			JButton button = new JButton();
			button.setSize(new Dimension(100, 100));
			button.setPreferredSize(button.getSize());
			button.setName(s);
			button.setText(ApplicationInternationalization.getString(s+"Chart"));
//			button.setIcon(new ImageIcon(image));
			button.setHorizontalTextPosition(JButton.CENTER);
			button.setVerticalTextPosition(JButton.BOTTOM);
			button.setContentAreaFilled(false);
			button.setFocusPainted(false);
			button.setBorderPainted(true);					
			// Save button icon
//			ImagesUtilities.addImageButton(button.getName(), image);
//			button.addMouseListener(new MouseAdapter() {
//				public void mouseExited(MouseEvent evt) {
//					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  
//					ImagesUtilities.decreaseImageBrightness((JButton) evt.getSource());
//				}
//	
//				public void mouseEntered(MouseEvent evt) {
//					setCursor(new Cursor(Cursor.HAND_CURSOR));  
//					ImagesUtilities.increaseImageBrightness((JButton) evt.getSource());
//				}
//			});
			button.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					// Clean selection from other buttons
					for (Component c: panelCharts.getComponents())
						if (c instanceof JButton) 
							((JButton)c).setContentAreaFilled(false);
					((JButton)e.getSource()).setContentAreaFilled(true);
					selectedChartType = ((JButton)e.getSource()).getName();
					try {
						cbCharts.setEnabled(true);
						clearCombos();
						// Fill combobox of charts
						fillComboChartTypes();
						// Fill combobox of projects
						fillComboProjects();
						setEnabledPanelCustomize(true);						
					} catch (JDOMException e1) {
						JOptionPane.showMessageDialog(frame, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(frame, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
					} catch (NonPermissionRole e1) {
						JOptionPane.showMessageDialog(frame, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
					} catch (NotLoggedException e1) {
						JOptionPane.showMessageDialog(frame, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(frame, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
					}
					if (((JButton)e.getSource()).getName().equals("pie")) {
						setRenderVisibility(true);
					}
				}
			});
			
			panelCharts.add(button);
		}		
	}
	
	protected void clearCombos() {
		cbCharts.removeAllItems();
		cbProjects.removeAllItems();
		cbUsers.removeAllItems();
	}

	// Load available charts into combobox 
	private void fillComboChartTypes() throws JDOMException, IOException {		
		List<String> chartsId = StatisticsGenerator.getInstance().getIdChart(selectedChartType);
		List<String> chartsName = StatisticsGenerator.getInstance().getIdNamesChart(selectedChartType);
		List<String> chartsDesc = StatisticsGenerator.getInstance().getIdDescChart(selectedChartType);
		ChartInfo chart = null;
		for (int i = 0; i< chartsId.size(); i++) {
			chart = new ChartInfo(chartsId.get(i), ApplicationInternationalization.getString(chartsName.get(i)), ApplicationInternationalization.getString(chartsDesc.get(i)));
			cbCharts.addItem(chart);
		}
		cbCharts.setSelectedIndex(0);
	}
	
	private void fillComboProjects() throws RemoteException, NonPermissionRole, NotLoggedException, SQLException, Exception {
		List<Project> projects = ClientController.getInstance().getProjects();
		for (Project p: projects) {
			cbProjects.addItem(p);
		}
		cbProjects.setSelectedIndex(-1);
	}

	private ButtonGroup getButtonGroup() {
		if(buttonGroup == null) {
			buttonGroup = new ButtonGroup();
		}
		return buttonGroup;
	}
	
	private JLabel getLblCharType() {
		if(lblCharType == null) {
			lblCharType = new JLabel();
			lblCharType.setBounds(10, 12, 74, 16);
			lblCharType.setName("lblCharType");
			lblCharType.setText(ApplicationInternationalization.getString("chartType"));
		}
		return lblCharType;
	}
	
	private JComboBox getCbCharts() {
		if(cbCharts == null) {
			cbCharts = new JComboBox();
			cbCharts.setBounds(83, 9, 229, 23);
			cbCharts.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					cbChartsActionPerformed(evt);
				}
			});
		}
		return cbCharts;
	}
	
	private JTextArea getTxtDescription() {
		if(txtDescription == null) {
			txtDescription = new JTextArea();
			txtDescription.setLineWrap(true);
			txtDescription.setWrapStyleWord(true);
			txtDescription.setBounds(84, 38, 229, 105);
			txtDescription.setName("txtDescription");
		}
		return txtDescription;
	}
	
	private JLabel getLblDescription() {
		if(lblDescription == null) {
			lblDescription = new JLabel();
			lblDescription.setBounds(10, 34, 69, 16);
			lblDescription.setName("lblDescription");
			lblDescription.setText(ApplicationInternationalization.getString("chartDescription"));
		}
		return lblDescription;
	}
	
	
	private JLabel getLblProject() {
		if(lblProject == null) {
			lblProject = new JLabel();
			lblProject.setBounds(10, 152, 69, 16);
			lblProject.setName("lblProject");
			lblProject.setText(ApplicationInternationalization.getString("chartProject"));
		}
		return lblProject;
	}
	
	private JComboBox getCbProjects() {
		if(cbProjects == null) {;
			cbProjects = new JComboBox();
			cbProjects.setBounds(84, 149, 229, 23);
			cbProjects.setName("cbProjects");
			cbProjects.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					cbProjectsActionPerformed(evt);
				}
			});
		}
		return cbProjects;
	}
	
	private JLabel getLblUser() {
		if(lblUser == null) {
			lblUser = new JLabel();
			lblUser.setBounds(10, 180, 50, 16);
			lblUser.setName("lblUser");
			lblUser.setText(ApplicationInternationalization.getString("chartUser"));
		}
		return lblUser;
	}
	
	private JLabel getLblDate() {
		if(lblDate == null) {
			lblDate = new JLabel();
			lblDate.setBounds(10, 208, 40, 16);
			lblDate.setName("lblDate");
			lblDate.setText(ApplicationInternationalization.getString("chartRange"));
		}
		return lblDate;
	}
	
	private JComboBox getCbUsers() {
		if(cbUsers == null) {
			cbUsers = new JComboBox();
			cbUsers.setBounds(84, 177, 229, 23);
		}
		return cbUsers;
	}
	
	private JComboBox getCbRange() {
		if(cbRange == null) {
			cbRange = new JComboBox();
			cbRange.setBounds(84, 205, 91, 23);
			cbRange.addItem(ApplicationInternationalization.getString("rangeAnnually"));
			cbRange.addItem(ApplicationInternationalization.getString("rangeMonthly"));
			cbRange.setSelectedIndex(-1);
		}
		return cbRange;
	}
	
	private JScrollPane getJScrollPane2() {
		if(jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(84, 38, 229, 106);
			jScrollPane2.setViewportView(getTxtDescription());
		}
		return jScrollPane2;
	}

	public void setRenderVisibility(boolean visible) {
		lblEffect.setVisible(visible);
		chk3D.setVisible(visible);
	}
	
	// Depending on the chosen chart, enabling or disabling components 
	private void cbChartsActionPerformed(ActionEvent evt) {
		try {
			if (cbCharts.getSelectedIndex()!= -1) {
				selectedChart = (ChartInfo) cbCharts.getSelectedItem();
				if (selectedChart.getId() != null) {
					setConfigurationChart();
					cbProjects.setEnabled(oneProject);
					cbUsers.setEnabled(oneUser);
					cbRange.setEnabled(historical);
					txtDescription.setText(selectedChart.getDescription());
				}
			}
		} catch (JDOMException e) {
			JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// When a project is selected, load users from that project
	private void cbProjectsActionPerformed(ActionEvent evt) {
		cbUsers.removeAllItems();
		if (cbProjects.getSelectedIndex() != -1) {
			List<User> users;
			try {
				users = ClientController.getInstance().getUsersProject((Project) cbProjects.getSelectedItem());
				for (User u: users) {
					cbUsers.addItem(u);
				}
				cbUsers.setSelectedIndex(-1);
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRole e) {
				JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}		
		}
	}	
	
	private void setConfigurationChart() throws JDOMException, IOException {
		oneProject = StatisticsGenerator.getInstance().isOneProject(selectedChart.getId());
		oneUser = StatisticsGenerator.getInstance().isOneUser(selectedChart.getId());
		percentage = StatisticsGenerator.getInstance().isPercentage(selectedChart.getId());
		historical = StatisticsGenerator.getInstance().isHistorical(selectedChart.getId());
		resource = StatisticsGenerator.getInstance().getResource(selectedChart.getId());		
	}

	@Action
	public void Cancel () {
		chartPanel = null;
		this.dispose();
	}
	
	// Action used to generate a chart
	@Action
	public void Generate () {		
		Project pro = null;
		User u = null;
		boolean isAnnually = true;
		AbstractDataset dataset = null;
		JFreeChart chart = null;
		
		// TODO: Validar que todos los campos esten rellenos
		
		if (cbRange.getSelectedIndex() == 1)
			isAnnually = false;
		
		try {
			if (oneProject) {
				pro = (Project) cbProjects.getSelectedItem();
				if (oneUser) {
					u = (User) cbUsers.getSelectedItem();
					if (historical) {	
						dataset = (AbstractDataset) StatisticsGenerator.getInstance().createDatasetEvolutionUser(pro, u, isAnnually); // Caso 3.3
						chart = generateLineChart(txtTitleChart.getText(), (DefaultCategoryDataset) dataset, "", "", false);
					}
				}
				else {
					if (historical) {
						dataset = (AbstractDataset) StatisticsGenerator.getInstance().createDatasetHistoricalProject(pro, isAnnually); // Caso 1.2
						chart = generateLineChart(txtTitleChart.getText(), (DefaultCategoryDataset) dataset, "", "", false);
					}
					else {
						dataset = StatisticsGenerator.getInstance().createDatasetProjectParticipation(pro, percentage); // Caso 1.1
						if (percentage)
							chart = generatePieChart(txtTitleChart.getText(), (DefaultPieDataset) dataset, false);
						else
							chart = generateBarChart(txtTitleChart.getText(), (DefaultCategoryDataset) dataset, "", "", false);
					}
				}
			}
			else {
				if (oneUser) {
					dataset = StatisticsGenerator.getInstance().createDatasetKnowledgeDeveloper(u, percentage); // Caso 3.2
					if (percentage)
						chart = generatePieChart(txtTitleChart.getText(), (DefaultPieDataset) dataset, false);
					else
						chart = generateBarChart(txtTitleChart.getText(), (DefaultCategoryDataset) dataset, "", "", false);
				}
				else {
					dataset = StatisticsGenerator.getInstance().createDatasetResourcesProject(resource); // Caso 2.1 y 2.2
					chart = generateBarChart(txtTitleChart.getText(), (DefaultCategoryDataset) dataset, "", "", false);
				}
			}
//			panelConfiguration.removeAll();
			chartPanel = new ChartPanel(chart);
			this.dispose();
//			panelConfiguration.add(chartPanel);
//			chartPanel.setBounds(0, 0, 323, 239);
//			panelConfiguration.validate();
//			panelConfiguration.repaint();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRole e) {
			JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}		
	}	
			
	private JFreeChart generateLineChart(String title, DefaultCategoryDataset dataset, String xAxisName, String yAxisName, boolean showLegend) {
		final JFreeChart chart = ChartFactory.createLineChart(
				title,      // chart title
	            xAxisName,                   // domain axis label
	            yAxisName,                  // range axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL, // orientation
	            showLegend,                     // include legend
	            true,                     // tooltips
	            false                     // urls
	        );
		return chart;  	    
	}
	
	private JFreeChart generateBarChart(String title, DefaultCategoryDataset dataset, String xAxisName, String yAxisName, boolean showLegend) {
		final JFreeChart chart = ChartFactory.createBarChart(
				title,      // chart title
	            xAxisName,                   // domain axis label
	            yAxisName,                  // range axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL, // orientation
	            showLegend,                     // include legend
	            true,                     // tooltips
	            false                     // urls
	        );
		return chart;
	}

	private JFreeChart generatePieChart(String title, DefaultPieDataset dataset, boolean showLegend) {
		final JFreeChart chart = ChartFactory.createPieChart(
				title,
				dataset,
				showLegend, // legend?
				true, // tooltips?
				false // URLs?
			);
		return chart;
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}
}
