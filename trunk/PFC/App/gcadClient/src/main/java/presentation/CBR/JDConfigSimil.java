package presentation.CBR;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.retrieveAlgorithms.NNConfig;
import model.business.control.CBR.retrieveAlgorithms.NNMethod;
import model.business.control.CBR.similarity.local.EnumDistance;
import model.business.control.CBR.similarity.local.Equal;
import model.business.control.CBR.similarity.local.Interval;
import model.business.control.CBR.similarity.local.LocalSimilarityFunction;
import model.business.knowledge.EnumAlgorithmCBR;
import model.business.knowledge.EnumSimilFunctions;
import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

import bussiness.control.ClientController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class JDConfigSimil extends javax.swing.JDialog {
	private JPanel pnlAlgorithm;
	private JLabel lblAlgorithm;
	private JButton btnOK;
	private JButton btnCancel;
	private JPanel pnlButtons;
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
	private final int POSX_COLUMN2 = 132;
	private final int POSX_COLUMN3 = 286;
	private final int POSX_COLUMN4 = 413;
	
	private final int POSY = 23;
	private final int INCREMENT_POSY = 35;
	private Hashtable dic;
	private int numberAttributes;
	private List<Attribute> attributes;
	private Project caseToEval;
		
	public JDConfigSimil(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					pnlAlgorithm = new JPanel();
					getContentPane().add(pnlAlgorithm);
					pnlAlgorithm.setBounds(0, 0, 574, 96);
					pnlAlgorithm.setBorder(BorderFactory.createTitledBorder(""));
					pnlAlgorithm.setLayout(null);
					{
						lblAlgorithm = new JLabel();
						pnlAlgorithm.add(lblAlgorithm);
						lblAlgorithm.setBounds(17, 20, 54, 16);
						lblAlgorithm.setName("lblAlgorithm");
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
					}
					{
						txtNumberK = new JTextField();
						pnlAlgorithm.add(txtNumberK);
						txtNumberK.setBounds(89, 60, 210, 23);
					}
				}
				{
					pnlAttributes = new JPanel();
					getContentPane().add(pnlAttributes);
					pnlAttributes.setBounds(0, 108, 574, 420);
					pnlAttributes.setBorder(BorderFactory.createTitledBorder(""));
					pnlAttributes.setLayout(null);
					{
						lblAttributes = new JLabel();
						pnlAttributes.add(lblAttributes);
						lblAttributes.setBounds(17, 23, 77, 16);
					}
					{
						lblFunction = new JLabel();
						pnlAttributes.add(lblFunction);
						lblFunction.setBounds(132, 23, 99, 16);
						lblFunction.setName("lblFunction");
					}
					{
						lblWeight = new JLabel();
						pnlAttributes.add(lblWeight);
						lblWeight.setBounds(433, 23, 38, 16);
						lblWeight.setName("lblWeight");
					}
					
					// Change labels of slider
					dic = new Hashtable();
					for (int i=0; i<=100; i++) {
						double aux = new Double(i);
						double value = aux / 100.00;
						dic.put(new Integer(i), new JLabel(String.valueOf(value)));
					}
					setAttributesName();
					{
						lblParams = new JLabel();
						pnlAttributes.add(lblParams);
						lblParams.setBounds(286, 23, 79, 16);
						lblParams.setName("lblParams");
					}
				}
				{
					pnlButtons = new JPanel();
					getContentPane().add(pnlButtons);
					pnlButtons.setBounds(0, 534, 574, 51);
					pnlButtons.setLayout(null);
					{
						btnCancel = new JButton();
						pnlButtons.add(btnCancel);
						btnCancel.setBounds(490, 11, 74, 25);
						btnCancel.setName("btnCancel");
						btnCancel.setAction(getAppActionMap().get("Cancel"));
					}
					{
						btnOK = new JButton();
						pnlButtons.add(btnOK);
						btnOK.setBounds(405, 11, 74, 25);
						btnOK.setName("btnOK");
						btnOK.setAction(getAppActionMap().get("OK"));
					}
				}
			}
			this.setSize(590, 623);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

	// Method used to retrieve all the attributes from one project (case) and show them in the UI
	private void setAttributesName() throws RemoteException, Exception {
		caseToEval = new Project();
		attributes = ClientController.getInstance().getAttributesFromProject(caseToEval);
		// Show attributes name 
		numberAttributes = 1;
		for (Attribute att : attributes) {
			// Ignore id and UID
			if (!att.getName().equals("id") && (!att.getName().equals("serialVersionUID"))) {
				JLabel lblAtt = new JLabel ();
				//TODO: internacionalizacion
				lblAtt.setName("attribute_"+att.getName()+"_"+numberAttributes);
				lblAtt.setText(att.getName());
				pnlAttributes.add(lblAtt);
				lblAtt.setBounds(POSX_COLUMN1, POSY + INCREMENT_POSY * numberAttributes, 57, 16);
				
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
								if (((JLabel)o).getName().equals("lbl_"+name))
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
	public void Cancel() {
		this.dispose();
	}
	
	@Action 
	public void OK() {
		// Validate data and generate the configuration used to retrieve similar cases to the given case (project)
		boolean valid = true;
		int numberCases = Integer.parseInt(txtNumberK.getText());
		NNConfig configCBR = new NNConfig();
		
		if (cbAlgorithm.getSelectedIndex() == -1)
			valid = false; // TODO error
		if (valid) {
			// TODO: procesando ... -> spinner
			
			// Search values of similarity function and weights for each attribute
			for(Object o : pnlAttributes.getComponents()) {
				if (o instanceof JLabel) {
					JLabel lbl = (JLabel)o;
					// Search attribute
					if (lbl.getName().contains("attribute")) {
						int index = Integer.parseInt(lbl.getName().substring(lbl.getName().lastIndexOf("_")+1)) - 1;
						Attribute att = attributes.get(index);
						// Get the values (similarity function, function parameter and weight) for that attribute
						List<Object> values = searchValuesFromRow(index+1);
						LocalSimilarityFunction function = null;
						for (Object ob: values) {
							// Weight
							if (ob instanceof Double)
								configCBR.setWeight(att, (Double)ob);
							// Similarity Function
							else if (ob instanceof String) {
								String functionName = ob.toString();
								if (functionName.equals(EnumSimilFunctions.Interval.name()))
									function = new Interval();
								if (functionName.equals(EnumSimilFunctions.Equal.name()))
									function = new Equal();
								if (functionName.equals(EnumSimilFunctions.Enum.name()))
									function = new EnumDistance();
							}
							// Function parameter for Interval function
							else if (ob instanceof Integer) {
								if (function != null) {
									((Interval)function).setInterval((Integer)ob);
								}
							}
							configCBR.addLocalSimFunction(att, function);
						}
					}
				}
			}
			
			// Launch the algorithm
			List<Project> cases;
			try {
				cases = ClientController.getInstance().getProjects();
				ClientController.getInstance().executeAlgorithm(EnumAlgorithmCBR.valueOf(cbAlgorithm.getSelectedItem().toString()), cases, caseToEval, configCBR, numberCases);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NonPermissionRole e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotLoggedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	private List<Object> searchValuesFromRow(int row) {
		List<Object> result = new ArrayList<Object>();
		for(Object o : pnlAttributes.getComponents()) {
			if (o instanceof JLabel) {
				JLabel lbl = (JLabel)o;
				// Weight
				if (lbl.getName().contains("lbl_slider")) {
					int index = Integer.parseInt(lbl.getName().substring(lbl.getName().lastIndexOf("_")+1));
					if (index == row) {
						result.add(Double.parseDouble(lbl.getText()));
					}
				}
			}
			if (o instanceof JSpinner) {
				JSpinner sp = (JSpinner)o;
				// Function parameter
				if (sp.getName().contains(String.valueOf(row))) {					
					result.add(Integer.parseInt(sp.getValue().toString()));
					
				}
			}
			if (o instanceof JComboBox) {
				JComboBox cb = (JComboBox)o;
				// Similarity Function
				if (cb.getName().contains(String.valueOf(row))) {
					result.add(cb.getSelectedItem().toString());					
				}
			}
		}
		
		return result;
	}
}
