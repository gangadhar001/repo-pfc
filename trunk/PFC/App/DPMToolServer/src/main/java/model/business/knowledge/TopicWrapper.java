package model.business.knowledge;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents a set of Topics for one Project
 */
@XmlRootElement (name = "Topics" )
@XmlAccessorType( XmlAccessType.FIELD )
public class TopicWrapper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2825778853241760000L;
	
	@XmlElement( name = "Topic" )
	private ArrayList<Topic> topics = new ArrayList<Topic>();;
	
	public TopicWrapper() {
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
		return "Topic Wrapper " + topics;
	}

	public Topic getTopic(Topic t) {
		boolean found = false;
		Topic result = null;
		for (int i=0; i<topics.size() && !found; i++) {
			if (topics.get(i).equals(t)) {
				result = topics.get(i);
				found=true;
			}
		}
		return result;
	}

}
