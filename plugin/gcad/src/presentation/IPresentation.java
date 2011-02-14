package presentation;

import java.util.List;

import model.business.knowledge.Knowledge;

public interface IPresentation {

	public void updateState(boolean connected);
	
	public void updateActions(List<String> actionsName);
	
	public void updateKnowledgeAdded(Knowledge k);
	
	public void updateKnowledgeEdited(Knowledge k);
	
	public void updateKnowledgeRemoved(Knowledge k);
		
}
