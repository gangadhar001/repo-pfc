package presentation.panelsActions;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.business.knowledge.Notification;

import org.jdesktop.application.Application;

import presentation.customComponents.ImagePanel;
import presentation.customComponents.PDFGen.panelPDFDragged;
import presentation.customComponents.PDFGen.panelPDFDraggedTitle;
import presentation.customComponents.PDFGen.panelPDFElement;
import presentation.dragdrop.DragAndDropTransferHandler;
import presentation.dragdrop.PanelDropTargetListener;
import resources.ImagesUtilities;


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
public class panelPDFGeneration extends ImagePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3144486182937579912L;
	
	private JPanel panelElements;
	private JPanel sectionPanel_1;
	private panelPDFElement panelPDFElementTable;
	private panelPDFElement panelPDFElementText;
	private panelPDFElement panelPDFElementTitle;
	private JTabbedPane tabbedPane;
	
	protected final static int PANEL_INSETS = 10;
	
	/**
     * Keep a list of the user-added panels so can re-add
     */
    private List<panelPDFDragged> panelsDragged = new ArrayList<panelPDFDragged>();
    
    /**
     * <p>This represents the data that is transmitted in drag and drop.</p>
     * <p>In our limited case with only 1 type of dropped item, it will be a panel object!</p>
     * <p>Note DataFlavor can represent more than classes -- easily text, images, etc.</p>
     */
    private static DataFlavor dragAndDropPanelDataFlavor = null;
	
	public panelPDFGeneration() {
		super();
		try {
			super.setImage(ImagesUtilities.loadCompatibleImage("background.jpg"));
		} catch (IOException e) { }
		initGUI();
	}	

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(1008, 601));
			this.setSize(1008, 601);
			this.setLayout(null);
			this.setMinimumSize(new java.awt.Dimension(1008, 601));
			{
				panelElements = new JPanel();
				panelElements.setBounds(75, 56, 177, 490);
				this.add(panelElements);
				panelElements.setLayout(null);
				{
					panelPDFElementTitle = new panelPDFElement("Title", this);
					panelElements.add(panelPDFElementTitle);
					panelPDFElementTitle.setBounds(30, 20, 133, 135);
				}
//					{
//						panelPDFElement2 = new panelPDFElement();
//						panelElements.add(panelPDFElement2);
//						panelPDFElement2.setBounds(30, 176, 133, 135);
//					}
//					{
//						panelPDFElement3 = new panelPDFElement();
//						panelElements.add(panelPDFElement3);
//						panelPDFElement3.setBounds(30, 334, 133, 135);
//					}			
			}
			{
				tabbedPane = new JTabbedPane();
				this.add(tabbedPane);
				tabbedPane.setBounds(324, 56, 609, 490);
				tabbedPane.setName("tabbedPane");
				tabbedPane.addTab("Section 1", null, getSectionPanel_1(), null);
				
				// Set the Drop Target to the section panel
				sectionPanel_1.setTransferHandler(new DragAndDropTransferHandler());		        
		        // Create the listener to do the work when dropping on this object
				sectionPanel_1.setDropTarget(new DropTarget(sectionPanel_1, new PanelDropTargetListener(sectionPanel_1, this)));
			}
			
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	 
	// Method to add a new PDF element to the current section
	public void addPanelToSection(panelPDFElement panelPDFElement) {
		panelPDFDragged element = null;
		if (panelPDFElement.getTitle().equals("Title")) {
			element = new panelPDFDraggedTitle();
		}

		// Add to list so will appear after next relayout
		panelsDragged.add(element);
		// Relayout the section panel.
		relayout();
	}
	
	public List<panelPDFDragged> getDraggedPanels() {
		return panelsDragged;
	}
	 
	 /*
     * Removes all components from section panel and re-adds them.
     * This is important for two things:
     *   Adding a new panel (user clicks on button)
     *   Re-ordering panels (user drags and drops a panel to acceptable drop target region)
     */
	public void relayout() {

		// Create the constraints, and go ahead and set those
		// that don't change for components
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;

		int row = 0;

		// TODO: coger la seccion actual
		// Clear out all previously added items
		sectionPanel_1.removeAll();

		// Put a lot of room around panels so can drop easily!
		gbc.insets = new Insets(PANEL_INSETS, PANEL_INSETS, PANEL_INSETS, PANEL_INSETS);

		// Add the panels, if any
		for (panelPDFDragged p : panelsDragged) {
			gbc.gridy = row++;
			sectionPanel_1.add(p, gbc);
		}

		// Add a vertical strut to push content to top.
		gbc.weighty = 1.0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = row++;
		Component strut = Box.createVerticalStrut(1);
		sectionPanel_1.add(strut, gbc);

		this.validate();
		this.repaint();
	}

	// Returns (creating, if necessary) the DataFlavor representing the PDF Element Dragged
	public static DataFlavor getDragAndDropPanelDataFlavor() throws Exception {
		// Lazy load/create the flavor
		if (dragAndDropPanelDataFlavor == null) {
			dragAndDropPanelDataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=panelPDFDragged");
		}
		
		return dragAndDropPanelDataFlavor;
	}
	 
	 private JPanel getSectionPanel_1() {
		 if(sectionPanel_1 == null) {
			 sectionPanel_1 = new JPanel();
		 }
		 return sectionPanel_1;
	 }

}
