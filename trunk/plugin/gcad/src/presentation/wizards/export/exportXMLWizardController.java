package presentation.wizards.export;

import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import internationalization.BundleInternationalization;

import model.business.control.Controller;
import model.business.knowledge.TopicWrapper;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import persistence.XMLAgent;

//TODO: referencia: http://kickjava.com/src/org/eclipse/ui/wizards/datatransfer/FileSystemExportWizard.java.htm

public class exportXMLWizardController extends Wizard implements IExportWizard {

	private exportXMLWizard page; 
	
	public exportXMLWizardController() {
		super();
		setWindowTitle(BundleInternationalization.getString("LoginWizardTitle"));
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		super.addPages();
		page = new exportXMLWizard();
		addPage(page);
	}


	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("ada");
		// ImageDescriptor

	}

	@Override
	public boolean performFinish() {
		boolean result = page.finish();
		if (result) {
			TopicWrapper t;
			try {
				t = Controller.getInstance().getTopicsWrapper();
				XMLAgent.marshal(page.getFilePath(), TopicWrapper.class, (TopicWrapper)t);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}

}
