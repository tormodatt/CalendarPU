package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Appointment.Room;

public class RoomTest {

	@Test
	public void MakeRoomTest() { 
		try{//her skal alle verdier være lovlige
			String roomName = "RFK"; 
			int capasity = 20; 
			String location = "U2-1"; 
			Room testrom = new Room(roomName, capasity, location); 
			assertEquals(20, testrom.getCapacity()); 
			assertEquals("RFK", testrom.getRoomName()); 
			assertEquals("U2-1", testrom.getLocation());
		}catch (Exception e){
			new IllegalArgumentException(); 
		}

		try{ //det er ikke lov med symboler i romnavnet
			Room testrom22 = new Room("test22##", 10, "U2-1"); 
			fail("the roomname is not valid"); 
		}catch (Exception e){
			new IllegalArgumentException(); 
		}

		try{ //kapasiteten til et rom skal ikke være negativ
			Room testrom = new Room("test", -10, "U2-1"); 
			fail("the capasity must be positive"); 
		}catch (Exception e){
			new IllegalArgumentException(); 
		}
		try{ //Location til et rom skal kun bestå av 0-9, a-z og - 
			Room testrom = new Room("test", 10, "U..3"); 
			fail("the room location cannot contain signs other than '-'"); 
		}catch (Exception e){
			new IllegalArgumentException(); 
		}
	}
	@Test
	public void CapasityTest(){
		try{
			Room testrom = new Room("test", -10, "U2-1"); 
			fail("the capasity of a room cannot be negative"); 
		}catch (Exception e){
			new IllegalArgumentException(); 
		}

		try{
			Room testrom = new Room("test", 20, "U2-1");
			assertEquals(20,  testrom.getCapacity());
			testrom.setCapacity(45);
			assertEquals(45, testrom.getCapacity());
		}catch (Exception e){

		}
		try{
			Room testrom = new Room("test", 20, "U2-1"); 
			testrom.setCapacity(-10);
			fail("the capasity of a room vannot be negative"); 
		}catch (Exception e){
			new IllegalArgumentException(); 
		}
	}

	@Test
	public void roomNameTest(){
		try{
			Room testrom = new Room("####???", 20, "U2-1"); 
			fail("a roomID cannot contain other than 0-9 a-z"); 
		}catch(Exception e){
			new IllegalArgumentException(); 
		}
		try{
			Room testrom = new Room("lovlig navn", 20, "U2-1"); 
			assertEquals("lovlig navn", testrom.getRoomName());
		}catch (Exception e){

		}
	}
	@Test
	public void roomLocationTest(){
		try{
			Room testrom = new Room("test", 20, "U2-1/"); 
			fail("the location is not valid");
		}catch (Exception e){
			new IllegalArgumentException();  
		}
		try{
			Room testrom = new Room("test", 20, "123ABC"); 
			assertEquals("123ABC", testrom.getLocation()); 
		}catch(Exception e){

		}
	}

	@Test
	public void addAppointmentTest(){
		fail("not yet implemented"); 
	}

	@Test
	public void removeAppointmentTest(){
		fail("not yet implemented"); 
	}



}
