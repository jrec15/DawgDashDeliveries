
/**
 * Creates a WorkerUser object to represent an employee's information
 */
public class WorkerUser {
	private String name;
	private String email;
	private String username;
	private String password;
	private String transportation;
	private int rating;
	private int totalRatings;
	private int totalDeliveries;
	private int pendingDeliveries;
	private String role;
	
	/**
	 * Constructor to create a new WorkerUser object
	 * @param newName The name of the worker
	 * @param newEmail The email of the worker
	 * @param newUsername The worker's username
	 * @param password The worker's password
	 * @param transportation The worker's mode of transportation
	 * @param rating The worker's calculated overall rating
	 * @param totalRatings The total number of ratings the worker has received
	 * @param totalDeliveries The total number of deliveries the worker has made
	 * @param pendingDeliveries Any deliveries assigned to the the work and still in progress
	 * @param role The role of the employee
	 */
	public WorkerUser(String newName, String newEmail, String newUsername, String password,
			String transportation, int rating, int totalRatings, int totalDeliveries, int pendingDeliveries,
			String role);
	
	public String getName();
	public String getEmail();
	public String getUsername();
	public String getPassword();
	public String getTransportation();
	public int getRating();
	public int getTotalRatings();
	public int getTotalDeliveries();
	public int getPendingDeliveries();
	public String getRole();
	
	
	public void setName(String newName);
	public void setEmail(String newEmail);
	public void setUsername(String newUsername);
	public void setPassword(String newPassword);
	public void setTransportation(String newTransportation);
	public void setRating(int newRating);
	public void setTotalRatings(int newTotalRatings);
	public void setTotalDeliveries(int newTotalDeliveries);
	public void setPendingDeliveries(int newPendingDeliveries);
	public void setRole(int newRole);
	
}
