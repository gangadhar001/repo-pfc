package gcad;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.ISources;

public class AbstractSourceProvider extends org.eclipse.ui.AbstractSourceProvider {

	public final static String CONDITION_NAME = "visibleWhenPermission";
	public final static String ENABLED = "ENABLED";
	public final static String DISABLED = "DISABLED";
	private boolean enabled = true;


	@Override
	public void dispose() {
	}

	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { CONDITION_NAME };
	}
	
	// Can't return NULL
	@Override
	public Map getCurrentState() {
		Map map = new HashMap(1);
		String value = enabled ? ENABLED : DISABLED;
		map.put(CONDITION_NAME, value);
		return map;
	}

	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		String value = enabled ? ENABLED : DISABLED;
		fireSourceChanged(ISources.WORKBENCH, CONDITION_NAME, value);
	}


}
