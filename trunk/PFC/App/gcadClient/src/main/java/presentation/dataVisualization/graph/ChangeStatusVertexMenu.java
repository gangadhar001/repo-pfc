package presentation.dataVisualization.graph;

import internationalization.ApplicationInternationalization;

import javax.swing.JMenu;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public class ChangeStatusVertexMenu<V>  extends JMenu implements VertexMenuListener<V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2437009711818323772L;
	private V vertex;
	@SuppressWarnings("rawtypes")
	private VisualizationViewer visComp;
	
	 // Creates a new instance of ChangeStatus Menu
    public ChangeStatusVertexMenu() {
        this.add(new AcceptVertexMenuItem<V>(this));
        this.add(new RejectVertexMenuItem<V>(this));
    }

	@SuppressWarnings("rawtypes")
	@Override
	public void setVertexAndView(V v, VisualizationViewer visComp) {
		this.vertex = v;
		this.visComp = visComp;
        this.setText(ApplicationInternationalization.getString("statusVertex"));
	}

	public V getVertex() {
		return vertex;
	}

	@SuppressWarnings("rawtypes")
	public VisualizationViewer getVisComp() {
		return visComp;
	} 
	

}
