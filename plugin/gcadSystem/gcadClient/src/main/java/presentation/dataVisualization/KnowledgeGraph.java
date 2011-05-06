package presentation.dataVisualization;

import javax.swing.AbstractButton;

import model.business.knowledge.Knowledge;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

public class KnowledgeGraph {

	private mxGraph graph;

	public KnowledgeGraph () {	
		graph = new mxGraph() {
			
			// Cannot edit cells
			public boolean isCellEditable(Object cell) {
				return false;
			}
			
			// Converter used to show a label in a cell (node)
			@Override
			public String convertValueToString (Object cell) {
				String result = "";
				if (cell instanceof mxCell) {
					Object node = ((mxCell) cell).getValue();
					if (node instanceof Knowledge) {
						Knowledge k = (Knowledge) node;
						result = k.getTitle();
					}
				}
				else 
					result =  super.convertValueToString(cell);
				return result;
			}
			
			public String getToolTipForCell(Object cell)
			{
				// Show tooltip with the title of the knowledge and its description
				String tip = "";
				if (cell instanceof mxCell) {
					Object node = ((mxCell) cell).getValue();
					if (node instanceof Knowledge) {
						Knowledge k = (Knowledge) node;
						
						tip = "<html>";
						mxCellState state = getView().getState(cell);				
						tip += "Title = " + k.getTitle();	
						tip += "<br>";
						tip += "Description = " + k.getDescription();		
						tip += "</html>";
					}
				}
				return tip;
			}			
		};
	}

	public mxGraph getGraph() {
		return graph;
	}
}
