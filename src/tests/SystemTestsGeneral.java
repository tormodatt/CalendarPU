package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import user.Group;
import user.User;
import calendar.Calendar;

/* Følgende skal finnes:
 * User perOlsen
 * User torNilsen
 * Group gruppe1
 * Group gruppe2
 * Group gruppe3
 * 
 * Følgende skal kunne opprettes (og slettes deretter).
 * User olaNordmann
 * User kariNordmann
 * Group gruppe4
 * Group gruppe5
 * Group gruppe6
 * 
 * Følgende skal ikke kunne opprettes
 * Username: "finnesIkke"
 * GroupID: "-1"
 */

public class SystemTestsGeneral {
	
	static User perOlsen;
	static User torNilsen;
	static Group gruppe1;
	static Group gruppe2;
	static Group gruppe3;
	
	public static void oneTimeSetUp() throws Exception {
		perOlsen = new User("Per", "Olsen", "perOlsen", "pass", "per.olsen@mail.com");
		torNilsen = new User("Tor", "Nilsen", "torNilsen", "pass", "tor.nilsen@mail.com");
		gruppe1 = new Group("gruppe1", perOlsen);
		gruppe2 = new Group("gruppe2", torNilsen);
		gruppe3 = new Group("gruppe3", perOlsen);
	}
	
	public static void oneTimeTearDown() throws Exception {
		// Slett brukere og grupper
	}
	
	@Test
	// Teste om en eksisterende bruker har tilknyttet en kalender."
	public void testG1_1() throws Exception {
		User user = new User("perOlsen");
		Calendar cal = user.getPersonalCalendar();
		assertNotNull(cal);
		assertEquals(user, cal);
	}
	
	@Test
	// Teste om en personlig kalender legges til ny bruker.
	public void testG1_2() throws Exception {
		User user = new User("Ola", "Nordmann", "olaNordmann", "pass", "ola.nordmann@mail.com");
		Calendar cal  = user.getPersonalCalendar();
		assertNotNull(cal);
		assertEquals(user, cal.getUser());
		// Slett brukeren.
	}
	
	@Test
	// Teste om en ny person i gruppe får tildelt gruppekalender."
	public void testG1_3() throws Exception {
		User user = new User("Ola", "Nordmann", "olaNordmann", "pass", "ola.nordmann@mail.com");
		user.addGroup(gruppe1);
		ArrayList<Group> groups = user.getGroups();
		Calendar cal = groups.get(0).getCalendar();
		assertNotNull(cal);
		assertEquals(gruppe1.getCalendar(), cal);
	}
	
	// Teste om dersom en person slettes, vil også kalenderen slettes.
	
	@Test
	// Teste om grupper kan legge til subsubgrupper.
	public void testG2_1() throws Exception {
		gruppe1.addSubGroup(gruppe2);
		Group subGroup = gruppe1.getSubGroups().get(0);
		int gid = subGroup.getGroupID();
		assertNotNull(subGroup);
		assertEquals(gruppe2, subGroup);
		
		Group gruppe1copy = new Group(gid);
		// DEBUG START
		System.out.println(gruppe1copy.getSubGroups());
		// DEBUG END
		subGroup = gruppe1copy.getSubGroups().get(0);
		assertNotNull(subGroup);
		assertEquals(gruppe2, subGroup);
	}
	
	@Test
	// Teste om en subgruppe tilhører en gitt gruppe.
	public void testG2_2() throws Exception {
		Group mainGroup = gruppe2.getMainGroup();
		assertEquals(gruppe1, mainGroup);
		
		Group gruppe2copy = new Group(2);
		mainGroup = gruppe2copy.getMainGroup();
		assertEquals(gruppe1, mainGroup);
		
	}
	
	// Teste at grupper uten sub- og main-grupper ikke skal returnere sub- eller main-grupper.
		public void testG2_3() throws Exception {
			assertNull(gruppe3.getSubGroups());
			assertNull(gruppe3.getMainGroup());
		}
	
	@Test
	// Tester om en en person kan legges til i en gruppe, fra personen.
	public void testG3_1() throws Exception {
		assertTrue((perOlsen.getGroups()).size() == 1);
		assertEquals(perOlsen.getGroups().get(0), gruppe1);
		assertTrue(gruppe1.getMembers().contains(perOlsen));
		
		perOlsen.addGroup(gruppe2);
		
		ArrayList<Group> groups = perOlsen.getGroups();
		assertTrue(groups.size() == 2);
		assertTrue(groups.get(0) == gruppe1 || groups.get(0) == gruppe2);
		assertTrue(groups.get(1) == gruppe1 || groups.get(0) == gruppe2);
		
		assertTrue(gruppe2.getMembers().contains(perOlsen));
		
	}
	
	@Test
	// Tester om en en person kan legges til i en gruppe, fra gruppen.
	public void testG3_2() throws Exception {
		
		gruppe2.addMember(perOlsen);
		
		ArrayList<Group> groups = perOlsen.getGroups();
		assertTrue(groups.size() == 2);
		assertTrue(groups.get(0) == gruppe1 || groups.get(0) == gruppe2);
		assertTrue(groups.get(1) == gruppe1 || groups.get(0) == gruppe2);
		
		assertTrue(gruppe2.getMembers().contains(perOlsen));
		
	}
	

	// G4: Teste om man kan koble til databasen og legge til kalendere.
	// Testen finnes i SystemTestsCalendar
	
	// G5: ...
	// Klienten (main-klassen) må lages først.
	
	// G6: Mange kalenderklienter skal kunne være koblet opp mot tjeneren samtidig.
	// Klienten (main-klassen) må lages først.
	
	
	
}