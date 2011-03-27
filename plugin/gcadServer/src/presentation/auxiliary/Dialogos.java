package presentation.auxiliary;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class is used for show dialog windows
 */
public class Dialogos {
	
	public static void showErrorDialog(JFrame frame, String title, String message) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static boolean showQuestionDialog(JFrame frame, String title, String message) {
		return (JOptionPane.showConfirmDialog(frame, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
	}
	
}
