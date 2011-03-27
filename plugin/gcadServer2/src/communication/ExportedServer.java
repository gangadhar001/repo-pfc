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

import model.business.control.Server;
import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

import org.apache.commons.configuration.ConfigurationException;

import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;

/**
 * Class that exports the server instance to be used by clients to execute operations from the server facade (interface)
 */
public class ExportedServer extends UnicastRemoteObject implements IServer {

	private static final String NAME_SERVER = "gcadServer";
	
	private static final long serialVersionUID = 7735848879217866237L;
	
	private IServer server;
	private boolean register;
	
	private static ExportedServer instancia;

	protected ExportedServer() throws RemoteException {
		super();
		server = Server.getInstance();
		register = false;
	}
	
	public static ExportedServer getServer() throws RemoteException {
		if(instancia == null) {
			instancia = new ExportedServer();
		}
		return instancia;
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
	public void login(String user, String pass) throws IncorrectEmployeeException, SQLException, NonExistentRole, RemoteException {
		server.login(user, pass);
		
	}

	@Override
	public void signout() throws SQLException, RemoteException {
		server.signout();
		
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
	public void notifyActionAllowed() throws RemoteException, ConfigurationException, NonPermissionRole {
		server.notifyActionAllowed();		
	}
    
  	
}
