package gcad.persistence;

import gcad.communications.DBConnectionManager;
import gcad.domain.knowledge.AbstractProposal;
import gcad.domain.knowledge.Answer;
import gcad.domain.knowledge.Proposal;
import gcad.exceptions.NoProjectProposalsException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/**
 * This class allows to query and insert proposals into database
 */
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
	 * This method returns all proposals and answers hierarchy of a project, keeping the tree hierarchy.
	 */
	public static ArrayList<AbstractProposal> queryProposalTreeProject(int projectId) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		SQLCommand command;
		ResultSet data;
		AbstractProposal proposal;
		// This list represents the tree hierarchy. Only proposals that are upper roots (without father) are added to the list.
		ArrayList<AbstractProposal> primaryRoots = new ArrayList<AbstractProposal>();
		Hashtable<Integer, AbstractProposal> proposals = new Hashtable<Integer, AbstractProposal>();
		ArrayList<Answer> answers;
		int parent;
		
		command = new SQLCommandSentence("SELECT * FROM " + PROPOSAL_TABLE
				+ " WHERE " + COL_PROJECT_ID + " = ?", projectId);
		data = DBConnectionManager.query(command);
		data.next();
		
		// If data are not obtained, it is because there are no proposals for this project
		if(data.getRow() == 0) {
			data.close();
			throw new NoProjectProposalsException("");
		} else {
			do {
				proposal = new Proposal();
				proposal.setId(data.getInt(COL_ID));
				proposal.setTitle(data.getString(COL_NAME));
				proposal.setDescription(data.getString(COL_DESCRIPTION));
				proposal.setDate(new Date(data.getTimestamp(COL_DATE).getTime()));
				// Set answers of this proposal
				answers = PFAnswer.queryAnswersFromProposal(proposal.getId());
				for (Answer a: answers) {
					proposal.add(a);
				}
				parent = data.getInt(COL_PARENT_PROPOSAL);
				// If this proposal has father, this proposal is added like a child of this father
				if (parent != 0) {
					proposals.get(parent).add(proposal);
				}
				
				// If this proposal has not father, it is upper root
				else 
					primaryRoots.add(proposal);
				
				proposals.put(proposal.getId(), proposal);
			} while(data.next());
			data.close();
		}
		return primaryRoots;
	}
	
	/**
	 * TODO: usado??? Este metodo devuelve todas las propuestas (sin jerarquia) de un proyecto
	 * 
	 
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
			throw new NoProjectProposalsException("");
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
	}*/
	
	public static void insert (Proposal proposal, int idParent) throws SQLException {
		SQLCommand command;
		ResultSet data;
		
		// TODO: Cambiar con variables (no constantes)
		// Insert the new proposal into database 
		command = new SQLCommandSentence("INSERT INTO " + PROPOSAL_TABLE
				+ " (" + COL_NAME + ", " + COL_DESCRIPTION
				+ ", "	+ COL_DATE + ", " + COL_CATEGORY
				+ ", " + COL_PROJECT_ID + ", " + COL_EMPLOYEE_ID
				+ ", " + COL_PARENT_PROPOSAL
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?)",
				proposal.getTitle(), proposal.getDescription(), proposal.getDate(), proposal.getCategory().toString(), 2, 3, idParent);
		DBConnectionManager.execute(command);
		
		// Retrieve the autonumber id assigned to the new proposal
		command = new SQLCommandSentence("SELECT LAST_INSERT_ID()");			
		data = DBConnectionManager.query(command);
		data.next();
		proposal.setId(data.getInt("LAST_INSERT_ID()"));
		data.close();
	}
	

}
