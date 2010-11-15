package gcad.proposals.models;

import gcad.domain.Proposal;
import gcad.exceptions.NoProjectProposalsException;
import gcad.listeners.ProposalListener;
import gcad.persistence.PFProposal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProposalManager {

	//TODO: singleton
	
	private static ProposalManager manager;
	private List<ProposalListener> listeners = new ArrayList<ProposalListener>();
	
	public ProposalManager () {	
	}
	
	public static ProposalManager getManager() {
		if (manager == null) {
			manager = new ProposalManager();
		}
		return manager;
	}
	
	public void makeProposalsTree (Proposal root) {
		
		// TODO: traducir a ingles. Manejar excepciones. Se muestra a la inversa

		// Se toma la jerarquia de propuestas de la base de datos
		Object[] proposals;
		try {
			proposals = PFProposal.queryProposalTreeProject(2);
			// Cada elemento del array son raíces de subarboles. Es decir, son propuestas
			for (Object p : proposals) {
				root.add((Proposal)p);
			}
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
		
		
		/*Proposal ex = new Proposal("Raiz", "prueba", new Date(),0);

		ex.add(new Answer("Nodo1", "a", new Date()));

		ex.add(new Answer("Nodo2", "b", new Date()));
		

		Proposal comp = new Proposal("Raiz2", "prueba2", new Date(),1);

		comp.add(new Answer("Nodo3", "c", new Date()));
		root.add(ex);
		root.add(comp);*/
	}
	
	public void addProposalManagerListener(ProposalListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	
	public void removeProposalManagerListener(ProposalListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}
	
	
}
