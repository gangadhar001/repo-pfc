package model.business.knowledge;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "Topics" )
@XmlAccessorType( XmlAccessType.FIELD )
public class TopicWrapper {
	
	@XmlElement( name = "Topic" )
	private ArrayList<Topic> topics;
	
	public TopicWrapper() {
		topics = new ArrayList<Topic>();
	}
	
	public void add(Topic topic) {
		topics.add(topic);
		
	}
	
	public void remove(Topic topic) {
		topics.remove(topic);
		
	}

	public ArrayList<Topic> getTopics() {
		return topics;
	}

	public void setTopics(ArrayList<Topic> topics) {
		this.topics = topics;
	}	
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Topics:\n");
		for (Topic t: topics ) {
			result.append("      " + t + "\n");
		}	
		return result.toString();
	}

}
