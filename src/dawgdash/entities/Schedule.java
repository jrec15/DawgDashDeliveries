package dawgdash.entities;
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
		this.sunday = "00-00-00-00";
		this.monday = "00-00-00-00";
		this.tuesday = "00-00-00-00";
		this.wednesday = "00-00-00-00";
		this.thursday = "00-00-00-00";
		this.friday = "00-00-00-00";
		this.saturday = "00-00-00-00";
	}
	
	/**
	 * Creates a schedule based on a worker and his hours.
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

	public String getSundayString() {
		String[] array = sunday.split("-");
		String result = "";
		boolean am = true;
		if (sunday.equals("00-00-00-00"))
			result = "Off";
		else {
			for (int i = 0; i < 4; i++) {
				if ((i == 0)||(i == 2)) {
					if (Integer.parseInt(array[i]) <= 12) {
						result += Integer.parseInt(array[i]) + ":";
						am = true;
						if (Integer.parseInt(array[i]) == 12)
							am = false;
					}
					else {
						result += (Integer.parseInt(array[i]) - 12) + ":";
						am = false;
					}
				}
				else {
					result += array[i];
					if (am)
						result += " am";
					else
						result += " pm";
					if (i == 1)
						result += " - ";
				}
			}
		}
		return result;
	}
	public void setSunday(String sunday) {
		this.sunday = sunday;
	}
	public String getMondayString() {
		String[] array = monday.split("-");
		String result = "";
		boolean am = true;
		if (monday.equals("00-00-00-00"))
			result = "Off";
		else {
			for (int i = 0; i < 4; i++) {
				if ((i == 0)||(i == 2)) {
					if (Integer.parseInt(array[i]) <= 12) {
						result += Integer.parseInt(array[i]) + ":";
						am = true;
						if (Integer.parseInt(array[i]) == 12)
							am = false;
					}
					else {
						result += (Integer.parseInt(array[i]) - 12) + ":";
						am = false;
					}
				}
				else {
					result += array[i];
					if (am)
						result += " am";
					else
						result += " pm";
					if (i == 1)
						result += " - ";
				}
			}
		}
		return result;
	}
	public void setMonday(String monday) {
		this.monday = monday;
	}
	public String getTuesdayString() {
		String[] array = tuesday.split("-");
		String result = "";
		boolean am = true;
		if (tuesday.equals("00-00-00-00"))
			result = "Off";
		else {
			for (int i = 0; i < 4; i++) {
				if ((i == 0)||(i == 2)) {
					if (Integer.parseInt(array[i]) <= 12) {
						result += Integer.parseInt(array[i]) + ":";
						am = true;
						if (Integer.parseInt(array[i]) == 12)
							am = false;
					}
					else {
						result += (Integer.parseInt(array[i]) - 12) + ":";
						am = false;
					}
				}
				else {
					result += array[i];
					if (am)
						result += " am";
					else
						result += " pm";
					if (i == 1)
						result += " - ";
				}
			}
		}
		return result;
	}
	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}
	public String getWednesdayString() {
		String[] array = wednesday.split("-");
		String result = "";
		boolean am = true;
		if (wednesday.equals("00-00-00-00"))
			result = "Off";
		else {
			for (int i = 0; i < 4; i++) {
				if ((i == 0)||(i == 2)) {
					if (Integer.parseInt(array[i]) <= 12) {
						result += Integer.parseInt(array[i]) + ":";
						am = true;
						if (Integer.parseInt(array[i]) == 12)
							am = false;
					}
					else {
						result += (Integer.parseInt(array[i]) - 12) + ":";
						am = false;
					}
				}
				else {
					result += array[i];
					if (am)
						result += " am";
					else
						result += " pm";
					if (i == 1)
						result += " - ";
				}
			}
		}
		return result;
	}
	public void setWednesday(String wednesday) {
		this.wednesday = wednesday;
	}
	public String getThursdayString() {
		String[] array = thursday.split("-");
		String result = "";
		boolean am = true;
		if (thursday.equals("00-00-00-00"))
			result = "Off";
		else {
			for (int i = 0; i < 4; i++) {
				if ((i == 0)||(i == 2)) {
					if (Integer.parseInt(array[i]) <= 12) {
						result += Integer.parseInt(array[i]) + ":";
						am = true;
						if (Integer.parseInt(array[i]) == 12)
							am = false;
					}
					else {
						result += (Integer.parseInt(array[i]) - 12) + ":";
						am = false;
					}
				}
				else {
					result += array[i];
					if (am)
						result += " am";
					else
						result += " pm";
					if (i == 1)
						result += " - ";
				}
			}
		}
		return result;
	}
	public void setThursday(String thursday) {
		this.thursday = thursday;
	}
	public String getFridayString() {
		String[] array = friday.split("-");
		String result = "";
		boolean am = true;
		if (friday.equals("00-00-00-00"))
			result = "Off";
		else {
			for (int i = 0; i < 4; i++) {
				if ((i == 0)||(i == 2)) {
					if (Integer.parseInt(array[i]) <= 12) {
						result += Integer.parseInt(array[i]) + ":";
						am = true;
						if (Integer.parseInt(array[i]) == 12)
							am = false;
					}
					else {
						result += (Integer.parseInt(array[i]) - 12) + ":";
						am = false;
					}
				}
				else {
					result += array[i];
					if (am)
						result += " am";
					else
						result += " pm";
					if (i == 1)
						result += " - ";
				}
			}
		}
		return result;
	}
	public void setFriday(String friday) {
		this.friday = friday;
	}
	public String getSaturdayString() {
		String[] array = saturday.split("-");
		String result = "";
		boolean am = true;
		if (saturday.equals("00-00-00-00"))
			result = "Off";
		else {
			for (int i = 0; i < 4; i++) {
				if ((i == 0)||(i == 2)) {
					if (Integer.parseInt(array[i]) <= 12) {
						result += Integer.parseInt(array[i]) + ":";
						am = true;
						if (Integer.parseInt(array[i]) == 12)
							am = false;
					}
					else {
						result += (Integer.parseInt(array[i]) - 12) + ":";
						am = false;
					}
				}
				else {
					result += array[i];
					if (am)
						result += " am";
					else
						result += " pm";
					if (i == 1)
						result += " - ";
				}
			}
		}
		return result;
	}
	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}
	
	public String getSunday() {
		return sunday;
	}

	public String getMonday() {
		return monday;
	}

	public String getTuesday() {
		return tuesday;
	}

	public String getWednesday() {
		return wednesday;
	}

	public String getThursday() {
		return thursday;
	}

	public String getFriday() {
		return friday;
	}

	public String getSaturday() {
		return saturday;
	}
	
}
