package presentation.customComponents.PDFGen;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

// REFERENCE: http://www.codeproject.com/KB/java/rounded-jpanel.aspx

import internationalization.ApplicationInternationalization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jdesktop.application.Application;

import presentation.customComponents.ImagePanel;
import presentation.customComponents.RoundedPanel;
import presentation.panelsActions.panelPDFGeneration;
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
public class panelPDFElement extends RoundedPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5770768725458703088L;
    private JButton btnAdd;
    private JLabel lblTitle;
    private ImagePanel imagePanel;
    
	private String elementName;
	private panelPDFGeneration parent;
	
	public panelPDFElement() {
		super();
		initGUI();
	}
	
	public panelPDFElement(String elementName, panelPDFGeneration parent) {
		super();
		this.elementName = elementName;
		this.parent = parent;
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(96, 102));
			this.setSize(96, 102);
			this.setMinimumSize(new java.awt.Dimension(96, 102));
			
			this.setLayout(null);
			{
				btnAdd = new JButton();
				this.add(btnAdd);
				btnAdd.setBounds(63, 74, 20, 20);
				btnAdd.setFocusPainted(false);
				btnAdd.setSelected(false);
				btnAdd.setToolTipText(ApplicationInternationalization.getString("Tooltip_insert"));
				btnAdd.setName("btnAdd");
				try 
				{
					btnAdd.setIcon(ImagesUtilities.loadIcon("menus/insert.png"));
				}
				catch (Exception e) { }				
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnAddActionPerformed();
					}
				});
			}
			{
				imagePanel = new ImagePanel();
				try {
					imagePanel.setImage(ImagesUtilities.loadCompatibleImage("PDFElements/" + elementName + ".png"));
				} catch (Exception e) { }
				this.add(imagePanel, new AnchorConstraint(36, 12, 15, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				imagePanel.setName("imagePanel");
				imagePanel.setBounds(11, 30, 70, 57);
				imagePanel.setPreferredSize(new java.awt.Dimension(70, 57));
			}
			{
				lblTitle = new JLabel();
				lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
				lblTitle.setText(elementName);
				this.add(lblTitle, new AnchorConstraint(5, 108, 98, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				lblTitle.setName("lblTitle");
				lblTitle.setBounds(7, 7, 77, 17);
			}

			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 
	 private void btnAddActionPerformed() {
		 parent.addPanelToSection(this);
	 }

	public String getTitle() {
		return elementName;
	}

	public void enableAddButton(boolean value) {
		btnAdd.setEnabled(value);
		
	}

}
