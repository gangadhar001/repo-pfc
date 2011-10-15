package presentation.customComponents.PDFGen;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JTextField;

import model.business.knowledge.Project;

import org.jdesktop.application.Application;

import exceptions.NotLoggedException;

import bussiness.control.ClientController;

import presentation.customComponents.ImagePanel;
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
public class panelPDFDraggedTable extends panelPDFDragged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 908251059799542523L;
	private ImagePanel imagePanel;
	private Project project;

	public panelPDFDraggedTable() {
		super();
		project = null;
		initGUI();
	}

	private void initGUI() {
		{
			this.setPreferredSize(new java.awt.Dimension(500, 100));
			this.setLayout(null);
			{
				imagePanel = new ImagePanel();
				try {
					imagePanel.setImage(ImagesUtilities.loadCompatibleImage("PDFElements/Table.png"));
				} catch (IOException e) { }
				this.add(imagePanel);
				imagePanel.setBounds(12, 12, 82, 71);
			}
		}

		Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
	
	}

	public Project getProject() {
		// TODO: quitar
		try {
			project = ClientController.getInstance().getProjectsFromCurrentUser().get(0);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLoggedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return project;
	}
	
	
}
