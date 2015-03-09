package tests;

import static org.junit.Assert.assertEquals;


import user.Group;
import user.User;

public class GroupTest {
	
	public void testSetName(){
		try{
			String name = "joanna"; 
			User testbruker1 = new User(name);
			Group testgruppe = new Group("joanna", testbruker1);
			assertEquals("joanna", testgruppe.getName()); 
			assertEquals(name, testgruppe.getName()); 
		} catch (Exception e) {
	
		}
	}
	
	
	
}


