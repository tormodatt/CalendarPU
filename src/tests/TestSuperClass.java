package tests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import user.Group;
import user.User;
import calendar.Database;

public class TestSuperClass extends Database {
	
	protected void deleteUser(String username) throws Exception {
		openConn();
		
		int cid = -1;
		PreparedStatement ps1 = connect.prepareStatement("SELECT `CalendarID` FROM `Calendar` WHERE `Username`=?");
		ps1.setString(1, username);
		ResultSet rs = ps1.executeQuery();
		while (rs.next()) {
			cid = rs.getInt(1);
		}
		
		PreparedStatement ps2 = connect.prepareStatement("DELETE FROM `User` WHERE `Username`=?;");
		ps2.setString(1, username);
		ps2.executeUpdate();
				
		PreparedStatement ps3 = connect.prepareStatement("DELETE FROM `Calendar` WHERE `CalendarID`=?;");
		ps3.setInt(1, cid);
		ps3.executeUpdate();
		closeConn();
	}
	
	protected void deleteUser(User user) throws Exception {
		deleteUser(user.getUsername());
	}
	
	protected void deleteGroup(int groupID) throws Exception  {
		openConn();
		PreparedStatement ps = connect.prepareStatement("DELETE FROM `Group` WHERE `GroupID`=?;");
		ps.setInt(1, groupID);
		ps.executeUpdate();
		closeConn();
	}
	
	protected void deleteGroup(Group group) throws Exception {
		deleteGroup(group.getGroupID());
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
		TestSuperClass a = new TestSuperClass();
//		User u = new User("En", "Person", "enPerson", "pass", "en.person@mail.com");
//		a.deleteUser("enPerson");
//		System.out.println(u.getPersonalCalendar().getCalendarID());
		
		User perOlsen = new User("Per", "Olsen", "perOlsen", "pass", "per.olsen@mail.com");
		User torNilsen = new User("Tor", "Nilsen", "torNilsen", "pass", "tor.nilsen@mail.com");
		Group gruppe1 = new Group("gruppe1", perOlsen);
		Group gruppe2 = new Group("gruppe2", torNilsen);
		Group gruppe3 = new Group("gruppe3", perOlsen);
		
	}
	
}
