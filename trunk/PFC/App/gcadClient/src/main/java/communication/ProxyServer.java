package communication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.JDOMException;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.Coordinates;
import model.business.knowledge.ISession;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentAddressException;
import exceptions.NonExistentNotificationException;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;
import exceptions.WSResponseException;

/**
 * Proxy used to connect to server
 *
 */
public class ProxyServer implements IServer {

	private IServer server;
	
	public void connectServer(String ip, int port) throws MalformedURLException, RemoteException, NotBoundException {
		String url;
		
		url = "rmi://" + ip + ":" + String.valueOf(port) + "/" + NAME_SERVER;
		server = (IServer)Naming.lookup(url);
	}	
	
	/*** Methods from server facade ***/
	@Override
	public ISession login(String user, String pass) throws IncorrectEmployeeException, SQLException, RemoteException, Exception {
		return server.login(user, pass);
		
	}

	@Override
	public void signout(long sessionID) throws SQLException, RemoteException, NotLoggedException, Exception {
		server.signout(sessionID);
		
	}

	@Override
	public void addTopic(long sessionId, Topic topic) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception {
		server.addTopic(sessionId, topic);
		
	}

	@Override
	public void addProposal(long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception {
		server.addProposal(sessionId, p, parent);
		
	}

	@Override
	public void addAnswer(long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception {
		server.addAnswer(sessionId, a, parent);
		
	}

	@Override
	public void modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception {
		server.modifyTopic(sessionId, newTopic, oldTopic);
	}

	@Override
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception {
		server.modifyProposal(sessionId, newProposal, oldProposal, parent);		
	}

	@Override
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception {
		server.modifyAnswer(sessionId, newAnswer, oldAnswer, parent);
		
	}

	public Proposal findParentAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception{
		return server.findParentAnswer(sessionId, a);
	}
	
	public Topic findParentProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.findParentProposal(sessionId, p);
	}
	
	@Override
	public TopicWrapper getTopicsWrapper(long sessionId) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.getTopicsWrapper(sessionId);
	}

	@Override
	public Project createProject(long sessionId, Project project) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception{
		return server.createProject(sessionId, project);		
	}

	@Override
	public void deleteAnswer(long sessionId, Answer answer) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception{
		server.deleteAnswer(sessionId, answer);
		
	}

	@Override
	public void deleteProposal(long sessionId, Proposal proposal) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception{
		server.deleteProposal(sessionId, proposal);
		
	}

	@Override
	public void deleteTopic(long sessionId, Topic t) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception{
		server.deleteTopic(sessionId, t);
		
	}

	@Override
	public ArrayList<Answer> getAnswers(long sessionId) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception{
		return server.getAnswers(sessionId);
	}


	@Override
	public ArrayList<Proposal> getProposals(long sessionId) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception{
		return server.getProposals(sessionId);
	}

	@Override
	public void register(long idSession, IClient client) throws RemoteException, SQLException, NotLoggedException, Exception{
		server.register(idSession, client);
		
	}

	@Override
	public void setCurrentProject(long sessionId, int projectId) throws RemoteException, NotLoggedException, Exception{
		server.setCurrentProject(sessionId, projectId);		
	}

	@Override
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception{
		return server.getAvailableOperations(sessionId);
	}

	@Override
	public List<Project> getProjects(long sessionId) throws RemoteException, NonPermissionRoleException, NotLoggedException, SQLException, Exception {
		return server.getProjects(sessionId); 
	}

	@Override
	public TopicWrapper getTopicsWrapper(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getTopicsWrapper(sessionId, p);
	}

	@Override
	public List<User> getUsersProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getUsersProject(sessionId, p);
	}

	@Override
	public List<Project> getProjectsFromCurrentUser(long sessionId) throws RemoteException, NotLoggedException, Exception {
		return server.getProjectsFromCurrentUser(sessionId);
	}

	@Override
	public User getLoggedUser(long sessionId) throws RemoteException, NotLoggedException, Exception {
		return server.getLoggedUser(sessionId);
	}

	@Override
	public List<Attribute> getAttributesFromProject(Project p) throws RemoteException, Exception {
		return server.getAttributesFromProject(p);
	}

	public List<User> getUsers(long sessionId) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getUsers(sessionId);
	}

	public void addProjectsUser(long sessionId, User user, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.addProjectsUser(sessionId, user, p);
	}

	@Override
	public void createNotification(long arg0, Notification arg1)
			throws SQLException, NonPermissionRoleException,
			NotLoggedException, RemoteException, Exception {
		server.createNotification(arg0, arg1);
		
	}

	@Override
	public void deleteNotification(long arg0, Notification arg1)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, NonExistentNotificationException, Exception {
		server.deleteNotification(arg0, arg1);
		
	}

	@Override
	public void deleteNotificationFromUser(long arg0, Notification arg1)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, NonExistentNotificationException, Exception {
		server.deleteNotificationFromUser(arg0, arg1);
		
	}

	@Override
	public List<Project> executeAlgorithm(long arg0, EnumAlgorithmCBR arg1,
			List<Project> arg2, Project arg3, ConfigCBR arg4, int arg5)
			throws RemoteException, Exception {
		return server.executeAlgorithm(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public Coordinates getCoordinates(long arg0, Address arg1)
			throws NonExistentAddressException, WSResponseException, Exception {
		return server.getCoordinates(arg0, arg1);
	}

	@Override
	public ArrayList<Notification> getNotificationsProject(long arg0)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		return server.getNotificationsProject(arg0);
	}

	@Override
	public ArrayList<Notification> getNotificationsUser(long arg0)
			throws SQLException, NonPermissionRoleException,
			NotLoggedException, RemoteException, Exception {
		return server.getNotificationsUser(arg0);
	}

	@Override
	public void modifyNotification(long arg0, Notification arg1)
			throws SQLException, NonPermissionRoleException,
			NotLoggedException, NonExistentNotificationException, Exception {
		server.modifyNotification(arg0, arg1);
		
	}

	@Override
	public void modifyNotificationState(long arg0, Notification arg1)
			throws NotLoggedException, SQLException,
			NonPermissionRoleException, Exception {
		server.modifyNotificationState(arg0, arg1);
		
	}


}
