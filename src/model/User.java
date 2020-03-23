package model;

public class User extends Entity {

	private String user;
	private String password;
	private Person person;
	
	public User() {}
	
	public User(String user, String password, Person person) {
		super();
		this.user = user;
		this.password = password;
		this.person = person;
	}
	
	public User(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}
