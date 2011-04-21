package presentation;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.Console;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import javax.swing.WindowConstants;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.application.Application;
import javax.swing.SwingUtilities;


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
public class NewJFrame extends javax.swing.JFrame {

	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NewJFrame inst = new NewJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				
			}
		});

	}

	private JButton jButton1;
	private DetailPanel panel;
	private JLabel jLabel1;
	private DetailsView view;

	public NewJFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, "Center");
				jButton1.setName("jButton1");
				jButton1.setBounds(0, 0, 164, 164);
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});
			}
			
			 panel = new DetailPanel(this);
			
				view = new DetailsView(panel);
			
			 {
				 jLabel1 = new JLabel();
				 panel.add(jLabel1);
				 jLabel1.setName("jLabel1");
				 jLabel1.setPreferredSize(new java.awt.Dimension(510, 245));
			 }
			
			this.setGlassPane(view);
			
			

				
			
			pack();
			this.setSize(632, 399);
			
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void jButton1ActionPerformed(ActionEvent evt) {

		view.fadeIn();
	}

	public void fadeOut() {
		
		view.fadeOut();
		
	}
}