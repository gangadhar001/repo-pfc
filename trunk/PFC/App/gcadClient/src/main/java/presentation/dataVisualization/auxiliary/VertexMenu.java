package presentation.dataVisualization.auxiliary;

import javax.swing.JPopupMenu;

//REFERENCE: http://www.grotto-networking.com/JUNG/
/**
 * Custom class used to show menu when a click in a vertex of the graph is detected
 * @param <E>
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
//        this.add(new pscCheckBox());
//        this.add(new tdmCheckBox());
    }    
    
    /*** Las acciones del menu ***/
//    public static class pscCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<Person> {
//    	Person v;
//        
//        public pscCheckBox() {
//            super("PSC Capable");
//            this.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    ;
//                }
//                
//            });
//        }
//        public void setVertexAndView(Person v, VisualizationViewer visComp) {
//            this.v = v;
//            this.setSelected(true);
//        }
//        
//    }
    
       
    
}
