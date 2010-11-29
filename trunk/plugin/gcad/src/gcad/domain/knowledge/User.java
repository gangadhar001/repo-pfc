package gcad.domain.knowledge;

/**
 * Abstract class that represents a system user
 */
public abstract class User {

	protected String nif;
	protected String login;
	protected String password;
	protected String name;
	protected String surname;
	protected String email;
	protected String telephone;
	
	public User() {
		nif = "";
		login = "";
		password = "";
		name = "";
		surname = "";
		email = "";
		telephone = "";
	}
	
	public User(String nif, String login, String password, String name, String surname, String email, String telephone) {
		this.nif = nif;
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telephone = telephone;
	}

	public abstract UserRole getRol();
	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public boolean equals(Object o) {
		User u;
		boolean dev;
		
		dev = false;
		if(o != null && o instanceof User) {
			u = (User)o;
			if(u.getClass().equals(this.getClass())) {
				dev = nif.equals(u.getNif()) && login.equals(u.getLogin()) && password.equals(u.getPassword()) && name.equals(u.getName()) && surname.equals(u.getSurname()) && email.equals(u.getEmail()) && telephone.equals(u.getTelephone());
			}
		}
		return dev;
	}

	public String toString() {
		return "User: " + nif + ", " + login + ", " + password + ", " + name + ", " + surname + ", " + email + ", " + telephone;
	}
	
}
