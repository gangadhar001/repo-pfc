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
import model.business.control.CBR.CaseEval;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.knowledge.Answer;
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
import model.business.knowledge.UserRole;

import org.jdesktop.application.Application;
import org.jdom.JDOMException;

import com.itextpdf.text.Image;

import presentation.JFMain;
import resources.LanguagesUtilities;
import resources.Language;

import communication.CommunicationsUtilities;
import communication.ExportedClient;
import communication.ProxyServer;

import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentNotificationException;
import exceptions.NonPermissionRoleException;
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
	private String projectName;

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
	public void initClient(String serverIP, String serverPort, String username, String pass) throws NotBoundException, RemoteException, IncorrectEmployeeException, SQLException, MalformedURLException, NotLoggedException, Exception{
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
			server.register(session.getId(), client);
		} catch(RemoteException e) {
			throw new RemoteException(ApplicationInternationalization.getString("ClientController_RegisterClient_Error"));				
		} 	
		
	}
	
//	// Close login frame and show main frame	
//	public void showMainFrame() {
//		Application.getInstance(JFLogin.class).getMainFrame().dispose();
//		Application.launch(JFMain.class, null);
//	}
	
	// Show the login frame
	public void startApplication(String [] args) {
		Application.launch(JFMain.class, args);		
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
	public Answer addAnwser(Answer a, Proposal p) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.addAnswer(session.getId(), a, p);
	}

	public Proposal addProposal(Proposal p, Topic parent) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.addProposal(session.getId(), p, parent);
		
	}

	public Topic addTopic(Topic topic) throws RemoteException, SQLException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.addTopic(session.getId(), topic);		
	}

	public TopicWrapper getTopicsWrapper() throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.getTopicsWrapper(session.getId());
	}
	
	public Topic findParentProposal(Proposal p) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.findParentProposal(session.getId(), p);
	}
	
	public Proposal findParentAnswer(Answer a) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.findParentAnswer(session.getId(), a);
	}

	public Answer modifyAnswer(Answer newAnswer, Answer oldAnswer, Proposal p) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.modifyAnswer(session.getId(), newAnswer, oldAnswer, p);
	}

	public Proposal modifyProposal(Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.modifyProposal(session.getId(), newProposal, oldProposal, parent);		
	}

	public Topic modifyTopic(Topic newTopic, Topic oldTopic) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.modifyTopic(session.getId(), newTopic, oldTopic);		
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

	public Project createProject(Project project) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.createProject(session.getId(), project);		
	}	

	public void deleteAnswer(Answer a) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		server.deleteAnswer(session.getId(), a);		
	}

	public void deleteProposal(Proposal proposal) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		server.deleteProposal(session.getId(), proposal);
		
	}
	
	public void deleteTopic(Topic topic) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		server.deleteTopic(session.getId(), topic);		
	}

	public ArrayList<Answer> getAnswers() throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.getAnswers(session.getId());
	}

	public ArrayList<Notification> getNotificationsProject() throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.getNotificationsProject(session.getId());
	}

	public ArrayList<Proposal> getProposals() throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		return server.getProposals(session.getId());
	}

	public void createNotification(Notification n) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.createNotification(session.getId(), n);
	}
	
	public void modifyNotification(Notification n) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentNotificationException, Exception {
		server.modifyNotification(session.getId(), n);		
	}

	public void modifyNotificationState(Notification n) throws NotLoggedException, SQLException, NonPermissionRoleException, Exception {
		server.modifyNotificationState(session.getId(), n);		
	}
	
	public void removeNotification(Notification notification) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		server.deleteNotification(session.getId(), notification);		
	}
	
	public void removeNotificationFromUser(Notification notification) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.deleteNotificationFromUser(session.getId(), notification);
	}
	
	public List<Operation> getAvailableOperations() throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		// Only make the request to the server the first time
		if (availableOperations.size() == 0)
			availableOperations = server.getAvailableOperations(session.getId());
		return availableOperations;
	} 
	
	public List<Project> getProjects() throws RemoteException, NonPermissionRoleException, NotLoggedException, SQLException, Exception {
		return server.getProjects(session.getId()); 
	}

	public TopicWrapper getTopicsWrapper(Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getTopicsWrapper(session.getId(), p);
	}

	public List<User> getUsersProject(Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getUsersProject(session.getId(), p);
	}
	
	public List<User> getUsers() throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.getUsers(session.getId());
	}

	public List<Project> getProjectsFromCurrentUser() throws RemoteException, NotLoggedException, Exception {
		return server.getProjectsFromCurrentUser(session.getId());
	}	
	
	public List<Attribute> getAttributesFromProject(Project p) throws RemoteException, Exception {
		return server.getAttributesFromProject(p);
	}
	
	public List<CaseEval> executeAlgorithm(EnumAlgorithmCBR algorithmName, Project caseToEval, ConfigCBR configCBR, int k) throws RemoteException, Exception {
		return server.executeAlgorithm(session.getId(), algorithmName, caseToEval, configCBR, k);
	}
	
	public User getLoggedUser() throws RemoteException, NotLoggedException, Exception {
		return server.getLoggedUser(session.getId());
	}
	
	public Language getDefaultLanguage() throws RemoteException, JDOMException, IOException {
		return LanguagesUtilities.getDefaultLanguage();
	}

	public ArrayList<Language> getLanguages() throws RemoteException, JDOMException, IOException {
		return LanguagesUtilities.getLanguages();
	}

	public void setDefaultLanguage(Language language) throws RemoteException, JDOMException, IOException {
		LanguagesUtilities.setDefaultLanguage(language);
		
	}

	public void closeSession() {
		mainWindowUI = Application.getInstance(JFMain.class);
		mainWindowUI.forceCloseSession();
	}

	public void approachlessServer() {
		mainWindowUI = Application.getInstance(JFMain.class);
		mainWindowUI.approachlessServer();		
	}

	public void notifyKnowledgeAdded(Knowledge k, Knowledge parentK) {
		mainWindowUI = Application.getInstance(JFMain.class);
		mainWindowUI.notifyKnowledgeAdded(k, parentK);
	}

	public void notifyKnowledgeEdited(Knowledge newK, Knowledge oldK) {
		mainWindowUI = Application.getInstance(JFMain.class);
		mainWindowUI.notifyKnowledgeEdited(newK, oldK);
		
	}
	
	public void notifyKnowledgeRemoved(Knowledge k)  {
		mainWindowUI = Application.getInstance(JFMain.class);
		mainWindowUI.notifyKnowledgeRemoved(k);		
	}
	
	public void notifyNotificationAvailable(Notification n) {
		mainWindowUI = Application.getInstance(JFMain.class);
		mainWindowUI.notifyNotificationAvailable(n);		
	}

	// Close main frame and show login frame
	public void closeMainFrame() {
		Application.getInstance(JFMain.class).getMainFrame().dispose();
		startApplication(null);		
	}
	
	// Close controller and application
	public void closeController() throws RemoteException, MalformedURLException {
		if(client != null) {
			client.disabled(clientIP);
		}
		try {	
			Application.getInstance(JFMain.class).getMainFrame().dispose();
		}
		catch(Exception e) { }
	}

	public String getRole() {
		return role;
	}

	public String getUserLogin() {
		return login;
	}

