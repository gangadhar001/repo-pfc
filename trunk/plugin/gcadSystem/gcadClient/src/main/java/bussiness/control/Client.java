package bussiness.control;

import java.rmi.RemoteException;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;

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
	
	public void setController(ClientController controller) {
		this.controller = controller;
	}
	
	@Override
	public void closeSession() throws RemoteException {
		controller.closeSession();

	}
	
	@Override
	public void notifyKnowledgeAdded(Knowledge k) throws RemoteException {
		controller.notifyKnowledgeAdded(k);
		
	}

	@Override
	public void notifyKnowledgeEdited(Knowledge newK, Knowledge oldK) throws RemoteException {
		controller.notifyKnowledgeEdited(newK, oldK);
		
	}

	@Override
	public void notifyKnowledgeRemoved(Knowledge k) throws RemoteException {
		controller.notifyKnowledgeRemoved(k);
		
	}

	@Override
	public void approachlessServer() throws RemoteException {
		controller.approachlessServer();
		
	}

	@Override
	public void notifyNotificationAvailable(Notification n) throws RemoteException {
		controller.notifyNotificationAvailable(n);		
	}

}
