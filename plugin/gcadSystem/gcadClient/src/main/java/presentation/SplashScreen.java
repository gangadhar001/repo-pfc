package presentation;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jdesktop.application.Application;

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
public class SplashScreen extends javax.swing.JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7291480776450245123L;
	private JLabel lblProgress;
	private JProgressBar progressBar;

	/**
	* Auto-generated main method to display this JDialog
	*/
		
	public SplashScreen() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {

			      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			this.setSize(637, 280);
			getContentPane().setLayout(null);
			{
				lblProgress = new JLabel();
				getContentPane().add(lblProgress, "Center");
				lblProgress.setName("lblProgress");
				lblProgress.setBounds(334, 249, 83, 22);
			}
			{
				progressBar = new JProgressBar();
				getContentPane().add(progressBar);
				progressBar.setBounds(417, 249, 209, 20);
				progressBar.setMaximum(100);
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setProgress(int progress) {
		final int p = progress;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				progressBar.setValue(progressBar.getValue() + p);
				
			}
		});
	}
	
	public void close() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				dispose();
				
			}
		});
	}

}
