package presentation;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import internationalization.ApplicationInternationalization;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.WindowConstants;
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
public class JFKnowledge extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8038923452938953421L;
	private JPanel panelActions;
	private JScrollPane jScrollPane1;
	private JButton jButton1;
	private JPanel mainPanel;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFKnowledge inst = new JFKnowledge();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public JFKnowledge() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		this.setTitle(ApplicationInternationalization.getString("titleJFKnowledge"));
		
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				mainPanel = new JPanel();
				AnchorLayout mainPanelLayout = new AnchorLayout();
				mainPanel.setLayout(mainPanelLayout);
				getContentPane().add(mainPanel, new AnchorConstraint(12, 896, 966, 238, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				mainPanel.setPreferredSize(new java.awt.Dimension(438, 317));
				{
					jButton1 = new JButton();
					mainPanel.add(jButton1, new AnchorConstraint(17, 567, 89, 432, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					jButton1.setName("jButton1");
					jButton1.setPreferredSize(new java.awt.Dimension(59, 23));
				}
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1, new AnchorConstraint(12, 462, 978, 21, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				jScrollPane1.setPreferredSize(new java.awt.Dimension(214, 319));
				{
					panelActions = new JPanel();
					jScrollPane1.setViewportView(panelActions);
					GridLayout panelActionsLayout = new GridLayout(5, 1);
					panelActionsLayout.setHgap(5);
					panelActionsLayout.setVgap(5);
					panelActionsLayout.setColumns(1);
					panelActionsLayout.setRows(5);
					panelActions.setLayout(panelActionsLayout);
					panelActions.setPreferredSize(new java.awt.Dimension(187, 508));
				}
			}
			pack();
			this.setSize(704, 379);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	// This method is used to configure the available knowledge actions
    private void createKnowledgePanel(Hashtable<String, List<String>> actions) {
	int nOperations = actions.size() + 1;
	GridLayout layout = new GridLayout();
	layout.setColumns(3);
	if (nOperations % 3 == 0)
		layout.setRows(nOperations/3);
	else
		layout.setRows(nOperations/3 + 1);
	
	layout.setHgap(10);
	layout.setHgap(10);
	
	panelActions.setLayout(layout);
	
	for (String s : actions.keySet()) {
		try {
			image = GraphicsUtilities.loadCompatibleImage(getClass()
					.getResource(GraphicsUtilities.IMAGES_PATH + s + ".png"));

			JPanel panel = new JPanel();

			JButton button = new JButton();
			button.setContentAreaFilled(false);
			button.setFocusPainted(false);
			button.setBorderPainted(true);
			
			button.setIcon(new ImageIcon(image));
			panel.add(button);
			button.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent evt) {
					buttonMouseExited(evt);
					decreaseImageBrightness((JButton) evt.getSource(),
							image);
				}

				public void mouseEntered(MouseEvent evt) {
					buttonMouseEntered(evt);
					increaseImageBrightness((JButton) evt.getSource(),
							image);
				}
			});
			
			JLabel label = new JLabel();
			label.setText("Show " + s + " view");
			panel.add(label);
			panelActions.add(panel);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getMainFrame(),
					e.getLocalizedMessage(),
					ApplicationInternationalization.getString("Error"),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	try {
		image = GraphicsUtilities.loadCompatibleImage(getClass()
				.getResource(GraphicsUtilities.IMAGES_PATH + "logout.png"));

		JPanel panel = new JPanel();

		JButton button = new JButton();
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorderPainted(true);

		button.setIcon(new ImageIcon(image));
		panel.add(button);
		button.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent evt) {
				buttonMouseExited(evt);
				decreaseImageBrightness((JButton) evt.getSource(), image);
			}

			public void mouseEntered(MouseEvent evt) {
				buttonMouseEntered(evt);
				increaseImageBrightness((JButton) evt.getSource(), image);
			}
		});

		JLabel label = new JLabel();
		label.setText("Logout");
		panel.add(label);
		panelActions.add(panel);
	} catch (IOException e) {
		JOptionPane.showMessageDialog(getMainFrame(),
				e.getLocalizedMessage(),
				ApplicationInternationalization.getString("Error"),
				JOptionPane.ERROR_MESSAGE);
	}
}    

}
