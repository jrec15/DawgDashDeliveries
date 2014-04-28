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

public class DBHelper {

	protected PreparedStatement addClientStatement;//
	protected PreparedStatement addWorkerStatement;//
	protected PreparedStatement setScheduleStatement;//
	protected PreparedStatement addDeliveryStatement;//

	protected PreparedStatement listClientsStatement;//
	protected PreparedStatement listWorkersStatement;//
	protected PreparedStatement listSchedulesStatement;//
	protected PreparedStatement listDeliveriesStatement;//

	protected PreparedStatement changeDeliveryStatus;
	protected PreparedStatement changeWorkerComment;
	protected PreparedStatement changeTimeCompleted;
	protected PreparedStatement changeWorkerForDelivery;


	protected PreparedStatement changeRatingWorker;//
	protected PreparedStatement changeRatingDelivery;



	protected PreparedStatement changeWorkerPendingDeliveries;//

	protected PreparedStatement changeWorkerEmail;//
	protected PreparedStatement changeClientEmail;//
	protected PreparedStatement changeDefaultAddress;//
	protected PreparedStatement changeClientPassword;//
	protected PreparedStatement changeWorkerPassword;//
	protected PreparedStatement changeWorkerTransportation;//
	protected PreparedStatement removeClient;//
	protected PreparedStatement removeWorker;//
	protected PreparedStatement checkIfValidLoginWorker;//
	protected PreparedStatement checkIfUsernameExistsWorker;//
	protected PreparedStatement checkIfValidLoginClient;//
	protected PreparedStatement checkIfUsernameExistsClient;//
	protected PreparedStatement getWorkerRole;//

	protected PreparedStatement getSpecificDelivery;
	protected PreparedStatement getSpecificClient;//
	protected PreparedStatement getSpecificWorker;//
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
			addClientStatement = conn.prepareStatement("INSERT INTO Client (Name, `Default "
					+ "Address`, Email, Username, Password) VALUES (?, ?, ?, ?, ?)");
			addWorkerStatement = conn.prepareStatement("INSERT INTO Worker (Name, Email, Username, "
					+ "Password, Transportation, Rating, `Total Ratings`, `Total Deliveries`,"
					+ "`Pending Deliveries`, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			setScheduleStatement = conn.prepareStatement("INSERT INTO Schedule (`Worker ID`, Sunday, "
					+ "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			addDeliveryStatement = conn.prepareStatement("INSERT INTO Delivery (`Client ID`, `Worker ID`, `Delivery Status`,"
					+ "`Rating`, `Worker Comment`, `Client Comment`, `Time Completed`, `Duration`, `Price`, `Transportation`, "
					+ "`Source Address`, `Destination Address`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");







			listClientsStatement = conn.prepareStatement("select ID, Name, `Default Address`, Email, Username, Password from Client");
			listWorkersStatement = conn.prepareStatement("select ID, Name, Email, Username, Password, Transportation, Rating, "
					+ "`Total Ratings`, `Total Deliveries`, `Pending Deliveries`, Role from Worker");
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
			
			changeWorkerPendingDeliveries = conn.prepareStatement("UPDATE `Worker` Set `Pending Deliveries` = ? Where ID = ?");
			changeRatingWorker = conn.prepareStatement("UPDATE `Worker` SET `Rating` = ? WHERE `ID` = ?");

			changeClientEmail = conn.prepareStatement("UPDATE `Client` SET `Email` = ? WHERE `ID` = ?");
			changeWorkerEmail = conn.prepareStatement("UPDATE `Worker` SET `Email` = ? WHERE `ID` = ?");
			changeWorkerTransportation = conn.prepareStatement("UPDATE `Worker` SET `Transportation` = ? WHERE `ID` = ?");
			changeDefaultAddress = conn.prepareStatement("UPDATE `Client` SET `Default Address` = ? WHERE `ID` = ?");
			changeClientPassword = conn.prepareStatement("UPDATE `Client` SET `Password` = ? WHERE `ID` = ?");
			changeWorkerPassword = conn.prepareStatement("UPDATE `Worker` SET `Password` = ? WHERE `ID` = ?");
			removeClient = conn.prepareStatement("DELETE FROM `Client` WHERE `ID` = ?");
			removeWorker = conn.prepareStatement("DELETE FROM `Worker` WHERE `ID` = ?");		
			getSpecificDelivery = conn.prepareStatement("SELECT * FROM `Delivery` WHERE `ID` = ?");
			getSpecificClient = conn.prepareStatement("SELECT * FROM `Client` WHERE `ID` = ?");
			getSpecificWorker = conn.prepareStatement("SELECT * FROM `Worker` WHERE `ID` = ?");
			updateTotalRatings = conn.prepareStatement("UPDATE `Worker` SET `Total Ratings` = ? WHERE `ID` = ?");
			
			//Relates to login so username already passed in during field checks
			checkIfValidLoginWorker = conn.prepareStatement("SELECT `Password` FROM `Worker` WHERE `Username` = ?");
			checkIfUsernameExistsWorker = conn.prepareStatement("SELECT COUNT(`Password`) FROM `Worker` WHERE `Username` = ?");
			checkIfValidLoginClient = conn.prepareStatement("SELECT `Password` FROM `Client` WHERE `Username` = ?");
			checkIfUsernameExistsClient = conn.prepareStatement("SELECT COUNT(`Password`) FROM `Client` WHERE `Username` = ?");
			getWorkerRole = conn.prepareStatement("SELECT `Role` FROM `Worker` WHERE `Username` = ?");
		}
		catch (SQLException sqle) {
			System.out.println("Exception in Constructor:" + sqle.getMessage());
		}
	}

