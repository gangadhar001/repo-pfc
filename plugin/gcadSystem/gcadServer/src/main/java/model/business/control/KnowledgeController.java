package model.business.control;

import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

import model.business.knowledge.Answer;
import model.business.knowledge.Groups;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Subgroups;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
import persistence.DAOAnswer;
import persistence.DAOProposal;
import persistence.DAOTopic;

/**
 * This class represents a controller that allows to manage knowledge.
 */
public class KnowledgeController {
		
	public static TopicWrapper getTopicsWrapper(long sessionId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		// This method access to database only the first time, when "topicWrapper" is not initialized.
		//if (topicWrapper == null) {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Get.name()));

		TopicWrapper topicWrapper = new TopicWrapper();
		for (Topic t: DAOTopic.queryTopicsProject(SessionController.getSession(sessionId).getCurrentActiveProject()))
			topicWrapper.add(t);
		//}
		return topicWrapper;
	}
	
	/**
	 * This method returns all existing proposals from a project 
	 * @throws NotLoggedException 
	 */
	public static ArrayList<Proposal> getProposals(long sessionId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Get.name()));

		ArrayList<Proposal> proposals = new ArrayList<Proposal>();
		for (Topic t: getTopicsWrapper(sessionId).getTopics()) {
			proposals.addAll(t.getProposals());
		}
		return proposals;
	}
	
	/**
	 * This method returns all existing answers from a project
	 * @throws NotLoggedException 
	 */
	public static ArrayList<Answer> getAnswers(long sessionId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Answer.name(), Operations.Get.name()));

		ArrayList<Answer> answers = new ArrayList<Answer>();
		for (Topic t: getTopicsWrapper(sessionId).getTopics()) {
			for (Proposal p: t.getProposals()) {
				answers.addAll(p.getAnswers());
			}
		}
		return answers;
	}

	/**
	 * Methods used to add new knowledge
	 * @throws NotLoggedException 
	 */
	public static void addTopic(long sessionId, User u, Project p, Topic topic) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Add.name()));

		// Set the user and project of the topic
		topic.setUser(u);
		topic.setProject(p);
		// Add new topic
		DAOTopic.insert(topic);
		//topicWrapper.add(topic);
		
	}
	
	public static void addProposal(long sessionId, User u, Proposal proposal, Topic parent) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Add.name()));

		// Set the user of the proposal
		proposal.setUser(u);
		DAOProposal.insert(proposal, parent.getId());
		parent.add(proposal);
	}
	
	public static void addAnswer(long sessionId, User u, Answer answer, Proposal parent) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Answer.name(), Operations.Add.name()));

		// Set the user of the proposal
		answer.setUser(u);
		DAOAnswer.insert(answer, parent.getId());
		parent.add(answer);
	}
	
	/**
	 * Methods used to modify knowledge
	 * @throws NotLoggedException 
	 */	
	public static void modifyTopic(long sessionId, User user, Topic newTopic, Topic oldTopic) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Modify.name()));

		newTopic.setUser(user);
		//// Copy proposal list and project to new Topic
		//newTopic.setProposals(oldTopic.getProposals());
		//newTopic.setProject(oldTopic.getProject());
		//newTopic.setId(oldTopic.getId());
		//// Remove old topic
		//int index = topicWrapper.getTopics().indexOf(oldTopic);
		//topicWrapper.remove(oldTopic);
		// Add new topic
		DAOTopic.update(newTopic);
		//topicWrapper.getTopics().add(index, newTopic);		
	}
	
	public static void modifyProposal(long sessionId, User user, Proposal newProposal, Proposal oldProposal, Topic newParent) throws SQLException, NonPermissionRole, InstantiationException, IllegalAccessException, ClassNotFoundException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Modify.name()));

		newProposal.setUser(user);				
		// If the new parent is different from the previous one, the old proposal is removed
		Topic t = findParentProposal(sessionId, oldProposal);
		if (t!=null && !t.equals(newParent)) {	
			t.remove(oldProposal);
			// Delete the old proposal from old topic (it deletes the answers too)
			DAOProposal.delete((Proposal) oldProposal.clone());
			DAOProposal.insert(newProposal, newParent.getId());
			// Insert answers from old Proposal
			for (Answer a: oldProposal.getAnswers())
				DAOAnswer.insert(a, newProposal.getId());
			newProposal.setAnswers(oldProposal.getAnswers());
			newParent.add(newProposal);
			
		}
		else {
			newProposal.setId(oldProposal.getId());
			newParent.remove(oldProposal);
			newParent.add(newProposal);			
			DAOProposal.update(newProposal);
		}
	}
	
	public static void modifyAnswer(long sessionId, User user, Answer newAnswer, Answer oldAnswer, Proposal newParent) throws SQLException, NonPermissionRole, InstantiationException, IllegalAccessException, ClassNotFoundException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Answer.name(), Operations.Modify.name()));

		newAnswer.setUser(user);
		// If the new parent is different from the previous one, the old answer is removed
		Proposal p = findParentAnswer(sessionId, oldAnswer);
		if (p!=null && !p.equals(newParent)) {		
			p.getAnswers().remove(oldAnswer);
			DAOAnswer.delete(oldAnswer);
			DAOAnswer.insert(newAnswer, newParent.getId());
			newParent.add(newAnswer);
		}
		else {
			newAnswer.setId(oldAnswer.getId());
			newParent.remove(oldAnswer);
			newParent.add(newAnswer);
			DAOAnswer.update(newAnswer);
		}
	}
	
	public static void deleteTopic(long sessionId, Topic to) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Delete.name()));

		//getTopicsWrapper(sessionId).remove(to);
		DAOTopic.delete(to);
	}
	
	public static void deleteProposal(long sessionId, Proposal p) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Delete.name()));

		//Topic t = findParentProposal(sessionId, p);
		//if (t!=null)
		//    t.getProposals().remove(p);
		DAOProposal.delete(p);
	}

	public static void deleteAnswer(long sessionId, Answer a) throws SQLException, NonPermissionRole, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Answer.name(), Operations.Delete.name()));

		//Proposal p = findParentAnswer(sessionId, a);
		//if (p!=null)
		//    p.getAnswers().remove(a);
		DAOAnswer.delete(a);
	}

	public static Proposal findParentAnswer(long sessionId, Answer a) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		Proposal result = null;
		for(Topic t: getTopicsWrapper(sessionId).getTopics()){
			for (Proposal p: t.getProposals())
				if (p.getAnswers().contains(a))
					result = p;
		}
		return result;
	}
	
	public static Topic findParentProposal(long sessionId, Proposal p) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NonPermissionRole, NotLoggedException {
		Topic result = null;	
		for(Topic t: getTopicsWrapper(sessionId).getTopics()) {
			if (t.getProposals().contains(p))
				result = t;
		}
		return result;
	}		
}
