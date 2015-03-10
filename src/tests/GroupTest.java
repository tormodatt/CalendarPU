package tests;

import static org.junit.Assert.assertEquals;
import user.Group;
import user.User;
import calendar.Database;

import java.sql.*;

public class GroupTest extends Database {
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
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
	
	public void testMainGroup() throws Exception {
		
	}
	
}
	
	
	
	
	
	
	
	
	



