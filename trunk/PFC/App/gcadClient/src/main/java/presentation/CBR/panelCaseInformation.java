package presentation.CBR;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.BorderFactory;

import javax.swing.WindowConstants;

import model.business.control.CBR.Attribute;
import model.business.knowledge.EnumSimilFunctions;
import model.business.knowledge.Project;

import org.jdesktop.application.Application;

import bussiness.control.ClientController;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
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
public class panelCaseInformation extends javax.swing.JPanel {

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
		
	private final int POSX_COLUMN1 = 17;
	private final int POSX_COLUMN2 = 135;	
	private final int POSY = 17;
	private final int INCREMENT_POSY = 35;

	
	public panelCaseInformation() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(269, 418));
			this.setLayout(null);
			this.setBorder(BorderFactory.createTitledBorder(""));
			
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showData(Project project) {
		clearPanel();
		
		// Show attributes of the project (case)
		List<Attribute> attributes;
		try {
			attributes = ClientController.getInstance().getAttributesFromProject(project);		
			// Show attributes name 
			int numberAttributes = 1;
			for (Attribute att : attributes) {
				// Ignore id and UID
				if (!att.getName().equals("id") && (!att.getName().equals("serialVersionUID"))) {
					JLabel lblAtt = new JLabel ();
					//TODO: internacionalizacion
					lblAtt.setName("attribute_"+att.getName()+"_"+numberAttributes);
					lblAtt.setText(att.getName());
					this.add(lblAtt);
					lblAtt.setBounds(POSX_COLUMN1, POSY + INCREMENT_POSY * numberAttributes, 90, 20);
					
					// Show attribute value (using reflection)
					JLabel lblAttValue = new JLabel ();
					//TODO: internacionalizacion
					lblAttValue.setName("attributeValue_"+att.getName()+"_"+numberAttributes);
					Field attField = Project.class.getDeclaredField(att.getName());
					attField.setAccessible(true);
					lblAttValue.setText(attField.get(project).toString());
					this.add(lblAttValue);
					lblAttValue.setBounds(POSX_COLUMN2, POSY + INCREMENT_POSY * numberAttributes, 100, 20);
					
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

}
