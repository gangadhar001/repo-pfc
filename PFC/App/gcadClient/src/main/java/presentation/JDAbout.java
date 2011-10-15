package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.application.Application;

import presentation.utils.AWTUtilitiesWrapper;

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

// REFERENCES: http://java.sun.com/developer/technicalArticles/GUI/translucent_shaped_windows/#Demo
public class JDAbout extends javax.swing.JDialog {
	/**
	 * 
	 */
	// TODO: hacerlo
	private static final long serialVersionUID = 2342089516245307105L;
	private JPanel jPanel1;
	private JButton btnOk;

	/**
	* Auto-generated main method to display this JDialog
	*/
		
	public JDAbout(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				this.setLayout(new java.awt.BorderLayout());
				{
					jPanel1 = new JPanel();
					getContentPane().add(jPanel1);
					jPanel1.setBounds(0, 0, 664, 322);
					jPanel1.setLayout(null);
					jPanel1.setName("jPanel1");
					{
						btnOk = new JButton();
						jPanel1.add(btnOk);
						btnOk.setBounds(603, 324, 66, 25);
						btnOk.setName("btnOk");
						btnOk.setText(ApplicationInternationalization.getString("btnOK"));
					}
				}
			}
			this.setUndecorated(true);
			
			this.setSize(680, 360);
			
			if (AWTUtilitiesWrapper.isTranslucencySupported(AWTUtilitiesWrapper.TRANSLUCENT))				
				AWTUtilitiesWrapper.setWindowOpacity(this, ((float) 95.0) / 100.0f);
			
			Shape shape = null;
            shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 50, 50);
            AWTUtilitiesWrapper.setWindowShape(this, shape);
            
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
