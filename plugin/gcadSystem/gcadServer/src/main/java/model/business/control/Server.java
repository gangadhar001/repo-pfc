package model.business.control;

import internationalization.BundleInternationalization;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.business.knowledge.Answer;
import model.business.knowledge.IMessageTypeLog;
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
	public ISession login (String user, String pass) throws IncorrectEmployeeException, SQLException, NonExistentRole, RemoteException, Exception {
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
	
	public void signout(long sessionID) throws SQLException, NotLoggedException, RemoteException, Exception {
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
	
	public void register(long sessionID, IClient client) throws RemoteException, NotLoggedException, SQLException, Exception {
		String login;
		Session session;
		ClientProxy clientProxy;
			
		// Check if the session is valid
		session = SessionController.getSessions().get(sessionID);
		if(session == null) {
			throw new NotLoggedException();
		}
		try {
			clientProxy = new ClientProxy();
			clientProxy.associate(client);
			ClientsController.attach(sessionID, clientProxy);
			// TODO:
			//ClientsController.notifyConnection(true);
			login = SessionController.getSession(sessionID).getUser().getLogin();
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
	public void addTopic (long sessionId, Topic topic) throws SQLException, NonPermissionRole, NotLoggedException {
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
		KnowledgeController.addTopic(sessionId, session.getUser(), project , topic);
	}
	
	public void addProposal (long sessionId, Proposal p, Topic parent) throws SQLException, NonPermissionRole, NotLoggedException {
		Session session = SessionController.getSession(sessionId);
		KnowledgeController.addProposal(sessionId, session.getUser(), p, parent);
	}
	
	public void addAnwser (long sessionId, Answer a, Proposal parent) throws SQLException, NonPermissionRole, NotLoggedException {
		Session session = SessionController.getSession(sessionId);
		KnowledgeController.addAnswer(sessionId, session.getUser(), a, parent);
	}
	
	/*** Methods used to modify Knowledge ***/
	public void modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws SQLException, NonPermissionRole, NotLoggedException {
		Session session = SessionController.getSession(sessionId);
		KnowledgeController.modifyTopic(sessionId, session.getUser(), newTopic, oldTopic);		
	}
	
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws SQLException, NonPermissionRole, InstantiationException, IllegalAccessException, ClassNotFoundException, NotLoggedException {
		Session session = SessionController.getSession(sessionId);
		KnowledgeController.modifyProposal(sessionId, session.getUser(), newProposal, oldProposal, parent);		
	}
	
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws SQLException, NonPermissionRole, InstantiationException, IllegalAccessException, ClassNotFoundException, NotLoggedException {
		Session session = SessionController.getSession(sessionId);
		KnowledgeController.modifyAnswer(sessionId, session.getUser(), newAnswer, oldAnswer, parent);		
	}
		
	/*** Methods used to delete Knowledge 
	 * @throws NotLoggedException ***/
	@Override
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException {
		KnowledgeController.deleteTopic(sessionId, to);
		
	}

	@Override
	public void deleteProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException {
		KnowledgeController.deleteProposal(sessionId, p);
		
	}

	@Override
	public void deleteAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException {
		KnowledgeController.deleteAnswer(sessionId, a);
		
	}
	
	/*** Methods to manage projects ***/
	public void createProject (long sessionId, Project p) throws SQLException, NonPermissionRole, NotLoggedException {
		ProjectController.createProject(sessionId, p);
	}
	
	/*** Methods used to manage notifications ***/
//	public void addNotification(long sessionId, Notification notification) throws SQLException, NonPermissionRole {
//		NotificationController.addNotification(notification);
//	}
	
	public void removeNotification(long sessionId, Notification notification) throws SQLException, NonPermissionRole, NotLoggedException {
		NotificationController.deleteNotification(sessionId, notification);
	}

	public ArrayList<Notification> getNotifications(long sessionId) throws SQLException, NonPermissionRole, NotLoggedException {
		Session session = SessionController.getSession(sessionId);
		return NotificationController.getNotifications(session.getCurrentActiveProject(), 0);
	}
	
	/*** Auxiliary methods  ***/
	public ArrayList<Proposal> getProposals(long sessionId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		return KnowledgeController.getProposals(sessionId);
	}
	
	public ArrayList<Answer> getAnswers(long sessionId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		return KnowledgeController.getAnswers(sessionId);
	}
	
	public Proposal findParentAnswer(long sessionId, Answer a) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		return KnowledgeController.findParentAnswer(sessionId, a);
	}
	
	public Topic findParentProposal(long sessionId, Proposal p) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		return KnowledgeController.findParentProposal(sessionId, p);
	}		
	
//	public ArrayList<Project> getProjectsUser() {
//		// TODO: return los proyectos del usuario de la sesion
//		// return ProjectController.getProjectsUser(session.getUser().getId());
//		return null;
//	}	
	
	public TopicWrapper getTopicsWrapper(long sessionId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
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
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws NonPermissionRole, NotLoggedException {
		return SessionController.getAvailableOperations(sessionId);
	}
	
	public void setCurrentProject(long sessionId, int id) throws NotLoggedException {
		SessionController.getSession(sessionId).setCurrentActiveProject(id);
		
	}

	public ArrayList<Language> getLanguages() throws ConfigurationException {
		return LanguagesController.getLanguages();
	}

}
