package user;

import static org.junit.Assert.*;

import org.junit.Test;

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

	@Test
	public void testSetCrendentials(){
		User testbruker;
		try {
			testbruker = new User("olnord");
			testbruker.setCredencials("Ola", "Nordmann", "olnord", "password", "mail@mail.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testChangeEmail(){

		testbruker.setMail("brodskrive@polegg@src.no");

	}



}


