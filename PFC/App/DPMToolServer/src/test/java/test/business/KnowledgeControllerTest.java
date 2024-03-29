package test.business;

import java.sql.SQLException;
import java.util.Date;

import model.business.control.Server;
import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.AnswerArgument;
import model.business.knowledge.Categories;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.File;
import model.business.knowledge.ISession;
import model.business.knowledge.KnowledgeStatus;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
import test.IDatosPruebas;
import test.communication.DummyClient;
import test.communication.BaseTest;

import communication.DBConnection;
import communication.DBConnectionManager;

import exceptions.NonExistentAnswerException;
import exceptions.NonExistentProposalException;
import exceptions.NonExistentTopicException;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;


public class KnowledgeControllerTest extends BaseTest {	

	private User chief, employee;
	private Company company;
	private Address address;
	private Project project;
	private Proposal pro, pro2;
	private Topic topic, topic2;	
	private DummyClient chiefClient, employeeClient;
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
			address = new Address("street", "city", "country", "zip", "address");
			company = new Company("456as", "company", "information", address);
			employee = new Employee("12345678L", "emp1", "emp1", "User1", "emp1", "", "", 2, company);
			chief = new ChiefProject("65413987L", "emp2", "emp2", "User", "chief", "", "", 12, company);
			project = new Project("project", "desc1", new Date(), new Date(), 203.36, 458, "bank", "java", 557);
			ans = new Answer("ans", "desc", new Date(), AnswerArgument.Neutral.name());
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
			// Iniciar sessi�n y registrar dos clientes
			sessionChief = server.login("emp2", "emp2");
			sessionEmployee = server.login("emp1", "emp1");
			Server.getInstance().addProjectsUser(sessionChief.getId(), chief, project);
			Server.getInstance().addProjectsUser(sessionChief.getId(), employee, project);
			chiefClient = new DummyClient();
			employeeClient = new DummyClient();
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
			// Se intenta a�adir un topic con una sesi�n inv�lida
			Server.getInstance().addTopic(-15, topic);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta a�adir un topic sin tener permiso			
			Server.getInstance().addTopic(sessionEmployee.getId(), topic);
			fail("se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) { 
		} catch(Exception e) {
			fail("se esperaba NonPermissionRoleException");
		}
		
