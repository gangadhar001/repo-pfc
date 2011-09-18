package presentation;
import internationalization.ApplicationInternationalization;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import model.business.knowledge.PDFDocument;
import model.business.knowledge.PDFElement;
import model.business.knowledge.Project;
import model.business.knowledge.Section;
import model.business.knowledge.Table;
import model.business.knowledge.Text;
import model.business.knowledge.Title;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.customComponents.HeaderFooter;
import presentation.utils.ImagePDFTreeCellRenderer;
import bussiness.control.ClientController;
import bussiness.control.PDFComposer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import exceptions.NonPermissionRoleException;
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
public class JDPdf extends JDialog {
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
	private JScrollPane jScrollPane1;
	private String headerImagePath = null;
	private String footImagePath = null;
	private DefaultTreeModel treeModel;
	private List<Project> projects;
	private JComboBox cbProjects;
	private JButton btnDelete;
	private JFileChooser fc;
	
	
	/**
	* Auto-generated main method to display this Dialog
	*/
		
	public JDPdf(JFrame frame) {
		super(frame);
		setTitle(ApplicationInternationalization.getString("PDFDialog_Title"));
		initGUI();	
		// Get projects from user logged
		fillComboProjects();
		initTree();
	}
	
	private void fillComboProjects() {
		try {
			projects = ClientController.getInstance().getProjectsFromCurrentUser();
			for(Project p : projects)
				cbProjects.addItem(p);
			cbProjects.setSelectedIndex(-1);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}		
	}

