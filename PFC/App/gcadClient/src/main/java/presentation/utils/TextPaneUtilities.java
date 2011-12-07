package presentation.utils;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class TextPaneUtilities {

	public static void setStyledText(JTextPane txtAreaKnowledge, String[] initString, String[] initStyles) {
		StyledDocument doc = txtAreaKnowledge.getStyledDocument();
		addStylesToDocument(doc);

		try {
			for (int i = 0; i < initString.length; i++) {
				doc.insertString(doc.getLength(), initString[i],
				doc.getStyle(initStyles[i]));
			}
		} catch (BadLocationException ble) {
		}

	}
	 
	protected static void addStylesToDocument(StyledDocument doc) {
		// Initialize some styles.
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(def, "Tahoma");

		Style s = doc.addStyle("bold", regular);
		StyleConstants.setBold(s, true);
	}
}
