package presentation;

import java.util.Vector;

import model.business.knowledge.Operations;

public interface IPresentation {

	public void updateState(boolean connected);
	
	public void updateActions(Vector<Operations> availableOperations);
	
	public void updateProposals();
	
}
