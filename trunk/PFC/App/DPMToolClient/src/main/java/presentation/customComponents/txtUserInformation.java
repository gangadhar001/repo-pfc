package presentation.customComponents;

import internationalization.ApplicationInternationalization;

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
		// Create text and styles for TxetPane
		String[] initString = {
				ApplicationInternationalization.getString("UserInfo")+ "\n", 
				"    " + ApplicationInternationalization.getString("userName") + " ",
				val.getName(),
				"\n",
				"    " + ApplicationInternationalization.getString("userSurname") + " ",
				val.getSurname(),
				"\n",
				"    " + ApplicationInternationalization.getString("userRole") + " ", 
				ApplicationInternationalization.getString(val.getRole().name()),
				"\n",
				"    " + ApplicationInternationalization.getString("userSeniority") + " ",
				String.valueOf(val.getSeniority()),
				"\n\n",
				ApplicationInternationalization.getString("company") + ": ",
				val.getCompany().getName() + ", " + val.getCompany().getAddress().getCity() + "(" 
				+ val.getCompany().getAddress().getCountry() + ")", 
				"\n"
		};
		
		String[] initStyles = { 
				"bold", 
				"bold", "regular", "regular",
				"bold", "regular", "regular",
				"bold", "regular", "regular",
				"bold", "regular", "regular",
				"bold", 
				"regular", "regular",
		};
		
		TextPaneUtilities.setStyledText(this, initString, initStyles);
	}
	
	
}
