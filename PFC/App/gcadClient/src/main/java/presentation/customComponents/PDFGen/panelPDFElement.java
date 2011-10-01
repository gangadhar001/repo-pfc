package presentation.customComponents.PDFGen;

// REFERENCE: http://www.codeproject.com/KB/java/rounded-jpanel.aspx

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
			this.setPreferredSize(new java.awt.Dimension(128, 128));
			this.setSize(128, 128);
			this.setMinimumSize(new java.awt.Dimension(128, 128));
			
			this.setLayout(null);
			{
				btnAdd = new JButton();
				this.add(btnAdd);
				btnAdd.setName("btnAdd");
				btnAdd.setBounds(84, 98, 39, 23);
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnAddActionPerformed();
					}
				});
			}
			{
				imagePanel = new ImagePanel();
				imagePanel.setImage(ImagesUtilities.loadCompatibleImage("PDFElements/" + elementName + ".png"));
				this.add(imagePanel);
				imagePanel.setBounds(12, 36, 104, 77);
				imagePanel.setName("imagePanel");
			}
			{
				lblTitle = new JLabel();
				lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
				lblTitle.setText(elementName);
				this.add(lblTitle);
				lblTitle.setBounds(7, 5, 101, 25);
				lblTitle.setName("lblTitle");
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

}
