package model.business.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import presentation.auxiliary.IWindowState;

import communication.IConnectionLog;

/**
 * Class that receives the messages generated by the server and displays them in a status window.
 */
public class WindowsLogManager implements IConnectionLog {

	private ArrayList<IWindowState> windows;
	private SimpleDateFormat dateFormat;
	
	public WindowsLogManager() {
		windows = new ArrayList<IWindowState>();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	public void putWindow(IWindowState ventana) {
		windows.add(ventana);
	}
	
	public void putMessage(String messageType, String message) {
		String date;
		
		date = dateFormat.format(new Date());
		for(IWindowState window : windows) {
			window.putMessage(date + ": " + message);
		}
	}
	
	public void putMessage(String user, String messageType, String message) {
		String date;
		
		date = dateFormat.format(new Date());
		for(IWindowState window : windows) {
			window.putMessage(date + " (" + user + "): " + message);
		}
	}

	public void updateConnectedClients(int clients) {
		for(IWindowState window : windows) {
			window.updateConnectedClients(clients);
		}
	}

}
