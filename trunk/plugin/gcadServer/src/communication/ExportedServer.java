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
import java.util.Vector;

import model.business.control.Server;
import model.business.knowledge.Answer;
import model.business.knowledge.ISession;
import model.business.knowledge.Notification;
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
	public ISession login(String user, String pass) throws IncorrectEmployeeException, SQLException, NonExistentRole, RemoteException {
		return server.login(user, pass);
		
	}

	@Override
	public void signout(long sessionID) throws SQLException, RemoteException, NotLoggedException {
		server.signout(sessionID);
		
	}
	
	@Override
	public void register(long sessionID, IClient client) throws RemoteException, NotLoggedException {
		server.register(sessionID, client);		
	}
    

	@Override
	public void addTopic(Topic topic) throws RemoteException, SQLException {
		server.addTopic(topic);
		
	}

	@Override
	public void addProposal(Proposal p, Topic parent) throws RemoteException, SQLException {
		server.addProposal(p, parent);
		
	}

	@Override
	public void addAnwser(Answer a, Proposal parent) throws RemoteException, SQLException {
		server.addAnwser(a, parent);
		
	}

	@Override
	public void modifyTopic(Topic newTopic, Topic oldTopic) throws RemoteException, SQLException {
		server.modifyTopic(newTopic, oldTopic);
	}

	@Override
	public void modifyProposal(Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException, SQLException {
		server.modifyProposal(newProposal, oldProposal, parent);		
	}

	@Override
	public void modifyAnswer(Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException, SQLException {
		server.modifyAnswer(newAnswer, oldAnswer, parent);
		
	}

	@Override
	public TopicWrapper getTopicsWrapper() throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return server.getTopicsWrapper();
	}

	@Override
	public void setCurrentProject(int idProject)  throws RemoteException {
		server.setCurrentProject(idProject);
		
	}

	@Override
	public void deleteTopic(Topic to) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProposal(Proposal p) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAnswer(Answer a) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createProject(Project p) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNotification(Notification notification)  throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeNotification(Notification notification)
	 throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Notification> getNotifications()  throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Proposal> getProposals()  throws RemoteException, SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, NoProposalsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Answer> getAnswers()  throws RemoteException, SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
  	
}
