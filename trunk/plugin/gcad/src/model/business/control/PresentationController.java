package model.business.control;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.PlatformUI;

import presentation.IPresentation;
import presentation.utils.InputDialogs;

public class PresentationController {

	private static ArrayList<IPresentation> observers = new ArrayList<IPresentation>();
	
	public static void attachObserver(IPresentation observer) {
		observers.add(observer);
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
		for (IPresentation observer: observers)
			observer.updateActions(actionsName);
	}

}
