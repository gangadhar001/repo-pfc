package communication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


import communication.IClient;
import communication.IServer;

import model.business.knowledge.Answer;
import model.business.knowledge.ISession;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import exceptions.IncorrectEmployeeException;
import exceptions.NoProposalsException;
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
	public ISession login(String user, String pass) throws IncorrectEmployeeException, SQLException, NonExistentRole, RemoteException {
		return server.login(user, pass);
		
	}

	@Override
	public void signout(long sessionID) throws SQLException, RemoteException, NotLoggedException{
		server.signout(sessionID);
		
	}

	@Override
	public void addTopic(long sessionId, Topic topic) throws RemoteException, SQLException {
		server.addTopic(sessionId, topic);
		
	}

	@Override
	public void addProposal(long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException {
		server.addProposal(sessionId, p, parent);
		
	}

	@Override
	public void addAnwser(long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException {
		server.addAnwser(sessionId, a, parent);
		
	}

	@Override
	public void modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException {
		server.modifyTopic(sessionId, newTopic, oldTopic);
	}

	@Override
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException {
		server.modifyProposal(sessionId, newProposal, oldProposal, parent);		
	}

	@Override
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException {
		server.modifyAnswer(sessionId, newAnswer, oldAnswer, parent);
		
	}

	@Override
	public TopicWrapper getTopicsWrapper(long sessionId) throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return server.getTopicsWrapper(sessionId);
	}


	@Override
	public void addNotification(long sessionId, Notification notification) throws RemoteException, SQLException {
		server.addNotification(sessionId, notification);		
	}

	@Override
	public void createProject(long sessionId, Project project) throws RemoteException, SQLException {
		server.createProject(sessionId, project);
		
	}

	@Override
	public void deleteAnswer(long sessionId, Answer answer) throws RemoteException, SQLException {
		server.deleteAnswer(sessionId, answer);
		
	}

	@Override
	public void deleteProposal(long sessionId, Proposal proposal) throws RemoteException, SQLException {
		server.deleteProposal(sessionId, proposal);
		
	}

	@Override
	public void deleteTopic(long sessionId, Topic t) throws RemoteException, SQLException {
		server.deleteTopic(sessionId, t);
		
	}

	@Override
	public ArrayList<Answer> getAnswers(long sessionId) throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return server.getAnswers(sessionId);
	}

	@Override
	public ArrayList<Notification> getNotifications(long sessionId) throws RemoteException, SQLException {
		return server.getNotifications(sessionId);
	}

	@Override
	public ArrayList<Proposal> getProposals(long sessionId) throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoProposalsException {
		return server.getProposals(sessionId);
	}

	@Override
	public void register(long idSession, IClient client) throws RemoteException, NotLoggedException {
		server.register(idSession, client);
		
	}

	@Override
	public void removeNotification(long sessionId, Notification notification) throws RemoteException, SQLException {
		server.removeNotification(sessionId, notification);
		
	}

	@Override
	public void setCurrentProject(long sessionId, int projectId) throws RemoteException {
		server.setCurrentProject(sessionId, projectId);
		
	}

	@Override
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException, NonPermissionRole {
			return server.getAvailableOperations(sessionId);

	}


}
