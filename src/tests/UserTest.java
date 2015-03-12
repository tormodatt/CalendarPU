package tests;
import static org.junit.Assert.*;

import org.junit.Test;

import user.User;


public class UserTest {

	private void testException(Exception e, Class<? extends Exception> c) {
		assertTrue(e + " should be assignable to " + c, c.isAssignableFrom(e.getClass()));

	}
	@Test //et helt lovlig brukernavn
	public void testSetUser1(){
		try{
			String name = "bh1nn"; 
			
			//Opprette bruker
			User testbruker = new User("Thomas", "pettersen", name, "password", "tiss@promp.no");
			
			//Hente bruker
			User testbruker1 = new User(name);
			
			assertEquals("Thomas", testbruker.getFirstname()); 
			assertEquals(name, testbruker.getUsername());
			assertEquals(name, testbruker1.getUsername());
		}
		catch (Exception e){
			fail("Det kastes en Exception.");
		}

	}
	public void testIllegalUsername(){
		try {
			String name = "??br"; 
			User testbruker = new User("Ola", "Nordmann", name, "password", "mail@mail.com");
			fail(name + "is an invalid username");
		} catch (Exception e) {
			new IllegalArgumentException(); 
		}
		try {
			String name = "ab vd"; 
			User testbruker = new User("Ola", "Nordmann", name, "password", "mail@mail.com");
			fail(name + "is an invalid username"); 
		} catch (Exception e) {
			testException(e, IllegalArgumentException.class);
		} 

	}
	@Test //her sendes det kun inn lovlige verdiier. 
	public void testSetCrendentials(){

		try {
			User testbruker = new User("Thomas", "pettersen","olnord", "password", "tiss@promp.no"); 
			testbruker.setCredencials("Ola", "Nordmann", "olnord", "password", "mail@mail.com");
			assertEquals("Ola", testbruker.getFirstname()); 
			assertEquals("Nordmann", testbruker.getLastname()); 
			assertEquals("olnord", testbruker.getUsername()); 
			assertEquals("mail@mail.com", testbruker.getMail()); 
		} catch (Exception e) {
			
		} 
	}
	@Test 
	public void testIllegalCredentials(){
		User testbruker; 
		try{
			testbruker = new User("Ola22", "Nordmann", "olnord", "password", "mail@mail.com");
			fail("Ola22 is an invalid first name"); 
		}catch (Exception e){
			testException(e, IllegalArgumentException.class);
		}
		try{
			testbruker = new User("Ola", "Nord mann", "olnord", "password", "mail@mail.com");
			fail("Nord mann is an invalid last name"); 
		}catch (Exception e){
			testException(e, IllegalArgumentException.class);
		}
		try {
			testbruker = new User("Ola", "Nordmann", "olnord", "password", "per.stian@ntnu@no"); 
			fail("a mail adress shout only contain one @");
		}catch (Exception e){
			testException(e, IllegalArgumentException.class);
		}

	}
	@Test
	public void testSetMail(){
		try{
			User testbruker = new User("Ola", "Nordmann", "olnord", "password", "mail@mail.com");
			System.out.println(testbruker.getMail());
			testbruker.setMail("peppes@pizza.no");
			System.out.println(testbruker.getMail());
			assertEquals("peppes@pizza.com", testbruker.getMail()); 
		}catch (Exception e){
			
		}
	}
}


