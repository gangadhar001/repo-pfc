package presentation.wizards.control.knowledge;


import java.util.Date;

import model.business.knowledge.Topic;
import presentation.wizards.knowledge.TopicViewWP;

/**
 * This abstract class represents a New Topic Wizard Controller when it is shown since a view
 */
public class NewTopicWC extends AbstractNewKnowledgeWC {
	
	public NewTopicWC(String wizardTitle) {
		super(wizardTitle);
	}

	@Override
	public boolean performFinish() {
		TopicViewWP page = (TopicViewWP) super.getPage();
		String title = page.getTitleText();
		String descriptionText = page.getDescriptionText();
		final Topic topic = new Topic(title, descriptionText, new Date());
		
		return super.runNewTopic(topic);
	}
}
