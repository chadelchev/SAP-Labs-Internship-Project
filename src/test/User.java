package test;

public class User {

	private String firstName;
	private String lastName;
	private String eMail;
	private String phone;
	private String location;
	private String password;

	// private int role;
	private String role;

	public User() {
		this.setFirstName(null);
		this.setLastName(null);
		this.seteMail(null);
		this.setPassword(null);
		this.setRole(null);
	}

	public User(String eMail, String firstName, String lastName, String phone,
			String location, String password, String role) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.seteMail(eMail);
		this.setPhone(phone);
		this.setLocation(location);
		this.setPassword(password);
		this.setRole(role);
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {

		return geteMail();
	}
}
