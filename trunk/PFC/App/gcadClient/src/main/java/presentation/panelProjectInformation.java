package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.Component;
import java.awt.Dimension;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;

import javax.swing.WindowConstants;

import model.business.control.CBR.Attribute;
import model.business.knowledge.Project;

import org.jdesktop.application.Application;

import presentation.customComponents.NumericTextField;

import bussiness.control.ClientController;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

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
public class panelProjectInformation extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4888933412773329217L;
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
		
	private final int POSX_COLUMN1 = 17;
	private final int POSX_COLUMN2 = 135;	
	private final int POSY = 0;
	private final int INCREMENT_POSY = 35;

	
	public panelProjectInformation() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(269, 418));
			this.setLayout(null);
			this.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("panelProject")));
			
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showData(Project project, boolean editable) {
		clearPanel();
		
		// Show attributes of the project
		List<Attribute> attributes;
		try {
			attributes = ClientController.getInstance().getAttributesFromProject(project);		
			// Show attributes name 
			int numberAttributes = 1;
			for (Attribute att : attributes) {
				// Ignore id and UID
				if (!att.getName().equals("id") && (!att.getName().equals("serialVersionUID"))) {
					JLabel lblAtt = new JLabel ();
					lblAtt.setName("attribute_"+att.getName()+"_"+numberAttributes);
					lblAtt.setText(ApplicationInternationalization.getString(att.getName()));
					this.add(lblAtt);
					lblAtt.setBounds(POSX_COLUMN1, POSY + INCREMENT_POSY * numberAttributes, 100, 20);
					
					// Show attribute value (using reflection)
					JTextField tbAttValue = null;
					Field attField = Project.class.getDeclaredField(att.getName());
					attField.setAccessible(true);
					if (att.getType() == int.class || att.getType() == double.class)
						tbAttValue = new NumericTextField();
					else
						// TODO: para fechas
						tbAttValue = new JTextField();					
					
					tbAttValue.setName("attributeValue_"+att.getName()+"_"+numberAttributes);
					if (attField.get(project) != null)
						tbAttValue.setText(attField.get(project).toString());
					// TODO: añadir inputVerifier para que los valores no sean vacios
					this.add(tbAttValue);
					tbAttValue.setBounds(POSX_COLUMN2, POSY + INCREMENT_POSY * numberAttributes, 100, 20);
					tbAttValue.setEditable(editable);
					
					numberAttributes++;
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private void clearPanel() {
		this.removeAll();
		
	}

	// Return the project
	public Project getProject() {
		Project newProject = new Project();
		newProject.setName(findAttValue("name").toString());
		newProject.setDescription(findAttValue("description").toString());
		// TODO:
//		newProject.setStartDate((Date)findAttValue("startDate"));
//		newProject.setEndDate((Date)findAttValue("endDate"));
		newProject.setStartDate(new Date());
		newProject.setEndDate(new Date());
		newProject.setBudget(Double.parseDouble(findAttValue("budget").toString()));
		newProject.setDomain(findAttValue("domain").toString());
		newProject.setEstimatedHours(Integer.parseInt(findAttValue("estimatedHours").toString()));
		newProject.setProgLanguage(findAttValue("progLanguage").toString());
		newProject.setQuantityLines(Integer.parseInt(findAttValue("quantityLines").toString()));	
		return newProject;
	}

	// Find the value of an attribute
	private Object findAttValue(String name) {
		boolean found = false;
		Object value = "";
		Component[] components = this.getComponents();
		for (int i = 0; i < components.length && !found; i++) {
			if (components[i] instanceof JTextField) {
				JTextField tf = (JTextField)components[i];
				// Function parameter
				if (tf.getName().contains("_"+name)) {					
					value = tf.getText();
					found = true;
				}
			}
			// TODO:fecha
		}
		return value;
	}

}
