package gcad;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.ISources;

public class SourceProvider extends org.eclipse.ui.AbstractSourceProvider {

	public final static String CONDITION_NEW_PROJECT = "newProjectVisibleWhenPermission";
	public final static String CONDITION_NEW_PROPOSAL = "newProposalVisibleWhenPermission";
	public final static String CONDITION_MODIFY_PROPOSAL = "modifyProposalVisibleWhenPermission";
	public final static String CONDITION_DELETE_PROPOSAL = "deleteProposalVisibleWhenPermission";
	public final static String CONDITION_NEW_ANSWER = "newAnswerVisibleWhenPermission";
	public final static String CONDITION_MODIFY_ANSWER = "modifyAnswerVisibleWhenPermission";
	public final static String CONDITION_DELETE_ANSWER = "deleteAnswerVisibleWhenPermission";
	
	public final static String ENABLED = "ENABLED";
	public final static String DISABLED = "DISABLED";

	@Override
	public void dispose() {
	}

	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { CONDITION_NEW_PROJECT, CONDITION_NEW_PROPOSAL, CONDITION_MODIFY_PROPOSAL, CONDITION_DELETE_PROPOSAL, CONDITION_NEW_ANSWER, CONDITION_MODIFY_ANSWER, CONDITION_DELETE_ANSWER};
	}

	
	public void setNewProjectMenuItemVisible(boolean enabled) {
		String value = enabled ? ENABLED : DISABLED;
		fireSourceChanged(ISources.WORKBENCH, CONDITION_NEW_PROJECT, value);
	}
	
	public void setNewProposalMenuItemVisible(boolean enabled) {
		String value = enabled ? ENABLED : DISABLED;
		fireSourceChanged(ISources.WORKBENCH, CONDITION_NEW_PROPOSAL, value);
	}
	
	public void setModifyProposalMenuItemVisible(boolean enabled) {
		String value = enabled ? ENABLED : DISABLED;
		fireSourceChanged(ISources.WORKBENCH, CONDITION_MODIFY_PROPOSAL, value);
	}
	
	public void setDeleteProposalMenuItemVisible(boolean enabled) {
		String value = enabled ? ENABLED : DISABLED;
		fireSourceChanged(ISources.WORKBENCH, CONDITION_DELETE_PROPOSAL, value);
	}
	
	public void setNewAnswerMenuItemVisible(boolean enabled) {
		String value = enabled ? ENABLED : DISABLED;
		fireSourceChanged(ISources.WORKBENCH, CONDITION_NEW_ANSWER, value);
	}
	
	public void setModifyAnswerMenuItemVisible(boolean enabled) {
		String value = enabled ? ENABLED : DISABLED;
		fireSourceChanged(ISources.WORKBENCH, CONDITION_MODIFY_ANSWER, value);
	}
	
	public void setDeleteAnswerMenuItemVisible(boolean enabled) {
		String value = enabled ? ENABLED : DISABLED;
		fireSourceChanged(ISources.WORKBENCH, CONDITION_DELETE_ANSWER, value);
	}

	@Override
	public Map getCurrentState() {
		Map map = new HashMap(7);
		map.put(CONDITION_NEW_PROJECT, ENABLED);
		map.put(CONDITION_NEW_PROPOSAL, ENABLED);
		map.put(CONDITION_MODIFY_PROPOSAL, ENABLED);
		map.put(CONDITION_DELETE_PROPOSAL, ENABLED);
		map.put(CONDITION_NEW_ANSWER, ENABLED);
		map.put(CONDITION_MODIFY_ANSWER, ENABLED);
		map.put(CONDITION_DELETE_ANSWER, ENABLED);
		return map;
	}


}
