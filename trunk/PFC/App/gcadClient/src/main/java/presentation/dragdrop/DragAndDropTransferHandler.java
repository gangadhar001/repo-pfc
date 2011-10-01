package presentation.dragdrop;


import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceMotionListener;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import presentation.customComponents.PDFGen.panelPDFDragged;


public class DragAndDropTransferHandler  extends TransferHandler implements DragSourceMotionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6168984608987558668L;

	public DragAndDropTransferHandler() {
        super();
    }

    // This creates the Transferable object.
    @Override()
    public Transferable createTransferable(JComponent c) {        
        // TaskInstancePanel implements Transferable
        if (c instanceof panelPDFDragged) {
            Transferable tip = (panelPDFDragged) c;
            return tip;
        }
        // Not found
        return null;
    }

    public void dragMouseMoved(DragSourceDragEvent dsde) {}

    // This is queried to see whether the component can be copied, moved, both or neither
    @Override()
    public int getSourceActions(JComponent c) {  
        if (c instanceof panelPDFDragged) {
            return TransferHandler.COPY;
        }
        
        return TransferHandler.NONE;
    }
}

