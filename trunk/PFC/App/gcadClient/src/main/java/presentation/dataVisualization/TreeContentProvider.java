package presentation.dataVisualization;

import javax.swing.tree.DefaultMutableTreeNode;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

/**
 * This class represents a content provider for the knowledge tree. It used to create the tree from topics collection
 */
public class TreeContentProvider {
	
	public static void setContentRootNode(DefaultMutableTreeNode root, TopicWrapper wr) {
		for (Topic t: wr.getTopics()) {
			DefaultMutableTreeNode childTopic = new DefaultMutableTreeNode(t);
			for (Proposal p: t.getProposals()) {
				DefaultMutableTreeNode childProposal = new DefaultMutableTreeNode(p);
				for (Answer a: p.getAnswers()) {
					DefaultMutableTreeNode childAnswer = new DefaultMutableTreeNode(a);
					childProposal.add(childAnswer);
				}
				childTopic.add(childProposal);
			}
			root.add(childTopic);
		}		
	}
}