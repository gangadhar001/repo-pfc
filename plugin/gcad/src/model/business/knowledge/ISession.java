package model.business.knowledge;

/**
 * Interface that defines the basic operations of the session class
 */
public interface ISession {

	public long getId();
	public int getCurrentActiveProject();
	public int getRole();
	
}
