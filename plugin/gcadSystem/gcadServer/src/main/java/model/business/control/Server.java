package model.business.control;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.business.knowledge.Answer;
import model.business.knowledge.IMessageTypeLog;
import model.business.knowledge.ISession;
import model.business.knowledge.Language;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Session;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;

import org.apache.commons.configuration.ConfigurationException;

import communication.ClientProxy;
import communication.DBConnectionManager;
import communication.IClient;
import communication.IServer;

import exceptions.IncorrectEmployeeException;
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
	public ISession login (String user, String pass) throws RemoteException, IncorrectEmployeeException, SQLException, NonExistentRole, Exception {
		ISession session;
		try {
			session = SessionController.login(user, pass);
			LogManager.putMessage(IMessageTypeLog.READ, "User '" + user + "' logged.");
		} catch(SQLException se) {
			LogManager.putMessage(IMessageTypeLog.READ, "Error SQL mientras se autenticaba el usuario '" + user + "': " + se.getLocalizedMessage());
			throw se;
		} catch(IncorrectEmployeeException iee) {
			LogManager.putMessage(IMessageTypeLog.READ, "Error al recuperar el usuario '" + user + "' que se estaba autenticando: " + iee.getLocalizedMessage());
			throw iee;
		} catch(NonExistentRole ner) {
			LogManager.putMessage(IMessageTypeLog.READ, "Error al recuperar los permisos del usuario '" + user + "': " + ner.getLocalizedMessage());
			throw ner;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.READ, "Error inesperado mientras se autenticaba un usuario: " + e.toString());
			throw e;
		}
		return session;
		
	}
	
	public void signout(long sessionID) throws RemoteException, SQLException, NotLoggedException, Exception {
		String login = "";
		try {
			// Liberamos la sesión del cliente
			if(SessionController.getSession(sessionID) != null)
				login = SessionController.getSession(sessionID).getUser().getLogin();
			SessionController.signout(sessionID);
			// Remove registered client
			ClientsController.detach(sessionID);
			DBConnectionManager.clear();
			LogManager.putMessage(IMessageTypeLog.INFO, "Liberado el cliente con id de sesión " + sessionID + ".");
			LogManager.putMessage(IMessageTypeLog.INFO, "Usuario '" + login + "' desconectado.");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(IMessageTypeLog.INFO, "Error SQL mientras se desconectaba el usuario '" + login + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.INFO, "Error al comprobar la sesión con id " + sessionID + " para liberar un cliente en el sistema: " + nte.getLocalizedMessage());
			throw nte;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.INFO, "Error inesperado mientras se liberaba un cliente en el sistema: " + e.toString());
			throw e;
		}
	}
	
	public void disconnectClients() throws RemoteException {
		ClientsController.disconnectClients();
		SessionController.disconnectClients();
	}
	
	public void register(long sessionID, IClient client) throws RemoteException, NotLoggedException, Exception {
		String login;
		Session session;
		ClientProxy clientProxy;
			
		try {
			// Check if the session is valid
			session = SessionController.getSessions().get(sessionID);
			if(session == null) {
				throw new NotLoggedException();
			}
			clientProxy = new ClientProxy();
			clientProxy.associate(client);
			ClientsController.attach(sessionID, clientProxy);
			// TODO:
			//ClientsController.notifyConnection(true);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.INFO, "Registrado el cliente con id de sesión " + sessionID + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.INFO, "Error al comprobar la sesión con id " + sessionID + " para registrar un cliente en el sistema: " + nte.getLocalizedMessage());
			throw nte;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.INFO, "Error inesperado mientras se registraba un cliente en el sistema: " + e.toString());
			throw e;
		}
	}
	
	/*** Methods used to add new Knowledge  ***/
	public void addTopic (long sessionId, Topic topic) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		boolean found = false;
		Project project = null;
		String login = "";
		try{
			Session session = SessionController.getSession(sessionId);
			// Search the current project
			for (Iterator<Project> i = session.getUser().getProjects().iterator(); i.hasNext() && !found; ) {
				project = (Project)i.next();
				if (project.getId() == session.getCurrentActiveProject()) {
					found = true;
				}		
			}	
			KnowledgeController.addTopic(sessionId, session.getUser(), project , topic);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Añadido un nuevo Topic con título " + topic.getTitle() + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Error SQL mientras se intentaba añadir el Topic con título " + topic.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, "Error al comprobar la sesión con id " + sessionId + " para añadir un nuevo topic: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Error al intentar realizar una operación no permitida al añadir un nuevo Topic: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.CREATE, "Error inesperado mientras se intentaba añadir un nuevo Topic: " + e.toString());
			throw e;
		}
	}
	
	public void addProposal (long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login ="";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.addProposal(sessionId, session.getUser(), p, parent);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Añadida una nueva Proposal con título " + p.getTitle() + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Error SQL mientras se intentaba añadir la Proposal con título " + p.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, "Error al comprobar la sesión con id " + sessionId + " para añadir una nueva Proposal: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Error al intentar realizar una operación no permitida al añadir una nueva Proposal: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.CREATE, "Error inesperado mientras se intentaba añadir una nueva Proposal: " + e.toString());
			throw e;
		}
	}
	
	public void addAnwser (long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try{
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.addAnswer(sessionId, session.getUser(), a, parent);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Añadida una nueva Answer con título " + a.getTitle() + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Error SQL mientras se intentaba añadir la Answer con título " + a.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, "Error al comprobar la sesión con id " + sessionId + " para añadir una nueva Answer: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Error al intentar realizar una operación no permitida al añadir una nueva Answer: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.CREATE, "Error inesperado mientras se intentaba añadir una nueva Answer: " + e.toString());
			throw e;
		}
	}
	
	/*** Methods used to modify Knowledge ***/
	public void modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.modifyTopic(sessionId, session.getUser(), newTopic, oldTopic);		
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, "Modificado el Topic con título " + oldTopic.getTitle() + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, "Error SQL mientras se intentaba modificar el Topic con título " + oldTopic.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, "Error al comprobar la sesión con id " + sessionId + " para modificar un Topic: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, "Error al intentar realizar una operación no permitida al modificar un Topic: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, "Error inesperado mientras se intentaba modificar un Topic: " + e.toString());
			throw e;
		}
	}
	
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login ="";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.modifyProposal(sessionId, session.getUser(), newProposal, oldProposal, parent);		
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, "Modificada la Proposal con título " + oldProposal.getTitle() + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, "Error SQL mientras se intentaba modificar la Proposal con título " + oldProposal.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, "Error al comprobar la sesión con id " + sessionId + " para modificar una Proposal: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, "Error al intentar realizar una operación no permitida al modificar una Proposal: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, "Error inesperado mientras se intentaba modificar una Proposal: " + e.toString());
			throw e;
		}
	}
	
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.modifyAnswer(sessionId, session.getUser(), newAnswer, oldAnswer, parent);		
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, "Modificado la Answer con título " + oldAnswer.getTitle() + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, "Error SQL mientras se intentaba modificar la Answer con título " + oldAnswer.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, "Error al comprobar la sesión con id " + sessionId + " para modificar una Answer: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, "Error al intentar realizar una operación no permitida al modificar una Answer: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, "Error inesperado mientras se intentaba modificar una Answer: " + e.toString());
			throw e;
		}
	}
		
	/*** Methods used to delete Knowledge 
	 * @throws NotLoggedException ***/
	@Override
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.deleteTopic(sessionId, to);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Eliminado el Topic con título " + to.getTitle() + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Error SQL mientras se intentaba eliminar el Topic con título " + to.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.DELETE, "Error al comprobar la sesión con id " + sessionId + " para eliminar un topic: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Error al intentar realizar una operación no permitida al eliminar un Topic: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.DELETE, "Error inesperado mientras se intentaba eliminar un Topic: " + e.toString());
			throw e;
		}
		
	}

	@Override
	public void deleteProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.deleteProposal(sessionId, p);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Eliminado la Proposal con título " + p.getTitle() + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Error SQL mientras se intentaba eliminar la Proposal con título " + p.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.DELETE, "Error al comprobar la sesión con id " + sessionId + " para eliminar una Proposal: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Error al intentar realizar una operación no permitida al eliminar una Proposal: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.DELETE, "Error inesperado mientras se intentaba eliminar una Proposal: " + e.toString());
			throw e;
		}
	}

	@Override
	public void deleteAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.deleteAnswer(sessionId, a);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Eliminado la Answer con título " + a.getTitle() + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Error SQL mientras se intentaba eliminar la Answer con título " + a.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.DELETE, "Error al comprobar la sesión con id " + sessionId + " para eliminar una Answer: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Error al intentar realizar una operación no permitida al eliminar una Answer: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.DELETE, "Error inesperado mientras se intentaba eliminar una Answer: " + e.toString());
			throw e;
		}		
	}
	
	/*** Methods to manage projects ***/
	public void createProject (long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			ProjectController.createProject(sessionId, p);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Creado el proyecto con título " + p.getName() + ".");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Error SQL mientras se intentaba crear el proyecto con título " + p.getName() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, "Error al comprobar la sesión con id " + sessionId + " para crear un proyecto: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, "Error al intentar realizar una operación no permitida al crear un proyecto: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.CREATE, "Error inesperado mientras se intentaba crear un proyecto: " + e.toString());
			throw e;
		}	
	}
	
	/*** Methods used to manage notifications ***/
