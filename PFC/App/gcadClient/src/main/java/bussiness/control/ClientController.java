package bussiness.control;

import internationalization.ApplicationInternationalization;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.retrieveAlgorithms.NNConfig;
import model.business.knowledge.Answer;
import model.business.knowledge.EnumAlgorithmCBR;
import model.business.knowledge.ISession;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Language;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
import model.business.knowledge.UserRole;

import org.jdesktop.application.Application;
import org.jdom.JDOMException;

import presentation.JFLogin;
import presentation.JFMain;

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
	private String clientIP;
	private String login;
	private String role;
	private JFLogin loginWindowUI;

	public ClientController() {
		availableOperations = new ArrayList<Operation>();
	}
	
	public static ClientController getInstance() {
		if(instance == null) {
			instance = new ClientController();
		}
		return instance;
	}	

	public String getClientIP() {
		return clientIP;
	}
	
	public int getPort () {
		return client.getListenPort();
		
	}	

	/*** Method to login and export the client (plug-in) ***/
	public void initClient(String serverIP, String serverPort, String username, String pass) throws NotBoundException, RemoteException, IncorrectEmployeeException, SQLException, NonExistentRole, MalformedURLException, NotLoggedException, Exception{
		// Get the local host IP
		clientIP = CommunicationsUtilities.getHostIP();
		
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
			login = username;
			role = UserRole.values()[session.getRole()].name();
			availableOperations.clear();
		} catch(RemoteException e) {
			throw new RemoteException(ApplicationInternationalization.getString("ClientController_Login_Error"));
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
		} 	
		
	}
	
	// Close login frame and show main frame	
	public void showMainFrame() {
		Application.getInstance(JFLogin.class).getMainFrame().dispose();
		Application.launch(JFMain.class, null);
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
	public void addAnwser(Answer a, Proposal p) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception {
		server.addAnwser(session.getId(), a, p);
	}

	public void addProposal(Proposal p, Topic parent) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.addProposal(session.getId(), p, parent);
		
	}

	public void addTopic(Topic topic) throws RemoteException, SQLException, NotLoggedException, NonPermissionRole, Exception {
		server.addTopic(session.getId(), topic);		
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

	public void modifyAnswer(Answer newAnswer, Answer oldAnswer, Proposal p) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.modifyAnswer(session.getId(), newAnswer, oldAnswer, p);
	}

	public void modifyProposal(Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.modifyProposal(session.getId(), newProposal, oldProposal, parent);
		
	}

	public void modifyTopic(Topic newTopic, Topic oldTopic) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.modifyTopic(session.getId(), newTopic, oldTopic);		
	}

	public void signout() throws RemoteException, SQLException, NotLoggedException, Exception {
		if (session != null) {
			server.signout(session.getId());
			availableOperations.clear();
			session = null;
		}
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

	public void deleteAnswer(Answer a) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.deleteAnswer(session.getId(), a);		
	}

	public void deleteProposal(Proposal proposal) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.deleteProposal(session.getId(), proposal);
		
	}
	
	public void deleteTopic(Topic topic) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.deleteTopic(session.getId(), topic);		
	}

	public ArrayList<Answer> getAnswers() throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		return server.getAnswers(session.getId());
	}

	public ArrayList<Notification> getNotifications() throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		return server.getNotifications(session.getId());
	}

	public ArrayList<Proposal> getProposals() throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		return server.getProposals(session.getId());
	}

	public void removeNotification(Notification notification) throws RemoteException, NotLoggedException, NonPermissionRole, Exception {
		server.removeNotification(session.getId(), notification);
		
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

	public List<Project> getProjectsFromCurrentUser() throws RemoteException, NotLoggedException, Exception {
		return server.getProjectsFromCurrentUser(session.getId());
	}	
	
	public List<Attribute> getAttributesFromProject(Project p) throws RemoteException, Exception {
		return server.getAttributesFromProject(p);
	}
	
	public List<Project> executeAlgorithm(EnumAlgorithmCBR algorithmName, List<Project> cases, Project caseToEval, NNConfig configCBR, int k) throws RemoteException, Exception {
		return server.executeAlgorithm(algorithmName, cases, caseToEval, configCBR, k);
	}
	
	public User getLoggedUser() throws RemoteException, NotLoggedException, Exception {
		return server.getLoggedUser(session.getId());
	}
	
	public Language getDefaultLanguage() throws RemoteException, JDOMException, IOException {
		return LanguagesController.getDefaultLanguage();
	}

	public ArrayList<Language> getLanguages() throws RemoteException, JDOMException, IOException {
		return LanguagesController.getLanguages();
	}

	public void setDefaultLanguage(Language language) throws RemoteException, JDOMException, IOException {
		LanguagesController.setDefaultLanguage(language);
		
	}

	public void closeSession() {
		if (Application.getInstance() instanceof JFMain) {
			mainWindowUI = Application.getInstance(JFMain.class);
			mainWindowUI.forceCloseSession();
		}
		else if (Application.getInstance() instanceof JFLogin) {
			loginWindowUI = Application.getInstance(JFLogin.class);
			loginWindowUI.forceCloseSession();
		}
	}

	public void approachlessServer() {
		if (Application.getInstance() instanceof JFMain) {
			mainWindowUI = Application.getInstance(JFMain.class);
			mainWindowUI.approachlessServer();
		}
		else if (Application.getInstance() instanceof JFLogin) {
			loginWindowUI = Application.getInstance(JFLogin.class);
			loginWindowUI.approachlessServer();
		}
		
	}

	public void notifyKnowledgeAdded(Knowledge k) throws RemoteException {
		mainWindowUI = Application.getInstance(JFMain.class);
		mainWindowUI.notifyKnowledgeAdded(k);
	}

	public void notifyKnowledgeEdited(Knowledge newK, Knowledge oldK) throws RemoteException {
		mainWindowUI = Application.getInstance(JFMain.class);
		mainWindowUI.notifyKnowledgeEdited(newK, oldK);
		
	}
	
	public void notifyKnowledgeRemoved(Knowledge k) throws RemoteException {
		mainWindowUI = Application.getInstance(JFMain.class);
		mainWindowUI.notifyKnowledgeRemoved(k);
		
	}
	
	public void notifyNotificationAvailable(Notification n) {
		// TODO Auto-generated method stub
		
	}

	// Close main frame and show login frame
	public void closeMainFrame() {
		Application.getInstance(JFMain.class).getMainFrame().dispose();
		startApplication(null);		
	}
	
	// Close controller and application
	public void closeController() throws RemoteException, MalformedURLException, NotBoundException {
		if(client != null) {
			client.disabled(clientIP);
		}
			
		Application.getInstance(JFLogin.class).getMainFrame().dispose();
		Application.getInstance(JFMain.class).getMainFrame().dispose();
	}

	public String getRole() {
		return role;
	}

	public String getUserLogin() {
		return login;
	}

	public void restartLoginFrame() throws InterruptedException {
		Application.getInstance(JFLogin.class).getMainFrame().dispose();
		Thread.sleep(700);
		startApplication(null);		
	}
	
	public void restartMainFrame() throws InterruptedException {
		Application.getInstance(JFMain.class).getMainFrame().dispose();
		Thread.sleep(700);
		Application.launch(JFMain.class, null);
	}

	
}
