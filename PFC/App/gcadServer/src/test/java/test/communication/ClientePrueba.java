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

import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;

import communication.IClient;

/**
 * Cliente dummy utilizado en las pruebas del servidor
 */
public class ClientePrueba extends UnicastRemoteObject implements IClient {

	private static final long serialVersionUID = -6461417903923553869L;

	private final int PUERTO_INICIAL_CLIENTE = 3995;

	private Object ultimoDato;
	private boolean llamadoServidorInaccesible;
	private boolean llamadoCerrarSesion;
	
	private boolean registro;
	private int puerto;
	
	public ClientePrueba() throws RemoteException {
		super();
		registro = false;
		llamadoServidorInaccesible = false;
		llamadoCerrarSesion = false;
		ultimoDato = null;
	}
	
    public void activate(String ip) throws RemoteException, MalformedURLException {
		boolean puertoUsado;

		// Si el objeto ya estaba exportado, controlamos las
		// excepciones y no las lanzamos hacia arriba
    	try {
    		if(!registro) {
    			// Buscamos un puerto que no esté ya en uso en el equipo
    			puertoUsado = true;
    			puerto = PUERTO_INICIAL_CLIENTE;
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
            Naming.bind("rmi://" + ip + ":" + String.valueOf(puerto) + "/" + IClient.CLIENT_NAME, this);
        } catch(AlreadyBoundException ex) {
            Naming.rebind("rmi://" + ip + ":" + String.valueOf(puerto) + "/" + IClient.CLIENT_NAME, this);
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
    		Naming.unbind("rmi://" + ip + ":" + String.valueOf(puerto) + "/" + IClient.CLIENT_NAME);
    	} catch(NotBoundException ex) {
    	}
    }
	
	public void approachlessServer() throws RemoteException {
		llamadoServidorInaccesible = true;
	}
	
	public void closeSession() throws RemoteException {
		llamadoCerrarSesion = true;
	}


	public Object getUltimoDato() {
		return ultimoDato;
	}

	public boolean isLlamadoServidorInaccesible() {
		return llamadoServidorInaccesible;
	}

	public boolean isLlamadoCerrarSesion() {
		return llamadoCerrarSesion;
	}

	@Override
	public void notifyKnowledgeRemoved(Knowledge k) throws RemoteException {
		ultimoDato = k;	
	}

	@Override
	public void notifyKnowledgeEdited(Knowledge newK, Knowledge oldK)
			throws RemoteException {
		ultimoDato = newK;		
	}

	@Override
	public void notifyKnowledgeAdded(Knowledge k, Knowledge parentK) throws RemoteException {
		ultimoDato = k;
	}

	@Override
	public void notifyNotificationAvailable(Notification n)
			throws RemoteException {
		ultimoDato = n;
		
	}

	public void setUltimoDato(Object object) {
		ultimoDato = object;
		
	}
	
}
