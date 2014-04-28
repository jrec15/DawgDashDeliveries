//package dawgdashdeliveries;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;



public class DBHelperTest extends DBHelper {
	

	@Before
	public void setUp() throws Exception {
		super.setupClient();
		super.setupWorker();
		super.setupSchedule();
		super.setupDelivery();
<<<<<<< HEAD
		
=======
>>>>>>> fe73e35f4c49386692edc060ab4bf933b3bd79f3
	}
	
	@Test
	public void test() {
	//	fail("Not yet implemented");
	}
	
	public DBHelperTest() throws Exception{
		DBHelper helper = new DBHelper();
    	assertNotNull("prepared statement should exist", helper.addClientStatement);
    	assertNotNull("prepared statement should exist", helper.addWorkerStatement);
    	assertNotNull("prepared statement should exist", helper.setScheduleStatement);
    	assertNotNull("prepared statement should exist", helper.addDeliveryStatement);
<<<<<<< HEAD
    	
=======
>>>>>>> fe73e35f4c49386692edc060ab4bf933b3bd79f3
    	
    	assertNotNull("prepared statement should exist", helper.listClientsStatement);
    	assertNotNull("prepared statement should exist", helper.listWorkersStatement);
    	assertNotNull("prepared statement should exist", helper.listSchedulesStatement);
    	assertNotNull("prepared statement should exist", helper.listDeliveriesStatement);
<<<<<<< HEAD
    	
    	
    	assertNotNull("prepared statement should exist", helper.changeDeliveryStatus); 
    	
    	assertNotNull("prepared statement should exist", helper.changeWorkerComment); 
    	assertNotNull("prepared statement should exist", helper.changeTimeCompleted); 
    	assertNotNull("prepared statement should exist", helper.changeWorkerForDelivery); 

    	assertNotNull("prepared statement should exist", helper.changeRatingWorker);
    	assertNotNull("prepared statement should exist", helper.changeRatingDelivery);
=======
>>>>>>> fe73e35f4c49386692edc060ab4bf933b3bd79f3
    	
    	assertNotNull("prepared statement should exist", helper.changeWorkerEmail);
    	assertNotNull("prepared statement should exist", helper.changeClientEmail);
    	assertNotNull("prepared statement should exist", helper.changeDefaultAddress);
    	assertNotNull("prepared statement should exist", helper.changeClientPassword);
    	assertNotNull("prepared statement should exist", helper.changeWorkerPassword);
    	assertNotNull("prepared statement should exist", helper.changeWorkerTransportation);
    	assertNotNull("prepared statement should exist", helper.removeClient);
    	assertNotNull("prepared statement should exist", helper.removeWorker);
    	assertNotNull("prepared statement should exist", helper.checkIfValidLoginWorker);
    	assertNotNull("prepared statement should exist", helper.checkIfUsernameExistsWorker);
    	assertNotNull("prepared statement should exist", helper.checkIfValidLoginClient);
    	assertNotNull("prepared statement should exist", helper.checkIfUsernameExistsClient);
    	assertNotNull("prepared statement should exist", helper.getWorkerRole);
	
	}
	
