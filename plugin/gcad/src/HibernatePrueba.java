import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import model.business.knowledge.Address;
import model.business.knowledge.Categories;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;

import org.hibernate.classic.Session;

import persistence.XMLAgent;
import persistence.utils.HibernateUtil;

public class HibernatePrueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*Address a = new Address(1, "street", "city", "country", "zip");
		Company c = new Company(1, "cif2", "name", "information", a);
		User u = new ChiefProject("nif1", "login1", "password", "name", "surname", "email", "telephone", c);
		Set<Project> projects = new HashSet<Project>();
		Project p1 = new Project("name1", "description", new Date(), new Date(), 212.12, 1, "domain", "progLanguage", 12);
		projects.add(p1); 
		u.setProjects(projects);
		Topic t = new Topic("adad", new Date());
		t.setProject(p1);*/
		
		
		HibernateUtil.setDatabaseURL("127.0.0.1");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		// CONSULTA

		
		TopicWrapper t = new TopicWrapper();
		t.setTopics((ArrayList<Topic>) session.createQuery("From Topic where Id=3").list());
		User u = (ChiefProject)(session.createQuery("From User where Id=4").list()).get(0);
		Topic lo = t.getTopics().get(0);
		Proposal p = new Proposal("title2", "description", new Date(), Categories.Design, 0);
		p.setUser(u);
		lo.add(p);
		session.save(p);
		session.getTransaction().commit();
		
		/*try {
			XMLAgent.marshal("prueba.xml", TopicWrapper.class, (TopicWrapper)t);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		// INSERT
		/*session.save(a);
		session.save(c);
		session.save(u);
		session.save(t);

		session.getTransaction().commit();
		session.close();*/
		
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
