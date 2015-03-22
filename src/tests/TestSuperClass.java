package tests;

import java.sql.PreparedStatement;

import Appointment.Room;
import calendar.Database;

public class TestSuperClass extends Database {
	
	protected void deleteUser(String username) throws Exception {
		openConn();
		PreparedStatement ps = connect.prepareStatement("DELETE FROM `User` WHERE `Username`=?;");
		ps.setString(1, username);
		ps.executeUpdate();
		closeConn();
	}
	
	protected void deleteGroup(int groupID) throws Exception  {
		openConn();
		PreparedStatement ps = connect.prepareStatement("DELETE FROM `Group` WHERE `GroupID`=?;");
		ps.setInt(1, groupID);
		ps.executeUpdate();
		closeConn();
	}
	
	protected void deleteRoom(String romnavn) throws Exception  {
		openConn();
		PreparedStatement ps = connect.prepareStatement("DELETE FROM `Room` WHERE `Name`=?;");
		ps.setString(1, romnavn);
		ps.executeUpdate();
		closeConn();
	}
	
	protected void deleteAppointment(int appointmentID) throws Exception  {
		openConn();
		PreparedStatement ps = connect.prepareStatement("DELETE FROM `Appointment` WHERE `AppointmentID`=?;");
		ps.setInt(1, appointmentID);
		ps.executeUpdate();
		closeConn();
	}
	
	protected void deleteCalendar(int calendarID) throws Exception  {
		openConn();
		PreparedStatement ps = connect.prepareStatement("DELETE FROM `Calendar` WHERE `CalendarID`=?;");
		ps.setInt(1, calendarID);
		ps.executeUpdate();
		closeConn();
	}
	
	
	public static void main(String[] args) throws Exception {
		AppointmentTest a = new AppointmentTest();
		// a.deleteUser("sandra");
		// User sandra = new User("sandra");
		// User sandra = new User("Sandra", "Buadu", "sandra", "pass", "sandra@gotmail.com");
		// System.out.println(sandra.getPersonalCalendar().getCalendarID());
		
		Room soverommet = new Room("soverommet", 2, "S2"); 
		// a.deleteRoom("Kantinen");
	}
	
}
