package communication;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Image;

import model.business.control.Server;
import model.business.control.CBR.Attribute;
import model.business.control.CBR.CaseEval;
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
import exceptions.NonExistentNotificationException;
import exceptions.NonExistentProposalException;
import exceptions.NonExistentTopicException;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;
import exceptions.WSResponseException;

/**
 * Class that exports the server instance to be used by clients to execute operations from the server facade (interface)
 */
public class ExportedServer extends UnicastRemoteObject implements IServer {
	
	private static final long serialVersionUID = 7735848879217866237L;
	
	private IServer server;
	private boolean register;
	
	private static ExportedServer instance;

	protected ExportedServer() throws RemoteException {
		super();
		server = Server.getInstance();
		register = false;
	}
	
	public static ExportedServer getServer() throws RemoteException {
		if(instance == null) {
			instance = new ExportedServer();
		}
		return instance;
	}
	
    public void activate(String serverIP, int serverPort) throws MalformedURLException, RemoteException {
		// If the server is already exports, don't throw exception
    	try {
    		if(!register) {
    			LocateRegistry.createRegistry(serverPort);
    			register = true;
    		}
    		exportObject(this, serverPort);
        } catch(ExportException ex) {
        	if(!ex.getMessage().toLowerCase().equals("object already exported")) {
        		throw ex;
        	}
        }
        try {
            Naming.bind("rmi://" + serverIP + ":" + String.valueOf(serverPort) + "/" + NAME_SERVER , this);
        } catch(AlreadyBoundException ex) {
            Naming.rebind("rmi://" + serverIP + ":" + String.valueOf(serverPort) + "/" + NAME_SERVER, this);
        }
    }
    
    public void deactivate(String serverIP, int serverPort) throws RemoteException, MalformedURLException {
    	try {
    		unexportObject(this, false);
    	} catch(NoSuchObjectException ex) {
    	}
    	try {
    		Naming.unbind("rmi://" + serverIP + ":" + String.valueOf(serverPort) + "/" + NAME_SERVER );
    	} catch(NotBoundException ex) {
    	}
    }
    
    /*** Methods from server facade ***/
    /*** Methods used to manage login and signout ***/
	@Override
	public ISession login (String user, String pass) throws RemoteException, IncorrectEmployeeException, SQLException, Exception {
		return server.login(user, pass);		
	}
	
	@Override
	public void signout(long sessionID) throws RemoteException, SQLException, NotLoggedException, Exception {
		server.signout(sessionID);
	}
	
	@Override
	public void register(long sessionID, IClient client) throws RemoteException, NotLoggedException, Exception {
		server.register(sessionID, client);
	}
	
