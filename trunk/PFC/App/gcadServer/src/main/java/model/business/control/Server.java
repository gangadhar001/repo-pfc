package model.business.control;

import internationalization.AppInternationalization;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.control.CBR.retrieveAlgorithms.EuclDistanceMethod;
import model.business.control.CBR.retrieveAlgorithms.NNMethod;
import model.business.knowledge.Answer;
import model.business.knowledge.IMessageTypeLog;
import model.business.knowledge.ISession;
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
import exceptions.NonExistentRoleException;
import exceptions.NonPermissionRoleException;
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
	public ISession login (String user, String pass) throws RemoteException, IncorrectEmployeeException, SQLException, NonExistentRoleException, Exception {
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
		} catch(NonExistentRoleException ner) {
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
	public void addTopic (long sessionId, Topic topic) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
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
	}
	
	public void addProposal (long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
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
	}
	
	public void addAnwser (long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
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
	}
	
	/*** Methods used to modify Knowledge ***/
	public void modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		String login = "";
		try {
			Session session = SessionController.getSession(sessionId);
			login = session.getUser().getLogin();
			KnowledgeController.modifyTopic(sessionId, session.getUser(), newTopic, oldTopic);		
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("ModifyTopic_msg") + " " + oldTopic.getTitle());
			ClientsController.notifyKnowledgeEdited(sessionId, newTopic, oldTopic);
		} catch(SQLException se) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("SQL_ModifyTopic_msg") + " '" + oldTopic.getTitle() + "': " + se.getLocalizedMessage());
			throw se;
		} catch(NotLoggedException nte) {
			LogManager.putMessage(IMessageTypeLog.UPDATE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_ModifyTopic_msg") + nte.getLocalizedMessage());
			throw nte;
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("NonPermission_ModifyTopic_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("Exception_ModifyTopic_msg") + " " + e.toString());
			throw e;
		}
	}
	
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
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
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("NonPermission_ModifyProposal_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("Exception_ModifyProposal_msg") + " " + e.toString());
			throw e;
		}
	}
	
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
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
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("NonPermission_ModifyAnswer_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.UPDATE, AppInternationalization.getString("Exception_ModifyAnswer_msg") + " " + e.toString());
			throw e;
		}
	}
		
	/*** Methods used to delete Knowledge ***/
	@Override
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
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
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("NonPermission_DeleteTopic_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("Exception_DeleteTopic_msg") + " " + e.toString());
			throw e;
		}		
	}

	@Override
	public void deleteProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
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
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("NonPermission_DeleteProposal_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("Exception_DeleteProposal_msg") + " " + e.toString());
			throw e;
		}	
	}

	@Override
	public void deleteAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
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
		} catch(NonPermissionRoleException npr) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("NonPermission_DeleteAnswer_msg") + " " + npr.getLocalizedMessage());
			throw npr;
		} catch(Exception e) {
			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("Exception_DeleteAnswer_msg") + " " + e.toString());
			throw e;
		}	
	}
	
	/*** Methods to manage projects ***/
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
	
	/*** Methods used to manage notifications ***/	
	// TODO:
//	public void addNotification(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
//		String login = "";
//		try {
//			Session session = SessionController.getSession(sessionId);
//			login = session.getUser().getLogin();
//			NotificationController.addNotification(sessionId, notification);
//			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("DeleteNotification_msg") + " " + notification.getKnowledge().getTitle());
//			ClientsController.notifiNotificationAvailable(sessionId, n);
//		} catch(SQLException se) {
//			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("SQL_DeleteNotification_msg") + " '" + notification.getKnowledge().getTitle() + "': " + se.getLocalizedMessage());
//			throw se;
//		} catch(NotLoggedException nte) {
//			LogManager.putMessage(IMessageTypeLog.DELETE, AppInternationalization.getString("NotLogged_msg") + " " + sessionId + " " + AppInternationalization.getString("NotLogged_DeleteNotification_msg") + nte.getLocalizedMessage());
//			throw nte;
//		} catch(NonPermissionRole npr) {
//			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("NonPermission_DeleteNotification_msg") + " " + npr.getLocalizedMessage());
//			throw npr;
//		} catch(Exception e) {
//			LogManager.putMessage(login, IMessageTypeLog.DELETE, AppInternationalization.getString("Exception_DeleteNotification_msg") + " " + e.toString());
//			throw e;
//		}			
//	}
	
	// TODO:
	public void removeNotification(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
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

	public ArrayList<Notification> getNotifications(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
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
	
	public List<Attribute> getAttributesFromProject(Project p) throws Exception {		
		List<Attribute> attributes;
		try {
			attributes = ProjectController.getAttributesFromProject(p);
			// TODO: mensajes
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("GetUsersProject_msg") + " " + p.getName());
		} catch(Exception e) {
			LogManager.putMessage(IMessageTypeLog.READ, AppInternationalization.getString("Exception_GetUsersProject_msg") + " " + p.getName() + ": " + e.toString());
			throw e;
		}		
		return attributes;
	}
	
	public void setCurrentProject(long sessionId, int id) throws RemoteException, NotLoggedException, Exception {
		SessionController.getSession(sessionId).setCurrentActiveProject(id);		
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
	
	/**
	 * This method reads the profiles available for a user role from a XML file.
	 */
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException, NonPermissionRoleException, NotLoggedException, Exception {
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
		
	public User getLoggedUser(long sessionId) throws RemoteException, NotLoggedException, Exception{
		return SessionController.getSession(sessionId).getUser();
	}

	@Override
	public List<Project> executeAlgorithm(EnumAlgorithmCBR algorithmName, List<Project> cases, Project caseToEval, ConfigCBR config, int k) throws RemoteException, Exception {
		List<Project> result = new ArrayList<Project>();
		switch(algorithmName) {
		case NN:
			result = NNMethod.evaluateSimilarity(caseToEval, cases, config, k);
			break;
		case Euclidean:
			result = EuclDistanceMethod.evaluateSimilarity(caseToEval, cases, config, k);
			break;
//		case Sim:
//			result = SimMethod.evaluateSimilarity(caseToEval, cases, config, k);
//			break;
		}
		return result;
	}
	
	@Override
	public List<User> getUsers(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return UsersController.getUsers(sessionId);
	}
	
	@Override
	public void addProjectsUser(long sessionId, User user, Project project) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		UsersController.addProjectsUser(sessionId, user, project);
	}
	
}
