package presentation.dataVisualization;

import javax.swing.JTree;
import presentation.utils.ImageTreeCellRenderer;

import model.business.knowledge.TopicWrapper;

public class KnowledgeTree extends JTree {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -728360403247318096L;

	public KnowledgeTree (TopicWrapper knowledge) {
		super(new TreeContentProvider(knowledge));
		this.setCellRenderer(new ImageTreeCellRenderer());
	}

}
