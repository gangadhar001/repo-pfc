package model.business.knowledge;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * This class represents an Employee
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class Employee extends User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9206473778252947612L;

	public Employee () {
		
	}

	public Employee(String nif, String login, String password, String name, String surname, String email, String telephone, Company c) {
		super(nif, login, password, name, surname, email, telephone, c);
	}
	
	public UserRole getRole(){
		return UserRole.Employee;
	}

}
