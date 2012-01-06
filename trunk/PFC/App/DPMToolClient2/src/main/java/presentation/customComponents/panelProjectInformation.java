package presentation.customComponents;

import internationalization.ApplicationInternationalization;

import java.awt.Component;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.business.control.CBR.Attribute;
import model.business.knowledge.Project;

import org.japura.gui.calendar.CalendarField;
import org.japura.gui.model.DateDocument;
import org.japura.util.date.DateSeparator;
import org.jdesktop.application.Application;

import com.ibm.icu.util.Calendar;

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

/**
 * Custom panel used to show the information of a Project
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
			this.setPreferredSize(new java.awt.Dimension(349, 418));
			this.setLayout(null);
			this.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("panelProject")));
			
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);			
					
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("deprecation")
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
					lblAtt.setBounds(POSX_COLUMN1, POSY + INCREMENT_POSY * numberAttributes, 150, 20);
					
					// Create the textbox, depends of the attribute type (using reflection)
					Field attField = Project.class.getDeclaredField(att.getName());
					attField.setAccessible(true);
					NumericTextField tbNumericAttValue;
					JTextField tbStringAttValue;
					CalendarField tbDateAttValue;
					
					if (att.getType() == int.class || att.getType() == double.class) {
						tbNumericAttValue = new NumericTextField();
						tbNumericAttValue.setName("attributeValue_"+att.getName()+"_"+numberAttributes);
						tbNumericAttValue.setText("");
						if (attField.get(project) != null) {
							tbNumericAttValue.setText(attField.get(project).toString());
						}

//						tbAttValue = tbNumericAttValue;
						this.add(tbNumericAttValue);
						tbNumericAttValue.setBounds(POSX_COLUMN2, POSY + INCREMENT_POSY * numberAttributes, 180, 20);
						tbNumericAttValue.setEditable(editable);
					}
					else if (att.getType() == java.util.Date.class) {						 
						DateDocument dd = new DateDocument(Locale.getDefault(), DateSeparator.SLASH); 
						tbDateAttValue = new CalendarField(dd); 
						tbDateAttValue.setCalendarButtonIcon(ImagesUtilities.class.getClassLoader().getResource("images/menus/calendar.png"));
						GregorianCalendar gc = new GregorianCalendar();
						tbDateAttValue.getDateDocument().setDate(gc.getTimeInMillis());
						tbDateAttValue.setName("attributeValue_"+att.getName()+"_"+numberAttributes);
						if (attField.get(project) != null) {
							Date date = (Date) attField.get(project);
							gc.set(GregorianCalendar.YEAR, date.getYear() + 1900); 
							gc.set(GregorianCalendar.MONTH, date.getMonth()); 
							gc.set(GregorianCalendar.DAY_OF_MONTH, date.getDate()); 
							tbDateAttValue.getDateDocument().setDate(gc.getTimeInMillis());
						}

//						tbAttValue = tbDateAttValue;
						this.add(tbDateAttValue);
						tbDateAttValue.setBounds(POSX_COLUMN2, POSY + INCREMENT_POSY * numberAttributes, 180, 20);
						if (!editable)
							tbDateAttValue.setEnabled(editable);
					}
					else {
						tbStringAttValue = new JTextField();
						tbStringAttValue.setName("attributeValue_"+att.getName()+"_"+numberAttributes);
						if (attField.get(project) != null) {	
							tbStringAttValue.setText(attField.get(project).toString());
						}

//						tbAttValue = tbStringAttValue;
						this.add(tbStringAttValue);
						tbStringAttValue.setEditable(editable);
						tbStringAttValue.setBounds(POSX_COLUMN2, POSY + INCREMENT_POSY * numberAttributes, 180, 20);						
					}
					
					
//					if (mandatoryFields.contains(att.getName()))
//						tbAttValue.setInputVerifier(new NotEmptyValidator(parent, tbAttValue, ApplicationInternationalization.getString("fieldMandatory")));
					
					numberAttributes++;
				}			
			}
			revalidate();
			repaint();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
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
		newProject.setStartDate((Date)findAttValue("startDate"));
		newProject.setEndDate((Date)findAttValue("endDate"));
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
			 if (components[i] instanceof NumericTextField) {
				NumericTextField tf = (NumericTextField)components[i];
				if (tf.getName().contains("_"+name)) {					
					value = tf.getText();
					found = true;
				}
			}
			if (components[i] instanceof JTextField) {
				JTextField tf = (JTextField)components[i];
				if (tf.getName().contains("_"+name)) {					
					value = tf.getText();
					found = true;
				}
			}
			else if (components[i] instanceof CalendarField) {
				CalendarField tf = (CalendarField)components[i];
				if (tf.getName().contains("_"+name)) {					
					value = tf.getDateDocument().getDate();
					found = true;
				}
			}
		}
		return value;
	}

	public void clean() {
		showData(new Project(), true);		
	}

	// Method used to validate data
	public boolean isValidData() {
		boolean result = true;
		result = !findAttValue("name").toString().isEmpty();
		result = !findAttValue("description").toString().isEmpty();		
		result = !findAttValue("budget").toString().isEmpty();
		result = !findAttValue("domain").toString().isEmpty();
		result = !findAttValue("estimatedHours").toString().isEmpty();
		result = !findAttValue("progLanguage").toString().isEmpty();
		result = !findAttValue("quantityLines").toString().isEmpty();
		
		if (result) {
			Calendar endCal = Calendar.getInstance();
			endCal.setTime((Date)findAttValue("endDate"));
			Calendar initCal = Calendar.getInstance();
			initCal.setTime((Date)findAttValue("startDate"));
			if (endCal.before(initCal)) {
				result = false;
				JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("dateComparison"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("incompleteDataCBR"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}

}
