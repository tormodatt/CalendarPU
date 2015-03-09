package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import calendar.Calendar;
import user.Group;
import user.User;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.sql.*;
import java.util.ArrayList;

import calendar.Database;


public class CalendarTest {
	private PreparedStatement preparedStatement; // bruker dette feltet til å skrive til databasen
	private ResultSet resultSet;

	@Test
	//tester kun lovlig input
	public void SetCalendarTest1(){ 
		try{
			User ola = new User("perOlsen");
			Calendar ola2015 = new Calendar(ola, "ola 2015"); 
			assertEquals("perOlsen", ola2015.getUser());
			assertEquals("ola 2015", ola2015.getTitle()); 

		} catch (Exception e){

		}try{
			User ola = new User("perOlsen"); 
			Group gruppe = new Group("test", ola); 
			Calendar test2015 = new Calendar(gruppe, "test2015"); 
			assertEquals("gruppe", test2015.getGroup()); 
			assertEquals("test2015", test2015.getTitle()); 

		}catch (Exception e){
		}

	}
	@Test
	//tester ulovlige kalendertitteler
	public void SetCalendarTest2(){
		try{
			User ola = new User("perOlsen"); 
			Calendar ola2015 = new Calendar(ola, "gru??"); 

		}catch (Exception e){
			new IllegalArgumentException(); 
		}
		try{
			User ola = new User("perOlsen"); 
			Group gruppe = new Group("test", ola);
			Calendar test2015 = new Calendar(gruppe, "?? ulovlig navn"); 

		}catch(Exception e){
			new IllegalArgumentException(); 

		}
	}
	@Test
	public void GetCalendeTest1(){ //hente et en kalender som er knyttet til en bruker
		try {
			User ola = new User("perOlsen");
			Calendar test = new Calendar(ola); 
			assertEquals("perOlsen", test.getUser()); 
			assertEquals("test2015", test.getTitle()); 
		}catch (Exception e) {

		} 
	}

}
