package presentation;

import gcadServer.Main;
import internationalization.AppInternationalization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import resources.Language;

import model.business.control.Server;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdom.JDOMException;

import resources.LanguagesUtilities;

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
public class JDLanguages extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -785514754688464502L;
	private ButtonGroup rbGroup;
	private JButton btnCancel;
	private JButton btnOk;
	private JLabel lblChoose;
	private Language selectedLanguage = null;
	private Language defaultLanguage;
	
	private static final int HEIGHT = 35;

	/**
	* Auto-generated main method to display this JDialog
	*/
		
	public JDLanguages(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private ApplicationActionMap getAppActionMap() {
		return Application.getInstance().getContext().getActionMap(this);
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				setTitle(AppInternationalization.getString("Title_Language"));
				setResizable(false);
			}
			{
				lblChoose = new JLabel();
				getContentPane().add(lblChoose);
				getContentPane().add(getBtnOk());
				getContentPane().add(getBtnCancel());
				lblChoose.setBounds(12, 14, 158, 16);
				lblChoose.setName("lblChoose");
				lblChoose.setText(AppInternationalization.getString("Label_Language"));
			}
			
			// Take the languages defined in the server
			ArrayList<Language> languages = Server.getInstance().getLanguages();
			defaultLanguage = Server.getInstance().getDefaultLanguage();
			int posY = 45;
			for(Language l :  languages) {			
				JRadioButton radio = new JRadioButton();
				getContentPane().add(radio, "Center");
				getRbGroup().add(radio);
				radio.setName(l.getCode());
				radio.setBounds(50, posY, 116, 34);
				radio.setText(l.getName());
				if (l.getCode().equals(defaultLanguage.getCode()))
					radio.setSelected(true);
				radio.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						selectedLanguage = new Language();
						selectedLanguage.setName(((JRadioButton)e.getSource()).getText());
						selectedLanguage.setCode(((JRadioButton)e.getSource()).getName());
						
					}
				});
				
				posY += HEIGHT;
			}
			
			this.setSize(233, 276);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private ButtonGroup getRbGroup() {
		if(rbGroup == null) {
			rbGroup = new ButtonGroup();
		}
		return rbGroup;
	}

	@Action
	public void OK() {
		if (selectedLanguage != null && !selectedLanguage.getCode().equals(defaultLanguage.getCode())) {
			if (JOptionPane.showConfirmDialog(this, AppInternationalization.getString("Confirm_Change_Language")) == JOptionPane.YES_OPTION) {
				// Set the selected language and restart the application
				try {
					LanguagesUtilities.setDefaultLanguage(selectedLanguage);
					this.dispose();
					// Restart frame
					Main.restart();
				} catch (JDOMException e) {
					JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else
			this.dispose();
	}
	
	@Action
	public void Cancel () {
		this.dispose();
	}
	
	private JButton getBtnOk() {
		if(btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(31, 201, 82, 25);
			btnOk.setName("btnOk");
			btnOk.setAction(getAppActionMap().get("OK"));
			btnOk.setText(AppInternationalization.getString("btn_OK_Text"));
		}
		return btnOk;
	}
	
	private JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(124, 201, 82, 25);
			btnCancel.setName("btnCancel");
			btnCancel.setAction(getAppActionMap().get("Cancel"));
			btnCancel.setText(AppInternationalization.getString("btn_Cancel_Text"));
		}
		return btnCancel;
	}

}
