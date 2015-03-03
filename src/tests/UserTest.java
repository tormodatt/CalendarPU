package tests;
import static org.junit.Assert.*;

import org.junit.Test;

import user.User;
import calendar.PersonalCalendar;

public class UserTest {

	private void testException(Exception e, Class<? extends Exception> c) {
		assertTrue(e + " should be assignable to " + c, c.isAssignableFrom(e.getClass()));

	}

	@Test //et helt lovlig brukernavn
	public void testSetUser1(){
		try {
			User testbruker = new User("olnord");
			assertEquals("olnord", testbruker.getUsername()); 
		} catch (Exception e) {
			fail("olnord is a valid username"); 
		} 

	}
	@Test //det skal ikke være lov med tall i brukernavnet
	public void testSetUser2(){
		try{
			String name = "bh1nn"; 
			User testbruker = new User(name);	
			fail(name + "is an invalid username"); 
		}catch (Exception e){
			testException(e, IllegalArgumentException.class);
		}
	}
	@Test //spesielle symboler er ikke lov i et brukernavn
	public void testSetUser3(){
		try {
			User testbruker = new User("??br");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test //et brukernavn skal være sammenhengende
	public void testSetUser4(){
		try {
			User testbruker = new User("ab  vd");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		 
	}

	@Test //her sendes det kun inn lovlige verdiier. 
	public void testSetCrendentials(){
		User testbruker;
		try {
			testbruker = new User("olnord");
			testbruker.setCredencials("Ola", "Nordmann", "olnord", "password", "mail@mail.com");
			testbruker = new User("olnord");
			testbruker.setCredencials("Ola", "Nordmann", "olnord", "password", "mail@mail.com");
			assertEquals("Ola Normann", testbruker.getName()); 
			assertEquals("olnord", testbruker.getUsername()); 
			assertEquals("mail@mail.com", testbruker.getMail()); 
		} catch (Exception e) {
			fail("All the credentials are valid");
		} 
	}

	@Test //navn med tall i skal ikke aksepteres
	public void testSetName(){
		User testbruker; 
		try{
			testbruker = new User("olnord");
			testbruker.setCredencials("Ola", "Nordmann", "olnord", "password", "mail@mail.com");
		}catch (Exception e){

		}
	}



}


