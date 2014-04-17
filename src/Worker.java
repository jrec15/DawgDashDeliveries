import java.util.ArrayList;


public class Worker {
	private int ID;
	private String name;
	private String email;
	private String password;
	private int transportation;
	private int rating;
	private int totalRatings;
	private int totalDeliveries;
	private int pendingDeliveries;
	private String role;
	private ArrayList<Delivery> deliveryList = new ArrayList<Delivery>;
	
	public Worker(int ID, String name, String email, String password, int transportation, 
			int rating, int totalRatings, int totalDeliveries, int pendingDeliveries, String role) {
		this.ID = ID;
		this.name = name;
		this.email = email;
		this.password = password;
		this.transportation = transportation;
		this.rating = rating;
		this.totalRatings = totalRatings;
		this.totalDeliveries = totalDeliveries;
		this.pendingDeliveries = pendingDeliveries;
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
	
	
	
}
