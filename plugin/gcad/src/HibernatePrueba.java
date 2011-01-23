import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
import persistence.XMLAgent;
import utils.HibernateUtil;

public class HibernatePrueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Address a = new Address(1, "street", "city", "country", "zip");
		Company c = new Company(1, "cif2", "name", "information", a);
		User u = new ChiefProject("nif1", "login1", "password", "name", "surname", "email", "telephone", c);
		Set<Project> projects = new HashSet<Project>();
		projects.add(new Project("name1", "description", new Date(), new Date(), 212.12, 1, "domain", "progLanguage", 12)); 
		u.setProjects(projects);
		
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		// CONSULTA
		List b = session.createQuery("From Topic where projectId=2").list();
		System.out.println(b);
		
		TopicWrapper t = new TopicWrapper();
		t.setTopics((ArrayList<Topic>) b);
		try {
			XMLAgent.marshal("prueba.xml", TopicWrapper.class, (TopicWrapper)t);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// INSERT
		/*session.save(a);
		session.save(c);
		session.save(u);
		//session.save(topic);
		session.getTransaction().commit();
		session.close();
		
		/*Topic t1 = new Topic("Tema 1");
		Topic t2 = new Topic("Tema 2");
		
		Proposal p1 = new Proposal("P1", "D1", new Date(), Categories.Analysis, 0);
		Proposal p2 = new Proposal("P2", "D2", new Date(), Categories.Design, 1);
		Proposal p3 = new Proposal("P3", "D3", new Date(), Categories.Analysis, 0);
		
		Answer a1 = new Answer("A1", "A1", new Date());
		Answer a2 = new Answer("A2", "A2", new Date());
		
		p1.add(a1);
		p1.add(a2);
		
		t1.add(p1);
		t1.add(p2);
		t2.add(p3);
		
		TopicWrapper t = new TopicWrapper();
		t.add(t1);
		t.add(t2);
		try {
			XMLAgent.marshal("prueba.xml", TopicWrapper.class, (TopicWrapper)t);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
