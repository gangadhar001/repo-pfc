package presentation;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import model.business.knowledge.Answer;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

import bussiness.control.ClientController;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class JFPruebaEstadisiticas extends javax.swing.JFrame {

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFPruebaEstadisiticas inst = new JFPruebaEstadisiticas();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	private List<Project> projects;
	private TopicWrapper tw;
	private List<User> users;
	
	public JFPruebaEstadisiticas() {
		super();
		initGUI();
		prueba();
	}
	
	private void prueba() {
		
		DefaultPieDataset dataset = new DefaultPieDataset();
	    DefaultCategoryDataset datasetBars= new DefaultCategoryDataset();
		try {
			ClientController.getInstance().initClient("192.168.1.177", "2995", "emp1", "emp1");
			// Se toman todos los proyectos
			projects = ClientController.getInstance().getProjects();
			// Se toma el TopicWrapper del proyecto deseado
			tw = ClientController.getInstance().getTopicsWrapper(projects.get(1));
			// Se cuenta el total de conocimiento
			int count = tw.getTopics().size();
			for(Topic t: tw.getTopics()) {
				count += t.getProposals().size();
				for (Proposal p: t.getProposals()) {
					count += p.getAnswers().size();
				}
			}
			// Se toman los usuarios del proyecto
			users = ClientController.getInstance().getUsersProject(projects.get(1));
			// Del TW anterior se cuenta para cada usuario
			for (User u: users) {
				int nKnow = getCountKnowledgeUser(u);
				double value = (nKnow * 100.0 / count);
				dataset.setValue(u.getName(), value);
				datasetBars.addValue(nKnow, u.getName(), u.getName());
			}
			
			// Ejmplo de Chart para el caso 1.1			
			
			JFreeChart chart = ChartFactory.createPieChart(
					"Sample Pie Chart",
					dataset,
					true, // legend?
					true, // tooltips?
					false // URLs?
					);
			
			JFreeChart chartBars= ChartFactory.createBarChart(
					"Bar Chart Demo", // chart title
					"User", // domain axis label
					"Nº decisiones", // range axis label
					datasetBars, // data
					PlotOrientation.VERTICAL, // orientation
					true, // include legend
					true, // tooltips?
					false // URLs?
					);
			
			ChartPanel panel = new ChartPanel(chart);
			getContentPane().add(panel);
			panel.setPreferredSize(new java.awt.Dimension(242, 74));
			ChartPanel panel2 = new ChartPanel(chartBars);
			getContentPane().add(panel2);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonPermissionRole e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLoggedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private int getCountKnowledgeUser(User u) {
		int cont = 0;
		for (Topic t: tw.getTopics()){
			if (t.getUser().equals(u))
				cont ++;
			for (Proposal p: t.getProposals()){
				if (p.getUser().equals(u))
					cont ++;
				for (Answer a: p.getAnswers()) {
					if (a.getUser().equals(u))
						cont ++;
				}
			}
		}
			
		return cont;
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
