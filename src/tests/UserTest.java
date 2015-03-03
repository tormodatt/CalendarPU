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
	@Test 
	public void testIllegalUsername(){
		try{
			String name = "bh1nn"; 
			User testbruker = new User(name);	
			fail(name + "is an invalid username"); 
		}catch (Exception e){
			testException(e, IllegalArgumentException.class);
		}
		try {
			String name = "??br"; 
			User testbruker2 = new User(name);
			fail(name + "is an invalid username");
		} catch (Exception e) {
			testException(e, IllegalArgumentException.class);
		}
		try {
			String name = "ab vd"; 
			User testbruker = new User(name);
			fail(name + "is an invalid username"); 
		} catch (Exception e) {
			testException(e, IllegalArgumentException.class);
		} 

	}


	@Test //her sendes det kun inn lovlige verdiier. 
	public void testSetCrendentials(){
		User testbruker;
		try {
			testbruker = new User("olnord");
			testbruker.setCredencials("Ola", "Nordmann", "olnord", "password", "mail@mail.com");
			assertEquals("Ola", testbruker.getFirstname()); 
			assertEquals("Nordmann", testbruker.getLastname()); 
			assertEquals("olnord", testbruker.getUsername()); 
			assertEquals("mail@mail.com", testbruker.getMail()); 
		} catch (Exception e) {
			fail("All the credentials are valid");
		} 
	}

	@Test 
	public void testIllegalCredentials(){
		User testbruker; 
		try{
			testbruker = new User("olnord");
			testbruker.setCredencials("Ola22", "Nordmann", "olnord", "password", "mail@mail.com");
			fail("Ola22 is an invalid first name"); 
		}catch (Exception e){
			testException(e, IllegalArgumentException.class);
		}
		try{
			testbruker = new User("olnord");
			testbruker.setCredencials("Ola", "Nord mann", "olnord", "password", "mail@mail.com");
			fail("Nord mann is an invalid last name"); 
		}catch (Exception e){
			testException(e, IllegalArgumentException.class);
		}
		try {
			testbruker = new User("olnord");
			testbruker.setCredencials("Ola", "Nordmann", "olnord", "password", "per.stian@ntnu.no.com"); 
			fail("a mail adress shout only have one dot after det @");
		}catch (Exception e){
			testException(e, IllegalArgumentException.class);
		}
		try {
			testbruker = new User("olnord");
			testbruker.setCredencials("Ola", "Nordmann", "olnord", "password", "per.stian@ntnu@no"); 
			fail("a mail adress shout only contain one @");
		}catch (Exception e){
			testException(e, IllegalArgumentException.class);
		}

	}

	@Test
	public void testSetMail(){
		try{
			User testbruker = new User("olnord"); 
			testbruker.setCredencials("Ola", "Nordmann", "olnord", "password", "mail@mail.com");
			testbruker.setMail("peppes@pizza.no");
			assertEquals("peppes@pizza.com", testbruker.getMail()); 
		}catch (Exception e){
			fail("they should be the same"); 
		}
	}



}