	@Test
	public void testAddClient() throws Exception{
		DBHelper instance = new DBHelper();
		
		Client client1 = new Client("David", "1180 White Oak Drive, Athens, GA 30606", "dseiav1@uga.edu",
				"dss", "password");
		Client client2 = new Client("Carrie", "1180 White Oak Drive, Athens, GA 30606", "wee12004@gmail.com",
				"wee1", "nope");
		Client client3 = new Client("Graphic Dude", "110 Ben Burton Parkway, Statham, GA 30666",
				"newWorld@graphics.com", "newWorld", "ahhhh");
		Client client4 = new Client("David S", "194 Highland Park Drive, Athens, GA 30605", "dseiav1@charter.net",
				"dude", "yep");
		
		instance.addClient(client1);
		instance.addClient(client2);
		instance.addClient(client3);
		instance.addClient(client4);
		
		ArrayList<Client> clientTestList= instance.getClientList();
     	assertEquals("Clients in list",4, clientTestList.size());
     	
     	assertEquals("Client 1 name","David",clientTestList.get(0).getName());
     	assertEquals("Client 1 address","1180 White Oak Drive, Athens, GA 30606",clientTestList.get(0).getDefaultAddress());
     	assertEquals("Client 1 email","dseiav1@uga.edu",clientTestList.get(0).getEmail());
     	assertEquals("Client 1 username","dss",clientTestList.get(0).getUsername());
     	assertEquals("Client 1 password","5f4dcc3b5aa765d61d8327deb882cf99",clientTestList.get(0).getPassword());
     	
     	assertEquals("Client 2 name","Carrie",clientTestList.get(1).getName());
     	assertEquals("Client 2 address","1180 White Oak Drive, Athens, GA 30606",clientTestList.get(1).getDefaultAddress());
     	assertEquals("Client 2 email","wee12004@gmail.com",clientTestList.get(1).getEmail());
     	assertEquals("Client 2 username","wee1",clientTestList.get(1).getUsername());
     	assertEquals("Client 2 password","4101bef8794fed986e95dfb54850c68b",clientTestList.get(1).getPassword());
     	
     	assertEquals("Client 3 name","Graphic Dude",clientTestList.get(2).getName());
     	assertEquals("Client 3 address","110 Ben Burton Parkway, Statham, GA 30666",clientTestList.get(2).getDefaultAddress());
     	assertEquals("Client 3 email","newWorld@graphics.com",clientTestList.get(2).getEmail());
     	assertEquals("Client 3 username","newWorld",clientTestList.get(2).getUsername());
     	assertEquals("Client 3 password","82dfb76426e6e317209202b31056b07a",clientTestList.get(2).getPassword());
     	
     	assertEquals("Client 4 name","David S",clientTestList.get(3).getName());
     	assertEquals("Client 4 address","194 Highland Park Drive, Athens, GA 30605",clientTestList.get(3).getDefaultAddress());
     	assertEquals("Client 4 email","dseiav1@charter.net",clientTestList.get(3).getEmail());
     	assertEquals("Client 4 username","dude",clientTestList.get(3).getUsername());
     	assertEquals("Client 4 password","9348ae7851cf3ba798d9564ef308ec25",clientTestList.get(3).getPassword());
     	
     	//Checking if changes in information are correct
     	
     	instance.changeClientPassword("dss", "password", "hellokittY" );
     	instance.changeClientEmail("thisIsNew@gmail.com", "newWorld");
     	
     	
     	clientTestList= instance.getClientList();
     	assertEquals("Clients in list",4, clientTestList.size());
     	
     	assertEquals("Client 3 email","thisIsNew@gmail.com",clientTestList.get(2).getEmail());
     	assertEquals("Client 1 password","eab785fc596b7aa2d24d5a6a438b74ac",clientTestList.get(0).getPassword());
     	
  
     	
     	
	
	}
	
	
	@Test
	public void testAddWorker() throws Exception{
		DBHelper instance = new DBHelper();
		
		Worker worker1 = new Worker("Henry", "hen@yahoo.com", "henWinner", "whatI", 1, "worker");
		Worker worker2 = new Worker("Carl", "carl@dawgdashdeliveries.com", "kingEND", "passcode", 3, "admin");
		Worker worker3 = new Worker("Gator", "inDA@swamp.net", "tenFoot", "tail", 3, "worker");
		Worker worker4 = new Worker("Tom", "tom@gmail.com", "killJerry", "never", 2, "worker");
		
		instance.addWorker(worker1);
		instance.addWorker(worker2);
		instance.addWorker(worker3);
		instance.addWorker(worker4);
		
		ArrayList<Worker> workerTestList= instance.getWorkerList();
     	assertEquals("Worekrs in list",4, workerTestList.size());
     	
     	assertEquals("Worker 1 name","Henry",workerTestList.get(0).getName());
     	assertEquals("Worker 1 email","hen@yahoo.com",workerTestList.get(0).getEmail());
     	assertEquals("Worker 1 username","henWinner", workerTestList.get(0).getUsername());
     	assertEquals("Worker 1 password","64fa8bc9aa638036e9a09bb1bd4da2ad",workerTestList.get(0).getPassword());
     	assertEquals("Worker 1 transportation", 1, workerTestList.get(0).getTransportation());
     	assertEquals("Worker 1 Total Ratings", 0, workerTestList.get(0).getTotalRatings());
     	assertEquals("Worker 1 Total Deliveries", 0, workerTestList.get(0).getTotalDeliveries());
     	assertEquals("Worker 1 Pending Deliveries", 0, workerTestList.get(0).getPendingDeliveries());
     	assertEquals("Worker 1 role","worker",workerTestList.get(0).getRole());
     	
     	assertEquals("Worker 2 name","Carl",workerTestList.get(1).getName());
     	assertEquals("Worker 2 email","carl@dawgdashdeliveries.com",workerTestList.get(1).getEmail());
     	assertEquals("Worker 2 username","kingEND", workerTestList.get(1).getUsername());
     	assertEquals("Worker 2 password","15472cd29f632e34f039403f2e635f66",workerTestList.get(1).getPassword());
     	assertEquals("Worker 2 transportation", 3, workerTestList.get(1).getTransportation());
     	assertEquals("Worker 2 Total Ratings", 0, workerTestList.get(1).getTotalRatings());
     	assertEquals("Worker 2 Total Deliveries", 0, workerTestList.get(1).getTotalDeliveries());
     	assertEquals("Worker 2 Pending Deliveries", 0, workerTestList.get(1).getPendingDeliveries());
     	assertEquals("Worker 2 role","admin",workerTestList.get(1).getRole());
     	
     	assertEquals("Worker 3 name","Gator",workerTestList.get(2).getName());
     	assertEquals("Worker 3 email","inDA@swamp.net",workerTestList.get(2).getEmail());
     	assertEquals("Worker 3 username","tenFoot", workerTestList.get(2).getUsername());
     	assertEquals("Worker 3 password","7aea2552dfe7eb84b9443b6fc9ba6e01",workerTestList.get(2).getPassword());
     	assertEquals("Worker 3 transportation", 3, workerTestList.get(2).getTransportation());
     	assertEquals("Worker 3 Total Ratings", 0, workerTestList.get(2).getTotalRatings());
     	assertEquals("Worker 3 Total Deliveries", 0, workerTestList.get(2).getTotalDeliveries());
     	assertEquals("Worker 3 Pending Deliveries", 0, workerTestList.get(2).getPendingDeliveries());
     	assertEquals("Worker 3 role","worker",workerTestList.get(2).getRole());
     	
     	assertEquals("Worker 4 name","Tom",workerTestList.get(3).getName());
     	assertEquals("Worker 4 email","tom@gmail.com",workerTestList.get(3).getEmail());
     	assertEquals("Worker 4 username","killJerry", workerTestList.get(3).getUsername());
     	assertEquals("Worker 4 password","c7561db7a418dd39b2201dfe110ab4a4",workerTestList.get(3).getPassword());
     	assertEquals("Worker 4 transportation", 2, workerTestList.get(3).getTransportation());
     	assertEquals("Worker 4 Total Ratings", 0, workerTestList.get(3).getTotalRatings());
     	assertEquals("Worker 4 Total Deliveries", 0, workerTestList.get(3).getTotalDeliveries());
     	assertEquals("Worker 4 Pending Deliveries", 0, workerTestList.get(3).getPendingDeliveries());
     	assertEquals("Worker 4 role","worker",workerTestList.get(3).getRole());
     	
     	
     	
     	//Checking when we change worker information 
     	instance.changeWorkerPassword("killJerry", "never", "helloNewPassword");
     	instance.changeWorkerEmail("golden@gmail.com", "tenFoot");
     	instance.changeWorkerTransportation(1, "tenFoot");
     	
     	//changeRating with zero ratings so far
     	instance.changeRatingWorker(5, "tenFoot");
     	
     	//changeRating with zero, the add another rating
     	instance.changeRatingWorker(3, "killJerry");
     	instance.changeRatingWorker(5, "killJerry");
     	
     	workerTestList= instance.getWorkerList();
     	assertEquals("Worekrs in list",4, workerTestList.size());
     	
	    
	    assertEquals("Worker 3 email","golden@gmail.com",workerTestList.get(2).getEmail());
	    assertEquals("Worker 4 password","3ed658947f0715d56ed555b172ac0b3b",workerTestList.get(3).getPassword());
     	assertEquals("Worker 3 transportation", 1, workerTestList.get(2).getTransportation());
     	
     	assertEquals("Worker 3 rating is", 5, workerTestList.get(2).getRating());
     	assertEquals("Worker 3 total ratings", 1, workerTestList.get(2).getTotalRatings());
     	
     	assertEquals("Worker 4 rating is", 4, workerTestList.get(3).getRating());
     	assertEquals("Worker 4 total ratings", 2, workerTestList.get(3).getTotalRatings());
     	
     	
     	
     	
	}
	
