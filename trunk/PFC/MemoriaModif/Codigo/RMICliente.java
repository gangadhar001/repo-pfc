public class ProxyServer implements IServer {
	....
	
	public void connectServer(String ip, int port) throws MalformedURLException, RemoteException, NotBoundException {
		String url;
		
		url = "rmi://" + ip + ":" + String.valueOf(port) + "/" + NAME_SERVER;
		server = (IServer)Naming.lookup(url);
	}	