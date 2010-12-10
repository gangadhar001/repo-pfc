package model.business.control;

import exceptions.IncorrectEmployeeException;
import exceptions.IncorrectOptionException;
import exceptions.InvalidSessionException;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import persistence.PFEmployee;

import model.business.knowledge.Operations;
import model.business.knowledge.Session;
import model.business.knowledge.User;
import model.business.knowledge.UserRole;

public class SessionController {

	// Table that associates the session ID with the Session Object
	private static Hashtable<Long, Session> sessions = new Hashtable<Long, Session>();

	/**
	 * Method that log in an user and creates a session
	 * @throws SQLException 
	 * @throws IncorrectEmployeeException 
	 */
	public static void login(String login, String password) throws IncorrectEmployeeException, SQLException {
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
		
	}

	public static Hashtable<Long, Session> getSessions() {
		return sessions;
	}

	// TODO: traducir
	public static void checkPermission(long idSesion, Operations operation) throws IncorrectOptionException, InvalidSessionException {
		Vector<Operations> operations;
		Session session;
		
		// Comprobamos si la sesión es válida
		session = sessions.get(idSesion);
		if(session == null) {
			throw new InvalidSessionException("El identificador de la sesión es inválido.");
		}

		// Obtenemos la lista de operaciones disponibles para el usuario
		operations = availableOperations(idSesion);

		// Comprobamos si se tienen permisos para realizar la operación
		if(!operations.contains(operation)) {
			throw new IncorrectOptionException("El rol " + UserRole.values()[(int)session.getRol()] + " no tiene permiso para realizar la operación " + operation.toString() + ".");
		}
	}
		
	
	// Método que devuelve las operaciones que puede realizar el usuario con el servidor
	public static Vector<Operations> availableOperations(long idSesion) {
		Vector<Operations> operations;
		Session session = sessions.get(idSesion);
		// Agregamos las operaciones permitidas para todos los usuarios
		operations = new Vector<Operations>();
		
		// TODO: añadir operaciones para cualquier usuario
		/*operaciones.add(Operaciones.ConsultarPropioUsuario);
		operaciones.add(Operaciones.ConsultarBeneficiario);
		operaciones.add(Operaciones.ConsultarCentros);
		operaciones.add(Operaciones.ConsultarVolante);
		operaciones.add(Operaciones.CorrespondeNIFUsuario);*/
		
		// Agregamos las operaciones permitidas para el jefe de proyecto (o admin)
		if(session.getRol() == UserRole.ChiefProject.ordinal()) {
			operations.add(Operations.CreateProject);
			// TODO: añadir mas operaciones
			/*operations.add(Operaciones.ModificarBeneficiario);
			operations.add(Operaciones.EliminarBeneficiario);
			operaciones.add(Operaciones.ConsultarMedico);
			operaciones.add(Operaciones.ConsultarMedicoCita);
			operaciones.add(Operaciones.ConsultarCitasBeneficiario);
			operaciones.add(Operaciones.ConsultarCitasMedico);
			operaciones.add(Operaciones.TramitarCita);
			operaciones.add(Operaciones.TramitarCitaVolante);
			operaciones.add(Operaciones.AnularCita);*/
		}
		
		return operations;
	}	
}
