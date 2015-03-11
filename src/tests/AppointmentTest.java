package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Appointment.Appointment;
import calendar.Calendar;
import Appointment.Room; 
import user.User;


public class AppointmentTest {

	@Test
	public void setAppointmentTest() {
		try{
			User ola = new User("Ola", "Kristiansen", "olakul", "kuleste123", "ola.kri@gmail.com");
			Calendar ok2015 = new Calendar(ola, "testkalender"); 
			Room kantinen = new Room("Kantinen", 22, "K-101"); 
			Appointment kiltfest = new Appointment(ok2015.getCalendarID(), ola, "Kiltfest", "2015-03-15 17:00:00.0" , "2015-03-15 18:00:00.0", kantinen, 2, "Kiltfest for å feire at de nye har fått kilt.",160, "2015-03-15 15:45:00.0");
			assertEquals("olakul", kiltfest.getOwner().getUsername()); 
			assertEquals("Kantinen", kiltfest.getRoom().getRoomName()); 
			assertEquals(ok2015.getCalendarID(), kiltfest.getCalendarID()); 
		}catch(Exception e){
		}

		try{
			User ola = new User("olakul"); 
			Calendar ok2015 = new Calendar(ola, "testkalender"); 
			Room soverommet = new Room("Soverommet", 2, "S2"); 
			Appointment beis = new Appointment(ok2015.getCalendarID(), ola, "Beis&&", "2015-04-01 22:15:00.0", "2015-04-01 22:45:00.0", soverommet, 3,"Fordi beis er bra. Alltid", 2, null ); 
			fail("The title is not valid"); 
		}catch(Exception e){
			new IllegalArgumentException(); 
		}

	}

	@Test
	public void updateAppointmentTest(){
		try {
			User stephanie = new User("Stephanie", "Buadu", "stephabu", "blomstererfint123", "stepabu@stud.ntnu.no");
			Calendar steph15 = new Calendar(stephanie, "partykalender"); 
			Room lesesal = new Room("lesesalen", 6, "GK-01"); 
			Room hjemme = new Room("hjemme",22, "RFKK"); 
			Appointment bursdag = new Appointment(steph15.getCalendarID(), stephanie, "bursdag", "2015-06-16 22:00:00.0", "2015-06-17 03:00:00.0", lesesal, 1, "Nå skal det feires bursdag", 20, null); 
			assertEquals(steph15.getCalendarID(), bursdag.getCalendarID()); 
			assertEquals("lesesalen", bursdag.getRoom().getRoomName()); 
			assertEquals("bursdag", bursdag.getTitle()); 
			bursdag.updateAppointment(steph15.getCalendarID(), stephanie, "flæææ", "2015-06-16 22:00:00.0", "2015-06-17 03:00:00.0", hjemme, 3, "det skal bli fylla!!", 10, null);
			assertEquals("flæææ", bursdag.getTitle()); 
			assertEquals("hjemme", bursdag.getRoom().getRoomName()); 
		}catch(Exception e){
			e.printStackTrace();
		} 	
	}
	
	@Test 
	public void addParticipant(){
		try {
			User sandra = new User("Sandra", "Buadu", "sandra", "1234567890", "sandra@gotmail.com");
			User thomas = new User("Thomas", "Kristiansen", "tomtom", "abcabcabc", "thomas@gmail.com"); 
			//må lage et arrangement og alle tilhørende ting
			//legge til deltakerne
			//sjekke at de er der
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}

}
