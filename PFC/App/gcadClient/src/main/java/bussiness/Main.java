package bussiness;

import org.jdesktop.application.Application;

import presentation.JFMain;
import presentation.SplashScreen;
import resources.CursorUtilities;
import bussiness.control.ClientController;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SplashScreen sps = new SplashScreen();
		sps.setLocationRelativeTo(null);
		sps.setVisible(true);
		CursorUtilities.showWaitCursor(sps);
		ClientController.getInstance().startApplication(args);
		for (int i = 1; i <= 100; i++) {
			try {
				Thread.sleep(50);
				sps.setProgress(1);
			} catch (InterruptedException e) { }

		}
		sps.close();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		CursorUtilities.showDefaultCursor(sps);
		Application.getInstance(JFMain.class).show();
	}

}
