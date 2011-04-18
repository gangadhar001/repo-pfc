package presentation;
import bussiness.control.ClientController;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import internationalization.ApplicationInternationalization;
import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import org.jdesktop.application.Action;
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
	private JPanel mainPanel;
	private BufferedImage image;
	// HashTable used to save the available operations for each knowledge subgroup (Proposal, Topic, Answer).
	// This HashTable will be used in order to build dynamically the UI
	private Hashtable<String, List<String>> actionsKnowledge = new Hashtable<String, List<String>>();
	
	private String subgroupSelected;
	
	public JFKnowledge(String subgroupSelected) {
		super();
		this.subgroupSelected = subgroupSelected;
		initGUI();
	}
	
	private void initGUI() {
		this.setTitle(ApplicationInternationalization.getString("titleJFKnowledge"));
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				closeWindow(evt);
			}
		});
		
		try {
			getContentPane().setLayout(null);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				mainPanel = new JPanel();
				mainPanel.setLayout(null);
				getContentPane().add(mainPanel, new AnchorConstraint(0, 0, 0, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				mainPanel.setBounds(190, 12, 439, 327);
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1, new AnchorConstraint(12,462,978,21,AnchorConstraint.ANCHOR_ABS,AnchorConstraint.ANCHOR_ABS,AnchorConstraint.ANCHOR_NONE,AnchorConstraint.ANCHOR_NONE));
				jScrollPane1.setBounds(12, 12, 166, 327);
				{
					panelActions = new JPanel();
					FlowLayout panelActionsLayout = new FlowLayout();
					panelActions.setLayout(panelActionsLayout);
					jScrollPane1.setViewportView(panelActions);
					panelActions.setPreferredSize(new java.awt.Dimension(145, 316));
				}
			}
			
			List<Operation> operations = ClientController.getInstance().getAvailableOperations();
			// Retrieve and store the subgroups of "Knowledge" group and its actions
			setKnowledgeActions(operations);
			// Create the knowledge actions panel
			createPanelActions();
			pack();
			this.setSize(657, 379);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	protected void closeWindow(WindowEvent evt) {
		this.dispose();
		
	}

	private void setKnowledgeActions(List<Operation> operations) {
		for(Operation o: operations) {
			if (o.getGroup().equals("Knowledge"))
				actionsKnowledge.put(o.getSubgroup(), o.getOperations());
		}
	}

	// This method is used to configure the available knowledge operations
//    private void createKnowledgePanel() {
//    	// Set layout
//    	GridLayout layout = new GridLayout();
//    	layout.setRows(panelActions.getHeight()/100);
//    	layout.setColumns(1);
//		
//		panelActions.setLayout(layout);
//    	try {
//			createPanelActions();
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
//		}		
//    }
    
	private void createPanelActions() throws IOException {
		ArrayList<String> subgroups = Collections.list(actionsKnowledge.keys()); 
		for (final String subgroup: subgroups) {
	    	// Load the subgroup image of the operation
			image = GraphicsUtilities.loadCompatibleImage(subgroup + ".png");
			
			JButton button = new JButton();
			button.setSize(new Dimension(100, 100));
			button.setPreferredSize(button.getSize());
			// Set the subgroup id as name of the button
			button.setName("btn_"+subgroup);
			button.setText(subgroup);
			button.setIcon(new ImageIcon(image));
			button.setHorizontalTextPosition(JButton.CENTER);
			button.setVerticalTextPosition(JButton.BOTTOM);
			button.setContentAreaFilled(false);
			button.setFocusPainted(false);
			button.setBorderPainted(true);					
			// Save button icon
			GraphicsUtilities.addImageButton(button.getName(), image);
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
			
			// Set the corresponding action for the name of the button
			button.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						// Clean selection from other buttons
						for (Component c: panelActions.getComponents())
							if (c instanceof JButton) 
								((JButton)c).setContentAreaFilled(false);
						// Set the corresponding panel on the main panel
						mainPanel.removeAll();
						Component component = (Component) (Class.forName("presentation.JPManage"+subgroup)).newInstance();
						mainPanel.add(component);
						mainPanel.validate();
						mainPanel.repaint();
						JButton buttonPressed = ((JButton)e.getSource()); 
						buttonPressed.setContentAreaFilled(true);
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
				}
			});				
			
			panelActions.add(button);
			
			// Simulate button click that corresponds with the action selected by user
			if (subgroup.equals(subgroupSelected))
				button.doClick();
		}
    }    
}
