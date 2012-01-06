package presentation.dataVisualization.graph;

import javax.swing.JPopupMenu;

import model.business.knowledge.UserRole;

//REFERENCE: http://www.grotto-networking.com/JUNG/
/**
 * Custom class used to show menu when a click in a vertex of the graph is detected
 */
public class VertexMenu<E> extends JPopupMenu {        
    /**
	 * 
	 */
	private static final long serialVersionUID = 5734793777086353132L;

	public VertexMenu(String role) {
        super();
        if (role.equals(UserRole.ChiefProject.name()))
        	this.add(new ChangeStatusVertexMenu<E>());
        this.addSeparator();
        this.add(new DeleteVertexMenuItem<E>());
        this.addSeparator();
        this.add(new AttachFileVertexMenuItem<E>());
    }    
       
    
}