//	public void restartLoginFrame() throws InterruptedException {
//		Thread.sleep(700);
//		startApplication(null);		
//	}
	
	public void restartMainFrame() throws InterruptedException {
		Application.getInstance(JFMain.class).getMainFrame().dispose();
		Thread.sleep(600);
		startApplication(null);
		Application.getInstance(JFMain.class).show();
	}

	public void addProjectsUser(User user, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.addProjectsUser(session.getId(), user, p);
	}

	public int attachFile(Knowledge k, File file) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.attachFile(session.getId(), k, file);
	}

	public byte[] composePDF(PDFConfiguration config, Image header, Image footer) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		return server.composePDF(session.getId(), config, header, footer);
	}

	public void removeProjectsUser(User u, Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.removeProjectsUser(session.getId(), u, p);		
	}

	public void updateProject(Project p) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		server.updateProject(session.getId(), p);		
	}

	public int getCurrentProject() throws RemoteException, NotLoggedException, Exception {
		return server.getCurrentProject(session.getId());
	}

	public byte[] exportInformation(Project project) throws RemoteException, NotLoggedException, NonPermissionRoleException, Exception {
		
		return server.exportInformation(session.getId(), project);
	}

	public void changeStatusKnowledge(Knowledge k) throws NonPermissionRoleException, RemoteException, SQLException, NotLoggedException, Exception {
		server.changeStatusKnowledge(session.getId(), k);
		
	}

	public ArrayList<Notification> getNotificationsUser() throws SQLException, NotLoggedException, RemoteException, Exception {
		return server.getNotificationsUser(session.getId());
	}

	public String getCurrentProjectName() {
		return projectName;
	}
	
	public void setCurrentProjectName(String name) {
		projectName = name;
	}

	public void clearMainFrame() {
		JFMain.clearContent();
		
	}

	public void closeSessionApproachlessServer() {
		session= null;
	}
	
}
