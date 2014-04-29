package dawgdash.dbaccess;
//package dawgdashdeliveries;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import dawgdash.entities.Delivery;
import dawgdash.entities.Schedule;
import dawgdash.entities.User;


public class DBHelper {

	protected PreparedStatement addUserStatement;//
	protected PreparedStatement setScheduleStatement;//
	protected PreparedStatement addDeliveryStatement;//

	protected PreparedStatement listUsersStatement;//
	protected PreparedStatement listSchedulesStatement;//
	protected PreparedStatement listDeliveriesStatement;//

	protected PreparedStatement changeDeliveryStatus;
	protected PreparedStatement changeWorkerComment;
	protected PreparedStatement changeTimeCompleted;
	protected PreparedStatement changeWorkerForDelivery;


	protected PreparedStatement changeRatingWorker;//
	protected PreparedStatement changeRatingDelivery;



	protected PreparedStatement changeWorkerPendingDeliveries;//

	protected PreparedStatement changeUserEmail;//
	protected PreparedStatement changeDefaultAddress;//
	protected PreparedStatement changeUserPassword;//
	protected PreparedStatement changeWorkerTransportation;//
	protected PreparedStatement removeUser;//
	protected PreparedStatement checkIfValidLogin;//
	protected PreparedStatement checkIfUsernameExists;//

	protected PreparedStatement getSpecificDelivery;
	protected PreparedStatement getSpecificUser;//
	protected PreparedStatement updateTotalRatings;//

