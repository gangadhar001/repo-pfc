package gcad.domain;

public class Employee extends User {
	
	public Employee() {
		super();
	}

	public Employee(String nif, String login, String password, String name, String surname, String email, String telephone) {
		super(nif, login, password, name, surname, email, telephone);
	}
	
	/*
	public EmployeeRole getRol(){
		return RolesUsuario.Citador;
	}*/

}