//	public void addNotification(long sessionId, Notification notification) throws SQLException, NonPermissionRole {
//		NotificationController.addNotification(notification);
//	}
	
	public void removeNotification(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			NotificationController.deleteNotification(sessionId, notification);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Eliminado la notificación con título .");
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Error SQL mientras se intentaba eliminar la notificación con título: " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.DELETE, "Error al comprobar la sesión con id " + sessionId + " para eliminar una notificación: " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, "Error al intentar realizar una operación no permitida al eliminar una notificación: " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.DELETE, "Error inesperado mientras se intentaba eliminar una notificación: " + e.toString());
			throw e;
		}	
		
	}

	public ArrayList<Notification> getNotifications(long sessionId) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		Session session = SessionController.getSession(sessionId);
		return NotificationController.getNotifications(sessionId, session.getCurrentActiveProject());
	}
	
	/*** Auxiliary methods  ***/
	public ArrayList<Proposal> getProposals(long sessionId) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		return KnowledgeController.getProposals(sessionId);
	}
	
	public ArrayList<Answer> getAnswers(long sessionId) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		return KnowledgeController.getAnswers(sessionId);
	}
	
	public Proposal findParentAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		return KnowledgeController.findParentAnswer(sessionId, a);
	}
	
	public Topic findParentProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		return KnowledgeController.findParentProposal(sessionId, p);
	}		
	
