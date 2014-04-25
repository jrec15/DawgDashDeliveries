//package dawgdashdeliveries;


public class Client {
	private int ID;
	private String name;
	private String defaultAddress;
	private String email;
	private String username;
	private String password;
	
	public Client(String name, String defaultAddress, String email, String username, String password) {
		ID = 0;
		this.name = name;
		this.defaultAddress = defaultAddress;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Creates a client when we know all the parameters
	 * @param ID
	 * @param name
	 * @param defaultAddress
	 * @param email
	 * @param username
	 * @param password
	 */
	public Client(int ID, String name, String defaultAddress, String email, String username, String password) {
		this.ID = ID;
		this.name = name;
		this.defaultAddress = defaultAddress;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefaultAddress() {
		return defaultAddress;
	}
	public void setDefaultAddress(String defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
