import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gcad.domain.control.KnowledgeManager;
import gcad.domain.knowledge.AbstractProposal;
import gcad.domain.knowledge.Answer;
import gcad.domain.knowledge.Categories;
import gcad.domain.knowledge.Proposal;
import gcad.exceptions.NoProjectProposalsException;
import gcad.persistence.PFProposal;


public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

			//KnowledgeManager manager = KnowledgeManager.getManager();
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
			System.out.println(Categories.valueOf("analysis"));
			 	
		

	}

}
