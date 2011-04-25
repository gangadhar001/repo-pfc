package model.business.control;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import communication.IClient;

import model.business.knowledge.ISession;
import model.business.knowledge.Knowledge;
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
	// Common operations for all roles. Used for internal purposes
	private static ArrayList<Operation> commonOperations;

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

	public static Session getSession(long sessionId) {
		return sessions.get(sessionId);
	}

	public static ArrayList<Operation> getAvailableOperations(long sessionId)
	{
		// Retrieve the available operations from profile file.
		ArrayList<Operation> operations = new ArrayList<Operation>();

		Session session = SessionController.getSession(sessionId);

		XMLConfiguration configFile;
		List<String> rolesName;
		List<String> profiles = new ArrayList<String>();
		List<String> profilesName;

		List<HierarchicalConfiguration> profilesConfiguration;
		int index;

		try
		{
			// Take the role of the logged user
			UserRole role = UserRole.values()[session.getRole()];
			// Load XML Roles file
			configFile = new XMLConfiguration(IResources.XML_PROFILES_PATH + "profiles-role.xml");
			// Use XPath expressions

			// Take defined roles in that XML file (returns a list)
			rolesName = configFile.getList("Role[@name]");
			// Check it the user role that is logged exists in that list
			if (rolesName.contains(role.toString()))
			{
				// Take the profiles of that role
				index = rolesName.indexOf(role.toString());
				profilesConfiguration = configFile.configurationsAt("Role(" + index + ").Profile");
				profiles = new ArrayList<String>();
				for (Iterator<HierarchicalConfiguration> it = profilesConfiguration.iterator(); it.hasNext(); )
				{
					HierarchicalConfiguration sub = it.next();
					// Reads profiles name for that role
					String profileName = sub.getString("name");
					profiles.add(profileName);
				}
				// Load operations XML
				configFile = new XMLConfiguration(IResources.XML_PROFILES_PATH + "profiles.xml");
				profilesName = configFile.getList("Profile[@name]");

				// For each profile, take groups, subgroups and operations and create an "Operation" object
				List<String> groupsName;
				List<String> subgroupsName;
				List<String> operationsName;
				Operation op;
				for (int i = 0; i < profilesName.size(); i++)
				{
					// If this profile belongs to the given role, reads its content
					if (profiles.contains(profilesName.get(i)))
					{
						// Reads groups
						groupsName = configFile.getList("Profile(" + i + ").Group[@id]");
						// For each group, read subgroup
						for (int j = 0; j < groupsName.size(); j++)
						{
							subgroupsName = configFile.getList("Profile(" + i + ").Group(" + j + ").Subgroup[@id]");
							// If there aren't any subgroups, take operations
							if (subgroupsName.size() == 0)
							{
								operationsName = configFile.getList("Profile(" + i + ").Group(" + j + ").Operations.id");
								op = new Operation(groupsName.get(j), "", operationsName);
								result.add(op);
							}
							else
							{
								// If there are subgroups, take operations for each one
								for (int k = 0; k < subgroupsName.size(); k++)
								{
									operationsName = configFile.getList("Profile(" + i + ").Group(" + j + ").Subgroup(" + k + ").Operations.id");
									op = new Operation(groupsName.get(j), subgroupsName.get(k), operationsName);
									result.add(op);
								}
							}
						}
					}
				}
			}
			else
			{
				throw new NonPermissionRole(BundleInternationalization.getString("Exception.NonPermissionRole") + role.toString());
			}
		}
		catch (ConfigurationException e)
		{
			// TODO: cambiar
			throw new NonPermissionRole(BundleInternationalization.getString("Exception.NonPermissionRole"));
		}
		return result;
	}

	// Method used for other managers to check the permissions of a user
	public static void checkPermission(long sessionId, Operation operation) 
	{
		ArrayList<Operation> operations;
		Session session;

		// Check if the session is valid
		session = SessionController.getSession(sessionId);
		if (session == null)
		{
			// TODO:
			throw new SesionInvalidaException("El identificador de la sesión es inválido.");
		}

		// Get the list of operations available to the user, defined in the profiles role file
		operation = getAvailableOperations(sessionId);
		// Append operations common for all roles
		operation.addAll(getCommonOperations());

		// Check if you have permission to perform operation
		if (!checkOperation(operations, operation))
			// TODO:
			throw new ;
	}

	// Method used to retrieve common operations for all users. It is done only the first time.
	private static ArrayList<Operation> getCommonOperations()
	{
		if (commonOperations == null)
		{
			commonOperations = new ArrayList<Operation>();
			commonOperations.add(new Operation(Groups.Knowldege, Subgroups.Topic, Operation.Get));
			commonOperations.add(new Operation(Groups.Knowldege, Subgroups.Proposal, Operation.Get));
			commonOperations.add(new Operation(Groups.Knowldege, Subgroups.Answer, Operation.Get));
			commonOperations.add(new Operation(Groups.Knowldege, Subgroups.Topic, Operation.Get)); 
			commonOperations.add(new Operation(Groups.Knowldege, Subgroups.Topic, Operation.Get));
		}
		return commonOperations;
	}

	// The operation is valid if it matches the group and subgroup and, furthermore, 
	// the operation is contained in the list of operations of that group.
	// The operation received shall be formed by a group, a subgroup and a single operation
	private static boolean checkOperation(ArrayList<Operation> operations, Operation operation)
	{
		boolean result = false;
		for (Operation op: operations) 
		{
			result = (op.getGroup().Equals(operation.getGroup()) && op.getSubgroup().Equals(operation.getSubgroup()));
			if (result)
			{
				result = (op.getOperations().contains(operation.getOperations().get(0)));
			}
		}
		return result;
	}
}
