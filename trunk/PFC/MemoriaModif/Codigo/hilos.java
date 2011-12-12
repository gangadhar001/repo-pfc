public class ClientProxy implements IClient {

    ....
	
	@Override
	public void notifyKnowledgeAdded(Knowledge k, Knowledge parentK) throws RemoteException {
		Thread thread;
		
		// Launch the operation on another thread for not stopping the server
		thread = new Thread(new notifyKnowledgeAddedThread(client, k, parentK));
		thread.start();		
	}

	....
	
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
	
	....
}