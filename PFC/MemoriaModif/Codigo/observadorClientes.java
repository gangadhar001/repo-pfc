public class ClientsController {

	private static Hashtable<Long, IClient> clients = new Hashtable<Long, IClient>();

	public static void attach(long sessionID, IClient client) {
		clients.put(sessionID, client);
	}

	public static void detach(long sessionID) {
		clients.remove(sessionID);
	}
	
    public static void notifyKnowledgeAdded(long sessionId, Knowledge k, Knowledge parentK) throws RemoteException {
		// Notify the clients (except the client that launched the operation) about the operation, in order to refresh their view
		for(Long id : clients.keySet()) 
			if (id != sessionId)
				clients.get(id).notifyKnowledgeAdded(k, parentK);
	}
	
	....
}
	       