package presentation.customComponents.PDFGen;
import internationalization.ApplicationInternationalization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import model.business.knowledge.Project;

import org.jdesktop.application.Application;

import presentation.JDConfigurePDFTable;
import presentation.customComponents.ImagePanel;
import resources.ImagesUtilities;
import bussiness.control.ClientController;
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
				this.addMouseListener(new MouseListener() {					
					@Override
					public void mousePressed(MouseEvent e){
				        if (e.isPopupTrigger())
				            doPop(e);
				    }
					
					@Override
				    public void mouseReleased(MouseEvent e){
				        if (e.isPopupTrigger())
				            doPop(e);
				    }
					
					@Override
					public void mouseExited(MouseEvent e) { }
					
					@Override
					public void mouseEntered(MouseEvent e) { }
					
					@Override
					public void mouseClicked(MouseEvent e) { }
				});
			}
		}

		Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
	
	}
	
	// Show the pop up menu 
	private void doPop(MouseEvent e){
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Configure");
        item.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				configurePDFTable();				
			}
		});
        menu.add(item);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

	// Show the dialog to choose a project 
	private void configurePDFTable() {
		JDConfigurePDFTable ct = new JDConfigurePDFTable();
		ct.setLocationRelativeTo(this);
		ct.setModal(true);
		ct.setVisible(true);
		// Get the selected project
		project = ct.getSelectedProject();
	}

	public Project getProject() {
		try {
			project = ClientController.getInstance().getProjectsFromCurrentUser().get(0);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		return project;
	}
	
	
}
