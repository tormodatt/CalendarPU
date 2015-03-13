package tests;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;

import org.junit.Test;

import user.User;
import Appointment.Appointment;
import Appointment.Room;
import calendar.Database;

public class SystemTestsAppointment extends Database {
	
	User u1;
	User u2;
	User u3;
	Room r1;
	
	private void setUp() throws Exception {
		u1 = new User("Per", "Olsen", "perOlsen", "pass", "per.olsen@mail.com");
		r1 = new Room("Zoo2", 10, "Andre etasje");
	}
	
	private void tearDown() throws Exception {
		PreparedStatement ps = connect.prepareStatement(
				"DELETE FROM `all_s_gr46_calendar`.`User` WHERE `Username`='perOlsen';\n"
				+ "DELETE FROM `all_s_gr46_calendar`.`Room` WHERE `Name`='Zoo2';\n");
		ps.executeUpdate();
	}
	
	@Test
	// Teste om brukeren kan legge inn en lovlig avtale i en kalender.
	public void testA1() throws Exception {
		try {
			setUp();
			Appointment a1 = new Appointment(u1.getPersonalCalendar(), u1, "Møte", "2015-03-13 12:00:00", "2015-03-13 14:00:00", r1, 1, "Møte med forretningsforbindelser.", 10);
			int aid = a1.getAppointmentID();
			u1.getPersonalCalendar().addAppointment(a1);
			
			Appointment a1Copy = new Appointment(aid);
			assertNotNull(a1Copy);
			assertEquals(a1, a1Copy);
		}
		finally {
			tearDown();
		}
	}
	
	@Test
	// Teste eksempler på at ulovlige kalendere ikke kan legges inn.
	public void testA1_2() throws Exception {
		try {
			setUp();
			try {
				Appointment a1 = new Appointment(u1.getPersonalCalendar(), u1, "Møte", "2015-03-13 12:00:00", "2015-03-13 10:00:00", r1, 1, "Møte med forretningsforbindelser.", 10);
				fail();
			}
			catch (Exception e) {
				// Her vil vi havne :)
			}
		}
		finally {
			tearDown();
		}
	}
	
	@Test
	// Teste eksempler på at ulovlige kalendere ikke kan legges inn.
	public void testA2() throws Exception {
		try {
			setUp();
			Appointment a1 = new Appointment(u1.getPersonalCalendar(), u1, "Møte", "2015-03-13 12:00:00", "2015-03-13 10:00:00", r1, 1, "Møte med forretningsforbindelser.", 10);
		}
		finally {
			tearDown();
		}
	}
	
	
	@Test
	// Teste eksempler på at ulovlige kalendere ikke kan legges inn.
	public void testMal() throws Exception {			
	}
}
