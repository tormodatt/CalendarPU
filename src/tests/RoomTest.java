package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Appointment.Room;

public class RoomTest {

	@Test
	public void MakeRoomTest() {
		try{
			String roomID = "RFK"; 
			int capasity = 20; 
			Room testrom = new Room(roomID, capasity); 
			assertEquals(20, testrom.getCapacity()); 
			assertEquals("RFK", testrom.getRoomID()); 

		}catch (Exception e){
		}

		try{
			Room testrom = new Room("test##", 10); 
			fail("the roomname is not valdig"); 
		}catch (Exception e){
			new IllegalArgumentException(); 
		}

		try{
			Room testrom = new Room("test", -10); 
			fail("the capasity must be positive"); 
		}catch (Exception e){
			new IllegalArgumentException(); 
		}
	}
	@Test
	public void CapasityTest(){
		try{
			Room testrom = new Room("test", -20); 
			fail("the capasity of a room cannot be negative"); 
		}catch (Exception e){
			new IllegalArgumentException(); 
		}

		try{
			Room testrom = new Room("test", 20);
			assertEquals(20,  testrom.getCapacity());
			testrom.setCapacity(45);
			assertEquals(45, testrom.getCapacity());
		}catch (Exception e){

		}
		try{
			Room testrom = new Room("test", 20); 
			testrom.setCapacity(-10);
			fail("the capasity of a room vannot be negative"); 
		}catch (Exception e){
			new IllegalArgumentException(); 
		}
	}

	@Test
	public void roomIDTest(){
		try{
			Room testrom = new Room("####???", 20); 
			fail("a roomID cannot contain other than 0-9 a-z"); 
		}catch(Exception e){
			new IllegalArgumentException(); 
		}
		try{
			Room testrom = new Room("lovlig navn", 20); 
			assertEquals("lovlig navn", testrom.getRoomID());
		}catch (Exception e){

		}
	}


}
