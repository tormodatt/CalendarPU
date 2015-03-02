package user;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public boolean test() {
		User testuser = new User(null, null, null, null, null);
		String navn = "petter"; 
		assertTrue(navn.equals(testuser.setNavn(navn))); 
	
	}
	
}


