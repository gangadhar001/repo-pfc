package gcad.persistence;

import gcad.communications.DBConnectionManager;
import gcad.domain.AbstractProposal;
import gcad.domain.Answer;
import gcad.domain.Proposal;
import gcad.exceptions.NoProjectProposalsException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class PFProposal {
	
	private static final String PROPOSAL_TABLE = "proposals";
	
	private static final String COL_ID = "id";
	private static final String COL_NAME = "name";
	private static final String COL_DESCRIPTION = "description";
	private static final String COL_DATE = "date";
	private static final String COL_CATEGORY = "category";
	private static final String COL_PROJECT_ID = "projectId";
	private static final String COL_EMPLOYEE_ID = "employeeId";
	private static final String COL_PARENT_PROPOSAL = "parentProposal";
	
	/** 
	 * Este metodo devuelve toda la jerarquia propuestas y respuestas de un proyecto, manteniendo
	 * la jerarqui de árbol.
	 */
	public static Object[] queryProposalTreeProject(int projectId) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		SQLCommand command;
		ResultSet data;
		AbstractProposal proposal;
		// Esta lista representa la jerarquia del arbol. Solo se añaden propuestas que sean raices superiores (sin padre),
		// junto con todos sus hijos.
		ArrayList<AbstractProposal> primaryRoots = new ArrayList<AbstractProposal>();
		Hashtable<Integer, AbstractProposal> proposals = new Hashtable<Integer, AbstractProposal>();
		ArrayList<Answer> answers;
		int parent;
		
		// Consultamos la base de datos
		command = new SQLCommandSentence("SELECT * FROM " + PROPOSAL_TABLE
				+ " WHERE " + COL_PROJECT_ID + " = ?", projectId);
		data = DBConnectionManager.query(command);
		data.next();
		
		// TODO: ingles. Si no se obtienen datos, es porque no existen propuestas para ese proyecto
		if(data.getRow() == 0) {
			data.close();
			throw new NoProjectProposalsException();
		} else {
			do {
				
				proposal = new Proposal();
				proposal.setId(data.getInt(COL_ID));
				proposal.setTitle(data.getString(COL_NAME));
				proposal.setDescription(data.getString(COL_DESCRIPTION));
				proposal.setDate(new Date(data.getTimestamp(COL_DATE).getTime()));
				// TODO: leer sus answers
				answers = PFAnswer.queryAnswersFromProposal(proposal.getId());
				for (Answer a: answers) {
					proposal.add(a);
				}
				parent = data.getInt(COL_PARENT_PROPOSAL);
				// Si tiene padre, se recupera dicho padre para añadir un hijo
				if (parent != 0) {
					proposals.get(parent).add(proposal);
				}
				
				// Si no tiene padre, es raiz primaria del arbol
				else 
					primaryRoots.add(proposal);
				
				proposals.put(proposal.getId(), proposal);
			} while(data.next());
			data.close();
		}
		return primaryRoots.toArray();
	}
	
	/**
	 * Este metodo devuelve todas las propuestas (sin jerarquia) de un proyecto
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static ArrayList<AbstractProposal> queryProposalsProject(int projectId) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		SQLCommand command;
		ResultSet data;
		AbstractProposal proposal;
		ArrayList<AbstractProposal> proposals = new ArrayList<AbstractProposal>();
		ArrayList<Answer> answers;
		
		// Consultamos la base de datos
		command = new SQLCommandSentence("SELECT * FROM " + PROPOSAL_TABLE
				+ " WHERE " + COL_PROJECT_ID + " = ?", projectId);
		data = DBConnectionManager.query(command);
		data.next();
		
		// TODO: ingles. Si no se obtienen datos, es porque no existen propuestas para ese proyecto
		if(data.getRow() == 0) {
			data.close();
			throw new NoProjectProposalsException();
		} else {
			do {
				
				proposal = new Proposal();
				proposal.setId(data.getInt(COL_ID));
				proposal.setTitle(data.getString(COL_NAME));
				proposal.setDescription(data.getString(COL_DESCRIPTION));
				proposal.setDate(new Date(data.getTimestamp(COL_DATE).getTime()));
				answers = PFAnswer.queryAnswersFromProposal(proposal.getId());
				for (Answer a: answers) {
					proposal.add(a);
				}
				proposals.add(proposal);
			} while(data.next());
			data.close();
		}
		return proposals;
	}
	
	public static void insert (Proposal proposal, int idParent) throws SQLException {
		SQLCommand command;
		ResultSet data;
		
		// TODO: Cambiar con variables (no constantes)
		command = new SQLCommandSentence("INSERT INTO " + PROPOSAL_TABLE
				+ " (" + COL_NAME + ", " + COL_DESCRIPTION
				+ ", "	+ COL_DATE + ", " + COL_CATEGORY
				+ ", " + COL_PROJECT_ID + ", " + COL_EMPLOYEE_ID
				+ ", " + COL_PARENT_PROPOSAL
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?)",
				proposal.getTitle(), proposal.getDescription(), proposal.getDate(), "analysis", 2, 3, idParent);
		DBConnectionManager.execute(command);
		
		// Recuperamos el id autonumérico asignado a la nueva cita
		command = new SQLCommandSentence("SELECT LAST_INSERT_ID()");			
		data = DBConnectionManager.query(command);
		data.next();
		proposal.setId(data.getInt("LAST_INSERT_ID()"));
		data.close();
	}
	

}
