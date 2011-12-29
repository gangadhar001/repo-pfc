package test.communication;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.itextpdf.text.Image;

import model.business.control.CBR.Attribute;
import model.business.control.CBR.CaseEval;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Coordinates;
import model.business.knowledge.Employee;
import model.business.knowledge.File;
import model.business.knowledge.ISession;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.PDFConfiguration;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Session;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;

import communication.IClient;
import communication.IServer;
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
 * Servidor dummy utilizado en las pruebas del cliente
 */
public class ServerPrueba extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = -6461417903923553869L;

	private final int PUERTO_INICIAL_SERVER = 3995;
	
	private boolean registro;
	private int puerto;

	private HashMap<Long, IClient> clients;

	private TopicWrapper tw;

	private Topic to;

	private Project p;

	private ChiefProject u;
	
	@SuppressWarnings("deprecation")
	public ServerPrueba() throws RemoteException {
		super();
		registro = false;
		clients = new HashMap<Long, IClient>();
		// Datos de pruebas, usados para generar los gráficos estadísticos
		p = new Project("name", "desc", new Date(2001, 0, 12), new Date (2015, 0, 13), 2020.1, 10121, "SW", "Java", 24242);
		tw = new TopicWrapper();
		to = new Topic("name", "desc", new Date());
		u = new ChiefProject("nif", "emp2", "emp", "name", "surname", "email", "telephone", 10, null);
		to.setUser(u);
		to.setProject(p);
		tw.add(to);
	}
	
    public void activate(String ip) throws RemoteException, MalformedURLException {
		boolean puertoUsado;

		// Si el objeto ya estaba exportado, controlamos las
		// excepciones y no las lanzamos hacia arriba
    	try {
    		if(!registro) {
    			// Buscamos un puerto que no esté ya en uso en el equipo
    			puertoUsado = true;
    			puerto = PUERTO_INICIAL_SERVER;
    			do {
    				try {
    					LocateRegistry.createRegistry(puerto);
    					puertoUsado = false;
    				} catch(ExportException e) {
    					puerto++;
    				}
    			} while(puertoUsado);
    			registro = true;
    		}
    		exportObject(this, puerto);
        } catch(ExportException ex) {
        	if(!ex.getMessage().toLowerCase().equals("object already exported")) {
        		throw ex;
        	}
        }
        try {
            Naming.bind("rmi://" + ip + ":" + String.valueOf(puerto) + "/" + IServer.NAME_SERVER, this);
        } catch(AlreadyBoundException ex) {
            Naming.rebind("rmi://" + ip + ":" + String.valueOf(puerto) + "/" + IServer.NAME_SERVER, this);
        }
    }
    
    public void deactivate(String ip) throws RemoteException, MalformedURLException {
		// Si el objeto no estaba exportado, controlamos las
		// excepciones y no las lanzamos hacia arriba
    	try {
    		unexportObject(this, false);
    	} catch(NoSuchObjectException ex) {
    	}
    	try {
    		Naming.unbind("rmi://" + ip + ":" + String.valueOf(puerto) + "/" + IServer.NAME_SERVER);
    	} catch(NotBoundException ex) {
    	}
    }    

	public int getPuerto() {
		return puerto;
	}

	public HashMap<Long, IClient> getClients() {
		return clients;
	}	

	public Project getProject() {
		return p;
	}

	public ChiefProject getUser() {
		return u;
	}

	@Override
	public Answer addAnswer(long arg0, Answer arg1, Proposal arg2)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public void addProjectsUser(long arg0, User arg1, Project arg2)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		
		
	}

	@Override
	public Proposal addProposal(long arg0, Proposal arg1, Topic arg2)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public Topic addTopic(long arg0, Topic arg1) throws RemoteException,
			SQLException, NonPermissionRoleException, NotLoggedException,
			Exception {
		
		return null;
	}

	@Override
	public int attachFile(long arg0, Knowledge arg1, File arg2)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		
		return 0;
	}

	@Override
	public void changeStatusKnowledge(long arg0, Knowledge arg1)
			throws NonPermissionRoleException, RemoteException, SQLException,
			NotLoggedException, Exception {
		
		
	}

	@Override
	public byte[] composePDF(long arg0, PDFConfiguration arg1, Image arg2,
			Image arg3) throws RemoteException, SQLException,
			NonPermissionRoleException, NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public void createNotification(long arg0, Notification arg1)
			throws SQLException, NonPermissionRoleException,
			NotLoggedException, RemoteException, Exception {
		
		
	}

	@Override
	public Project createProject(long arg0, Project arg1)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public void deleteAnswer(long arg0, Answer arg1) throws RemoteException,
			SQLException, NonPermissionRoleException,
			NonExistentAnswerException, NotLoggedException, Exception {
		
		
	}

	@Override
	public void deleteNotification(long arg0, Notification arg1)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, NonExistentNotificationException, Exception {
		
		
	}

	@Override
	public void deleteNotificationFromUser(long arg0, Notification arg1)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, NonExistentNotificationException, Exception {
		
		
	}

	@Override
	public void deleteProposal(long arg0, Proposal arg1)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NonExistentProposalException, NotLoggedException, Exception {
		
		
	}

	@Override
	public void deleteTopic(long arg0, Topic arg1) throws RemoteException,
			SQLException, NonPermissionRoleException,
			NonExistentTopicException, NotLoggedException, Exception {
		
		
	}

	@Override
	public List<CaseEval> executeAlgorithm(long arg0, EnumAlgorithmCBR arg1,
			List<Project> arg2, Project arg3, ConfigCBR arg4, int arg5)
			throws RemoteException, Exception {
		
		return null;
	}

	@Override
	public <T> byte[] exportInformation(long arg0, Project arg1)
			throws RemoteException, NonPermissionRoleException,
			NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public Proposal findParentAnswer(long arg0, Answer arg1)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public Topic findParentProposal(long arg0, Proposal arg1)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public ArrayList<Answer> getAnswers(long arg0) throws RemoteException,
			SQLException, NonPermissionRoleException, NotLoggedException,
			Exception {
		
		return null;
	}

	@Override
	public List<Attribute> getAttributesFromProject(Project arg0)
			throws RemoteException, Exception {
		
		return null;
	}

	@Override
	public ArrayList<Operation> getAvailableOperations(long arg0)
			throws RemoteException, NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public Coordinates getCoordinates(long arg0, Address arg1)
			throws NonExistentAddressException, WSResponseException, Exception {
		
		return null;
	}

	@Override
	public int getCurrentProject(long arg0) throws RemoteException,
			NotLoggedException, Exception {
		
		return 0;
	}

	@Override
	public User getLoggedUser(long arg0) throws RemoteException,
			NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public ArrayList<Notification> getNotificationsProject(long arg0)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public ArrayList<Notification> getNotificationsUser(long arg0)
			throws SQLException, NonPermissionRoleException,
			NotLoggedException, RemoteException, Exception {
		
		return null;
	}

	@Override
	public List<Project> getProjects(long arg0) throws RemoteException,
			NonPermissionRoleException, NotLoggedException, SQLException,
			Exception {
		List<Project> projects = new ArrayList<Project>();
		projects.add(p);
		return projects;
	}

	@Override
	public List<Project> getProjectsFromCurrentUser(long arg0)
			throws RemoteException, NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public ArrayList<Proposal> getProposals(long arg0) throws RemoteException,
			SQLException, NonPermissionRoleException, NotLoggedException,
			Exception {
		
		return null;
	}

	@Override
	public TopicWrapper getTopicsWrapper(long arg0) throws RemoteException,
			SQLException, NonPermissionRoleException, NotLoggedException,
			Exception {
		
		return null;
	}

	@Override
	public TopicWrapper getTopicsWrapper(long arg0, Project arg1)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		return tw;
	}

	@Override
	public List<User> getUsers(long arg0) throws RemoteException, SQLException,
			NonPermissionRoleException, NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public List<User> getUsersProject(long arg0, Project arg1)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		List<User> users = new ArrayList<User>();
		users.add(u);
		return users;
	}

	@Override
	public ISession login(String login, String pass) throws RemoteException,
			IncorrectEmployeeException, SQLException, Exception {
		User u = new Employee("1", login, pass, "name", "surname", "", "", 1, null);
		Session s = new Session(100, u);
		return s;
	}

	@Override
	public Answer modifyAnswer(long arg0, Answer arg1, Answer arg2,
			Proposal arg3) throws RemoteException, NonExistentAnswerException,
			SQLException, NonPermissionRoleException, NotLoggedException,
			Exception {
		
		return null;
	}

	@Override
	public void modifyNotification(long arg0, Notification arg1)
			throws SQLException, NonPermissionRoleException,
			NotLoggedException, NonExistentNotificationException, Exception {
		
		
	}

	@Override
	public void modifyNotificationState(long arg0, Notification arg1)
			throws NotLoggedException, SQLException,
			NonPermissionRoleException, Exception {
		
		
	}

	@Override
	public Proposal modifyProposal(long arg0, Proposal arg1, Proposal arg2,
			Topic arg3) throws RemoteException, NonExistentProposalException,
			SQLException, NonPermissionRoleException, NotLoggedException,
			Exception {
		
		return null;
	}

	@Override
	public Topic modifyTopic(long arg0, Topic arg1, Topic arg2)
			throws RemoteException, SQLException, NonExistentTopicException,
			NonPermissionRoleException, NotLoggedException, Exception {
		
		return null;
	}

	@Override
	public void register(long sessionId, IClient client) throws RemoteException,
			NotLoggedException, Exception {
		clients.put(sessionId, client);
	}

	@Override
	public void removeProjectsUser(long arg0, User arg1, Project arg2)
			throws RemoteException, SQLException, NonPermissionRoleException,
			NotLoggedException, Exception {
		
		
	}

	@Override
	public void setCurrentProject(long arg0, int arg1) throws RemoteException,
			NotLoggedException, Exception {
		
		
	}

	@Override
	public void signout(long sessionId) throws RemoteException, SQLException,
			NotLoggedException, Exception {
		clients.remove(sessionId);		
	}

	@Override
	public void updateProject(long arg0, Project arg1) throws RemoteException,
			SQLException, NonPermissionRoleException, NotLoggedException,
			Exception {
		
		
	}
	
}
