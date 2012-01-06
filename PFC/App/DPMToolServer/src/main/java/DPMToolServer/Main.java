package DPMToolServer;

import model.business.control.ServerController;

/**
 * Main class from server
 */
public class Main {
	
	private static ServerController cont;
	
	public static void main(String[] args) {			
		cont = new ServerController();
		cont.showServerWindowUI();
	}
	
	public static void restart() {
		cont.closeServerWindowUI();
		cont = new ServerController();
		cont.showServerWindowUI();
	}
	
}
