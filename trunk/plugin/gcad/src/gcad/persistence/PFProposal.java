package gcad.persistence;

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
		
		// TODO: cambiar la forma de llamar al agente. Hacerlo desde otra clase gestora de la base de datos
		// TODO: ¿¿ Usar el patron de ISO para hacerlo extensible a nuevas bases de datos ??
		Agent ag = Agent.getAgente();
		ag.setIp("localhost");
		ag.setPort(3306);
		ag.open();
		
		data = ag.query(command);
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
		ag.close();
		return proposals.values().toArray();
	}
	

}
