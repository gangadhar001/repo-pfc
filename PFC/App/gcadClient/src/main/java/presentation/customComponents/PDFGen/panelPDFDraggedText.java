package presentation.customComponents.PDFGen;
import java.io.IOException;
import javax.swing.JButton;

import javax.swing.ActionMap;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.customComponents.ImagePanel;
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
public class panelPDFDraggedText extends panelPDFDragged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 908251059799542523L;
	private ImagePanel imagePanel;
	private JTextArea txtDescription;
	private JScrollPane jScrollPane1;
	private JButton btnDelete;
	private panelPDFGeneration parent;

	public panelPDFDraggedText(panelPDFGeneration parent) {
		super();
		this.parent = parent;
		initGUI();
	}

	private void initGUI() {
		{
			this.setPreferredSize(new java.awt.Dimension(646, 95));
			this.setLayout(null);
			this.setSize(646, 95);
			this.setMinimumSize(new java.awt.Dimension(646, 95));
			this.setMaximumSize(new java.awt.Dimension(646, 95));
			{
				imagePanel = new ImagePanel();
				try {
					imagePanel.setImage(ImagesUtilities.loadCompatibleImage("PDFElements/Text.png"));
				} catch (IOException e) { }
				this.add(imagePanel);
				imagePanel.setBounds(12, 27, 52, 45);
			}
			{
				jScrollPane1 = new JScrollPane();
				this.add(jScrollPane1);
				jScrollPane1.setBounds(79, 6, 536, 83);
				{
					txtDescription = new JTextArea();
					jScrollPane1.setViewportView(txtDescription);
					txtDescription.setBounds(79, 12, 555, 93);
					txtDescription.setName("txtDescription");
					txtDescription.setPreferredSize(new java.awt.Dimension(525, 74));
				}
			}
			{
				btnDelete = new JButton();
				this.add(btnDelete);
				btnDelete.setAction(getAppActionMap().get("Delete"));
				btnDelete.setName("btnDelete");
				try {
					btnDelete.setIcon(ImagesUtilities.loadIcon("menus/remove.png"));
				} catch (Exception e) { }
			}
			{
				btnDelete.setText("");
				btnDelete.setBounds(621, 5, 20, 15);
				btnDelete.setSize(20, 20);
			}
		}

		Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
	}

	public String getContent() {
		return txtDescription.getText().trim();
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	@Action
	public void Delete () {
		parent.removeDragged(this);
	}
}
