package dawgdash.entities;

import java.util.ArrayList;


public class Worker extends User {
	private int ID;
	private String name;
	private String email;
	private String username; // i dint think we use this, name can be username
	private String password;
	private int transportation;
	private int rating;
	private int totalRatings;
	private int totalDeliveries;
	private int pendingDeliveries;
	private String role;
	private ArrayList<Delivery> deliveryList;

	/**
	 * Creates a when a new worker is added. We should know some of the paramters, and initialize
	 * the rest to default values;
	 * @param name
	 * @param email
	 * @param username
	 * @param password
	 * @param transportation
	 * @param role
	 */
	public Worker(String name, String email, String username, String password, int transportation, String role) {
		ID = 0;//Questioning the id
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.transportation = transportation;
		rating = 0;
		totalRatings = 0;
		totalDeliveries = 0;
		pendingDeliveries = 0;
		this.role = role;
		deliveryList = null; //NOT sure what to do here
	}

	/**
	 * Creates a worker when we know all the parameters (retrieving from database)
	 * @param ID
	 * @param name
	 * @param email
	 * @param username
	 * @param password
	 * @param transportation
	 * @param rating
	 * @param totalRatings
	 * @param totalDeliveries
	 * @param pendingDeliveries
	 * @param role
	 * @throws Exception
	 */
	public Worker(int ID, String name, String email, String username, String password, int transportation, 
			int rating, int totalRatings, int totalDeliveries, int pendingDeliveries, String role) throws Exception {
		this.ID = ID;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.transportation = transportation;
		this.rating = rating;
		this.totalRatings = totalRatings;
		this.totalDeliveries = totalDeliveries;
		this.pendingDeliveries = pendingDeliveries;
		this.role = role;
		//DBHelper instance = new DBHelper();
		//deliveryList = instance.getDeliveryListWorker(ID);
		//TODO: ADD BACK LATER
	}

	public ArrayList<Delivery> getDeliveryList() {
		return deliveryList;
	}

	public void setDeliveryList(ArrayList<Delivery> deliveryList) {
		this.deliveryList = deliveryList;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTransportation() {
		return transportation;
	}
	public void setTransportation(int transportation) {
		this.transportation = transportation;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getTotalRatings() {
		return totalRatings;
	}
	public void setTotalRatings(int totalRatings) {
		this.totalRatings = totalRatings;
	}
	public int getTotalDeliveries() {
		return totalDeliveries;
	}
	public void setTotalDeliveries(int totalDeliveries) {
		this.totalDeliveries = totalDeliveries;
	}
	public int getPendingDeliveries() {
		return pendingDeliveries;
	}
	public void setPendingDeliveries(int pendingDeliveries) {
		this.pendingDeliveries = pendingDeliveries;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
    }

}