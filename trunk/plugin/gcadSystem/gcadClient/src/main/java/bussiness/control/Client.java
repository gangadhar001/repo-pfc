package bussiness.control;

import java.rmi.RemoteException;
import java.util.List;

import model.business.knowledge.Knowledge;

import communication.IClient;

/***
 * This class is used by the server to notify changes to connected clients. 
 * It implements the remote interface 
 **/
public class Client implements IClient {

	private static final long serialVersionUID = -4312084563366631475L;
	
	private ClientController controller;
	
	public Client() {
	}
	
	public ClientController getControlador() {
		return controller;
	}
	
	public void setController(ClientController controller) {
		this.controller = controller;
	}
	
	@Override
	public void cerrarSesion() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cerrarSesionEliminacion() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void servidorInaccesible() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyActionsAllowed(List<String> actions) throws RemoteException {
		controller.notifyActionsAllowed(actions);
		
	}

	@Override
	public void notifyConnection(boolean connected) throws RemoteException {
		controller.notifyConnection(connected);
		
	}

	@Override
	public void notifyKnowledgeAdded(Knowledge k) throws RemoteException {
		controller.notifyKnowledgeAdded(k);
		
	}

	@Override
	public void notifyKnowledgeEdited(Knowledge k) throws RemoteException {
		controller.notifyKnowledgeEdited(k);
		
	}

	@Override
	public void notifyKnowledgeRemoved(Knowledge k) throws RemoteException {
		controller.notifyKnowledgeRemoved(k);
		
	}

}
