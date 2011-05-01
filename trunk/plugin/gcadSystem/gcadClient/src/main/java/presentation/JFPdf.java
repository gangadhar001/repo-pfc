package presentation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;

import javax.swing.WindowConstants;

import model.business.knowledge.Knowledge;
import model.business.knowledge.PDFDocument;
import model.business.knowledge.Section;
import model.business.knowledge.Text;
import model.business.knowledge.Title;
import model.business.knowledge.PDFElement;
import model.business.knowledge.TopicWrapper;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import bussiness.control.PDFComposer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import presentation.utils.ImagesUtilities;
import presentation.utils.ImageKnowledgeTreeCellRenderer;
import presentation.utils.ImagePDFTreeCellRenderer;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


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
public class JFPdf extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4882749427202036715L;
	private JPanel panelData;
	private JPanel mainContainer;
	private JPanel panelContent;
	private JLabel lblTitle;
	private JLabel lblHeader;
	private JTextField txtPathHeader;
	private JButton btnSection;
	private JLabel lblContent;
	private JTextArea txtContent;
	private JToolBar toolPDF;
	private JButton btnCancel;
	private JButton btnSave;
	private JTree treePDF;
	private JScrollPane scTree;
	private JPanel panelTree;
	private JCheckBox chkFoot;
	private JButton btnBrowseFoot;
	private JButton btnBrowseHeader;
	private JTextField txtPathFoot;
	private JCheckBox chkHeader;
	private JButton btnText;
	private JButton btnTable;
	private JButton btnTitle;
	private JLabel lblFoot;
	private JTextField txtSubject;
	private JTextField txtTitle;
	private JLabel lblSubject;
	private JPanel panelButtons;

	private DefaultMutableTreeNode selectedNode;
	private String headerImagePath;
	private String footImagePath;
	private DefaultTreeModel treeModel;
	
	
	/**
	* Auto-generated main method to display this JFrame
	*/
		
	public JFPdf() {
		super();
		initGUI();
		
		initTree();
	}
	
	private void initTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new PDFDocument());
		treeModel = new DefaultTreeModel(root);
		treePDF = new JTree(treeModel);
		treePDF.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treePDF.setCellRenderer(new ImagePDFTreeCellRenderer());
		treePDF.addTreeSelectionListener(new TreeSelectionListener() {				
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// Set the content of the previous element
				setContent();
				// Get selected element in the tree
				selectedNode = ((DefaultMutableTreeNode)e.getPath().getLastPathComponent());
				// Enable text area if selected node is Title o Text
				if (selectedNode.getUserObject() instanceof Text || selectedNode.getUserObject() instanceof Title) {
					txtContent.setEditable(true);
					txtContent.setText(((PDFElement)selectedNode.getUserObject()).getContent());
					txtContent.setCaretPosition(txtContent.getText().length());
				}
				else {
					txtContent.setText("");
					txtContent.setCaretPosition(0);
					txtContent.setEditable(false);
				}
			}
		});
		
		scTree.setViewportView(treePDF);
		
		panelTree.validate();
		panelTree.repaint();
		
	}

	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.05, 0.5, 0.03};
			thisLayout.rowHeights = new int[] {7, 1, 7};
			thisLayout.columnWeights = new double[] {0.1};
			thisLayout.columnWidths = new int[] {7};
			getContentPane().setLayout(thisLayout);
			{
				panelData = new JPanel();
				GridBagLayout panelDataLayout = new GridBagLayout();
				panelDataLayout.columnWidths = new int[] {7, 7};
				panelDataLayout.rowHeights = new int[] {7, 7};
				panelDataLayout.columnWeights = new double[] {0.1, 0.6};
				panelDataLayout.rowWeights = new double[] {0.05, 0.05};
				getContentPane().add(panelData, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));
				panelData.setLayout(panelDataLayout);
				panelData.setBorder(BorderFactory.createTitledBorder("Meta-Data"));
				{
					lblTitle = new JLabel();
					panelData.add(lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					lblTitle.setName("lblTitle");
				}
				{
					lblSubject = new JLabel();
					panelData.add(lblSubject, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					lblSubject.setName("lblSubject");
				}
				{
					txtTitle = new JTextField();
					panelData.add(txtTitle, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					txtSubject = new JTextField();
					panelData.add(txtSubject, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					txtSubject.setSize(206, 23);
					txtSubject.setName("txtSubject");
					txtSubject.setPreferredSize(new java.awt.Dimension(206, 23));
				}
			}
			{
				mainContainer = new JPanel();
				GridBagLayout mainContainerLayout = new GridBagLayout();
				mainContainerLayout.columnWidths = new int[] {7};
				mainContainerLayout.rowHeights = new int[] {7, 7};
				mainContainerLayout.columnWeights = new double[] {0.1};
				mainContainerLayout.rowWeights = new double[] {0.01, 0.1};
				getContentPane().add(mainContainer, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));
				mainContainer.setLayout(mainContainerLayout);
				mainContainer.setBorder(BorderFactory.createTitledBorder("Content"));
				{
					panelContent = new JPanel();
					GridBagLayout panelContentLayout = new GridBagLayout();
					panelContentLayout.columnWidths = new int[] {7, 7, 7};
					panelContentLayout.rowHeights = new int[] {7, 7};
					panelContentLayout.columnWeights = new double[] {0.08, 0.35, 0.03};
					panelContentLayout.rowWeights = new double[] {0.05, 0.05};
					mainContainer.add(panelContent, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					panelContent.setLayout(panelContentLayout);
					{
						lblHeader = new JLabel();
						panelContent.add(lblHeader, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
						lblHeader.setName("lblHeader");
					}
					{
						lblFoot = new JLabel();
						panelContent.add(lblFoot, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
						lblFoot.setName("lblFoot");
					}
					{
						chkHeader = new JCheckBox();
						panelContent.add(chkHeader, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 90, 0, 0), 0, 0));
					}
					{
						txtPathHeader = new JTextField();
						panelContent.add(txtPathHeader, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						txtPathHeader.setName("txtPathHeader");
					}
					{
						txtPathFoot = new JTextField();
						panelContent.add(txtPathFoot, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						txtPathFoot.setName("txtPathFoot");
					}
					{
						btnBrowseHeader = new JButton();
						panelContent.add(btnBrowseHeader, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
						btnBrowseHeader.setName("btnBrowseHeader");
					}
					{
						btnBrowseFoot = new JButton();
						panelContent.add(btnBrowseFoot, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
						btnBrowseFoot.setName("btnBrowseFoot");
					}
					{
						chkFoot = new JCheckBox();
						panelContent.add(chkFoot, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 90, 0, 0), 0, 0));
					}
				}
				{
					panelTree = new JPanel();
					GridBagLayout panelTreeLayout = new GridBagLayout();
					panelTreeLayout.columnWidths = new int[] {7};
					panelTreeLayout.rowHeights = new int[] {7, 7, 7};
					panelTreeLayout.columnWeights = new double[] {0.1};
					panelTreeLayout.rowWeights = new double[] {0.07, 0.3, 0.2};
					mainContainer.add(panelTree, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
					panelTree.setLayout(panelTreeLayout);
					{
						scTree = new JScrollPane();
						panelTree.add(scTree, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					}
					{
						toolPDF = new JToolBar();
						panelTree.add(toolPDF, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						{
							btnSection = createButtonToolbar("Section");
							toolPDF.add(btnSection);
							btnSection.setPreferredSize(new java.awt.Dimension(28, 19));
						}
						{
							btnTitle = createButtonToolbar("Title");
							toolPDF.add(btnTitle);
							btnTitle.setName("btnTitle");
						}
						{
							btnText = createButtonToolbar("Text");
							toolPDF.add(btnText);
						}
						// TODO:
//						{
//							btnTable = createButtonToolbar("Table");
//							toolPDF.add(btnTable);
//						}
					}
					{
						txtContent = new JTextArea();
						panelTree.add(txtContent, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(27, 0, 0, 0), 0, 0));
						txtContent.setName("txtContent");
						txtContent.addFocusListener(new FocusAdapter() {
							public void focusLost(FocusEvent evt) {
								txtContentFocusLost(evt);
							}
						});
					}
					{
						lblContent = new JLabel();
						panelTree.add(lblContent, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(6, 0, 0, 0), 0, 0));
						lblContent.setName("lblContent");
					}
				}
			}
			{
				panelButtons = new JPanel();
				GridBagLayout panelButtonsLayout = new GridBagLayout();
				panelButtonsLayout.columnWidths = new int[] {7, 7};
				panelButtonsLayout.rowHeights = new int[] {7};
				panelButtonsLayout.columnWeights = new double[] {0.91, 0.03};
				panelButtonsLayout.rowWeights = new double[] {0.1};
				getContentPane().add(panelButtons, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
				panelButtons.setLayout(panelButtonsLayout);
				{
					btnSave = new JButton();
					panelButtons.add(btnSave, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					btnSave.setName("btnSave");
					btnSave.setSize(50, 23);
					btnSave.setAction(getAppActionMap().get("save"));
					btnSave.setPreferredSize(new java.awt.Dimension(50, 23));
				}
				{
					btnCancel = new JButton();
					panelButtons.add(btnCancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
					btnCancel.setName("btnCancel");
					btnCancel.setPreferredSize(new java.awt.Dimension(50, 23));
				}
			}
			pack();
			this.setSize(399, 586);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

	private JButton createButtonToolbar(String text) throws MalformedURLException, IOException {
    	JButton button = new JButton();
    	button.setBorder(BorderFactory.createEmptyBorder(5,10,4,10));
    	button.setFocusPainted(false);
    	button.setHorizontalTextPosition(SwingConstants.CENTER);
    	button.setVerticalTextPosition(SwingConstants.BOTTOM);
    	button.setRequestFocusEnabled(false);
    	button.setAction(getAppActionMap().get("add_"+text));
    	button.setText(text);
    	button.setIcon(ImagesUtilities.loadIcon(text+".png"));
    	return button;	    
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	@Action
	public void save() {
		try {
			Document doc = new Document();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo PDF","pdf","PDF");
			JFileChooser fileChooser = new JFileChooser();       
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showSaveDialog(this);
			if ( result == JFileChooser.APPROVE_OPTION ) {
				File path = fileChooser.getSelectedFile().getAbsoluteFile();
	            PdfWriter.getInstance(doc, new FileOutputStream(path + ".pdf"));
				doc.open();
				PDFComposer.composePDF(doc, (DefaultMutableTreeNode) treePDF.getModel().getRoot());
				doc.close();
	        }
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*** Actions used to add new elements to the tree ***/
	@Action
	public void add_Section () {
		// A section can only be inserted as a child of another section or the document
		if (selectedNode != null && (selectedNode.getUserObject() instanceof Section || selectedNode.getUserObject() instanceof PDFDocument)) {
			Section s = new Section();
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(s);
			treeModel.insertNodeInto(child, selectedNode, selectedNode.getChildCount());
			treePDF.scrollPathToVisible(new TreePath(child.getPath()));
		}
	}
	
	@Action
	public void add_Table () {		
	}
	
	@Action
	public void add_Title () {
		// Title can only be inserted as a child of a section, and it must be the first element
		if (selectedNode != null && selectedNode.getUserObject() instanceof Section && selectedNode.isLeaf()) {
			Title t = new Title();
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(t);
			treeModel.insertNodeInto(child, selectedNode, selectedNode.getChildCount());
			treePDF.scrollPathToVisible(new TreePath(child.getPath()));
		}
	}
	
	@Action
	public void add_Text () {
		// Text can only be inserted as a child of a section
		if (selectedNode != null && selectedNode.getUserObject() instanceof Section) {
			Text t = new Text();
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(t);
			treeModel.insertNodeInto(child, selectedNode, selectedNode.getChildCount());
			treePDF.scrollPathToVisible(new TreePath(child.getPath()));
		}		
	}
	
	private void txtContentFocusLost(FocusEvent evt) {
		// When text area lost the focus, set the content to the selected node
		setContent();
	}
	 private void setContent() {
		 if (selectedNode != null && (selectedNode.getUserObject() instanceof Text || selectedNode.getUserObject() instanceof Title)) 
			 ((PDFElement)selectedNode.getUserObject()).setContent(txtContent.getText());
	 }

}