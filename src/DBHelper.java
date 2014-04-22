import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHelper {
	
	protected PreparedStatement addClientStatement;
	protected PreparedStatement addWorkerStatement;
	protected PreparedStatement setScheduleStatement;
	protected PreparedStatement giveEstimateStatement;
	
	
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
		}
		catch (SQLException sqle) {
			System.out.println("Exception in Constructor:" + sqle.getMessage());
	}
	}
	
	//David Herald
	public ArrayList<Client> getClientList();
	
	public ArrayList<Worker> getWorkerList();
	
	public ArrayList<Schedule> getScheduleList();
	
	public ArrayList<Estimate> getEstimateList();
	
	public ArrayList<Delivery> getDeliveryList();
	
	public ArrayList<Delivery> getDeliveryListClient(int clientID);
	
	public ArrayList<Delivery> getDeliveryListWorker(int workerID);
	
	public Client getClient(int clientID);
	
	public Worker getWorker(int workerID);
	
	public Schedule getSchedule(int workerID);
	
	public Estimate getEstimate(int estimateID);
	
	public Delivery getDelivery(int deliveryID);
	//
	
	//Ignore for now
	public void assignDelivery(Estimate estimate, rest of parameters) {
		for (through workers) {
			switch:
			asap {	
				if (currently scheduled) {
					if (not on a job) {
						add to result list;
					}
				}
			}
			future {
					
			}
		}
		for (through result list) {
			keep track of who has highest rating;
			store as a result;
		}
		assign to that worker;
	}
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
	public void checkIfValidLogin(paramaters); 
	
	public void changePassword(parameters);
	
	public void changeEmail(parameters);
	
	public void changeAddress(parameters);
	
	public void removeClient(clientID);
	
	public void removeWorker(workerID);
	
	public void hashPassword(String password);
	
	
	
}
