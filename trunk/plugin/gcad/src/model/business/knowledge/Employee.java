package model.business.knowledge;


/**
 * This class represents an Employee
 */
public class Employee extends User {
	
	//TODO: pasarlo al constructor
	private Project project;
	
	public Employee() {
		super();
	}

	public Employee(String nif, String login, String password, String name, String surname, String email, String telephone) {
		super(nif, login, password, name, surname, email, telephone);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}	
	
	public UserRole getRol(){
		return UserRole.Employee;
	}
	
	

}
