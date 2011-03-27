package communication;

import java.rmi.RemoteException;

/**
 * TODO: cambiar
 * Proxy used to connect with clients
 */
public class ClientProxy implements IClient {

	private IClient cliente;
	
	public void asociar(IClient cliente) {
		// Este proxy no se conecta a un objeto remoto con 'Naming.lookup' porque
		// el objeto remoto ya lo proporciona el cliente al llamar a 'registrar'
		this.cliente = cliente;
	}

	// M�todos del cliente

	public void actualizarVentanas(int operacion, Object dato) throws RemoteException {
		Thread hilo;
		
		// Lanzamos la operaci�n en otro hilo para no detener el servidor
		hilo = new Thread(new HiloActualizarVentanas(cliente, operacion, dato));
		hilo.start();
	}
	
	public void cerrarSesion() throws RemoteException {
		Thread hilo;
		
		// Lanzamos la operaci�n en otro hilo para no detener el servidor
		hilo = new Thread(new HiloCerrarSesion(cliente));
		hilo.start();
	}

	public void cerrarSesionEliminacion() throws RemoteException {
		Thread hilo;
		
		// Lanzamos la operaci�n en otro hilo para no detener el servidor
		hilo = new Thread(new HiloCerrarSesionEliminacion(cliente));
		hilo.start();
	}
	
	public void servidorInaccesible() throws RemoteException {
		Thread hilo;
		
		// Lanzamos la operaci�n en otro hilo para no detener el servidor
		hilo = new Thread(new HiloServidorInaccesible(cliente));
		hilo.start();
	}
	
	/**
	 * Hilo utilizado para lanzar la operaci�n cerrarSesion en un cliente.
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
				// Aqu� no se puede manejar la excepci�n
			}
		}
		
	}
	
	/**
	 * Hilo utilizado para lanzar la operaci�n cerrarSesionEliminacion en un cliente.
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
				// Aqu� no se puede manejar la excepci�n
			}
		}
		
	}

	/**
	 * Hilo utilizado para lanzar la operaci�n actualizarVentanas en un cliente.
	 */
	private class HiloActualizarVentanas implements Runnable {
	
		private IClient cliente;
		private int operacion;
		private Object dato;
		
		public HiloActualizarVentanas(IClient cliente, int operacion, Object dato) {
			this.cliente = cliente;
			this.operacion = operacion;
			this.dato = dato;
		}
		
		public void run() {
			try {
				cliente.actualizarVentanas(operacion, dato);
			} catch(Exception e) {
				// Aqu� no se puede manejar la excepci�n
			}
		}
		
	}
	
	/**
	 * Hilo utilizado para lanzar la operaci�n servidorInaccesible en un cliente.
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
				// Aqu� no se puede manejar la excepci�n
			}
		}
		
	}

}
