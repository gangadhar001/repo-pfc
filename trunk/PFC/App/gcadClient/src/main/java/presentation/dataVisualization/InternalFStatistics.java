package presentation.dataVisualization;
import javax.swing.JPanel;

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
			this.setPreferredSize(new java.awt.Dimension(176, 171));
			this.setBounds(0, 0, 176, 171);
			setVisible(true);
			this.setName("this");
			getContentPane().setLayout(null);
			{
				panelChart = new JPanel();
				getContentPane().add(panelChart);
				panelChart.setBounds(0, 0, 175, 146);
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addChartPanel(ChartPanel chartPanel) {
		panelChart.setSize(chartPanel.getPreferredSize());
		this.setSize(chartPanel.getPreferredSize().width + 20, chartPanel.getPreferredSize().height + 20);
		panelChart.add(chartPanel);
		panelChart.revalidate();
		panelChart.repaint();
		this.revalidate();
		this.repaint();
	}

}
