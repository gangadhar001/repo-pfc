package model.business.control;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.Knowledge;
import communication.IClient;

/**
 * TODO: esta clase es el observador del servidor. Se registran los clientes (plugin) This class represents an observer pattern
 */
public class ClientsController {

	private static ArrayList<IClient> observers = new ArrayList<IClient>();
	private static List<String> actions = new ArrayList<String>();
	
	public static void disconnectClients() throws RemoteException {
		try {
			// Cerramos todos los clientes que hay conectados al servidor
			for (IClient observer: observers)
				// Notificamos al cliente que el servidor ha sido desconectado
				observer.servidorInaccesible();
			
		} finally {
			// Reseteamos la tabla de sesiones y clientes (lo hacemos aquí,
			// y no iteración a iteración en el bucle anterior, para evitar
			// el problema de tablas mutantes)
			
			observers = new ArrayList<IClient>();
		}
	}
//	public static void attachObserver(ICliente observer) {
//		observers.add(observer);
//		// Each time you add a view to the observer, update the permissions. 
//		// This is done so that if a view is opened after making login, the view has the allowed actions well configured
//		observer.updateActions(actions);
//	}
//	
//	public static void detachObserver(ICliente observer) {
//		observers.remove(observer);
//	}
//	
//	public static void detachObservers() {
//		observers.clear();
//	}
//	
//	public static void notifyConnection(boolean connected) {
//		for (ICliente observer: observers)
//			observer.updateState(connected);
//	}
//		
//	public static void notifyKnowledgeAdded(Knowledge k) {
//		for (ICliente observer: observers)
//			observer.updateKnowledgeAdded(k);
//	}
//	
//	public static void notifyKnowledgeEdited(Knowledge k) {
//		for (ICliente observer: observers)
//			observer.updateKnowledgeEdited(k);
//	}
//	
//	public static void notifyKnowledgeRemoved(Knowledge k) {
//		for (ICliente observer: observers)
//			observer.updateKnowledgeRemoved(k);
//	}
//	
//	public static void notifyActionsAllowed(List<String> actionsName) {
//		actions = actionsName;
//		for (ICliente observer: observers)
//			observer.updateActions(actionsName);
//	}

}

