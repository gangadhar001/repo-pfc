package presentation;
import internationalization.ApplicationInternationalization;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import model.business.knowledge.Groups;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Operation;
import model.business.knowledge.Subgroups;

import org.jdesktop.application.Application;

import resources.ImagesUtilities;
import bussiness.control.ClientController;


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
public class JDKnowledge extends javax.swing.JDialog {
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
	
	// This variables are used when this frame is invoked by the knowledge view, when
	// an element and operation are selected.
	private String subgroupSelected;
	private Object data;
	private String operationToDo;
	private JDialog dialog;
	
	public JDKnowledge() {
		super();
		this.subgroupSelected = null;
		this.operationToDo = null;
		dialog = this;
		data = null;
		initGUI();
	}
	
	public JDKnowledge(String subgroupSelected, Object data, String operationToDo) {
		super();
		this.subgroupSelected = subgroupSelected;
		this.data = data;
		this.operationToDo = operationToDo;
		dialog = this;
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
				getContentPane().add(mainPanel);
				mainPanel.setBounds(190, 12, 439, 327);
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
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
			if (o.getGroup().equals(Groups.Knowledge.name()))
				actionsKnowledge.put(o.getSubgroup(), o.getOperations());
		}
	}
    
	@SuppressWarnings("rawtypes")
	private void createPanelActions() throws IOException {
		ArrayList<String> subgroups = Collections.list(actionsKnowledge.keys()); 
		for (final String subgroup: subgroups) {
			if (!subgroup.equals(Subgroups.PDFGeneration.name())) {
		    	// Load the subgroup image of the operation
				image = ImagesUtilities.loadCompatibleImage(subgroup + ".png");
				// Create button
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
				ImagesUtilities.addImageButton(button.getName(), image);
				button.addMouseListener(new MouseAdapter() {
					public void mouseExited(MouseEvent evt) {
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  
						ImagesUtilities.decreaseImageBrightness((JButton) evt.getSource());
					}
		
					public void mouseEntered(MouseEvent evt) {
						setCursor(new Cursor(Cursor.HAND_CURSOR));  
						ImagesUtilities.increaseImageBrightness((JButton) evt.getSource());
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
							Component component = null;
							if (data != null) {
								// Use constructor of the corresponding panel, in order to pass the data to the constructor
								// This is reflection
								Constructor c = Class.forName("presentation.panelsManageKnowledge.JPManage"+subgroup).getConstructor(new Class [] {JDialog.class, Object.class, String.class});
								component = (Component) c.newInstance(new Object [] {dialog, data, operationToDo});
							}
							else {
								// Pass this dialog to the children panels
								Constructor c = Class.forName("presentation.panelsManageKnowledge.JPManage"+subgroup).getConstructor(new Class [] {JDialog.class});
								component = (Component) c.newInstance(new Object [] {dialog});
							}
							
							mainPanel.add(component);
							mainPanel.validate();
							mainPanel.repaint();
							JButton buttonPressed = ((JButton)e.getSource()); 
							buttonPressed.setContentAreaFilled(true);
						} catch (InstantiationException e1) {
							JOptionPane.showMessageDialog(null, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
						} catch (IllegalAccessException e1) {
							JOptionPane.showMessageDialog(null, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
						} catch (ClassNotFoundException e1) {
							JOptionPane.showMessageDialog(null, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
						} catch (SecurityException e1) {
							JOptionPane.showMessageDialog(null, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
						} catch (NoSuchMethodException e1) {
							JOptionPane.showMessageDialog(null, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
						} catch (IllegalArgumentException e1) {
							JOptionPane.showMessageDialog(null, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
						} catch (InvocationTargetException e1) {
							JOptionPane.showMessageDialog(null, e1.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
						}					
					}
				});				
				
				panelActions.add(button);
				
				// Simulate button click that corresponds with the action selected by user.
				// If it is null, no default action
				if (subgroupSelected != null && subgroup.equals(subgroupSelected))
					button.doClick();
			}
		}
    }

	public Knowledge getNewKnowledge() {
		// TODO Auto-generated method stub
		return null;
	}   
}
