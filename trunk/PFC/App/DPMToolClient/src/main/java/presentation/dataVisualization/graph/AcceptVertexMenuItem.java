package presentation.dataVisualization.graph;


import internationalization.ApplicationInternationalization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import model.business.knowledge.Knowledge;
import model.business.knowledge.KnowledgeStatus;

import presentation.dataVisualization.KnowledgeGraph;


import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * A class to implement the menu item used to accept the knowledge
 */
public class AcceptVertexMenuItem<V> extends JMenuItem implements VertexMenuListener<V> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6467171889466404586L;
	
    
    // Creates a new instance of AcceptVertexMenuItem
    public AcceptVertexMenuItem(final ChangeStatusVertexMenu<V> parent) {
        super(ApplicationInternationalization.getString("acceptVertex"));
        this.addActionListener(new ActionListener(){
            @SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
            	parent.getVisComp().getPickedVertexState().pick(parent.getVertex(), false);
                KnowledgeGraph.setSelectedVertex((Knowledge) parent.getVertex());
                KnowledgeGraph.setStatus(KnowledgeStatus.Accepted);
                parent.getVisComp().repaint();
            }
        });
    }

	// Implements the VertexMenuListener interface.
    @SuppressWarnings("rawtypes")
	public void setVertexAndView(V v, VisualizationViewer visComp) {
        this.setText(ApplicationInternationalization.getString("acceptVertex"));
    }
    
}
