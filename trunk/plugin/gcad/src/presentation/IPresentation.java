package presentation;

import java.util.Vector;

import model.business.knowledge.Operations;

public interface IPresentation {

	public void updateState(boolean connected);
	
	public void updateOperations(Vector<Operations> availableOperations);
	
	public void updateProposals();
	
}
