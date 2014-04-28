import static org.junit.Assert.*;

import org.junit.Test;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class ScheduleTest {
	
	@Before
	public void setUp() throws Exception {
		DBHelper instance = new DBHelper();
		
		instance.setupUser();
		instance.setupDelivery();
		instance.setupSchedule();
	}
	
	@Test
	public void test() {
	}
	@Test
	public void testConstructor() throws Exception {
		DBHelper instance = new DBHelper();
		
		
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
		
		Schedule schedule1 = new Schedule(1, "12-30-23-00", "01-00-09-00", "08-30-14-30", "10-20-17-30", "08-00-12-00", "14-00-18-00", "00-00-00-00");
		Schedule schedule2 = new Schedule(2, "08-15-13-30", "00-00-00-00", "09-15-15-30", "08-00-13-00", "12-00-16-00", "08-00-12-00", "13-00-18-00");
		Schedule schedule3 = new Schedule(3, "10-00-23-00", "01-00-24-00", "00-00-00-00", "13-20-18-00", "09-00-13-00", "10-15-13-15", "08-00-13-30");
		Schedule schedule4 = new Schedule(4, "15-00-23-00", "01-00-24-00", "13-15-18-00", "00-00-00-00", "13-00-18-00", "00-00-00-00", "10-00-14-00");

		instance.setSchedule(schedule1);
		instance.setSchedule(schedule2);
		instance.setSchedule(schedule3);
		instance.setSchedule(schedule4);
		
		
		ArrayList<Schedule> scheduleTestList= instance.getScheduleList();
     	assertEquals("Schedules in list",4, scheduleTestList.size());
     	
     	assertEquals("Schedule 1 Worker ID", 1, scheduleTestList.get(0).getWorkerID());
     	assertEquals("Schedule 1 Sunday", "12:30 pm - 11:00 pm", scheduleTestList.get(0).getSundayString());
     	assertEquals("Schedule 1 Monday", "1:00 am - 9:00 am", scheduleTestList.get(0).getMondayString());
     	assertEquals("Schedule 1 Tuesday", "8:30 am - 2:30 pm", scheduleTestList.get(0).getTuesdayString());
     	assertEquals("Schedule 1 Wednesday", "10:20 am - 5:30 pm", scheduleTestList.get(0).getWednesdayString());
     	assertEquals("Schedule 1 Thursday", "8:00 am - 12:00 pm", scheduleTestList.get(0).getThursdayString());
     	assertEquals("Schedule 1 Friday", "2:00 pm - 6:00 pm", scheduleTestList.get(0).getFridayString());
     	assertEquals("Schedule 1 Saturday", "Off", scheduleTestList.get(0).getSaturdayString());
    
     
	}

}
