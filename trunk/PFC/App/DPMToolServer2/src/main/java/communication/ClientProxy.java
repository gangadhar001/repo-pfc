package communication;

import java.rmi.RemoteException;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;

/**
 * Proxy used to connect with clients and request operation from that client
 */
public class ClientProxy implements IClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8805121423986535150L;
	private IClient client;
	
	public void associate(IClient client) {
		this.client = client;
	}	
	
	public void closeSession() throws RemoteException {
		Thread thread;
		
		// Launch the operation on another thread for not stopping the server
		thread = new Thread(new closeSessionThread(client));
		thread.start();
	}
	
	public void approachlessServer() throws RemoteException {
		Thread thread;
		
		// Launch the operation on another thread for not stopping the server
		thread = new Thread(new approachlessServerThread(client));
		thread.start();
	}

	@Override
	public void notifyKnowledgeRemoved(Knowledge k) throws RemoteException {
		Thread thread;
		
		// Launch the operation on another thread for not stopping the server
		thread = new Thread(new notifyKnowledgeRemovedThread(client, k));
		thread.start();		
	}
	
	@Override
	public void notifyKnowledgeEdited(Knowledge newK, Knowledge oldK) throws RemoteException {
		Thread thread;
		
		// Launch the operation on another thread for not stopping the server
		thread = new Thread(new notifyKnowledgeEditedThread(client, newK, oldK));
		thread.start();		
	}
	
	@Override
	public void notifyKnowledgeAdded(Knowledge k, Knowledge parentK) throws RemoteException {
		Thread thread;
		
		// Launch the operation on another thread for not stopping the server
		thread = new Thread(new notifyKnowledgeAddedThread(client, k, parentK));
		thread.start();		
	}
	
	@Override
	public void notifyNotificationAvailable(Notification n) throws RemoteException {
		Thread thread;
		
		// Launch the operation on another thread for not stopping the server
		thread = new Thread(new notifyNotificationAvailableThread(client, n));
		thread.start();		
	}

	
	/**
	 * Threads used to launch operations in a client
	 */
	private class closeSessionThread implements Runnable {
	
		private IClient client;
		
		public closeSessionThread(IClient client) {
			this.client = client;
		}
		
		public void run() {
			try {
				client.closeSession();
			} catch(Exception e) {
			}
		}		
	}

	private class approachlessServerThread implements Runnable {
	
		private IClient client;
		
		public approachlessServerThread(IClient client) {
			this.client = client;
		}
		
		public void run() {
			try {
				client.approachlessServer();
			} catch(Exception e) {
			}
		}		
	}
	
	private class notifyKnowledgeRemovedThread implements Runnable {
		
		private IClient client;
		private Knowledge k;
		
		public notifyKnowledgeRemovedThread(IClient client, Knowledge k) {
			this.client = client;
			this.k = k;
		}
		
		public void run() {
			try {
				client.notifyKnowledgeRemoved(k);
			} catch(Exception e) {
			}
		}		
	}
	
	private class notifyKnowledgeEditedThread implements Runnable {
		
		private IClient client;
		private Knowledge newK;
		private Knowledge oldK;
		
		public notifyKnowledgeEditedThread(IClient client, Knowledge newK, Knowledge oldK) {
			this.client = client;
			this.newK = newK;
			this.oldK = oldK;
		}
		
		public void run() {
			try {
				client.notifyKnowledgeEdited(newK, oldK);
			} catch(Exception e) {
			}
		}		
	}
	
	private class notifyKnowledgeAddedThread implements Runnable {
		
		private IClient client;
		private Knowledge k;
		private Knowledge parentK;
		
		public notifyKnowledgeAddedThread(IClient client, Knowledge k, Knowledge parentK) {
			this.client = client;
			this.k = k;
			this.parentK = parentK;
		}
		
		public void run() {
			try {
				client.notifyKnowledgeAdded(k, parentK);
			} catch(Exception e) {
			}
		}		
	}
	
	private class notifyNotificationAvailableThread implements Runnable {
		
		private IClient client;
		private Notification n;
		
		public notifyNotificationAvailableThread(IClient client, Notification n) {
			this.client = client;
			this.n = n;
		}
		
		public void run() {
			try {
				client.notifyNotificationAvailable(n);
			} catch(Exception e) {
			}
		}		
	}
}
