package presentation.dataVisualization.graph;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JPopupMenu;

//REFERENCE: http://www.grotto-networking.com/JUNG/
/**
 * Custom JUNG GraphMousePlugin that brings up the popup menus when a vertex is
 * clicked in a graph. 
 */
public class PopupVertexEdgeMenuMousePlugin<V, E> extends AbstractPopupGraphMousePlugin {
    private JPopupMenu vertexPopup;
    
    public PopupVertexEdgeMenuMousePlugin() {
        this(MouseEvent.BUTTON3_MASK);
    }
    
    public PopupVertexEdgeMenuMousePlugin(int modifiers) {
        super(modifiers);
    }    
     
    // Implementation of the AbstractPopupGraphMousePlugin method     
    @SuppressWarnings("unchecked")
	protected void handlePopup(MouseEvent e) {
        final VisualizationViewer<V,E> vv = (VisualizationViewer<V,E>)e.getSource();
        Point2D p = e.getPoint();
        
        GraphElementAccessor<V,E> pickSupport = vv.getPickSupport();
        if(pickSupport != null) {
            final V v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            if(v != null) {
                updateVertexMenu(v, vv, p);
                vertexPopup.show(vv, e.getX(), e.getY());
            }            
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateVertexMenu(V v, VisualizationViewer vv, Point2D point) {
        if (vertexPopup == null) return;
        Component[] menuComps = vertexPopup.getComponents();
        for (Component comp: menuComps) {
            if (comp instanceof VertexMenuListener) {
                ((VertexMenuListener)comp).setVertexAndView(v, vv);
            }
            if (comp instanceof MenuPointListener) {
                ((MenuPointListener)comp).setPoint(point);
            }
        }
        
    }
    
    public JPopupMenu getVertexPopup() {
        return vertexPopup;
    }
    
    public void setVertexPopup(JPopupMenu vertexPopup) {
        this.vertexPopup = vertexPopup;
    }    
    
}
