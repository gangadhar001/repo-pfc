package presentation.customComponents.PDFGen;
import java.io.IOException;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JTextField;

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
public class panelPDFDraggedTitle extends panelPDFDragged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 908251059799542523L;
	private ImagePanel imagePanel;
	private JButton btnDelete;
	private JTextField tbTitle;
	private panelPDFGeneration parent;

	public panelPDFDraggedTitle(panelPDFGeneration parent) {
		super();
		this.parent = parent;
		initGUI();
	}

	private void initGUI() {
		{
			this.setPreferredSize(new java.awt.Dimension(646, 67));
			this.setLayout(null);
			this.setMaximumSize(new java.awt.Dimension(646, 67));
			this.setMinimumSize(new java.awt.Dimension(646, 67));
			{
				imagePanel = new ImagePanel();
				try {
					imagePanel.setImage(ImagesUtilities.loadCompatibleImage("PDFElements/Title.png"));
				} catch (IOException e) { }
				this.add(imagePanel);
				imagePanel.setBounds(12, 12, 50, 45);
			}
			{
				tbTitle = new JTextField();
				this.add(tbTitle);
				tbTitle.setBounds(68, 19, 547, 36);
				tbTitle.setName("tbTitle");
			}
			{
				btnDelete = new JButton();
				this.add(btnDelete);
				btnDelete.setBounds(621, 5, 20, 15);
				btnDelete.setSize(16, 16);
				btnDelete.setAction(getAppActionMap().get("Delete"));
				btnDelete.setContentAreaFilled(false);
				btnDelete.setText("");
				try {
					btnDelete.setIcon(ImagesUtilities.loadIcon("menus/remove.png"));
				} catch (Exception e) { }
			}
		}

		Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
	}

	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	public String getContent() {
		return tbTitle.getText().trim();
	}
	
	@Action
	public void Delete () {
		parent.removeDragged(this);
		parent.setEnableTitle(true);
	}
}
