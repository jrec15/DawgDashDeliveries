package dawgdash.entities;

public abstract class User {
	private int id;
	private String name;
	private String email;
	private String username;
	private String password;
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
}
