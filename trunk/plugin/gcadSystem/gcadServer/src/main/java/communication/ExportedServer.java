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
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import model.business.control.Server;
import model.business.knowledge.Answer;
import model.business.knowledge.ISession;
import model.business.knowledge.Notification;
import model.business.knowledge.Operation;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

import org.apache.commons.configuration.ConfigurationException;

import exceptions.IncorrectEmployeeException;
import exceptions.NoProposalsException;
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

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
    
    public IServer getExportedServer() {
    	return server;
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
	public void register(long sessionID, IClient client) throws RemoteException, NotLoggedException, Exception {
		server.register(sessionID, client);		
	}
    

	@Override
	public void addTopic(long sessionId, Topic topic) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException {
		server.addTopic(sessionId, topic);
		
	}

	@Override
	public void addProposal(long sessionId, Proposal p, Topic parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException {
		server.addProposal(sessionId, p, parent);
		
	}

	@Override
	public void addAnwser(long sessionId, Answer a, Proposal parent) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException {
		server.addAnwser(sessionId, a, parent);
		
	}

	@Override
	public void modifyTopic(long sessionId, Topic newTopic, Topic oldTopic) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException {
		server.modifyTopic(sessionId, newTopic, oldTopic);
	}

	@Override
	public void modifyProposal(long sessionId, Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException, NonPermissionRole, InstantiationException, IllegalAccessException, ClassNotFoundException, NotLoggedException {
		server.modifyProposal(sessionId, newProposal, oldProposal, parent);		
	}

	@Override
	public void modifyAnswer(long sessionId, Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException, NonPermissionRole, InstantiationException, IllegalAccessException, ClassNotFoundException, NotLoggedException {
		server.modifyAnswer(sessionId, newAnswer, oldAnswer, parent);
		
	}

	public Proposal findParentAnswer(long sessionId, Answer a) throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		return server.findParentAnswer(sessionId, a);
	}
	
	public Topic findParentProposal(long sessionId, Proposal p) throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		return server.findParentProposal(sessionId, p);
	}		
	
	@Override
	public TopicWrapper getTopicsWrapper(long sessionId) throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		return server.getTopicsWrapper(sessionId);
	}

	@Override
	public void setCurrentProject(long sessionId, int idProject)  throws RemoteException, NotLoggedException {
		server.setCurrentProject(sessionId, idProject);
		
	}

	@Override
	public void deleteTopic(long sessionId, Topic to) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProposal(long sessionId, Proposal p) throws RemoteException, SQLException, NonPermissionRole, NotLoggedException {
		server.deleteProposal(sessionId, p);
		
	}

	@Override
	public void deleteAnswer(long sessionId, Answer a) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createProject(long sessionId, Project p) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}


//	public void addNotification(long sessionId, Notification notification)  throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
//	}

	@Override
	public void removeNotification(long sessionId, Notification notification)
	 throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Notification> getNotifications(long sessionId)  throws RemoteException, SQLException, NonPermissionRole, NotLoggedException {
		return server.getNotifications(sessionId);
	}

	@Override
	public ArrayList<Proposal> getProposals(long sessionId)  throws RemoteException, SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, NonPermissionRole, NotLoggedException {
		return server.getProposals(sessionId);
	}

	@Override
	public ArrayList<Answer> getAnswers(long sessionId)  throws RemoteException, SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Operation> getAvailableOperations(long sessionId) throws RemoteException,
			NonPermissionRole, NotLoggedException {
		return server.getAvailableOperations(sessionId);
	}
  	
}
