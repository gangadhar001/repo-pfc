package presentation;
import internationalization.AppInternationalization;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

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
public class SplashScreen extends javax.swing.JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7291480776450245123L;
	private JLabel lblProgress;
	private JProgressBar progressBar;
	private ImagePanel panel;

	/**
	* Auto-generated main method to display this JDialog
	*/
	
	{
		 //Set Look & Feel
	    try {
	    	javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
	    } catch(Exception e) {
	    	JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
	    }
	}
		
	public SplashScreen() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setSize(637, 288);
			getContentPane().setLayout(null);
			{
				panel = new ImagePanel();
				panel.setImage(ImagesUtilities.loadCompatibleImage("splash.png"));
				getContentPane().add(panel);
				panel.setBounds(0, 0, 637, 288);
				panel.setLayout(null);
				{
					lblProgress = new JLabel();
					panel.add(lblProgress);
					lblProgress.setName("lblProgress");
					lblProgress.setBounds(325, 256, 85, 18);
				}
				{
					progressBar = new JProgressBar();
					panel.add(progressBar);
					progressBar.setBounds(418, 257, 209, 20);
					progressBar.setMaximum(100);
				}
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
