package presentation.dataVisualization.graph;

import javax.swing.JPopupMenu;

public class VertexMenu<V> extends JPopupMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 778231113613276814L;

	public VertexMenu() {
		super("Vertex Menu");
		this.add(new DeleteVertexMenuItem<V>());
		this.addSeparator();
	}
}
