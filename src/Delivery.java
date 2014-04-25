//package dawgdashdeliveries;

import java.sql.Timestamp;
import java.util.Calendar;
class Delivery {
	private int ID;
	private int estimateID;
	private int workerID;
	private String deliveryStatus;
	private String paymentStatus;
	private int rating;
	private String workerComment;
	private String clientComment;
	private Timestamp timeCompleted;
	
	/**
	 * NOTE: This is only set to work for ASAP right now. To do a pickup time we would need that as a variable,
	 * which we did not originally establish.
	 * @param estimateID
	 * @param clientComment Any comments the client has about the delivery (not sure if this
	 * is what we had planned for this or not)
	 * @throws Exception 
	 */
	public Delivery(int estimateID, String clientComment) throws Exception {
		ID = 0;
		this.estimateID = estimateID;
		deliveryStatus = "Pending"; //OR whatever we want here
		paymentStatus = "Paid"; //Not sure we need this.. do we assume they've paid once delivery is official?
		rating = 0; // I'm thinking 0 represents no rating received. 1-5 represent an actual rating given
		workerComment = "None";
		this.clientComment = clientComment;
		timeCompleted = null; //Not sure what to do with timeCompleted here
		
		int maxRating = -1;
		Worker bestWorker = null;
		DBHelper instance = new DBHelper();		
		for (Worker worker : instance.getWorkerList()) {
			 Schedule schedule = instance.getSchedule(worker.getID());
			 switch (Calendar.DAY_OF_WEEK) {
			 	 //ASSUMING we are putting schedule times in a "xx-xx" format as this seems best
			 	 //Leaving minutes OUT for now. Would take a good bit more code. Could add in 30 mins pretty easily though
				 case (1) : String[] sunday = schedule.getSunday().split("-");
				 			if (Integer.parseInt(sunday[0]) <= Calendar.HOUR_OF_DAY) {
				 				boolean check = true;
				 				if (Integer.parseInt(sunday[0]) == Calendar.HOUR_OF_DAY) {
				 					check = false;
				 					if (Integer.parseInt(sunday[1]) <= Calendar.MINUTE) 
				 						check = true;
					 				if (check == true) {
						 				if (Integer.parseInt(sunday[2]) >= Calendar.HOUR_OF_DAY) {
						 					//NOTE: remember to update schedule at the end since worker is now on a job. This is
						 					//very tricky ATM since I don't have minutes and we aren't calculating duration
						 					if (Integer.parseInt(sunday[2]) == Calendar.HOUR_OF_DAY) {
						 						check = false;
						 						//This would mean a worker with 1 min left in schedule could be assigned
						 						//a job. Let's stick to 15 or 30 min intervals to avoid this dilemma. 
						 						if (Integer.parseInt(sunday[3]) > Calendar.MINUTE)
						 							check = true;
						 						if (check == true) {
						 							if (worker.getRating() > maxRating) {
						 								bestWorker = worker;
						 							}
						 						}
						 					}
						 				}
					 				}
				 				}
				 			}
				 			break;
				 case (2) : String[] monday = schedule.getMonday().split("-");
							if (Integer.parseInt(monday[0]) <= Calendar.HOUR_OF_DAY) {
					 			if (Integer.parseInt(monday[1]) > Calendar.HOUR_OF_DAY) {
					 				//NOTE: remember to update schedule at the end since worker is now on a job. This is
					 				//very tricky ATM since I don't have minutes and we aren't calculating duration
					 				if (worker.getRating() > maxRating) {
											bestWorker = worker;
				 					}
				 				}
				 			}
				 			break;
				 case (3) : String[] tuesday = schedule.getTuesday().split("-");
							if (Integer.parseInt(tuesday[0]) <= Calendar.HOUR_OF_DAY) {
								if (Integer.parseInt(tuesday[1]) > Calendar.HOUR_OF_DAY) {
					 				//NOTE: remember to update schedule at the end since worker is now on a job. This is
					 				//very tricky ATM since I don't have minutes and we aren't calculating duration
					 				if (worker.getRating() > maxRating) {
											bestWorker = worker;
				 					}
				 				}
				 			}
		 					break;
				 case (4) : String[] wednesday = schedule.getWednesday().split("-");
							if (Integer.parseInt(wednesday[0]) <= Calendar.HOUR_OF_DAY) {
					 			if (Integer.parseInt(wednesday[1]) > Calendar.HOUR_OF_DAY) {
					 				//NOTE: remember to update schedule at the end since worker is now on a job. This is
									//very tricky ATM since I don't have minutes and we aren't calculating duration
				 					if (worker.getRating() > maxRating) {
				 							bestWorker = worker;
				 					}
				 				}
					 		}
		 					break;
				 case (5) : String[] thursday = schedule.getThursday().split("-");
						 	if (Integer.parseInt(thursday[0]) <= Calendar.HOUR_OF_DAY) {
					 			if (Integer.parseInt(thursday[1]) > Calendar.HOUR_OF_DAY) {
					 				//NOTE: remember to update schedule at the end since worker is now on a job. This is
					 				//very tricky ATM since I don't have minutes and we aren't calculating duration
					 				if (worker.getRating() > maxRating) {
											bestWorker = worker;
				 					}
				 				}
				 			}
		 					break;
				 case (6) : String[] friday = schedule.getFriday().split("-");
				 			if (Integer.parseInt(friday[0]) <= Calendar.HOUR_OF_DAY) {
					 			if (Integer.parseInt(friday[1]) > Calendar.HOUR_OF_DAY) {
					 				//NOTE: remember to update schedule at the end since worker is now on a job. This is
									//very tricky ATM since I don't have minutes and we aren't calculating duration
				 					if (worker.getRating() > maxRating) {
				 							bestWorker = worker;
				 					}
				 				}
					 		}
		 					break;
				 case (7) : String[] saturday = schedule.getSaturday().split("-");
				 			if (Integer.parseInt(saturday[0]) <= Calendar.HOUR_OF_DAY) {
				 				if (Integer.parseInt(saturday[1]) > Calendar.HOUR_OF_DAY) {
					 				//NOTE: remember to update schedule at the end since worker is now on a job. This is
					 				//very tricky ATM since I don't have minutes and we aren't calculating duration
					 				if (worker.getRating() > maxRating) {
												bestWorker = worker;
				 					}
				 				}
				 			}
		 					break;
			 }
		}
		if (bestWorker != null)
			workerID = bestWorker.getID();
		
	}
	
	/**
	 * Creates a delivery object when we already know all the parameters. Will be used when getting deliveries from
	 * the database.
	 * @param ID
	 * @param estimateID
	 * @param workerID
	 * @param deliveryStatus
	 * @param paymentStatus
	 * @param rating
	 * @param workerComment
	 * @param clientComment
	 * @param timeCompleted
	 */
	public Delivery(int ID, int estimateID, int workerID, String deliveryStatus, String paymentStatus, int rating,
			String workerComment, String clientComment, Timestamp timeCompleted) {
		this.ID = ID;
		this.estimateID = estimateID;
		this.workerID = workerID;
		this.deliveryStatus = deliveryStatus;
		this.paymentStatus = paymentStatus;
		this.rating = rating;
		this.workerComment = workerComment;
		this.clientComment = clientComment;
		this.timeCompleted = timeCompleted;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getEstimateID() {
		return estimateID;
	}

	public void setEstimateID(int estimateID) {
		this.estimateID = estimateID;
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

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
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
}
