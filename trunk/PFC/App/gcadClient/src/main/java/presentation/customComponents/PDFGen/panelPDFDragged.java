package presentation.customComponents.PDFGen;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import presentation.customComponents.RoundedPanel;
import presentation.dragdrop.DragAndDropTransferHandler;
import presentation.dragdrop.DraggableMouseListener;
import presentation.panelsActions.panelPDFGeneration;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class panelPDFDragged extends RoundedPanel implements Transferable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2371562767403267428L;

	public panelPDFDragged() {
		super();
		
		// Add the listener which will export this panel for dragging
        this.addMouseListener(new DraggableMouseListener());
        
        // Add the handler, which negotiates between drop target and this 
        // draggable panel
        this.setTransferHandler(new DragAndDropTransferHandler());
	}

	/*
     * One of three methods defined by the Transferable interface.
     * Returns supported DataFlavor. Again, we're only supporting this actual Object within the JVM.
     * For more information, see the JavaDoc for DataFlavor.
     */
    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] flavors = {null};       
        
        try {
            flavors[0] = panelPDFGeneration.getDragAndDropPanelDataFlavor();
        } catch (Exception ex) {
            return null;
        }
        
        return flavors;
    }

    /*
     * One of three methods defined by the Transferable interface
     * Determines whether this object supports the DataFlavor. In this case, only one is supported: for this object itself
     */
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        DataFlavor[] flavors = {null};
        
        try {
            flavors[0] = panelPDFGeneration.getDragAndDropPanelDataFlavor();
        } catch (Exception ex) {           
            return false;
        }

        for (DataFlavor f : flavors) {
            if (f.equals(flavor)) {
                return true;
            }
        }

        return false;
    }

	 /*
     * One of three methods defined by the Transferable interface.
     * If multiple DataFlavor's are supported, can choose what Object to return.
     */
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		DataFlavor thisFlavor = null;

        try {
            thisFlavor = panelPDFGeneration.getDragAndDropPanelDataFlavor();
        } catch (Exception ex) {
            return null;
        }

        if (thisFlavor != null && flavor.equals(thisFlavor)) {
            return this;
        }

        return null;
	}
}
