package tests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import user.User;
import Appointment.Appointment;
import Appointment.Room;
import calendar.Calendar;


public class AppointmentTest extends TestSuperClass {
	
	
	// Lage Appointments med riktige og gale verdier.
	@Test
	public void setAppointmentTest() throws Exception {
		deleteRoom("Kantinen");
		deleteRoom("Soverommet");
		deleteUser("olakul");
		
		User ola = new User("Ola", "Kristiansen", "olakul", "kuleste123", "ola.kri@gmail.com");
		Calendar ok2015 = ola.getPersonalCalendar(); 
		Room kantinen = new Room("Kantinen", 22, "K-101"); 
		Appointment kiltfest = new Appointment(ola, "Kiltfest", Timestamp.valueOf("2015-03-15 17:00:00.0"), Timestamp.valueOf("2015-03-15 18:00:00.0"), 2, "Kiltfest for å feire at de nye har fått kilt.",160);
		kiltfest.updateRoom(kantinen);
		
		assertEquals("olakul", kiltfest.getOwner().getUsername()); 
		assertEquals("Kantinen", kiltfest.getRoom().getRoomName()); 
		assertEquals(ok2015.getCalendarID(), kiltfest.getCalendar().getCalendarID()); 

		ola = new User("olakul");  
		Room soverommet = new Room("Soverommet", 2, "S2"); 
		try {
			Appointment beis = new Appointment(ola, "Beis&&", Timestamp.valueOf("2015-04-01 22:15:00.0"), Timestamp.valueOf("2015-04-01 22:45:00.0"), 3,"Fordi beis er bra. Alltid", 2); 			
			fail("The title should not be allowed."); 
		}
		catch (Exception e) {
			// Her vil vi havne.
		}

	}

	// Oppdatere med riktige verdier.
	@Test
	public void updateAppointmentTest() throws Exception {
		deleteRoom("lesesalen");
		deleteRoom("hjemme");
		deleteUser("stephabu");
		
		User stephanie = new User("Stephanie", "Buadu", "stephabu", "blomstererfint123", "stepabu@stud.ntnu.no"); 
		Room lesesal = new Room("lesesalen", 6, "GK-01"); 
		Room hjemme = new Room("hjemme",22, "RFKK"); 
		Appointment bursdag = new Appointment(stephanie, "bursdag", Timestamp.valueOf("2015-06-16 22:00:00.0"), Timestamp.valueOf("2015-06-17 03:00:00.0"), 1, "Nå skal det feires bursdag", 20);
		bursdag.updateRoom(lesesal);
		assertEquals(stephanie.getPersonalCalendar().getCalendarID(), bursdag.getCalendar().getCalendarID()); 
		assertEquals("lesesalen", bursdag.getRoom().getRoomName()); 
		assertEquals("bursdag", bursdag.getTitle());
		
		bursdag.updateTitle("flæææ");
		bursdag.updateDescription("det skal bli fylla!!");
		bursdag.updateMaxParticipants(10);
		bursdag.updateRoom(hjemme);
		assertEquals("flæææ", bursdag.getTitle()); 
		assertEquals("hjemme", bursdag.getRoom().getRoomName());  	
	}
	
	@Test 
	public void addParticipant() throws Exception {
		deleteUser("sandra");
		deleteUser("tomtom");
		deleteUser("fredrikOlsen");
		
		User sandra = new User("Sandra", "Buadu", "sandra", "pass", "sandra@gotmail.com");
		User thomas = new User("Thomas", "Kristiansen", "tomtom", "pass", "thomas@gmail.com");
		User fredrik = new User("Fredrik", "Olsen", "fredrikOlsen", "pass", "fredrik@gmail.com");
		Appointment fotball = new Appointment(sandra, "Fotballtrening", Timestamp.valueOf("2015-04-20 18:00:00"), Timestamp.valueOf("2015-04-20 18:00:00"), 3, "Spille fotball", 20);
		fotball.addParticipant(sandra);
		fotball.addParticipant(thomas);
		fotball.addParticipant(fredrik);
		
		ArrayList<User> participants = new ArrayList<User>(Arrays.asList(sandra, thomas, fredrik));
		
		assertTrue(participants.equals(fotball.getParticipants()));
		
	}

}
