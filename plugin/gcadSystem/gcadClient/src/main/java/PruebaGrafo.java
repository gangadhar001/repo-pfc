

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

import bussiness.control.ClientController;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class PruebaGrafo extends javax.swing.JFrame {
	private static final long serialVersionUID = -844106998814982739L;
	private mxGraph graph;

	public PruebaGrafo()
	{
		super("Hello, World!");
		
		graph = new mxGraph();
		Object parent = graph.getDefaultParent();


		
		graph.getModel().beginUpdate();
		try
		{
//		   Object v1 = graph.insertVertex(parent, null, "Hello", 30, 20, 80,
//		         30);
//		   Object v2 = graph.insertVertex(parent, null, "World!",
//		         240, 150, 80, 30);
//		   Object v3 = graph.insertVertex(parent, null, "3",
//			         250, 180, 100, 50);
//		   graph.insertEdge(parent, null, "", v1, v2);
//		   graph.insertEdge(parent, null, "", v1, v3);
//		   
//		   Object v4 = graph.insertVertex(parent, null, "Hello", 20, 100, 80,
//			         30);
			
			TopicWrapper topics = ClientController.getInstance().getTopicsWrapper();
		 
			int xPos = 30;
			int yPos = 30;
			
			int cont = 1;
			int totalY = 0;
			for (Topic t: topics.getTopics()) {
				Object vertexTopic = graph.insertVertex(parent, null, t.getTitle(), xPos, yPos * cont, 80,
						30);
				for (Proposal p: t.getProposals()) {
					Object vertexProposal = graph.insertVertex(parent, null, p.getTitle(), xPos, 50, 80,
							30);
					graph.insertEdge(parent, null, "", vertexTopic, vertexProposal);
					for (Answer a : p.getAnswers()) {
						Object vertexAnswer = graph.insertVertex(parent, null, a.getTitle(), xPos, 100, 80,
								30);
						graph.insertEdge(parent, null, "", vertexProposal, vertexAnswer );
					}
				}
				cont++;
			}
		
		   mxHierarchicalLayout layout = new mxHierarchicalLayout(graph, SwingConstants.WEST);
		   layout.setFineTuning(true);
		   
		   layout.execute(graph.getDefaultParent());
			
		   graph.setCellsEditable(false);
			graph.setEdgeLabelsMovable(false);
			graph.setCellsDisconnectable(false);
			graph.setCellsBendable(false);
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
		   graph.getModel().endUpdate();
		}
		
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
		
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				graph.setSelectionCell(cell);
				
				if (cell != null)
				{
					System.out.println("cell="+graph.getLabel(cell));
				}
			}
		});
		
		graphComponent.setAntiAlias(true);
		graphComponent.setEnabled(false);
		

	PruebaGrafo frame = new PruebaGrafo();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(400, 320);
	frame.setVisible(true);

}
}
