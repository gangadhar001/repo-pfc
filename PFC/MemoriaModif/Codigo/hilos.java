public class ClientProxy implements IClient {

    ....
	
	@Override
	public void notifyKnowledgeAdded(Knowledge k, Knowledge parentK) throws RemoteException {
		Thread thread;
		
		// Launch the operation on another thread for not stopping the server
		thread = new Thread(new notifyKnowledgeAddedThread(client, k, parentK));
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
	public void notifyKnowledgeRemoved(Knowledge k) throws RemoteException {
		Thread thread;
		
		// Launch the operation on another thread for not stopping the server
		thread = new Thread(new notifyKnowledgeRemovedThread(client, k));
		thread.start();		
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
	
	....
}