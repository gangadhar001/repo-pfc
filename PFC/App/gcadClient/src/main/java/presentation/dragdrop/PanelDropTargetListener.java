package presentation.dragdrop;


import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import presentation.customComponents.PDFGen.panelPDFDragged;
import presentation.panelsActions.panelPDFGeneration;

/**
 * Class that represents listeners for drop.
 */
public class PanelDropTargetListener implements DropTargetListener {

    
    private static final Cursor droppableCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
            					notDroppableCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    
	private JPanel panelWithDrop;
	private panelPDFGeneration parent;

    public PanelDropTargetListener(JPanel sheet, panelPDFGeneration parent) {
        this.panelWithDrop = sheet;
        this.parent = parent;
    }

    // Could easily find uses for these, like cursor changes, etc.
    public void dragEnter(DropTargetDragEvent dtde) {}
    public void dragOver(DropTargetDragEvent dtde) {
        if (!this.panelWithDrop.getCursor().equals(droppableCursor)) {
            this.panelWithDrop.setCursor(droppableCursor);
        }
    }
    
    public void dropActionChanged(DropTargetDragEvent dtde) {}
    public void dragExit(DropTargetEvent dte) {
        this.panelWithDrop.setCursor(notDroppableCursor);
    }

    /**
     * Drop Event
     */
    public void drop(DropTargetDropEvent dtde) {
        // Done with cursors, dropping
        this.panelWithDrop.setCursor(Cursor.getDefaultCursor());
        
        DataFlavor dragAndDropPanelFlavor = null;
        
        Object transferableObj = null;
        Transferable transferable = null;
        
        try {
            // Grab expected flavor
            dragAndDropPanelFlavor = panelPDFGeneration.getDragAndDropPanelDataFlavor();            
            transferable = dtde.getTransferable();
//            DropTargetContext c = dtde.getDropTargetContext();
            
            // What does the Transferable support
            if (transferable.isDataFlavorSupported(dragAndDropPanelFlavor)) {
                transferableObj = dtde.getTransferable().getTransferData(dragAndDropPanelFlavor);
            } 
            
        } catch (Exception ex) { }
        
        if (transferableObj != null) {
        
	        // Cast it to the panel that has been dragged
	        panelPDFDragged droppedPanel = (panelPDFDragged)transferableObj;
	        
	        // Get the y offset from the top of the WorkFlowSheetPanel
	        // for the drop option (the cursor on the drop)
	        final int dropYLoc = dtde.getLocation().y;
	
	        // We need to map the Y axis values of drop as well as other
	        // panels so can sort by location.
	        Map<Integer, panelPDFDragged> yLocMapForPanels = new HashMap<Integer, panelPDFDragged>();
	        yLocMapForPanels.put(dropYLoc, droppedPanel);
	
	        // Iterate through the existing demo panels. Going to find their locations.
	        for (panelPDFDragged nextPanel : parent.getDraggedPanels()) {	
	            // Grab the y value
	            int y = nextPanel.getY();
	
	            // If is the same dropped panel, skip
	            if (!nextPanel.equals(droppedPanel)) {
	                yLocMapForPanels.put(y, nextPanel);
	            }
	        }
	
	        // Grab the Y values and sort them
	        List<Integer> sortableYValues = new ArrayList<Integer>();
	        sortableYValues.addAll(yLocMapForPanels.keySet());
	        Collections.sort(sortableYValues);
	
	        // Put the panels in list in order of appearance
	        List<panelPDFDragged> orderedPanels = new ArrayList<panelPDFDragged>();
	        for (Integer i : sortableYValues) {
	            orderedPanels.add(yLocMapForPanels.get(i));
	        }
	        
	        // Grab the in-memory list and re-add panels in order.
	        List<panelPDFDragged> inMemoryPanelList =  parent.getDraggedPanels();
	        inMemoryPanelList.clear();
	        inMemoryPanelList.addAll(orderedPanels);
	    
	        // Request relayout contents, or else won't update GUI following drop.
	        // Will add back in the order to which we just sorted
	        parent.relayout();
        }
    }
} 