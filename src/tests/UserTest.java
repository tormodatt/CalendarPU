package tests;
import static org.junit.Assert.*;

import org.junit.Test;

import user.User;
import calendar.PersonalCalendar;

public class UserTest {

	@Test //et helt lovlig brukernavn
	public void testSetUser1() throws Exception {
		User testbruker = new User("olnord"); 

	}
	@Test //det skal ikke være lov med tall i brukernavnet
	public void testSetUser2(){
		try{
			User testbruker = new User("bh1nn");	
		}catch (Exception e){
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


