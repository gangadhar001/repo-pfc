package model.treeviewer;

import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * This class represents a content provider for the tree. It is used to return the children 
 * that belongs to a parent
 */
public class KnowledgeContentProvider implements ITreeContentProvider{

	private static Object[] NO_ELEMENT = new Object[0];
	
	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] childrens = NO_ELEMENT;
		if (parentElement instanceof TopicWrapper) {
			TopicWrapper topic = (TopicWrapper) parentElement;
			childrens = topic.getTopics().toArray();
		}
		else if (parentElement instanceof Topic) {
			Topic topic = (Topic) parentElement;
			childrens = topic.getProposals().toArray();
		}
		else if (parentElement instanceof Proposal) {
			Proposal proposal = (Proposal) parentElement;
			childrens = proposal.getAnswers().toArray();
		}
		return childrens;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}


}
