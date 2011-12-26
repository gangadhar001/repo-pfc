// REFERENCE: http://code.google.com/p/gisgraph/source/browse/trunk/src/gisgraph/components/visualization/DeleteVertexMenuItem.java?r=32

package presentation.dataVisualization.graph;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * A class to implement the deletion of a vertex from within a 
 * PopupVertexEdgeMenuMousePlugin.
 */
public class DeleteVertexMenuItem<V> extends JMenuItem implements VertexMenuListener<V> {
    private V vertex;
    private VisualizationViewer visComp;
    
    /** Creates a new instance of DeleteVertexMenuItem */
    public DeleteVertexMenuItem() {
        super("Delete Vertex");
        this.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                visComp.getPickedVertexState().pick(vertex, false);
                visComp.getGraphLayout().getGraph().removeVertex(vertex);
                visComp.repaint();
            }
        });
    }

    public void setVertexAndView(V v, VisualizationViewer visComp) {
        this.vertex = v;
        this.visComp = visComp;
        this.setText("Delete Vertex " + v.toString());
    }
    
}
