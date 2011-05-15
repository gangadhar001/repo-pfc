package bussiness;

import bussiness.control.ClientController;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientController.getInstance().startApplication(args);
	}

}
