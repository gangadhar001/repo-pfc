package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import model.business.knowledge.Answer;
import model.business.knowledge.ISession;
import model.business.knowledge.Notification;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

import org.apache.commons.configuration.ConfigurationException;

import exceptions.IncorrectEmployeeException;
import exceptions.NoProposalsException;
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

/**
 * Remote interface that is used by the client applications, in order to request operations to the server.
 * This is the server Facade
 *
 */
public interface IServer extends Remote {
	
	public static final String NAME_SERVER = "gcadServer";

	/*** Methods used to manage login and signout ***/
	public ISession login (String user, String pass) throws RemoteException, IncorrectEmployeeException, SQLException, NonExistentRole;
	
	public void signout(long sessionID) throws RemoteException, SQLException, NotLoggedException ;

	public void register(long sessionID, IClient client) throws RemoteException, NotLoggedException;
		
//	public void initDBConnection(String ip, String port) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
//		DBConnectionManager.addConnection(new DBConnection(ip, Integer.parseInt(port), "dbgcad"));
//	}
//	
	/*** Methods used to add new Knowledge ***/
	public void addTopic (Topic topic) throws RemoteException,  SQLException;
	
	public void addProposal (Proposal p, Topic parent) throws RemoteException,  SQLException;
	
	public void addAnwser (Answer a, Proposal parent) throws RemoteException,  SQLException;
	
	/*** Methods used to modify Knowledge ***/
	public void modifyTopic(Topic newTopic, Topic oldTopic) throws RemoteException, SQLException;
	
	public void modifyProposal(Proposal newProposal, Proposal oldProposal, Topic parent) throws RemoteException,  SQLException;
	
	public void modifyAnswer(Answer newAnswer, Answer oldAnswer, Proposal parent) throws RemoteException,  SQLException;
		
	/*** Methods used to delete Knowledge ***/
	public void deleteTopic(Topic to) throws RemoteException, SQLException;
	
	public void deleteProposal(Proposal p) throws RemoteException, SQLException;

	public void deleteAnswer (Answer a) throws RemoteException, SQLException;
	
	
	public void createProject (Project p) throws RemoteException, SQLException;
		
	/*** Methods used to manage notifications ***/
	public void addNotification(Notification notification)  throws RemoteException, SQLException ;
	
	public void removeNotification(Notification notification)  throws RemoteException, SQLException ;

	public ArrayList<Notification> getNotifications()  throws RemoteException, SQLException;
	
	/*** Auxiliary methods ***/
	public ArrayList<Proposal> getProposals()  throws RemoteException,  SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoProposalsException ;
	
	public ArrayList<Answer> getAnswers()  throws RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException ;
	
//	public ArrayList<Project> getProjectsUser() {
//		// TODO: return los proyectos del usuario de la sesion
//		// return ProjectController.getProjectsUser(session.getUser().getId());
//		return null;
//	}	
	
	/*** Auxiliary methods ***/
	public void setCurrentProject(int idProject)  throws RemoteException;
	
	public TopicWrapper getTopicsWrapper() throws RemoteException,  SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
		
	/*** Methods used to manage the UI observer ***/
//	public void notifyLogin () {
//		ClientsController.notifyConnection(true);
//	}
//	
//	public void notifySignOut () {
//		ClientsController.notifyConnection(false);
//	}
//	
//	public void notifyKnowledgeAdded(Knowledge k) {
//		ClientsController.notifyKnowledgeAdded(k);
//	}
//	
//	public void notifyKnowledgeChanged(Knowledge k) {
//		ClientsController.notifyKnowledgeEdited(k);
//	}
//	
//	public void notifyKnowledgeRemoved(Knowledge k) {
//		ClientsController.notifyKnowledgeRemoved(k);
//	}
	
}