	/*** Methods used to add new Knowledge ***/
	@Override
	public Topic addTopic (long sessionId, Topic topic) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.addTopic(sessionId, topic);
	}
	
	@Override
	public Proposal addProposal (long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.addProposal(sessionId, p, parent);
	}
	
	@Override
	public Answer addAnswer (long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.addAnswer(sessionId, a, parent);
	}
	
	/*** Methods used to modify Knowledge ***/
	@Override
	public Topic modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException, NonPermissionRoleException, NonExistentTopicException, NotLoggedException, Exception {
		return server.modifyTopic(sessionId, newTopic, oldTopic);
	}
	
	@Override
	public Proposal modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException, NonExistentProposalException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.modifyProposal(sessionId, newProposal, oldProposal, parent);
	}
	
	@Override
	public Answer modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException, NonExistentAnswerException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.modifyAnswer(sessionId, newAnswer, oldAnswer, parent);
	}
		
	/*** Methods used to delete Knowledge ***/
	@Override
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, NonExistentTopicException, Exception {
		server.deleteTopic(sessionId, to);
	}

	@Override
	public void deleteProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, NonExistentProposalException, Exception {
		server.deleteProposal(sessionId, p);
	}

	@Override
	public void deleteAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, NonExistentAnswerException, Exception {
		server.deleteAnswer(sessionId, a);
	}
	
	/*** Methods to manage projects ***/
	@Override
	public Project createProject (long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.createProject(sessionId, p);
	}
	
	@Override
	public void addProjectsUser(long sessionId, User user, Project project) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.addProjectsUser(sessionId, user, project);
	}
	
	@Override
	public void updateProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.updateProject(sessionId, p);		
	}

	@Override
	public void removeProjectsUser(long sessionId, User user, Project project) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.removeProjectsUser(sessionId, user, project);		
	}
	
	@Override
	public List<Project> getProjects(long sessionId) throws RemoteException, NonPermissionRoleException, NotLoggedException, SQLException, Exception{
		return server.getProjects(sessionId);
	}
	
	@Override
	public List<User> getUsersProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getUsersProject(sessionId, p);
	}
	
	@Override
	public List<Project> getProjectsFromCurrentUser(long sessionId) throws RemoteException, NotLoggedException, Exception {
		return server.getProjectsFromCurrentUser(sessionId);
	}
	
	/*** Methods used to manage notifications ***/		
	@Override
	public ArrayList<Notification> getNotificationsUser(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getNotificationsUser(sessionId);
	}

	@Override
	public void createNotification(long sessionId, Notification n) throws SQLException, NonPermissionRoleException, NotLoggedException, RemoteException, Exception {
		server.createNotification(sessionId, n);
	}
	
	@Override
	public void modifyNotification(long sessionId, Notification not) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, NonExistentNotificationException, Exception {
		server.modifyNotification(sessionId, not);
	}
	
	@Override
	public void modifyNotificationState(long sessionId, Notification not) throws RemoteException, NotLoggedException, SQLException, NonPermissionRoleException, Exception {
		server.modifyNotificationState(sessionId, not);
	}

	@Override
	public void deleteNotification(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.deleteNotification(sessionId, notification);
	}
	
	@Override
	public void deleteNotificationFromUser(long sessionId, Notification notification) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.deleteNotificationFromUser(sessionId, notification);
	}

	@Override
	public ArrayList<Notification> getNotificationsProject(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getNotificationsProject(sessionId);
	}
	
	/*** Auxiliary methods  ***/
	@Override
	public ArrayList<Proposal> getProposals(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getProposals(sessionId);
	}
	
	@Override
	public ArrayList<Answer> getAnswers(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getAnswers(sessionId);
	}
	
	@Override
	public Proposal findParentAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.findParentAnswer(sessionId, a);
	}
	
	@Override
	public Topic findParentProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.findParentProposal(sessionId, p);
	}		
		
	@Override
	public TopicWrapper getTopicsWrapper(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getTopicsWrapper(sessionId);
	}
	
	@Override
	public TopicWrapper getTopicsWrapper(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getTopicsWrapper(sessionId, p);
	}
	
	@Override
	public void setCurrentProject(long sessionId, int id) throws RemoteException, NotLoggedException, Exception {
		server.setCurrentProject(sessionId, id);		
	}	
	
	/**
	 * This method reads the profiles available for a user role from a XML file.
	 */
	@Override
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException, NotLoggedException, Exception {
		return server.getAvailableOperations(sessionId);
	}
		
	@Override
	public User getLoggedUser(long sessionId) throws RemoteException, NotLoggedException, Exception{
		return server.getLoggedUser(sessionId);
	}
	
	@Override
	public List<User> getUsers(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getUsers(sessionId);
	}
	
	@Override
	public Coordinates getCoordinates(long sessionId, Address add) throws NonExistentAddressException, WSResponseException, Exception {
		return server.getCoordinates(sessionId, add);
	}

	/*** Methods used in CBR ***/
	@Override
	public List<Attribute> getAttributesFromProject(Project p) throws Exception {		
		return server.getAttributesFromProject(p);
	}
	
	@Override
	public List<CaseEval> executeAlgorithm(long sessionId, EnumAlgorithmCBR algorithmName, Project caseToEval, ConfigCBR config, int k) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception{
		return server.executeAlgorithm(sessionId, algorithmName, caseToEval, config, k);
	}

	@Override
	public int attachFile(long sessionId, Knowledge k, File file) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.attachFile(sessionId, k, file);
	}

	@Override
	public byte[] composePDF(long sessionId, PDFConfiguration configuration, Image headerImage, Image footImage) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.composePDF(sessionId, configuration, headerImage, footImage);
	}

	@Override
	public int getCurrentProject(long sessionId) throws RemoteException, NotLoggedException, Exception {
		return server.getCurrentProject(sessionId);		
	}

	@Override
	public <T> byte[] exportInformation(long sessionId, Project project) throws RemoteException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.exportInformation(sessionId, project);
	}

	@Override
	public void changeStatusKnowledge(long sessionId, Knowledge k) throws NonPermissionRoleException, RemoteException, SQLException, NotLoggedException, Exception {
		server.changeStatusKnowledge(sessionId, k);
		
	}
}
