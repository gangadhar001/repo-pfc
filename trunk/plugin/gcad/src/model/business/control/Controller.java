package model.business.control;

import internationalization.BundleInternationalization;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.business.knowledge.AbstractKnowledge;
import model.business.knowledge.IActions;
import model.business.knowledge.ISession;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.UserRole;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.ISourceProviderService;

import persistence.PFProposal;
import persistence.communications.DBConfiguration;
import persistence.communications.DBConnectionManager;

import exceptions.IncorrectEmployeeException;
import exceptions.NoProjectProposalsException;
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;
import gcad.IResources;
import gcad.SourceProvider;

public class Controller {

	private ISession session = null;
	
	private static Controller instance = null;
		
	public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	public void login (String user, String pass) throws IncorrectEmployeeException, SQLException, NonExistentRole {
		session = SessionController.login(user, pass);
	}
	
	public void notifyLogin () {
		//TODO: se notifica la conexion con la base de datos a las vistas
		PresentationController.notifyConnection(true);
	}
	
	public void notifySignOut () {
		//TODO: se notifica la conexion con la base de datos a las vistas
		PresentationController.notifyConnection(false);
	}
	
	public void addProposal (Proposal p, Topic parent) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		KnowledgeController.addProposal(p, parent);
	}
	
	public void notifyKnowledgeAdded(AbstractKnowledge newKnowledge) {
		PresentationController.notifyProposals(newKnowledge);
	}
	
	public ArrayList<AbstractKnowledge> getProposals() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return KnowledgeController.getProposals();
	}
	
	/* TODO: añadir funcionalidades para que esta clase gestione los casos
	de uso del plugin */
	
	public boolean isLogged () {
		return session != null;
	}

	public ISession getSession() {
		return session;
	}

	public static TopicWrapper getKnowledgeTreeProject(int idProject) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return KnowledgeController.getKnowledgeTreeProject(idProject);
	}
	
	/**
	 * This method reads the profiles available for a user role from a XML file, and enables/disables the corresponding menu items.
	 * First of all, when no user is logged, only is enabled "Session" menu
	 */
	public void notifyActionAllowed() throws ConfigurationException, NonPermissionRole {		
		XMLConfiguration config;
	    
		List<String> rolesName;
		List<String> profiles;
		List<String> profilesName;
		List<String> actionsName;
		
		List<HierarchicalConfiguration> profilesConfiguration;
		List<HierarchicalConfiguration> actionsConfigurations;
		
		int index;
		String profile; 
		
		UserRole role = UserRole.values()[session.getRole()];
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
			
			// Enabled/disabled actions from the menus
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
			
			//TODO: actualizar las vistas para cambiar su toolbar 
		    PresentationController.notifyActionsAllowed(actionsName);
	    }
	    
	    else {
	    	throw new NonPermissionRole(BundleInternationalization.getString("Exception.NonPermissionRole") + role.toString());
	    }
	}

	public void signout() throws SQLException {
		session = null;
		DBConnectionManager.closeConnections();
		DBConnectionManager.clearConnections();		
	}

	public void initDBConnection(DBConfiguration configuration) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		// Close older connections (ignore errors)
		try {
			DBConnectionManager.closeConnections();
		} catch(SQLException e) { }
		DBConnectionManager.clearConnections();
		
		// Create and initialize a new database connection
		DBConnectionManager.initializate(configuration);
		
	}
			
}
