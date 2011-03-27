package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * TODO: cambiar
 * Interface with operations that can request to customers, and must implement the applications 
 * they want to register on the server.
 */
public interface IClient extends Remote {
	
	public final String NOMBRE_CLIENTE = "cliente";
	
	public void actualizarVentanas(int operacion, Object dato) throws RemoteException;
	
	public void servidorInaccesible() throws RemoteException;
	
	public void cerrarSesion() throws RemoteException;
	
	public void cerrarSesionEliminacion() throws RemoteException;
	
}
