package presentation;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import internationalization.ApplicationInternationalization;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.User;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

import presentation.utils.ImagesUtilities;

import bussiness.control.ClientController;
import bussiness.control.StatisticsGenerator;

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
	private JComboBox cbMin;
	private JComboBox cbMax;
	private JLabel lblMax;
	private JLabel lblMin;
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
	private Document doc;

	/**
	* Auto-generated main method to display this JDialog
	*/
	
	// This class is used to store the id, name and description of a chart.
	private class ChartInfo {
		private String Id;
		private String name;
		private String desc;
		
		public ChartInfo(String string, String string2, String string3) {
			// TODO Auto-generated constructor stub
		}
		public String getId() {
			return Id;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return desc;
		}
		
	}
		
	public JDStatistics(JFrame frame) {
		super(frame);
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
				btnCreate.setBounds(391, 394, 55, 23);
				btnCreate.setName("btnCreate");
				btnCreate.setAction(getAppActionMap().get("Generate"));
				btnCreate.setText(ApplicationInternationalization.getString("GenerateButton"));
			}
			{
				btnCancel = new JButton();
				getContentPane().add(btnCancel);
				btnCancel.setBounds(451, 394, 60, 23);
				btnCancel.setName("btnCancel");
				btnCancel.setAction(getAppActionMap().get("Cancel"));
				btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
			}
			{
				getContentPane().setLayout(null);
				{
					jScrollPane1 = new JScrollPane();
					getContentPane().add(jScrollPane1);
					jScrollPane1.setBounds(12, 12, 157, 371);
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
					panelConfigurationChart.setBounds(175, 12, 341, 376);
					panelConfigurationChart.setLayout(null);
					{
						panelConfiguration = new JPanel();
						panelConfigurationChart.add(panelConfiguration);
						panelConfiguration.setBounds(12, 0, 322, 267);
						panelConfiguration.setLayout(null);
						panelConfiguration.setBorder(BorderFactory.createTitledBorder(""));
						panelConfiguration.setLayout(null);
						panelConfiguration.add(getLblCharType());
						panelConfiguration.add(getCbCharts());
						panelConfiguration.add(getTxtDescription());
						panelConfiguration.add(getLblDescription());
						panelConfiguration.add(getLblProject());
						panelConfiguration.add(getCbProjects());
						panelConfiguration.add(getLblUser());
						panelConfiguration.add(getLblDate());
						panelConfiguration.add(getCbUsers());
						panelConfiguration.add(getCbRange());
						panelConfiguration.add(getLblMin());
						panelConfiguration.add(getCbMin());
						panelConfiguration.add(getLblMax());
						panelConfiguration.add(getCbMax());
					}
					{
						panelCustomize = new JPanel();
						panelConfigurationChart.add(panelCustomize);
						panelCustomize.setBounds(12, 279, 324, 91);
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
							rbShowLegend.setBounds(86, 37, 64, 20);
							rbShowLegend.setName("rbShowLegend");
							rbShowLegend.setText(ApplicationInternationalization.getString("showLegendChart"));
						}
						{
							rbHideLegend = new JRadioButton();
							panelCustomize.add(rbHideLegend);
							rbHideLegend.setBounds(172, 37, 57, 20);
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
						}
					}
					getButtonGroup().add(rbHideLegend);
					getButtonGroup().add(rbShowLegend);
					
					// Add buttons for each type of chart
					createChartButtons();
					
					// Read XML file for charts
					doc = StatisticsGenerator.readXMLCharts();
					
					// Fill combobox of charts and projects
					fillComboChartTypes();
					fillComboProjects();
				}
			}
			this.setSize(538, 461);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

	private void createChartButtons() throws JDOMException, IOException {
		// Get types of chart from XML
		List<String> types = StatisticsGenerator.getChartsTypes(doc);
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
			button.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent evt) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  
					ImagesUtilities.decreaseImageBrightness((JButton) evt.getSource());
				}
	
				public void mouseEntered(MouseEvent evt) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));  
					ImagesUtilities.increaseImageBrightness((JButton) evt.getSource());
				}
			});
			button.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					// Clean selection from other buttons
					for (Component c: panelCharts.getComponents())
						if (c instanceof JButton) 
							((JButton)c).setContentAreaFilled(false);
					((JButton)e.getSource()).setContentAreaFilled(true);
					selectedChartType = ((JButton)e.getSource()).getName(); 
					if (((JButton)e.getSource()).getName().equals("pie")) {
						setRenderVisibility(true);
					}
				}
			});
		}		
	}
	
	// Load available charts into combobox 
	private void fillComboChartTypes() throws JDOMException {		
		List<String> chartsId = StatisticsGenerator.getIdNamesChart(doc, selectedChartType);
		List<String> chartsName = StatisticsGenerator.getIdNamesChart(doc, selectedChartType);
		List<String> chartsDesc = StatisticsGenerator.getIdNamesChart(doc, selectedChartType);
		ChartInfo chart = null;
		for (int i = 0; i< chartsId.size(); i++) {
			chart = new ChartInfo(chartsId.get(i), chartsName.get(i), chartsDesc.get(i));
			cbCharts.addItem(chart);
		}
		cbCharts.setSelectedIndex(0);
	}
	
	private void fillComboProjects() throws RemoteException, NonPermissionRole, NotLoggedException, SQLException, Exception {
		List<Project> projects = ClientController.getInstance().getProjects();
		for (Project p: projects) {
			cbProjects.addItem(p);
		}
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
		}
		return lblUser;
	}
	
	private JLabel getLblDate() {
		if(lblDate == null) {
			lblDate = new JLabel();
			lblDate.setBounds(10, 208, 40, 16);
			lblDate.setName("lblDate");
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
			cbRange.setBounds(84, 205, 72, 23);
			cbRange.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					cbRangeActionPerformed(evt);
				}
			});
		}
		return cbRange;
	}
	
	private JLabel getLblMin() {
		if(lblMin == null) {
			lblMin = new JLabel();
			lblMin.setBounds(180, 208, 54, 16);
			lblMin.setName("lblMin");
		}
		return lblMin;
	}
	
	private JComboBox getCbMin() {
		if(cbMin == null) {
			cbMin = new JComboBox();
			cbMin.setBounds(239, 205, 74, 23);
		}
		return cbMin;
	}
	
	private JLabel getLblMax() {
		if(lblMax == null) {
			lblMax = new JLabel();
			lblMax.setBounds(180, 236, 54, 16);
			lblMax.setName("lblMax");
		}
		return lblMax;
	}
	
	private JComboBox getCbMax() {
		if(cbMax == null) {
			cbMax = new JComboBox();
			cbMax.setBounds(239, 233, 74, 23);
		}
		return cbMax;
	}
	
	public void setRenderVisibility(boolean visible) {
		lblEffect.setVisible(visible);
		chk3D.setVisible(visible);
	}
	
	// Depending on the chosen chart, enabling or disabling components 
	private void cbChartsActionPerformed(ActionEvent evt) {
		try {
			selectedChart = (ChartInfo) cbCharts.getSelectedItem();
			setConfigurationChart();
			cbProjects.setEnabled(oneProject);
			cbUsers.setEnabled(oneUser);
			cbRange.setEnabled(historical);
			cbMin.setEnabled(historical);
			cbMax.setEnabled(historical);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// When a project is selected, load users from that project
	private void cbProjectsActionPerformed(ActionEvent evt) {
		List<User> users;
		try {
			users = ClientController.getInstance().getUsersProject((Project) cbProjects.getSelectedItem());
			for (User u: users) {
				cbUsers.addItem(u);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonPermissionRole e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLoggedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	
	// Loads date range of the selected project (months or years)
	private void cbRangeActionPerformed(ActionEvent evt) {

	}
	
	private void setConfigurationChart() throws JDOMException, IOException {
		// Configuration XML file is used to create the chart corresponding to the selected case
		Document doc = StatisticsGenerator.readXMLConfiguration();
		oneProject = StatisticsGenerator.isOneProject(doc, selectedChart.getId());
		oneUser = StatisticsGenerator.isOneUser(doc, selectedChart.getId());
		percentage = StatisticsGenerator.isPercentage(doc, selectedChart.getId());
		historical = StatisticsGenerator.isHistorical(doc, selectedChart.getId());
		resource = StatisticsGenerator.getResource(doc, selectedChart.getId());		
	}

	@Action
	public void Cancel () {
		this.dispose();
	}
	
	// Action used to generate a chart
	@Action
	public void Generate () {
		// TODO: Validar todos campos rellenos
		
		
		Project pro = null;
		User u = null;
		
		if (oneProject) {
			pro = cbProjects.getSelectedItem();
			if (oneUser) {
				u = cbUsers.getSelectedItem();
				if (historical) {
					createChartEvolutionUser();
				}
			}
			else {
				if (historical) {
					createChartHistoricalProject();
				}
				else 
					createChartKnowledgeProject(percentage);
			}
		}
		else {
			if (oneUser) {
				createChartKnowledgeUser(percentage);
			}
			else
				createChartResourcesProjects(resource);
		}
	}
		
		
		
		
		
		
		private void createPieChart() {
		
		
		DefaultPieDataset dataset = new DefaultPieDataset();
			ClientController.getInstance().initClient("192.168.1.177", "2995", "emp1", "emp1");
			// Se toman todos los proyectos
			projects = ClientController.getInstance().getProjects();
			// Se toma el TopicWrapper del proyecto deseado
			tw = ClientController.getInstance().getTopicsWrapper(projects.get(1));
			// Se cuenta el total de conocimiento
			int count = tw.getTopics().size();
			for(Topic t: tw.getTopics()) {
				count += t.getProposals().size();
				for (Proposal p: t.getProposals()) {
					count += p.getAnswers().size();
				}
			}
			// Se toman los usuarios del proyecto
			users = ClientController.getInstance().getUsersProject(projects.get(1));
			// Del TW anterior se cuenta para cada usuario
			for (User u: users) {
				int nKnow = getCountKnowledgeUser(u);
				double value = (nKnow * 100.0 / count);
				dataset.setValue(u.getName(), value);
				datasetBars.addValue(nKnow, u.getName(), u.getName());
			}
			
			// Ejmplo de Chart para el caso 1.1			
			
			JFreeChart chart = ChartFactory.createPieChart(
					"Sample Pie Chart",
					dataset,
					true, // legend?
					true, // tooltips?
					false // URLs?
					);
			
			JFreeChart chartBars= ChartFactory.createBarChart(
					"Bar Chart Demo", // chart title
					"User", // domain axis label
					"Nº decisiones", // range axis label
					datasetBars, // data
					PlotOrientation.VERTICAL, // orientation
					true, // include legend
					true, // tooltips?
					false // URLs?
					);
			
			ChartPanel panel = new ChartPanel(chart);
			getContentPane().add(panel);
			panel.setPreferredSize(new java.awt.Dimension(242, 74));
			ChartPanel panel2 = new ChartPanel(chartBars);
			getContentPane().add(panel2);
		
	}
		
		

}
