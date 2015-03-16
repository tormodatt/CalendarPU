package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import user.Group;
import user.User;
import calendar.Calendar;
import calendar.Database;

/* F�lgende skal finnes:
 * User perOlsen
 * User torNilsen
 * Group gruppe1
 * Group gruppe2
 * Group gruppe3
 * 
 * F�lgende skal kunne opprettes (og slettes deretter).
 * User olaNordmann
 * User kariNordmann
 * Group gruppe4
 * Group gruppe5
 * Group gruppe6
 * 
 * F�lgende skal ikke kunne opprettes
 * Username: "finnesIkke"
 * GroupID: "-1"
 */

public class SystemTestsGeneral extends Database {
	
	User perOlsen;
	User torNilsen;
	Group gruppe1;
	Group gruppe2;
	Group gruppe3;
	
	@Before
	public void oneTimeSetUp() throws Exception {
		deleteUser("perOlsen");
		deleteUser("torNilsen");
		deleteGroup("gruppe1");
		deleteGroup("gruppe2");
		deleteGroup("gruppe3");
		perOlsen = new User("Per", "Olsen", "perOlsen", "pass", "per.olsen@mail.com");
		torNilsen = new User("Tor", "Nilsen", "torNilsen", "pass", "tor.nilsen@mail.com");
		gruppe1 = new Group("gruppe1", perOlsen);
		gruppe2 = new Group("gruppe2", torNilsen);
		gruppe3 = new Group("gruppe3", perOlsen);
	}
	
	@After
	public void oneTimeTearDown() throws Exception {
		// Slett brukere og grupper
	}
	
	public void deleteUser(String username) throws Exception {
		openConn();
		PreparedStatement ps = connect.prepareStatement("DELETE FROM `all_s_gr46_calendar`.`User` WHERE `Username`=?;");
		ps.setString(1, username);
		ps.executeUpdate();
		closeConn();
	}
	
	public void deleteGroup(String groupName) throws Exception {
		openConn();
		PreparedStatement ps = connect.prepareStatement("DELETE FROM `all_s_gr46_calendar`.`Group` WHERE `Name`=?;");
		ps.setString(1, groupName);
		ps.executeUpdate();
		closeConn();
	}
	
	@Test
	// Teste om en eksisterende bruker har tilknyttet en kalender."
	public void testG1_1() throws Exception {
		User user = new User("perOlsen");
		Calendar cal = user.getPersonalCalendar();
		assertNotNull(cal);
		assertEquals(user, cal.getUser());
	}
	
	@Test
	// Teste om en personlig kalender legges til ny bruker.
	public void testG1_2() throws Exception {
		deleteUser("olaNordmann");
		User user = new User("Ola", "Nordmann", "olaNordmann", "pass", "ola.nordmann@mail.com");
		Calendar cal  = user.getPersonalCalendar();
		assertNotNull(cal);
		assertEquals(user, cal.getUser());
		deleteUser("olaNordmann");
	}
	
	@Test
	// Teste om en ny person i gruppe f�r tildelt gruppekalender."
	public void testG1_3() throws Exception {
		User user = new User("Ola", "Nordmann", "olaNordmann", "pass", "ola.nordmann@mail.com");
		user.addGroup(gruppe1);
		ArrayList<Group> groups = user.getGroups();
		Calendar cal = groups.get(0).getCalendar();
		assertNotNull(cal);
		assertEquals(gruppe1.getCalendar(), cal);
	}
	
	// Teste om dersom en person slettes, vil ogs� kalenderen slettes.
	
	@Test
	// Teste om grupper kan legge til subsubgrupper.
	public void testG2_1() throws Exception {
		gruppe1.addSubGroup(gruppe2);
		Group subGroup = gruppe1.getSubGroups().get(0);
		assertNotNull(subGroup);
		assertEquals(gruppe2, subGroup);
		
		Group gruppe1copy = new Group(1);
		subGroup = gruppe1copy.getSubGroups().get(0);
		assertNotNull(subGroup);
		assertEquals(gruppe2, subGroup);
	}
	
	@Test
	// Teste om en subgruppe tilh�rer en gitt gruppe.
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
	// Klienten (main-klassen) m� lages f�rst.
	
	// G6: Mange kalenderklienter skal kunne v�re koblet opp mot tjeneren samtidig.
	// Klienten (main-klassen) m� lages f�rst.
	
	
	
}