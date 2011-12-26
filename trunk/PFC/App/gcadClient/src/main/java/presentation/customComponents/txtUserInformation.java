package presentation.customComponents;

import internationalization.ApplicationInternationalization;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JTextPane;

import model.business.knowledge.User;
import presentation.utils.TextPaneUtilities;

public class txtUserInformation extends JTextPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4883862810110118404L;

	public txtUserInformation() {
		super();
		setEditable(false);
	}
	
	public void showUserInfo(User val) {
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		// Create text and styles for TxetPane
		String[] initString = {
				ApplicationInternationalization.getString("user")+ ": ", 
				val.getName() + ", " + val.getSurname(),
				"\n",
				ApplicationInternationalization.getString("company") + ": ",
				val.getCompany().getName() + ", " + val.getCompany().getAddress().getCity() + "(" 
				+ val.getCompany().getAddress().getCountry() + ")", 
				"\n"
		};
		
		String[] initStyles = { 
				"bold", "regular", "regular",
				"bold", "regular", "regular",
		};
		
		TextPaneUtilities.setStyledText(this, initString, initStyles);
	}
	
	
}
