package presentation;
import bussiness.control.ClientController;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import internationalization.ApplicationInternationalization;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.WindowConstants;

import model.business.knowledge.Operation;

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
	private BufferedImage image;
	// HashTable used to save the available operations for each knowledge subgroup (Proposal, Topic, Answer).
	// This hashTable will be used in order to build dynamically the UI
	private Hashtable<String, List<String>> actionsKnowledge = new Hashtable<String, List<String>>();
	
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
					panelActions.setPreferredSize(new java.awt.Dimension(196, 326));
				}
			}
			
			List<Operation> operations = ClientController.getInstance().getAvailableOperations();
			// Retrieve and store the subgroups of Knowledge group and its actions
			setKnowledgeActions(operations);
			// Create the actions panel
			createKnowledgePanel(operations);
			pack();
			this.setSize(704, 379);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void setKnowledgeActions(List<Operation> operations) {
		for(Operation o: operations) {
			if (o.getGroup().equals("Knowledge"))
				actionsKnowledge.put(o.getSubgroup(), o.getOperations());
		}
	}

	// This method is used to configure the available knowledge operations
    private void createKnowledgePanel(List<Operation> operations) {
    	// Set layout
    	GridLayout layout = new GridLayout();
    	layout.setRows(actionsKnowledge.size());
    	layout.setColumns(1);
    	
    	layout.setHgap(10);
		layout.setHgap(10);
		
		panelActions.setLayout(layout);
    	try {
			createPanelActions();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}		
    }
    
	private void createPanelActions() throws IOException {
		ArrayList<String> subgroups = Collections.list(actionsKnowledge.keys()); 
		for (String s: subgroups) {
	    	// Load the subgroup image of the operation
			image = GraphicsUtilities.loadCompatibleImage(s + ".png");
			JPanel panel = new JPanel();
			
			JButton button = new JButton();
			// Set the subgroup name
			button.setName(s);
			button.setContentAreaFilled(false);
			button.setFocusPainted(false);
			button.setBorderPainted(true);				
			button.setIcon(new ImageIcon(image));
			// Save button icon
			GraphicsUtilities.addImageButton(button.getName(), image);
			panel.add(button);
			button.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent evt) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  
					GraphicsUtilities.decreaseImageBrightness((JButton) evt.getSource());
				}
	
				public void mouseEntered(MouseEvent evt) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));  
					GraphicsUtilities.increaseImageBrightness((JButton) evt.getSource());
				}
			});
			
			JLabel label = new JLabel();
			label.setText(s);
			panel.add(label);
			panelActions.add(panel);
		}
    }    

}
