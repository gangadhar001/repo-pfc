import java.util.Date;


import gcad.domain.Answer;
import gcad.domain.Proposal;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Proposal root = new Proposal("Raiz", "prueba", new Date(),0);

		root.add(new Answer("Nodo1", "a", new Date()));

		root.add(new Answer("Nodo2", "b", new Date()));

		Proposal comp = new Proposal("Raiz2", "prueba2", new Date(),1);

		comp.add(new Answer("Nodo3", "c", new Date()));

		comp.add(new Answer("Nodo4", "d", new Date()));


		root.add(comp);


		System.out.println(root.getInformation());

		
	}

}
