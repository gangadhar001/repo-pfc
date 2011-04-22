package presentation.dataVisualization;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

/**
 * This class represents a content provider for the tree. It is used to return the children 
 * that belongs to a parent
 */
public class TreeContentProvider implements TreeModel{

	private TopicWrapper root;
	
	public TreeContentProvider(TopicWrapper root) {
		this.root = root;
	}
	
	@Override
	public Object getChild(Object parentElement, int index) {
		Object o = null;
		if (parentElement instanceof TopicWrapper) {
			TopicWrapper topic = (TopicWrapper) parentElement;
			o = topic.getTopics().get(index);
		}
		else if (parentElement instanceof Topic) {
			Topic topic = (Topic) parentElement;
			o = topic.getProposals().toArray()[index];
		}
		else if (parentElement instanceof Proposal) {
			Proposal proposal = (Proposal) parentElement;
			o = proposal.getAnswers().toArray()[index];
		}
		return o;
	}

	@Override
	public Object getRoot() {
		return this.root;
	}

	@Override
	public int getChildCount(Object parent) {
		int count = 0;
		if (parent instanceof TopicWrapper)
			count = ((TopicWrapper)parent).getTopics().size();
		if (parent instanceof Topic)
			count = ((Topic)parent).getProposals().size();
		if (parent instanceof Proposal)
			count = ((Proposal)parent).getAnswers().size();
		return count;
	}

	@Override
	public boolean isLeaf(Object node) {
		boolean result = false;
		if (node instanceof Answer)
			result = true;
		return result;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		int index = -1;
		if (parent instanceof TopicWrapper)
			index = ((TopicWrapper)parent).getTopics().indexOf(child);
		if (parent instanceof Topic) {
			Topic t = ((Topic)parent);
			Proposal[] pro = (Proposal[]) t.getProposals().toArray();
			for (int i=0; i<pro.length; i++) {
				if (pro[i].equals((Proposal)child))
					index = i;
			}
		}			
		if (parent instanceof Proposal) {
			Proposal p = ((Proposal)parent);
			Answer[] an = (Answer[]) p.getAnswers().toArray();
			for (int i=0; i<an.length; i++) {
				if (an[i].equals((Answer)child))
					index = i;
			}
		}
		return index;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}

}
