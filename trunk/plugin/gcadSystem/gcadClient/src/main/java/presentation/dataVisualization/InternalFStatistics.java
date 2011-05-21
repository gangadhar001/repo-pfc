package presentation.dataVisualization;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
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
	private JToolBar toolBar;
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
//		setResizable(true);
//		setClosable(true);
		
		try {
			setPreferredSize(new Dimension(400, 300));
			this.setBounds(0, 0, 400, 300);
			setVisible(true);
			GridBagLayout thisLayout = new GridBagLayout();
			this.setName("this");
			thisLayout.rowWeights = new double[] {0.1, 0.7};
			thisLayout.rowHeights = new int[] {7, 7};
			thisLayout.columnWeights = new double[] {0.1};
			thisLayout.columnWidths = new int[] {7};
			getContentPane().setLayout(thisLayout);
			{
				toolBar = new JToolBar();
				getContentPane().add(toolBar, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				panelChart = new JPanel();
				getContentPane().add(panelChart, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addChartPanel(ChartPanel chartPanel) {
		panelChart.add(chartPanel);
		panelChart.revalidate();
		panelChart.repaint();
	}

}
