package presentation.panelsActions;

import internationalization.ApplicationInternationalization;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.business.knowledge.PDFConfiguration;
import model.business.knowledge.PDFElement;
import model.business.knowledge.PDFSection;
import model.business.knowledge.PDFTable;
import model.business.knowledge.PDFText;
import model.business.knowledge.PDFTitle;

import org.jdesktop.application.Application;

import presentation.JDPdf;
import presentation.JFMain;
import presentation.customComponents.ImagePanel;
import presentation.customComponents.RoundedPanel;
import presentation.customComponents.PDFGen.panelPDFDragged;
import presentation.customComponents.PDFGen.panelPDFDraggedTable;
import presentation.customComponents.PDFGen.panelPDFDraggedText;
import presentation.customComponents.PDFGen.panelPDFDraggedTitle;
import presentation.customComponents.PDFGen.panelPDFElement;
import presentation.dragdrop.DragAndDropTransferHandler;
import presentation.dragdrop.PanelDropTargetListener;
import resources.CursorUtilities;
import resources.ImagesUtilities;
import exceptions.NotLoggedException;


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
	private JPanel panelHeader;
	private JScrollPane scrollSection1;
	private JPanel sectionPanel_1;
	private panelPDFElement panelPDFElementTable;
	private JLabel lblHeader;
	private panelPDFElement panelPDFElementText;
	private panelPDFElement panelPDFElementTitle;
	private JTabbedPane tabbedPane;
	
	protected final static int PANEL_INSETS = 20;
	
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
			super.setImage(ImagesUtilities.loadCompatibleImage("background.png"));
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
				panelElements = new RoundedPanel();
				panelElements.setBackground(new Color(227, 219, 237));
				panelElements.setOpaque(false);
				this.add(panelElements, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(20, 60, 20, 80), 0, 0));
				panelElements.setLayout(null);
				panelElements.setSize(133, 522);
				panelElements.setPreferredSize(new java.awt.Dimension(133, 522));
				panelElements.setMinimumSize(new java.awt.Dimension(133, 522));
				panelElements.setMaximumSize(new java.awt.Dimension(133, 522));
				{
					panelPDFElementTitle = new panelPDFElement("Title", this);
					panelElements.add(panelPDFElementTitle);
					panelPDFElementTitle.setBounds(20, 89, 96, 102);
					panelPDFElementTitle.setOpaque(false);
				}
				{
					panelPDFElementText = new panelPDFElement("Text", this);
					panelElements.add(panelPDFElementText);
					panelPDFElementText.setBounds(20, 231, 96, 102);
					panelPDFElementText.setOpaque(false);
				}
				{
					panelPDFElementTable = new panelPDFElement("Table", this);
					panelElements.add(panelPDFElementTable);
					panelElements.add(getPanelHeader());
					panelPDFElementTable.setBounds(20, 370, 96, 102);
					panelPDFElementTable.setOpaque(false);
				}			
			}
			{
				tabbedPane = new JTabbedPane();
				this.add(tabbedPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(20, 0, 20, 60), 0, 0));
				tabbedPane.setName("tabbedPane");
				tabbedPane.setPreferredSize(new java.awt.Dimension(618, 543));
				tabbedPane.setSize(618, 543);
				tabbedPane.setMinimumSize(new java.awt.Dimension(618, 543));
				int count = tabbedPane.getTabCount();
				tabbedPane.addTab(ApplicationInternationalization.getString("Section_tab") + " " + (count + 1), null, getJScrollPane1(), null);
				// Register a tab change listener
				tabbedPane.addChangeListener(new ChangeListener() {					
					@Override
					public void stateChanged(ChangeEvent evt) {
				        JTabbedPane tabPanel = (JTabbedPane)evt.getSource();
				        // Add drop event
				        JPanel sectionPanel = (JPanel) ((JScrollPane)tabPanel.getComponentAt(tabPanel.getSelectedIndex())).getViewport().getComponent(0);
				        sectionPanel.setTransferHandler(new DragAndDropTransferHandler());		        
				        // Create the listener to do the work when dropping on this object
				        sectionPanel.setDropTarget(new DropTarget(sectionPanel, new PanelDropTargetListener(sectionPanel, panelPDFGeneration.this)));
				        // Enable or disable "add" button of the title element
				        changeTitleStatus(sectionPanel);
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
	 
	protected void changeTitleStatus(JPanel sectionPanel) {
		boolean found = false;
		for(Component c : sectionPanel.getComponents()) {
			if (c instanceof panelPDFDraggedTitle) {
				found = true;
				setEnableTitle(false);
			}
		}
		if (!found)
			setEnableTitle(true);
	}

	// Method to add a new PDF element to the current section panel
	public void addPanelToSection(panelPDFElement panelPDFElement) throws RemoteException, NotLoggedException, Exception {
		panelPDFDragged element = null;
		if (panelPDFElement.getTitle().equals("Title")) {
			element = new panelPDFDraggedTitle(this);
			panelPDFElement.enableAddButton(false);
		}
		else if (panelPDFElement.getTitle().equals("Text")) {
			element = new panelPDFDraggedText(this);
		}
		else if (panelPDFElement.getTitle().equals("Table")) {
			element = new panelPDFDraggedTable(this);
			((panelPDFDraggedTable)element).configureRole();
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
	
	// Return the panels of the current section
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
        // Clear out all previously added items in current section
        JPanel sectionPanel = (JPanel)((JScrollPane)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex())).getViewport().getComponent(0);
        sectionPanel.removeAll();

        // Add the panels, if any
        List<panelPDFDragged> panels = getSectionDraggedPanels(); 
        sectionPanel.add(Box.createVerticalStrut(20));
        for (panelPDFDragged p : panels) {
            sectionPanel.add(p);
            sectionPanel.add(Box.createVerticalStrut(20));
            p.setAlignmentX(CENTER_ALIGNMENT);
        }       

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
	 
	 public void addSection() {
		 JPanel sectionPanel = new JPanel();	
		 JScrollPane scrollSection = new JScrollPane();
		 scrollSection.setPreferredSize(new java.awt.Dimension(670, 533));
		 scrollSection.setViewportView(sectionPanel);
		 //sectionPanel.setPreferredSize(new java.awt.Dimension(613, 462));
		 sectionPanel.setBackground(new Color(255, 255, 255));
		 sectionPanel.setLayout(new BoxLayout(sectionPanel,BoxLayout.Y_AXIS));
		 int count = tabbedPane.getTabCount();
		 tabbedPane.addTab(ApplicationInternationalization.getString("Section_tab") + " " + (count + 1), null, scrollSection, null);
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
	 
	 // Create the sections of PDF Document
	 public void compile() {
		 CursorUtilities.showWaitCursor(this);
		 // Get sections and its elements
		 List<PDFSection> sections = new ArrayList<PDFSection>();
		 Enumeration<Integer> keys = panelsDragged.keys();
		 while(keys.hasMoreElements()) {
			 int sectionNumber = keys.nextElement();
			 List<PDFElement> elementsInSection = new ArrayList<PDFElement>();
			 for(panelPDFDragged pd: panelsDragged.get(sectionNumber)) {
				 PDFElement element = null;
				 if (pd instanceof panelPDFDraggedTitle) {
					 element = new PDFTitle(((panelPDFDraggedTitle)pd).getContent());
				 }
				 else if (pd instanceof panelPDFDraggedText) {
					 element = new PDFText(((panelPDFDraggedText)pd).getContent());
				 }
				 else if (pd instanceof panelPDFDraggedTable) {
					 element = new PDFTable(((panelPDFDraggedTable)pd).getProject());
				 }
				 elementsInSection.add(element);
			 }
			 sections.add(new PDFSection(elementsInSection));				 
		 }
		 
		 PDFConfiguration config = new PDFConfiguration(sections);
		 if (config.isValid()) {
			 JDPdf dialog = new JDPdf(config);
			 dialog.setLocationRelativeTo(this);
			 dialog.setModal(true);
			 dialog.setVisible(true);
		 }
		 else {
			 CursorUtilities.showDefaultCursor(this);
			 JOptionPane.showMessageDialog(this, config.getErrorMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		 }
	 }

	public void removeDragged(panelPDFDragged panelPDFDragged) {
		JPanel sectionPanel = (JPanel)((JScrollPane)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex())).getViewport().getComponent(0);
        sectionPanel.remove(panelPDFDragged);
        panelsDragged.get(tabbedPane.getSelectedIndex()).remove(panelPDFDragged);
		relayout();
	}
	
	private JScrollPane getJScrollPane1() {
		if(scrollSection1 == null) {
			scrollSection1 = new JScrollPane();
			scrollSection1.setPreferredSize(new java.awt.Dimension(670, 533));
			scrollSection1.setViewportView(getSectionPanel_1());
		}
		return scrollSection1;
	}
	
	 private JPanel getSectionPanel_1() {
		 if(sectionPanel_1 == null) {
			 sectionPanel_1 = new JPanel();			 
			 sectionPanel_1.setName("sectionPanel_1");
			 sectionPanel_1.setLayout(new BoxLayout(sectionPanel_1,BoxLayout.Y_AXIS));
			 //sectionPanel_1.setPreferredSize(new java.awt.Dimension(613, 462));
		 }
		 return sectionPanel_1;
	 }
	 
	 private JPanel getPanelHeader() {
		 if(panelHeader == null) {
			 try {
				panelHeader = new RoundedPanel(false, ImagesUtilities.loadCompatibleImage("fondoPDF.png"));
			} catch (IOException e) {
				panelHeader = new RoundedPanel();
			}
			 panelHeader.setBounds(0, 0, 128, 45);
			 panelHeader.setName("panelHeader");
			 panelHeader.setLayout(null);
			 panelHeader.add(getLblHeader());
			 panelHeader.setOpaque(false);
		 }
		 return panelHeader;
	 }
	 
	 private JLabel getLblHeader() {
		 if(lblHeader == null) {
			 lblHeader = new JLabel();
			 lblHeader.setBounds(29, 10, 87, 23);
			 Font font = new Font("Times New Roman", Font.BOLD, 16);
			 lblHeader.setForeground(Color.WHITE);
			 lblHeader.setFont(font);
			 lblHeader.setText(ApplicationInternationalization.getString("PDFElements"));
		 }
		 return lblHeader;
	 }

	public void setEnableTitle(boolean value) {
		for(Component c: panelElements.getComponents()) {
			if (c instanceof panelPDFElement){
				if (((panelPDFElement) c).getTitle().equals("Title"))
					((panelPDFElement)c).enableAddButton(value);
			}
		}
		
	}

}
