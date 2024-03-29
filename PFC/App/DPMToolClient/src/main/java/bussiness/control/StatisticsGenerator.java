package bussiness.control;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Project;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DefaultPieDataset;

import presentation.utils.DateUtilities;
import resources.XMLUtilities;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

public class StatisticsGenerator {

	private static StatisticsGenerator instance = null;
	private Document docCharts;
	private Document docChartsConfiguration;
	
	public StatisticsGenerator() throws JDOMException, IOException {
		docCharts = XMLUtilities.readXML(this.getClass().getClassLoader().getResource("charts/charts.xml"));
		docChartsConfiguration = XMLUtilities.readXML(this.getClass().getClassLoader().getResource("charts/chart-generation.xml"));
	}
	
	public static StatisticsGenerator getInstance() throws JDOMException, IOException {
		if (instance == null)
			instance = new StatisticsGenerator();
		return instance;
	}
	
	public ArrayList<String> getChartsTypes() throws JDOMException {
		ArrayList<String> types = new ArrayList<String>();
		// Get values from XML using XPath
		List<Attribute> result = XMLUtilities.selectAttributes(docCharts, "//@type"); 
		for(Attribute att : result)
			types.add(att.getValue());
		return types;
	}
	
	public String getIconName(String chartType) throws JDOMException {
		String pathIcon = "";
		pathIcon = XMLUtilities.selectString(docCharts, "/Charts/Chart[@type='"+chartType+"']/icon");
		return pathIcon;
	}
	
	public ArrayList<String> getIdChart(String chartType) throws JDOMException {
		ArrayList<String> id = new ArrayList<String>();
		// Get values from XML using XPath
		List<Element> result = XMLUtilities.selectNodes(docCharts, "/Charts/Chart[@type='"+chartType+"']/id");	
		for(Element el : result)
			id.add(el.getValue());
		return id;
	}
	
	public ArrayList<String> getIdNamesChart(String chartType) throws JDOMException {
		ArrayList<String> idNames = new ArrayList<String>();
		// Get values from XML using XPath
		List<Element> result = XMLUtilities.selectNodes(docCharts, "/Charts/Chart[@type='"+chartType+"']/name");	
		for(Element el : result)
			idNames.add(el.getValue());
		return idNames;
	}
	
	public ArrayList<String> getIdDescChart(String chartType) throws JDOMException {
		ArrayList<String> idDesc = new ArrayList<String>();
		// Get values from XML using XPath
		List<Element> result = XMLUtilities.selectNodes(docCharts, "/Charts/Chart[@type='"+chartType+"']/desc");	
		for(Element el : result)
			idDesc.add(el.getValue());
		return idDesc;
	}	

	public boolean isOneProject(String id) throws JDOMException {
		Element node = XMLUtilities.selectNodeFromValueChild(docChartsConfiguration, "//Chart/id[. ='"+id+"']", "oneProject");
		return Boolean.parseBoolean(node.getValue());
	}

	public boolean isOneUser(String id) throws JDOMException {
		Element node = XMLUtilities.selectNodeFromValueChild(docChartsConfiguration, "//Chart/id[. ='"+id+"']", "oneUser");
		return Boolean.parseBoolean(node.getValue());
	}

	public boolean isPercentage(String id) throws JDOMException {
		Element node = XMLUtilities.selectNodeFromValueChild(docChartsConfiguration, "//Chart/id[. ='"+id+"']", "percentage");
		return Boolean.parseBoolean(node.getValue());
	}

	public boolean isHistorical(String id) throws JDOMException {
		Element node = XMLUtilities.selectNodeFromValueChild(docChartsConfiguration, "//Chart/id[. ='"+id+"']", "historical");
		boolean result;
		if (node == null)
			result = false;
		else
			result = Boolean.parseBoolean(node.getValue());
		return result;
	}

	public String getResource(String id) throws JDOMException {
		Element node = XMLUtilities.selectNodeFromValueChild(docChartsConfiguration, "//Chart/id[. ='"+id+"']", "total_Number");
		String result;
		if (node == null)
			result = "";
		else
			result = node.getValue();
		return result;
	}

