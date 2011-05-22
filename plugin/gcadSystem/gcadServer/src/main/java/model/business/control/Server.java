package model.business.control;

import internationalization.AppInternationalization;

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
 */
public class Server implements IServer {

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
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("User_msg") + " '" + user + "' " +  AppInternationalization.getString("Logged_msg"));
		} catch(SQLException se) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("SQL_Login_msg") + " '" + user + "': " + se.getLocalizedMessage());
			throw se;
		} catch(IncorrectEmployeeException iee) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("IncorrectEmployee_msg") + " '" + user + "' " + AppInternationalization.getString("Login_msg") + iee.getLocalizedMessage());
			throw iee;
		} catch(NonExistentRole ner) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("Role_permissions_msg") + " '" + user + "': " + ner.getLocalizedMessage());
			throw ner;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("Exception_login") + " " + e.toString());
			throw e;
		}
		return session;
		
	}
	
	public void signout(long sessionID) throws RemoteException, SQLException, NotLoggedException, Exception {
		String login = "";
		try {
			if(SessionController.getSession(sessionID) != null)
				login = SessionController.getSession(sessionID).getUser().getLogin();
			SessionController.signout(sessionID);
			// Remove registered client
			ClientsController.detach(sessionID);
			DBConnectionManager.clear();
			LogManager.putMessage(IMessageTypeLog.INFO, AppInternationalization.getString("Signout_msg") + " " + sessionID );
			LogManager.putMessage(IMessageTypeLog.INFO, AppInternationalization.getString("User_msg") + " '" + login + "' " + AppInternationalization.getString("Logout_msg"));
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(SQLException se) {
			LogManager.putMessage(IMessageTypeLog.INFO, AppInternationalization.getString("SQL_Logout_msg") + " '" + login + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionID + " " + AppInternationalization.getString("NotLogged_operation") + nte.getLocalizedMessage());
			throw nte;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.INFO, AppInternationalization.getString("Exception_signout") + " " + e.toString());
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
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Register_msg") + " " + sessionID);
			LogManager.updateConnectedClients(ClientsController.getClients());
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionID + " " + AppInternationalization.getString("NotLogged_Register_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.INFO, AppInternationalization.getString("Exception_register") + " " + e.toString());
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
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewTopic_msg") + " " + topic.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_newTopic_msg") + " '" + topic.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewTopic_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewTopic_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_newTopic_msg") + " " + e.toString());
			throw e;
		}
	}
	
	public void addProposal (long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login ="";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.addProposal(sessionId, session.getUser(), p, parent);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewProposal_msg") + " " + p.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_newProposal_msg") + " '" + p.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewProposal_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewProposal_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_newProposal_msg") + " " + e.toString());
			throw e;
		}
	}
	
	public void addAnwser (long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try{
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.addAnswer(sessionId, session.getUser(), a, parent);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewAnswer_msg") + " " + a.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_newAnswer_msg") + " '" + a.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewAnswer_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewAnswer_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_newAnswer_msg") + " " + e.toString());
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
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("ModifyTopic_msg") + " " + oldTopic.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_ModifyTopic_msg") + " '" + oldTopic.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_ModifyTopic_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_ModifyTopic_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_ModifyTopic_msg") + " " + e.toString());
			throw e;
		}
	}
	
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login ="";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.modifyProposal(sessionId, session.getUser(), newProposal, oldProposal, parent);		
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("ModifyProposal_msg") + " " + oldProposal.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_ModifyProposal_msg") + " '" + oldProposal.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_ModifyProposal_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_ModifyProposal_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_ModifyProposal_msg") + " " + e.toString());
			throw e;
		}
	}
	
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.modifyAnswer(sessionId, session.getUser(), newAnswer, oldAnswer, parent);		
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("ModifyAnswer_msg") + " " + oldAnswer.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_ModifyAnswer_msg") + " '" + oldAnswer.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_ModifyAnswer_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_ModifyAnswer_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_ModifyAnswer_msg") + " " + e.toString());
			throw e;
		}
	}
		
	/*** Methods used to delete Knowledge ***/
	@Override
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			KnowledgeController.deleteTopic(sessionId, to);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("DeleteTopic_msg") + " " + to.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_DeleteTopic_msg") + " '" + to.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_DeleteTopic_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_DeleteTopic_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_DeleteTopic_msg") + " " + e.toString());
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
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("DeleteProposal_msg") + " " + p.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_DeleteProposal_msg") + " '" + p.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_DeleteProposal_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_DeleteProposal_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_DeleteProposal_msg") + " " + e.toString());
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
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("DeleteAnswer_msg") + " " + a.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_DeleteAnswer_msg") + " '" + a.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_DeleteAnswer_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_DeleteAnswer_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_DeleteAnswer_msg") + " " + e.toString());
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
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewProject_msg") + " " + p.getName());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_NewProject_msg") + " '" + p.getName() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewProject_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewProject_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_NewProject_msg") + " " + e.toString());
			throw e;
		}	
	}
	
	/*** Methods used to manage notifications ***/	
	public void removeNotification(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			NotificationController.deleteNotification(sessionId, notification);
			login = session.getUser().getLogin();
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("DeleteNotification_msg") + " " + notification.getKnowledge().getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("SQL_DeleteNotification_msg") + " '" + notification.getKnowledge().getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_DeleteNotification_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRole npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_DeleteNotification_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.INFO, AppInternationalization.getString("Exception_DeleteNotification_msg") + " " + e.toString());
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
	


}