//	public ArrayList<Project> getProjectsUser() {
//		// TODO: return los proyectos del usuario de la sesion
//		// return ProjectController.getProjectsUser(session.getUser().getId());
//		return null;
//	}	
	
	public TopicWrapper getTopicsWrapper(long sessionId) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		return KnowledgeController.getTopicsWrapper(sessionId);
	}
	
	public TopicWrapper getTopicsWrapper(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		return KnowledgeController.getTopicsWrapper(sessionId, p);
	}
	
	public List<Project> getProjects(long sessionId) throws RemoteException, NonPermissionRole, NotLoggedException, SQLException, Exception{
		return ProjectController.getProjects(sessionId);
	}
	
	public List<User> getUsersProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		return ProjectController.getUsersProject(sessionId, p);
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
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException, NonPermissionRole, NotLoggedException {
		return SessionController.getAvailableOperations(sessionId);
	}
	
	public void setCurrentProject(long sessionId, int id) throws RemoteException, NotLoggedException, Exception {
		SessionController.getSession(sessionId).setCurrentActiveProject(id);
		
	}

	public ArrayList<Language> getLanguages() throws ConfigurationException {
		return LanguagesController.getLanguages();
	}

	@Override
	public List<Project> getProjectsFromCurrentUser(long sessionId) throws RemoteException, NotLoggedException, Exception {
		List<Project> result = new ArrayList<Project>();
		Set<Project> projects = SessionController.getSessions().get(sessionId).getUser().getProjects();
		for (Project p : projects)
			result.add(p);
		return result;
	}

}