	//David Herald
	/** 
	 * Returns a list of all clients
	 */
	public ArrayList<Client> getClientList(){
		ArrayList<Client> list = new ArrayList<Client>();

		try{
			ResultSet rs = listClientsStatement.executeQuery();
			while(rs.next()){
				//package the current record as a Client object
				int id = rs.getInt("ID");
				String name = rs.getString("Name");
				String defaddress = rs.getString("Default Address");
				String email = rs.getString("Email");
				String username = rs.getString("Username");
				String password = rs.getString("Password");
				Client client = new Client(id, name, defaddress, email, username, password);
				//add the client to the list to return
				list.add(client);
			} //end of loop
		}catch(Exception e){
			System.out.println("Error populating Client list\n" + e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/**
	 * Returns a list of all workers
	 */
	public ArrayList<Worker> getWorkerList(){
		ArrayList<Worker> list = new ArrayList<Worker>();
		try{
			ResultSet rs = listWorkersStatement.executeQuery();
			while(rs.next()){
				//package the current record as a Worker object
				int id = rs.getInt("ID");
				String name = rs.getString("Name");
				String email = rs.getString("Email");
				String username = rs.getString("Username");
				String password = rs.getString("Password");
				int transportation = rs.getInt("Transportation");
				int rating = rs.getInt("Rating");
				int totalRating = rs.getInt("Total Ratings");
				int totalDeliveries = rs.getInt("Total Deliveries");
				int pendingDeliveries = rs.getInt("Pending Deliveries");
				String role = rs.getString("Role");
				Worker worker = new Worker(id, name, email, username, password, transportation, rating, totalRating, 
						totalDeliveries, pendingDeliveries, role);
				//add the worker to the list to return
				list.add(worker);
			} //end of loop
		}catch(Exception e){
			System.out.println("Error populating Worker list\n" + e.getClass().getName() + ": " + e.getMessage());
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
	 * Returns the client info for the client with the specified client ID
	 */
	public Client getClient(int clientID){
		ArrayList<Client> clientList = getClientList();
		for(int i = 0; i < clientList.size(); i++){
			if(clientList.get(i).getID() == clientID){
				return clientList.get(i);
			}
		}
		System.out.println("Client with id " + clientID + " not found.");
		return null;
	}

	/**
	 * Returns the worker info for the worker with the specified worker ID
	 */
	public Worker getWorker(int workerID){
		ArrayList<Worker> workerList = getWorkerList();
		for(int i = 0; i < workerList.size(); i++){
			if(workerList.get(i).getID() == workerID){
				return workerList.get(i);
			}
		}
		System.out.println("Worker with id " + workerID + " not found.");
		return null;
	}

	/**
	 * Returns a list of deliveries for a single client
	 */
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
	 */
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
		ArrayList<Delivery> list = new ArrayList<Delivery>();
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
	public void addClient(Client client) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		try {
			addClientStatement.setString(1, client.getName());
			addClientStatement.setString(2, client.getDefaultAddress());
			addClientStatement.setString(3, client.getEmail());
			addClientStatement.setString(4, client.getUsername());
			addClientStatement.setString(5, hashPassword(client.getPassword()));
			addClientStatement.executeUpdate();
		}
		catch (SQLException sqle){
			System.out.println("Exception in addClient:" + sqle.getMessage());
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


	public void addWorker(Worker worker) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		try {
			addWorkerStatement.setString(1, worker.getName());
			addWorkerStatement.setString(2, worker.getEmail());
			addWorkerStatement.setString(3,  worker.getUsername());
			addWorkerStatement.setString(4, hashPassword(worker.getPassword()));
			addWorkerStatement.setInt(5, worker.getTransportation());
			addWorkerStatement.setInt(6, worker.getRating());
			addWorkerStatement.setInt(7, worker.getTotalRatings());
			addWorkerStatement.setInt(8, worker.getTotalDeliveries());
			addWorkerStatement.setInt(9, worker.getPendingDeliveries());
			addWorkerStatement.setString(10, worker.getRole());
			addWorkerStatement.executeUpdate();
		}
		catch (SQLException sqle){
			System.out.println("Exception in addWorker:" + sqle.getMessage());
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
			
			
			Worker worker = getWorker(delivery.getWorkerID());
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

	public void setupClient() throws Exception{
		String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
		String DB_USER = "root";
		String DB_PASS = "";
		//String JDBC_URL = "jdbc:mysql://localhost:3306/TestDawg";
		//String DB_USER = "athena";
		//String DB_PASS = "wisdomgoddess";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			PreparedStatement clearTableStatement = conn.prepareStatement("delete from Client");
			clearTableStatement.execute();
			PreparedStatement resetAutoIncrementStatement = conn.prepareStatement("ALTER TABLE Client AUTO_INCREMENT = 1");
			resetAutoIncrementStatement.executeUpdate();
		}
		catch (SQLException sqle) {
			System.out.println("Exception in setupClient:" + sqle.getMessage());
		}
	}


	public void setupWorker() throws Exception{
		String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
		String DB_USER = "root";
		String DB_PASS = "";
		//String JDBC_URL = "jdbc:mysql://localhost:3306/TestDawg";
		//String DB_USER = "athena";
		//String DB_PASS = "wisdomgoddess";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			PreparedStatement clearTableStatement = conn.prepareStatement("delete from Worker");
			clearTableStatement.execute();
			PreparedStatement resetAutoIncrementStatement = conn.prepareStatement("ALTER TABLE Worker AUTO_INCREMENT = 1");
			resetAutoIncrementStatement.executeUpdate();
		}
		catch (SQLException sqle) {
			System.out.println("Exception in setupWorker:" + sqle.getMessage());
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
			getSpecificWorker.setInt(1, passedWorkerID);
			ResultSet rsWorker = getSpecificWorker.executeQuery();

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
				getSpecificWorker.setInt(1,  workerID);
				ResultSet rsWorker = getSpecificWorker.executeQuery();
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
	public void changeClientPassword(int passedClientID, String passedExistingPassword, String passedNewPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{

		try
		{
			String usernameClient;
			
			getSpecificClient.setInt(1, passedClientID);
			ResultSet rsClient = getSpecificClient.executeQuery();
				
			if(rsClient.next())
			{
				usernameClient = rsClient.getString("Username");
			}
			else
			{
				System.out.println("ERROR IN CHANGE CLEINT PASSWORD");
				return;
			}
			
			
			//Don't hash existing password as it is done in checkIfValidLoginClient
			if (checkIfValidLoginClient(usernameClient, passedExistingPassword ))
			{
				changeClientPassword.setString(1, hashPassword(passedNewPassword));
				changeClientPassword.setInt(2, passedClientID);
				changeClientPassword.executeUpdate();

			}
			else
			{
				System.out.println("Invalid Existing Password");
				return;
			}

		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE CLIENT_PASSWORD: " + sqle.getMessage());
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

	//TODO: If false what do you want to do
	public void changeWorkerPassword(int passedWorkerID, String passedExistingPassword, String passedNewPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{

		try
		{
			String usernameWorker;
			
			getSpecificWorker.setInt(1, passedWorkerID);
			ResultSet rsWorker = getSpecificWorker.executeQuery();
				
			if(rsWorker.next())
			{
				usernameWorker = rsWorker.getString("Username");
			}
			else
			{
				System.out.println("ERROR IN CHANGE WORKER PASSWORD");
				return;
			}
			
			//Don't hash existing password as it is done in checkIfValidLoginClient
			if(checkIfValidLoginWorker(usernameWorker, passedExistingPassword))
			{
				changeWorkerPassword.setString(1, hashPassword(passedNewPassword));
				changeWorkerPassword.setInt(2, passedWorkerID);
				changeWorkerPassword.executeUpdate();
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

	public String checkIfValidLogin(String passedUsername, String passedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException
	{
		try
		{

			
			if (checkIfValidLoginWorker(passedUsername, passedPassword))
			{
				getWorkerRole.setString(1, passedUsername);
				ResultSet rsRole = getWorkerRole.executeQuery();
				if(rsRole.next())
				{

					String temp = rsRole.getString("Role");
					System.out.println(temp);
					temp = temp.toLowerCase();
					return temp;
				}
				else
				{
					return "ERROR IN LOGIN: WORKER";
				}
			}
			else if (checkIfValidLoginClient(passedUsername, passedPassword))
			{
				return "customer";
			}
			else
			{
				return "Invalid Username/Password";
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

		return "ERROR IN CHECK LOGIN";
	}

	//Called from checkIfValidLogin
	public boolean checkIfValidLoginWorker(String passedUsername, String passedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		try
		{
			//First check if user exists
			String receivedUsername = passedUsername.trim();
			checkIfUsernameExistsWorker.setString(1, receivedUsername);
			ResultSet rsCount = checkIfUsernameExistsWorker.executeQuery();
			if(rsCount.next())
			{
				int count = rsCount.getInt("COUNT(`Password`)");
				if (count == 0)
				{
					System.out.println("BAD USERNAME");
					return false;
				}
			}


			checkIfValidLoginWorker.setString(1, receivedUsername);
			ResultSet rsPassword = checkIfValidLoginWorker.executeQuery();

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


	//Called from checkIfValidLogin
	public boolean checkIfValidLoginClient(String passedUsername, String passedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		try
		{
			//First check if user exists
			String receivedName = passedUsername.trim();
			checkIfUsernameExistsClient.setString(1, receivedName);
			ResultSet rsCount = checkIfUsernameExistsClient.executeQuery();
			if(rsCount.next())
			{
				int count = rsCount.getInt("COUNT(`Password`)");
				if (count == 0)
				{
					System.out.println("BAD NAME");
					return false;
				}
			}


			checkIfValidLoginClient.setString(1, receivedName);
			ResultSet rsPassword = checkIfValidLoginClient.executeQuery();
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
			System.out.println("ERROR IN CHECK_IF_VALID_LOGIN_CLIENT: " + sqle.getMessage());
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("ERROR IN HASH_PASSWORD INVAILD ALGORITHM:" + e.getMessage());
		}
		catch (UnsupportedEncodingException e)
		{
			System.out.println("ERROR IN HASH_PASSWORD INVAILD ENCODING:" + e.getMessage());
		}

		System.out.println("Username/Password Invalid:");
		return false;

	}

	
	public void changeClientEmail(int passedClientID, String passedEmail)
	{
		try
		{
			
			changeClientEmail.setString(1, passedEmail);
			changeClientEmail.setInt(2, passedClientID);
			changeClientEmail.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE CLIENT_EMAIL: " + sqle.getMessage());
		}
	}

	public void changeWorkerEmail(int passedClientID, String passedEmail)
	{
		try
		{
			changeWorkerEmail.setString(1, passedEmail);
			changeWorkerEmail.setInt(2, passedClientID);
			changeWorkerEmail.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN CHANGE WORKER_EMAIL: " + sqle.getMessage());
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
	public void removeClient(int passedClientID)
	{

		try
		{
			removeClient.setInt(1,passedClientID);
			removeClient.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN DELETE CLIENT: " + sqle.getMessage());
		}
	}

	//TODO: Might be a bad idea, as we would lose archive information, might want to do something with the entity
	public void removeWorker(int passedWorkerID)
	{

		try
		{
			removeWorker.setInt(1,passedWorkerID);
			removeWorker.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR IN DELETE WORKER: " + sqle.getMessage());
		}
	}

	
	//TODO: when delivery is done update pending deliveries-- worker, total deliveries++




}

