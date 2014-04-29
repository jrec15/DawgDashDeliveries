package dawgdash.entities;
//package dawgdashdeliveries;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dawgdash.entities.Delivery;
import dawgdash.entities.Schedule;
import dawgdash.entities.User;
import dawgdash.dbaccess.DBHelper;

public class DeliveryTest {
	
	@Before
	public void setUp() throws Exception {
		DBHelper instance = new DBHelper();
		
		instance.setupUser();
		instance.setupSchedule();
		instance.setupDelivery();
	}
	
	@Test
	public void test() {
	}
	@Test
	public void testConstructor() throws Exception {
		DBHelper instance = new DBHelper();
		
		User client1 = new User("David", "dseiav1@uga.edu",
				"dss", "password", "1180 White Oak Drive, Athens, GA 30606", "client");
		User client2 = new User("Carrie", "wee12004@gmail.com",
				"wee1", "nope", "1180 White Oak Drive, Athens, GA 30606", "client");
		User client3 = new User("Graphic Dude",
				"newWorld@graphics.com", "newWorld", "ahhhh", "110 Ben Burton Parkway, Statham, GA 30666", "client");
		User client4 = new User("David S", "dseiav1@charter.net",
				"dude", "yep", "194 Highland Park Drive, Athens, GA 30605", "client");
		instance.addUser(client1);
		instance.addUser(client2);
		instance.addUser(client3);
		instance.addUser(client4);
		
		User worker1 = new User("Henry", "hen@yahoo.com", "henWinner", "whatI", 1, "worker");
		worker1.setRating(3);
		User worker2 = new User("Carl", "carl@dawgdashdeliveries.com", "kingEND", "passcode", 3, "admin");
		worker2.setRating(2);
		User worker3 = new User("Gator", "inDA@swamp.net", "tenFoot", "tail", 3, "worker");
		worker3.setRating(4);
		User worker4 = new User("Tom", "tom@gmail.com", "killJerry", "never", 2, "worker");
		worker4.setRating(5);
		instance.addUser(worker1);
		instance.addUser(worker2);
		instance.addUser(worker3);
		instance.addUser(worker4);
		
		Schedule schedule1 = new Schedule(1, "12-30-24-00", "01-00-24-00", "08-30-14-30", "10-20-17-30", "08-00-12-00", "14-00-18-00", "00-00-00-00");
		Schedule schedule2 = new Schedule(2, "08-15-13-30", "01-00-00-00", "09-15-15-30", "08-00-13-00", "12-00-16-00", "08-00-12-00", "13-00-18-00");
		Schedule schedule3 = new Schedule(3, "10-00-24-00", "01-00-24-00", "00-00-00-00", "13-20-18-00", "09-00-13-00", "10-15-13-15", "08-00-13-30");
		Schedule schedule4 = new Schedule(4, "15-00-24-00", "01-00-24-00", "13-15-18-00", "00-00-00-00", "13-00-18-00", "00-00-00-00", "10-00-14-00");

		instance.setSchedule(schedule1);
		instance.setSchedule(schedule2);
		instance.setSchedule(schedule3);
		instance.setSchedule(schedule4);
		
		Delivery delivery1 = new Delivery(1, "leave it at the side door", 1, "555 wall street", "678 athens drive" );
		Delivery delivery2 = new Delivery(2, "Bring it around front", 3, "999 wall street", "234 athens drive" );
		Delivery delivery3 = new Delivery(3, "Leave with receptionist", 2, "1020 wall street", "0923 athens drive" );
		Delivery delivery4 = new Delivery(4, "None", 3, "7777 wall street", "303 athens drive" );
		
		instance.addDelivery(delivery1);
		instance.addDelivery(delivery2);
		instance.addDelivery(delivery3);
		instance.addDelivery(delivery4);
		
		
		ArrayList<Delivery> deliveryTestList= instance.getDeliveryList();
     	assertEquals("Deliveries in list",4, deliveryTestList.size());
     	
     	assertEquals("Delivery 1 ID", 1, deliveryTestList.get(0).getID());
     	assertEquals("Delivery 1 Client ID", 1, deliveryTestList.get(0).getClientID());
     	assertEquals("Delivery 1 Worker ID", 4, deliveryTestList.get(0).getWorkerID());
     	assertEquals("Delivery 1 Delivery Status", "Pending", deliveryTestList.get(0).getDeliveryStatus());
     	assertEquals("Delivery 1 Rating", 0, deliveryTestList.get(0).getRating());
     	assertEquals("Delivery 1 Worker Comment", "None", deliveryTestList.get(0).getWorkerComment());
     	assertEquals("Delivery 1 Client Comment", "leave it at the side door", deliveryTestList.get(0).getClientComment());
     	assertEquals("Delivery 1 Duration", 30, deliveryTestList.get(0).getDuration());
     	assertEquals("Delivery 1 Transportation", 1, deliveryTestList.get(0).getTransportation());
     	assertEquals("Delivery 1 Source Address", "555 wall street", deliveryTestList.get(0).getSourceAddress());
     	assertEquals("Delivery 1 Destination Address", "678 athens drive", deliveryTestList.get(0).getDestinationAddress());
     	
     	assertEquals("Delivery 2 Worker ID", 3, deliveryTestList.get(1).getWorkerID());
     	assertEquals("Delivery 3 Worker ID", 4, deliveryTestList.get(2).getWorkerID());
     	assertEquals("Delivery 4 Worker ID", 3, deliveryTestList.get(3).getWorkerID());
     
	}

}
