import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHelper {
	
	public DBHelper throws Exception {
		//Might need to change to match your info
		String JDBC_URL = "jdbc:mysql://localhost:3306/DawgDashDeliveries";
		String DB_USER = "root";
		String DB_PASS = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			//add prepared statements here
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
	
	public Estimate getEstimate(int clientID);
	
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
	public void addClient(parameters);
	
	public void addWorker(parameters);
	
	public void setSchedule(parameters);
	
	public void getEstimate(parameters);
	
	public void setup();
	
	public void setupClient();
	
	public void setupWorker();
	
	public void setupSchedule();
	
	public void setupEstimate();
	
	public void setupDelivery();
	//end justin
	//David Seivitch
	public void checkIfValidLogin(paramters); 
	
	public void changePassword(parameters);
	
	public void changeUsername(parameters);
	
	public void changeEmail(parameters);
	
	public void changeAddress(parameters);
	
	public void removeClient(clientID);
	
	public void removeWorker(workerID);
	
	public void hashPassword(String password);
	
	
	
}
