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
	private ArrayList<Topic> topics = new ArrayList<Topic>();