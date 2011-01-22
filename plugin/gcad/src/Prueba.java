import java.util.Date;

import javax.xml.bind.JAXBException;

import org.hibernate.classic.Session;

import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
import persistence.XMLAgent;
import utils.HibernateUtil;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Address a = new Address(1, "street", "city", "country", "zip");
		Company c = new Company(1, "cif", "name", "information", a);
		User u = new Employee("nif", "login", "password", "name", "surname", "email", "telephone", c);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(u);
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
