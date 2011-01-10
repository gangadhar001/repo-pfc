package presentation;

import java.util.List;

public interface IPresentation {

	public void updateState(boolean connected);
	
	public void updateActions(List<String> actionsName);
	
	public void updateProposals();
	
}
