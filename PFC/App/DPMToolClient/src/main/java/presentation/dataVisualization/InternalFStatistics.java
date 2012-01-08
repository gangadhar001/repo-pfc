package presentation.dataVisualization;
import java.awt.Dimension;
import java.awt.Font;

import internationalization.ApplicationInternationalization;

import javax.swing.JPanel;

import org.japura.gui.WrapLabel;
import org.jdesktop.application.Application;
import org.jfree.chart.ChartPanel;


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
public class InternalFStatistics extends javax.swing.JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7190463711080941025L;
	private JPanel panelChart;
	private WrapLabel lblType;

	/**
	* Auto-generated main method to display this 
	* JInternalFrame inside a new JFrame.
	*/
		
	public InternalFStatistics() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		setResizable(true);
		setClosable(true);
		
		try {
			this.setPreferredSize(new java.awt.Dimension(578, 354));
			this.setBounds(0, 0, 578, 354);
			setVisible(true);
			this.setName("this");
			getContentPane().setLayout(null);
			{
				panelChart = new JPanel();
				getContentPane().add(panelChart);
				panelChart.setBounds(0, 69, 576, 260);
			}
			{
				lblType = new WrapLabel();
				getContentPane().add(lblType);
				lblType.setBounds(6, 7, 565, 30);
				lblType.setName("lblType");
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addChartPanel(String projectName, String type, boolean showProjectName, ChartPanel chartPanel) {		
		panelChart.setSize(chartPanel.getPreferredSize());
		this.setSize(chartPanel.getPreferredSize().width + 20, chartPanel.getPreferredSize().height + 20);
		lblType.setFont(new Font(lblType.getFont().getName(), Font.BOLD, lblType.getFont().getSize()));
		lblType.setWrapWidth(chartPanel.getPreferredSize().width);
		lblType.setSize(new Dimension(chartPanel.getPreferredSize().width, 30));
		lblType.setText(ApplicationInternationalization.getString(type));
		if (showProjectName)
			lblType.setText(lblType.getText() + ": " + projectName);
		panelChart.add(chartPanel);
		panelChart.revalidate();
		panelChart.repaint();
		this.revalidate();
		this.repaint();
	}

}
