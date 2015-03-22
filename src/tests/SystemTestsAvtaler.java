package tests;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import user.Group;
import user.User;
import Appointment.Appointment;
import Appointment.Notification;
import Appointment.Room;
import calendar.Database;

@SuppressWarnings("unused")
public class SystemTestsAvtaler extends Database {
	
	User u1;
	User u2;
	User u3;
	Group g1;
	int gid;
	Room r1;
	Appointment a1;
	
	@Before
	private void setUp() throws Exception {
		u1 = new User("Per", "Olsen", "perOlsen", "pass", "per.olsen@mail.com");
		r1 = new Room("Zoo2", 10, "Andre etasje");
	}
	
	private void setUp2() throws Exception {
		u1 = new User("Per", "Olsen", "perOlsen", "pass", "per.olsen@mail.com");
		u2 = new User("Tor", "Nilsen", "torNilsen", "pass", "tor.nilsen@mail.com");
		u3 = new User("Ole", "Hansen", "oleHansen", "pass", "ole.hansen@mail.com");
		Group g1 = new Group("Supergruppa", u1);
		gid = g1.getGroupID();
		g1.addMember(u2);
		g1.addMember(u3);		
		r1 = new Room("Zoo2", 10, "Andre etasje");
		Appointment a1 = new Appointment(u1, "M�te", Timestamp.valueOf("2015-03-13 12:00:00"), Timestamp.valueOf("2015-03-13 14:00:00"), 1, "M�te med forretningsforbindelser.", 10);
		g1.getCalendar().addAppointment(a1);
	}
	
	@After
	private void tearDown() throws Exception {
		PreparedStatement ps = connect.prepareStatement(
				"DELETE FROM `all_s_gr46_calendar`.`User` WHERE `Username`='perOlsen';\n"
				+ "DELETE FROM `all_s_gr46_calendar`.`Room` WHERE `Name`='Zoo2';\n");
		ps.executeUpdate();
	}
	
	private void tearDown2() throws Exception {
		PreparedStatement ps1 = connect.prepareStatement("DELETE FROM `all_s_gr46_calendar`.`User` WHERE `Username`='perOlsen';\n");
		PreparedStatement ps2 = connect.prepareStatement("DELETE FROM `all_s_gr46_calendar`.`User` WHERE `Username`='torNilsen';\n");
		PreparedStatement ps3 = connect.prepareStatement("DELETE FROM `all_s_gr46_calendar`.`User` WHERE `Username`='oleHansen';\n");
		PreparedStatement ps4 = connect.prepareStatement("DELETE FROM `all_s_gr46_calendar`.`Group` WHERE `GroupID`='?';\n");
		ps4.setInt(1, gid);
		PreparedStatement ps5 = connect.prepareStatement("DELETE FROM `all_s_gr46_calendar`.`Room` WHERE `Name`='Zoo2';\n");
		ps1.executeUpdate();
		ps2.executeUpdate();
		ps3.executeUpdate();
		ps4.executeUpdate();
		ps5.executeUpdate();
	}
	
	@Test
	// Teste om brukeren kan legge inn en lovlig avtale i en kalender.
	public void testA1() throws Exception {
		try {
			setUp();
			Appointment a1 = new Appointment(u1, "M�te", Timestamp.valueOf("2015-03-13 12:00:00"), Timestamp.valueOf("2015-03-13 14:00:00"), 1, "M�te med forretningsforbindelser.", 10);
			int aid = a1.getAppointmentID();
			u1.getPersonalCalendar().addAppointment(a1);
			
			Appointment a1Copy = new Appointment(aid, u1);
			assertNotNull(a1Copy);
			assertEquals(a1, a1Copy);
		}
		finally {
			tearDown();
		}
	}
	
