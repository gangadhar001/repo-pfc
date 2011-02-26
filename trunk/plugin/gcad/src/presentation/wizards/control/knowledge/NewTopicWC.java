package presentation.wizards.control.knowledge;


import java.util.Date;

import model.business.knowledge.Topic;
import presentation.wizards.knowledge.TopicWP;

/**
 * This class allows to login
 */
public class NewTopicWC extends AbstractNewKnowledgeWC {
	
	public NewTopicWC(String wizardTitle) {
		super(wizardTitle);
	}

	@Override
	public boolean performFinish() {
		TopicWP page = (TopicWP) super.getPage();
		String title = page.getTitleText();
		String descriptionText = page.getDescriptionText();
		final Topic topic = new Topic(title, descriptionText, new Date());
		
		return super.runNewTopic(topic);
	}
}
