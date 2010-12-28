package gcad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.ISources;

public class SourceProvider extends org.eclipse.ui.AbstractSourceProvider {
	
	public final static String ENABLED = "ENABLED";
	public final static String DISABLED = "DISABLED";

	@Override
	public void dispose() {
	}

	@Override
	public String[] getProvidedSourceNames() {
		return new IActions().actions;
	}

	public void setMenuItemVisible(boolean enabled, String nameItem) {
		String value = enabled ? ENABLED : DISABLED;
		fireSourceChanged(ISources.WORKBENCH, nameItem, value);
	}
	
	@Override
	public Map getCurrentState() {
		String[] actions = IActions.actions;
		Map map = new HashMap(actions.length);
		// First of all, only is enabled the action "login"
		for (String act: actions) {
			map.put(act, DISABLED);
		}
		return map;
	}
	
	


}