		try {
			// Se intenta a�adir un topic 
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			assertTrue(Server.getInstance().getTopicsWrapper(sessionChief.getId()).getTopics().size()==0);
			Server.getInstance().addTopic(sessionChief.getId(), topic);
			assertTrue(Server.getInstance().getTopicsWrapper(sessionChief.getId()).getTopics().size()==1);
			assertEquals(Server.getInstance().getTopicsWrapper(sessionChief.getId()).getTopics().get(0), topic);
			// Comprobamos que se ha avisado a los clientes del cambio
			Thread.sleep(800);
			assertEquals(employeeClient.getUltimoDato(), topic);
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testAddProposal() {
		try {
			// Se intenta a�adir una propuesta con una sesi�n inv�lida
			Server.getInstance().addProposal(-15, pro, topic);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}	
		
		try {
			// Se intentan obtner las propuestas con una sesi�n inv�lida
			Server.getInstance().getProposals(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}	
		
		try {
			// Se intentan buscar el padre de una propuesta con una sesi�n inv�lida
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
			// Se intenta a�adir una propuesta 
			assertTrue(Server.getInstance().getProposals(sessionEmployee.getId()).size() == 0);
			Server.getInstance().addProposal(sessionEmployee.getId(), pro, topic);
			assertTrue(Server.getInstance().getProposals(sessionEmployee.getId()).size() == 1);
			assertEquals(Server.getInstance().getProposals(sessionEmployee.getId()).get(0), pro);
			assertEquals(server.findParentProposal(sessionEmployee.getId(), pro), topic);
			// Comprobamos que se ha avisado a los clientes del cambio 
			Thread.sleep(800);
			assertEquals(chiefClient.getUltimoDato(), pro);
			assertFalse(employeeClient.getUltimoDato().equals(pro));
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testAddAnswer() {
		try {
			// Se intenta a�adir una respuesta con una sesi�n inv�lida
			Server.getInstance().addAnswer(-15, ans, pro);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}			

		try {
			// Se intentan obtner las respuestas con una sesi�n inv�lida
			Server.getInstance().getAnswers(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}	
		
		try {
			// Se intentan buscar el padre de una respuesta con una sesi�n inv�lida
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
			// Se intenta a�adir una respuesta 
			assertTrue(Server.getInstance().getAnswers(sessionEmployee.getId()).size() == 0);
			Server.getInstance().addAnswer(sessionEmployee.getId(), ans, pro);
			assertTrue(Server.getInstance().getAnswers(sessionEmployee.getId()).size()==1);
			assertEquals(Server.getInstance().getAnswers(sessionEmployee.getId()).get(0), ans);
			assertEquals(server.findParentAnswer(sessionEmployee.getId(), ans), pro);
			// Comprobamos que se ha avisado a los clientes del cambio 
			Thread.sleep(700);
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
			fail("se esperaba SQLException");
		} catch (SQLException e) { 
		} catch(Exception e) {
			fail("se esperaba SQLException");
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
			// Comprobamos que se ha avisado a los clientes del cambio 
			Thread.sleep(700);
			assertEquals(employeeClient.getUltimoDato(), newTopic);
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
			// Comprobamos que se ha avisado a los clientes del cambio 
			Thread.sleep(700);
			assertEquals(chiefClient.getUltimoDato(), newPro);
			assertFalse(employeeClient.getUltimoDato().equals(newPro));
		} catch (NonExistentTopicException e) { 
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {			
			// Se intenta modificar una propuesta cambiando su topic padre
			Proposal newPro = (Proposal) server.getProposals(sessionEmployee.getId()).get(0).clone();
			Server.getInstance().addTopic(sessionChief.getId(), topic2);
			Server.getInstance().modifyProposal(sessionEmployee.getId(), newPro, pro, topic2);
			assertTrue(server.getProposals(sessionEmployee.getId()).get(0).getId() != pro.getId());
			assertTrue(server.getTopicsWrapper(sessionEmployee.getId()).getTopics().size() == 2);
			assertTrue(server.getTopicsWrapper(sessionEmployee.getId()).getTopic(topic2).getProposals().contains(newPro));
			assertFalse(server.getTopicsWrapper(sessionEmployee.getId()).getTopic(topic).getProposals().contains(newPro));
			// Comprobamos que se ha avisado a los clientes del cambio 
			Thread.sleep(700);
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
			// Comprobamos que se ha avisado a los clientes del cambio 
			Thread.sleep(700);
			assertEquals(chiefClient.getUltimoDato(), newAnswer);
			assertFalse(employeeClient.getUltimoDato().equals(newAnswer));
		} catch (NonExistentTopicException e) { 
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {			
			// Se intenta modificar una propuesta cambiando su topic padre
			Answer newAnswer = (Answer) server.getAnswers(sessionEmployee.getId()).get(0).clone();
			Server.getInstance().addProposal(sessionChief.getId(), pro2, topic);
			Server.getInstance().modifyAnswer(sessionEmployee.getId(), newAnswer, ans, pro2);
			assertTrue(server.getAnswers(sessionEmployee.getId()).get(0).getId() != ans.getId());
			assertTrue(server.getProposals(sessionEmployee.getId()).size() == 2);
			// Comprobamos que se ha avisado a los clientes del cambio 
			Thread.sleep(700);
			assertFalse(employeeClient.getUltimoDato().equals(newAnswer));
		} catch (NonExistentTopicException e) { 
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testDeleteTopic() {
		try {
			// Se intenta eliminar un topic con una sesi�n inv�lida			
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
			// Comprobamos que se ha avisado a los clientes del cambio 
			Thread.sleep(700);
			assertEquals(employeeClient.getUltimoDato(), topic);
		} catch(Exception e) {
			fail(e.toString());
		}	
	}
	
	public void testDeleteProposal() {
		try {
			// Se intenta eliminar una propuesta con una sesi�n inv�lida			
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
			// Comprobamos que se ha avisado a los clientes del cambio 
			Thread.sleep(700);
			assertEquals(employeeClient.getUltimoDato(), pro);
		} catch(Exception e) {
			fail(e.toString());
		}	
	}
	
	
	public void testDeleteAnswer() {
		try {
			// Se intenta eliminar una respuesta con una sesi�n inv�lida			
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
			// Comprobamos que se ha avisado a los clientes del cambio 
			Thread.sleep(700);
			assertEquals(employeeClient.getUltimoDato(), ans);
		} catch(Exception e) {
			fail(e.toString());
		}	
	}
	
	public void testGetTopicsWrapper() {
		try {
			// Get topics wrapper con una sesi�n inv�lida		
			Server.getInstance().getTopicsWrapper(-15);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}
		
		try {
			// Get topics wrapper con una sesi�n inv�lida		
			Server.getInstance().getTopicsWrapper(-15, project);
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
	
	public void testAttachFiles() {
		try {
			// Se intenta adjuntar archivos con una sesi�n inv�lida		
			Server.getInstance().attachFile(-15, null, null);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}		
		
		try {
			// Se adjunta un archivo
			byte[] content =  {8, 12};
			File file = new File("file", content);
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			Server.getInstance().setCurrentProject(sessionEmployee.getId(), project.getId());
			topic.setId(Server.getInstance().addTopic(sessionChief.getId(), topic).getId());
			Server.getInstance().attachFile(sessionEmployee.getId(), topic, file);
			assertTrue(topic.getFiles().size()==1);
			// Se recupera la decision y se comprueba que el contenido del archivo es el mismo
			TopicWrapper tw = Server.getInstance().getTopicsWrapper(sessionEmployee.getId(), project);
			Topic to = tw.getTopic(topic);
			assertEquals(to.getFiles().size(), 1);
			assertEquals(((File)to.getFiles().toArray()[0]).getContent()[0], content[0]);			
			assertEquals(((File)to.getFiles().toArray()[0]).getContent()[1], content[1]);
			// Comprobamos que se ha avisado a los clientes del cambio
			Thread.sleep(700);
			assertEquals(chiefClient.getUltimoDato(), topic);
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testChangeStatusKnowledge() {
		try {
			// Se intenta cambiar el estado de una decisi�n con una sesi�n inv�lida
			Server.getInstance().changeStatusKnowledge(-15, null);
			fail("se esperaba NotLoggedException");
		} catch (NotLoggedException e) { 
		} catch(Exception e) {
			fail("se esperaba NotLoggedException");
		}		
		
		try {
			// Se intenta cambiar el estado de una decisi�n sin tener permiso
			Server.getInstance().changeStatusKnowledge(sessionEmployee.getId(), pro);
			fail("se esperaba NonPermissionRoleException");
		} catch (NonPermissionRoleException e) { 
		} catch(Exception e) {
			fail("se esperaba NonPermissionRoleException");
		}		
		
		try {
			Server.getInstance().setCurrentProject(sessionChief.getId(), project.getId());
			topic.setId(Server.getInstance().addTopic(sessionChief.getId(), topic).getId());
			Server.getInstance().addProposal(sessionChief.getId(), pro, topic);
			// Se cambia el estado de las decisiones
			topic.setStatus(KnowledgeStatus.Accepted);
			Server.getInstance().changeStatusKnowledge(sessionChief.getId(), topic);			
			TopicWrapper tw = Server.getInstance().getTopicsWrapper(sessionChief.getId(), project);
			assertTrue(tw.getTopics().get(0).getStatus().name().equals(topic.getStatus().name()));
			// Comprobamos que se ha avisado a los clientes del cambio
			Thread.sleep(700);
			assertEquals(employeeClient.getUltimoDato(), topic);
		} catch(Exception e) {
			fail(e.toString());
		}		
	}
}
