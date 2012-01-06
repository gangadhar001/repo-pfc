package test.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.business.control.Server;
import model.business.knowledge.Address;
import model.business.knowledge.Categories;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.ISession;
import model.business.knowledge.PDFConfiguration;
import model.business.knowledge.PDFElement;
import model.business.knowledge.PDFSection;
import model.business.knowledge.PDFTable;
import model.business.knowledge.PDFText;
import model.business.knowledge.PDFTitle;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.User;
import test.communication.BaseTest;

import com.itextpdf.text.Image;
import communication.DBConnection;
import communication.DBConnectionManager;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;


public class PDFTest extends BaseTest {	

	private User chief, employee;
	private Company company;
	private Address address;
	private Project project1;
	private ISession session;

	private Topic topic;
	private Proposal proposal;
	
	protected void setUp() {	
		try {
			// Preparamos la base de datos
			super.setUp();
			// Creamos un administrador de prueba
			DBConnectionManager.clear();
			DBConnectionManager.addConnection(new DBConnection());
			address = new Address("street", "city", "country", "zip", "address");
			company = new Company("456as", "company", "information", address);
			employee = new Employee("12345678L", "emp1", "emp1", "User1", "emp1", "", "", 2, company);
			chief = new ChiefProject("65413987L", "emp2", "emp2", "User", "chief", "", "", 12, company);
			project1 = new Project("GCAD", "Desktop Swing application used to manage knowledge in DGS", new Date(), new Date(), 23458.1, 12341, "dgs", "java", 25604);
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(address);
			DBConnectionManager.insert(company);
			DBConnectionManager.insert(chief);
			DBConnectionManager.insert(employee);
			DBConnectionManager.finishTransaction();			
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	protected void tearDown() {
		try {
			// Cerramos la base de datos
			super.tearDown();
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testPDFInvalidSession() {	
		try {
			// Se intenta crear un PDF con una sesión inválida
			Server.getInstance().composePDF(-15, null, null, null);
			fail("Se esperaba NotLoggedException");
		} catch (NotLoggedException e) {
		} catch (Exception e) {
			fail("Se esperaba NotLoggedException");
		}
	}
	
	public void testPDFInvalidPermission() {		
		try {
			// // Se intenta crear un PDF sin tener permiso
			session = Server.getInstance().login("emp1", "emp1");
			Server.getInstance().composePDF(session.getId(), null, null, null);
			fail("Se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) {
		} catch (Exception e) {
			fail("Se esperaba NonPermissionRoleException");
		}
	}
		
	public void testComposePDF() {			
		// Prueba de generación de PDF
		try {
			session = Server.getInstance().login("emp2", "emp2");
			project1.setId(Server.getInstance().createProject(session.getId(), project1).getId());
			Server.getInstance().addProjectsUser(session.getId(), chief, project1);
			Server.getInstance().setCurrentProject(session.getId(), project1.getId());
			topic = new Topic("topic", "desc", new Date());
			proposal = new Proposal("pro", "desc", new Date(), Categories.Development);
			Server.getInstance().addTopic(session.getId(), topic);
			Server.getInstance().addProposal(session.getId(), proposal, topic);
			
			// Se crean los elementos del documento PDF
			 List<PDFSection> sections = new ArrayList<PDFSection>();
			 List<PDFElement> elements = new ArrayList<PDFElement>();
			 PDFTitle title = new PDFTitle("Decisions for the project: " + project1.getName());
			 PDFText text = new PDFText("The following table summarizes the decisions taken in the project: " + project1.getName());
			 PDFTable table = new PDFTable(project1);
			 elements.add(title);
			 elements.add(text);
			 elements.add(table);
			 PDFSection section = new PDFSection(elements);
			 sections.add(section);
			 
			 // Se toman imagenes de cabecera y pie de pagina
			 Image header = Image.getInstance(PDFTest.class.getClassLoader().getResource("images/Topic.png"));
			 Image footer = Image.getInstance(PDFTest.class.getClassLoader().getResource("images/Answer.png"));
			 // Se lanza el algoritmo
			 PDFConfiguration config = new PDFConfiguration(sections);
			 if (config.isValid()) {
				 byte[] result = Server.getInstance().composePDF(session.getId(), config, header, footer);
				 assertTrue(result.length > 0);
			 }
			 
			 // Casos no correctos
			 ((PDFTitle)elements.get(0)).setTitle("");
			 assertEquals(config.isValid(), false);
			 ((PDFText)elements.get(1)).setContent("");
			 assertEquals(config.isValid(), false);
			 ((PDFTable)elements.get(2)).setProject(null);
			 assertEquals(config.isValid(), false);
			 elements.remove(0);
			 assertEquals(config.isValid(), false);
			 elements.clear();
			 assertEquals(config.isValid(), false);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
}