	// Create tree with the PDF document as root 
	private void initTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new PDFDocument());
		treeModel = new DefaultTreeModel(root);
		treePDF = new JTree(treeModel);
		treePDF.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treePDF.setCellRenderer(new ImagePDFTreeCellRenderer());
		treePDF.addTreeSelectionListener(new TreeSelectionListener() {				
			@Override
			// When change the selected item, stores the value of that node
			public void valueChanged(TreeSelectionEvent e) {
				// Set the content of the previous selected element
				setContent();
				// Get selected element in the tree
				selectedNode = ((DefaultMutableTreeNode)e.getPath().getLastPathComponent());
				// Enable text area if selected node is Title o Text
				if (selectedNode.getUserObject() instanceof Text || selectedNode.getUserObject() instanceof Title) {
					txtContent.setVisible(true);
					cbProjects.setVisible(false);
					txtContent.setEditable(true);
					txtContent.setText(((PDFElement)selectedNode.getUserObject()).getContent());
					txtContent.setCaretPosition(txtContent.getText().length());
				}
				 // Enable combobox of projects if selected node is Table
				else if (selectedNode.getUserObject() instanceof Table) {
					txtContent.setVisible(false);
					cbProjects.setVisible(true);
					cbProjects.setSize(new Dimension(100,35));
					String content = ((PDFElement)selectedNode.getUserObject()).getContent();
					if (content == null)
						cbProjects.setSelectedItem(-1);
					else
						cbProjects.setSelectedItem(Integer.parseInt(content));
				}
				
				else {
					txtContent.setVisible(true);
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
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle(ApplicationInternationalization.getString("TitleJFPdf"));
			getContentPane().setLayout(null);
			{
				panelData = new JPanel();
				GridBagLayout panelDataLayout = new GridBagLayout();
				panelDataLayout.columnWidths = new int[] {7, 7};
				panelDataLayout.rowHeights = new int[] {7, 7};
				panelDataLayout.columnWeights = new double[] {0.1, 0.6};
				panelDataLayout.rowWeights = new double[] {0.05, 0.05};
				getContentPane().add(panelData, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));
				panelData.setLayout(panelDataLayout);
				panelData.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("groupMetaData")));
				panelData.setBounds(5, 5, 433, 95);
				{
					lblTitle = new JLabel();
					panelData.add(lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					lblTitle.setName("lblTitle");
					lblTitle.setText(ApplicationInternationalization.getString("lblTitlePdf"));
				}
				{
					lblSubject = new JLabel();
					panelData.add(lblSubject, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					lblSubject.setName("lblSubject");
					lblSubject.setText(ApplicationInternationalization.getString("lblSubjectPdf"));
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
				mainContainer.setBounds(5, 105, 433, 445);
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
						lblHeader.setText(ApplicationInternationalization.getString("lblHeaderPdf"));
					}
					{
						lblFoot = new JLabel();
						panelContent.add(lblFoot, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
						lblFoot.setName("lblFoot");
						lblFoot.setText(ApplicationInternationalization.getString("lblFootPdf"));
					}
					{
						chkHeader = new JCheckBox();
						panelContent.add(chkHeader, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 122, 0, 0), 0, 0));
						chkHeader.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								chkHeaderActionPerformed(evt);
							}
						});
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
						btnBrowseHeader.setText(ApplicationInternationalization.getString("btnBrowse"));
						btnBrowseHeader.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnBrowseHeaderActionPerformed(evt);
							}
						});
					}
					{
						btnBrowseFoot = new JButton();
						panelContent.add(btnBrowseFoot, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
						btnBrowseFoot.setName("btnBrowseFoot");
						btnBrowseFoot.setText(ApplicationInternationalization.getString("btnBrowse"));
						btnBrowseFoot.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnBrowseFootActionPerformed(evt);
							}
						});
					}
					{
						chkFoot = new JCheckBox();
						panelContent.add(chkFoot, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 122, 0, 0), 0, 0));
						chkFoot.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								chkFootActionPerformed(evt);
							}
						});
					}
				}
				{
					panelTree = new JPanel();
					mainContainer.add(panelTree, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
					panelTree.setLayout(null);
					{
						scTree = new JScrollPane();
						panelTree.add(scTree, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						scTree.setBounds(0, 48, 413, 164);
					}
					{
						toolPDF = new JToolBar();
						panelTree.add(toolPDF, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						toolPDF.setBounds(0, 9, 413, 29);
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
						{
							btnTable = createButtonToolbar("Table");
							toolPDF.add(btnTable);
						}
						{
							toolPDF.addSeparator();
							btnDelete = createButtonToolbar("Delete");
							toolPDF.add(btnDelete);
						}
					}
					{
						jScrollPane1 = new JScrollPane();
						panelTree.add(jScrollPane1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(27, 0, 0, 0), 0, 0));
						jScrollPane1.setBounds(0, 218, 413, 116);
						{
							txtContent = new JTextArea();
							jScrollPane1.setViewportView(txtContent);
							txtContent.setName("txtContent");
							txtContent.setBounds(0, 205, 408, 105);
							txtContent.addFocusListener(new FocusAdapter() {
								public void focusLost(FocusEvent evt) {
									txtContentFocusLost(evt);
								}
							});
						}
					}
					{
						cbProjects = new JComboBox();
						cbProjects.setVisible(false);
						panelTree.add(cbProjects, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(27, 0, 0, 0), 0, 0));
						cbProjects.setName("cbProjects");
						cbProjects.setBounds(0, 218, 413, 30);
						cbProjects.addFocusListener(new FocusAdapter() {
							public void focusLost(FocusEvent evt) {
								cbProjectsFocusLost(evt);
							}
						});
					}
					{
						lblContent = new JLabel();
						panelTree.add(lblContent, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(6, 0, 0, 0), 0, 0));
						lblContent.setName("lblContent");
						lblContent.setBounds(0, 0, 0, 0);
					}
				}
			}
			{
				panelButtons = new JPanel();
				getContentPane().add(panelButtons, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
				panelButtons.setLayout(null);
				panelButtons.setBounds(5, 510, 428, 37);
			}
			{
				btnSave = new JButton();
				getContentPane().add(btnSave);
				btnSave.setName("btnSave");
				btnSave.setAction(getAppActionMap().get("save"));
				btnSave.setText(ApplicationInternationalization.getString("btnSave"));
				btnSave.setBounds(252, 559, 86, 25);
			}
			{
				btnCancel = new JButton();
				getContentPane().add(btnCancel);
				btnCancel.setName("btnCancel");
				btnCancel.setAction(getAppActionMap().get("Cancel"));
				btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
				btnCancel.setBounds(349, 559, 85, 25);
				
			}
			pack();
			this.setSize(461, 628);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	private JButton createButtonToolbar(String text) throws MalformedURLException, IOException {
    	JButton button = new JButton();
    	button.setBorder(BorderFactory.createEmptyBorder(5,10,4,10));
    	button.setFocusPainted(false);
    	button.setHorizontalTextPosition(SwingConstants.CENTER);
    	button.setVerticalTextPosition(SwingConstants.BOTTOM);
    	button.setRequestFocusEnabled(false);
    	button.setAction(getAppActionMap().get(text));
    	button.setText(ApplicationInternationalization.getString(text));
//    	button.setIcon(ImagesUtilities.loadIcon(text+".png"));
    	return button;	    
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	@Action
	public void save() {
		File path = null;
		try {
			FileNameExtensionFilter filter = new FileNameExtensionFilter(ApplicationInternationalization.getString("PDFFile"), "pdf", "PDF");
			JFileChooser fileChooser = new JFileChooser();       
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.APPROVE_OPTION ) {
				// Margin of documents
				float marginTop = 20;
				float marginBottom = 20;
				Image headerImage = getImage(headerImagePath);
				if (headerImage != null)
					marginTop = headerImage.getHeight() + 20;
				Image footImage = getImage(footImagePath);
				if (footImage != null)
					marginBottom = footImage.getHeight() + 20;
				
				Document doc = new Document(PageSize.A4, 20, 20, marginTop, marginBottom);
				path = fileChooser.getSelectedFile().getAbsoluteFile();
	            PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(path + ".pdf"));
	            // Event used to add header image and foot image
	            HeaderFooter event = new HeaderFooter(headerImage, footImage);
				pdfWriter.setPageEvent(event);				
	            
				doc.open();            
				PDFComposer.composePDF(doc, (DefaultMutableTreeNode) treePDF.getModel().getRoot(), projects);
				doc.close();
				// TODO: dialogo informacion
				this.dispose();
	        }
		} catch(IOException e) {
			if (path != null)
				path.delete();
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);			
		} catch (DocumentException e) {
			if (path != null)
				path.delete();
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException e) {
			if (path != null)
				path.delete();
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			if (path != null)
				path.delete();
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			if (path != null)
				path.delete();
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			if (path != null)
				path.delete();
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			if (path != null)
				path.delete();
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private Image getImage(String path) throws MalformedURLException, IOException, DocumentException {
		Image result = null;
		if (path != null)
			result = Image.getInstance(path);
		return result;
		
	}

	/*** Actions availables in toolbar ***/
	@Action
	public void Section () {
		// A section can only be inserted as a child of another section or the document
		if (selectedNode != null && (selectedNode.getUserObject() instanceof Section || selectedNode.getUserObject() instanceof PDFDocument)) {
			Section s = new Section();
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(s);
			treeModel.insertNodeInto(child, selectedNode, selectedNode.getChildCount());
			treePDF.scrollPathToVisible(new TreePath(child.getPath()));
		}
		else {
			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("SectionParentIncorrect"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@Action
	public void Table () {
		// A table can only be inserted as a child of a section
		if (selectedNode != null && selectedNode.getUserObject() instanceof Section) {
			Table t = new Table();
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(t);
			treeModel.insertNodeInto(child, selectedNode, selectedNode.getChildCount());
			treePDF.scrollPathToVisible(new TreePath(child.getPath()));
		}
		else {
			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("TableParentIncorrect"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@Action
	public void Title () {
		// Title can only be inserted as a child of a section, and it must be the first element
		if (selectedNode != null && selectedNode.getUserObject() instanceof Section && selectedNode.isLeaf()) {
			Title t = new Title();
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(t);
			treeModel.insertNodeInto(child, selectedNode, selectedNode.getChildCount());
			treePDF.scrollPathToVisible(new TreePath(child.getPath()));
		}
		else {
			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("TitleParentIncorrect"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@Action
	public void Text () {
		// Text can only be inserted as a child of a section
		if (selectedNode != null && selectedNode.getUserObject() instanceof Section) {
			Text t = new Text();
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(t);
			treeModel.insertNodeInto(child, selectedNode, selectedNode.getChildCount());
			treePDF.scrollPathToVisible(new TreePath(child.getPath()));
		}		
		else {
			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("TextParentIncorrect"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@Action
	public void Delete() {
		if (selectedNode != null) {
			if (selectedNode.getUserObject() instanceof PDFDocument)
				JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("PDFFrame_RootNode"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
			else { 
				// Removes the node and its data
				treeModel.removeNodeFromParent(selectedNode);
				cbProjects.setSelectedIndex(-1);
				txtContent.setVisible(true);
				txtContent.setText("");
			}
		}
		else {
			JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("PDFFrame_NotSelectedNode"), ApplicationInternationalization.getString("Warning"), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@Action
	public void Cancel () {
		this.dispose();
	}
	
	private void txtContentFocusLost(FocusEvent evt) {
		// When text area lost the focus, set the content to the selected node
		setContent();
	}
	
	private void cbProjectsFocusLost(FocusEvent evt) {
		// When combobox lost the focus, set the content to the selected node
		setContent();
	}
	 
	private void setContent() {
		 if (selectedNode != null && (selectedNode.getUserObject() instanceof Text || selectedNode.getUserObject() instanceof Title)) 
			 ((PDFElement)selectedNode.getUserObject()).setContent(txtContent.getText());
		 else if (selectedNode != null && selectedNode.getUserObject() instanceof Table)
			 // Save the index of the selected project
			 ((PDFElement)selectedNode.getUserObject()).setContent(String.valueOf(cbProjects.getSelectedIndex()));
			 
	}
	
	private void chkHeaderActionPerformed(ActionEvent evt) {
		txtPathHeader.setEnabled(true);
		btnBrowseHeader.setEnabled(true);
	}
	
	private void chkFootActionPerformed(ActionEvent evt) {
		txtPathFoot.setEnabled(true);
		btnBrowseFoot.setEnabled(true);
	}
	
	private void btnBrowseHeaderActionPerformed(ActionEvent evt) {
		headerImagePath = getPath(); 		
	}
	
	private String getPath() {
		String path = null;
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "Image Files (*.png, *.jpg, *.jpeg, *.bmp, *.tiff, *.tif, *.gif)";
			}
			
			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				return (f.getName().toLowerCase().endsWith("png") || f.getName().toLowerCase().endsWith("jpg") || f.getName().toLowerCase().endsWith("jpeg") 
						|| f.getName().toLowerCase().endsWith("bmp") || f.getName().toLowerCase().endsWith("tiff") || f.getName().toLowerCase().endsWith("gif") 
						|| f.getName().toLowerCase().endsWith("tif"));
			}
		});
		int result = fc.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION)
			path = fc.getSelectedFile().getAbsolutePath();
		return path;
	}

	private void btnBrowseFootActionPerformed(ActionEvent evt) {
		footImagePath = getPath();
		
	}

}
