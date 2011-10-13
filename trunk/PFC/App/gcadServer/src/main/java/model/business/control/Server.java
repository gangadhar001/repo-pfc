package model.business.control;

import internationalization.AppInternationalization;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jdom.JDOMException;

import resources.Language;
import resources.LanguagesUtilities;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.Coordinates;
import model.business.knowledge.File;
import model.business.knowledge.IMessageTypeLog;
import model.business.knowledge.ISession;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Session;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;

import communication.ClientProxy;
import communication.IClient;
import communication.IServer;

import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentAddressException;
import exceptions.NonExistentAnswerException;
import exceptions.NonExistentFileException;
import exceptions.NonExistentNotificationException;
import exceptions.NonExistentProposalException;
import exceptions.NonExistentTopicException;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;
import exceptions.WSResponseException;

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
	
	/*** Methods used in server ***/
	public Language getDefaultLanguage() throws RemoteException, JDOMException, IOException {
		return LanguagesUtilities.getDefaultLanguage();
	}

	public ArrayList<Language> getLanguages() throws RemoteException, JDOMException, IOException {
		return LanguagesUtilities.getLanguages();
	}

	public void setDefaultLanguage(Language language) throws RemoteException, JDOMException, IOException {
		LanguagesUtilities.setDefaultLanguage(language);
		
	}
	
	/*** Methods used to manage login and signout ***/
	@Override
	public ISession login (String user, String pass) throws RemoteException, IncorrectEmployeeException, SQLException, Exception {
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
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("Exception_login") + " " + e.toString());
			throw e;
		}
		return session;
		
	}
	
	@Override
	public void signout(long sessionID) throws RemoteException, SQLException, NotLoggedException, Exception {
		String login = "";
		try {
			if(SessionController.getSession(sessionID) != null)
				login = SessionController.getSession(sessionID).getUser().getLogin();
			SessionController.signout(sessionID);
			// Remove registered client
			ClientsController.detach(sessionID);
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
	
	@Override
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
			// Register client
			clientProxy.associate(client);
			ClientsController.attach(sessionID, clientProxy);
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
	@Override
	public Topic addTopic (long sessionId, Topic topic) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		boolean found = false;
		Project project = null, aux;
		String login = "";
		try{
			Session session = SessionController.getSession(sessionId);
			// Search the current project
			for (Iterator<Project> i = session.getUser().getProjects().iterator(); i.hasNext() && !found; ) {
				aux = i.next();
				if (aux.getId() == session.getCurrentActiveProject()) {
					found = true;
					project = aux;
				}		
			}	
			login = session.getUser().getLogin();
			KnowledgeController.addTopic(sessionId, session.getUser(), project , topic);
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewTopic_msg") + " " + topic.getTitle());
			ClientsController.notifyKnowledgeAdded(sessionId, topic);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("SQL_newTopic_msg") + " '" + topic.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewTopic_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewTopic_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("Exception_newTopic_msg") + " " + e.toString());
			throw e;
		}
		// Return the new topic added
		return topic;
	}
	
	@Override
	public Proposal addProposal (long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login ="";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			KnowledgeController.addProposal(sessionId, session.getUser(), p, parent);
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewProposal_msg") + " " + p.getTitle());
			ClientsController.notifyKnowledgeAdded(sessionId, p);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("SQL_newProposal_msg") + " '" + p.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewProposal_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewProposal_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("Exception_newProposal_msg") + " " + e.toString());
			throw e;
		}
		// Return the new topic added
		return p;
	}
	
	@Override
	public Answer addAnswer (long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		try{
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			KnowledgeController.addAnswer(sessionId, session.getUser(), a, parent);
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewAnswer_msg") + " " + a.getTitle());
			ClientsController.notifyKnowledgeAdded(sessionId, a);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("SQL_newAnswer_msg") + " '" + a.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewAnswer_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewAnswer_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("Exception_newAnswer_msg") + " " + e.toString());
			throw e;
		}
		return a;
	}
	
	/*** Methods used to modify Knowledge ***/
	@Override
	public Topic modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException, NonPermissionRoleException, NonExistentTopicException, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			KnowledgeController.modifyTopic(sessionId, session.getUser(), newTopic);		
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("ModifyTopic_msg") + " " + oldTopic.getTitle());
			ClientsController.notifyKnowledgeEdited(sessionId, newTopic, oldTopic);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("SQL_ModifyTopic_msg") + " '" + oldTopic.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_ModifyTopic_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonExistentTopicException net) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NonExistentTopic_msg"));
			throw net;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("NonPermission_ModifyTopic_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("Exception_ModifyTopic_msg") + " " + e.toString());
			throw e;
		}
		return newTopic;
	}
	
	@Override
	public Proposal modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException, NonExistentProposalException, NonPermissionRoleException, NotLoggedException, Exception {
		String login ="";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			KnowledgeController.modifyProposal(sessionId, session.getUser(), newProposal, oldProposal, parent);					
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("ModifyProposal_msg") + " " + oldProposal.getTitle());
			ClientsController.notifyKnowledgeEdited(sessionId, newProposal, oldProposal);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("SQL_ModifyProposal_msg") + " '" + oldProposal.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_ModifyProposal_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonExistentProposalException net) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NonExistentProposal_msg"));
			throw net;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("NonPermission_ModifyProposal_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("Exception_ModifyProposal_msg") + " " + e.toString());
			throw e;
		}
		return newProposal;
	}
	
	@Override
	public Answer modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException, NonExistentAnswerException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			KnowledgeController.modifyAnswer(sessionId, session.getUser(), newAnswer, oldAnswer, parent);		
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("ModifyAnswer_msg") + " " + oldAnswer.getTitle());
			ClientsController.notifyKnowledgeEdited(sessionId, newAnswer, oldAnswer);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("SQL_ModifyAnswer_msg") + " '" + oldAnswer.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_ModifyAnswer_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonExistentAnswerException net) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NonExistentAnswer_msg"));
			throw net;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("NonPermission_ModifyAnswer_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("Exception_ModifyAnswer_msg") + " " + e.toString());
			throw e;
		}
		return newAnswer;
	}
		
	/*** Methods used to delete Knowledge ***/
	@Override
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, NonExistentTopicException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			KnowledgeController.deleteTopic(sessionId, to);
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("DeleteTopic_msg") + " " + to.getTitle());
			ClientsController.notifyKnowledgeRemoved(sessionId, to);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("SQL_DeleteTopic_msg") + " '" + to.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.DELETE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_DeleteTopic_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonExistentTopicException net) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NonExistentTopic_msg"));
			throw net;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("NonPermission_DeleteTopic_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("Exception_DeleteTopic_msg") + " " + e.toString());
			throw e;
		}		
	}

	@Override
	public void deleteProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, NonExistentProposalException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			KnowledgeController.deleteProposal(sessionId, p);
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("DeleteProposal_msg") + " " + p.getTitle());
			ClientsController.notifyKnowledgeRemoved(sessionId, p);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("SQL_DeleteProposal_msg") + " '" + p.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.DELETE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_DeleteProposal_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonExistentProposalException net) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NonExistentProposal_msg"));
			throw net;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("NonPermission_DeleteProposal_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("Exception_DeleteProposal_msg") + " " + e.toString());
			throw e;
		}	
	}

	@Override
	public void deleteAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, NonExistentAnswerException, Exception {
		String login = "";
		try {
			
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			KnowledgeController.deleteAnswer(sessionId, a);
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("DeleteAnswer_msg") + " " + a.getTitle());
			ClientsController.notifyKnowledgeRemoved(sessionId, a);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("SQL_DeleteAnswer_msg") + " '" + a.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.DELETE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_DeleteAnswer_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonExistentAnswerException net) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NonExistentAnswer_msg"));
			throw net;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("NonPermission_DeleteAnswer_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("Exception_DeleteAnswer_msg") + " " + e.toString());
			throw e;
		}	
	}
	
	/*** Methods to manage projects ***/
	@Override
	public Project createProject (long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		Project result;
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			result = ProjectController.createProject(sessionId, p);
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewProject_msg") + " " + p.getName());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("SQL_NewProject_msg") + " '" + p.getName() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewProject_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewProject_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("Exception_NewProject_msg") + " " + e.toString());
			throw e;
		}
		return result;
	}
	
	@Override
	public void addProjectsUser(long sessionId, User user, Project project) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			UsersController.addProjectsUser(sessionId, user, project);
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("AddProjectsUser_msg") + " " + project.getName());		
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("SQL_AddProjectsUser_msg") + " " + project.getName() );
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_AddProjectsUser_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("NonPermission_AddProjectsUser_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("Exception_AddProjectsUser_msg") + " " + e.toString());
			throw e;
		}
	}
	
	@Override
	public List<Project> getProjects(long sessionId) throws RemoteException, NonPermissionRoleException, NotLoggedException, SQLException, Exception{
		String login = "";
		List<Project> projects;
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			projects = ProjectController.getProjects(sessionId);
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetProjects_msg"));
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_GetProjects_msg")  + " " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetProjects_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_GetProjects_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetProjects_msg") + " " + e.toString());
			throw e;
		}		
		return projects;
	}
	
	@Override
	public List<User> getUsersProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		List<User> users;
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			users = ProjectController.getUsersProject(sessionId, p);
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetUsersProject_msg") + " " + p.getName());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_GetUsersProject_msg")  + " " + p.getName() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetUsersProject_msg") + " " + p.getName() + ": " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_GetUsersProject_msg") +  " " + p.getName() + ": " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetUsersProject_msg") + " " + p.getName() + ": " + e.toString());
			throw e;
		}		
		return users;
	}
	
	@Override
	public List<Project> getProjectsFromCurrentUser(long sessionId) throws RemoteException, NotLoggedException, Exception {
		String login = "";
		List<Project> result = new ArrayList<Project>();
		User u = null;
		try {
			Session session = SessionController.getSession(sessionId);
			u = session.getUser();
			Set<Project> projects = u.getProjects();
			for (Project p : projects)
				result.add(p);
			login = u.getLogin();
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetProjectsUser_msg") + " " + u.getName());
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetProjectsUser_msg") + ": " + nte.getLocalizedMessage());
			throw nte;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetProjectsUser_msg") + " " + u.getName() + ": " + e.toString());
			throw e;
		}		
		return result;
	}
	
	/*** Methods used to manage notifications ***/		
	@Override
	public ArrayList<Notification> getNotificationsUser(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		ArrayList<Notification> notifications;
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			notifications = NotificationController.getNotificationsUser(sessionId, session.getUser().getId(), session.getCurrentActiveProject());
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetNotificationsUser_msg"));
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_GetNotificationsUser_msg") + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetNotificationsUser_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_GetNotificationsUser_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetNotificationsUser_msg") + " " + e.toString());
			throw e;
		}			
		return notifications;
	}

	@Override
	public void createNotification(long sessionId, Notification n) throws SQLException, NonPermissionRoleException, NotLoggedException, RemoteException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			NotificationController.insertNotification(sessionId, n);
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewNotification_msg") + " " + n.getKnowledge().getTitle());
			ClientsController.notifyNotificationAvailable(n);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("SQL_NewNotification_msg") + " '" + n.getKnowledge().getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewNotification_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewNotification_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("Exception_NewNotification_msg") + " " + e.toString());
			throw e;
		}		
	}
	
	@Override
	public void modifyNotification(long sessionId, Notification not) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, NonExistentNotificationException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			NotificationController.update(sessionId, not);		
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("ModifyNotification_msg") + " " + not.getKnowledge().getTitle());			
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("SQL_ModifyNotification_msg") + " '" + not.getKnowledge().getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_ModifyNotification_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonExistentNotificationException net) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NonExistentNotification_msg"));
			throw net;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("NonPermission_ModifyNotification_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("Exception_ModifyNotification_msg") + " " + e.toString());
			throw e;
		}
	}
	
	@Override
	public void modifyNotificationState(long sessionId, Notification not) throws RemoteException, NotLoggedException, SQLException, NonPermissionRoleException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			NotificationController.updateState(sessionId, session.getUser().getId(), not);
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("ModifyNotification_msg") + " " + not.getKnowledge().getTitle());			
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("SQL_ModifyNotification_msg") + " '" + not.getKnowledge().getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_ModifyNotification_msg") + nte.getLocalizedMessage());
			throw nte;		
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("NonPermission_ModifyNotification_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("Exception_ModifyNotification_msg") + " " + e.toString());
			throw e;
		}
	}

	@Override
	public void deleteNotification(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			NotificationController.deleteNotification(sessionId, notification);
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("DeleteNotification_msg") + " " + notification.getKnowledge().getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("SQL_DeleteNotification_msg") + " '" + notification.getKnowledge().getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.DELETE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_DeleteNotification_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("NonPermission_DeleteNotification_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("Exception_DeleteNotification_msg") + " " + e.toString());
			throw e;
		}			
	}
	
	@Override
	public void deleteNotificationFromUser(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			NotificationController.deleteNotificationFromUser(sessionId, session.getUser(), notification);
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("DeleteNotification_msg") + " " + notification.getKnowledge().getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("SQL_DeleteNotification_msg") + " '" + notification.getKnowledge().getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.DELETE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_DeleteNotification_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("NonPermission_DeleteNotification_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("Exception_DeleteNotification_msg") + " " + e.toString());
			throw e;
		}			
	}

	@Override
	public ArrayList<Notification> getNotificationsProject(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		ArrayList<Notification> notifications;
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			notifications = NotificationController.getNotificationsProject(sessionId, session.getCurrentActiveProject());
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetNotifications_msg"));
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_GetNotifications_msg") + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetNotifications_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_GetNotifications_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetNotifications_msg") + " " + e.toString());
			throw e;
		}			
		return notifications;
	}
	
	/*** Auxiliary methods  ***/
	@Override
	public ArrayList<Proposal> getProposals(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		ArrayList<Proposal> proposals;
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			proposals = KnowledgeController.getProposals(sessionId);
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetProposals_msg"));
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_GetProposals_msg")  + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetProposals_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_GetProposals_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetProposals_msg") + " " + e.toString());
			throw e;
		}		
		return proposals;
	}
	
	@Override
	public ArrayList<Answer> getAnswers(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		ArrayList<Answer> answers;
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			answers = KnowledgeController.getAnswers(sessionId);
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetAnswers_msg"));
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_GetAnswers_msg")  + " " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetAnswers_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_GetAnswers_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetAnswers_msg") + " " + e.toString());
			throw e;
		}		
		return answers;
	}
	
	@Override
	public Proposal findParentAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		Proposal proposal;
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			proposal = KnowledgeController.findParentAnswer(sessionId, a);
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("FindParentAnswer_msg") + " " + a.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_FindParentAnswer_msg") + " " + a.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_FindParentAnswer_msg") + " " + a.getTitle() + ": " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_FindParentAnswer_msg") +  " " + a.getTitle() + ": " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_FindParentAnswer_msg") + " " + a.getTitle() + ": " + e.toString());
			throw e;
		}		
		return proposal;
	}
	
	@Override
	public Topic findParentProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		Topic topic;
		try {
			Session session = SessionController.getSession(sessionId);	
			login = session.getUser().getLogin();
			topic =  KnowledgeController.findParentProposal(sessionId, p);			
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("FindParentProposal_msg") + " " + p.getTitle());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_FindParentProposal_msg") + " " + p.getTitle() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_FindParentProposal_msg") + " " + p.getTitle() + ": " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_FindParentProposal_msg") +  " " + p.getTitle() + ": " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_FindParentProposal_msg") + " " + p.getTitle() + ": " + e.toString());
			throw e;
		}		
		return topic;
	}		
		
	@Override
	public TopicWrapper getTopicsWrapper(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		TopicWrapper tw;
		try {
			Session session = SessionController.getSession(sessionId);	
			login = session.getUser().getLogin();
			tw = KnowledgeController.getTopicsWrapper(sessionId);
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetTW_msg"));
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_GetTW_msg")  + " " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetTW_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_GetTW_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetTW_msg") + " " + e.toString());
			throw e;
		}		
		return tw;
	}
	
	@Override
	public TopicWrapper getTopicsWrapper(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		TopicWrapper tw;
		try {
			Session session = SessionController.getSession(sessionId);	
			login = session.getUser().getLogin();
			tw = KnowledgeController.getTopicsWrapper(sessionId, p);
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetTWProject_msg") + " " + p.getName());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_GetTWProject_msg") + " " + p.getName() + ": " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetTWProject_msg") + " " + p.getName() + ": " + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_GetTWProject_msg") +  " " + p.getName() + ": " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetTWProject_msg") + " " + p.getName() + ": " + e.toString());
			throw e;
		}		
		return tw;
	}
	
	@Override
	public void setCurrentProject(long sessionId, int id) throws RemoteException, NotLoggedException, Exception {
		SessionController.getSession(sessionId).setCurrentActiveProject(id);		
	}	
	
	/**
	 * This method reads the profiles available for a user role from a XML file.
	 */
	@Override
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException, NotLoggedException, Exception {
		String login = "";
		ArrayList<Operation> operations;
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			operations = SessionController.getAvailableOperations(sessionId);
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetOperations_msg"));
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetOperations_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_GetOperations_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetOperations_msg") + " " + e.toString());
			throw e;
		}		
		return operations;
	}
		
	@Override
	public User getLoggedUser(long sessionId) throws RemoteException, NotLoggedException, Exception{
		String login = "";
		User user = null;
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			user = SessionController.getSession(sessionId).getUser();
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetLoggedUser_msg") + " ");		
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetLoggedUser_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetLoggedUser_msg") + " " + e.toString());
			throw e;
		}		
		return user;
	}
	
	@Override
	public List<User> getUsers(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		List<User> users = new ArrayList<User>();
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			users = UsersController.getUsers(sessionId);
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("GetUsers_msg") + " ");		
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("SQL_GetUsers_msg") + " '" );
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_GetUsers_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonPermission_GetUsers_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetUsers_msg") + " " + e.toString());
			throw e;
		}		
		
		return users;
	}
	
	@Override
	public Coordinates getCoordinates(long sessionId, Address add) throws NonExistentAddressException, WSResponseException, Exception {
		Coordinates coor = null;			
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			coor = GeoCoder.getGeoCoordinates(add);	
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("DeleteNotification_msg") + " " + add.toString());
		} catch(NonExistentAddressException nea) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonExistent_GetCoordinates_msg") + " " + add.toString());
			throw nea;
		} catch(WSResponseException wsre) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("WSResponse_GetCoordinates_msg") + " " + add.toString());
			throw wsre;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetCoordinates_msg") + " ");
			throw e;
		}
		return coor;
	}

	/*** Methods used in CBR ***/
	@Override
	public List<Attribute> getAttributesFromProject(Project p) throws Exception {		
		List<Attribute> attributes;
		try {
			attributes = ProjectController.getAttributesFromProject(p);
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("GetAttributes_msg") + " ");
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetAttributes_msg") + " : " + e.toString());
			throw e;
		}		
		return attributes;
	}
	
	@Override
	public List<Project> executeAlgorithm(long sessionId, EnumAlgorithmCBR algorithmName, List<Project> cases, Project caseToEval, ConfigCBR config, int k) throws NonPermissionRoleException, NotLoggedException, Exception {
		// TODO: mensajes
		List<Project> projects = new ArrayList<Project>();			
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			projects = CBRController.executeAlgorithm(sessionId, algorithmName, cases, caseToEval, config, k);	
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("DeleteNotification_msg") + " " );
		} catch(NonPermissionRoleException nea) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("NonExistent_GetCoordinates_msg") + " " );
			throw nea;
		} catch(NotLoggedException wsre) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("WSResponse_GetCoordinates_msg") + " " );
			throw wsre;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetCoordinates_msg") + " ");
			throw e;
		}
		return projects;		
	}	
	
	/*** Methods used to manage Files ***/
	public int attachFile(long sessionId, File file) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		// TODO: mensajes
		String login = "";
		int id = -1;
		try{
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			id = KnowledgeController.attachFile(sessionId, file);
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewAnswer_msg") + " " + file.getFileName());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("SQL_newAnswer_msg") + " '" + file.getFileName() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewAnswer_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewAnswer_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("Exception_newAnswer_msg") + " " + e.toString());
			throw e;
		}
		return id;
	}
	
	public List<File> getAttachedFiles(long sessionId, Knowledge k) throws RemoteException, SQLException, NonExistentFileException, NonPermissionRoleException, NotLoggedException, Exception {
		// TODO: mensajes
		String login = "";
		List<File> files = new ArrayList<File>();
		try{
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			files = KnowledgeController.getAttachedFiles(sessionId, k);
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NewAnswer_msg") + " " + k.getId());
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("SQL_newAnswer_msg") + " '" + k.getId() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.CREATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_NewAnswer_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("NonPermission_NewAnswer_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.CREATE, AppInternationalization.getString("Exception_newAnswer_msg") + " " + e.toString());
			throw e;
		}
		return files;
	}
}
