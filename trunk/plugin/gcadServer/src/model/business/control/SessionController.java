package model.business.control;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import communication.IClient;

import model.business.knowledge.ISession;
import model.business.knowledge.Session;
import model.business.knowledge.User;
import persistence.DAOUser;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;
import exceptions.NotLoggedException;

/**
 * This class represents a controller that allows to manage user sessions.
 */
public class SessionController {

	// Table that associates the session ID with the Session Object
	private static Hashtable<Long, Session> sessions = new Hashtable<Long, Session>();

	/**
	 * Method that log in an user and creates a session
	 */
	public static Session login(String login, String password) throws IncorrectEmployeeException, SQLException, NonExistentRole {
		Enumeration<Session> openedSessions; 
		Session session, openedSession;
		User user;
		Random rnd;
		String encryptedPassword;
		boolean found;
		long idSesion;
				
		/* TODO: Encriptamos la contraseña del usuario
		try {
			passwordEncriptada = UtilidadesDominio.encriptarPasswordSHA1(password);			
		} catch(NoSuchAlgorithmException e) {
			throw new SQLException("No se puede encriptar la contraseña del usuario.");
		}*/
		// Comprobamos el login y la contraseña encriptada del usuario
		user = DAOUser.queryUser(login, password);

		// Comprobamos si el usuario ya tenía una sesión iniciada
		openedSessions = sessions.elements();
		openedSession = null;
		found = false;
		while(openedSessions.hasMoreElements() && !found) {
			openedSession = openedSessions.nextElement();
			if(openedSession.getUser().getNif().equals(user.getNif())) {
				found = true;
			}
		}
		
		// Close previous opened session
		if(found) {
			IClient client = ClientsController.getClient(openedSession.getId());
			if (client != null)
				try {
					// Forzamos a que el cliente antiguo salga del sistema
					client.cerrarSesion();
				} catch(RemoteException e) {
					// Ignoramos la excepción
				}
			sessions.remove(openedSession.getId());
			// TODO: para usar un log ServidorFrontend.getServidor().liberar(sesionAbierta.getId());
		}

		// Generate unique ID
		rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		do {
			idSesion = Math.abs(rnd.nextLong());
		} while(sessions.containsKey(idSesion));

		session = new Session(idSesion, user);
		sessions.put(idSesion, session);
		
		return session;
		
	}

	public static Hashtable<Long, Session> getSessions() {
		return sessions;
	}

	public static void signout(long sessionID) throws NotLoggedException {
		Session session;
		
		// Comprobamos si la sesión es válida
		session = sessions.get(sessionID);
		if(session == null) {
			throw new NotLoggedException();
		}

		// Quitamos la sesión y el cliente
		sessions.remove(sessionID);
	}

	public static void disconnectClients() throws RemoteException {
		sessions = new Hashtable<Long, Session>();
		
	}
}
