package model.business.knowledge;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType( XmlAccessType.FIELD )
public class ChiefProject extends User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2298009369141533960L;

	public ChiefProject () {
		
	}

	public ChiefProject(String nif, String login, String password, String name, String surname, String email, String telephone, Company c) {
		super(nif, login, password, name, surname, email, telephone, c);
	}
	
	public UserRole getRole(){
		return UserRole.ChiefProject;
	}

}
