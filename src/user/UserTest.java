package user;

import static org.junit.Assert.*;

import org.junit.Test;

import calendar.PersonalCalendar;

public class UserTest {

	@Test
	public void testSetUser1() {
		User testbruker = new User("Ola", "Nordmann", "olnord", "12a34b56c78d90e", "ola.nordmann@bedrift.no"); 
			
	}
	 @Test
	public void testSetUser2(){
		
	}
	 
	@Test
	public void testChangeName(){
		User testbruker = new User("Ola", "Nordmann", "olnord", "12a34b56c78d90e", "ola.nordmann@bedrift.no");
		
		)
		
	@Test
	public voir testChangeEmail(){
		User testbruker = new User("Ola", "Nordmann", "olnord", "12a34b56c78d90e", "ola.nordmann@bedrift.no");
		testbruker.setMail("brodskrive@polegg@src.no");
		
	}
		


}


