package presentation.CBR;

import internationalization.ApplicationInternationalization;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.CaseEval;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.control.CBR.EnumSimilFunctions;
import model.business.control.CBR.similarity.local.Difference;
import model.business.control.CBR.similarity.local.Equal;
import model.business.control.CBR.similarity.local.LocalSimilarityFunction;
import model.business.control.CBR.similarity.local.Threshold;
import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.customComponents.NumericTextField;
import resources.CursorUtilities;
import bussiness.control.ClientController;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

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
public class panelConfigSimil extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3288470197499471033L;
	private JPanel pnlAlgorithm;
	private JButton btnClean;
	private JLabel lblAlgorithm;
	private JButton btnOK;
	private JButton btnCancel;
	private JLabel lblParams;
	private JLabel lblWeight;
	private JLabel lblFunction;
	private JLabel lblAttributes;
	private JPanel pnlAttributes;
	private JTextField txtNumberK;
	private JLabel lblNumberK;
	private JComboBox cbAlgorithm;

	/**
	* Auto-generated main method to display this JDialog
	*/
	
	private final int POSX_COLUMN1 = 17;
	private final int POSX_COLUMN2 = 145;
	private final int POSX_COLUMN3 = 286;
	private final int POSX_COLUMN4 = 413;
	
	private final int POSY = 23;
	private final int INCREMENT_POSY = 35;
	private Hashtable<Integer, JLabel> dic;
	private int numberAttributes;
	private List<Attribute> attributes;
	private Project caseToEval;
	private JDConfigProject parent;
	private JLabel lblThreshold;
		
	public panelConfigSimil(JDConfigProject parent) {
		this.parent = parent;
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	private void initGUI() {
		try {
			this.setLayout(null);
			this.setSize(520, 557);
			this.setPreferredSize(new java.awt.Dimension(520, 557));
			{
				{
					pnlAlgorithm = new JPanel();
					this.add(pnlAlgorithm);
					pnlAlgorithm.setBounds(-1, 0, 520, 100);
					pnlAlgorithm.setBorder(BorderFactory.createTitledBorder(""));
					pnlAlgorithm.setLayout(null);
					{
						lblAlgorithm = new JLabel();
						pnlAlgorithm.add(lblAlgorithm);
						lblAlgorithm.setBounds(17, 20, 54, 16);
						lblAlgorithm.setName("lblAlgorithm");
						lblAlgorithm.setText(ApplicationInternationalization.getString("lblAlgorithm"));
					}
					{
						cbAlgorithm = new JComboBox();
						pnlAlgorithm.add(cbAlgorithm);
						for (EnumAlgorithmCBR ea : EnumAlgorithmCBR.values())
							cbAlgorithm.addItem(ea.name());
						cbAlgorithm.setBounds(89, 17, 210, 23);
					}
					{
						lblNumberK = new JLabel();
						pnlAlgorithm.add(lblNumberK);
						lblNumberK.setBounds(17, 63, 65, 16);
						lblNumberK.setName("lblNumberK");
						lblNumberK.setText(ApplicationInternationalization.getString("lblKNumber"));
					}
					{
						txtNumberK = new JTextField();
						pnlAlgorithm.add(txtNumberK);
						txtNumberK.setBounds(89, 60, 210, 23);
					}
				}
				{
					pnlAttributes = new JPanel();
					this.add(pnlAttributes);
					pnlAttributes.setBounds(0, 110, 520, 400);
					pnlAttributes.setBorder(BorderFactory.createTitledBorder(""));
					pnlAttributes.setLayout(null);
					addTitleLabels();
					
					// Change labels of slider
					dic = new Hashtable<Integer, JLabel>();
					for (int i=0; i<=100; i++) {
						double aux = new Double(i);
						double value = aux / 100.00;
						dic.put(new Integer(i), new JLabel(String.valueOf(value)));
					}
					{
						lblParams = new JLabel();
						pnlAttributes.add(lblParams);
						lblParams.setBounds(POSX_COLUMN3, 23, 79, 16);
						lblParams.setName("lblParams");
						lblParams.setText(ApplicationInternationalization.getString("lblParams"));
					}
				}
				{
					btnCancel = new JButton();
					this.add(btnCancel);
					btnCancel.setBounds(429, 522, 85, 23);
					btnCancel.setName("btnCancel");
					btnCancel.setAction(getAppActionMap().get("Back"));
					btnCancel.setText(ApplicationInternationalization.getString("btnBack"));
				}
				{
					btnOK = new JButton();
					this.add(btnOK);
					btnOK.setBounds(327, 522, 91, 23);
					btnOK.setName("btnOK");
					btnOK.setAction(getAppActionMap().get("OK"));
					btnOK.setText(ApplicationInternationalization.getString("btnFinish"));
				}
				{
					btnClean = new JButton();
					this.add(btnClean);
					btnClean.setBounds(0, 522, 14, 10);
					btnClean.setSize(91, 23);
					btnClean.setAction(getAppActionMap().get("Clean"));
					btnClean.setText(ApplicationInternationalization.getString("btnClear"));
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addTitleLabels() {
		{
			lblAttributes = new JLabel();
			pnlAttributes.add(lblAttributes);
			lblAttributes.setBounds(17, 23, 77, 16);
			lblAttributes.setText(ApplicationInternationalization.getString("lblAttributes"));
			lblAttributes.setFont(new Font(lblAttributes.getFont().getName(), Font.BOLD, 12));
		}
		{
			lblFunction = new JLabel();
			pnlAttributes.add(lblFunction);
			lblFunction.setBounds(POSX_COLUMN2 + 15, 23, 99, 16);
			lblFunction.setName("lblFunction");
			lblFunction.setText(ApplicationInternationalization.getString("lblFunction"));
			lblFunction.setFont(new Font(lblFunction.getFont().getName(), Font.BOLD, 12));
		}
		{
			lblThreshold = new JLabel();
			pnlAttributes.add(lblThreshold);
			lblThreshold.setBounds(POSX_COLUMN3 + 15, 23, 99, 16);
			lblThreshold.setName("lblThreshold");
			lblThreshold.setText(ApplicationInternationalization.getString("lblThreshold"));
			lblThreshold.setFont(new Font(lblThreshold.getFont().getName(), Font.BOLD, 12));
		}
		{
			lblWeight = new JLabel();
			pnlAttributes.add(lblWeight);
			lblWeight.setBounds(POSX_COLUMN4 + 20, 23, 38, 16);
			lblWeight.setName("lblWeight");
			lblWeight.setText(ApplicationInternationalization.getString("lblWeight"));
			lblWeight.setFont(new Font(lblWeight.getFont().getName(), Font.BOLD, 12));
		}
		
	}

	// Method used to retrieve all the attributes from one project (case) and show them in the UI
	private void setAttributesName() throws RemoteException, Exception {
		pnlAttributes.removeAll();
		addTitleLabels();
		attributes = ClientController.getInstance().getAttributesFromProject(caseToEval);
		// Show attributes name 
		numberAttributes = 1;
		for (Attribute att : attributes) {
			// Ignore id and UID
			if (!att.getName().equals("id") && (!att.getName().equals("serialVersionUID"))) {
				JLabel lblAtt = new JLabel ();
				lblAtt.setName("attribute_"+att.getName()+"_"+numberAttributes);
				lblAtt.setText(ApplicationInternationalization.getString(att.getName()));
				pnlAttributes.add(lblAtt);
				lblAtt.setBounds(POSX_COLUMN1, POSY + INCREMENT_POSY * numberAttributes, 120, 16);
				
				// Show local similarity function supported for that attribute type
				JComboBox cbFunctions = new JComboBox();
				cbFunctions.addItem(EnumSimilFunctions.Equal.name());
				cbFunctions.addItem(EnumSimilFunctions.Difference.name());
				if ((att.getType() == double.class) || (att.getType() == int.class) || (att.getType() == Date.class)) {
					cbFunctions.addItem(EnumSimilFunctions.Threshold.name());
					// Add numeric text field in order to select the threshold
					NumericTextField nText = new NumericTextField();
					nText.setName("nText"+numberAttributes);
					pnlAttributes.add(nText);
					nText.setBounds(POSX_COLUMN3, POSY + INCREMENT_POSY * numberAttributes, 100, 25);
					
				}
				if (att.getType() == Enum.class)
					cbFunctions.addItem(EnumSimilFunctions.Enum.name());
				cbFunctions.setName("cb_" + numberAttributes);
				cbFunctions.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						cbFunctionsActionPerformed(evt);
					}
				});
				cbFunctions.setSelectedIndex(0);
				
				pnlAttributes.add(cbFunctions);
				cbFunctions.setBounds(POSX_COLUMN2, POSY + INCREMENT_POSY * numberAttributes, 100, 25);
				
				// Add slider for weights
				JSlider slider = new JSlider();
				pnlAttributes.add(slider);
				slider.setBounds(POSX_COLUMN4, POSY + INCREMENT_POSY * numberAttributes , 70, 30);
				slider.setName("slider_"+numberAttributes);
				slider.setMinimum(0);
				slider.setValue(0);
				slider.addChangeListener(new ChangeListener() {
					
					@Override
					public void stateChanged(ChangeEvent e) {
						String name = ((JSlider)e.getSource()).getName();
						double value = new Double(((JSlider)e.getSource()).getValue());
						for (Object o : pnlAttributes.getComponents()) {
							if (o instanceof JLabel) {
								if (((JLabel)o).getName() != null && ((JLabel)o).getName().equals("lbl_"+name))
									((JLabel)o).setText(String.valueOf(value/100));
							}
						}						
					}
				});
				slider.setLabelTable(dic);
				JLabel lblValueSlider = new JLabel();
				lblValueSlider.setName("lbl_slider_"+numberAttributes);
				lblValueSlider.setText("0.0");
				pnlAttributes.add(lblValueSlider);
				lblValueSlider.setBounds(POSX_COLUMN4 + 80, POSY + INCREMENT_POSY * numberAttributes, 50, 25);
				
				numberAttributes ++;
			}				
		}		
	}
	
	// Disable the function parameter when its type isn't Threshold
	private void cbFunctionsActionPerformed(ActionEvent evt) {
		JComboBox combo = (JComboBox) evt.getSource();
		// Row from selected attribute
		int row = Integer.parseInt(combo.getName().substring(combo.getName().lastIndexOf("_")+1));
		if (!combo.getSelectedItem().toString().equals(EnumSimilFunctions.Threshold.name())) {
			for (Object o : pnlAttributes.getComponents()) {
				if (o instanceof JComboBox) {
					if (((JComboBox)o).getName().equals("cbOperations_"+row))
						((JComboBox)o).setEnabled(false);
				}
				else if (o instanceof NumericTextField) {
					if (((NumericTextField)o).getName().contains(String.valueOf(row)))
						((NumericTextField)o).setEnabled(false);
				}
			}
		}
		else {
			for (Object o : pnlAttributes.getComponents()) {
				if (o instanceof JComboBox) {
					if (((JComboBox)o).getName().equals("cbOperations_"+row))
						((JComboBox)o).setEnabled(true);
				}
				else if (o instanceof NumericTextField) {
					if (((NumericTextField)o).getName().contains(String.valueOf(row)))
						((NumericTextField)o).setEnabled(true);
				}
			}	
		}
	}
	
	@Action
	public void Back() {
		parent.back();
	}
	
	@Action 
	public void OK() {
		CursorUtilities.showWaitCursor(this);
		// Validate data and generate the configuration used to retrieve similar cases to the given case (project)
		boolean valid = true;		
		if (cbAlgorithm.getSelectedIndex() == -1)
			valid = false; 
		if (valid) {
			// Invoke a new thread in order to show the panel with the process
			// spinner
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					parent.getGlassPane().setColorB(241);
					parent.getGlassPane().start();
					Thread performer = new Thread(new Runnable() {
						public void run() {
							processCases();
						}
					}, "Performer");
					performer.start();
				}
			});
		}
		else {
			CursorUtilities.showDefaultCursor(this);
			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("SelectAlgorithm"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void processCases() {
		int numberCases = 0;
		try {
			 numberCases = Integer.parseInt(txtNumberK.getText());
		}
		catch (NumberFormatException e) { }
		
		ConfigCBR configCBR = new ConfigCBR();
		
		// Search values of similarity function and weights for each attribute
		for(Object o : pnlAttributes.getComponents()) {
			if (o instanceof JLabel) {
				JLabel lbl = (JLabel)o;
				// Search attribute
				if (lbl.getName() != null && lbl.getName().contains("attribute")) {
					int index = Integer.parseInt(lbl.getName().substring(lbl.getName().lastIndexOf("_")+1));
					// Ignore id and SerialUID
					Attribute att = attributes.get(index + 1);
					// Get the values (similarity function, function parameter and weight) for that attribute
					List<Object> values = searchValuesFromRow(index);
					LocalSimilarityFunction function = null;
					for (Object ob: values) {
						// Weight
						if (ob instanceof Double)
							configCBR.setWeight(att, (Double)ob);
						// Similarity Function
						else if (ob instanceof LocalSimilarityFunction) {
							function = (LocalSimilarityFunction)ob;
							configCBR.addLocalSimFunction(att, function);
						}														
					}
				}
			}
		}
		
		// Launch the algorithm
		try {
			List<CaseEval> result = ClientController.getInstance().executeAlgorithm(EnumAlgorithmCBR.valueOf(cbAlgorithm.getSelectedItem().toString()), caseToEval, configCBR, numberCases);
			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("retrievalCases") + ": " + result.size() + " " + ApplicationInternationalization.getString("casesName"), ApplicationInternationalization.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
			if (result.size() > 0) {
				JDRetrievalCases showCases = new JDRetrievalCases(result, parent.getParentD());
				parent.getGlassPane().stop();
				parent.dispose();
				CursorUtilities.showDefaultCursor(this);
				showCases.setLocationRelativeTo(null);
				showCases.setModal(true);
				showCases.setVisible(true);					
			}
		} catch (RemoteException e) {
			parent.getGlassPane().stop();
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			parent.getGlassPane().stop();
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			parent.getGlassPane().stop();
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			parent.getGlassPane().stop();
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			parent.getGlassPane().stop();
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}			
		
	}

	// Search the value of the local similarity function for one attribute
	private List<Object> searchValuesFromRow(int row) {
		List<Object> result = new ArrayList<Object>();
		for(Object o : pnlAttributes.getComponents()) {
			if (o instanceof JLabel) {
				JLabel lbl = (JLabel)o;
				// Get Weight
				if (lbl.getName() != null && lbl.getName().contains("lbl_slider")) {
					int index = Integer.parseInt(lbl.getName().substring(lbl.getName().lastIndexOf("_")+1));
					if (index == row) {
						result.add(Double.parseDouble(lbl.getText()));
					}
				}
			}
			if (o instanceof JComboBox) {
				JComboBox cb = (JComboBox)o;
				// Similarity Function
				if (cb.getName().equals("cb_" + String.valueOf(row))) {
					LocalSimilarityFunction function = null;
					if (cb.getSelectedItem().toString().equals(EnumSimilFunctions.Threshold.name())) {
						function = new Threshold();
						((Threshold)function).setThreshold(getThresholdValue(row));
					}
					else if (cb.getSelectedItem().toString().equals(EnumSimilFunctions.Difference.name()))
						function = new Difference();
					// TODO: enum
//					else if (cb.getSelectedItem().toString().equals(EnumSimilFunctions.Enum.name()))
//						function = new Enum();
					else if (cb.getSelectedItem().toString().equals(EnumSimilFunctions.Equal.name()))
						function = new Equal();
					result.add(function);					
				}
			}
		}
		
		return result;
	}

	// Get value of threshold for one attribute
	private double getThresholdValue(int row) {
		boolean found = false;
		double result = 0.0;
		Object[] components = pnlAttributes.getComponents();
		for (int i = 0; i < components.length && !found; i++) {
			if (components[i] instanceof NumericTextField) {
				NumericTextField sp = (NumericTextField)components[i];
				// Function parameter
				if (sp.getName().contains(String.valueOf(row))) {					
					result = Double.parseDouble(sp.getText().toString());
					found = true;
				}
			}
		}
		return result;
	}

	public void setProject(Project project) {
		caseToEval = project;
		try {
			setAttributesName();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Action
	public void Clean() {
		txtNumberK.setText("");
		cbAlgorithm.setSelectedIndex(-1);
		if (caseToEval == null)
			caseToEval = new Project();
		setProject(caseToEval);
	}
	
	
}
