package presentation.dataVisualization.auxiliary;
// REFERENCE: http://www.grotto-networking.com/JUNG/
	
import presentation.panelsActions.panelKnowledgeView;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * An interface for show menu items on the currently selected vertex
 */	
public interface VertexMenuListener<V> {
    /**
     * Used to set the vertex and visulization component.
     * @param parent 
     */
     @SuppressWarnings("rawtypes")
	void setVertexAndView(V v, VisualizationViewer visView); 
    
}