	@Test
	// Teste eksempler p� at ulovlige kalendere ikke kan legges inn.
	public void testA1_2() throws Exception {
		try {
			setUp();
			try {
				Appointment a1 = new Appointment(u1, "M�te", Timestamp.valueOf("2015-03-13 12:00:00"), Timestamp.valueOf("2015-03-13 10:00:00"), 1, "M�te med forretningsforbindelser.", 10);
				fail("Sluttidspunktet kan ikke v�re f�r starttidspunktet.");
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
	// A2a: Teste at endring i tidspunkt skal varsles til deltakere.
	public void testA2a() throws Exception {
		try {
			setUp2();
			//Appointment a1 = new Appointment(u1, "M�te", Timestamp.valueOf("2015-03-13 12:00:00"), Timestamp.valueOf("2015-03-13 14:00:00"), 1, "M�te med forretningsforbindelser.", 10);
			a1.updateStart(Timestamp.valueOf("2015-03-13 16:00:00"));
			a1.updateEnd(Timestamp.valueOf("2015-03-13 18:00:00"));
			PreparedStatement ps1 = connect.prepareStatement("SELECT NotificationID FROM Notification WHERE Receiver = \"torNilsen\"");
			ResultSet rs = ps1.executeQuery();
			int nid = -1;
			while (rs.next()) {
				nid = rs.getInt(1);			
			}
			Notification n  = new Notification(nid);
			assertNotNull("Det har ikke blitt opprettet noen notification.", n);
			assertEquals("Feil mottaker av notification.", u2, n.getReceiver());
		}
		finally {
			tearDown2();
		}
	}
	
	@Test
	// A2b: 
	public void testA2b() throws Exception {
		User u1 = new User("Per", "Olsen", "perOlsen", "pass", "per.olsen@mail.com");
		Room r1 = new Room("Zoo2", 10, "Andre etasje");
		Appointment a1 = new Appointment(u1, "M�te", Timestamp.valueOf("2015-03-13 12:00:00"), Timestamp.valueOf("2015-03-13 14:00:00"), 1, "M�te med forretningsforbindelser.", 10);
		try {
			Appointment a2 = new Appointment(u1, "Party", Timestamp.valueOf("2015-03-13 13:00:00"), Timestamp.valueOf("2015-03-13 15:00:00"), 1, "Kult party.", 10);
				// Denne avtalen skal ikke kunne opprettes men denne kombinasjonen av tid og rom.
				fail("Det ble ikke kastet en exception ved overlapp.");
		}
		catch (Exception e) {
		}
	}
	
	// A3: Brukere skal kunne se avtalene i sin egen kalender.
	// M� lage main-klassen f�rst.
	
	@Test
	// A5a: M�tedeltakere skal kunne bekrefte og avkrefte deltakelse.
	public void testA5a() throws Exception {
		User u1 = new User("Per", "Olsen", "perOlsen", "pass", "per.olsen@mail.com");
		User u2 = new User("Tor", "Nilsen", "torNilsen", "pass", "tor.nilsen@mail.com");
		Room r1 = new Room("Zoo2", 10, "Andre etasje");
		Appointment a1 = new Appointment(u1, "M�te", Timestamp.valueOf("2015-03-13 12:00:00"), Timestamp.valueOf("2015-03-13 14:00:00"), 1, "M�te med forretningsforbindelser.", 10);
		int aid = a1.getAppointmentID();
		a1.addParticipant(u2);
		PreparedStatement ps = connect.prepareStatement("SELECT * FROM Invited WHERE User = \"torNilsen\" AND AppointmentID = ?");
		ps.setInt(1, aid);
		ResultSet rs = ps.executeQuery();
		boolean confirmed = false;
		while (rs.next()) {
			confirmed = rs.getBoolean(3);
		}
	}
	
	@Test
	// A5d: 
	public void testA5c() throws Exception {
		Appointment a1 = new Appointment(u1, "M�te", Timestamp.valueOf("2015-03-13 12:00:00"), Timestamp.valueOf("2015-03-13 14:00:00"), 1, "M�te med forretningsforbindelser.", 10);
	}
	
	@Test
	// Beskrivelse
	public void testMal() throws Exception {			
	}
}
