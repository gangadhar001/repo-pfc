//REFERENCE: code.google.com/p/wholebrain/source/browse/mcb/trunk/src/main/java/org/wholebrainproject/mcb/mousemenu/PopupVertexEdgeMenuMousePlugin.java?r=2693

package presentation.dataVisualization.graph;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JPopupMenu;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;

/**
 * A GraphMousePlugin that brings up distinct popup menus when an edge or vertex is
 * appropriately clicked in a graph.
 */
public class PopupVertexEdgeMenuMousePlugin<V, E> extends AbstractPopupGraphMousePlugin {
    private JPopupMenu vertexPopup;
    
    public PopupVertexEdgeMenuMousePlugin() {
        this(MouseEvent.BUTTON3_MASK);
    }
    
    /**
     * Creates a new instance of PopupVertexEdgeMenuMousePlugin
     */
    public PopupVertexEdgeMenuMousePlugin(int modifiers) {
        super(modifiers);
    }
    
    @SuppressWarnings("unchecked")
	protected void handlePopup(MouseEvent e) {
        final VisualizationViewer<V,E> vv = (VisualizationViewer<V,E>)e.getSource();
        Point2D p = e.getPoint();
        
        GraphElementAccessor<V,E> pickSupport = vv.getPickSupport();
        if(pickSupport != null) {
            final V v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            if(v != null) {
                updateVertexMenu(v, vv);
                vertexPopup.show(vv, e.getX(), e.getY());
            }
        }
    }
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateVertexMenu(V v, VisualizationViewer vv) {
        if (vertexPopup == null) return;
        Component[] menuComps = vertexPopup.getComponents();
        for (Component comp: menuComps) {
            if (comp instanceof VertexMenuListener) {
                ((VertexMenuListener)comp).setVertexAndView(v, vv);
            }
        }
        
    }
    
    /**
     * Getter for the vertex popup.
     */
    public JPopupMenu getVertexPopup() {
        return vertexPopup;
    }
    
    /**
     * Setter for the vertex popup.
     */
    public void setVertexPopup(JPopupMenu vertexPopup) {
        this.vertexPopup = vertexPopup;
    }
    
}
