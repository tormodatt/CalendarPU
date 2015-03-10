package tests;

import static org.junit.Assert.assertEquals;
import user.Group;
import user.User;
import calendar.Database;

import org.junit.Test;

import java.sql.*;

public class GroupTest extends Database {
	
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
	
}
	
		

	
	
	
	
	
	
	



