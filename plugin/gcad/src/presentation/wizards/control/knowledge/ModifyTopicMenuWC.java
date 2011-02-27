package presentation.wizards.control.knowledge;

import java.util.Date;

import model.business.knowledge.Topic;
import presentation.wizards.knowledge.ModifyTopicWP;

/**
 * This class represents a Wizard Controller to modify a topic when it is invoke since the menu
 */
public class ModifyTopicMenuWC extends AbstractModifyKnowledgeWC {

	public ModifyTopicMenuWC(String string) {
		super(string);
	}

	@Override
	public boolean performFinish() {
		ModifyTopicWP page = (ModifyTopicWP) super.getPage();
		String title = page.getTitleText();
		String description = page.getDescriptionText();
		Topic oldTopic = (Topic) page.getTopics().get(page.getItemCbTopic());
		Topic newTopic = new Topic(title, description, new Date());
		// Set the topic id with the id of the old topic
		newTopic.setId(oldTopic.getId());
			
		return runModifyTopic(newTopic, oldTopic);
	}

}
