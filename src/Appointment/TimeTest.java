package Appointment;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeTest {
	
	@Test
	public void testDate() {
		Time time = new Time(2014, 03, 02, 17, 30, 60);
		int year = time.getDate().getYear(); 
		int month = time.getDate().getMonth(); 
		int date = time.getDate().getDate(); 
		int hours = time.getDate().getHours(); 
		int minutes = time.getDate().getMinutes(); 
		int duration = time.getDuration(); 
		assertEquals(year, 2014);
		assertEquals(month, 03);
		assertEquals(date, 02);
		assertEquals(hours, 17);
		assertEquals(minutes, 30);
		assertEquals(duration, 60);
	}
	
	
	

}