	@Test
	public void testCheckIfValidLoginWorker() throws Exception{
		DBHelper instance = new DBHelper();
		
		Worker worker1 = new Worker("Henry", "hen@yahoo.com", "henWinner", "whatI", 1, "worker");
		Worker worker2 = new Worker("Carl", "carl@dawgdashdeliveries.com", "kingEND", "passcode", 3, "admin");
		Worker worker3 = new Worker("Gator", "inDA@swamp.net", "tenFoot", "tail", 3, "worker");
		Worker worker4 = new Worker("Tom", "tom@gmail.com", "killJerry", "never", 2, "worker");
		
		instance.addWorker(worker1);
		instance.addWorker(worker2);
		instance.addWorker(worker3);
		instance.addWorker(worker4);
		
		ArrayList<Worker> workerTestList= instance.getWorkerList();
     	assertEquals("Worekrs in list",4, workerTestList.size());
     	
     	assertTrue("Test to see if password login is true without extra space: ", instance.checkIfValidLoginWorker("henWinner", "whatI"));
     	assertTrue("Test to see if password login is true with extra space on password: ", instance.checkIfValidLoginWorker("henWinner", "whatI "));
     	assertTrue("Test to see if password login is true with extra space on username: ", instance.checkIfValidLoginWorker("henWinner ", "whatI"));
     	
     	assertFalse("Test to see if no password passed during login is false", instance.checkIfValidLoginWorker("henWinner", ""));
     	assertFalse("Invalid Username passed", instance.checkIfValidLoginWorker("heWinner", "whatI"));
     	assertFalse("Invalid password passed during login", instance.checkIfValidLoginWorker("henWinner", "what"));
     	assertFalse("Invalid case change password passed during login", instance.checkIfValidLoginWorker("henWinner", "whati"));
     	
	}
	
