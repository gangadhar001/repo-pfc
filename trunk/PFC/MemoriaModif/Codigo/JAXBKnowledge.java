@XmlAccessorType( XmlAccessType.FIELD )
public abstract class Knowledge implements Serializable {

	private static final long serialVersionUID = -7039151251262020404L;
	
	protected int id;
	protected String title;
	@XmlJavaTypeAdapter(DateAdapter.class)
	protected Date date;
	protected String description;
	protected KnowledgeStatus status;
	@XmlElement( name = "Author" ) protected User user;
	@XmlElement( name = "File" )
	private Set<File> files = new HashSet<File>();