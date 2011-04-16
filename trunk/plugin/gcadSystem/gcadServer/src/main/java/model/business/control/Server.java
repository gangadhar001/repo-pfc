package model.business.control;

import internationalization.BundleInternationalization;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.business.knowledge.Answer;
import model.business.knowledge.IResources;
import model.business.knowledge.ISession;
import model.business.knowledge.Language;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Session;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.UserRole;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

import communication.ClientProxy;
import communication.DBConnectionManager;
import communication.IClient;
import communication.IServer;

import exceptions.IncorrectEmployeeException;
import exceptions.NoProposalsException;
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

/**
 * This class implements the facade of the server.
 * TODO: todos los metodos lanzan la remoteException
 */
public class Server implements IServer {

//	private Session session = null;

	private static Server instance = null;
		
	public static Server getInstance() {
		if(instance == null) {
			instance = new Server();
		}
		return instance;
	}
	
	/*** Methods used to manage login and signout ***/
	public ISession login (String user, String pass) throws IncorrectEmployeeException, SQLException, NonExistentRole {
		return SessionController.login(user, pass);
		
	}
	
	public void signout(long sessionID) throws SQLException, NotLoggedException {
		SessionController.signout(sessionID);
		// Remove registered client
		ClientsController.detach(sessionID);
		DBConnectionManager.clear();
	}
	
	public void disconnectClients() throws RemoteException {
		ClientsController.disconnectClients();
		SessionController.disconnectClients();
	}
	
	public void register(long sessionID, IClient client) throws RemoteException, NotLoggedException {
		String login;
		Session session;
		ClientProxy clientProxy;
		
		//TODO:
		if (client == null)
			throw new NullPointerException("El cliente que se quiere registrar no puede ser nulo.");
		
		// Check if the session is valid
		session = SessionController.getSessions().get(sessionID);
		if(session == null) {
			throw new NotLoggedException();
		}
//		try {
		clientProxy = new ClientProxy();
		clientProxy.associate(client);
		ClientsController.attach(sessionID, clientProxy);
		ClientsController.notifyConnection(true);
//			login = GestorSesiones.getSesion(idSesion).getUsuario().getLogin();
//			GestorConexionesLog.ponerMensaje(login, ITiposMensajeLog.TIPO_INFO, "Registrado el cliente con id de sesión " + idSesion + ".");
//			GestorConexionesLog.actualizarClientesEscuchando(GestorSesiones.getClientes().size());
//		} catch(SesionNoIniciadaException snie) {
//			GestorConexionesLog.ponerMensaje(ITiposMensajeLog.TIPO_INFO, "Error al comprobar la sesión con id " + idSesion + " para registrar un cliente en el sistema: " + snie.getLocalizedMessage());
//			throw snie;
//		} catch(NullPointerException npe) {
//			GestorConexionesLog.ponerMensaje(ITiposMensajeLog.TIPO_INFO, "Error al intentar registrar un cliente con datos no válidos: " + npe.getLocalizedMessage());
//			throw npe;
//		} catch(Exception e) {
//			GestorConexionesLog.ponerMensaje(ITiposMensajeLog.TIPO_INFO, "Error inesperado mientras se registraba un cliente en el sistema: " + e.toString());
//			throw e;
//		}
	}

//	public void initDBConnection(String ip, String port) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
//		DBConnectionManager.addConnection(new DBConnection(ip, Integer.parseInt(port), "dbgcad"));
//	}
	
	/*** Methods used to add new Knowledge ***/
	public void addTopic (long sessionId, Topic topic) throws SQLException {
		boolean found = false;
		Project project = null;
		Session session = SessionController.getSession(sessionId);
		// Search the current project
		for (Iterator<Project> i = session.getUser().getProjects().iterator(); i.hasNext() && !found; ) {
			project = (Project)i.next();
			if (project.getId() == session.getCurrentActiveProject()) {
				found = true;
			}		
		}	
		KnowledgeController.addTopic(session.getUser(), project , topic);
	}
	
	public void addProposal (long sessionId, Proposal p, Topic parent) throws SQLException {
		Session session = SessionController.getSession(sessionId);
		KnowledgeController.addProposal(session.getUser(), p, parent);
	}
	
	public void addAnwser (long sessionId, Answer a, Proposal parent) throws SQLException {
		Session session = SessionController.getSession(sessionId);
		KnowledgeController.addAnswer(session.getUser(), a, parent);
	}
	
	/*** Methods used to modify Knowledge ***/
	public void modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws SQLException {
		Session session = SessionController.getSession(sessionId);
		KnowledgeController.modifyTopic(session.getUser(), newTopic, oldTopic);		
	}
	
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws SQLException {
		Session session = SessionController.getSession(sessionId);
		KnowledgeController.modifyProposal(session.getUser(), newProposal, oldProposal, parent);		
	}
	
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws SQLException {
		Session session = SessionController.getSession(sessionId);
		KnowledgeController.modifyAnswer(session.getUser(), newAnswer, oldAnswer, parent);		
	}
		
