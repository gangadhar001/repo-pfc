package model.business.control;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import model.business.knowledge.ISession;
import model.business.knowledge.Session;
import model.business.knowledge.User;
import persistence.PFEmployee;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;

public class SessionController {

	// Table that associates the session ID with the Session Object
	private static Hashtable<Long, Session> sessions = new Hashtable<Long, Session>();

	/**
	 * Method that log in an user and creates a session
	 */
	public static ISession login(String login, String password) throws IncorrectEmployeeException, SQLException, NonExistentRole {
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
		user = PFEmployee.queryUser(login, password);

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
		
		// Si el usuario ya tenía una sesion iniciada, se cierra
		if(found) {
			sessions.remove(openedSession.getId());
			// TODO: para usar un log ServidorFrontend.getServidor().liberar(sesionAbierta.getId());
		}

		// Creamos un identificador único para la nueva sesión
		rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		do {
			idSesion = Math.abs(rnd.nextLong());
		} while(sessions.containsKey(idSesion));

		// Creamos la sesión y la guardamos en la tabla de sesiones
		session = new Session(idSesion, user);
		sessions.put(idSesion, session);
		
		return (ISession) session;
		
	}

	public static Hashtable<Long, Session> getSessions() {
		return sessions;
	}

	public static void signout(ISession session) {
		sessions.remove(session.getId());		
	}
}
