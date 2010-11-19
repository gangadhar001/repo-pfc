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
	private static final String COL_PARENT_PROPOSAL = "parentProposal";
	
	public static Object[] queryProposalTreeProject(int projectId) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		SQLCommand command;
		ResultSet data;
		AbstractProposal proposal;
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
				// Existe padre
				if (parent != 0)
					proposals.get(parent).add(proposal);
				// Si no tiene padre, es raiz del arbol
				else 
					proposals.put(proposal.getId(), proposal);
			} while(data.next());
			data.close();
		}
		return proposals.values().toArray();
	}
	

}
