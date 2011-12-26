package model.business.control;

import internationalization.AppInternationalization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import model.business.knowledge.Answer;
import model.business.knowledge.File;
import model.business.knowledge.Groups;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Notification;
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
import resources.XMLUtilities;
import exceptions.NonExistentAnswerException;
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
	public static void addTopic(long sessionId, User u, Project p, Topic topic) throws Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Add.name()));

		if (exists(topic, getTopicsWrapper(sessionId).getTopics().toArray()))
			throw new SQLException(AppInternationalization.getString("SQLExistingKnowledgeAdd"));
			
		// Set the user and project of the topic
		topic.setUser(u);
		topic.setProject(p);
		// Add new topic
		DAOTopic.insert(topic);
		//topicWrapper.add(topic);
		
		createNotification(sessionId, topic, Operations.Add);		
	}

	public static void addProposal(long sessionId, User u, Proposal proposal, Topic parent) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentTopicException, Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Add.name()));

		if (exists(proposal, parent.getProposals().toArray()))
			throw new SQLException(AppInternationalization.getString("SQLExistingKnowledgeAdd"));
		
		// Set the user of the proposal
		proposal.setUser(u);
		DAOProposal.insert(proposal, parent.getId());
		parent.add(proposal);
		
		createNotification(sessionId, proposal, Operations.Add);
	}
	
	public static void addAnswer(long sessionId, User u, Answer answer, Proposal parent) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentProposalException, Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Answer.name(), Operations.Add.name()));

		if (exists(answer, parent.getAnswers().toArray()))
			throw new SQLException(AppInternationalization.getString("SQLExistingKnowledgeAdd"));
		
		// Set the user of the proposal
		answer.setUser(u);
		DAOAnswer.insert(answer, parent.getId());
		parent.add(answer);
		
		createNotification(sessionId, answer, Operations.Add);
	}
	
	/**
	 * Methods used to modify knowledge
	 */	
	public static void modifyTopic(long sessionId, User user, Topic newTopic) throws SQLException, NonPermissionRoleException, NotLoggedException, Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Modify.name()));

		if (exists(newTopic, getTopicsWrapper(sessionId).getTopics().toArray()))
			throw new SQLException(AppInternationalization.getString("SQLExistingKnowledgeEdit"));
		
		newTopic.setUser(user);		
		DAOTopic.update(newTopic);		
		
		createNotification(sessionId, newTopic, Operations.Modify);
	}
	
	public static void modifyProposal(long sessionId, User user, Proposal newProposal, Proposal oldProposal, Topic newParent) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentTopicException, NonExistentProposalException, Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Modify.name()));
		
		newProposal.setUser(user);
		Topic auxParent = (Topic) newParent.clone();
		// If the new parent is different from the previous one, the old proposal is removed
		Topic t = findParentProposal(sessionId, oldProposal);
		if (t!=null && !t.equals(newParent)) {	
			if (exists(newProposal, t.getProposals().toArray()))
				throw new SQLException(AppInternationalization.getString("SQLExistingKnowledgeEdit"));
			
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
				// Restore parent
				newParent = (Topic) auxParent.clone();
				throw e;
			} catch(NonExistentTopicException e) {
				// Restore parent
				newParent = (Topic) auxParent.clone();
				throw e;				
			} catch(SQLException e) {
				newParent = (Topic) auxParent.clone();
				throw e;	
			}
			
		}
		else {
			if (exists(newProposal, newParent.getProposals().toArray()))
				throw new SQLException(AppInternationalization.getString("SQLExistingKnowledgeEdit"));
			
			newParent.remove(oldProposal);
			newParent.add(newProposal);		
			try {
				DAOProposal.update(newProposal);
			} catch(NonExistentProposalException e) {
				// Restore parent
				newParent = (Topic) auxParent.clone();
				throw e;				
			} catch(SQLException e) {
				// Restore parent
				newParent = (Topic) auxParent.clone();
				throw e;	
			}			
		}
		
		createNotification(sessionId, newProposal, Operations.Modify);
	}
	
	public static void modifyAnswer(long sessionId, User user, Answer newAnswer, Answer oldAnswer, Proposal newParent) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentAnswerException, NonExistentProposalException, Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Answer.name(), Operations.Modify.name()));
		
		newAnswer.setUser(user);
		Proposal auxParent = (Proposal) newParent.clone();
		// If the new parent is different from the previous one, the old answer is removed
		Proposal p = findParentAnswer(sessionId, oldAnswer);
		if (p!=null && !p.equals(newParent)) {	
			if (exists(newAnswer, p.getAnswers().toArray()))
				throw new SQLException(AppInternationalization.getString("SQLExistingKnowledgeEdit"));
			
			try{
				p.getAnswers().remove(oldAnswer);
				DAOAnswer.delete(oldAnswer);
				DAOAnswer.insert(newAnswer, newParent.getId());
				newParent.add(newAnswer);
		
			} catch(NonExistentProposalException e) {
				// Restore parent
				newParent = (Proposal) auxParent.clone();
				throw e;
			} catch(NonExistentAnswerException e) {
				// Restore parent
				newParent = (Proposal) auxParent.clone();
				throw e;				
			} catch(SQLException e) {
				// Restore parent
				newParent = (Proposal) auxParent.clone();
				throw e;	
			}
		}
		else {
			if (exists(newAnswer, newParent.getAnswers().toArray()))
				throw new SQLException(AppInternationalization.getString("SQLExistingKnowledgeEdit"));
			
			try{
				newParent.remove(oldAnswer);
				newParent.add(newAnswer);
				DAOAnswer.update(newAnswer);
			} catch(NonExistentAnswerException e) {
				// Restore parent
				newParent = (Proposal) auxParent.clone();
				throw e;				
			} catch(SQLException e) {
				// Restore parent
				newParent = (Proposal) auxParent.clone();
				throw e;	
			}
		}
		
		createNotification(sessionId, newAnswer, Operations.Modify);
	}
	
	public static void deleteTopic(long sessionId, Topic to) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentTopicException, Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Topic.name(), Operations.Delete.name()));

		//getTopicsWrapper(sessionId).remove(to);
		DAOTopic.delete(to);
		
		createNotification(sessionId, to, Operations.Delete);
	}
	
	public static void deleteProposal(long sessionId, Proposal p) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentProposalException, Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Delete.name()));

		DAOProposal.delete(p);
		
		createNotification(sessionId, p, Operations.Delete);
	}

	public static void deleteAnswer(long sessionId, Answer a) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentAnswerException, Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Answer.name(), Operations.Delete.name()));

		DAOAnswer.delete(a);
		
		createNotification(sessionId, a, Operations.Delete);
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

	public static int attachFile(long sessionId, Knowledge k, File file) throws SQLException, NonPermissionRoleException, NotLoggedException, NonExistentProposalException, NonExistentAnswerException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Add.name()));

		k.getFiles().add(file);
