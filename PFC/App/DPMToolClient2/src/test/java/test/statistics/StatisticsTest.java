package test.statistics;


import java.util.List;

import junit.framework.TestCase;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DefaultPieDataset;

import test.communication.ServerPrueba;
import bussiness.control.ClientController;
import bussiness.control.StatisticsGenerator;

import communication.CommunicationsUtilities;


public class StatisticsTest extends TestCase {

	// Servidor dummy que devolverá datos de prueba
	private ServerPrueba server;
	
	protected void setUp() {	
		try {
			// Preparamos la base de datos
			super.setUp();
			server = new ServerPrueba();
			// Se inicia el servidor de prueba
			server.activate(CommunicationsUtilities.getHostIP());
			// Se inicia el cliente
			ClientController.getInstance().initClient(CommunicationsUtilities.getHostIP(), String.valueOf(server.getPuerto()), "emp1", "emp1");
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	protected void tearDown() {
		try {
			super.tearDown();
			// Se detiene el servidor
			server.deactivate(CommunicationsUtilities.getHostIP());
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	public void testGetChartsTypes() {	
		try {
			// Se intentan obtener los tipos de gráficos disponibles
			List<String> types = StatisticsGenerator.getInstance().getChartsTypes();
			assertEquals(types.size(), 8);
			// Se recupera un icono de uno de los tipos de graficos
			String path = StatisticsGenerator.getInstance().getIconName(types.get(0));
			assertNotNull(path);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testGetChartsInformation() {	
		try {
			List<String> types = StatisticsGenerator.getInstance().getChartsTypes();
			// Se intentan obtener el id de un tipo de gráfico (pastel)
			List<String> ids = StatisticsGenerator.getInstance().getIdChart(types.get(1));
			assertEquals(ids.size(), 2);
			// Se intentan obtener los nombres de un tipo de gráfico (pastel)
			List<String> names = StatisticsGenerator.getInstance().getIdNamesChart(types.get(1));
			assertEquals(names.size(), 2);
			// Se intentan obtener las descripciones de un tipo de gráfico (barras)
			List<String> descs = StatisticsGenerator.getInstance().getIdDescChart(types.get(0));
			assertEquals(descs.size(), 4);
			// Se comprueba si el tipo de gráfico "Pastel participación desarrolladores en un proyecto" 
			// es para un proyecto, usuario, etc.
			assertEquals(StatisticsGenerator.getInstance().isHistorical(ids.get(0)), false);
			assertEquals(StatisticsGenerator.getInstance().isOneProject(ids.get(0)), true);
			assertEquals(StatisticsGenerator.getInstance().isOneUser(ids.get(0)), false);
			assertEquals(StatisticsGenerator.getInstance().isPercentage(ids.get(0)), true);
			// Se intenta obtener un recurso de un tipo de gráfico
			String resource = StatisticsGenerator.getInstance().getResource(ids.get(1));
			assertEquals(resource, "");
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testCreateDatasetEvolutionUser() {
		try {
			// Se intenta crear un dataset de un tipo de gráfico de lineas, para un
			// proyecto y usuario, de manera anual
			CategoryDataset dataset = StatisticsGenerator.getInstance().createDatasetEvolutionUser(server.getProject(), server.getUser(), true);
			// En el proyecto existian 15 años de diferencia, por lo que deben existir 15 series en el dataset
			assertEquals(dataset.getColumnCount(), 15);
			// Ahora se crea el dataset de manera mensual
			CategoryDataset datasetMonthly = StatisticsGenerator.getInstance().createDatasetEvolutionUser(server.getProject(), server.getUser(), false);
			// En el proyecto existian 15 años de diferencia, por lo que deben existir 174 series (meses) en el dataset,
			// ya que los años de diferencia no son completos
			assertEquals(datasetMonthly.getColumnCount(), 169);
		} catch (Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testCreateDatasetHistoricalProject() {
		try {
			// Se intenta crear un dataset de un tipo de gráfico de lineas, para un
			// proyecto, de manera anual
			CategoryDataset dataset = StatisticsGenerator.getInstance().createDatasetHistoricalProject(server.getProject(), true);
			// En el proyecto existian 15 años de diferencia, por lo que deben existir 15 series en el dataset
			assertEquals(dataset.getColumnCount(), 15);
			// Ahora se crea el dataset de manera mensual
			CategoryDataset datasetMonthly = StatisticsGenerator.getInstance().createDatasetHistoricalProject(server.getProject(), false);
			// En el proyecto existian 15 años de diferencia, por lo que deben existir 174 series (meses) en el dataset,
			// ya que los años de diferencia no son completos
			assertEquals(datasetMonthly.getColumnCount(), 169);
		} catch (Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testCreateDatasetProjectParticipation() {
		try {
			// Se intenta crear un dataset de un tipo de gráfico de barras o pastel, 
			// para la participacion de desarrolladores en un proyecto
			AbstractDataset dataset = StatisticsGenerator.getInstance().createDatasetProjectParticipation(server.getProject(), false);
			// En el proyecto existia 1 desarrollador
			assertEquals(((DefaultCategoryDataset)dataset).getColumnCount(), 1);
			// Ahora se crea el dataset para un diagrama de pastel
			dataset = StatisticsGenerator.getInstance().createDatasetProjectParticipation(server.getProject(), true);
			assertEquals(((DefaultPieDataset)dataset).getItemCount(), 1);
		} catch (Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testCreateDatasetKnowledgeDeveloper() {
		try {
			// Se intenta crear un dataset de un tipo de gráfico que representa la cantidad de decisiones
			// creadas por un usuario en todos los proyectos en los que trabaja
			AbstractDataset dataset = StatisticsGenerator.getInstance().createDatasetKnowledgeDeveloper(server.getUser(), false);
			// En el proyecto existia 1 desarrollador con una única decisión
			assertEquals(((DefaultCategoryDataset)dataset).getColumnCount(), 1);
			// Ahora se crea el dataset para un diagrama de pastel
			dataset = StatisticsGenerator.getInstance().createDatasetKnowledgeDeveloper(server.getUser(), true);
			assertEquals(((DefaultPieDataset)dataset).getItemCount(), 1);
		} catch (Exception e) {
			fail(e.toString());
		}		
	}
	
	public void testCreateDatasetResourcesProject() {
		try {
			// Se intenta crear un dataset de un tipo de gráfico que representa la cantidad de recursos (decisiones, empleados, etc)
			// que pertenecen a cada uno de los proyectos existentes
			DefaultCategoryDataset dataset = StatisticsGenerator.getInstance().createDatasetResourcesProject("knowledge");
			// Solo existe un proyecto, con una única decisión
			assertEquals(dataset.getColumnCount(), 1);
			// Ahora se crea el dataset para el numero de desarrolladores
			dataset = StatisticsGenerator.getInstance().createDatasetResourcesProject("developers");
			// Solo existe un proyecto, con un único empleado
			assertEquals(dataset.getColumnCount(), 1);
		} catch (Exception e) {
			fail(e.toString());
		}		
	}
}