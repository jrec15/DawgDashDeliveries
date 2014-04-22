
public class Estimate {
	private int ID;
	private int clientID;
	private int duration;
	private double price;
	private int transportation;
	private String sourceAddress;
	private String destinationAddress;
	
	/**
	 * Giving initial estimate. Will have to set ID later as it is not known until entered in DB. Ignoring google maps
	 * for now so just enter a value for duration (in minutes)
	 * @param clientID
	 * @param duration Will find with google maps later, for now just give some value in minutes
	 * @param transportation Int value (1,2,3) representing bike, car, or truck
	 * @param sourceAddress
	 * @param destinationAddress
	 */
	public Estimate(int clientID, int duration, int transportation, String sourceAddress, String destinationAddress) {
		ID = 0;
		this.clientID = clientID;
		this.duration = duration;
		price = duration*(.5) + 10;
		this.transportation = transportation;
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
	}
	
	/**
	 * Creates an estimate object when we already know all the parameters. Will be used when getting estimates from
	 * the database.
	 * @param ID
	 * @param clientID
	 * @param duration
	 * @param price
	 * @param transportation
	 * @param sourceAddress
	 * @param destinationAddress
	 */
	public Estimate(int ID, int clientID, int duration, double price, int transportation, String sourceAddress, 
			String destinationAddress) {
		this.ID = ID;
		this.clientID = clientID;
		this.duration = duration;
		this.price = price;
		this.transportation = transportation;
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getTransportation() {
		return transportation;
	}

	public void setTransportation(int transportation) {
		this.transportation = transportation;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
}
