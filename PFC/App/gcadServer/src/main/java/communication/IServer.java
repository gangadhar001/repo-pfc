package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Image;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.Coordinates;
import model.business.knowledge.File;
import model.business.knowledge.ISession;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.PDFConfiguration;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
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
 * Remote interface that is used by the client applications, in order to request operations to the server.
 * This is the server Facade
 */
public interface IServer extends Remote {
	
	public static final String NAME_SERVER = "gcadServer";

	/*** Methods used to manage login and signout ***/
	public ISession login(String user, String pass) throws RemoteException, IncorrectEmployeeException, SQLException, Exception;
	
	public void signout(long sessionID) throws RemoteException, SQLException, NotLoggedException, Exception ;

	public void register(long sessionID, IClient client) throws RemoteException, NotLoggedException, Exception;
		
	/*** Methods used to add new Knowledge 
	 * @return ***/
	public Topic addTopic (long sessionId, Topic topic) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public Proposal addProposal (long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public Answer addAnswer (long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	/*** Methods used to modify Knowledge  ***/
	public Topic modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException, NonExistentTopicException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public Proposal modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, NonExistentProposalException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public Answer modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, NonExistentAnswerException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
		
	/*** Methods used to delete Knowledge ***/
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException, NonPermissionRoleException, NonExistentTopicException, NotLoggedException, Exception;
	
	public void deleteProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRoleException, NonExistentProposalException, NotLoggedException, Exception;

	public void deleteAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRoleException, NonExistentAnswerException, NotLoggedException, Exception;	
	
	/*** Methods used to manage projects ***/
	public Project createProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public void updateProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public void addProjectsUser(long sessionId, User user, Project project) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public void removeProjectsUser(long sessionId, User user, Project project) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
		
	public List<Project> getProjects(long sessionId) throws RemoteException, NonPermissionRoleException, NotLoggedException, SQLException, Exception;
	
	public List<User> getUsersProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public List<Project> getProjectsFromCurrentUser(long sessionId) throws RemoteException, NotLoggedException, Exception;
	
	/*** Methods used to manage notifications ***/	
	public ArrayList<Notification> getNotificationsProject(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public ArrayList<Notification> getNotificationsUser(long sessionId) throws SQLException, NonPermissionRoleException, NotLoggedException, RemoteException, Exception;

	public void createNotification(long sessionId, Notification n) throws SQLException, NonPermissionRoleException, NotLoggedException, RemoteException, Exception;
		
	public void modifyNotification(long sessionId, Notification not) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentNotificationException, Exception;
	
	public void modifyNotificationState(long sessionId, Notification not) throws NotLoggedException, SQLException, NonPermissionRoleException, Exception;
	
	public void deleteNotification(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, NonExistentNotificationException, Exception;
	
	public void deleteNotificationFromUser(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, NonExistentNotificationException, Exception;
		
	/*** Auxiliary methods ***/	
	public ArrayList<Proposal> getProposals(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public ArrayList<Answer> getAnswers(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public Proposal findParentAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public Topic findParentProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;

	public void setCurrentProject(long sessionId, int idProject) throws RemoteException, NotLoggedException, Exception;
	
	public TopicWrapper getTopicsWrapper(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public TopicWrapper getTopicsWrapper(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException, NotLoggedException, Exception;
	
	public User getLoggedUser(long sessionId) throws RemoteException, NotLoggedException, Exception;
	
	public List<User> getUsers(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public Coordinates getCoordinates(long sessionId, Address add) throws NonExistentAddressException, WSResponseException, Exception;
		
	/*** Methods used in CBR ***/
	public List<Attribute> getAttributesFromProject(Project p)  throws RemoteException, Exception;

	public List<Project> executeAlgorithm(long sessionId, EnumAlgorithmCBR algorithmName, List<Project> cases, Project caseToEval, ConfigCBR config, int k)	throws RemoteException, Exception;	
	
	/*** Methods used to manage Files ***/
	public int attachFile(long sessionId, File file) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
	
	public List<File> getAttachedFiles(long sessionId, Knowledge k) throws RemoteException, SQLException, NonExistentFileException, NonPermissionRoleException, NotLoggedException, Exception;
	
	/*** Method used to generate the PDF ***/
	public byte[] composePDF(long sessionId, PDFConfiguration configuration, Image headerImage, Image footImage) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception;
}
