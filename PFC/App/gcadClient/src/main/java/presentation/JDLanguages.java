package presentation;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.business.knowledge.Language;

import bussiness.control.ClientController;

public class JDLanguages extends javax.swing.JDialog {

	/**
	* Auto-generated main method to display this JDialog
	*/
		
	public JDLanguages(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			setSize(400, 300);
			
			// Take the languages defined in the server
			ArrayList<Language> languages = ClientController.getInstance().getLanguages();
			System.out.println(languages);
			Language l = ClientController.getInstance().getDefaultLanguage();
			Language aux = new Language("English", "en_US");
			ClientController.getInstance().setDefaultLanguage(aux);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
