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

public class DBHelper {
	
	protected PreparedStatement addClientStatement;
	protected PreparedStatement addWorkerStatement;
	protected PreparedStatement setScheduleStatement;
	protected PreparedStatement giveEstimateStatement;
	
    protected PreparedStatement listClientsStatement;
	protected PreparedStatement listWorkersStatement;
	protected PreparedStatement listSchedulesStatement;
	protected PreparedStatement listDeliveriesStatement;
	protected PreparedStatement listEstimatesStatement;
    
    protected PreparedStatement changeWorkerEmail;
	protected PreparedStatement changeClientEmail;
	protected PreparedStatement changeDefaultAddress;
    protected PreparedStatement changeClientPassword;
    protected PreparedStatement changeWorkerPassword;
    protected PreparedStatement changeWorkerTransportation;
    protected PreparedStatement removeClient;
    protected PreparedStatement removeWorker;
    protected PreparedStatement checkIfValidLoginWorker;
    protected PreparedStatement checkIfUsernameExistsWorker;
    protected PreparedStatement checkIfValidLoginClient;
    protected PreparedStatement checkIfUsernameExistsClient;
    
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
			giveEstimateStatement = conn.prepareStatement("INSERT INTO Estimate (`Client ID`, Duration, "
					+ "Price, Transportation, `Source Address`, `Destination Address`) VALUES (?, ?, ?, ?, ?, ?)");
			
            listClientsStatement = conn.prepareStatement("select ID, Name, `Default Address`, Email, Username, Password from Client");
			listWorkersStatement = conn.prepareStatement("select ID, Name, Email, Username, Password, Transportation, Rating, "
					+ "`Total Ratings`, `Total Deliveries`, `Pending Deliveries`, Role from Worker");
			listSchedulesStatement = conn.prepareStatement("select `Worker ID`, Sunday, Monday, Tuesday, Wednesday, Thursday, "
					+ "Friday, Saturday from Schedule");
			listDeliveriesStatement = conn.prepareStatement("select ID, `Estimate ID`, `Worker ID`, `Delivery Status`, `Payment Status`, "
					+ "Rating, `Worker Comment`, `Client Comment`, `Time Completed` from Delivery");
			listEstimatesStatement = conn.prepareStatement("select `Client ID`, Duration, Price, Transportation, `Source Address`, `Destination Address` from Estimate");
            
            changeClientEmail = conn.prepareStatement("UPDATE `Client` SET `Email` = ? WHERE `Username` = ?");
            changeWorkerEmail = conn.prepareStatement("UPDATE `Worker` SET `Email` = ? WHERE `Username` = ?");
            changeWorkerTransportation = conn.prepareStatement("UPDATE `Worker` SET `Transportation` = ? WHERE `Username` = ?");
            changeDefaultAddress = conn.prepareStatement("UPDATE `Client` SET `Default Address` = ? WHERE `Username` = ?");
            changeClientPassword = conn.prepareStatement("UPDATE `Client` SET `Password` = ? WHERE `Username` = ?");
            changeWorkerPassword = conn.prepareStatement("UPDATE `Worker` SET `Password` = ? WHERE `Username` = ?");
            removeClient = conn.prepareStatement("DELETE FROM `Client` WHERE `Username` = ?");
            removeWorker = conn.prepareStatement("DELETE FROM `Worker` WHERE `Username` = ?");
            checkIfValidLoginWorker = conn.prepareStatement("SELECT `Password` FROM `Worker` WHERE `Username` = ?");
            checkIfUsernameExistsWorker = conn.prepareStatement("SELECT COUNT(`Password`) FROM `Worker` WHERE `Username` = ?");
            checkIfValidLoginClient = conn.prepareStatement("SELECT `Password` FROM `Client` WHERE `Username` = ?");
            checkIfUsernameExistsClient = conn.prepareStatement("SELECT COUNT(`Password`) FROM `Client` WHERE `Username` = ?");
            		
            
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
				int workerID = rs.getInt("WorkerID");
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
	
