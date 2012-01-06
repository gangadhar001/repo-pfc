package presentation.panelsActions;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import internationalization.ApplicationInternationalization;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.jdesktop.application.Application;

import presentation.customComponents.ImagePanel;
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
public class panelStatisticsGeneration extends ImagePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5958964221039291732L;
	private JDesktopPane desktopPane;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
		
	public panelStatisticsGeneration() {
		super();
		try {
			super.setImage(ImagesUtilities.loadCompatibleImage("background.png"));
		} catch (Exception e) { }		
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(1008, 601));
			this.setSize(1008, 601);
			this.setMinimumSize(new java.awt.Dimension(1008, 601));
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			{
				desktopPane = new JDesktopPane();
				desktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
				this.add(desktopPane, new AnchorConstraint(0, 1000, 1001, 0, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				desktopPane.setName("desktopPane");
				desktopPane.setPreferredSize(new java.awt.Dimension(902, 402));
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void addStatistic(JInternalFrame frameStatistics) {
		desktopPane.add(frameStatistics);
	}

}
