package model.business.control;

import java.util.ArrayList;
import java.util.List;
import presentation.IPresentation;

public class PresentationController {

	private static ArrayList<IPresentation> observers = new ArrayList<IPresentation>();
	private static List<String> actions = new ArrayList<String>();
	
	public static void attachObserver(IPresentation observer) {
		observers.add(observer);
		//TODO: cada vez que se añade una vista al observador, se le actualizan los permisos 
		// Esto se hace para que si una vista se abre despues de haber hecho login, tenga las acciones
		// oportunas configuradas
		observer.updateActions(actions);
	}
	
	public static void detachObserver(IPresentation observer) {
		observers.remove(observer);
	}
	
	public static void detachObservers() {
		observers.clear();
	}
	
	public static void notifyConnection(boolean connected) {
		for (IPresentation observer: observers)
			observer.updateState(connected);
	}
		
	public static void notifyKnowledge() {
		for (IPresentation observer: observers)
			observer.updateKnowledge();
	}
	
	public static void notifyActionsAllowed(List<String> actionsName) {
		actions = actionsName;
		for (IPresentation observer: observers)
			observer.updateActions(actionsName);
	}

}

