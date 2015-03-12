package tests;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import user.Group;
import user.User;
import calendar.Calendar;
import calendar.Database;

public class SystemTests extends Database {
	
	@Test
	//TODO Skriv ferdig opprette-kalender-tester når user er ferdig.
	//Tester kun lovlig input
	public void SetCalendarTest1() throws Exception { 
		try{
			User ola = new User("perOlsen");
			Calendar ola2015 = new Calendar(ola, "ola 2015");
			
			assertEquals("perOlsen", ola2015.getUser().getUsername());
			assertEquals("ola 2015", ola2015.getTitle()); 
		}
		catch (Exception e){
			fail();
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
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	@Test
	public void testSetName() {
		try {
			String name = "joanna"; 
			User testbruker1 = new User(name);
			Group testgruppe = new Group("Bestegruppen", testbruker1);
			assertEquals("joanna", testgruppe.getName()); 
			assertEquals(name, testgruppe.getName()); 
		} catch (Exception e) {
	
		}
	}
	
	@Test
	public void testSetLeader() {
		try {
			String name = "hans"; 
			User testUser = new User(name); 
			Group testGroup = new Group("Kongegruppen", testUser);
			testGroup.setLeader(testUser);
			assertEquals(testGroup.getLeader(), testUser);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testGroupID() throws Exception {
		try {
			String name = "jojo";
			User testUser = new User(name);
			Group testGroup = new Group("Kul gruppe", testUser);
			preparedStatement = connect.prepareStatement("insert into Group values (default,?,?)");
			preparedStatement.setString(1,name);
			preparedStatement.setString(2,testUser.getUsername());
			resultSet = preparedStatement.executeQuery();
			assertEquals(testGroup.getGroupID(), resultSet.getInt("GroupID"));
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testMainGroup() throws Exception {
		try {
			String name = "jojo";
			String name2 = "haha";
			User testUser = new User(name);
			User testUser2 = new User(name2);
			Group testGroup = new Group("Kul gruppe", testUser);
			Group testGroup2 = new Group("Kulere gruppe", testUser2);
			testGroup.setMainGroup(testGroup2);
			preparedStatement = connect.prepareStatement("insert into Group_relation (Super_group) values (?)");
			preparedStatement.setInt(1,testGroup2.getGroupID());
			resultSet = preparedStatement.executeQuery();
			assertEquals(testGroup.getMainGroup().getLeader().getUsername(), "haha");
		} catch (Exception e) {
		}	
	}
	
	@Test
	public void testSubGroup() throws Exception {
		try {
			String name = "jojo";
			String name2 = "haha";
			User testUser = new User(name);
			User testUser2 = new User(name2);
			Group testGroup = new Group("Kul gruppe", testUser);
			Group testGroup2 = new Group("Kulere gruppe", testUser2);
			testGroup.addSubGroup(testGroup2);
			assertEquals(testGroup.getSubGroups(), testGroup2);
		} catch (Exception e) {
		}
	}
		
	@Test
	public void testG1() {
		fail("Not yet implemented");			
	}
	
	
	
}