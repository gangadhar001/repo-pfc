import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gcad.domain.AbstractProposal;
import gcad.domain.Answer;
import gcad.domain.Proposal;
import gcad.exceptions.NoProjectProposalsException;
import gcad.persistence.PFProposal;
import gcad.proposals.models.ProposalManager;


public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ProposalManager manager = ProposalManager.getManager();
			 /*Object[] a = PFProposal.queryProposalTreeProject(1);
			 for (Object p: a) {
				 Proposal ej = (Proposal)p;
				 System.out.println(ej.toString());
				 List<AbstractProposal> b = ej.getProposals();
				 for (AbstractProposal c: b){
					 if (c instanceof Proposal) {
						 System.out.println("\tP "+c.toString());
					 }
					 else if (c instanceof Answer)
						 System.out.println("\tA "+c.getInformation());
				 }
			 }*/
			System.out.println(manager.getProposals());
			 	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoProjectProposalsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