	/**
	 * Returns a list of all scheduled deliveries
	 */
	public ArrayList<Delivery> getDeliveryList(){
		ArrayList<Delivery> list = new ArrayList<Delivery>();
		try{
			ResultSet rs = listDeliveriesStatement.executeQuery();
			while(rs.next()){
				//package the current record as a Delivery object
				int id = rs.getInt("ID");
				int estimateID = rs.getInt("EstimateID");
				int workerID = rs.getInt("WorkerID");
				String deliveryStatus = rs.getString("DeliveryStatus");
				String paymentStatus = rs.getString("PaymentStatus");
				int rating = rs.getInt("Rating");
				String workerComment = rs.getString("WorkerComment");
				String clientComment = rs.getString("ClientComment");
				Timestamp timeCompleted = rs.getTimestamp("TimeCompleted");
				Delivery delivery = new Delivery(id, estimateID, workerID, deliveryStatus, paymentStatus, rating,
						workerComment, clientComment, timeCompleted);
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
			int estimateID = deliveryList.get(i).getEstimateID();
			int checkID = getEstimate(estimateID).getClientID();
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
			System.out.println("No deliveries found for worker with id " + workerID);
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
	 * Returns an estimate, which exists separately from a delivery
	 */
	public Estimate getEstimate(int estimateID){
		ArrayList<Estimate> list = new ArrayList<Estimate>();
		try{
			ResultSet rs = listEstimatesStatement.executeQuery();
			while(rs.next()){
				//first check for id
				int id = rs.getInt("ID");
				if(rs.getInt("ID") == estimateID){
					//if id matches, get the remaining info and package as an Estimate object 
					int clientID = rs.getInt("ClientID");
					int duration = rs.getInt("Duration");
					double price = rs.getDouble("Price");
					int transportation = rs.getInt("Transportation");
					String srcAddress = rs.getString("SourceAddress");
					String destAddress = rs.getString("DestinationAddress");
					Estimate estimate = new Estimate(id, clientID, duration, price, transportation, srcAddress, destAddress);
					//return the completed Estimate object
					return estimate;
				}
			} //end of loop
		}catch(Exception e){
			System.out.println("Error populating Estimate list or returning Estimate\n" + e.getClass().getName() + ": " + e.getMessage());
		}
		//if no estimate was found and no exception occured, then there was no estimate with the given id
		System.out.println("No Estimate found with ID " + estimateID + ".");
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
					int estimateID = rs.getInt("EstimateID");
					int workerID = rs.getInt("WorkerID");
					String deliveryStatus = rs.getString("DeliveryStatus");
					String paymentStatus = rs.getString("PaymentStatus");
					int rating = rs.getInt("Rating");
					String workerComment = rs.getString("WorkerComment");
					String clientComment = rs.getString("ClientComment");
					Timestamp timeCompleted = rs.getTimestamp("TimeCompleted");
					Delivery delivery = new Delivery(id, estimateID, workerID, deliveryStatus, paymentStatus, rating,
							workerComment, clientComment, timeCompleted);
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
		
		//Thinking complex stuff should actually go in estimate class?
		public void giveEstimate(Estimate estimate) {
			try {
				giveEstimateStatement.setInt(1, estimate.getClientID());
				giveEstimateStatement.setInt(2, estimate.getDuration());
				giveEstimateStatement.setDouble(3, estimate.getPrice());
				giveEstimateStatement.setInt(4, estimate.getTransportation());
				giveEstimateStatement.setString(5, estimate.getSourceAddress());
				giveEstimateStatement.setString(6, estimate.getDestinationAddress());
				giveEstimateStatement.executeUpdate();
			}
			catch (SQLException sqle){
				System.out.println("Exception in giveEstimate:" + sqle.getMessage());
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
		
		public void setupEstimate() throws Exception{
			String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
			String DB_USER = "root";
			String DB_PASS = "";
			//String JDBC_URL = "jdbc:mysql://localhost:3306/TestDawg";
			//String DB_USER = "athena";
			//String DB_PASS = "wisdomgoddess";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement clearTableStatement = conn.prepareStatement("delete from Estimate");
				clearTableStatement.execute();
				PreparedStatement resetAutoIncrementStatement = conn.prepareStatement("ALTER TABLE Estimate AUTO_INCREMENT = 1");
				resetAutoIncrementStatement.executeUpdate();
			}
			catch (SQLException sqle) {
				System.out.println("Exception in setupEstimate:" + sqle.getMessage());
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
	
    
    //David Seivitch
	
	//TODO: If false what do you want to do
	public void changeClientPassword(String passedUsername, String passedExistingPassword, String passedNewPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
       
        try
        {
            //Don't hash existing password as it is done in checkIfValidLoginClient
        	if (checkIfValidLoginClient(passedUsername, passedExistingPassword ))
            {
            	changeClientPassword.setString(1, hashPassword(passedNewPassword));
            	changeClientPassword.setString(2, passedUsername);
            	changeClientPassword.executeUpdate();
        
            }
            else
            {
            	System.out.println("Invalid Existing Password");
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
    public void changeWorkerPassword(String passedUsername, String passedExistingPassword, String passedNewPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
       
        try
        {
        	//Don't hash existing password as it is done in checkIfValidLoginClient
        	if(checkIfValidLoginWorker(passedUsername, passedExistingPassword))
            {
            	changeWorkerPassword.setString(1, hashPassword(passedNewPassword));
            	changeWorkerPassword.setString(2, passedUsername);
            	changeWorkerPassword.executeUpdate();
            }
            else
            {
            	System.out.println("Invalid Existing Password");
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
    
    public void changeClientEmail(String passedEmail, String passedUsername)
    {
        try
        {
        	changeClientEmail.setString(1, passedEmail);
            changeClientEmail.setString(2, passedUsername);
            changeClientEmail.executeUpdate();
        }
        catch (SQLException sqle)
        {
            System.out.println("ERROR IN CHANGE CLIENT_EMAIL: " + sqle.getMessage());
        }
    }
    
    public void changeWorkerEmail(String passedEmail, String passedUsername)
    {
        try
        {
        	changeWorkerEmail.setString(1, passedEmail);
            changeWorkerEmail.setString(2, passedUsername);
            changeWorkerEmail.executeUpdate();
        }
        catch (SQLException sqle)
        {
            System.out.println("ERROR IN CHANGE WORKER_EMAIL: " + sqle.getMessage());
        }
    }
    
    public void changeWorkerTransportation(int passedINT, String passedUsername)
    {
        try
        {
        	changeWorkerTransportation.setInt(1, passedINT);
            changeWorkerTransportation.setString(2, passedUsername);
            changeWorkerTransportation.executeUpdate();
        }
        catch (SQLException sqle)
        {
            System.out.println("ERROR IN CHANGE WORKER_TRANSPORTATION: " + sqle.getMessage());
        }
    }
    
    public void changeDefaultAddress(String passedAddress, String passedUsername)
    {
        
        try
        {
            changeDefaultAddress.setString(1, passedAddress);
            changeDefaultAddress.setString(2, passedUsername);
            changeDefaultAddress.executeUpdate();
        }
        catch (SQLException sqle)
        {
            System.out.println("ERROR IN CHANGE DEFAULT_ADDRESS: " + sqle.getMessage());
        }
    }
	
    //TODO: Might be a bad idea, as we would lose archive information, might want to do somthing with the entity
    public void removeClient(String passedUsername)
    {
        
        try
        {
            removeClient.setString(1,passedUsername);
            removeClient.executeUpdate();
        }
        catch (SQLException sqle)
        {
            System.out.println("ERROR IN DELETE CLIENT: " + sqle.getMessage());
        }
    }
	
    //TODO: Might be a bad idea, as we would lose archive information, might want to do somthing with the entity
    public void removeWorker(String passedUsername)
    {
        
        try
        {
        	removeWorker.setString(1,passedUsername);
            removeWorker.executeUpdate();
        }
        catch (SQLException sqle)
        {
            System.out.println("ERROR IN DELETE WORKER: " + sqle.getMessage());
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
	
    
	public boolean checkIfValidLoginWorker(String passedUsername, String passedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		try
		{
			//First check if user exists
			String receivedUsername = passedUsername.trim();
			checkIfUsernameExistsWorker.setString(1, receivedUsername);
			ResultSet rsCount = checkIfUsernameExistsWorker.executeQuery();
			while(rsCount.next())
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
			while(rsPassword.next())
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
	
	
	//Trim password and Username before setting into database
	public boolean checkIfValidLoginClient(String passedUsername, String passedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		try
		{
			//First check if user exists
			String receivedName = passedUsername.trim();
			checkIfUsernameExistsClient.setString(1, receivedName);
			ResultSet rsCount = checkIfUsernameExistsClient.executeQuery();
			while(rsCount.next())
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
			
			while(rsPassword.next())
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
    //TODO: update rating not here, just question
    
    
	
	
}

