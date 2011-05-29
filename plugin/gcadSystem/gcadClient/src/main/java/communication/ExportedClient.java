package communication;

import internationalization.ApplicationInternationalization;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;
import bussiness.control.Client;

/**
 * Class that exports the client instance to be used by the server, to notify changes.
 *
 */
public class ExportedClient extends UnicastRemoteObject implements IClient{

	private static final long serialVersionUID = -8947755537963610413L;
	
	private final int INITIAL_CLIENT_PORT = 3995;
	private final int FINAL_CLIENT_PORT = INITIAL_CLIENT_PORT + 1000;

	private IClient client;
	private boolean registry;
	private int port;
	
	private static ExportedClient instance;

	protected ExportedClient() throws RemoteException {
		super();
		registry = false;
		client = new Client();
	}
	
	public static ExportedClient getClient() throws RemoteException {
		if(instance == null) {
			instance = new ExportedClient();
		}
		return instance;
	}
	
    public void activate(String ip) throws RemoteException, MalformedURLException {
		boolean usedPort;

		// If the client is already exports, don't throw exception
    	try {
    		if(!registry) {
    			// Look for an unused port
    			usedPort = true;
    			port = INITIAL_CLIENT_PORT;
    			do {
    				try {
    					LocateRegistry.createRegistry(port);
    					usedPort = false;
    				} catch(ExportException e) {
    					port++;
    				}
    				if(port > FINAL_CLIENT_PORT) {
    					throw new ExportException(ApplicationInternationalization.getString("ExportedClient_NoSuchPort"));
    				}
    			} while(usedPort);
    			registry = true;
    		}
    		exportObject(this, port);
        } catch(ExportException ex) {
        	if(!ex.getMessage().toLowerCase().equals("object already exported")) {
        		throw ex;
        	}
        }
        try {
        	
            Naming.bind("rmi://" + ip + ":" + String.valueOf(port) + "/" + "cliente", this);
        } catch(AlreadyBoundException ex) {
            Naming.rebind("rmi://" + ip + ":" + String.valueOf(port) + "/" + "cliente", this);
        }
    }
    
    public void disabled(String ip) throws RemoteException, MalformedURLException, NotBoundException {
    	try {
    		unexportObject(this, false);
    	} catch(NoSuchObjectException ex) {
    	}
    	try {
    		Naming.unbind("rmi://" + ip + ":" + String.valueOf(port) + "/" + "cliente");
    	} catch(NotBoundException ex) {
    	}
    }
    
    public IClient getExportedClient() {
    	return client;
    }
    
    public int getListenPort() {
    	return port;
    }

	@Override
	public void approachlessServer() throws RemoteException {
		client.approachlessServer();		
	}

	@Override
	public void closeSession() throws RemoteException {
		client.closeSession();		
	}

	@Override
	public void notifyKnowledgeAdded(Knowledge k) throws RemoteException {
		client.notifyKnowledgeAdded(k);		
	}

	@Override
	public void notifyKnowledgeEdited(Knowledge newK, Knowledge oldK) throws RemoteException {
		client.notifyKnowledgeEdited(newK, oldK);		
	}

	@Override
	public void notifyKnowledgeRemoved(Knowledge k) throws RemoteException {
		client.notifyKnowledgeRemoved(k);		
	}

	@Override
	public void notifyNotificationAvailable(Notification n) throws RemoteException {
		client.notifyNotificationAvailable(n);		
	}
    

	
}