//		DAOFile.insert(file);
		if (k instanceof Topic)
			DAOTopic.update((Topic)k);
		else if (k instanceof Proposal)
			DAOProposal.update((Proposal)k);
		else 
			DAOAnswer.update((Answer)k);
		
		// Return the auto-assigned id
		return file.getId();
	}
	
	public static <T> byte[] exportInformation(long sessionId, Project project) throws NonPermissionRoleException, NotLoggedException, JAXBException, SQLException {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Get.name()));
		TopicWrapper tw = getTopicsWrapper(sessionId, project);
		return (XMLUtilities.marshal(tw.getClass(), tw)).toByteArray();
	}

	public static void changeStatusKnowledge(long sessionId, Knowledge k) throws NonPermissionRoleException, NotLoggedException, SQLException, Exception {
		// Check if have permission to perform the operation
		SessionController.checkPermission(sessionId, new Operation(Groups.Knowledge.name(), Subgroups.Proposal.name(), Operations.Get.name()));
		
		if (k instanceof Topic)
			DAOTopic.update((Topic)k);
		else if (k instanceof Proposal)
			DAOProposal.update((Proposal)k);
		else 
			DAOAnswer.update((Answer)k);
		
	}

	private static boolean exists(Knowledge k, Object[] objects) {
		boolean found = false;
		for(int i=0; i<objects.length && !found; i++) {
			if (((Knowledge)objects[i]).getTitle().equals(k.getTitle()))
					found = true;
		}
		return found;
	}
	
	// Method used to create an alert (notification) 
	// Create the notification for all the users of the current project
	private static void createNotification(long sessionId, Knowledge k, Operations operation) throws NonPermissionRoleException, NotLoggedException, SQLException, Exception {
		String message = "";
		if (operation.name().equals(Operations.Modify.name()))
			message = AppInternationalization.getString("ModifiedKnowledgeNotification");
		else if (operation.name().equals(Operations.Add.name()))
			message = AppInternationalization.getString("AddedKnowledgeNotification");
		else if (operation.name().equals(Operations.Delete.name()))
			message = AppInternationalization.getString("DeletedKnowledgeNotification");
			
		Project currentProject = searchCurrentProject(Server.getInstance().getProjects(sessionId), SessionController.getSession(sessionId).getCurrentActiveProject());
		Set<User> usersCurrentProject = new HashSet<User>(Server.getInstance().getUsersProject(sessionId, currentProject));
		Notification n = new Notification(k, "Unread", currentProject, message, usersCurrentProject);
		Server.getInstance().createNotification(sessionId, n);		
	}

	// Return the project that has a specified ID
	private static Project searchCurrentProject(List<Project> projects, int currentProject) {
		Project result = null;
		for(int i=0; i<projects.size() && result == null; i++)
			if (projects.get(i).getId() == currentProject)
				result = projects.get(i);
		return result;
	}

}
