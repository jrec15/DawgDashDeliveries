//package dawgdashdeliveries;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Delivery {
	private int ID;
	private int clientID;
	private int workerID;
	private String deliveryStatus;
	private int rating;
	private String workerComment;
	private String clientComment;
	private Timestamp timeCompleted;
	private int duration;
	private double price;
	private int transportation;
	private String sourceAddress;
	private String destinationAddress;
	
	/**
	 * NOTE: This is only set to work for ASAP right now. To do a pickup time we would need that as a variable,
	 * which we did not originally establish.
	 * @param estimateID
	 * @param clientComment Any comments the client has about the delivery
	 * @throws Exception 
	 */
	public Delivery(int clientID, String clientComment, 
			int transportation, String sourceAddress, String destinationAddress) throws Exception {
		ID = 0;
		this.clientID = clientID;
		deliveryStatus = "Pending"; //OR whatever we want here
		rating = 0; // I'm thinking 0 represents no rating received. 1-5 represent an actual rating given
		workerComment = "None";
		this.clientComment = clientComment;
		Date date = new Date();
		timeCompleted = new Timestamp(date.getTime()); //TODO: Change to 00:00, as not finished, Not sure what to do with timeCompleted here
		duration = 30;
		price = 30;
		this.transportation = transportation;
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		
		User bestWorker = null;
		int minutesLeft;
		int minPending = 100;
		int maxRating = -1;
		DBHelper instance = new DBHelper();		
		for (User worker : instance.getUserList()) {
			 Schedule schedule = instance.getSchedule(worker.getID());
			 Calendar calendar = Calendar.getInstance();
			 int day = calendar.get(Calendar.DAY_OF_WEEK);
			 switch (day) {
				 case (1) : String[] sunday = schedule.getSunday().split("-");
				 			if (Integer.parseInt(sunday[0]) <= (calendar.get(Calendar.HOUR_OF_DAY))) {
				 				boolean check = true;
				 				if (Integer.parseInt(sunday[0]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
				 					check = false;
				 					if (Integer.parseInt(sunday[1]) <= (calendar.get(Calendar.MINUTE))) 
				 						check = true;
				 				}
				 				if (check == true) {
					 				if (Integer.parseInt(sunday[2]) >= (calendar.get(Calendar.HOUR_OF_DAY))) {
					 					if (Integer.parseInt(sunday[2]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
					 						check = false;
					 						minutesLeft = Integer.parseInt(sunday[3]) - (calendar.get(Calendar.MINUTE));
					 						if (Integer.parseInt(sunday[3]) > (calendar.get(Calendar.MINUTE)))
					 							check = true;
					 					}
					 					else
					 						minutesLeft = 60*(Integer.parseInt(sunday[2])) - 60*(calendar.get(Calendar.HOUR_OF_DAY))
					 							- (calendar.get(Calendar.MINUTE));
					 					if (check == true) {
					 						if (minutesLeft >= 30) {
					 							if (worker.getTransportation() >= transportation) {
					 								if (worker.getPendingDeliveries() <= minPending) {
					 									if (worker.getPendingDeliveries() < minPending) {
					 										minPending = worker.getPendingDeliveries();
					 										maxRating = worker.getRating();
					 										bestWorker = worker;
					 									}
					 									else if (worker.getPendingDeliveries() == minPending) {
					 										if (worker.getRating() > maxRating) {
					 											maxRating = worker.getRating();
					 											bestWorker = worker;
					 										}
					 									}
					 								}
					 							}
					 						}
					 					}
					 				}
				 				}
				 			}
				 			break;
				 case (2) : String[] monday = schedule.getMonday().split("-");
				 			if (Integer.parseInt(monday[0]) <= (calendar.get(Calendar.HOUR_OF_DAY))) {
				 				boolean check = true;
				 				if (Integer.parseInt(monday[0]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
				 					check = false;
				 					if (Integer.parseInt(monday[1]) <= (calendar.get(Calendar.MINUTE))) 
				 						check = true;
				 				}
				 				if (check == true) {
					 				if (Integer.parseInt(monday[2]) >= (calendar.get(Calendar.HOUR_OF_DAY))) {
					 					if (Integer.parseInt(monday[2]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
					 						check = false;
					 						minutesLeft = Integer.parseInt(monday[3]) - (calendar.get(Calendar.MINUTE));
					 						if (Integer.parseInt(monday[3]) > (calendar.get(Calendar.MINUTE)))
					 							check = true;
					 					}
					 					else
					 						minutesLeft = 60*(Integer.parseInt(monday[2])) - 60*(calendar.get(Calendar.HOUR_OF_DAY))
					 							- (calendar.get(Calendar.MINUTE));
					 					if (check == true) {
					 						if (minutesLeft >= 30) {
					 							if (worker.getTransportation() >= transportation) {
					 								if (worker.getPendingDeliveries() <= minPending) {
					 									if (worker.getPendingDeliveries() < minPending) {
					 										minPending = worker.getPendingDeliveries();
					 										maxRating = worker.getRating();
					 										bestWorker = worker;
					 									}
					 									else if (worker.getPendingDeliveries() == minPending) {
					 										if (worker.getRating() > maxRating) {
					 											maxRating = worker.getRating();
					 											bestWorker = worker;
					 										}
					 									}
					 								}
					 							}
					 						}
					 					}
					 				}
				 				}
				 			}
				 			break;
				 case (3) : String[] tuesday = schedule.getTuesday().split("-");
				 			if (Integer.parseInt(tuesday[0]) <= (calendar.get(Calendar.HOUR_OF_DAY))) {
				 				boolean check = true;
				 				if (Integer.parseInt(tuesday[0]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
				 					check = false;
				 					if (Integer.parseInt(tuesday[1]) <= (calendar.get(Calendar.MINUTE))) 
				 						check = true;
				 				}
				 				if (check == true) {
					 				if (Integer.parseInt(tuesday[2]) >= (calendar.get(Calendar.HOUR_OF_DAY))) {
					 					if (Integer.parseInt(tuesday[2]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
					 						check = false;
					 						minutesLeft = Integer.parseInt(tuesday[3]) - (calendar.get(Calendar.MINUTE));
					 						if (Integer.parseInt(tuesday[3]) > (calendar.get(Calendar.MINUTE)))
					 							check = true;
					 					}
					 					else
					 						minutesLeft = 60*(Integer.parseInt(tuesday[2])) - 60*(calendar.get(Calendar.HOUR_OF_DAY))
					 							- (calendar.get(Calendar.MINUTE));
					 					if (check == true) {
					 						if (minutesLeft >= 30) {
					 							if (worker.getTransportation() >= transportation) {
					 								if (worker.getPendingDeliveries() <= minPending) {
					 									if (worker.getPendingDeliveries() < minPending) {
					 										minPending = worker.getPendingDeliveries();
					 										maxRating = worker.getRating();
					 										bestWorker = worker;
					 									}
					 									else if (worker.getPendingDeliveries() == minPending) {
					 										if (worker.getRating() > maxRating) {
					 											maxRating = worker.getRating();
					 											bestWorker = worker;
					 										}
					 									}
					 								}
					 							}
					 						}
					 					}
					 				}
				 				}
				 			}
				 			break;
				 case (4) : String[] wednesday = schedule.getWednesday().split("-");
				 			if (Integer.parseInt(wednesday[0]) <= (calendar.get(Calendar.HOUR_OF_DAY))) {
				 				boolean check = true;
				 				if (Integer.parseInt(wednesday[0]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
				 					check = false;
				 					if (Integer.parseInt(wednesday[1]) <= (calendar.get(Calendar.MINUTE))) 
				 						check = true;
				 				}
				 				if (check == true) {
					 				if (Integer.parseInt(wednesday[2]) >= (calendar.get(Calendar.HOUR_OF_DAY))) {
					 					if (Integer.parseInt(wednesday[2]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
					 						check = false;
					 						minutesLeft = Integer.parseInt(wednesday[3]) - (calendar.get(Calendar.MINUTE));
					 						if (Integer.parseInt(wednesday[3]) > (calendar.get(Calendar.MINUTE)))
					 							check = true;
					 					}
					 					else
					 						minutesLeft = 60*(Integer.parseInt(wednesday[2])) - 60*(calendar.get(Calendar.HOUR_OF_DAY))
					 							- (calendar.get(Calendar.MINUTE));
					 					if (check == true) {
					 						if (minutesLeft >= 30) {
					 							if (worker.getTransportation() >= transportation) {
					 								if (worker.getPendingDeliveries() <= minPending) {
					 									if (worker.getPendingDeliveries() < minPending) {
					 										minPending = worker.getPendingDeliveries();
					 										maxRating = worker.getRating();
					 										bestWorker = worker;
					 									}
					 									else if (worker.getPendingDeliveries() == minPending) {
					 										if (worker.getRating() > maxRating) {
					 											maxRating = worker.getRating();
					 											bestWorker = worker;
					 										}
					 									}
					 								}
					 							}
					 						}
					 					}
					 				}
				 				}
				 			}
				 			break;
				 case (5) : String[] thursday = schedule.getThursday().split("-");
				 			if (Integer.parseInt(thursday[0]) <= (calendar.get(Calendar.HOUR_OF_DAY))) {
				 				boolean check = true;
				 				if (Integer.parseInt(thursday[0]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
				 					check = false;
				 					if (Integer.parseInt(thursday[1]) <= (calendar.get(Calendar.MINUTE))) 
				 						check = true;
				 				}
				 				if (check == true) {
					 				if (Integer.parseInt(thursday[2]) >= (calendar.get(Calendar.HOUR_OF_DAY))) {
					 					if (Integer.parseInt(thursday[2]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
					 						check = false;
					 						minutesLeft = Integer.parseInt(thursday[3]) - (calendar.get(Calendar.MINUTE));
					 						if (Integer.parseInt(thursday[3]) > (calendar.get(Calendar.MINUTE)))
					 							check = true;
					 					}
					 					else
					 						minutesLeft = 60*(Integer.parseInt(thursday[2])) - 60*(calendar.get(Calendar.HOUR_OF_DAY))
					 							- (calendar.get(Calendar.MINUTE));
					 					if (check == true) {
					 						if (minutesLeft >= 30) {
					 							if (worker.getTransportation() >= transportation) {
					 								if (worker.getPendingDeliveries() <= minPending) {
					 									if (worker.getPendingDeliveries() < minPending) {
					 										minPending = worker.getPendingDeliveries();
					 										maxRating = worker.getRating();
					 										bestWorker = worker;
					 									}
					 									else if (worker.getPendingDeliveries() == minPending) {
					 										if (worker.getRating() > maxRating) {
					 											maxRating = worker.getRating();
					 											bestWorker = worker;
					 										}
					 									}
					 								}
					 							}
					 						}
					 					}
					 				}
				 				}
				 			}
				 			break;
				 case (6) : String[] friday = schedule.getFriday().split("-");
				 			if (Integer.parseInt(friday[0]) <= (calendar.get(Calendar.HOUR_OF_DAY))) {
				 				boolean check = true;
				 				if (Integer.parseInt(friday[0]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
				 					check = false;
				 					if (Integer.parseInt(friday[1]) <= (calendar.get(Calendar.MINUTE))) 
				 						check = true;
				 				}
				 				if (check == true) {
					 				if (Integer.parseInt(friday[2]) >= (calendar.get(Calendar.HOUR_OF_DAY))) {
					 					if (Integer.parseInt(friday[2]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
					 						check = false;
					 						minutesLeft = Integer.parseInt(friday[3]) - (calendar.get(Calendar.MINUTE));
					 						if (Integer.parseInt(friday[3]) > (calendar.get(Calendar.MINUTE)))
					 							check = true;
					 					}
					 					else
					 						minutesLeft = 60*(Integer.parseInt(friday[2])) - 60*(calendar.get(Calendar.HOUR_OF_DAY))
					 							- (calendar.get(Calendar.MINUTE));
					 					if (check == true) {
					 						if (minutesLeft >= 30) {
					 							if (worker.getTransportation() >= transportation) {
					 								if (worker.getPendingDeliveries() <= minPending) {
					 									if (worker.getPendingDeliveries() < minPending) {
					 										minPending = worker.getPendingDeliveries();
					 										maxRating = worker.getRating();
					 										bestWorker = worker;
					 									}
					 									else if (worker.getPendingDeliveries() == minPending) {
					 										if (worker.getRating() > maxRating) {
					 											maxRating = worker.getRating();
					 											bestWorker = worker;
					 										}
					 									}
					 								}
					 							}
					 						}
					 					}
					 				}
				 				}
				 			}
				 			break;
				 case (7) : String[] saturday = schedule.getSaturday().split("-");
				 			if (Integer.parseInt(saturday[0]) <= (calendar.get(Calendar.HOUR_OF_DAY))) {
				 				boolean check = true;
				 				if (Integer.parseInt(saturday[0]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
				 					check = false;
				 					if (Integer.parseInt(saturday[1]) <= (calendar.get(Calendar.MINUTE))) 
				 						check = true;
				 				}
				 				if (check == true) {
					 				if (Integer.parseInt(saturday[2]) >= (calendar.get(Calendar.HOUR_OF_DAY))) {
					 					if (Integer.parseInt(saturday[2]) == (calendar.get(Calendar.HOUR_OF_DAY))) {
					 						check = false;
					 						minutesLeft = Integer.parseInt(saturday[3]) - (calendar.get(Calendar.MINUTE));
					 						if (Integer.parseInt(saturday[3]) > (calendar.get(Calendar.MINUTE)))
					 							check = true;
					 					}
					 					else
					 						minutesLeft = 60*(Integer.parseInt(saturday[2])) - 60*(calendar.get(Calendar.HOUR_OF_DAY))
					 							- (calendar.get(Calendar.MINUTE));
					 					if (check == true) {
					 						if (minutesLeft >= 30) {
					 							if (worker.getTransportation() >= transportation) {
					 								if (worker.getPendingDeliveries() <= minPending) {
					 									if (worker.getPendingDeliveries() < minPending) {
					 										minPending = worker.getPendingDeliveries();
					 										maxRating = worker.getRating();
					 										bestWorker = worker;
					 									}
					 									else if (worker.getPendingDeliveries() == minPending) {
					 										if (worker.getRating() > maxRating) {
					 											maxRating = worker.getRating();
					 											bestWorker = worker;
					 										}
					 									}
					 								}
					 							}
					 						}
					 					}
					 				}
				 				}
				 			}
				 			break;
			 }
		}
		if (bestWorker != null) {
			workerID = bestWorker.getID();
		}
		else
			workerID = 0;
		
	}
	
	/**
	 * Creates a delivery object when we already know all the parameters. Will be used when getting deliveries from
	 * the database.
	 * @param ID
	 * @param clientID
	 * @param workerID
	 * @param deliveryStatus
	 * @param paymentStatus
	 * @param rating
	 * @param workerComment
	 * @param clientComment
	 * @param timeCompleted
	 * @param duration
	 * @param price
	 * @param transportation
	 * @param sourceAddress
	 * @param destinationAddress
	 */
	public Delivery(int ID, int clientID, int workerID, String deliveryStatus, int rating,
			String workerComment, String clientComment, Timestamp timeCompleted, int duration,
			int price, int transportation, String sourceAddress, String destinationAddress) {
		this.ID = ID;
		this.clientID = clientID;
		this.workerID = workerID;
		this.deliveryStatus = deliveryStatus;
		this.rating = rating;
		this.workerComment = workerComment;
		this.clientComment = clientComment;
		this.timeCompleted = timeCompleted;
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

	public int getWorkerID() {
		return workerID;
	}

	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getWorkerComment() {
		return workerComment;
	}

	public void setWorkerComment(String workerComment) {
		this.workerComment = workerComment;
	}

	public String getClientComment() {
		return clientComment;
	}

	public void setClientComment(String clientComment) {
		this.clientComment = clientComment;
	}

	public Timestamp getTimeCompleted() {
		return timeCompleted;
	}

	public void setTimeCompleted(Timestamp timeCompleted) {
		this.timeCompleted = timeCompleted;
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
