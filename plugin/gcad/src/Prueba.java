import java.util.Date;

import javax.xml.bind.JAXBException;

import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import persistence.XMLAgent;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Proposal a = new Proposal();
		
		Proposal p = new Proposal("a", "b", new Date(), Categories.Analysis, 0);
		Proposal p1 = new Proposal("c", "d", new Date(), Categories.Analysis, 1);
		p1.add(new Proposal("e", "f", new Date(), Categories.Analysis, 1));
		p.add(p1);
		p.add(new Answer("a1","adae",new Date()));
		a.add(p);
		
		try {
			XMLAgent.marshal("prueba.xml", Proposal.class, (Proposal)p);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
