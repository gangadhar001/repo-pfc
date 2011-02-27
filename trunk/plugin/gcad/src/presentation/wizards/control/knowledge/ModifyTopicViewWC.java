package presentation.wizards.control.knowledge;

import java.util.Date;

import model.business.knowledge.Topic;
import presentation.wizards.knowledge.ModifyTopicWP;

/**
 * This class represents a Wizard Controller to modify a topic when it is invoke since a view
 */
public class ModifyTopicViewWC extends AbstractModifyKnowledgeWC {

	private Topic topicSelected;

	public ModifyTopicViewWC(String wizardTitle, Topic topicSelected) {
		super(wizardTitle);
		this.topicSelected = topicSelected;
	}

	@Override
	public boolean performFinish() {
		ModifyTopicWP page = (ModifyTopicWP) super.getPage();
		String title = page.getTitleText();
		String description = page.getDescriptionText();
		Topic newTopic = new Topic(title, description, new Date());
		// Set the topic id with the id of the old topic
		newTopic.setId(topicSelected.getId());
			
		return runModifyTopic(newTopic, topicSelected);
	}

}
