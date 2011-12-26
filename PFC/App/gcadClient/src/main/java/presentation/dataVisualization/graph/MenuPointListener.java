package presentation.dataVisualization.graph;

import java.awt.geom.Point2D;

// REFERENCE: http://www.grotto-networking.com/JUNG/	
/**
 * Set the point at which the mouse was clicked. Used to show Popup menus in that point
 */
public interface MenuPointListener {
    /**
     * Sets the point of the mouse click.
     */
 void setPoint(Point2D point);
    
}
