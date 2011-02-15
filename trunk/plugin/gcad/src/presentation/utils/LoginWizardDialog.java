package presentation.utils;

import java.io.IOException;
import java.sql.SQLException;

import model.business.control.Controller;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import presentation.wizards.control.login.LoginWizardController;
import presentation.wizards.login.LoginWizardPage;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;

public class LoginWizardDialog extends WizardDialog {

	private IWizard newWizard;
	
	public LoginWizardDialog(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		this.newWizard = newWizard;
	}
	
	// TODO: cuando se pulsa next, se conecta con la base de datos y se pasa la informacion a la otra pagina
	@Override
	protected void nextPressed() {
 		/*HibernateUtil.setDatabaseURL("127.0.0.1");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		// CONSULTA

		
		TopicWrapper t = new TopicWrapper();
		t.setTopics((ArrayList<Topic>) session.createQuery("From Topic where Id=2").list());
		User u = (ChiefProject)(session.createQuery("From User where Id=2").list()).get(0);
		Topic lo = t.getTopics().get(0);
		Proposal p = new Proposal("title3", "description", new Date(), Categories.Design, 0);
		p.setUser(u);
		lo.add(p);
		session.save(p);
		session.getTransaction().commit();
		session.close();*/
		
		if (newWizard instanceof LoginWizardController) {
			LoginWizardPage page = ((LoginWizardController) newWizard).getPage();
			// Tomar los datos de la primera pagina
			String userName = page.getLoginText();
			String passText = page.getPassText();
			String IPText = page.getIPText();
			String portText = page.getPortText();
			try {
				doNext(userName, passText, IPText, portText);
				super.nextPressed();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IncorrectEmployeeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NonExistentRole e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This will create the database connection and log in the user
	 */
	private void doNext(String user, String pass, String IP, String port) throws CoreException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, IncorrectEmployeeException, NonExistentRole {				
		// Create and initialize a new database connection
		Controller.getInstance().initDBConnection(IP, port);
		
		// Login
		Controller.getInstance().login(user,pass);		
	}

}
