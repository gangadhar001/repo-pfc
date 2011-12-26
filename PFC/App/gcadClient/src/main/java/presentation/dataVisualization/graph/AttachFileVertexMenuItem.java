package presentation.dataVisualization.graph;


import internationalization.ApplicationInternationalization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import model.business.knowledge.Knowledge;

import presentation.dataVisualization.KnowledgeGraph;


import edu.uci.ics.jung.visualization.VisualizationViewer;

// REFERENCE: http://www.grotto-networking.com/JUNG/
/**
 * A class to implement the attach of a file in a vertex
 */
public class AttachFileVertexMenuItem<V> extends JMenuItem implements VertexMenuListener<V> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6467171889466404586L;
	
	// Generic vertex
	private V vertex;
    @SuppressWarnings("rawtypes")
	private VisualizationViewer visComp;
    
    // Creates a new instance of DeleteVertexMenuItem
    public AttachFileVertexMenuItem() {
        super(ApplicationInternationalization.getString("attachFile"));
        this.addActionListener(new ActionListener(){
            @SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
                visComp.getPickedVertexState().pick(vertex, false);
                KnowledgeGraph.setSelectedVertex((Knowledge) vertex);
                KnowledgeGraph.attachFile();
                visComp.repaint();
            }
        });
    }

	// Implements the VertexMenuListener interface.
    @SuppressWarnings("rawtypes")
	public void setVertexAndView(V v, VisualizationViewer visComp) {
        this.vertex = v;
        this.visComp = visComp;
        this.setText(ApplicationInternationalization.getString("attachFile"));
    }
    
}
