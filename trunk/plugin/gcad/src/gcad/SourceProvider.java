package gcad;

import java.util.HashMap;
import java.util.Map;

import model.business.knowledge.IActions;

import org.eclipse.ui.ISources;

public class SourceProvider extends org.eclipse.ui.AbstractSourceProvider {
	
	public final static String ENABLED = "ENABLED";
	public final static String DISABLED = "DISABLED";

	@Override
	public void dispose() {
	}

	@Override
	public String[] getProvidedSourceNames() {
		return IActions.actions;
	}

	public void setMenuItemVisible(boolean enabled, String nameItem) {
		String value = enabled ? ENABLED : DISABLED;
		fireSourceChanged(ISources.WORKBENCH, nameItem, value);
	}
	
	@Override
	public Map<String, String> getCurrentState() {
		String[] actions = IActions.actions;
		Map<String, String> map = new HashMap<String, String>(actions.length);
		for (String act: actions) {
			map.put(act, DISABLED);
		}
		return map;
	}
	
	


}
