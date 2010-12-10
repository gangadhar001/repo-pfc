package model.business.control;

import java.util.ArrayList;
import java.util.Vector;

import model.business.knowledge.Operations;

import presentation.IPresentation;

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
	
	public static void notifyPermission(Vector<Operations> availableOperations) {
		for (IPresentation observer: observers)
			observer.updateOperations(availableOperations);
	}
	
	public static void notifyProposals() {
		for (IPresentation observer: observers)
			observer.updateProposals();
	}
}