	/*** Methods used to generate datasets ***/	
	// Create dataset for the chart of evolution of developer
	public CategoryDataset createDatasetEvolutionUser(Project pro, User u, boolean isAnnually) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		ArrayList<Integer> years;
		Hashtable<Integer, List<Integer>> months;
		// Get knowledge from project
		TopicWrapper tw = ClientController.getInstance().getTopicsWrapper(pro);
		// Get knowledge from user
		List<Knowledge> knowledgeUser = ClientController.getInstance().getKnowledgeUser(tw, u);  
		if (isAnnually) {
			// Get years
			years = DateUtilities.getYearsBetweenDates(pro.getStartDate(), pro.getEndDate());
			// Create dataset
			for (int year: years) {
				dataset.addValue(countDecisionYear(knowledgeUser, year), "Historical", String.valueOf(year)); 
			}
		}
		else {
			// Get months
			months = DateUtilities.getMonthsBetweenDates(pro.getStartDate(), pro.getEndDate());
			Enumeration<Integer> e = months.keys();
			while (e.hasMoreElements()) {
				int year = e.nextElement();
				// Create dataset
				for (int month : months.get(year)) {
					dataset.addValue(countDecisionMonth(knowledgeUser, month, year), "Historical", String.valueOf(month) + "/" + String.valueOf(year)); 
				}
			}
		}			
		return dataset;
	}	
	
	// Create dataset for the chart of historical of a project
	public CategoryDataset createDatasetHistoricalProject(Project pro, boolean isAnnually) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		ArrayList<Integer> years;
		Hashtable<Integer, List<Integer>> months;
		// Get knowledge from project
		TopicWrapper tw = ClientController.getInstance().getTopicsWrapper(pro);
		List<Knowledge> knowledgeProject = ClientController.getInstance().getKnowledgeProject(tw);  
		if (isAnnually) {
			// Get years
			years = DateUtilities.getYearsBetweenDates(pro.getStartDate(), pro.getEndDate());
			// Create dataset
			for (int year: years) {
				dataset.addValue(countDecisionYear(knowledgeProject, year), "Historical", String.valueOf(year)); 
			}
		}
		else {
			// Get months
			months = DateUtilities.getMonthsBetweenDates(pro.getStartDate(), pro.getEndDate());
			Enumeration<Integer> e = months.keys();
			while (e.hasMoreElements()) {
				int year = e.nextElement();
				// Create dataset
				for (int month : months.get(year)) {
					dataset.addValue(countDecisionMonth(knowledgeProject, month, year), "Historical", String.valueOf(month) + "/" + String.valueOf(year)); 
				}
			}
		}			
		return dataset;
	}
	
	// Create dataset for the chart of participation of each user on the project
	public AbstractDataset createDatasetProjectParticipation(Project pro, boolean percentage) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		AbstractDataset dataset = null;
		if (percentage)
			dataset = new DefaultPieDataset();
		else 
			dataset= new DefaultCategoryDataset();
		
		// Get knowledge from project
		TopicWrapper tw = ClientController.getInstance().getTopicsWrapper(pro);
		List<Knowledge> knowledgeProject = ClientController.getInstance().getKnowledgeProject(tw);
		int knowledgeProjectCount = knowledgeProject.size();
		// Get users that participate on that project
		List<User> users = ClientController.getInstance().getUsersProject(pro);
		for (User u: users) {
			// Get knowledge from each user
			List<Knowledge> knowledgeUser = ClientController.getInstance().getKnowledgeUser(tw, u);  
			int knowledgeUserCount = knowledgeUser.size();
			double value = (knowledgeUserCount * 100.0 / knowledgeProjectCount);
			if (percentage)
				((DefaultPieDataset)dataset).setValue(u.getName(), value);
			else 
				((DefaultCategoryDataset)dataset).addValue(knowledgeUserCount, u.getName(), u.getName());			
		}
		return dataset;
	}
	
	// Create dataset for the chart of number of knowledge made on each project for that user
	public AbstractDataset createDatasetKnowledgeDeveloper(User u, boolean percentage) throws RemoteException, NonPermissionRoleException, NotLoggedException, SQLException, Exception {
		AbstractDataset dataset = null;
		if (percentage)
			dataset = new DefaultPieDataset();
		else 
			dataset= new DefaultCategoryDataset();
		
		int totalCount = 0;
		int knowledgeUserCount;
		Hashtable<String, Integer> parcial_counts = new Hashtable<String, Integer>();
		
		// Get all projects
		List<Project> projects = ClientController.getInstance().getProjects();
		List<User> usersInProject; 
		for (Project p: projects) {
			// Get the user of that project
			usersInProject = ClientController.getInstance().getUsersProject(p);
			if (usersInProject.contains(u)){
				// Get knowledge from user on each project
				TopicWrapper tw = ClientController.getInstance().getTopicsWrapper(p);
				List<Knowledge> knowledgeUser = ClientController.getInstance().getKnowledgeUser(tw, u);  
				knowledgeUserCount = knowledgeUser.size();
				parcial_counts.put(p.getName(), knowledgeUserCount);
				totalCount += knowledgeUserCount;
				if (!percentage)
					((DefaultCategoryDataset)dataset).addValue(knowledgeUserCount, p.getName(), p.getName());			
			}
		}
		if (percentage) {
			for (String projectName: parcial_counts.keySet()) {
				double value = ((parcial_counts.get(projectName) * 100.0) / totalCount);
				((DefaultPieDataset)dataset).setValue(projectName, value);
			}
		}
		return dataset;
	}
	
	// Create dataset for the chart of number of resources on each project
	public DefaultCategoryDataset createDatasetResourcesProject(String resource) throws RemoteException, SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int totalResources = 0;
		
		// Get all projects
		List<Project> projects = ClientController.getInstance().getProjects();
		for (Project p: projects) {
			TopicWrapper tw = ClientController.getInstance().getTopicsWrapper(p);
			if (resource.equals("knowledge")) {
				// Get knowledge from each project
				List<Knowledge> knowledgeProject = ClientController.getInstance().getKnowledgeProject(tw);
				totalResources = knowledgeProject.size();
			}
			else if (resource.equals("developers")) {
				// Get users from each project
				List<User> usersProject = ClientController.getInstance().getUsersProject(p);
				totalResources = usersProject.size();
			}
			dataset.addValue(totalResources, p.getName(), p.getName());		
		}
		
		return dataset;
	}
	
	/*** Auxiliary methods used to generate datasets ***/
	// Get number of decisions of that user on that project on that year
	private Number countDecisionYear(List<Knowledge> knowledge, int year) {
		int cont = 0;		
		for (Knowledge k: knowledge) {
			if (DateUtilities.yearEquals(k.getDate(), year))
				cont ++;
		}			
		return cont;		
	}
	
	// Get number of decisions of that user on that project on that month
	private Number countDecisionMonth(List<Knowledge> knowledge, int month, int year) {
		int cont = 0;		
		for (Knowledge k: knowledge) {
			if (DateUtilities.monthEquals(k.getDate(), month, year))
				cont ++;
		}			
		return cont;
	}
}
