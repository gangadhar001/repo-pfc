package bussiness;

import javax.swing.JFrame;

import presentation.JDLanguages;
import bussiness.control.ClientController;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientController.getInstance().startApplication(args);
		JDLanguages lang = new JDLanguages(new JFrame());
//		SplashScreen sps = new SplashScreen();
//		sps.setLocationRelativeTo(null);
//	    sps.setVisible(true);
//		 for (int i = 0; i <= 100; i++)
//		    {
//			 try {
//				Thread.sleep(100);
//				sps.setProgress(1); 
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		      
//		    }
//		 sps.close();
	}

}
