package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import user.Group;
import user.User;
import calendar.Calendar;


public class CalendarTest {
	
	private User ola;
	
	private void setUp() throws Exception {
		ola = new User("perOlsen");
	}
	
	@Test
	//TODO Skriv ferdig opprette-kalender-tester når user er ferdig.
	//Tester kun lovlig input
	public void SetCalendarTest1() throws Exception {
		try{
			setUp();
			Calendar ola2015 = ola.getPersonalCalendar();
			
			assertEquals("perOlsen", ola2015.getUser().getUsername());
			assertEquals("ola 2015", ola2015.getTitle()); 
		}
		catch (Exception e){
			fail(e.toString());
		}
		try{
			User ola = new User("perOlsen"); 
			Group gruppe = new Group("test", ola); 
			Calendar test2015 = new Calendar(gruppe, "test2015"); 
			assertEquals("gruppe", test2015.getGroup().getName()); 
			assertEquals("test2015", test2015.getTitle()); 
		}
		catch (Exception e){
			fail("Det kastes en exception.");
		}
	}
	
	@Test
	//tester ulovlige kalendertitteler
	public void SetCalendarTest2(){	
		try{
			User ola = new User("perOlsen");
			Calendar ola2015 = new Calendar(ola, "gr"); 

		}
		catch (Exception e){			
			new IllegalArgumentException(); 
		}	
		try{
			User ola = new User("perOlsen");
			Group gruppe = new Group("test", ola);
			Calendar test2015 = new Calendar(gruppe, "?? ulovlig navn"); 
		}
		catch(Exception e){
			new IllegalArgumentException(); 
		}
	}
	
	@Test
	public void GetCalendarTestUser(){ //hente et en kalender som er knyttet til en bruker
		try {
			User ola = new User("perOlsen");
			Calendar test = new Calendar(ola); 
			assertEquals("perOlsen", test.getUser().getUsername()); 
			assertEquals("test2015", test.getTitle()); 
		}catch (Exception e) {
			fail("");
		}
	}
	public void GetCalendarTestGroup(){
		try{
			
			User ola = new User("perOlsen"); 
			Group test = new Group("test", ola); 
			Calendar cal = new Calendar(test, "calcal");  
			assertEquals("calcal", cal.getTitle()); 
			assertEquals("test", cal.getGroup().getName()); 
		}catch(Exception e){
			
		}
	}
	
	@Test
	public void setAppointmentTest(){
		fail("not yet implemented"); 
	}

}
