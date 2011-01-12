package presentation;

import java.util.List;

import model.business.knowledge.AbstractKnowledge;

public interface IPresentation {

	public void updateState(boolean connected);
	
	public void updateActions(List<String> actionsName);
	
	public void updateProposals(AbstractKnowledge newKnowledge);
	
}
