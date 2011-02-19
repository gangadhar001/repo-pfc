package model.graph;

import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

/**
 * This class represents a content provider for the graph. It is used to return the nodes 
 * that are connected to other
 */
public class NodeContentProvider extends ArrayContentProvider implements IGraphEntityContentProvider {

	@Override
	public Object[] getConnectedTo(Object entity) {
		Object[] result = null;
		if (entity instanceof Topic)
			result = ((Topic)entity).getProposals().toArray();
		else if (entity instanceof Proposal)
			result = ((Proposal)entity).getAnswers().toArray();
		return result;
	}

}
