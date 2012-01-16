package presentation.dataVisualization;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.io.IOException;

import internationalization.ApplicationInternationalization;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.japura.gui.WrapLabel;
import org.jdesktop.application.Application;
import org.jdesktop.swingx.JXLabel;
import org.jfree.chart.ChartPanel;

import resources.ImagesUtilities;


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
	private JXLabel lblType;

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
			this.setPreferredSize(new java.awt.Dimension(582, 354));
			this.setBounds(0, 0, 582, 354);
			setVisible(true);
			getContentPane().setLayout(null);
			{
				panelChart = new JPanel();
				getContentPane().add(panelChart);
				panelChart.setBounds(5, 49, 571, 280);
			}
			{
				lblType = new JXLabel();
				getContentPane().add(lblType);
				lblType.setBounds(5, 0, 571, 50);
				lblType.setName("lblType");
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addChartPanel(String projectName, String type, boolean showProjectName, ChartPanel chartPanel) {		
		panelChart.setSize(chartPanel.getPreferredSize());
		panelChart.add(chartPanel);
		panelChart.revalidate();
		panelChart.repaint();
		this.setSize(panelChart.getSize().width + 20, panelChart.getSize().height + 100);
		lblType.setFont(new Font(lblType.getFont().getName(), Font.BOLD, 15));
		lblType.setLineWrap(true);
		lblType.setSize(new Dimension(panelChart.getSize().width, 50));
		lblType.setText(ApplicationInternationalization.getString(type));
		if (showProjectName)
			lblType.setText(lblType.getText() + ": " + projectName);
		
		this.revalidate();
		this.repaint();
	}

}
