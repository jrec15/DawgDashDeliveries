
/**
 * Creates a ClientUser object to represent a client's information
 */
public class ClientUser {
	private String name;
	private String defaultAddress;
	private String email;
	private String username;
	private String password;
	
	/**
	 * Constructor to create a new ClientUser object
	 * @param newName The name of the client
	 * @param newDefaultAddress The address the client wishes to save for shipping
	 * @param newEmail The email of the client
	 * @param newUsername The client's username
	 * @param password The client's password
	 */
	public ClientUser(String newName, String newDefaultAddress, 
			String newEmail, String newUsername, String password);
	
	public String getName();
	public String getDefaultAddress();
	public String getEmail();
	public String getUsername();
	public String getPassword();
	
	public void setName(String newName);
	public void setDefaultAddress(String newDefaultAddress);
	public void setEmail(String newEmail);
	public void setUsername(String newUsername);
	public void setPassword(String newPassword);
	
}
