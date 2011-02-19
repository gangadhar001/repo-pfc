package model.graph;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * This class represents a custom GraphViewer. It is necessary to access to the underlying 
 * graph of the GraphViewer
 */
public class MyGraphViewer extends GraphViewer {

	public MyGraphViewer(Composite composite, int style) {
		super(composite, style);
	}
	
	public Graph getGraph() {
		return graph;
	}

}
