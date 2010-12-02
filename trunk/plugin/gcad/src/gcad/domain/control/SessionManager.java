package gcad.domain.control;

import gcad.domain.knowledge.Session;
import gcad.domain.knowledge.User;
import gcad.exceptions.IncorrectEmployeeException;
import gcad.persistence.PFEmployee;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

public class SessionManager {

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
	
	
	/* Método que devuelve las operaciones que puede realizar el usuario con el servidor
	public static Vector<Operaciones> operacionesDisponibles(long idSesion) throws SesionInvalidaException {
		Vector<Operaciones> operaciones;
		Sesion sesion;
		
		// Comprobamos si la sesión es válida
		sesion = sesiones.get(idSesion);
		if(sesion == null) {
			throw new SesionInvalidaException("El identificador de la sesión es inválido.");
		}
		
		// Agregamos las operaciones permitidas para todos los usuarios
		operaciones = new Vector<Operaciones>();
		operaciones.add(Operaciones.ConsultarPropioUsuario);
		operaciones.add(Operaciones.ConsultarBeneficiario);
		operaciones.add(Operaciones.ConsultarCentros);
		operaciones.add(Operaciones.ConsultarVolante);
		operaciones.add(Operaciones.CorrespondeNIFUsuario);
		
		// Agregamos las operaciones permitidas para citadores y administradores
		if(sesion.getRol() == RolesUsuario.Administrador.ordinal() || sesion.getRol() == RolesUsuario.Citador.ordinal()) {
			operaciones.add(Operaciones.RegistrarBeneficiario);
			operaciones.add(Operaciones.ModificarBeneficiario);
			operaciones.add(Operaciones.EliminarBeneficiario);
			operaciones.add(Operaciones.ConsultarMedico);
			operaciones.add(Operaciones.ConsultarMedicoCita);
			operaciones.add(Operaciones.ConsultarCitasBeneficiario);
			operaciones.add(Operaciones.ConsultarCitasMedico);
			operaciones.add(Operaciones.TramitarCita);
			operaciones.add(Operaciones.TramitarCitaVolante);
			operaciones.add(Operaciones.AnularCita);
		}
		
		// Agregamos las operaciones permitidas para administradores
		if(sesion.getRol() == RolesUsuario.Administrador.ordinal()) {
			operaciones.add(Operaciones.ConsultarUsuario);
			operaciones.add(Operaciones.RegistrarUsuario);
			operaciones.add(Operaciones.ModificarUsuario);
			operaciones.add(Operaciones.EliminarUsuario);
			operaciones.add(Operaciones.RegistrarMedico);
			operaciones.add(Operaciones.ModificarMedico);
			operaciones.add(Operaciones.EliminarMedico);
			operaciones.add(Operaciones.ConsultarMedicosTipo);
			operaciones.add(Operaciones.ConsultarSustitutosPosibles);
			operaciones.add(Operaciones.EstablecerSustituto);
			operaciones.add(Operaciones.ConsultarBeneficiariosMedico);
		}
		
		// Agregamos las operaciones permitidas para médicos
		if(sesion.getRol() == RolesUsuario.Médico.ordinal()) {
			operaciones.add(Operaciones.ConsultarMedicosTipo);
			operaciones.add(Operaciones.EmitirVolante);
			operaciones.add(Operaciones.ConsultarCitasPropiasMedico);
		}
		
		return operaciones;
	}
	
	// Método utilizado por otros gestores para comprobar los permisos de un usuario
	public static void comprobarPermiso(long idSesion, Operaciones operacion) throws SesionInvalidaException, OperacionIncorrectaException {
		Vector<Operaciones> operaciones;
		Sesion sesion;
		
		// Comprobamos si la sesión es válida
		sesion = sesiones.get(idSesion);
		if(sesion == null) {
			throw new SesionInvalidaException("El identificador de la sesión es inválido.");
		}

		// Obtenemos la lista de operaciones disponibles para el usuario
		operaciones = operacionesDisponibles(idSesion);

		// Comprobamos si se tienen permisos para realizar la operación
		if(!operaciones.contains(operacion)) {
			throw new OperacionIncorrectaException("El rol " + RolesUsuario.values()[(int)sesion.getRol()] + " no tiene permiso para realizar la operación " + operacion.toString() + ".");
		}
	}*/

	
}
