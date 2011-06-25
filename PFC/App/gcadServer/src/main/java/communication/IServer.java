package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.knowledge.Answer;
import model.business.knowledge.ISession;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
import exceptions.IncorrectEmployeeException;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

/**
 * Remote interface that is used by the client applications, in order to request operations to the server.
 * This is the server Facade
 */
public interface IServer extends Remote {
	
	public static final String NAME_SERVER = "gcadServer";

	/*** Methods used to manage login and signout ***/
	public ISession login (String user, String pass) throws RemoteException, IncorrectEmployeeException, SQLException, Exception;
	
	public void signout(long sessionID) throws RemoteException, SQLException, NotLoggedException, Exception ;

	public void register(long sessionID, IClient client) throws RemoteException, NotLoggedException, SQLException, Exception;
		
	/*** Methods used to add new Knowledge ***/
	public void addTopic (long sessionId, Topic topic) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public void addProposal (long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public void addAnswer (long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	/*** Methods used to modify Knowledge  ***/
	public void modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
		
	/*** Methods used to delete Knowledge ***/
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public void deleteProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;

	public void deleteAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;	
	
	public Project createProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
		
	/*** Methods used to manage notifications ***/	
	public void removeNotification(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;

	public ArrayList<Notification> getNotificationsProject(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	/*** Auxiliary methods ***/
	public ArrayList<Proposal> getProposals(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public ArrayList<Answer> getAnswers(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public Proposal findParentAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public Topic findParentProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	/*** Auxiliary methods ***/
	public void setCurrentProject(long sessionId, int idProject) throws RemoteException, NotLoggedException, Exception;
	
	public TopicWrapper getTopicsWrapper(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public TopicWrapper getTopicsWrapper(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public List<Project> getProjects(long sessionId) throws RemoteException, NonPermissionRoleException, NotLoggedException, SQLException, Exception;
	
	public List<User> getUsersProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;

	public List<Project> getProjectsFromCurrentUser(long sessionId) throws RemoteException, NotLoggedException, Exception;
	
	public User getLoggedUser(long sessionId) throws RemoteException, NotLoggedException, Exception;
	
	/*** Methods used in CBR ***/
	public List<Attribute> getAttributesFromProject(Project p)  throws RemoteException, Exception;
	
	public List<Project> executeAlgorithm(EnumAlgorithmCBR algorithmName, List<Project> cases, Project caseToEval, ConfigCBR config, int k) throws RemoteException, Exception;

	List<User> getUsers(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;

	void addProjectsUser(long sessionId, User user, Project project) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
}