	/*** Methods used to delete Knowledge ***/
//	public void deleteTopic(Topic to) throws SQLException {
//		KnowledgeController.deleteTopic(to);
//		notifyKnowledgeRemoved(to);
//	}
//	
//	public void deleteProposal(Proposal p) throws SQLException {
//		KnowledgeController.deleteProposal(p);
//		notifyKnowledgeRemoved(p);
//	}
//
//	public void deleteAnswer (Answer a) throws SQLException {
//		KnowledgeController.deleteAnswer(a);	
//		notifyKnowledgeRemoved(a);
//	}
	
	/*** Methods to manage projects ***/
	public void createProject (long sessionId, Project p) throws RemoteException, SQLException {
		ProjectController.createProject(p);
	}
	
	/*** Methods used to manage notifications ***/
	public void addNotification(long sessionId, Notification notification) throws SQLException {
		NotificationController.deleteNotification(notification);
	}
	
	public void removeNotification(long sessionId, Notification notification) throws SQLException {
		NotificationController.deleteNotification(notification);
	}

	public ArrayList<Notification> getNotifications(long sessionId) throws SQLException {
		Session session = SessionController.getSession(sessionId);
		return NotificationController.getNotifications(session.getCurrentActiveProject());
	}
	
	/*** Auxiliary methods ***/
	public ArrayList<Proposal> getProposals(long sessionId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoProposalsException {
		return KnowledgeController.getProposals(sessionId);
	}
	
	public ArrayList<Answer> getAnswers(long sessionId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return KnowledgeController.getAnswers(sessionId);
	}
	
//	public ArrayList<Project> getProjectsUser() {
//		// TODO: return los proyectos del usuario de la sesion
//		// return ProjectController.getProjectsUser(session.getUser().getId());
//		return null;
//	}	
	
	public TopicWrapper getTopicsWrapper(long sessionId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return KnowledgeController.getTopicsWrapper(sessionId);
	}
		
	/*** Methods used to manage the UI observer ***/
//	public void notifyLogin () {
//		ClientsController.notifyConnection(true);
//	}
//	
//	public void notifySignOut () {
//		ClientsController.notifyConnection(false);
//	}
//	
//	public void notifyKnowledgeAdded(Knowledge k) {
//		ClientsController.notifyKnowledgeAdded(k);
//	}
//	
//	public void notifyKnowledgeChanged(Knowledge k) {
//		ClientsController.notifyKnowledgeEdited(k);
//	}
//	
//	public void notifyKnowledgeRemoved(Knowledge k) {
//		ClientsController.notifyKnowledgeRemoved(k);
//	}
	
	/**
	 * This method reads the profiles available for a user role from a XML file.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws NonPermissionRole {
		ArrayList<Operation> result = new ArrayList<Operation>();
		
		Session session = SessionController.getSession(sessionId);
	    
		XMLConfiguration configFile;
		List<String> rolesName;
		List<String> profiles = new ArrayList<String>();
		List<String> profilesName;	
		
		List<HierarchicalConfiguration> profilesConfiguration;
//		List<HierarchicalConfiguration> groupsConfigurations;
//		List<HierarchicalConfiguration> actionsConfigurations;
//		
		int index;
//		String profile; 
		try {
		// Take the role of the logged user
		UserRole role = UserRole.values()[session.getRole()];
		// Load XML Roles file
	    configFile = new XMLConfiguration(IResources.XML_PROFILES_PATH+"profiles-role.xml");
	    // Use XPath expressions
//	    configFile.setExpressionEngine(new XPathExpressionEngine());
	    
//	    // Take defined roles in that XML file (returns a list)
	    rolesName = configFile.getList("Role[@name]");		    		
	    // Check it the user role that is logged exists in that list
	    if (rolesName.contains(role.toString())) {
	    // Take the profiles of that role
	    	index = rolesName.indexOf(role.toString());
//	    List<HierarchicalConfiguration> nodes = configFile.configurationsAt("//Roles/Role[@name='"+role.name()+"']");
//	    for (HierarchicalConfiguration subNode : nodes)
//	    	profiles.add(subNode.getString("Profile/name"));
	    	profilesConfiguration = configFile.configurationsAt("Role("+index+").Profile");
	    	profiles = new ArrayList<String>();		    
	    	for(Iterator<HierarchicalConfiguration> it = profilesConfiguration.iterator(); it.hasNext();)
		    {
		        HierarchicalConfiguration sub = it.next();
		        // Reads profiles name for that role
		        String profileName = sub.getString("name");
		        profiles.add(profileName);
		    }		    
//		if (profiles.size() != 0){
			// Load operations XML
			configFile = new XMLConfiguration(IResources.XML_PROFILES_PATH+"profiles.xml");
			profilesName = configFile.getList("Profile[@name]");
//			groupsConfigurations = new ArrayList<HierarchicalConfiguration>();
//			actionsConfigurations = new ArrayList<HierarchicalConfiguration>();
//	
			// For each profile, take groups, subgroups and operations and create an "Operation" object
			List<String> groupsName;
			List<String> subgroupsName;
			List<String> operationsName;
			Operation op;
			for (int i=0; i<profilesName.size(); i++) {
				// If this profile belongs to the given role, reads its content
				if (profiles.contains(profilesName.get(i))) {
					// Reads groups
					groupsName = configFile.getList("Profile("+i+").Group[@name]");
					// For each group, read subgroup
					for (int j=0; j<groupsName.size(); j++){
						subgroupsName = configFile.getList("Profile("+i+").Group("+j+").Subgroup[@name]");
						// If there aren't any subgroups, take operations
						if (subgroupsName.size() == 0) {
							operationsName = configFile.getList("Profile("+i+").Group("+j+").Operations.name");
							op = new Operation(groupsName.get(i), "", operationsName);
							result.add(op);
						}
						else {
							// If there are subgroups, take operations for each one
							for (int k=0; k<subgroupsName.size(); k++) {
								operationsName = configFile.getList("Profile("+i+").Group("+j+").Subgroup("+k+").Operations.name");
								op = new Operation(groupsName.get(i), "", operationsName);
								result.add(op);
							}		
						}
					}					
				}
			}
//				if (profilesName.contains(profile)) {
//					index = profilesName.indexOf(profile);
//					groupsConfigurations.addAll(config.configurationsAt("Profile("+index+").Group"));
				
			
//			for (int i=0; i<profiles.size(); i++) {
//				profile = profiles.get(i);
//				// Take the actions corresponding to the profiles that was read previously
//				if (profilesName.contains(profile)) {
//					index = profilesName.indexOf(profile);
//					actionsConfigurations.addAll(config.configurationsAt("Profile("+index+").Actions"));
//				}
//			}
	
			
			
			
//			for(Iterator<HierarchicalConfiguration> it = groupsConfigurations.iterator(); it.hasNext();)
//			    {
//					actionsName = new ArrayList<String>();	
//					actionsConfigurations.clear();
//				
//			        HierarchicalConfiguration subGroup = it.next();
//			        actionsConfigurations.addAll(subGroup.configurationsAt("Actions"));	
//			        
//					for(Iterator<HierarchicalConfiguration> itAct = actionsConfigurations.iterator(); itAct.hasNext();)
//				    {
//					
//				        HierarchicalConfiguration subAct = itAct.next();
//				        actionsName.addAll(subAct.getList("name"));
//				        if (!result.contains(subGroup.getString("name")))
//				        	result.put(subGroup.getString("name"), actionsName);
//				        else
//				        	result.get(subGroup.getString("name")).addAll(actionsName);
//				        
//				    }
//					
//					
//			    }
			
			
			
			// TODO: hacer esto al llamar al login, desde el plugin. Enabled/disabled actions from the menus
//			ISourceProviderService sourceProviderService = (ISourceProviderService)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(ISourceProviderService.class);
//			SourceProvider commandStateService = null;
//			for (String act: IActions.actions) {
//				// Enable action
//				if (actionsName.contains(act)) {
//					commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(act);
//					commandStateService.setMenuItemVisible(true, act);
//				}
//				// Disable
//				else {
//					commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(act);
//					commandStateService.setMenuItemVisible(false, act);
//				}
//					
//			}			
//	    }
	    
	    } else {
	    	throw new NonPermissionRole(BundleInternationalization.getString("Exception.NonPermissionRole") + role.toString());
	    }
		} catch (ConfigurationException e) {
			// TODO: cambiar
			throw new NonPermissionRole(BundleInternationalization.getString("Exception.NonPermissionRole"));
		}
	    return result;
	}
	
//	public boolean isLogged () {
//		return session != null;
//	}

//	public ISession getSession(long sessionId) {
//		return SessionController.getSession(sessionId);
//	}

	public void setCurrentProject(long sessionId, int id) {
		SessionController.getSession(sessionId).setCurrentActiveProject(id);
		
	}

	public ArrayList<Language> getLanguages() throws ConfigurationException {
		return LanguagesController.getLanguages();
	}

	@Override
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProposal(long sessionId, Proposal p) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAnswer(long sessionId, Answer a) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
