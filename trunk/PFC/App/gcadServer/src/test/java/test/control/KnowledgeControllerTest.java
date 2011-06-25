package test.control;

import java.util.Date;

import model.business.control.Server;
import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.ISession;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
import test.communication.ClientePrueba;
import test.communication.PruebasBase;
import test.utils.IDatosPruebas;

import communication.DBConnection;
import communication.DBConnectionManager;

import exceptions.NonExistentAnswerException;
import exceptions.NonExistentProposalException;
import exceptions.NonExistentTopicException;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;


public class KnowledgeControllerTest extends PruebasBase {	

	private User chief, employee;
	private Company company;
	private Address address;
	private Project project;
	private Proposal pro, pro2;
	private Topic topic, topic2;	
	private ClientePrueba chiefClient, employeeClient;
	private Server server;
	private ISession sessionChief, sessionEmployee;
	private Answer ans;
	
	protected void setUp() {	
		try {
			// Preparamos la base de datos
			super.setUp();
			// Creamos un administrador de prueba
			DBConnectionManager.clear();
			DBConnectionManager.addConnection(new DBConnection());
			address = new Address("street", "city", "country", "zip");
			company = new Company("456as", "company", "information", address);
			employee = new Employee("12345678L", "emp1", "emp1", "User1", "emp1", "", "", 2, company);
			chief = new ChiefProject("65413987L", "emp2", "emp2", "User", "chief", "", "", 12, company);
			project = new Project("project", "desc1", new Date(), new Date(), 203.36, 458, "bank", "java", 557);
			ans = new Answer("ans", "desc", new Date(), "Pro");
			pro = new Proposal("pro1", "desc", new Date(), Categories.Analysis);
			pro2 = new Proposal("pro2", "desc2", new Date(), Categories.Design);
			topic = new Topic("topic1", "desc", new Date());
			topic2 = new Topic("topic2", "desc2", new Date());
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(address);
			DBConnectionManager.insert(company);
			DBConnectionManager.insert(project);
			DBConnectionManager.insert(chief);
			DBConnectionManager.insert(employee);
			DBConnectionManager.finishTransaction();
			server = new Server();
			chief.getProjects().add(project);
			employee.getProjects().add(project);
			// INiciar sessión y registrar dos clientes
			sessionChief = server.login("emp2", "emp2");
			sessionEmployee = server.login("emp1", "emp1");
			Server.getInstance().addProjectsUser(sessionChief.getId(), chief, project);
			Server.getInstance().addProjectsUser(sessionChief.getId(), employee, project);
			chiefClient = new ClientePrueba();
			employeeClient = new ClientePrueba();
			chiefClient.activate(IDatosPruebas.IP_ESCUCHA_CLIENTES);
			employeeClient.activate(IDatosPruebas.IP_ESCUCHA_CLIENTES);
			server.register(sessionChief.getId(), chiefClient);
			server.register(sessionEmployee.getId(), employeeClient);
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
	
	public void testAddTopic() {
		try {
			// Se intenta añadir un topic con una sesión inválida
			Server.getInstance().addTopic(-15, topic);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta añadir un topic sin tener permiso			
			Server.getInstance().addTopic(sessionEmployee.getId(), topic);
			fail("se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) { 
		} catch(Exception e) {
			fail("se esperaba NonPermissionRoleException");
		}
		
		try {
			// Se intenta añadir un topic 
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			assertTrue(Server.getInstance().getTopicsWrapper(sessionChief.getId()).getTopics().size()==0);
			Server.getInstance().addTopic(sessionChief.getId(), topic);
			assertTrue(Server.getInstance().getTopicsWrapper(sessionChief.getId()).getTopics().size()==1);
			assertEquals(Server.getInstance().getTopicsWrapper(sessionChief.getId()).getTopics().get(0), topic);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(employeeClient.getUltimoDato(), topic);
			assertNull(chiefClient.getUltimoDato());
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testAddProposal() {
		try {
			// Se intenta añadir una propuesta con una sesión inválida
			Server.getInstance().addProposal(-15, pro, topic);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}	
		
		try {
			// Se intentan obtner las propuestas con una sesión inválida
			Server.getInstance().getProposals(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}	
		
		try {
			// Se intentan buscar el padre de una propuesta con una sesión inválida
			Server.getInstance().findParentProposal(-15, pro);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}	
		
		try {
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			Server.getInstance().setCurrentProject(sessionEmployee.getId(), project.getId());
			Server.getInstance().addTopic(sessionChief.getId(), topic);
			// Se intenta añadir una propuesta 
			assertTrue(Server.getInstance().getProposals(sessionEmployee.getId()).size() == 0);
			Server.getInstance().addProposal(sessionEmployee.getId(), pro, topic);
			assertTrue(Server.getInstance().getProposals(sessionEmployee.getId()).size() == 1);
			assertEquals(Server.getInstance().getProposals(sessionEmployee.getId()).get(0), pro);
			assertEquals(server.findParentProposal(sessionEmployee.getId(), pro), topic);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(chiefClient.getUltimoDato(), pro);
			assertFalse(employeeClient.getUltimoDato().equals(pro));
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testAddAnswer() {
		try {
			// Se intenta añadir una respuesta con una sesión inválida
			Server.getInstance().addAnswer(-15, ans, pro);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}			

		try {
			// Se intentan obtner las respuestas con una sesión inválida
			Server.getInstance().getAnswers(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}	
		
		try {
			// Se intentan buscar el padre de una respuesta con una sesión inválida
			Server.getInstance().findParentAnswer(-15, ans);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}	
		
		try {
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			Server.getInstance().setCurrentProject(sessionEmployee.getId(), project.getId());
			Server.getInstance().addTopic(sessionChief.getId(), topic);
			Server.getInstance().addProposal(sessionEmployee.getId(), pro, topic);
			// Se intenta añadir una respuesta 
			assertTrue(Server.getInstance().getAnswers(sessionEmployee.getId()).size() == 0);
			Server.getInstance().addAnswer(sessionEmployee.getId(), ans, pro);
			assertTrue(Server.getInstance().getAnswers(sessionEmployee.getId()).size()==1);
			assertEquals(Server.getInstance().getAnswers(sessionEmployee.getId()).get(0), ans);
			assertEquals(server.findParentAnswer(sessionEmployee.getId(), ans), pro);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(chiefClient.getUltimoDato(), ans);
			assertFalse(employeeClient.getUltimoDato().equals(pro));
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testModifyTopic() {		
		try {
			// Se intenta modificar un topic con una sesion invalida
			topic.setProject(project);
			topic.setUser(chief);
			Topic newTopic = (Topic) topic.clone();
			newTopic.setDescription("other desc");
			newTopic.setTitle("new Title");
			Server.getInstance().modifyTopic(-15, newTopic, topic);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta modificar un topic sin tener permiso
			topic.setProject(project);
			topic.setUser(chief);
			Topic newTopic = (Topic) topic.clone();
			newTopic.setDescription("other desc");
			newTopic.setTitle("new Title");
			Server.getInstance().modifyTopic(sessionEmployee.getId(), newTopic, topic);
			fail("se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) { 
		} catch(Exception e) {
			fail("se esperaba NonPermissionRoleException");
		}

		try {
			// Se intenta modificar un topic inexistente
			topic.setProject(project);
			topic.setUser(chief);			
			Topic newTopic = (Topic) topic.clone();
			newTopic.setDescription("other desc");
			newTopic.setTitle("new Title");
			Server.getInstance().modifyTopic(sessionChief.getId(), newTopic, topic);
			fail("se esperaba NonExistentTopicException");
		} catch (NonExistentTopicException e) { 
		} catch(Exception e) {
			fail("se esperaba NonExistentTopicException");
		}	
		
		try {
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			Server.getInstance().addTopic(sessionChief.getId(), topic);
			// Se intenta modificar un topic
			Topic newTopic = (Topic) topic.clone();
			newTopic.setDescription("other desc");
			newTopic.setTitle("new Title");
			Server.getInstance().modifyTopic(sessionChief.getId(), newTopic, topic);
			assertEquals(server.getTopicsWrapper(sessionChief.getId()).getTopics().get(0), newTopic);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(employeeClient.getUltimoDato(), newTopic);
			assertNull(chiefClient.getUltimoDato());
		} catch (NonExistentTopicException e) { 
		} catch(Exception e) {
			fail(e.toString());
		}	
	}
	
	public void testModifyProposal() {		
		try {
			// Se intenta modificar una propuesta con una sesion invalida			
			pro.setUser(chief);
			Proposal newPro = (Proposal) pro.clone();
			newPro.setDescription("other desc");
			newPro.setTitle("new Title");
			Server.getInstance().modifyProposal(-15, newPro, pro, topic);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta modificar una propuesta inexistente
			pro.setUser(chief);
			Proposal newPro = (Proposal) pro.clone();
			newPro.setDescription("other desc");
			newPro.setTitle("new Title");
			Server.getInstance().modifyProposal(sessionEmployee.getId(), newPro, pro, topic);
			fail("se esperaba NonExistentProposalException");
		} catch (NonExistentProposalException e) { 
		} catch(Exception e) {
			fail("se esperaba NonExistentProposalException");
		}
		
		try {
			topic.getProposals().clear();
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			Server.getInstance().setCurrentProject(sessionEmployee.getId(), project.getId());
			Server.getInstance().addTopic(sessionChief.getId(), topic);
			Server.getInstance().addProposal(sessionEmployee.getId(), pro, topic);
			
			// Se intenta modificar una propuesta sin cambiar su padre
			Proposal newPro = (Proposal) pro.clone();
			newPro.setDescription("other desc");
			newPro.setTitle("new Title");
			Server.getInstance().modifyProposal(sessionEmployee.getId(), newPro, pro, topic);
			assertTrue(server.getProposals(sessionEmployee.getId()).size() == 1);
			assertEquals(server.getProposals(sessionEmployee.getId()).get(0), newPro);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(chiefClient.getUltimoDato(), newPro);
			assertFalse(employeeClient.getUltimoDato().equals(newPro));
		} catch (NonExistentTopicException e) { 
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {			
			// Se intenta modificar una propuesta cambiando su topic padre
			Proposal newPro = (Proposal) server.getProposals(sessionEmployee.getId()).get(0).clone();
			topic2.add(newPro);
			Server.getInstance().addTopic(sessionChief.getId(), topic2);
			Server.getInstance().modifyProposal(sessionEmployee.getId(), newPro, pro, topic);
			assertTrue(server.getProposals(sessionEmployee.getId()).get(0).getId() != pro.getId());
			assertTrue(server.getTopicsWrapper(sessionEmployee.getId()).getTopics().size() == 2);
			assertTrue(server.getTopicsWrapper(sessionEmployee.getId()).getTopic(topic).getProposals().contains(newPro));
			assertFalse(server.getTopicsWrapper(sessionEmployee.getId()).getTopic(topic2).getProposals().contains(newPro));
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(chiefClient.getUltimoDato(), newPro);
			assertFalse(employeeClient.getUltimoDato().equals(newPro));
		} catch (NonExistentTopicException e) { 
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testModifyAnswers() {		
		try {
			// Se intenta modificar una respuesta con una sesion invalida			
			ans.setUser(chief);
			Answer newAnswer = (Answer) ans.clone();
			newAnswer.setDescription("other desc");
			newAnswer.setTitle("new Title");
			Server.getInstance().modifyAnswer(-15, newAnswer, ans, pro);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta modificar una respuesta inexistente
			ans.setUser(chief);
			Answer newAnswer = (Answer) ans.clone();
			newAnswer.setDescription("other desc");
			newAnswer.setTitle("new Title");
			Server.getInstance().modifyAnswer(sessionEmployee.getId(), newAnswer, ans, pro);
			fail("se esperaba NonExistentAnswerException");
		} catch (NonExistentAnswerException e) { 
		} catch(Exception e) {
			fail("se esperaba NonExistentAnswerException");
		}
		
		try {
			topic.getProposals().clear();
			pro.getAnswers().clear();
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			Server.getInstance().setCurrentProject(sessionEmployee.getId(), project.getId());
			Server.getInstance().addTopic(sessionChief.getId(), topic);
			Server.getInstance().addProposal(sessionEmployee.getId(), pro, topic);
			Server.getInstance().addAnswer(sessionEmployee.getId(), ans, pro);
			
			// Se intenta modificar una respuesta sin cambiar su padre
			Answer newAnswer = (Answer) ans.clone();
			newAnswer.setDescription("other desc");
			newAnswer.setTitle("new Title");
			Server.getInstance().modifyAnswer(sessionEmployee.getId(), newAnswer, ans, pro);
			assertEquals(server.getAnswers(sessionEmployee.getId()).get(0), newAnswer);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(chiefClient.getUltimoDato(), newAnswer);
			assertFalse(employeeClient.getUltimoDato().equals(newAnswer));
		} catch (NonExistentTopicException e) { 
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {			
			// Se intenta modificar una propuesta cambiando su topic padre
			Answer newAnswer = (Answer) server.getAnswers(sessionEmployee.getId()).get(0).clone();
			pro2.add(newAnswer);
			Server.getInstance().addProposal(sessionChief.getId(), pro2, topic);
			Server.getInstance().modifyAnswer(sessionEmployee.getId(), newAnswer, ans, pro);
			assertTrue(server.getAnswers(sessionEmployee.getId()).get(0).getId() != ans.getId());
			assertTrue(server.getProposals(sessionEmployee.getId()).size() == 2);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(chiefClient.getUltimoDato(), newAnswer);
			assertFalse(employeeClient.getUltimoDato().equals(newAnswer));
		} catch (NonExistentTopicException e) { 
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testDeleteTopic() {
		try {
			// Se intenta eliminar un topic con una sesión inválida			
			Server.getInstance().deleteTopic(-15, topic);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta eliminar un topic inexistente
			Server.getInstance().deleteTopic(sessionChief.getId(), topic);
			fail("se esperaba NonExistentTopicException");
		} catch (NonExistentTopicException e) { 
		} catch(Exception e) {
			fail("se esperaba NonExistentTopicException");
		}
		
		try {
			// Se intenta eliminar un topic sin permiso
			Server.getInstance().deleteTopic(sessionEmployee.getId(), topic);
			fail("se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) { 
		} catch(Exception e) {
			fail("se esperaba NonPermissionRoleException");
		}
		
		try {
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			Server.getInstance().addTopic(sessionChief.getId(), topic);			
			// Se intenta eliminar un topic 
			assertTrue(Server.getInstance().getTopicsWrapper(sessionChief.getId()).getTopics().size() == 1);
			Server.getInstance().deleteTopic(sessionChief.getId(), topic);
			assertTrue(Server.getInstance().getTopicsWrapper(sessionEmployee.getId()).getTopics().size() == 0);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(employeeClient.getUltimoDato(), topic);
			assertNull(chiefClient.getUltimoDato());
		} catch(Exception e) {
			fail(e.toString());
		}	
	}
	
	public void testDeleteProposal() {
		try {
			// Se intenta eliminar una propuesta con una sesión inválida			
			Server.getInstance().deleteProposal(-15, pro);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta eliminar una propuesta inexistente
			Server.getInstance().deleteProposal(sessionEmployee.getId(), pro);
			fail("se esperaba NonExistentProposalException");
		} catch (NonExistentProposalException e) { 
		} catch(Exception e) {
			fail("se esperaba NonExistentProposalException");
		}

		try {
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			Server.getInstance().setCurrentProject(sessionEmployee.getId(), project.getId());
			Server.getInstance().addTopic(sessionChief.getId(), topic);
			Server.getInstance().addProposal(sessionChief.getId(), pro, topic);
			// Se intenta eliminar una propuesta 
			assertTrue(Server.getInstance().getProposals(sessionChief.getId()).size() == 1);
			Server.getInstance().deleteProposal(sessionChief.getId(), pro);
			assertTrue(Server.getInstance().getProposals(sessionChief.getId()).size() == 0);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(employeeClient.getUltimoDato(), pro);
			assertNull(chiefClient.getUltimoDato());
		} catch(Exception e) {
			fail(e.toString());
		}	
	}
	
	
	public void testDeleteAnswer() {
		try {
			// Se intenta eliminar una respuesta con una sesión inválida			
			Server.getInstance().deleteAnswer(-15, ans);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta eliminar una propuesta inexistente
			Server.getInstance().deleteAnswer(sessionEmployee.getId(), ans);
			fail("se esperaba NonExistentProposalException");
		} catch (NonExistentAnswerException e) { 
		} catch(Exception e) {
			fail("se esperaba NonExistentProposalException");
		}

		try {
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			Server.getInstance().setCurrentProject(sessionEmployee.getId(), project.getId());
			Server.getInstance().addTopic(sessionChief.getId(), topic);
			Server.getInstance().addProposal(sessionChief.getId(), pro, topic);
			Server.getInstance().addAnswer(sessionChief.getId(), ans, pro);
			// Se intenta eliminar una propuesta 
			assertTrue(Server.getInstance().getAnswers(sessionChief.getId()).size() == 1);
			Server.getInstance().deleteAnswer(sessionChief.getId(), ans);
			assertTrue(Server.getInstance().getAnswers(sessionChief.getId()).size() == 0);
			// Comprobamos que se ha avisado a los clientes del cambio del beneficiario
			Thread.sleep(100);
			assertEquals(employeeClient.getUltimoDato(), ans);
			assertNull(chiefClient.getUltimoDato());
		} catch(Exception e) {
			fail(e.toString());
		}	
	}
	
	public void testGetTopicsWrapper() {
		try {
			// Get topics wrapper con una sesión inválida		
			Server.getInstance().getTopicsWrapper(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Get topics wrapper	
			TopicWrapper tw = Server.getInstance().getTopicsWrapper(sessionEmployee.getId());
			assertTrue(tw.getTopics().size() == 0);		
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Get topics wrapper	
			TopicWrapper tw = Server.getInstance().getTopicsWrapper(sessionChief.getId());
			assertTrue(tw.getTopics().size() == 0);		
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			Server.getInstance().setCurrentProject(sessionEmployee.getId(), project.getId());
			Server.getInstance().addTopic(sessionChief.getId(), topic);
			// Get topics wrapper	
			TopicWrapper tw = Server.getInstance().getTopicsWrapper(sessionChief.getId());
			assertTrue(tw.getTopics().size() == 1);		
			Server.getInstance().addTopic(sessionChief.getId(), topic2);
			tw = Server.getInstance().getTopicsWrapper(sessionChief.getId());
			assertTrue(tw.getTopics().size() == 2);
			tw = Server.getInstance().getTopicsWrapper(sessionEmployee.getId());
			assertTrue(tw.getTopics().size() == 2);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			Server.getInstance().addProposal(sessionEmployee.getId(), pro, topic);
			// Get topics wrapper	
			TopicWrapper tw = Server.getInstance().getTopicsWrapper(sessionChief.getId());
			assertTrue(tw.getTopics().size() == 2);	
			assertTrue(tw.getTopic(topic).getProposals().contains(pro));
			Server.getInstance().addProposal(sessionEmployee.getId(), pro2, topic2);
			tw = Server.getInstance().getTopicsWrapper(sessionChief.getId());
			assertTrue(tw.getTopics().size() == 2);
			assertTrue(tw.getTopic(topic2).getProposals().contains(pro2));
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Get topics wrapper	
			TopicWrapper tw = Server.getInstance().getTopicsWrapper(sessionChief.getId(), project);
			assertTrue(tw.getTopics().size() == 2);	
			assertTrue(tw.getTopic(topic).getProposals().contains(pro));
			Server.getInstance().deleteTopic(sessionChief.getId(), topic);
			tw = Server.getInstance().getTopicsWrapper(sessionChief.getId(), project);
			assertTrue(tw.getTopics().size() == 1);
		} catch(Exception e) {
			fail(e.toString());
		}
	}
}
