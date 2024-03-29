package communication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

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
	public ISession login(String user, String pass) throws IncorrectEmployeeException, SQLException, NonExistentRole, RemoteException, Exception {
		return server.login(user, pass);
		
	}

	@Override
	public void signout(long sessionID) throws SQLException, RemoteException, NotLoggedException, Exception {
		server.signout(sessionID);
		
	}

	@Override
	public void addTopic(long sessionId, Topic topic) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception {
		server.addTopic(sessionId, topic);
		
	}

	@Override
	public void addProposal(long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception {
		server.addProposal(sessionId, p, parent);
		
	}

	@Override
	public void addAnwser(long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception {
		server.addAnwser(sessionId, a, parent);
		
	}

	@Override
	public void modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception {
		server.modifyTopic(sessionId, newTopic, oldTopic);
	}

	@Override
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception {
		server.modifyProposal(sessionId, newProposal, oldProposal, parent);		
	}

	@Override
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception {
		server.modifyAnswer(sessionId, newAnswer, oldAnswer, parent);
		
	}

	public Proposal findParentAnswer(long sessionId, Answer a) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception{
		return server.findParentAnswer(sessionId, a);
	}
	
	public Topic findParentProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception {
		return server.findParentProposal(sessionId, p);
	}
	
	@Override
	public TopicWrapper getTopicsWrapper(long sessionId) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception {
		return server.getTopicsWrapper(sessionId);
	}

	@Override
	public void createProject(long sessionId, Project project) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception{
		server.createProject(sessionId, project);
		
	}

	@Override
	public void deleteAnswer(long sessionId, Answer answer) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception{
		server.deleteAnswer(sessionId, answer);
		
	}

	@Override
	public void deleteProposal(long sessionId, Proposal proposal) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception{
		server.deleteProposal(sessionId, proposal);
		
	}

	@Override
	public void deleteTopic(long sessionId, Topic t) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception{
		server.deleteTopic(sessionId, t);
		
	}

	@Override
	public ArrayList<Answer> getAnswers(long sessionId) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception{
		return server.getAnswers(sessionId);
	}

	@Override
	public ArrayList<Notification> getNotifications(long sessionId) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception{
		return server.getNotifications(sessionId);
	}

	@Override
	public ArrayList<Proposal> getProposals(long sessionId) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception{
		return server.getProposals(sessionId);
	}

	@Override
	public void register(long idSession, IClient client) throws RemoteException, SQLException, NotLoggedException, Exception{
		server.register(idSession, client);
		
	}

	@Override
	public void removeNotification(long sessionId, Notification notification) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception{
		server.removeNotification(sessionId, notification);
		
	}

	@Override
	public void setCurrentProject(long sessionId, int projectId) throws RemoteException, NotLoggedException, Exception{
		server.setCurrentProject(sessionId, projectId);		
	}

	@Override
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception{
		return server.getAvailableOperations(sessionId);
	}

	@Override
	public List<Project> getProjects(long sessionId) throws RemoteException, NonPermissionRole, NotLoggedException, SQLException, Exception {
		return server.getProjects(sessionId); 
	}

	@Override
	public TopicWrapper getTopicsWrapper(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		return server.getTopicsWrapper(sessionId, p);
	}

	@Override
	public List<User> getUsersProject(long sessionId, Project p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
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

}
