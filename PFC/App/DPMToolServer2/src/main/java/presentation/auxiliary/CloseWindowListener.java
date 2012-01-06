package presentation.auxiliary;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Interface for the "CloseWindow" event
 */
public interface CloseWindowListener extends EventListener {

	public void closeWindow(EventObject evt);
	
}
