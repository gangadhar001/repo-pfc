package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.customComponents.HeaderFooter;
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
	private JButton btnCancel;
	private JButton btnSave;
	private JCheckBox chkFoot;
	private JButton btnBrowseFoot;
	private JButton btnBrowseHeader;
	private JTextField txtPathFoot;
	private JCheckBox chkHeader;
	private JLabel lblFoot;
	private JTextField txtSubject;
	private JTextField txtTitle;
	private JLabel lblSubject;

	private String headerImagePath = null;
	private String footImagePath = null;
	private JFileChooser fc;
	private PDFConfiguration configuration;
	
	
	/**
	* Auto-generated main method to display this Dialog
	*/
		
	public JDPdf(PDFConfiguration config) {
		super();
		this.configuration = config;
		setTitle(ApplicationInternationalization.getString("PDFDialog_Title"));
		initGUI();			
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle(ApplicationInternationalization.getString("TitleJFPdf"));
			getContentPane().setLayout(null);
			{
				panelData = new JPanel();
				getContentPane().add(panelData, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));
				panelData.setLayout(null);
				panelData.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("groupMetaData")));
				panelData.setBounds(5, 5, 442, 103);
				{
					lblTitle = new JLabel();
					panelData.add(lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					lblTitle.setName("lblTitle");
					lblTitle.setText(ApplicationInternationalization.getString("lblTitlePdf"));
					lblTitle.setBounds(10, 29, 51, 16);
				}
				{
					lblSubject = new JLabel();
					panelData.add(lblSubject, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					lblSubject.setName("lblSubject");
					lblSubject.setText(ApplicationInternationalization.getString("lblSubjectPdf"));
					lblSubject.setBounds(10, 63, 52, 19);
				}
				{
					txtTitle = new JTextField();
					panelData.add(txtTitle, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					txtTitle.setBounds(68, 26, 359, 23);
				}
				{
					txtSubject = new JTextField();
					panelData.add(txtSubject, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					txtSubject.setSize(206, 23);
					txtSubject.setName("txtSubject");
					txtSubject.setPreferredSize(new java.awt.Dimension(206, 23));
					txtSubject.setBounds(68, 60, 359, 23);
				}
			}
			{
				mainContainer = new JPanel();
				getContentPane().add(mainContainer, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));
				mainContainer.setLayout(null);
				mainContainer.setBorder(BorderFactory.createTitledBorder("Content"));
				mainContainer.setBounds(5, 120, 442, 162);
				{
					panelContent = new JPanel();
					mainContainer.add(panelContent, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					panelContent.setLayout(null);
					panelContent.setBounds(5, 21, 432, 136);
					{
						lblHeader = new JLabel();
						panelContent.add(lblHeader, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
						lblHeader.setName("lblHeader");
						lblHeader.setText(ApplicationInternationalization.getString("lblHeaderPdf"));
						lblHeader.setBounds(5, 4, 169, 16);
					}
					{
						lblFoot = new JLabel();
						panelContent.add(lblFoot, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
						lblFoot.setName("lblFoot");
						lblFoot.setText(ApplicationInternationalization.getString("lblFootPdf"));
						lblFoot.setBounds(5, 73, 124, 16);
					}
					{
						chkHeader = new JCheckBox();
						panelContent.add(chkHeader, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 122, 0, 0), 0, 0));
						chkHeader.setBounds(141, 4, 14, 17);
						chkHeader.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								chkHeaderActionPerformed();
							}
						});
					}
					{
						txtPathHeader = new JTextField();
						panelContent.add(txtPathHeader, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						txtPathHeader.setName("txtPathHeader");
						txtPathHeader.setBounds(5, 29, 334, 23);
					}
					{
						txtPathFoot = new JTextField();
						panelContent.add(txtPathFoot, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						txtPathFoot.setName("txtPathFoot");
						txtPathFoot.setBounds(5, 101, 334, 23);
					}
					{
						btnBrowseHeader = new JButton();
						panelContent.add(btnBrowseHeader, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
						btnBrowseHeader.setName("btnBrowseHeader");
						btnBrowseHeader.setText(ApplicationInternationalization.getString("btnBrowse"));
						btnBrowseHeader.setBounds(356, 29, 65, 23);
						btnBrowseHeader.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnBrowseHeaderActionPerformed();
							}
						});
					}
					{
						btnBrowseFoot = new JButton();
						panelContent.add(btnBrowseFoot, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
						btnBrowseFoot.setName("btnBrowseFoot");
						btnBrowseFoot.setText(ApplicationInternationalization.getString("btnBrowse"));
						btnBrowseFoot.setBounds(356, 101, 67, 23);
						btnBrowseFoot.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnBrowseFootActionPerformed();
							}
						});
					}
					{
						chkFoot = new JCheckBox();
						panelContent.add(chkFoot, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 122, 0, 0), 0, 0));
						chkFoot.setBounds(141, 75, 14, 17);
						chkFoot.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								chkFootActionPerformed();
							}
						});
					}
				}
			}
			{
				btnSave = new JButton();
				getContentPane().add(btnSave);
				btnSave.setName("btnSave");
				btnSave.setAction(getAppActionMap().get("save"));
				btnSave.setText(ApplicationInternationalization.getString("btnSave"));
				btnSave.setBounds(252, 311, 86, 25);
			}
			{
				btnCancel = new JButton();
				getContentPane().add(btnCancel);
				btnCancel.setName("btnCancel");
				btnCancel.setAction(getAppActionMap().get("Cancel"));
				btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
				btnCancel.setBounds(357, 311, 85, 25);
				
			}
			pack();
			this.setSize(469, 385);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	@Action
	public void save() {
		File path = null;	
		// TODO: a�adir metadatos del PDF (autor, hora, asunto ...). Validarlos
		FileNameExtensionFilter filter = new FileNameExtensionFilter(ApplicationInternationalization.getString("PDFFile"), "pdf", "PDF");
		JFileChooser fileChooser = new JFileChooser();       
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION ) {
			path = fileChooser.getSelectedFile().getAbsoluteFile();
			try {
				PDFComposer.createDocument(configuration, path, headerImagePath, footImagePath);
				// TODO: dialogo informacion
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
			this.dispose();
        }
	}
	
	@Action
	public void Cancel () {
		this.dispose();
	}
	
	private void chkHeaderActionPerformed() {
		txtPathHeader.setEnabled(true);
		btnBrowseHeader.setEnabled(true);
	}
	
	private void chkFootActionPerformed() {
		txtPathFoot.setEnabled(true);
		btnBrowseFoot.setEnabled(true);
	}
	
	private void btnBrowseHeaderActionPerformed() {
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

	private void btnBrowseFootActionPerformed() {
		footImagePath = getPath();
		
	}

}
