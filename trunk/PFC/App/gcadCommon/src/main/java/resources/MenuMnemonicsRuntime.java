package resources;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;

/**
 * This is a utility class which can be used to set the mnemonics of all the
 * items in a menubar on runtime.
 */
public class MenuMnemonicsRuntime {

	// REFERENCE: http://www.javareference.com/jrexamples/viewexample.jsp?id=95

	public static void setMnemonics(JMenuBar mb) {
		MenuElement[] menuElements = mb.getSubElements();
		int num = menuElements.length;
		if (num > 0) {
			setMnemonicForTopLevel(menuElements);
			for (int i = 0; i < num; ++i) {
				if (menuElements[i] instanceof JMenu)					
					setMnemonic((JMenu) menuElements[i]);
			}
		}
	}

	//
	// This method sets mnemonic of all the top level menus in the menubar
	//
	private static void setMnemonicForTopLevel(MenuElement[] menuElements) {
		int num = menuElements.length;
		char mnemonics[] = null;
		if (num > 0) {

			mnemonics = new char[num];
			for (int i = 0; i < num; i++) {
				if (menuElements[i] instanceof JMenu) {
					
					JMenu menu = (JMenu) menuElements[i];		
					String text = menu.getText();
					// Get the corresponding mnemonic for this text
					char mnemonic = getMnemonic(text, mnemonics);
					if (' ' != mnemonic) {
						menu.setMnemonic(mnemonic);
						mnemonics[i] = mnemonic;
					}
				}
			}
		}
	}

	//
	// This method sets mnemonic of the menuitems and submenus of a given menu.
	// To set different Mnemonic of each item it firsr checks first character of
	// the all the words then the second , third
	// and so on till a unique character is found.
	private static void setMnemonic(JMenu jm) {
		Component mcomps[] = jm.getMenuComponents();
		int num = mcomps.length;

		if (num > 0) { 
			char mnemonics[] = new char[num];
	
			for (int i = 0; i < num; i++) {
				if (mcomps[i] instanceof JMenuItem) {					
					JMenuItem menuitem = (JMenuItem) mcomps[i];
					
					String text = menuitem.getText();
					char mnemonic = getMnemonic(text, mnemonics);
					if (' ' != mnemonic) {
						menuitem.setMnemonic(mnemonic);
						menuitem.setAccelerator(KeyStroke.getKeyStroke(mnemonic, ActionEvent.ALT_MASK));
						mnemonics[i] = mnemonic;
					}
					
					if (menuitem instanceof JMenu)
						setMnemonic((JMenu) menuitem);
				}
			}
		}
	}

	private static char getMnemonic(String text, char[] mnemonics) {
		Vector<String> words = new Vector<String>();
		StringTokenizer t = new StringTokenizer(text);
		int maxsize = 0;

		while (t.hasMoreTokens()) {
			String word = t.nextToken();
			if (word.length() > maxsize)
				maxsize = word.length();
			words.addElement(word);
		}
		words.trimToSize();

		for (int i = 0; i < maxsize; ++i) {
			char mnemonic = getMnemonic(words, mnemonics, i);
			if (' ' != mnemonic)
				return mnemonic;
		}

		return ' ';
	}

	private static char getMnemonic(Vector<String> words, char[] mnemonics, int index) {
		int numwords = words.size();

		for (int i = 0; i < numwords; i++) {
			String word = words.elementAt(i);
			if (index < word.length()) {
				char c = word.charAt(index);
				if (!isMnemonicExists(c, mnemonics))
					return c;
			}
		}
		return ' ';
	}

	private static boolean isMnemonicExists(char c, char[] mnemonics) {
		int num = mnemonics.length;
		boolean exists = false;
		for (int i = 0; i < num && !exists; i++)
			if (mnemonics[i] == c)
				exists = true;
		return exists;
	}

}
