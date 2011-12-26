// REFERENCE: http://code.google.com/p/gisgraph/source/browse/trunk/src/gisgraph/components/visualization/VertexMenuListener.java?spec=svn32&r=32
package presentation.dataVisualization.graph;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public interface VertexMenuListener<V> {
    @SuppressWarnings("rawtypes")
	void setVertexAndView(V v, VisualizationViewer visView);    
}
