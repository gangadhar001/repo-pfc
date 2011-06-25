package gcadServer;

import model.business.control.ServerController;

/**
 * Main class from server
 */
public class Main {
	
	public static void main(String[] args) {
		ServerController cont;		
		cont = new ServerController();
		cont.showServerWindowUI();
	}
	
}
