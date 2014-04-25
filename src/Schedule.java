//package dawgdashdeliveries;


public class Schedule {
	private int workerID;
	private String sunday;
	private String monday;
	private String tuesday;
	private String wednesday;
	private String thursday;
	private String friday;
	private String saturday;
	
	/**
	 * Initializes a schedule for a worker to default values.
	 * @param workerID
	 */
	public Schedule(int workerID) {
		this.workerID = workerID;
		this.sunday = "00-00";
		this.monday = "00-00";
		this.tuesday = "00-00";
		this.wednesday = "00-00";
		this.thursday = "00-00";
		this.friday = "00-00";
		this.saturday = "00-00";
	}
	
	/**
	 * Creates a schedule based on a worker and his hours. NOTE for now format as "xx-xx"
	 * @param workerID
	 * @param sunday
	 * @param monday
	 * @param tuesday
	 * @param wednesday
	 * @param thursday
	 * @param friday
	 * @param saturday
	 */
	public Schedule(int workerID, String sunday, String monday, String tuesday, String wednesday,
			String thursday, String friday, String saturday) {
		this.workerID = workerID;
		this.sunday = sunday;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
	}
	
	
	public int getWorkerID() {
		return workerID;
	}
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}
	public String getSunday() {
		return sunday;
	}
	public void setSunday(String sunday) {
		this.sunday = sunday;
	}
	public String getMonday() {
		return monday;
	}
	public void setMonday(String monday) {
		this.monday = monday;
	}
	public String getTuesday() {
		return tuesday;
	}
	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}
	public String getWednesday() {
		return wednesday;
	}
	public void setWednesday(String wednesday) {
		this.wednesday = wednesday;
	}
	public String getThursday() {
		return thursday;
	}
	public void setThursday(String thursday) {
		this.thursday = thursday;
	}
	public String getFriday() {
		return friday;
	}
	public void setFriday(String friday) {
		this.friday = friday;
	}
	public String getSaturday() {
		return saturday;
	}
	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}
	
	
}
