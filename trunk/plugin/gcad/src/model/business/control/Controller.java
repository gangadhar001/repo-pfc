package model.business.control;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.knowledge.AbstractProposal;
import model.business.knowledge.ISession;
import model.business.knowledge.Proposal;
import exceptions.IncorrectEmployeeException;
import exceptions.NoProjectProposalsException;

public class Controller {

	private ISession session;
	
	private static Controller instance = null;
		
	public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	public void login (String user, String pass) throws IncorrectEmployeeException, SQLException {
		session = SessionController.login(user, pass);
	}
	
	public void notifyLogin () {
		//TODO: se notifica la conexion con la base de datos a las vistas
		PresentationController.notifyConnection(true);
		// TODO: se notifican los permisos
		PresentationController.notifyPermission(SessionController.availableOperations(session.getId()));
	}
	
	public static void addKnowledge (AbstractProposal p, Proposal parent) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		KnowledgeController.addKnowledge(p, parent);
	}
	
	public static void notifyKnowledgeAdded() {
		PresentationController.notifyProposals();
	}
	
	public static ArrayList<AbstractProposal> getProposals() throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return KnowledgeController.getProposals();
	}
	
	/* TODO: añadir funcionalidades para que esta clase gestione los casos
	de uso del plugin */
	
	public boolean isLogged () {
		return session != null;
	}

	
}
