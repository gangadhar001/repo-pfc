package presentation.CBR;

import internationalization.ApplicationInternationalization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.control.CBR.EnumSimilFunctions;
import model.business.control.CBR.similarity.local.Equal;
import model.business.control.CBR.similarity.local.Interval;
import model.business.control.CBR.similarity.local.LocalSimilarityFunction;
import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

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
		
	public panelConfigSimil(JDConfigProject parent) {
		this.parent = parent;
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	private void initGUI() {
		try {
			{
			}
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
					{
						lblAttributes = new JLabel();
						pnlAttributes.add(lblAttributes);
						lblAttributes.setBounds(17, 23, 77, 16);
						lblAttributes.setText(ApplicationInternationalization.getString("lblAttributes"));
					}
					{
						lblFunction = new JLabel();
						pnlAttributes.add(lblFunction);
						lblFunction.setBounds(POSX_COLUMN2 + 10, 23, 99, 16);
						lblFunction.setName("lblFunction");
						lblFunction.setText(ApplicationInternationalization.getString("lblFunction"));
					}
					{
						lblWeight = new JLabel();
						pnlAttributes.add(lblWeight);
						lblWeight.setBounds(433, 23, 38, 16);
						lblWeight.setName("lblWeight");
						lblWeight.setText(ApplicationInternationalization.getString("lblWeight"));
					}
					
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
					btnCancel.setBounds(429, 522, 91, 23);
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
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	// Method used to retrieve all the attributes from one project (case) and show them in the UI
	private void setAttributesName() throws RemoteException, Exception {
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
				if ((att.getType() == double.class) || (att.getType() == int.class)) {
					cbFunctions.addItem(EnumSimilFunctions.Interval.name());
					// Add spinner used to select the value of the function parameter
					SpinnerModel jSpinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
					JSpinner spinner = new JSpinner();
					pnlAttributes.add(spinner);
					spinner.setModel(jSpinnerModel);
					spinner.setName("spinner_"+numberAttributes);
					spinner.setBounds(POSX_COLUMN3, POSY + INCREMENT_POSY * numberAttributes, 50, 25);
					
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
	
	// Disable the function parameter when its type isn't Interval
	private void cbFunctionsActionPerformed(ActionEvent evt) {
		JComboBox combo = (JComboBox) evt.getSource();
		int row = Integer.parseInt(combo.getName().substring(combo.getName().lastIndexOf("_")+1));
		if (!combo.getSelectedItem().toString().equals(EnumSimilFunctions.Interval.name())) {
			for (Object o : pnlAttributes.getComponents()) {
				if (o instanceof JSpinner) {
					if (((JSpinner)o).getName().contains(String.valueOf(row)))
						((JSpinner)o).setEnabled(false);
				}
			}
		}
		else {
			for (Object o : pnlAttributes.getComponents()) {
				if (o instanceof JSpinner) {
					if (((JSpinner)o).getName().contains(String.valueOf(row)))
						((JSpinner)o).setEnabled(true);
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
		List<Project> cases;
		try {
			cases = ClientController.getInstance().getProjects();
			List<Project> result = ClientController.getInstance().executeAlgorithm(EnumAlgorithmCBR.valueOf(cbAlgorithm.getSelectedItem().toString()), cases, caseToEval, configCBR, numberCases);
			// TODO: mostrar numero de resultados encontrados
			if (result.size() > 0) {
				JDRetrievalCases showCases = new JDRetrievalCases(cases);
				parent.getGlassPane().stop();
				parent.dispose();
				CursorUtilities.showDefaultCursor(this);
				showCases.setLocationRelativeTo(null);
				showCases.setModal(true);
				showCases.setVisible(true);					
			}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}			
		
	}

	private List<Object> searchValuesFromRow(int row) {
		List<Object> result = new ArrayList<Object>();
		for(Object o : pnlAttributes.getComponents()) {
			if (o instanceof JLabel) {
				JLabel lbl = (JLabel)o;
				// Weight
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
				if (cb.getName().contains(String.valueOf(row))) {
					LocalSimilarityFunction function = null;
					if (cb.getSelectedItem().toString().equals(EnumSimilFunctions.Interval.name())) {
						function = new Interval();
						((Interval)function).setInterval(getIntervalValue(row));
					}
					if (cb.getSelectedItem().toString().equals(EnumSimilFunctions.Equal.name()))
						function = new Equal();
					if (cb.getSelectedItem().toString().equals(EnumSimilFunctions.Enum.name()))
						//TODO: function = new EnumDistance();
						;
					result.add(function);					
				}
			}
		}
		
		return result;
	}

	private double getIntervalValue(int row) {
		boolean found = false;
		double result = 0.0;
		Object[] components = pnlAttributes.getComponents();
		for (int i = 0; i < components.length && !found; i++) {
			if (components[i] instanceof JSpinner) {
				JSpinner sp = (JSpinner)components[i];
				// Function parameter
				if (sp.getName().contains(String.valueOf(row))) {					
					result = Double.parseDouble(sp.getValue().toString());
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
}