	@Test
	public void testCheckIfValidLoginClient() throws Exception{
		DBHelper instance = new DBHelper();
	
		Client client1 = new Client("David", "1180 White Oak Drive, Athens, GA 30606", "dseiav1@uga.edu",
				"dss", "password");
		Client client2 = new Client("Carrie", "1180 White Oak Drive, Athens, GA 30606", "wee12004@gmail.com",
				"wee1", "nope");
		Client client3 = new Client("Graphic Dude", "110 Ben Burton Parkway, Statham, GA 30666",
				"newWorld@graphics.com", "newWorld", "ahhhh");
		Client client4 = new Client("David S", "194 Highland Park Drive, Athens, GA 30605", "dseiav1@charter.net",
				"dude", "yep");
		
		instance.addClient(client1);
		instance.addClient(client2);
		instance.addClient(client3);
		instance.addClient(client4);
		
		ArrayList<Client> clientTestList= instance.getClientList();
     	assertEquals("Clients in list",4, clientTestList.size());
     	
     	assertTrue("Test to see if password login is true without extra space: ", instance.checkIfValidLoginClient("newWorld", "ahhhh"));
     	assertTrue("Test to see if password login is true with extra space on password: ", instance.checkIfValidLoginClient("newWorld", "ahhhh "));
     	assertTrue("Test to see if password login is true with extra space on username: ", instance.checkIfValidLoginClient("newWorld ", "ahhhh"));
     	
     	assertFalse("Test to see if no password passed during login is false", instance.checkIfValidLoginClient("newWorld", ""));
     	assertFalse("Invalid Username passed", instance.checkIfValidLoginClient("neWorld", "ahhhh"));
     	assertFalse("Invalid password passed during login", instance.checkIfValidLoginClient("newWorld", "ahh"));
     	assertFalse("Invalid case change password passed during login", instance.checkIfValidLoginClient("newWorld", "ahhhH"));
	}

	
	@Test
	public void testCheckIfValidLogin() throws Exception{
		DBHelper instance = new DBHelper();
		
		Worker worker1 = new Worker("Henry", "hen@yahoo.com", "henWinner", "whatI", 1, "worker");
		Worker worker2 = new Worker("Carl", "carl@dawgdashdeliveries.com", "kingEND", "passcode", 3, "admin");
		Worker worker3 = new Worker("Gator", "inDA@swamp.net", "tenFoot", "tail", 3, "worker");
		Worker worker4 = new Worker("Tom", "tom@gmail.com", "killJerry", "never", 2, "worker");
		
		instance.addWorker(worker1);
		instance.addWorker(worker2);
		instance.addWorker(worker3);
		instance.addWorker(worker4);
		
		Client client1 = new Client("David", "1180 White Oak Drive, Athens, GA 30606", "dseiav1@uga.edu",
				"dss", "password");
		Client client2 = new Client("Carrie", "1180 White Oak Drive, Athens, GA 30606", "wee12004@gmail.com",
				"wee1", "nope");
		Client client3 = new Client("Graphic Dude", "110 Ben Burton Parkway, Statham, GA 30666",
				"newWorld@graphics.com", "newWorld", "ahhhh");
		Client client4 = new Client("David S", "194 Highland Park Drive, Athens, GA 30605", "dseiav1@charter.net",
				"dude", "yep");
		
		instance.addClient(client1);
		instance.addClient(client2);
		instance.addClient(client3);
		instance.addClient(client4);
		
		ArrayList<Worker> workerTestList= instance.getWorkerList();
     	assertEquals("Worekrs in list",4, workerTestList.size());
     	
     	assertEquals("Test to see if password login is true without extra space: return role", "worker",  instance.checkIfValidLogin("henWinner", "whatI"));
     	assertEquals("Test to see if password login is true with extra space on password: return role", "worker", instance.checkIfValidLogin("henWinner", "whatI "));
     	assertEquals("Test to see if password login is true with extra space on username: return role", "worker", instance.checkIfValidLogin("henWinner ", "whatI"));
     	assertEquals("Test to see if password login is true without extra space on username: return role", "admin", instance.checkIfValidLogin("kingEnd ", "passcode"));
     	
     	assertEquals("Test to see if no password passed during login: is rejected", "Invalid Username/Password", instance.checkIfValidLogin("henWinner", ""));
     	assertEquals("Invalid Username passed during login: is rejected", "Invalid Username/Password", instance.checkIfValidLogin("heWinner", "whatI"));
     	assertEquals("Invalid password passed during login: is rejected", "Invalid Username/Password", instance.checkIfValidLogin("henWinner", "what"));
     	assertEquals("Invalid case change password passed during login: is rejected", "Invalid Username/Password", instance.checkIfValidLogin("henWinner", "whati"));
     	
     	ArrayList<Client> clientTestList= instance.getClientList();
     	assertEquals("Clients in list",4, clientTestList.size());
     	
     	assertEquals("Test to see if password login is true without extra space: ", "customer", instance.checkIfValidLogin("newWorld", "ahhhh"));
     	assertEquals("Test to see if password login is true with extra space on password: ", "customer", instance.checkIfValidLogin("newWorld", "ahhhh "));
     	assertEquals("Test to see if password login is true with extra space on username: ", "customer",instance.checkIfValidLogin("newWorld ", "ahhhh"));
     	
     	assertEquals("Test to see if no password passed during login is false", "Invalid Username/Password", instance.checkIfValidLogin("newWorld", ""));
     	assertEquals("Invalid Username passed", "Invalid Username/Password",instance.checkIfValidLogin("neWorld", "ahhhh"));
     	assertEquals("Invalid password passed during login", "Invalid Username/Password", instance.checkIfValidLogin("newWorld", "ahh"));
     	assertEquals("Invalid case change password passed during login", "Invalid Username/Password", instance.checkIfValidLogin("newWorld", "ahhhH"));
	}
}
