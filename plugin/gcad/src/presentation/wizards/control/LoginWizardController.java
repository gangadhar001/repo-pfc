package presentation.wizards.control;


import gcad.IActions;
import gcad.IResources;
import gcad.SourceProvider;
import internationalization.BundleInternationalization;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.business.control.Controller;
import model.business.knowledge.UserRole;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.ISourceProviderService;
import org.omg.CORBA.portable.InputStream;

import persistence.communications.DBConfiguration;
import persistence.communications.DBConnectionManager;
import presentation.wizards.LoginWizardPage;
import exceptions.IncorrectEmployeeException;

/**
 * This class allows to login
 */
public class LoginWizardController extends Wizard {
		
	private LoginWizardPage page;

	public LoginWizardController () {
		super();
		setWindowTitle(BundleInternationalization.getString("LoginWizardTitle"));
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		super.addPages();
		page = new LoginWizardPage(BundleInternationalization.getString("LoginWizardTitle"));
		addPage(page);
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String userName = page.getLoginText();
		final String passText = page.getPassText();
		final String IPText = page.getIPText();
		final String portText = page.getPortText();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					try {
						doFinish(monitor, userName, passText, IPText, portText);
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
						// It is only possible to throw this exception type
						throw new InvocationTargetException(e);
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// It is only possible to throw this exception type
						throw new InvocationTargetException(e);
					}					
				
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
			// Notify to the views the satisfactory login
			Controller.getInstance().notifyLogin();
			// This class is responsible to update the menu items, depend on the user role logged
			updateMenus(UserRole.values()[Controller.getInstance().getSession().getRol()]);
			
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;	
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * The worker method. It will log in the user and create the database connection
	 */
	private void doFinish(IProgressMonitor monitor, String user, String pass, String IP, String port) throws CoreException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, IncorrectEmployeeException {		
		monitor.beginTask(BundleInternationalization.getString("DBMonitorMessage"), 60);
		// TODO: poner al usuario en una sesion
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("DBMonitorMessage"));

		// Create the configuration of the database connection
		DBConfiguration configuration = new DBConfiguration(IP, Integer.parseInt(port));
		monitor.worked(10);
		// Close older connections (ignore errors)
		try {
			DBConnectionManager.closeConnections();
			monitor.worked(10);
		} catch(SQLException e) { }
		DBConnectionManager.clearConnections();
		monitor.worked(10);
		
		// Create and initializate a new database connection
		DBConnectionManager.initializate(configuration);
		monitor.worked(10);
		
		// TODO: Identificarse
		Controller.getInstance().login(user,pass);		
	}
	
	/**
	 * This method reads the profiles available for a user role from a XML file, and enables/disables the corresponding menu items.
	 * First of all, when no user is logged, only is enabled "Session" menu
	 */
	public void updateMenus(UserRole role) throws ConfigurationException {
		//TODO: según el rol, activar/desactivar menus
		// Hacerlo por XML
		// Al inicio, solo está activo el menu "Session"
		// Segun la lista de operaciones leida del XML, se activa. Recorrer esa lista y llamar al "getSourceProvider" con
		// el valor correspondiente de la interfaz
		XMLConfiguration config;
	    
		List<String> rolesName;
		List<String> profiles;
		List<String> profilesName;
		List<String> actionsName;
		
		List<HierarchicalConfiguration> profilesConfiguration;
		List<HierarchicalConfiguration> actionsConfigurations;
		
		int index;
		String profile; 
		
		// If no user is logged, disabled all menus and actions, except "Session" menu
		if (role==null) {
			
		}
		
		else {
		    config = new XMLConfiguration(this.getClass().getClassLoader().getResource(IResources.XML_PROFILES_PATH+"profiles-role.xml"));
		    // Take defined roles in that XML file (returns a list)
		    rolesName = config.getList("Role.name");		    		
		    // Check it the user role that is logged exists in that list
		    if (rolesName.contains(role.toString())) {
		    	// If it exists, take its profiles (actions)
		    	index = rolesName.indexOf(role.toString());
		    	profilesConfiguration = config.configurationsAt("Role("+index+").Profile");
		    	profiles = new ArrayList<String>();		    
		    	for(Iterator it = profilesConfiguration.iterator(); it.hasNext();)
			    {
			        HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
			        // "Sub" contains now all data about a single field
			        String profileName = sub.getString("name");
			        profiles.add(profileName);
			    }	
			    System.out.println("Perfiles: " + profiles);
			    
				
				// When it is read the profiles of that user role, read the actions and menu items available for those profiles
				config = new XMLConfiguration(this.getClass().getClassLoader().getResource(IResources.XML_PROFILES_PATH+"profiles.xml"));
				profilesName = config.getList("Profile.name");
				actionsConfigurations = new ArrayList<HierarchicalConfiguration>();
		
				for (int i=0; i<profiles.size(); i++) {
					profile = profiles.get(i);
					// Take the actions corresponding to the profiles that was read previously
					if (profilesName.contains(profile)) {
						index = profilesName.indexOf(profile);
						actionsConfigurations.addAll(config.configurationsAt("Profile("+index+").Actions"));
					}
				}
		
				// Take the actions name
				actionsName = new ArrayList<String>();	
				for(Iterator it = actionsConfigurations.iterator(); it.hasNext();)
				    {
				        HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
				        actionsName.addAll(sub.getList("name"));
				    }	
				System.out.println("Acciones para esos perfiles: " + actionsName);
				
				// Enabled/disabled actions
				ISourceProviderService sourceProviderService = (ISourceProviderService)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(ISourceProviderService.class);
				SourceProvider commandStateService = null;
				for (String act: IActions.actions) {
					// Enable action
					if (actionsName.contains(act)) {
						commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(act);
						commandStateService.setMenuItemVisible(true, act);
					}
					// Disable
					else {
						commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(act);
						commandStateService.setMenuItemVisible(false, act);
					}
						
				}
		    }
		    else {} //TODO: no existe ese rol en el sistema
		}
	}
}
