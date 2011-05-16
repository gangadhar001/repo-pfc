package bussiness.control;

import internationalization.ApplicationInternationalization;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.application.Application;

import presentation.JFLogin;
import presentation.JFMain;

import model.business.knowledge.Answer;
import model.business.knowledge.ISession;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;

import communication.CommunicationsUtilities;
import communication.ExportedClient;
import communication.IClient;
import communication.ProxyServer;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

/***
 * This is the class which controls all the functionality of the client. 
 * It makes request to the server and controls the client UI
 */
public class ClientController {

	// Proxy of the server
	private ProxyServer server;
	// Exported client. This is the object which the server register to notify changes
	private ExportedClient client;
	// Controller of the local client
	private static ClientController instance;
	// List of availables operations for the logged user
	private List<Operation> availableOperations;
	private JFMain mainWindowUI;
	private ISession session;

	public ClientController() {
		availableOperations = new ArrayList<Operation>();
	}
	
	public static ClientController getInstance() {
		if(instance == null) {
			instance = new ClientController();
		}
		return instance;
	}

	/*** Method to login and export the client (plug-in) ***/
	public void initClient(String serverIP, String serverPort, String username, String pass) throws NotBoundException, RemoteException, IncorrectEmployeeException, SQLException, NonExistentRole, MalformedURLException, NotLoggedException {
		// Get the local host IP
		String clientIP = CommunicationsUtilities.getHostIP();
		
		// Indicate to RMI that it have to use the given IP as IP of this host in remote communications.
		// This instruction is necessary because if the computer belongs to more than one network, RMI may take a private IP as the host IP 
		// and incoming communications won't work 
		System.setProperty("java.rmi.server.hostname", clientIP);
		server = null;
		try {
			server = new ProxyServer();
			server.connectServer(serverIP, Integer.valueOf(serverPort));
		} catch(NotBoundException e) {
			throw new NotBoundException(ApplicationInternationalization.getString("ClientController_ConnectServer_DisconnectMessage"));
		} catch(RemoteException e) {
			throw new RemoteException(ApplicationInternationalization.getString("ClientController_ConnectServer_Error"));
		} catch (MalformedURLException e) {
			throw new RemoteException(ApplicationInternationalization.getString("ClientController_ConnectServer_Error"));
		}
		
		try {
			// Login in the server
			session = server.login(username, pass);
			//usuarioAutenticado = login;
		} catch(RemoteException e) {
			throw new RemoteException(ApplicationInternationalization.getString("ClientController_Login_Error"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			// Create and export the client
			client = ExportedClient.getClient();
			client.activate(clientIP);
			((Client)client.getExportedClient()).setController(this);
			// Register the exported object in the server			
			server.register(session.getId(), (IClient)client);
		} catch(RemoteException e) {
			throw new RemoteException(ApplicationInternationalization.getString("ClientController_RegisterClient_Error"));				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		// Close login frame and show main frame
		Application.getInstance(JFLogin.class).getMainFrame().dispose();
		mainWindowUI = new JFMain();
		Application.launch(mainWindowUI.getClass(), null);
		
	}
	
	// Show the login frame
	public void startApplication(String [] args) {
		Application.launch(JFLogin.class, args);		
	}
	
	
	// Get knowledge from an user on a project
	public List<Knowledge> getKnowledgeUser(TopicWrapper tw, User u) {
		List<Knowledge> result = new ArrayList<Knowledge>();
		for (Topic t: tw.getTopics()){
			if (t.getUser().equals(u))
				result.add(t);
			for (Proposal p: t.getProposals()){
				if (p.getUser().equals(u))
					result.add(p);
				for (Answer a: p.getAnswers()) {
					if (a.getUser().equals(u))
						result.add(a);
				}
			}
		}
		return result;
	}
	
	// Get knowledge from a project
	public List<Knowledge> getKnowledgeProject(TopicWrapper tw) {
		List<Knowledge> result = new ArrayList<Knowledge>();
		for (Topic t: tw.getTopics()){
				result.add(t);
			for (Proposal p: t.getProposals()){
					result.add(p);
				for (Answer a: p.getAnswers()) {
						result.add(a);
				}
			}
		}
		return result;
	}

	
	/*** Methods to make requests to the server ***/
	public void addAnwser(Answer arg0, Proposal arg1) throws RemoteException,
			SQLException {
		// TODO Auto-generated method stub
		
	}

	public void addProposal(Proposal p, Topic parent) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.addProposal(session.getId(), p, parent);
		
	}

	public void addTopic(Topic arg0) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		// TODO Auto-generated method stub
		
	}

	public TopicWrapper getTopicsWrapper() throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		return server.getTopicsWrapper(session.getId());
	}
	
	public Topic findParentProposal(Proposal p) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		return server.findParentProposal(session.getId(), p);
	}
	
	public Proposal findParentAnswer(Answer a) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		return server.findParentAnswer(session.getId(), a);
	}

	public void modifyAnswer(Answer arg0, Answer arg1, Proposal arg2) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		// TODO Auto-generated method stub
		
	}

	public void modifyProposal(Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.modifyProposal(session.getId(), newProposal, oldProposal, parent);
		
	}

	public void modifyTopic(Topic arg0, Topic arg1) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		// TODO Auto-generated method stub
		
	}

	public void signout() throws RemoteException, SQLException, NotLoggedException, Exception {
		server.signout(session.getId());		
	}
	
	public boolean isLogged () {
		return session != null;
	}

	public void setCurrentProject(int selectedProject) throws RemoteException, NotLoggedException, Exception {
		server.setCurrentProject(session.getId(), selectedProject);
		
	}

	public void createProject(Project project) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.createProject(session.getId(), project);
		
	}

	public void notifyActionsAllowed(List<String> actions) throws RemoteException {
//		PresentationController.notifyActionsAllowed(actions);
		
	}

	public void notifyConnection(boolean connected) throws RemoteException {
//		 PresentationController.notifyConnection(connected);
		
	}

	public void notifyKnowledgeAdded(Knowledge k) throws RemoteException {
//		PresentationController.notifyKnowledgeAdded(k);
		
	}

	public void notifyKnowledgeEdited(Knowledge k) throws RemoteException {
//		PresentationController.notifyKnowledgeEdited(k);
		
	}
	
	public void notifyKnowledgeRemoved(Knowledge k) throws RemoteException {
//		PresentationController.notifyKnowledgeRemoved(k);
		
	}

	public void deleteAnswer(Answer arg0) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		// TODO Auto-generated method stub
		
	}

	public void deleteProposal(Proposal proposal) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.deleteProposal(session.getId(), proposal);
		
	}
	
	public void deleteTopic(Topic arg0) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Answer> getAnswers() throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Notification> getNotifications() throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		return server.getNotifications(session.getId());
	}

	public ArrayList<Proposal> getProposals() throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		return server.getProposals(session.getId());
	}

	public void removeNotification(Notification arg0) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		// TODO Auto-generated method stub
		
	}
	public List<Operation> getAvailableOperations() throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		// Only make the request to the server the first time
		if (availableOperations.size() == 0)
			availableOperations = server.getAvailableOperations(session.getId());
		return availableOperations;
	} 
	
	public List<Project> getProjects() throws RemoteException, NonPermissionRole, NotLoggedException, SQLException, Exception {
		return server.getProjects(session.getId()); 
	}

	public TopicWrapper getTopicsWrapper(Project p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		return server.getTopicsWrapper(session.getId(), p);
	}

	public List<User> getUsersProject(Project p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException, Exception {
		return server.getUsersProject(session.getId(), p);
	}	

	
}
