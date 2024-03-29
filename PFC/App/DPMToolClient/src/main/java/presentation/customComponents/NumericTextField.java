package presentation.customComponents;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * Custom Numeric TextField
 *
 */
// REFERENCE: http://www.devx.com/tips/Tip/14311
public class NumericTextField extends JTextField {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3284584209747763414L;
	final static String badchars = "`~!@#$%^&*()_+=\\|\"':;?/><, ";

    public void processKeyEvent(KeyEvent ev) {

        char c = ev.getKeyChar();

        if((Character.isLetter(c) && !ev.isAltDown()) || badchars.indexOf(c) > -1) {
            ev.consume();            
        }    
        else 
        	super.processKeyEvent(ev);
    }
}