	public DBHelper() throws Exception {
		//Might need to change to match your info

		String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
		String DB_USER = "root";
		String DB_PASS = "";
		//String JDBC_URL = "jdbc:mysql://localhost:3306/TestDawg";
		//String DB_USER = "athena";
		//String DB_PASS = "wisdomgoddess";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);


			//add prepared statements here
			addUserStatement = conn.prepareStatement("INSERT INTO User (Name, Email, Username, Password, "
					+ "`Client Address`, Transportation, Rating, `Total Ratings`,"
					+ "`Total Deliveries`, `Pending Deliveries`, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			setScheduleStatement = conn.prepareStatement("INSERT INTO Schedule (`Worker ID`, Sunday, "
					+ "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			addDeliveryStatement = conn.prepareStatement("INSERT INTO Delivery (`Client ID`, `Worker ID`, `Delivery Status`,"
					+ "`Rating`, `Worker Comment`, `Client Comment`, `Time Completed`, `Duration`, `Price`, `Transportation`, "
					+ "`Source Address`, `Destination Address`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");



			//TODO: ALL WORKER IDs/CLIENT IDs will be changed later


			
			listUsersStatement = conn.prepareStatement("select ID, Name, Email, Username, Password, `Client Address`, Transportation, Rating, "
					+ "`Total Ratings`, `Total Deliveries`, `Pending Deliveries`, Role from User");
			listSchedulesStatement = conn.prepareStatement("select `Worker ID`, Sunday, Monday, Tuesday, Wednesday, Thursday, "
					+ "Friday, Saturday from Schedule");
			listDeliveriesStatement = conn.prepareStatement("select ID, `Client ID`, `Worker ID`, `Delivery Status`, "
					+ "Rating, `Worker Comment`, `Client Comment`, `Time Completed`, Duration, Price, Transportation,"
					+ "`Source Address`, `Destination Address` from Delivery");


			changeDeliveryStatus = conn.prepareStatement("UPDATE `Delivery` SET `Delivery Status` = ? WHERE ID = ?");
			changeWorkerComment = conn.prepareStatement("UPDATE `Delivery` SET `Worker Comment` = ? WHERE ID = ?");
			changeTimeCompleted = conn.prepareStatement("UPDATE `Delivery` SET `Time Completed` = ? WHERE ID = ?");
			changeWorkerForDelivery = conn.prepareStatement("UPDATE `Delivery` Set `Worker ID` = ? WHERE ID = ?");
			changeRatingDelivery = conn.prepareStatement("UPDATE `Delivery` SET `Rating` = ? WHERE `ID` = ?");
			
			changeWorkerPendingDeliveries = conn.prepareStatement("UPDATE `User` Set `Pending Deliveries` = ? Where ID = ?");
			changeRatingWorker = conn.prepareStatement("UPDATE `User` SET `Rating` = ? WHERE `ID` = ?");
			
			//TODO LATER
			changeUserEmail = conn.prepareStatement("UPDATE `User` SET `Email` = ? WHERE `ID` = ?");
			changeWorkerTransportation = conn.prepareStatement("UPDATE `User` SET `Transportation` = ? WHERE `ID` = ?");
			changeDefaultAddress = conn.prepareStatement("UPDATE `User` SET `Client Address` = ? WHERE `ID` = ?");
			changeUserPassword = conn.prepareStatement("UPDATE `User` SET `Password` = ? WHERE `ID` = ?");
			removeUser = conn.prepareStatement("DELETE FROM `User` WHERE `ID` = ?");		
			getSpecificDelivery = conn.prepareStatement("SELECT * FROM `Delivery` WHERE `ID` = ?");
			getSpecificUser = conn.prepareStatement("SELECT * FROM `User` WHERE `ID` = ?");
			updateTotalRatings = conn.prepareStatement("UPDATE `User` SET `Total Ratings` = ? WHERE `ID` = ?");
			
			//Relates to login so username already passed in during field checks
			checkIfValidLogin = conn.prepareStatement("SELECT `Password` FROM `User` WHERE `Username` = ?");
			checkIfUsernameExists = conn.prepareStatement("SELECT COUNT(`Password`) FROM `User` WHERE `Username` = ?");
		}
		catch (SQLException sqle) {
			System.out.println("Exception in Constructor:" + sqle.getMessage());
		}
	}

	//David Herald

	/**
	 * Returns a list of all users
	 */
	public ArrayList<User> getUserList(){
		ArrayList<User> list = new ArrayList<User>();
		try{
			ResultSet rs = listUsersStatement.executeQuery();
			while(rs.next()){
				//package the current record as a User object
				int id = rs.getInt("ID");
				String name = rs.getString("Name");
				String email = rs.getString("Email");
				String username = rs.getString("Username");
				String password = rs.getString("Password");
				String clientAddress = rs.getString("Client Address");
				int transportation = rs.getInt("Transportation");
				int rating = rs.getInt("Rating");
				int totalRating = rs.getInt("Total Ratings");
				int totalDeliveries = rs.getInt("Total Deliveries");
				int pendingDeliveries = rs.getInt("Pending Deliveries");
				String role = rs.getString("Role");
				User user = new User(id, name, email, username, password, clientAddress, transportation, rating, totalRating, 
						totalDeliveries, pendingDeliveries, role);
				//add the user to the list to return
				list.add(user);
			} //end of loop
		}catch(Exception e){
			System.out.println("Error populating User list\n" + e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/**
	 * Returns a list of all schedules
	 */
	public ArrayList<Schedule> getScheduleList(){
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		try{
			ResultSet rs = listSchedulesStatement.executeQuery();
			while(rs.next()){
				//package the current record as a Schedule object
				int workerID = rs.getInt("Worker ID");
				String sunday = rs.getString("Sunday");
				String monday = rs.getString("Monday");
				String tuesday = rs.getString("Tuesday");
				String wednesday = rs.getString("Wednesday");
				String thursday = rs.getString("Thursday");
				String friday = rs.getString("Friday");
				String saturday = rs.getString("Saturday"); //Saturday, Saturday, Saaaturday! Saturday night's all right for fighting!
				Schedule schedule = new Schedule(workerID, sunday, monday, tuesday, wednesday, thursday, friday, saturday);
				//add the schedule to the list to return
				list.add(schedule);
			} //end of loop
		} catch(Exception e){
			System.out.println("Error populating Schedule list\n" + e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * Returns a list of all scheduled deliveries
	 */
	public ArrayList<Delivery> getDeliveryList(){
		ArrayList<Delivery> list = new ArrayList<Delivery>();
		try{
			ResultSet rs = listDeliveriesStatement.executeQuery();
			while(rs.next()){
				//package the current record as a Delivery object
				int id = rs.getInt("ID");
				int clientID = rs.getInt("Client ID");
				int workerID = rs.getInt("Worker ID");
				String deliveryStatus = rs.getString("Delivery Status");
				int rating = rs.getInt("Rating");
				String workerComment = rs.getString("Worker Comment");
				String clientComment = rs.getString("Client Comment");
				Timestamp timeCompleted = rs.getTimestamp("Time Completed");
				int duration = rs.getInt("Duration");
				int price = rs.getInt("Price");
				int transportation = rs.getInt("Transportation");
				String sourceAddress = rs.getString("Source Address");
				String destinationAddress = rs.getString("Destination Address");
				Delivery delivery = new Delivery(id, clientID, workerID, deliveryStatus, rating,
						workerComment, clientComment, timeCompleted, duration, price, transportation,
						sourceAddress, destinationAddress);
				//add the delivery to the list to be returned
				list.add(delivery);
			} //end of loop
		}catch(Exception e){
			System.out.println("Error populating Delivery list\n" + e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/**
	 * Returns the user info for the user with the specified user ID
	 */
	public User getUser(int userID){
		ArrayList<User> userList = getUserList();
		for(int i = 0; i < userList.size(); i++){
			if(userList.get(i).getID() == userID){
				return userList.get(i);
			}
		}
		System.out.println("User with id " + userID + " not found.");
		return null;
	}
	
	/** ADD OR DELETE LATER IF WE NEED OR DONT NEED
	 * Returns a list of deliveries for a single client
	 *
	public ArrayList<Delivery> getDeliveryListClient(int clientID){
		ArrayList<Delivery> deliveryList = getDeliveryList();
		ArrayList<Delivery> filteredList = new ArrayList<Delivery>();
		for(int i = 0; i < deliveryList.size(); i++){
			//get the client ID to check by getting the estimate ID first, which has a client ID attached
			int checkID = deliveryList.get(i).getClientID();
			if(checkID == clientID){
				filteredList.add(deliveryList.get(i));
			}
		}
		if(filteredList.size() > 0){
			return filteredList;
		}else{
			System.out.println("No deliveries found for client with id " + clientID);
			return null;
		}
	}
	/**
	 * Returns a list of deliveries for a single worker
	 *
	public ArrayList<Delivery> getDeliveryListWorker(int workerID){
		ArrayList<Delivery> deliveryList = getDeliveryList();
		ArrayList<Delivery> filteredList = new ArrayList<Delivery>();
		for(int i = 0; i < deliveryList.size(); i++){
			if(deliveryList.get(i).getWorkerID() == workerID){
				filteredList.add(deliveryList.get(i));
			}
		}
		if(filteredList.size() > 0){
			return filteredList;
		}else{
			System.out.println("No deliveries found for worker with idsdrs " + workerID);
			return null;
		}
	}
	**/
	
	/**
	 * Returns the schedule for the worker with the specified worker ID
	 */
	public Schedule getSchedule(int workerID){
		ArrayList<Schedule> scheduleList = getScheduleList();
		for(int i = 0; i < scheduleList.size(); i++){
			if(scheduleList.get(i).getWorkerID() == workerID){
				return scheduleList.get(i);
			}
		}
		System.out.println("No schedule found for worker with id " + workerID);
		return null;
	}


	/**
	 * Returns info about the delivery with the specified delivery ID
	 */
	public Delivery getDelivery(int deliveryID){
		try{
			ResultSet rs = listDeliveriesStatement.executeQuery();
			while(rs.next()){
				//first check for id
				int id = rs.getInt("ID");
				if(rs.getInt("ID") == deliveryID){
					//if id matches, get the remaining info and package as a Delivery object
					int clientID = rs.getInt("Client ID");
					int workerID = rs.getInt("Worker ID");
					String deliveryStatus = rs.getString("Delivery Status");
					int rating = rs.getInt("Rating");
					String workerComment = rs.getString("WorkerComment");
					String clientComment = rs.getString("ClientComment");
					Timestamp timeCompleted = rs.getTimestamp("TimeCompleted");
					int duration = rs.getInt("Duration");
					int price = rs.getInt("Price");
					int transportation = rs.getInt("Transportation");
					String sourceAddress = rs.getString("Source Address");
					String destinationAddress = rs.getString("Destination Address");
					Delivery delivery = new Delivery(id, clientID, workerID, deliveryStatus, rating,
							workerComment, clientComment, timeCompleted, duration, price, transportation,
							sourceAddress, destinationAddress);
					//return the completed Delivery object
					return delivery;
				}
			} //end of loop
		}catch(Exception e){
			System.out.println("Error populating Delivery list or returning Delivery\n" + e.getClass().getName() + ": " + e.getMessage());
		}
		//if no estimate was found and no exception occured, then there was no estimate with the given id
		System.out.println("No Delivery found with ID " + deliveryID + ".");
		return null;
	}
	//

	//justin

	public void addUser(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		try {
			addUserStatement.setString(1, user.getName());
			addUserStatement.setString(2, user.getEmail());
			addUserStatement.setString(3,  user.getUsername());
			addUserStatement.setString(4, hashPassword(user.getPassword()));
			addUserStatement.setString(5,  user.getClientAddress());
			addUserStatement.setInt(6, user.getTransportation());
			addUserStatement.setInt(7, user.getRating());
			addUserStatement.setInt(8, user.getTotalRatings());
			addUserStatement.setInt(9, user.getTotalDeliveries());
			addUserStatement.setInt(10, user.getPendingDeliveries());
			addUserStatement.setString(11, user.getRole());
			addUserStatement.executeUpdate();
		}
		catch (SQLException sqle){
			System.out.println("Exception in addUser:" + sqle.getMessage());
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("ERROR IN HASH_PASSWORD INVAILD ALGORITHM:" + e.getMessage());
		}
		catch (UnsupportedEncodingException e)
		{
			System.out.println("ERROR IN HASH_PASSWORD INVAILD ENCODING:" + e.getMessage());
		}
	}

	public void setSchedule(Schedule schedule) {
		try {
			setScheduleStatement.setInt(1, schedule.getWorkerID());
			setScheduleStatement.setString(2, schedule.getSunday());
			setScheduleStatement.setString(3, schedule.getMonday());
			setScheduleStatement.setString(4, schedule.getTuesday());
			setScheduleStatement.setString(5, schedule.getWednesday());
			setScheduleStatement.setString(6, schedule.getThursday());
			setScheduleStatement.setString(7, schedule.getFriday());
			setScheduleStatement.setString(8, schedule.getSaturday());
			setScheduleStatement.executeUpdate();
		}
		catch (SQLException sqle){
			System.out.println("Exception in setSchedule:" + sqle.getMessage());
		}
	}

	//TODO: UPDATE Pending Deliveries for related Worker
	public void addDelivery(Delivery delivery) {
		try {
			addDeliveryStatement.setInt(1, delivery.getClientID());
			addDeliveryStatement.setInt(2, delivery.getWorkerID());
			addDeliveryStatement.setString(3, delivery.getDeliveryStatus());
			addDeliveryStatement.setInt(4, delivery.getRating());
			addDeliveryStatement.setString(5, delivery.getWorkerComment());
			addDeliveryStatement.setString(6, delivery.getClientComment());
			addDeliveryStatement.setTimestamp(7, delivery.getTimeCompleted());
			addDeliveryStatement.setInt(8, delivery.getDuration());
			addDeliveryStatement.setDouble(9, delivery.getPrice());
			addDeliveryStatement.setInt(10, delivery.getTransportation());
			addDeliveryStatement.setString(11, delivery.getSourceAddress());
			addDeliveryStatement.setString(12, delivery.getDestinationAddress());
			addDeliveryStatement.executeUpdate();
			
			
			User worker = getUser(delivery.getWorkerID());
			int pending = worker.getPendingDeliveries();
			changeWorkerPendingDeliveries.setInt(1, pending + 1);
			changeWorkerPendingDeliveries.setInt(2, worker.getID());
			changeWorkerPendingDeliveries.executeUpdate();

			//TODO: since it is assigned we should change this to in-progress, issue is delivery is not keeping track of its own id 
			// change status of delivery to In-Progress
			changeDeliveryStatus(delivery.getID(), "In-Progress");
		}
		catch (SQLException sqle){
			System.out.println("Exception in addDelivery " + sqle.getMessage());
		}
	}

	public void setupUser() throws Exception{
		String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
		String DB_USER = "root";
		String DB_PASS = "";
		//String JDBC_URL = "jdbc:mysql://localhost:3306/TestDawg";
		//String DB_USER = "athena";
		//String DB_PASS = "wisdomgoddess";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			PreparedStatement clearTableStatement = conn.prepareStatement("delete from User");
			clearTableStatement.execute();
			PreparedStatement resetAutoIncrementStatement = conn.prepareStatement("ALTER TABLE User AUTO_INCREMENT = 1");
			resetAutoIncrementStatement.executeUpdate();
		}
		catch (SQLException sqle) {
			System.out.println("Exception in setupUser:" + sqle.getMessage());
		}
	}

	public void setupSchedule() throws Exception{
		String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
		String DB_USER = "root";
		String DB_PASS = "";
		//String JDBC_URL = "jdbc:mysql://localhost:3306/TestDawg";
		//String DB_USER = "athena";
		//String DB_PASS = "wisdomgoddess";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			PreparedStatement clearTableStatement = conn.prepareStatement("delete from Schedule");
			clearTableStatement.execute();
			PreparedStatement resetAutoIncrementStatement = conn.prepareStatement("ALTER TABLE Schedule AUTO_INCREMENT = 1");
			resetAutoIncrementStatement.executeUpdate();
		}
		catch (SQLException sqle) {
			System.out.println("Exception in setupSchedule:" + sqle.getMessage());
		}
	}

	public void setupDelivery() throws Exception{
		String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
		String DB_USER = "root";
		String DB_PASS = "";
		//String JDBC_URL = "jdbc:mysql://localhost:3306/TestDawg";
		//String DB_USER = "athena";
		//String DB_PASS = "wisdomgoddess";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			PreparedStatement clearTableStatement = conn.prepareStatement("delete from Delivery");
			clearTableStatement.execute();
			PreparedStatement resetAutoIncrementStatement = conn.prepareStatement("ALTER TABLE Delivery AUTO_INCREMENT = 1");
			resetAutoIncrementStatement.executeUpdate();
		}
		catch (SQLException sqle) {
			System.out.println("Exception in setupDelivery:" + sqle.getMessage());
		}
	}

	//end justin

	//Justin and David Seiavitch
	
	//TODO: add totaldeliveries to formula, to account for 1 rating out-weighing other proven workers 
	public void changeRatingWorker(int passedWorkerID, int passedRating)
	{
		try
		{
			ResultSet rsWorker = getSpecificUser.executeQuery();

			//int totalDeliveries = 0;
			int totalRatings = 0;
			int currentRating = 0;

			if(rsWorker.next())
			{
				//totalDeliveries = rsWorker.getInt("Total Deliveries");
				totalRatings = rsWorker.getInt("Total Ratings");
				currentRating = rsWorker.getInt("Rating");
			}
			else
			{
				System.out.println("ERROR NO SUCH WORKER FOR RATING CHANGE DB_CHANGE_RATING");
				return;
			}

			if (totalRatings == 0)
			{

				changeRatingWorker.setInt(1, passedRating);
				changeRatingWorker.setInt(2, passedWorkerID);
				changeRatingWorker.executeUpdate();

				updateTotalRatings.setInt(1, 1);
				updateTotalRatings.setInt(2, passedWorkerID);
				updateTotalRatings.executeUpdate();
				return;
			}
			else if (totalRatings > 0)
			{
				int newRating = ( ((totalRatings*currentRating) + passedRating) / (totalRatings+1) );

				changeRatingWorker.setInt(1, newRating);
				changeRatingWorker.setInt(2, passedWorkerID);
				changeRatingWorker.executeUpdate();

				updateTotalRatings.setInt(1, 2);
				updateTotalRatings.setInt(2, passedWorkerID);
				updateTotalRatings.executeUpdate();
				return;
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE RATING: " + sqle.getMessage());
		}
	}

	// TODO:CHECK SHOULD BE GOOD close delivery, set pendingworker--, set deliverystatus "completed", set timestamp, set workercomment
	//issue setrating has to be separate because client task not worker task
	public void completeDelivery(int passedDeliveryID, String passedWorkerComment) throws SQLException
	{
		try
		{
			changeDeliveryStatus(passedDeliveryID, "Completed");

			Date date = new Date();
			changeTimeCompleted(passedDeliveryID, new Timestamp(date.getTime()));
			if (!(passedWorkerComment.equals("")))
			{
				changeWorkerComment(passedDeliveryID, passedWorkerComment);
			}
			else
			{
				changeWorkerComment(passedDeliveryID, "Worker Reported No Problems");
			}

			getSpecificDelivery.setInt(1,  passedDeliveryID);
			ResultSet rsDelivery = getSpecificDelivery.executeQuery();

			if(rsDelivery.next())
			{
				int workerID = rsDelivery.getInt("Worker ID");
				int pending;
				getSpecificUser.setInt(1,  workerID);
				ResultSet rsWorker = getSpecificUser.executeQuery();
				if(rsWorker.next())
				{
					pending = rsDelivery.getInt("Pending Deliveries");
				}
				else
				{
					System.out.println("COULD NOT FIND WORKER WHEN CLOSING JOB: CHANGE_DELIVERY_STATUS");
					return;
				}
				changeWorkerPendingDeliveries.setInt(1, pending - 1);
				changeWorkerPendingDeliveries.setInt(2, workerID);
				changeWorkerPendingDeliveries.executeUpdate();
			}
			else
			{
				System.out.println("COULD NOT FIND WORKER ID WHEN CLOSING JOB: CHANGE_DELIVERY_STATUS");
				return;
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CLOSING JOB STATUS: " + sqle.getMessage());
		}
	}
	
	
	//TODO NEED TO CHECK THAT THIS IS WORKING CORRECT client sets rating, update worker rating
	//TODO MIGHT ADD IN THE FUTURE CLIENT COMMENTS ON SERVICE (ADVERTISING)
	public void setDeliveryRating(int passedDeliveryID, int passedDeliveryRating) throws SQLException
	{
		try
		{
			changeRatingDelivery.setInt(1, passedDeliveryRating);
			changeRatingDelivery.setInt(2, passedDeliveryID);
			changeRatingDelivery.executeQuery();
			
			
			getSpecificDelivery.setInt(1,  passedDeliveryID);
			ResultSet rsDelivery = getSpecificDelivery.executeQuery();

			if(rsDelivery.next())
			{
				int workerID = rsDelivery.getInt("Worker ID");
				changeRatingWorker(workerID, passedDeliveryRating);
		
			}
			else
			{
				System.out.println("COULD NOT FIND WORKER ID WHEN CHANGING DELIVERY RATING");
				return;
			}
			
			
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE DELIVERY RATING: " + sqle.getMessage());
		}
	}
	
	
	public void changeDeliveryStatus(int passedDeliveryID, String passedDeliveryStatus) throws SQLException
	{
		try
		{
			changeDeliveryStatus.setString(1, passedDeliveryStatus);
			changeDeliveryStatus.setInt(2,  passedDeliveryID);
			changeDeliveryStatus.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE DELIVERY STATUS: " + sqle.getMessage());
		}
	}
	
	public void changeWorkerComment(int passedDeliveryID, String passedWorkerComment) throws SQLException
	{
		try
		{
			changeWorkerComment.setString(1, passedWorkerComment);
			changeWorkerComment.setInt(2, passedDeliveryID);
			changeWorkerComment.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE WORKER COMMENT: " + sqle.getMessage());
		}
	}

	public void changeTimeCompleted(int passedDeliveryID, Timestamp timeCompleted) throws SQLException
	{
		try
		{
			changeTimeCompleted.setTimestamp(1, timeCompleted);
			changeTimeCompleted.setInt(2, passedDeliveryID);
			changeTimeCompleted.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE TIME COMPLETED: " + sqle.getMessage());
		}
	}
	//TODO THIS IS AN ADMIN TASK AS IT RIGHT NOW DOES NOT CALL TO REASSIGN DELIVERY(BEST PRACTICE ADMIN CONTROL)
	public void changeWorkerForDelivery(int passedDeliveryID, int passedWorkerID)
	{
		try
		{
			changeWorkerForDelivery.setInt(1, passedWorkerID);
			changeWorkerForDelivery.setInt(2, passedDeliveryID);
			changeWorkerForDelivery.executeUpdate();
			
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE WORKER FOR DELIVERY: " + sqle.getMessage());
		}
	}

	//David Seivitch

	//TODO: If false what do you want to do
	public void changeUserPassword(int passedUserID, String passedExistingPassword, String passedNewPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{

		try
		{
			String usernameUser;
			
			getSpecificUser.setInt(1, passedUserID);
			ResultSet rsUser = getSpecificUser.executeQuery();
				
			if(rsUser.next())
			{
				usernameUser = rsUser.getString("Username");
			}
			else
			{
				System.out.println("ERROR IN CHANGE WORKER PASSWORD");
				return;
			}
			
			//Don't hash existing password as it is done in checkIfValidLoginClient
			if(checkIfValidLogin(usernameUser, passedExistingPassword))
			{
				changeUserPassword.setString(1, hashPassword(passedNewPassword));
				changeUserPassword.setInt(2, passedUserID);
				changeUserPassword.executeUpdate();
			}
			else
			{
				System.out.println("Invalid Existing Password");
				return;
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE WORKER_PASSWORD: " + sqle.getMessage());
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("ERROR IN HASH_PASSWORD INVAILD ALGORITHM:" + e.getMessage());
		}
		catch (UnsupportedEncodingException e)
		{
			System.out.println("ERROR IN HASH_PASSWORD INVAILD ENCODING:" + e.getMessage());
		}

	}

	//Returns a hashed password in string format
	public String hashPassword(String passedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException{

		String hash = passedPassword;

		try
		{
			//Sets the algorithm for conversion
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Sets the digested hash into the byte array using UTF-8 coding to convert into byte type 
			byte byteData[] = md.digest(hash.getBytes("UTF-8"));

			//Future holder of hex type hashed password
			StringBuilder sb = new StringBuilder();

			// Appends each byte code read from byteArray to sb after byte is converted into hex type 
			// %02x describes the formating type used for conversion for hex
			for(int i = 0; i < byteData.length; i++)
			{
				sb.append(String.format("%02x", byteData[i]));
			}
			//Sets hash to string literal
			hash = sb.toString();

			//Used to set test cases
			//System.out.println("Passed in "+ passedPassword +" returned after hash "+ hash);
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("ERROR IN HASH_PASSWORD INVAILD ALGORITHM:" + e.getMessage());
		}
		catch (UnsupportedEncodingException e)
		{
			System.out.println("ERROR IN HASH_PASSWORD INVAILD ENCODING:" + e.getMessage());
		}
		return hash;

	}

	//Called from checkIfValidLogin
	public boolean checkIfValidLogin(String passedUsername, String passedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		try
		{
			//First check if user exists
			String receivedUsername = passedUsername.trim();
			checkIfUsernameExists.setString(1, receivedUsername);
			ResultSet rsCount = checkIfUsernameExists.executeQuery();
			if(rsCount.next())
			{
				int count = rsCount.getInt("COUNT(`Password`)");
				if (count == 0)
				{
					System.out.println("BAD USERNAME");
					return false;
				}
			}


			checkIfValidLogin.setString(1, receivedUsername);
			ResultSet rsPassword = checkIfValidLogin.executeQuery();

			String password = null;
			if(rsPassword.next())
			{
				password = rsPassword.getString("Password");
			}


			String receivedPassword = hashPassword(passedPassword.trim());
			if (password.equals(receivedPassword))
			{
				return true;
			}
			else
			{
				System.out.println("BAD PASSWORD");
				return false;
			}


		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHECK_IF_VALID_LOGIN_WORKER: " + sqle.getMessage());
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("ERROR IN HASH_PASSWORD INVAILD ALGORITHM:" + e.getMessage());
		}
		catch (UnsupportedEncodingException e)
		{
			System.out.println("ERROR IN HASH_PASSWORD INVAILD ENCODING:" + e.getMessage());
		}

		System.out.println("Username/Password Invaild:");
		return false;

	}


	public void changeUserEmail(int passedUserID, String passedEmail)
	{
		try
		{
			changeUserEmail.setString(1, passedEmail);
			changeUserEmail.setInt(2, passedUserID);
			changeUserEmail.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE USER_EMAIL: " + sqle.getMessage());
		}
	}

	public void changeWorkerTransportation(int passedWorkerID, int passedTransportation)
	{
		try
		{
			changeWorkerTransportation.setInt(1, passedTransportation);
			changeWorkerTransportation.setInt(2, passedWorkerID);
			changeWorkerTransportation.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE WORKER_TRANSPORTATION: " + sqle.getMessage());
		}
	}

	public void changeDefaultAddress(int passedClientID, String passedAddress)
	{

		try
		{
			changeDefaultAddress.setString(1, passedAddress);
			changeDefaultAddress.setInt(2, passedClientID);
			changeDefaultAddress.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE DEFAULT_ADDRESS: " + sqle.getMessage());
		}
	}


	//TODO: Might be a bad idea, as we would lose archive information, might want to do something with the entity
	public void removeUser(int passedUserID)
	{

		try
		{
			removeUser.setInt(1,passedUserID);
			removeUser.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN DELETE USER: " + sqle.getMessage());
		}
	}

	
	//TODO: when delivery is done update pending deliveries-- worker, total deliveries++




}

