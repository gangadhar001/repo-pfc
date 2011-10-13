package presentation.dataVisualization.auxiliary;

import javax.swing.JPopupMenu;

//REFERENCE: http://www.grotto-networking.com/JUNG/
/**
 * Custom class used to show menu when a click in a vertex of the graph is detected
 */
public class VertexMenu<E> extends JPopupMenu {        
    /**
	 * 
	 */
	private static final long serialVersionUID = 5734793777086353132L;

	public VertexMenu() {
        super();
        this.add(new DeleteVertexMenuItem<E>());
        this.addSeparator();
        this.add(new AttachFileVertexMenuItem<E>());
    }    
       
    
}
