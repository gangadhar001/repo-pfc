package resources;

import java.awt.Component;
import java.awt.Cursor;

public class CursorUtilities {

	public static void showWaitCursor(Component c) {
		c.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
	
	public static void showDefaultCursor(Component c) {
		c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}
