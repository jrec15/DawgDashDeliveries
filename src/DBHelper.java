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
	
	public DBHelper() throws Exception {
		//Might need to change to match your info
		String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
		String DB_USER = "root";
		String DB_PASS = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			//add prepared statements here
			addClientStatement = conn.prepareStatement("INSERT INTO Client (Name, `Default "
					+ "Address`, Email, Username, Password) VALUES (?, ?, ?, ?, ?)");
			addWorkerStatement = conn.prepareStatement("INSERT INTO Worker (Name, Email,"
					+ "Password, Transportation, Rating, `Total Ratings`, `Total Deliveries`"
					+ "`Pending Deliveries`, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			setScheduleStatement = conn.prepareStatement("INSERT INTO Schedule (`Worker ID`, Sunday, "
					+ "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			giveEstimateStatement = conn.prepareStatement("INSERT INTO Estimate (`Client ID`, Duration, "
					+ "Price, Transportation, `Source Address`, `Destination Address`) VALUES (?, ?, ?, ?, ?, ?)");
			listClientsStatement = conn.prepareStatement("select ID, Name, DefaultAddress, Email, Username, Password from Client");
			listWorkersStatement = conn.prepareStatement("select ID, Name, Email, Username, Password, Transportation, Rating, "
					+ "TotalRatings, TotalDeliveries, PendingDeliveries, Role from Worker");
			listSchedulesStatement = conn.prepareStatement("select WorkerID, Sunday, Monday, Tuesday, Wednesday, Thursday, "
					+ "Friday, Saturday from Schedule");
			listDeliveriesStatement = conn.prepareStatement("select ID, EstimateID, WorkerID, DeliveryStatus, PaymentStatus, "
					+ "Rating, WorkerComment, ClientComment, TimeCompleted from Delivery");
			listEstimatesStatement = conn.prepareStatement("select ClientID, Duration, Price, Transportation, SourceAddress, DestinationAddress from Estimate");
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
				String defaddress = rs.getString("DefaultAddress");
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
				int totalRating = rs.getInt("TotalRatings");
				int totalDeliveries = rs.getInt("TotalDeliveries");
				int pendingDeliveries = rs.getInt("PendingDeliveries");
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
		public void addClient(Client client) {
			try {
				addClientStatement.setString(1, client.getName());
				addClientStatement.setString(2, client.getDefaultAddress());
				addClientStatement.setString(3, client.getEmail());
				addClientStatement.setString(4, client.getUsername());
				addClientStatement.setString(5, client.getPassword());
				addClientStatement.executeUpdate();
			}
			catch (SQLException sqle){
				System.out.println("Exception in addClient:" + sqle.getMessage());
			}
		}
		
		//Note** We forgot username here
		public void addWorker(Worker worker) {
			try {
				addWorkerStatement.setString(1, worker.getName());
				addWorkerStatement.setString(2, worker.getEmail());
				addWorkerStatement.setString(3, worker.getPassword());
				addWorkerStatement.setInt(4, worker.getTransportation());
				addWorkerStatement.setInt(5, worker.getRating());
				addWorkerStatement.setInt(6, worker.getTotalRatings());
				addWorkerStatement.setInt(7, worker.getTotalDeliveries());
				addWorkerStatement.setInt(8, worker.getPendingDeliveries());
				addWorkerStatement.setString(9, worker.getRole());
				addWorkerStatement.executeUpdate();
			}
			catch (SQLException sqle){
				System.out.println("Exception in addWorker:" + sqle.getMessage());
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
		
		public void setupClient() {
			String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
			String DB_USER = "root";
			String DB_PASS = "";
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
		
		
		public void setupWorker() {
			String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
			String DB_USER = "root";
			String DB_PASS = "";
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
		
		public void setupSchedule() {
			String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
			String DB_USER = "root";
			String DB_PASS = "";
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
		
		public void setupEstimate() {
			String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
			String DB_USER = "root";
			String DB_PASS = "";
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
		
		public void setupDelivery() {
			String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
			String DB_USER = "root";
			String DB_PASS = "";
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
	public boolean checkIfValidLogin(parameters); 
	
	public void changePassword(parameters);
	
	public void changeEmail(parameters);
	
	public void changeAddress(parameters);
	
	public void removeClient(clientID);
	
	public void removeWorker(workerID);
	
	public void hashPassword(String password);
	
	
	
}

