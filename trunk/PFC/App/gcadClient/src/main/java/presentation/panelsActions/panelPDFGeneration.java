package presentation.panelsActions;

import internationalization.ApplicationInternationalization;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.application.Application;

import presentation.JFMain;
import presentation.customComponents.ImagePanel;
import presentation.customComponents.PDFGen.panelPDFDragged;
import presentation.customComponents.PDFGen.panelPDFDraggedTable;
import presentation.customComponents.PDFGen.panelPDFDraggedText;
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
	
	protected final static int PANEL_INSETS = 50;
	
	private JFMain parent;
	
	// Keep a list of the user-added panels in each section 
    private Hashtable<Integer, ArrayList<panelPDFDragged>> panelsDragged = new Hashtable<Integer, ArrayList<panelPDFDragged>>();
    
    /*
     * This represents the data that is transmitted in drag and drop.
     * In our case with only 1 type of dropped item, it will be a panel object
     * Note DataFlavor can represent more than classes -- easily text, images, etc
     */
    private static DataFlavor dragAndDropPanelDataFlavor = null;
	
	public panelPDFGeneration(JFMain parent) {
		super();
		try {
			super.setImage(ImagesUtilities.loadCompatibleImage("background.jpg"));
		} catch (IOException e) { }
		this.parent = parent;
		initGUI();
	}	

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(1008, 601));
			this.setSize(1008, 601);
			GridBagLayout thisLayout = new GridBagLayout();
			this.setLayout(thisLayout);
			this.setMinimumSize(new java.awt.Dimension(1008, 601));
			thisLayout.rowWeights = new double[] {0.5};
			thisLayout.rowHeights = new int[] {7};
			thisLayout.columnWeights = new double[] {0.0, 0.9};
			thisLayout.columnWidths = new int[] {7, 7};
			{
				panelElements = new JPanel();
				this.add(panelElements, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 60, 20, 80), 0, 0));
				panelElements.setLayout(null);
				panelElements.setSize(133, 522);
				panelElements.setPreferredSize(new java.awt.Dimension(133, 522));
				panelElements.setMinimumSize(new java.awt.Dimension(133, 522));
				panelElements.setMaximumSize(new java.awt.Dimension(133, 522));
				{
					panelPDFElementTitle = new panelPDFElement("Title", this);
					panelElements.add(panelPDFElementTitle);
					panelPDFElementTitle.setBounds(20, 38, 96, 102);
				}
				{
					panelPDFElementText = new panelPDFElement("Text", this);
					panelElements.add(panelPDFElementText);
					panelPDFElementText.setBounds(20, 197, 96, 102);
				}
				{
					panelPDFElementTable = new panelPDFElement("Table", this);
					panelElements.add(panelPDFElementTable);
					panelPDFElementTable.setBounds(20, 356, 96, 102);
				}			
			}
			{
				tabbedPane = new JTabbedPane();
				this.add(tabbedPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(20, 0, 20, 60), 0, 0));
				tabbedPane.setName("tabbedPane");
				tabbedPane.setPreferredSize(new java.awt.Dimension(618, 543));
				tabbedPane.setSize(618, 543);
				tabbedPane.setMinimumSize(new java.awt.Dimension(618, 543));
				tabbedPane.addTab(ApplicationInternationalization.getString("Section_tab") +  " 1", null, getSectionPanel_1(), null);
				// Register a change listener
				tabbedPane.addChangeListener(new ChangeListener() {					
					@Override
					public void stateChanged(ChangeEvent evt) {
				        JTabbedPane pane = (JTabbedPane)evt.getSource();
				        // Add drop event
				        JPanel sectionPanel = (JPanel) pane.getComponentAt(pane.getSelectedIndex());
				        sectionPanel.setTransferHandler(new DragAndDropTransferHandler());		        
				        // Create the listener to do the work when dropping on this object
				        sectionPanel.setDropTarget(new DropTarget(sectionPanel, new PanelDropTargetListener(sectionPanel, panelPDFGeneration.this)));
				    }
				});				
				
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
	 
	// Method to add a new PDF element to the current section panel
	public void addPanelToSection(panelPDFElement panelPDFElement) {
		panelPDFDragged element = null;
		if (panelPDFElement.getTitle().equals("Title")) {
			element = new panelPDFDraggedTitle();
		}
		else if (panelPDFElement.getTitle().equals("Text")) {
			element = new panelPDFDraggedText();
		}
		else if (panelPDFElement.getTitle().equals("Table")) {
			element = new panelPDFDraggedTable();
		}
		
		if(element != null) {
			int index = tabbedPane.getSelectedIndex();
			// Add to list so will appear after next relayout
			if (panelsDragged.containsKey(index)) {
				panelsDragged.get(index).add(element);
			}
			else {
				ArrayList<panelPDFDragged> list = new ArrayList<panelPDFDragged>();
				list.add(element);
				panelsDragged.put(index, list);
			}
			
			// Relayout the current section panel.
			relayout();
		}
	}
	
	// Return the panles of the current section
	public List<panelPDFDragged> getSectionDraggedPanels() {
		return panelsDragged.get(tabbedPane.getSelectedIndex());
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

		// Clear out all previously added items in current section
		JPanel sectionPanel = (JPanel)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		sectionPanel.removeAll();

		// Put a lot of room around panels so can drop easily!
		gbc.insets = new Insets(PANEL_INSETS, PANEL_INSETS, PANEL_INSETS, PANEL_INSETS);

		// Add the panels, if any
		List<panelPDFDragged> panels = getSectionDraggedPanels();
		for (panelPDFDragged p : panels) {
			gbc.gridy = row++;
			sectionPanel.add(p, gbc);
		}

		// Add a vertical strut to push content to top.
		gbc.weighty = 1.0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = row++;
		Component strut = Box.createVerticalStrut(1);
		sectionPanel.add(strut, gbc);

		this.validate();
		this.repaint();
	}

	// Returns (creating, if necessary) the DataFlavor representing the PDF Element Dragged
	public static DataFlavor getDragAndDropPanelDataFlavor() throws Exception {
		// Lazy load/create the flavor
		if (dragAndDropPanelDataFlavor == null) {
			dragAndDropPanelDataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + 
					";class=presentation.customComponents.PDFGen.panelPDFDragged");
		}
		
		return dragAndDropPanelDataFlavor;
	}
	 
	 private JPanel getSectionPanel_1() {
		 if(sectionPanel_1 == null) {
			 sectionPanel_1 = new JPanel();
			 sectionPanel_1.setName("sectionPanel_1");
			 sectionPanel_1.setPreferredSize(new java.awt.Dimension(613, 462));
		 }
		 return sectionPanel_1;
	 }
	 
	 public void addSection() {
		 JPanel sectionPanel = new JPanel();
		 sectionPanel.setPreferredSize(new java.awt.Dimension(613, 462));
		 sectionPanel.setBackground(new Color(255, 255, 255));
		 int count = tabbedPane.getTabCount();
		 tabbedPane.addTab(ApplicationInternationalization.getString("Section_tab") + " " + (count + 1), null, sectionPanel, null);
		 parent.enableToolbarButton("DeleteSection", true);
		 tabbedPane.setSelectedIndex(count);
	 }
	 
	 public void deleteSection() {
		 if (tabbedPane.getTabCount() > 1) {
			 tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			 if(tabbedPane.getTabCount() == 1)
				 parent.enableToolbarButton("DeleteSection", false);
		 }
	 }
	 
	 public void compile() {
		 
	 }

}
