package model.business.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.Answer;
import model.business.knowledge.File;
import model.business.knowledge.Groups;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Subgroups;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
import persistence.DAOAnswer;
import persistence.DAOFile;
import persistence.DAOProposal;
import persistence.DAOTopic;
import exceptions.NonExistentAnswerException;
import exceptions.NonExistentFileException;
import exceptions.NonExistentProposalException;
import exceptions.NonExistentTopicException;
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

/**
 * This class represents a controller that allows to manage knowledge.
 */
public class KnowledgeController {
		
	// Method used to get all the hierarchy of knowledge of the current project
	public static TopicWrapper getTopicsWrapper(long sessionId) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Get.name()));

		TopicWrapper topicWrapper = new TopicWrapper();
		try {
			List<Topic> topics = DAOTopic.queryTopicsProject(SessionController.getSession(sessionId).getCurrentActiveProject());
			for (Topic t: topics)
				topicWrapper.add(t);
		} catch (NonExistentTopicException e) { 
			// Ignore exception, in order to return an empty list
		}
		return topicWrapper;
	}
	
	// Method used to get all the hierarchy of knowledge of a specific project
	public static TopicWrapper getTopicsWrapper(long sessionId, Project p) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Get.name()));

		TopicWrapper topicWrapper = new TopicWrapper();
		List<Topic> topics;
		try {
			topics = DAOTopic.queryTopicsProject(p.getId());
			for (Topic t: topics)
				topicWrapper.add(t);
		} catch (NonExistentTopicException e) {
			// Ignore exception, in order to return an empty list
		}
		return topicWrapper;
	}
	
	/**
	 * This method returns all existing proposals from current project 
	 */
	public static ArrayList<Proposal> getProposals(long sessionId) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Get.name()));

		ArrayList<Proposal> proposals = new ArrayList<Proposal>();
		for (Topic t: getTopicsWrapper(sessionId).getTopics()) {
			proposals.addAll(t.getProposals());
		}
		return proposals;
	}
	
	/**
	 * This method returns all existing answers from current project
	 */
	public static ArrayList<Answer> getAnswers(long sessionId) throws SQLException, NonPermissionRoleException, NotLoggedException {
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
	 */
	public static void addTopic(long sessionId, User u, Project p, Topic topic) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Add.name()));

		// Set the user and project of the topic
		topic.setUser(u);
		topic.setProject(p);
		// Add new topic
		DAOTopic.insert(topic);
		//topicWrapper.add(topic);
		
	}
	
	public static void addProposal(long sessionId, User u, Proposal proposal, Topic parent) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentTopicException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Add.name()));

		// Set the user of the proposal
		proposal.setUser(u);
		DAOProposal.insert(proposal, parent.getId());
		parent.add(proposal);
	}
	
	public static void addAnswer(long sessionId, User u, Answer answer, Proposal parent) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentProposalException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Answer.name(), Operations.Add.name()));

		// Set the user of the proposal
		answer.setUser(u);
		DAOAnswer.insert(answer, parent.getId());
		parent.add(answer);
	}
	
	/**
	 * Methods used to modify knowledge
	 */	
	public static void modifyTopic(long sessionId, User user, Topic newTopic) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Modify.name()));

		newTopic.setUser(user);		
		DAOTopic.update(newTopic);		
	}
	
	public static void modifyProposal(long sessionId, User user, Proposal newProposal, Proposal oldProposal, Topic newParent) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentTopicException, NonExistentProposalException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Modify.name()));
		
		newProposal.setUser(user);
		Topic auxParent = (Topic) newParent.clone();
		// If the new parent is different from the previous one, the old proposal is removed
		Topic t = findParentProposal(sessionId, oldProposal);
		if (t!=null && !t.equals(newParent)) {	
			try {
				t.remove(oldProposal);
				// Delete the old proposal from old topic (it deletes the answers too)
				DAOProposal.delete((Proposal) oldProposal.clone());
				DAOProposal.insert(newProposal, newParent.getId());
				// Insert answers from old Proposal
				for (Answer a: oldProposal.getAnswers())
					DAOAnswer.insert(a, newProposal.getId());
				newProposal.setAnswers(oldProposal.getAnswers());
				newParent.add(newProposal);
			} catch(NonExistentProposalException e) {
				// Restaure parent
				newParent = (Topic) auxParent.clone();
				throw e;
			} catch(NonExistentTopicException e) {
				// Restaure parent
				newParent = (Topic) auxParent.clone();
				throw e;				
			} catch(SQLException e) {
				newParent = (Topic) auxParent.clone();
				throw e;	
			}
			
		}
		else {
			newParent.remove(oldProposal);
			newParent.add(newProposal);		
			try {
				DAOProposal.update(newProposal);
			} catch(NonExistentProposalException e) {
				// Restaure parent
				newParent = (Topic) auxParent.clone();
				throw e;				
			} catch(SQLException e) {
				newParent = (Topic) auxParent.clone();
				throw e;	
			}			
		}
	}
	
	public static void modifyAnswer(long sessionId, User user, Answer newAnswer, Answer oldAnswer, Proposal newParent) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentAnswerException, NonExistentProposalException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Answer.name(), Operations.Modify.name()));
		
		newAnswer.setUser(user);
		Proposal auxParent = (Proposal) newParent.clone();
		// If the new parent is different from the previous one, the old answer is removed
		Proposal p = findParentAnswer(sessionId, oldAnswer);
		if (p!=null && !p.equals(newParent)) {	
			try{
				p.getAnswers().remove(oldAnswer);
				DAOAnswer.delete(oldAnswer);
				DAOAnswer.insert(newAnswer, newParent.getId());
				newParent.add(newAnswer);
		
			} catch(NonExistentProposalException e) {
				// Restaure parent
				newParent = (Proposal) auxParent.clone();
				throw e;
			} catch(NonExistentAnswerException e) {
				// Restaure parent
				newParent = (Proposal) auxParent.clone();
				throw e;				
			} catch(SQLException e) {
				newParent = (Proposal) auxParent.clone();
				throw e;	
			}
		}
		else {
			try{
				newParent.remove(oldAnswer);
				newParent.add(newAnswer);
				DAOAnswer.update(newAnswer);
			} catch(NonExistentAnswerException e) {
				// Restaure parent
				newParent = (Proposal) auxParent.clone();
				throw e;				
			} catch(SQLException e) {
				newParent = (Proposal) auxParent.clone();
				throw e;	
			}
		}
	}
	
	public static void deleteTopic(long sessionId, Topic to) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentTopicException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Delete.name()));

		//getTopicsWrapper(sessionId).remove(to);
		DAOTopic.delete(to);
	}
	
	public static void deleteProposal(long sessionId, Proposal p) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentProposalException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Delete.name()));

		DAOProposal.delete(p);
	}

	public static void deleteAnswer(long sessionId, Answer a) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentAnswerException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Answer.name(), Operations.Delete.name()));

		DAOAnswer.delete(a);
	}

	public static Proposal findParentAnswer(long sessionId, Answer a) throws SQLException, NonPermissionRoleException, NotLoggedException {
		Proposal result = null;
		for(Topic t: getTopicsWrapper(sessionId).getTopics()){
			for (Proposal p: t.getProposals())
				if (p.getAnswers().contains(a))
					result = p;
		}
		return result;
	}
	
	public static Topic findParentProposal(long sessionId, Proposal p) throws SQLException, NonPermissionRoleException, NotLoggedException {
		Topic result = null;	
		for(Topic t: getTopicsWrapper(sessionId).getTopics()) {
			if (t.getProposals().contains(p))
				result = t;
		}
		return result;
	}

	public static int attachFile(long sessionId, File file) throws SQLException, NonPermissionRoleException, NotLoggedException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Add.name()));

		DAOFile.insert(file);
		// Return the auto-assigned id
		return file.getId();
	}

	public static List<File> getAttachedFiles(long sessionId, Knowledge k) throws NonPermissionRoleException, NotLoggedException, SQLException, NonExistentFileException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Get.name()));

		return DAOFile.queryAllFiles(k.getId());
		
	}		
}
