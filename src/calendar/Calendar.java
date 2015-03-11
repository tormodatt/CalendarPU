package calendar;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import sun.applet.Main;
import user.*;
import Appointment.*;

public class Calendar extends Database {

	private int calendarID;
	private String title;
	private User user;
	private Group group;

	public ArrayList<Appointment> appointments;

	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	//Opprette personlig kalender
	public Calendar(User user, String title) throws Exception {
		try {
			if(isValidTitle(title)){
				openConn();
				preparedStatement = connect.prepareStatement("insert into Calendar (Title, Username) values (?,?)");
				preparedStatement.setString(1,title);
				preparedStatement.setString(2,user.getUsername());
				preparedStatement.executeUpdate();
			}else throw new IllegalArgumentException("The calendars title is not valid"); 
		} finally {
			closeConn();
		}
		this.user = user; 
		this.title = title; 
	}

	//Opprette gruppekalender
	public Calendar(Group group, String title) throws Exception {

		try {
			if(isValidTitle(title)){
				openConn();
				preparedStatement = connect.prepareStatement("insert into Calendar (Title, GroupID) values (?,?)");
				preparedStatement.setString(1,title);
				preparedStatement.setInt(2,group.getGroupID());
				preparedStatement.executeUpdate();
			}else throw new IllegalArgumentException("The title is not valid");
		}finally {
			closeConn();
		}
		this.group = group; 
		this.title = title; 
	}

	//Hente personlig kalender
	public Calendar(User user) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Calendar WHERE Username=?");
			preparedStatement.setString(1,user.getUsername());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.calendarID = resultSet.getInt("CalendarID");
				this.title = resultSet.getString("Title");
			}
			setAppointments();
		} finally {
			closeConn();
		}
		this.user = user;
	}

	//Hente gruppekalender
	public Calendar(Group group) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Calendar WHERE GroupID=?");
			preparedStatement.setInt(1,group.getGroupID());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.calendarID = resultSet.getInt("CalendarID");
				this.title = resultSet.getString("Title");
			}
			setAppointments();
		} finally {
			closeConn();
		}
		this.group = group; 
	}

	//Gettere
	public int getCalendarID() {
		return calendarID;
	}
	public String getTitle() {
		return title;
	}
	public User getUser() {
		return user;
	}
	public Group getGroup() {
		return group;
	}

	//Settere
	public void setAppointments() throws Exception {
		preparedStatement = connect.prepareStatement("select AppointmentID from Appointment WHERE CalendarID = ?");
		preparedStatement.setInt(1, calendarID);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			this.appointments.add(new Appointment(resultSet.getInt("AppointmentID")));
		}
	}

	//Valideringsmetoder
	private static boolean isValidTitle(String title){
		String str = title.toLowerCase(); 
		int index = 0; 
		for (int i = 0; i < str.length();  ++i){
			char c = str.charAt(i); 
			if((c <= 'z' && c>='a') || (c >= '0' && c <= '9') || c == 'æ' || c == 'ø' || c== 'å' || c == '-' || c == ' '){
				++index; 
			}else return false; 
		}
		if(index < 3) return false; 
		return true; 
	}

}
