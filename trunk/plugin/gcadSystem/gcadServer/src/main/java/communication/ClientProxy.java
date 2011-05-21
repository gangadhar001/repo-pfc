package communication;

import java.rmi.RemoteException;
import java.util.List;

import model.business.knowledge.Knowledge;

/**
 * TODO: cambiar
 * Proxy used to connect with clients and request operation from that client
 */
public class ClientProxy implements IClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8805121423986535150L;
	private IClient cliente;
	
	public void associate(IClient cliente) {
		this.cliente = cliente;
	}	
	
	public void cerrarSesion() throws RemoteException {
		Thread hilo;
		
		// Lanzamos la operación en otro hilo para no detener el servidor
		hilo = new Thread(new HiloCerrarSesion(cliente));
		hilo.start();
	}

	public void cerrarSesionEliminacion() throws RemoteException {
		Thread hilo;
		
		// Lanzamos la operación en otro hilo para no detener el servidor
		hilo = new Thread(new HiloCerrarSesionEliminacion(cliente));
		hilo.start();
	}
	
	public void servidorInaccesible() throws RemoteException {
		Thread hilo;
		
		// Lanzamos la operación en otro hilo para no detener el servidor
		hilo = new Thread(new HiloServidorInaccesible(cliente));
		hilo.start();
	}
	
	/**
	 * Hilo utilizado para lanzar la operación cerrarSesion en un cliente.
	 */
	private class HiloCerrarSesion implements Runnable {
	
		private IClient cliente;
		
		public HiloCerrarSesion(IClient cliente) {
			this.cliente = cliente;
		}
		
		public void run() {
			try {
				cliente.cerrarSesion();
			} catch(Exception e) {
				// Aquí no se puede manejar la excepción
			}
		}
		
	}
	
	/**
	 * Hilo utilizado para lanzar la operación cerrarSesionEliminacion en un cliente.
	 */
	private class HiloCerrarSesionEliminacion implements Runnable {
	
		private IClient cliente;
		
		public HiloCerrarSesionEliminacion(IClient cliente) {
			this.cliente = cliente;
		}
		
		public void run() {
			try {
				cliente.cerrarSesionEliminacion();
			} catch(Exception e) {
				// Aquí no se puede manejar la excepción
			}
		}
		
	}

	
	/**
	 * Hilo utilizado para lanzar la operación servidorInaccesible en un cliente.
	 */
	private class HiloServidorInaccesible implements Runnable {
	
		private IClient cliente;
		
		public HiloServidorInaccesible(IClient cliente) {
			this.cliente = cliente;
		}
		
		public void run() {
			try {
				cliente.servidorInaccesible();
			} catch(Exception e) {
				// Aquí no se puede manejar la excepción
			}
		}
		
	}

	@Override
	public void notifyActionsAllowed(List<String> actionsName)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyConnection(boolean connected) throws RemoteException {
Thread hilo;
		
		// Lanzamos la operación en otro hilo para no detener el servidor
		hilo = new Thread(new notifyConnectionHilo(cliente, true));
		hilo.start();
	}

private class notifyConnectionHilo implements Runnable {
	
	private IClient cliente;
	private boolean conn;
	
	public notifyConnectionHilo(IClient cliente, boolean conn) {
		this.cliente = cliente;
		this.conn = conn;
	}
	
	public void run() {
		try {
			cliente.notifyConnection(conn);
		} catch(Exception e) {
			// Aquí no se puede manejar la excepción
		}
	}
	
}

	@Override
	public void notifyKnowledgeRemoved(Knowledge k) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyKnowledgeEdited(Knowledge k) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyKnowledgeAdded(Knowledge k) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
