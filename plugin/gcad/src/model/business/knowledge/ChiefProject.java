package model.business.knowledge;

public class ChiefProject extends Employee {
	
	// TODO: pasar el proyecto(s)
	public ChiefProject() {
		super();
	}

	public ChiefProject(String nif, String login, String password, String name, String surname, String email, String telephone) {
		super(nif, login, password, name, surname, email, telephone);
	}
	
	public UserRole getRole(){
		return UserRole.ChiefProject;
	}

}
