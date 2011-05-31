package presentation.dataVisualization;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JToolBar;

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
		setResizable(true);
		setClosable(true);
		
		try {
			this.setPreferredSize(new java.awt.Dimension(420, 288));
			this.setBounds(0, 0, 705, 403);
			setVisible(true);
			AnchorLayout thisLayout = new AnchorLayout();
			this.setName("this");
			getContentPane().setLayout(thisLayout);
			{
				toolBar = new JToolBar();
				getContentPane().add(toolBar, new AnchorConstraint(1, 1013, 131, -1, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				toolBar.setPreferredSize(new java.awt.Dimension(420, 32));
			}
			{
				panelChart = new JPanel();
				getContentPane().add(panelChart, new AnchorConstraint(39, 996, 995, -1, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				panelChart.setPreferredSize(new java.awt.Dimension(420, 266));
				panelChart.setSize(420, 266);
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addChartPanel(ChartPanel chartPanel) {
		chartPanel.setPreferredSize(new java.awt.Dimension(420, 266));
		panelChart.add(chartPanel);
		panelChart.revalidate();
		panelChart.repaint();
	}

}
