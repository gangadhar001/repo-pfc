package model.business.control;

import internationalization.BundleInternationalization;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.business.knowledge.Answer;
import model.business.knowledge.IActions;
import model.business.knowledge.ISession;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Language;
import model.business.knowledge.Notification;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Session;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.UserRole;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.ISourceProviderService;

import persistence.communications.DBConnection;
import persistence.communications.DBConnectionManager;
import exceptions.IncorrectEmployeeException;
import exceptions.NoProposalsException;
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;
import gcad.IResources;
import gcad.SourceProvider;

/**
 * This class represents a controller to centralize all the control of the tool
 */
public class Controller {

	private Session session = null;

	private static Controller instance = null;
		
	public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	/*** Methods used to manage login and signout ***/
	public void login (String user, String pass) throws IncorrectEmployeeException, SQLException, NonExistentRole {
		session = SessionController.login(user, pass);
	}
	
	public void signout() throws SQLException {
		SessionController.signout(session);
		DBConnectionManager.clear();
		session = null;
	}

	public void initDBConnection(String ip, String port) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		DBConnectionManager.addConnection(new DBConnection(ip, Integer.parseInt(port), "dbgcad"));
	}
	
	/*** Methods used to add new Knowledge ***/
	public void addTopic (Topic topic) throws SQLException {
		boolean found = false;
		Project project = null;
		// Search te current project
		for (Iterator<Project> i = session.getUser().getProjects().iterator(); i.hasNext() && !found; ) {
			project = (Project)i.next();
			if (project.getId() == session.getCurrentActiveProject()) {
				found = true;
			}		
		}	
		KnowledgeController.addTopic(session.getUser(), project , topic);
	}
	
	public void addProposal (Proposal p, Topic parent) throws SQLException {
		KnowledgeController.addProposal(session.getUser(), p, parent);
	}
	
	public void addAnwser (Answer a, Proposal parent) throws SQLException {
		KnowledgeController.addAnswer(session.getUser(), a, parent);
	}
	
	/*** Methods used to modify Knowledge ***/
	public void modifyTopic(Topic newTopic, Topic oldTopic) throws SQLException {
		KnowledgeController.modifyTopic(session.getUser(), newTopic, oldTopic);		
	}
	
	public void modifyProposal(Proposal newProposal, Proposal oldProposal, Topic parent) throws SQLException {
		KnowledgeController.modifyProposal(session.getUser(), newProposal, oldProposal, parent);		
	}
	
	public void modifyAnswer(Answer newAnswer, Answer oldAnswer, Proposal parent) throws SQLException {
		KnowledgeController.modifyAnswer(session.getUser(), newAnswer, oldAnswer, parent);		
	}
		
	/*** Methods used to delete Knowledge ***/
	public void deleteTopic(Topic to) throws SQLException {
		KnowledgeController.deleteTopic(to);
		notifyKnowledgeRemoved(to);
	}
	
	public void deleteProposal(Proposal p) throws SQLException {
		KnowledgeController.deleteProposal(p);
		notifyKnowledgeRemoved(p);
	}

	public void deleteAnswer (Answer a) throws SQLException {
		KnowledgeController.deleteAnswer(a);	
		notifyKnowledgeRemoved(a);
	}
	
	/*** Methods used to manage notifications ***/
	public void addNotification(Notification notification) throws SQLException {
		NotificationController.deleteNotification(notification);
	}
	
	public void removeNotification(Notification notification) throws SQLException {
		NotificationController.deleteNotification(notification);
	}

	public ArrayList<Notification> getNotifications() throws SQLException {
		return NotificationController.getNotifications(session.getCurrentActiveProject());
	}
	
	/*** Auxiliary methods ***/
	public ArrayList<Proposal> getProposals() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoProposalsException {
		return KnowledgeController.getProposals();
	}
	
	public ArrayList<Answer> getAnswers() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return KnowledgeController.getAnswers();
	}
	
//	public ArrayList<Project> getProjectsUser() {
//		// TODO: return los proyectos del usuario de la sesion
//		// return ProjectController.getProjectsUser(session.getUser().getId());
//		return null;
//	}	
	
	public TopicWrapper getTopicsWrapper() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return KnowledgeController.getTopicsWrapper();
	}
		
	/*** Methods used to manage the UI observer ***/
	public void notifyLogin () {
		PresentationController.notifyConnection(true);
	}
	
	public void notifySignOut () {
		PresentationController.notifyConnection(false);
	}
	
	public void notifyKnowledgeAdded(Knowledge k) {
		PresentationController.notifyKnowledgeAdded(k);
	}
	
	public void notifyKnowledgeChanged(Knowledge k) {
		PresentationController.notifyKnowledgeEdited(k);
	}
	
	public void notifyKnowledgeRemoved(Knowledge k) {
		PresentationController.notifyKnowledgeRemoved(k);
	}
	
	/**
	 * This method reads the profiles available for a user role from a XML file, and enables/disables the corresponding menu items.
	 * First of all, when no user is logged, only is enabled "Session" menu
	 */
	@SuppressWarnings("unchecked")
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
	    	for(Iterator<HierarchicalConfiguration> it = profilesConfiguration.iterator(); it.hasNext();)
		    {
		        HierarchicalConfiguration sub = it.next();
		        // "Sub" contains now all data about a single field
		        String profileName = sub.getString("name");
		        profiles.add(profileName);
		    }		    
			
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
			for(Iterator<HierarchicalConfiguration> it = actionsConfigurations.iterator(); it.hasNext();)
			    {
			        HierarchicalConfiguration sub = it.next();
			        actionsName.addAll(sub.getList("name"));
			    }	
			
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
		    PresentationController.notifyActionsAllowed(actionsName);
	    }
	    
	    else {
	    	throw new NonPermissionRole(BundleInternationalization.getString("Exception.NonPermissionRole") + role.toString());
	    }
	}
	
	public boolean isLogged () {
		return session != null;
	}

	public ISession getSession() {
		return session;
	}

	public void setCurrentProject(int id) {
		session.setCurrentActiveProject(id);
		
	}

	public ArrayList<Language> getLanguages() throws ConfigurationException {
		return LanguagesController.getLanguages();
	}

}
