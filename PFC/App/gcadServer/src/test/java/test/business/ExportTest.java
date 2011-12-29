package test.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.business.control.Server;
import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.AnswerArgument;
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
import model.business.knowledge.Session;
import model.business.knowledge.Topic;
import model.business.knowledge.User;
import test.communication.PruebasBase;

import com.itextpdf.text.Image;
import communication.DBConnection;
import communication.DBConnectionManager;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;


public class ExportTest extends PruebasBase {	

	private User chief, employee;
	private Company company;
	private Address address;
	private Project project1;
	private ISession session;

	private Topic topic;
	private Proposal proposal;
	private Answer ans;
	private Proposal pro;
	
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
			ans = new Answer("ans", "desc", new Date(), AnswerArgument.Neutral.name());
			pro = new Proposal("pro1", "desc", new Date(), Categories.Analysis);
			topic = new Topic("topic1", "desc", new Date());			
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(address);
			DBConnectionManager.insert(company);
			DBConnectionManager.insert(chief);
			DBConnectionManager.insert(employee);
			DBConnectionManager.finishTransaction();	
			// Iniciar sesión e insertar decisiones a exportar 
			ISession s = Server.getInstance().login("emp2", "emp2");
			project1.setId(Server.getInstance().createProject(s.getId(), project1).getId());
			Server.getInstance().addProjectsUser(s.getId(), chief, project1);
			Server.getInstance().setCurrentProject(s.getId(), project1.getId());
			Server.getInstance().addTopic(s.getId(), topic);
			Server.getInstance().addProposal(s.getId(), pro, topic);
			Server.getInstance().addAnswer(s.getId(), ans, pro);
					
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
	
	public void testExportInvalidSession() {	
		try {
			// Se intenta exportar las decisiones con una sesión inválida
			Server.getInstance().exportInformation(-15, project1);
			fail("Se esperaba NotLoggedException");
		} catch (NotLoggedException e) {
		} catch (Exception e) {
			fail("Se esperaba NotLoggedException");
		}
	}
	
	public void testExportInvalidPermission() {		
		try {
			// Se intenta exportar sin tener permiso
			session = Server.getInstance().login("emp1", "emp1");
			Server.getInstance().exportInformation(session.getId(), project1);
			fail("Se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) {
		} catch (Exception e) {
			fail("Se esperaba NonPermissionRoleException");
		}
	}
		
	public void testExport() {			
		// Prueba para exportar información
		try {
			session = Server.getInstance().login("emp2", "emp2");
			byte[] result = Server.getInstance().exportInformation(session.getId(), project1);				 
			assertTrue(result.length > 0);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
}
