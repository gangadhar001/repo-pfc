package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import model.business.knowledge.Answer;
import model.business.knowledge.ISession;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

import org.apache.commons.configuration.ConfigurationException;

import exceptions.IncorrectEmployeeException;
import exceptions.NoProposalsException;
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

/**
 * Remote interface that is used by the client applications, in order to request operations to the server.
 * This is the server Facade
 *
 */
public interface IServer extends Remote {
	
	public static final String NAME_SERVER = "gcadServer";

	/*** Methods used to manage login and signout ***/
	public ISession login (String user, String pass) throws RemoteException, IncorrectEmployeeException, SQLException, NonExistentRole, Exception;
	
	public void signout(long sessionID) throws RemoteException, SQLException, NotLoggedException, Exception ;

	public void register(long sessionID, IClient client) throws RemoteException, NotLoggedException, SQLException, Exception;
		
//	public void initDBConnection(String ip, String port) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
//		DBConnectionManager.addConnection(new DBConnection(ip, Integer.parseInt(port), "dbgcad"));
//	}
//	
	/*** Methods used to add new Knowledge ***/
	public void addTopic (long sessionId, Topic topic) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException;
	
	public void addProposal (long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException;
	
	public void addAnwser (long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException;
	
	/*** Methods used to modify Knowledge  ***/
	public void modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException;
	
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException,  SQLException, NonPermissionRole, InstantiationException, IllegalAccessException, ClassNotFoundException, NotLoggedException;
	
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException, NonPermissionRole, InstantiationException, IllegalAccessException, ClassNotFoundException, NotLoggedException;
		
	/*** Methods used to delete Knowledge ***/
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException;
	
	public void deleteProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException;

	public void deleteAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException;
	
	
	public void createProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException;
		
	/*** Methods used to manage notifications ***/
//	public void addNotification(long sessionId, Notification notification)  throws RemoteException, SQLException ;
	
	public void removeNotification(long sessionId, Notification notification)  throws RemoteException, SQLException, NonPermissionRole, NotLoggedException ;

	public ArrayList<Notification> getNotifications(long sessionId)  throws RemoteException, SQLException, NonPermissionRole, NotLoggedException;
	
	/*** Auxiliary methods ***/
	public ArrayList<Proposal> getProposals(long sessionId)  throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException;
	
	public ArrayList<Answer> getAnswers(long sessionId)  throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException;
	
	public Proposal findParentAnswer(long sessionId, Answer a) throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException;
	
	public Topic findParentProposal(long sessionId, Proposal p) throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException;
//	public ArrayList<Project> getProjectsUser() {
//		// TODO: return los proyectos del usuario de la sesion
//		// return ProjectController.getProjectsUser(session.getUser().getId());
//		return null;
//	}	
	
	/*** Auxiliary methods ***/
	public void setCurrentProject(long sessionId, int idProject)  throws RemoteException, NotLoggedException;
	
	public TopicWrapper getTopicsWrapper(long sessionId) throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException;
	
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException, NonPermissionRole, NotLoggedException;
		
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
	
}
