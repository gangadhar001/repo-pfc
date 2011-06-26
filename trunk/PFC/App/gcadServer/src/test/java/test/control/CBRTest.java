package test.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.business.control.Server;
import model.business.control.CBR.Attribute;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.control.CBR.similarity.local.Difference;
import model.business.control.CBR.similarity.local.Equal;
import model.business.control.CBR.similarity.local.Interval;
import model.business.control.CBR.similarity.local.LocalSimilarityFunction;
import model.business.knowledge.Address;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.ISession;
import model.business.knowledge.Project;
import model.business.knowledge.User;
import test.communication.PruebasBase;

import communication.DBConnection;
import communication.DBConnectionManager;

import exceptions.NotLoggedException;


public class CBRTest extends PruebasBase {	

	private User chief, employee;
	private Company company;
	private Address address;
	private Project project1, project2, project3;
	
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
			project1 = new Project("GCAD", "Desktop Swing application used to manage knowledge in DGS", new Date(), new Date(), 23458.1, 12341, "dgs", "java", 25604);
			project2 = new Project("PrintPro", "Processor to print remotely", new Date(), new Date(), 12000.0, 9005, "bank", "C#", 9503);
			project3 = new Project("CHS", "County health system", new Date(), new Date(), 1500.1, 22045, "health", "C", 38356);
			DBConnectionManager.initTransaction();
			DBConnectionManager.insert(address);
			DBConnectionManager.insert(company);
			DBConnectionManager.insert(project1);
			DBConnectionManager.insert(project2);
			DBConnectionManager.insert(project3);
			chief.getProjects().add(project1);
			chief.getProjects().add(project3);
			employee.getProjects().add(project2);
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
	
	public void testCBR() {	
		Project caseToEval = null;
		List<Attribute> atts = new ArrayList<Attribute>();
		List<Project> cases = new ArrayList<Project>();
		List<Project> result = new ArrayList<Project>();
		ISession session;
		ConfigCBR configCBR = null;
		LocalSimilarityFunction function, function2;
		
		try {
			// se intenta ejecutar un algoritmo de CBR con una sesión inválida
			Server.getInstance().executeAlgorithm(-15, EnumAlgorithmCBR.NN, cases, caseToEval, configCBR, 0);
			fail("Se esperaba NotLoggedException");
		} catch (NotLoggedException e) {
		} catch (Exception e) {
			fail("Se esperaba NotLoggedException");
		}
		
		try {
			// Se intenta ejecutar un algoritmo de CBR sin tener permiso
			session = Server.getInstance().login("emp1", "emp1");
			Server.getInstance().executeAlgorithm(-15, EnumAlgorithmCBR.NN, cases, caseToEval, configCBR, 0);
			fail("Se esperaba NotLoggedException");
		} catch (NotLoggedException e) {
		} catch (Exception e) {
			fail("Se esperaba NotLoggedException");
		}
		
		/* Se ejecuta el algoritmo con el modo NN
		/ Se espera que con la configuracion que se va a crear, el proyecto más similar sea el Project2, 
		 * luego el Project1 y luego el Project3 */
		try {
			session = Server.getInstance().login("emp2", "emp2");
			
			/*** FASE DE RECUPERACIÓN ***/
			// Se crea un nuevo proyecto que es el que se desea usar para comparar en el CBR
			caseToEval = new Project("New Project", "Project Used to compare", new Date(), new Date(), 15000.0, 5012, "bank", "C#", 11000);
			// Se obtienen todos los proyectos
			cases = Server.getInstance().getProjects(session.getId());
			assertTrue(cases.size() == 3);
			// Se toman los atributos del proyecto
			atts = Server.getInstance().getAttributesFromProject(caseToEval);
			assertTrue(atts.size() == 11 || atts.size() == 12);
			// Se configuran los pesos y funciones de semejanza del proiyecto a evaluar (ignorando el id y el UID)
			configCBR = new ConfigCBR();
			/* FUNCIONES */
			// Titulo, descripcion y fechas del proyecto iguales
			configCBR.addLocalSimFunction(atts.get(2), new Equal());
			configCBR.addLocalSimFunction(atts.get(3), new Equal());
			configCBR.addLocalSimFunction(atts.get(4), new Equal());
			configCBR.addLocalSimFunction(atts.get(5), new Equal());
			// Presupuesto en un intervalo
			function = new Interval();
			((Interval)function).setInterval(2);
			configCBR.addLocalSimFunction(atts.get(6), function);
			// Diferencia de lineas de codigo
			configCBR.addLocalSimFunction(atts.get(7), new Difference());
			// Dominio y lenguaje iguales
			configCBR.addLocalSimFunction(atts.get(8), new Equal());
			configCBR.addLocalSimFunction(atts.get(9), new Equal());
			// Numero de horas en un intervalo
			function2 = new Interval();
			((Interval)function2).setInterval(3);
			configCBR.addLocalSimFunction(atts.get(10), function);
			
			/* PESOS */
			// Titulo, descripcion con peso 0
			configCBR.setWeight(atts.get(2), 0);
			configCBR.setWeight(atts.get(3), 0);
			// Fechas con peso 1
			configCBR.setWeight(atts.get(4), 1);
			configCBR.setWeight(atts.get(5), 1);
			// Presupuesto con peso 0.67
			configCBR.setWeight(atts.get(6), 0.67);
			// Diferencia, dominio y lenguaje con peso 1
			configCBR.setWeight(atts.get(7), 1);
			configCBR.setWeight(atts.get(8), 1);
			configCBR.setWeight(atts.get(9), 1);
			// Numero de horas con peso 0.8
			function2 = new Interval();
			((Interval)function2).setInterval(3);
			configCBR.setWeight(atts.get(10),0.8);
			
			// Se ejecuta cogiendo todos los proyectos y método NN (k=0)
			result = Server.getInstance().executeAlgorithm(session.getId(), EnumAlgorithmCBR.NN, cases, caseToEval, configCBR, 0);
			assertTrue(result.size() == 3);
			// El más similar es el Project2, luego el Project1 y por último el Project3
			assertEquals(result.get(0), project2);
			assertEquals(result.get(1), project1);
			assertEquals(result.get(2), project3);
			
			// Se ejecuta con k = 1 y método NN
			result = Server.getInstance().executeAlgorithm(session.getId(), EnumAlgorithmCBR.NN, cases, caseToEval, configCBR, 1);
			assertTrue(result.size() == 1);
			assertEquals(result.get(0), project2);
			
			// Se ejecuta con k = 1 y método Euclidean. Ahora el más semejante es el Project3
			// Se demuestra que este método no es muy fiable
			result = Server.getInstance().executeAlgorithm(session.getId(), EnumAlgorithmCBR.Euclidean, cases, caseToEval, configCBR, 1);
			assertTrue(result.size() == 1);
			assertEquals(result.get(0), project3);
			
			// Se ejecuta con k = 0 y método Euclidean. Ahora el más semejante es el Project3
			// Se demuestra que este método no es muy fiable
			result = Server.getInstance().executeAlgorithm(session.getId(), EnumAlgorithmCBR.Euclidean, cases, caseToEval, configCBR, 0);
			assertTrue(result.size() == 3);
			assertEquals(result.get(0), project3);
			assertEquals(result.get(1), project1);
			assertEquals(result.get(2), project2);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
}
